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
@Api("空间活动")
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


    //热门活动
    /**
     * @return xin.xiaoer.modules.mobile.bean.ResponseBean
     * @Description TODO 热门活动
     * @Param [params, request]
     * @Author XiaoDong 修改
     **/
    @RequestMapping("/getHotActivity")
    public ResponseBean HotActivity(@RequestParam Map<String, String> params, HttpServletRequest request) {
        //查询列表数
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
        //查询列表数据

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
        //以上修改状态
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

    //活动评选
    @RequestMapping("/getActivityAppraisal")
    public ResponseBean ActivityAppraisal(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        //查询列表数
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

    //活动评选详情页
    @RequestMapping("/getActivityAppraisalDetail")
    public ResponseBean ActivityAppraisalDetail(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        //查询列表数
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
     * @Description TODO 活动报道详情
     * @Param [reportId, userId]
     **/
    @RequestMapping(value = "/activityReportDetail", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("活动报道详情")
    public ResponseBean activityReportDetail(Integer reportId, Long userId) {
        //查询列表数据

        ActivityReport activityReport = activityReportService.get(reportId);
        if (activityReport == null || !"1".equals(activityReport.getState())) {
//            return R.error("无此活动报道");
            return new ResponseBean(false, ResponseBean.FAILED, "异常参数", "无此活动报告", null);
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
     * @Description //TODO 活动报道新闻
     * @Param [params]
     **/
    @RequestMapping(value = "/activityReport", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("活动报道")
    public ResponseBean activityReport(@RequestParam Map<String, Object> params) {
        //查询列表数据

        if (params.get("pageCount") == null || params.get("curPageNum") == null) {
//            return R.error("错误参数");
            return new ResponseBean(false, ResponseBean.FAILED, "参数异常", "缺少参数", null);
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
     * @Description //TODO 精彩活动列表,暂时以时间排序
     * @Param [spaceId]
     **/
    @ApiOperation("精彩活动")
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
     * @Description //TODO 活动列表
     * @Param [params, request]
     **/
    @RequestMapping(value = "/listData", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("空间活动列表")
    public ResponseBean listData(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        //查询列表数据

        if (params.get("pageCount") == null || params.get("curPageNum") == null || params.get("activityStatus") == null || params.get("spaceId") == null) {
//            return R.error("错误参数");
            return new ResponseBean(false, ResponseBean.FAILED, "参数异常", "缺少参数", null);
        }
        String city = (String) request.getSession().getAttribute("city");
//      String requestPageCount = request.getParameter("pageCount");
        String pageCount = params.get("pageCount").toString();//条数
        String curPageNum = params.get("curPageNum").toString();//当前页
        String activityStatusCode = params.get("activityStatus").toString();//进行状态
        String spaceId = params.get("spaceId").toString();//空间id
        if (StringUtility.isEmpty(spaceId)) {
            params.put("spaceId", 0);
        }
        params.put("limit", pageCount);
        params.put("page", curPageNum);
        params.put("state", "1");
        params.put("activityStatusCode", activityStatusCode);
        params.put("city", city);
        Query query = new Query(params);

        CloudStorageService cloudStorageService = OSSFactory.build();//图片服务
        Map<String, Object> checkParam = new HashMap<>();
        checkParam.put("activityStatusCode", activityStatusCode);
        List<Activity> activities = activityService.getList(checkParam);//查询列表,貌似是多余的查询
        for (Activity activity : activities) {
            activityService.checkExpired(activity);//校验活动是否结束
        }
        List<ActivityListItem> activityListItems = activityService.queryListData(query);//活动列表
        for (ActivityListItem activityListItem : activityListItems) {
//            activityListItem.setAttendYN(false);
            Integer totalUsers = activityAttendService.getCountByActivityId(activityListItem.getActivityId());//此活动参与人数
            activityListItem.setTotalUsers(totalUsers);//设置参与人数
            if (!StringUtility.isEmpty(activityListItem.getFeaturedImage())) {
                activityListItem.setFeaturedImage(cloudStorageService.generatePresignedUrl(activityListItem.getFeaturedImage()));//设置图片
            }
        }
        int total = activityService.countListData(query);

        PageUtils pageUtil = new PageUtils(activityListItems, total, query.getLimit(), query.getPage());

        return new ResponseBean(false, ResponseBean.SUCCESS, null, pageUtil);
    }

    /**
     * @return xin.xiaoer.modules.mobile.bean.ResponseBean
     * @Author dong
     * @Description //TODO 活动详情
     * @Param [activityId, userId]
     **/
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation("活动详情")
    public ResponseBean detail(Integer activityId, Long userId) throws Exception {
        Activity activity = activityService.get(activityId);//根据id查询活动
        if (activity == null || !"1".equals(activity.getState())) {
            return new ResponseBean(false, ResponseBean.FAILED, "参数异常", "无此活动", null);
        }
        Map map = new HashMap();
        map.put("articleId", activity.getActivityId());
        map.put("articleTypeCode", Article.ACTIVITY);
        int count = likeService.getCount(map);//点赞数
        activity = activityService.checkExpired(activity);//校验活动进行状态

        activity.setReadCount(activity.getReadCount() + 1);//阅读次数+1
        activityService.update(activity);//更新数据库

        SysUser sysUser = sysUserService.queryObject(activity.getCreateBy());//创建人

        Integer totalUsers = activityAttendService.getCountByActivityId(activityId);//参与人数

        if (userId != null) {
            ActivityAttend activityAttend = activityAttendService.getByUserIdAndActivityId(activityId, userId);//根据活动id和用户id查活动简讯
            if (activityAttend != null) {//貌似是否参与活动
                activity.setAttendYN(true);
            } else {
                activity.setAttendYN(false);
            }
            Like like = likeService.getByArticleAndUser(Article.ACTIVITY/*活动参与*/, Long.parseLong(Integer.toString(activityId))/*活动id*/, userId/*用户id*/);
            if (like == null) {//貌似是否点赞活动
                activity.setLikeYn(false);
            } else {
                activity.setLikeYn(true);
            }
            ActivityUserSign activityUserSign = activityUserSignService.getByUserAndActivity(userId, activityId);//没搞懂
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
        activity.setReviewCount(reviewCount);//评论数

        activity.setCreateUser(sysUser.getNickname());//设置创建人
        activity.setTotalUsers(totalUsers);//设置参与人数

        CloudStorageService cloudStorageService = OSSFactory.build();//一下都是获取图片
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
     * @Description TODO 活动列表
     * @Param [params, request]
     **/
    @RequestMapping(value = "/listData1", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("空间活动列表")
    public ResponseBean listData1(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        //查询列表数据

        if (params.get("pageCount") == null || params.get("curPageNum") == null || params.get("activityStatus") == null || params.get("spaceId") == null) {
//            return R.error("错误参数");
//            return new ResponseBean(false,ResponseBean.FAILED,"空指针","参数异常",null);
            return new ResponseBean(false, ResponseBean.FAILED, "参数异常", "缺少参数", null);
        }
//        String requestPageCount = request.getParameter("pageCount");
        String pageCount = params.get("pageCount").toString();//条数
        String curPageNum = params.get("curPageNum").toString();//当前页
        String activityStatusCode = params.get("activityStatus").toString();//进行状态
        String spaceId = params.get("spaceId").toString();//空间id
        if (StringUtility.isEmpty(spaceId)) {
            params.put("spaceId", 0);
        }
        params.put("limit", pageCount);
        params.put("page", curPageNum);
        params.put("state", "1");
        params.put("activityStatusCode", activityStatusCode);
        Query query = new Query(params);

        CloudStorageService cloudStorageService = OSSFactory.build();//图片服务
        Map<String, Object> checkParam = new HashMap<>();
        checkParam.put("activityStatusCode", activityStatusCode);
        List<Activity> activities = activityService.getList(checkParam);
        for (Activity activity : activities) {
            activityService.checkExpired(activity);//校验活动是否结束
        }
        List<ActivityListItem> activityListItems = activityService.queryListData(query);//活动列表
        for (ActivityListItem activityListItem : activityListItems) {
//            activityListItem.setAttendYN(false);0
            Integer totalUsers = activityAttendService.getCountByActivityId(activityListItem.getActivityId());//此活动参与人数
            activityListItem.setTotalUsers(totalUsers);//设置参与人数
            if (!StringUtility.isEmpty(activityListItem.getFeaturedImage())) {
                activityListItem.setFeaturedImage(cloudStorageService.generatePresignedUrl(activityListItem.getFeaturedImage()));//设置图片
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
     * @Description TODO 报名
     * @Param [activityId, userId]
     **/
    @GetMapping("/attendActivity/{activityId}/{userId}")
    @ApiOperation("报名参加活动")
    public ResponseBean attend(@PathVariable("activityId") Integer activityId, @PathVariable("userId") Long userId) {
        Activity activity = activityService.get(activityId);
        if (activity == null || !"1".equals(activity.getState().toString())) {
            return new ResponseBean(false, ResponseBean.FAILED, null, "无效的活动", null);
        }
        SysUser sysUser = sysUserService.get(userId);
        if (sysUser == null || !"1".equals(sysUser.getStatus().toString())) {
            return new ResponseBean(false, ResponseBean.FAILED, null, "无效的用户", null);
        }

        ActivityAttend attend = activityAttendService.getByUserIdAndActivityId(activityId, userId);
        if (attend != null && "1".equals(attend.getState())) {
            return new ResponseBean(false, ResponseBean.FAILED, null, "此用户已报名", null);
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
     * @Description TODO 活动详情-取消报名
     * @Param [activityId, userId]
     * @Author XiaoDong
     **/
    @GetMapping("/cancelActivity/{activityId}/{userId}")
    public ResponseBean cancelActivity(@PathVariable("activityId") Integer activityId, @PathVariable("userId") Long userId) {
        Activity activity = activityService.get(activityId);
        if (activity == null || !"1".equals(activity.getState().toString())) {
            return new ResponseBean(false, ResponseBean.FAILED, null, "无效的活动", null);
        }
        SysUser sysUser = sysUserService.get(userId);
        if (sysUser == null || !"1".equals(sysUser.getStatus().toString())) {
            return new ResponseBean(false, ResponseBean.FAILED, null, "无效的用户", null);
        }
        ActivityAttend attend = activityAttendService.getByUserIdAndActivityId(activityId, userId);
        if (attend == null || !"1".equals(attend.getState())) {
            return new ResponseBean(false, ResponseBean.FAILED, null, "无报名信息", null);
        }
        attend.setState("0");
        activityAttendService.update(attend);
        return new ResponseBean(false, ResponseBean.SUCCESS, null, null);
    }
}
