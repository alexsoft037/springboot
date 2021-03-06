package xin.xiaoer.modules.website.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import xin.xiaoer.common.oss.CloudStorageService;
import xin.xiaoer.common.oss.OSSFactory;
import xin.xiaoer.common.utils.PageUtils;
import xin.xiaoer.common.utils.Query;
import xin.xiaoer.common.utils.StringUtility;
import xin.xiaoer.entity.Article;
import xin.xiaoer.entity.File;
import xin.xiaoer.entity.SysUser;
import xin.xiaoer.modules.activity.entity.Activity;
import xin.xiaoer.modules.activity.entity.ActivityAttend;
import xin.xiaoer.modules.activity.entity.ActivityUserSign;
import xin.xiaoer.modules.activity.service.ActivityAttendService;
import xin.xiaoer.modules.activity.service.ActivityService;
import xin.xiaoer.modules.activity.service.ActivitySignNumberService;
import xin.xiaoer.modules.activity.service.ActivityUserSignService;
import xin.xiaoer.modules.activityreport.entity.ActivityReport;
import xin.xiaoer.modules.activityreport.service.ActivityReportService;
import xin.xiaoer.modules.favourite.entity.Favourite;
import xin.xiaoer.modules.favourite.service.FavouriteService;
import xin.xiaoer.modules.like.entity.Like;
import xin.xiaoer.modules.like.service.LikeService;
import xin.xiaoer.modules.mobile.bean.ResponseBean;
import xin.xiaoer.modules.mobile.entity.ActivityListItem;
import xin.xiaoer.modules.mobile.entity.ActivityReportListItem;
import xin.xiaoer.modules.review.service.ReviewService;
import xin.xiaoer.modules.setting.service.IntegralService;
import xin.xiaoer.service.CommparaService;
import xin.xiaoer.service.FileService;
import xin.xiaoer.service.SysUserService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("website/activity")
@Api("????????????")
public class WebAPIActivityController {
    @Value("${baidu.apiKey}")
    private String baiduKey;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private FileService fileService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ActivityAttendService activityAttendService;

    @Autowired
    private CommparaService commparaService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private ActivitySignNumberService activitySignNumberService;

    @Autowired
    private ActivityUserSignService activityUserSignService;

    @Autowired
    private IntegralService integralService;
    @Autowired
    private ActivityReportService activityReportService;

    @Autowired
    private FavouriteService favouriteService;


    //????????????
    /**
     * @return xin.xiaoer.modules.mobile.bean.ResponseBean
     * @Description TODO ????????????
     * @Param [params, request]
     * @Author XiaoDong ??????
     **/
    @RequestMapping("/getHotActivity")
    public ResponseBean HotActivity(@RequestParam Map<String, String> params, HttpServletRequest request) {
        //???????????????
//        String curPageNum = params.get("page").toString();
//        String pageCount = params.get("limit").toString();
//        params.put("page", curPageNum);
//        params.put("limit", pageCount);
//        Query query = new Query(params);
//        List<Map> hotActivityList = activityService.getHotActivity(query);
//        CloudStorageService cloudStorageService = OSSFactory.build();
//        for (Map donatemap : hotActivityList) {
//            donatemap.put("featured_image", cloudStorageService.generatePresignedUrl((String) donatemap.get("featured_image")));
//            //System.out.println("++++++++++"+donatemap.get("avatar"));
//        }
//        int total = activityService.gethotacCount(query);
//        PageUtils pageUtil = new PageUtils(hotActivityList, total, query.getLimit(), query.getPage());
//        return new ResponseBean(false, "success", null, pageUtil);
        //??????????????????

//        String requestPageCount = request.getParameter("pageCount");
        String pageCount = params.get("pageCount");
        String curPageNum = params.get("curPageNum");
        String activityStatusCode = params.get("activityStatus");
        params.put("limit", pageCount);
        params.put("page", curPageNum);
        params.put("state", "1");
        params.put("activityStatusCode", activityStatusCode);
        CloudStorageService cloudStorageService = OSSFactory.build();
        Map<String, Object> checkParam = new HashMap<>();
        checkParam.put("activityStatusCode", activityStatusCode);
        List<Activity> activities = activityService.getList(checkParam);
        for (Activity activity: activities){
            activityService.checkExpired(activity);
        }
        //??????????????????
        PageHelper.startPage(Integer.parseInt(curPageNum),Integer.parseInt(pageCount));
        params.remove("pageCount");
        params.remove("curPageNum");
        List<Map<String,String>> activityListItems = activityService.getHotActivity(params);
        for (Map<String,String> map : activityListItems) {
            if (!StringUtils.isBlank(map.get("featuredImage"))){
                map.put("featuredImage",cloudStorageService.generatePresignedUrl(map.get("featuredImage")));
            }
        }
        PageInfo<Map<String,String>> info = new PageInfo<>(activityListItems);
        return new ResponseBean(false, "success", null, info);
    }

    //????????????
    @RequestMapping("/getActivityAppraisal")
    public ResponseBean ActivityAppraisal(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        //???????????????
        String curPageNum = params.get("page").toString();
        String pageCount = params.get("limit").toString();
        params.put("page", curPageNum);
        params.put("limit", pageCount);
        Query query = new Query(params);
        List<Map> appraisalActivityList = activityService.getActivityAppraisal(query);
        CloudStorageService cloudStorageService = OSSFactory.build();
        for (Map donatemap : appraisalActivityList) {
            List<File> files = fileService.getByRelationId(donatemap.get("featured_image").toString());
            if (files.size() > 0){
                donatemap.put("featured_image",cloudStorageService.generatePresignedUrl(files.get(0).getUrl()));
            }
        }
        int total = activityService.getappraisalCount(query);
        PageUtils pageUtil = new PageUtils(appraisalActivityList, total, query.getLimit(), query.getPage());
        return new ResponseBean(false, "success", null, pageUtil);
    }

    //?????????????????????
    @RequestMapping("/getActivityAppraisalDetail")
    public ResponseBean ActivityAppraisalDetail(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        //???????????????
        String appraisalId = params.get("appraisalId").toString();
        params.put("appraisalId", appraisalId);
        List<Map> appraisalActivityList = activityService.getActivityAppraisalDetail(params);
        CloudStorageService cloudStorageService = OSSFactory.build();
        for (Map donatemap : appraisalActivityList) {
            donatemap.put("featured_image", cloudStorageService.generatePresignedUrl((String) donatemap.get("featured_image")));
            //System.out.println("++++++++++"+donatemap.get("avatar"));
        }
        return new ResponseBean(false, "success", null, appraisalActivityList);
    }

    /**
     * @return xin.xiaoer.modules.mobile.bean.ResponseBean
     * @Author dong
     * @Description TODO ??????????????????
     * @Param [reportId, userId]
     **/
    @RequestMapping(value = "/activityReportDetail", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("??????????????????")
    public ResponseBean activityReportDetail(Integer reportId, Long userId) {
        //??????????????????

        ActivityReport activityReport = activityReportService.get(reportId);
        if (activityReport == null || !"1".equals(activityReport.getState())) {
//            return R.error("??????????????????");
            return new ResponseBean(false, ResponseBean.FAILED, "????????????", "??????????????????", null);
        }

        if (userId != null) {
            Like like = likeService.getByArticleAndUser(Article.ACTIVITY_REPORT, Long.parseLong(Integer.toString(reportId)), userId);
            if (like == null) {
                activityReport.setLikeYn(false);
            } else {
                activityReport.setLikeYn(true);
            }
            Favourite favourite = favouriteService.getByArticleAndUser(Article.ACTIVITY_REPORT, activityReport.getReportId().longValue(), userId);
            if (favourite != null) {
                activityReport.setFavouriteYn(true);
            } else {
                activityReport.setFavouriteYn(false);
            }
        } else {
            activityReport.setLikeYn(false);
            activityReport.setFavouriteYn(false);
        }
        activityReport.setReadCount(activityReport.getReadCount() + 1);
        activityReportService.update(activityReport);
        Integer reviewCount = reviewService.getCountByCodeAndId(Article.ACTIVITY_REPORT, Long.parseLong(Integer.toString(reportId)));
        activityReport.setReviewCount(reviewCount);

        CloudStorageService cloudStorageService = OSSFactory.build();
        List<File> files = fileService.getByRelationId(activityReport.getFeaturedImage());
        SysUser sysUser = sysUserService.queryObject(activityReport.getCreateBy());
        if (sysUser != null) {
            if (StringUtility.isEmpty(sysUser.getNickname())) {
                activityReport.setCreateUser(activityReport.getAuthorName());
            } else {
                activityReport.setCreateUser(sysUser.getNickname());
            }
            if (sysUser.getUserClassCode() != null) {
                if (sysUser.getUserClassCode().equals(SysUser.NEWSMAN)) {
                    activityReport.setByNewsMan(true);
                } else {
                    activityReport.setByNewsMan(false);
                }
            } else {
                activityReport.setByNewsMan(false);
            }
        } else {
            activityReport.setByNewsMan(false);
        }

        if (files.size() > 0) {
            if (!StringUtility.isEmpty(files.get(0).getUrl())) {
                activityReport.setFeaturedImage(cloudStorageService.generatePresignedUrl(files.get(0).getUrl()));
            } else {
                activityReport.setFeaturedImage("");
            }
        } else {
            activityReport.setFeaturedImage("");
        }

//        return R.ok().put("list", activityReport);
        return new ResponseBean(false, ResponseBean.SUCCESS, null, activityReport);
    }

    /**
     * @return xin.xiaoer.modules.mobile.bean.ResponseBean
     * @Author dong
     * @Description //TODO ??????????????????
     * @Param [params]
     **/
    @RequestMapping(value = "/activityReport", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("????????????")
    public ResponseBean activityReport(@RequestParam Map<String, Object> params) {
        //??????????????????

        if (params.get("pageCount") == null || params.get("curPageNum") == null) {
//            return R.error("????????????");
            return new ResponseBean(false, ResponseBean.FAILED, "????????????", "????????????", null);
        }
//        String requestPageCount = request.getParameter("pageCount");
        String pageCount = params.get("pageCount").toString();
        String curPageNum = params.get("curPageNum").toString();
        String userId = null;
        if (params.get("userId") != null) {
            userId = params.get("userId").toString();
        }


//        if (params.get("spaceId")!=null){
//
//            String spaceId = params.get("spaceId").toString();
//            if (StringUtility.isEmpty(spaceId)) {
//                params.put("spaceId", 0);
//            }
//        }else {
//                params.put("spaceId", 0);
//
//        }

        if (params.get("spaceId") == null || StringUtils.isBlank(params.get("spaceId").toString())) {
            params.put("spaceId", 0);
        }
        params.put("limit", pageCount);
        params.put("page", curPageNum);
        params.put("state", "1");
        params.remove("userId");
        Query query = new Query(params);
        CloudStorageService cloudStorageService = OSSFactory.build();
        List<ActivityReportListItem> reportListItems = activityReportService.queryListData(query);
        for (ActivityReportListItem reportListItem : reportListItems) {
            String reportId = Integer.toString(reportListItem.getReportId());
            if (StringUtils.isNotBlank(userId)) {
                Like like = likeService.getByArticleAndUser(Article.ACTIVITY_REPORT, Long.parseLong(reportId), Long.parseLong(userId));
                if (like == null) {
                    reportListItem.setLikeYn(false);
                } else {
                    reportListItem.setLikeYn(true);
                }
            } else {
                reportListItem.setLikeYn(false);
            }
            SysUser sysUser = sysUserService.queryObject(reportListItem.getCreateBy());
            if (sysUser != null) {
                reportListItem.setCreateUser(sysUser.getNickname());
                if (sysUser.getUserClassCode() != null) {
                    if (sysUser.getUserClassCode().equals(SysUser.NEWSMAN)) {
                        reportListItem.setByNewsMan(true);
                    } else {
                        reportListItem.setByNewsMan(false);
                    }
                } else {
                    reportListItem.setByNewsMan(false);
                }
            } else {
                reportListItem.setByNewsMan(false);
            }

            if (!StringUtility.isEmpty(reportListItem.getFeaturedImage())) {
                reportListItem.setFeaturedImage(cloudStorageService.generatePresignedUrlWithResize(reportListItem.getFeaturedImage(), "320", "320"));
            }
        }
        int total = activityReportService.countListData(query);

        PageUtils pageUtil = new PageUtils(reportListItems, total, query.getLimit(), query.getPage());

//        return R.ok().put("list", pageUtil);
        return new ResponseBean(false, ResponseBean.SUCCESS, null, pageUtil);
    }

    /**
     * @return xin.xiaoer.modules.mobile.bean.ResponseBean
     * @Author dong
     * @Description //TODO ??????????????????,?????????????????????
     * @Param [spaceId]
     **/
    @ApiOperation("????????????")
    @GetMapping("/splendidActivity")
    @ResponseBody
    public ResponseBean splendidActivity(Integer spaceId) {

        List<Activity> list = activityService.getSplendidActivity(spaceId);
//        return R.ok().put("list", list);
        return new ResponseBean(false, ResponseBean.SUCCESS, null, list);
    }

    /**
     * @return xin.xiaoer.modules.mobile.bean.ResponseBean
     * @Author dong
     * @Description //TODO ????????????
     * @Param [params, request]
     **/
    @RequestMapping(value = "/listData", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("??????????????????")
    public ResponseBean listData(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        //??????????????????

        if (params.get("pageCount") == null || params.get("curPageNum") == null || params.get("activityStatus") == null || params.get("spaceId") == null) {
//            return R.error("????????????");
            return new ResponseBean(false, ResponseBean.FAILED, "????????????", "????????????", null);
        }
        String city = (String) request.getSession().getAttribute("city");
//      String requestPageCount = request.getParameter("pageCount");
        String pageCount = params.get("pageCount").toString();//??????
        String curPageNum = params.get("curPageNum").toString();//?????????
        String activityStatusCode = params.get("activityStatus").toString();//????????????
        String spaceId = params.get("spaceId").toString();//??????id
        if (StringUtility.isEmpty(spaceId)) {
            params.put("spaceId", 0);
        }
        params.put("limit", pageCount);
        params.put("page", curPageNum);
        params.put("state", "1");
        params.put("activityStatusCode", activityStatusCode);
        params.put("city", city);
        Query query = new Query(params);

        CloudStorageService cloudStorageService = OSSFactory.build();//????????????
        Map<String, Object> checkParam = new HashMap<>();
        checkParam.put("activityStatusCode", activityStatusCode);
        List<Activity> activities = activityService.getList(checkParam);//????????????,????????????????????????
        for (Activity activity : activities) {
            activityService.checkExpired(activity);//????????????????????????
        }
        List<ActivityListItem> activityListItems = activityService.queryListData(query);//????????????
        for (ActivityListItem activityListItem : activityListItems) {
//            activityListItem.setAttendYN(false);
            Integer totalUsers = activityAttendService.getCountByActivityId(activityListItem.getActivityId());//?????????????????????
            activityListItem.setTotalUsers(totalUsers);//??????????????????
            if (!StringUtility.isEmpty(activityListItem.getFeaturedImage())) {
                activityListItem.setFeaturedImage(cloudStorageService.generatePresignedUrl(activityListItem.getFeaturedImage()));//????????????
            }
        }
        int total = activityService.countListData(query);

        PageUtils pageUtil = new PageUtils(activityListItems, total, query.getLimit(), query.getPage());

        return new ResponseBean(false, ResponseBean.SUCCESS, null, pageUtil);
    }

    /**
     * @return xin.xiaoer.modules.mobile.bean.ResponseBean
     * @Author dong
     * @Description //TODO ????????????
     * @Param [activityId, userId]
     **/
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("????????????")
    public ResponseBean detail(Integer activityId, Long userId) throws Exception {
        Activity activity = activityService.get(activityId);//??????id????????????
        if (activity == null || !"1".equals(activity.getState())) {
            return new ResponseBean(false, ResponseBean.FAILED, "????????????", "????????????", null);
        }
        Map map = new HashMap();
        map.put("articleId", activity.getActivityId());
        map.put("articleTypeCode", Article.ACTIVITY);
        int count = likeService.getCount(map);//?????????
        activity = activityService.checkExpired(activity);//????????????????????????

        activity.setReadCount(activity.getReadCount() + 1);//????????????+1
        activityService.update(activity);//???????????????

        SysUser sysUser = sysUserService.queryObject(activity.getCreateBy());//?????????

        Integer totalUsers = activityAttendService.getCountByActivityId(activityId);//????????????

        if (userId != null) {
            ActivityAttend activityAttend = activityAttendService.getByUserIdAndActivityId(activityId, userId);//????????????id?????????id???????????????
            if (activityAttend != null) {//????????????????????????
                activity.setAttendYN(true);
            } else {
                activity.setAttendYN(false);
            }
            Like like = likeService.getByArticleAndUser(Article.ACTIVITY/*????????????*/, Long.parseLong(Integer.toString(activityId))/*??????id*/, userId/*??????id*/);
            if (like == null) {//????????????????????????
                activity.setLikeYn(false);
            } else {
                activity.setLikeYn(true);
            }
            ActivityUserSign activityUserSign = activityUserSignService.getByUserAndActivity(userId, activityId);//?????????
            if (activityUserSign == null) {
                activity.setSignYn(false);
            } else {
                if (activityUserSign.getState().equals("1")) {
                    activity.setSignYn(true);
                } else {
                    activity.setSignYn(false);
                }
            }
        } else {
            activity.setSignYn(false);
            activity.setLikeYn(false);
            activity.setAttendYN(false);
        }


        Integer reviewCount = reviewService.getCountByCodeAndId(Article.ACTIVITY, Long.parseLong(Integer.toString(activityId)));
        activity.setReviewCount(reviewCount);//?????????

        activity.setCreateUser(sysUser.getNickname());//???????????????
        activity.setTotalUsers(totalUsers);//??????????????????

        CloudStorageService cloudStorageService = OSSFactory.build();//????????????????????????
        List<File> files = fileService.getByRelationId(activity.getFeaturedImage());

        if (files.size() > 0) {
            if (!StringUtility.isEmpty(files.get(0).getUrl())) {
                activity.setFeaturedImage(cloudStorageService.generatePresignedUrl(files.get(0).getUrl()));
            } else {
                activity.setFeaturedImage("");
            }
        } else {
            activity.setFeaturedImage("");
        }

        map.clear();
        map.put("data", activity);
        map.put("likeCount", count);
        return new ResponseBean(false, ResponseBean.SUCCESS, null, map);
    }


    /**
     * @return xin.xiaoer.modules.mobile.bean.ResponseBean
     * @Author XiaoDong
     * @Description TODO ????????????
     * @Param [params, request]
     **/
    @RequestMapping(value = "/listData1", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("??????????????????")
    public ResponseBean listData1(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        //??????????????????

        if (params.get("pageCount") == null || params.get("curPageNum") == null || params.get("activityStatus") == null || params.get("spaceId") == null) {
//            return R.error("????????????");
//            return new ResponseBean(false,ResponseBean.FAILED,"?????????","????????????",null);
            return new ResponseBean(false, ResponseBean.FAILED, "????????????", "????????????", null);
        }
//        String requestPageCount = request.getParameter("pageCount");
        String pageCount = params.get("pageCount").toString();//??????
        String curPageNum = params.get("curPageNum").toString();//?????????
        String activityStatusCode = params.get("activityStatus").toString();//????????????
        String spaceId = params.get("spaceId").toString();//??????id
        if (StringUtility.isEmpty(spaceId)) {
            params.put("spaceId", 0);
        }
        params.put("limit", pageCount);
        params.put("page", curPageNum);
        params.put("state", "1");
        params.put("activityStatusCode", activityStatusCode);
        Query query = new Query(params);

        CloudStorageService cloudStorageService = OSSFactory.build();//????????????
        Map<String, Object> checkParam = new HashMap<>();
        checkParam.put("activityStatusCode", activityStatusCode);
        List<Activity> activities = activityService.getList(checkParam);
        for (Activity activity : activities) {
            activityService.checkExpired(activity);//????????????????????????
        }
        List<ActivityListItem> activityListItems = activityService.queryListData(query);//????????????
        for (ActivityListItem activityListItem : activityListItems) {
//            activityListItem.setAttendYN(false);0
            Integer totalUsers = activityAttendService.getCountByActivityId(activityListItem.getActivityId());//?????????????????????
            activityListItem.setTotalUsers(totalUsers);//??????????????????
            if (!StringUtility.isEmpty(activityListItem.getFeaturedImage())) {
                activityListItem.setFeaturedImage(cloudStorageService.generatePresignedUrl(activityListItem.getFeaturedImage()));//????????????
            }
        }
        int total = activityService.countListData(query);

        PageUtils pageUtil = new PageUtils(activityListItems, total, query.getLimit(), query.getPage());

//        return R.ok().put("list", pageUtil);
        return new ResponseBean(false, ResponseBean.SUCCESS, null, pageUtil);
    }

    /**
     * @return xin.xiaoer.modules.mobile.bean.ResponseBean
     * @Author XiaoDong
     * @Description TODO ??????
     * @Param [activityId, userId]
     **/
    @GetMapping("/attendActivity/{activityId}/{userId}")
    @ApiOperation("??????????????????")
    public ResponseBean attend(@PathVariable("activityId") Integer activityId, @PathVariable("userId") Long userId) {
        Activity activity = activityService.get(activityId);
        if (activity == null || !"1".equals(activity.getState().toString())) {
            return new ResponseBean(false, ResponseBean.FAILED, null, "???????????????", null);
        }
        SysUser sysUser = sysUserService.get(userId);
        if (sysUser == null || !"1".equals(sysUser.getStatus().toString())) {
            return new ResponseBean(false, ResponseBean.FAILED, null, "???????????????", null);
        }

        ActivityAttend attend = activityAttendService.getByUserIdAndActivityId(activityId, userId);
        if (attend != null && "1".equals(attend.getState())) {
            return new ResponseBean(false, ResponseBean.FAILED, null, "??????????????????", null);
        }
        if (attend == null) {
            attend  = new ActivityAttend();
            attend.setActivityId((long) activityId);
            attend.setState("1");
            attend.setUserId(userId);
            activityAttendService.save(attend);
            return new ResponseBean(false, ResponseBean.SUCCESS, null, null);
        }

        attend.setState("1");
        activityAttendService.update(attend);
        return new ResponseBean(false, ResponseBean.SUCCESS, null, null);
    }

    /**
     * @return xin.xiaoer.modules.mobile.bean.ResponseBean
     * @Description TODO ????????????-????????????
     * @Param [activityId, userId]
     * @Author XiaoDong
     **/
    @GetMapping("/cancelActivity/{activityId}/{userId}")
    public ResponseBean cancelActivity(@PathVariable("activityId") Integer activityId, @PathVariable("userId") Long userId) {
        Activity activity = activityService.get(activityId);
        if (activity == null || !"1".equals(activity.getState().toString())) {
            return new ResponseBean(false, ResponseBean.FAILED, null, "???????????????", null);
        }
        SysUser sysUser = sysUserService.get(userId);
        if (sysUser == null || !"1".equals(sysUser.getStatus().toString())) {
            return new ResponseBean(false, ResponseBean.FAILED, null, "???????????????", null);
        }
        ActivityAttend attend = activityAttendService.getByUserIdAndActivityId(activityId, userId);
        if (attend == null || !"1".equals(attend.getState())) {
            return new ResponseBean(false, ResponseBean.FAILED, null, "???????????????", null);
        }
        attend.setState("0");
        activityAttendService.update(attend);
        return new ResponseBean(false, ResponseBean.SUCCESS, null, null);
    }
}
