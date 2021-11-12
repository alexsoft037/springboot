package xin.xiaoer.modules.mobile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import xin.xiaoer.common.integralConfig.IntegralConfig;
import xin.xiaoer.common.integralConfig.IntegralConfigFactory;
import xin.xiaoer.common.oss.CloudStorageService;
import xin.xiaoer.common.oss.OSSFactory;
import xin.xiaoer.common.utils.UUID;
import xin.xiaoer.common.utils.*;
import xin.xiaoer.entity.Article;
import xin.xiaoer.entity.Commpara;
import xin.xiaoer.entity.File;
import xin.xiaoer.entity.SysUser;
import xin.xiaoer.modules.activity.entity.Activity;
import xin.xiaoer.modules.activity.entity.ActivityAttend;
import xin.xiaoer.modules.activity.entity.ActivitySignNumber;
import xin.xiaoer.modules.activity.entity.ActivityUserSign;
import xin.xiaoer.modules.activity.service.ActivityAttendService;
import xin.xiaoer.modules.activity.service.ActivityService;
import xin.xiaoer.modules.activity.service.ActivitySignNumberService;
import xin.xiaoer.modules.activity.service.ActivityUserSignService;
import xin.xiaoer.modules.like.entity.Like;
import xin.xiaoer.modules.like.service.LikeService;
import xin.xiaoer.modules.mobile.bean.ResponseBean;
import xin.xiaoer.modules.mobile.entity.ActivityFeaturedItem;
import xin.xiaoer.modules.mobile.entity.ActivityListItem;
import xin.xiaoer.modules.review.service.ReviewService;
import xin.xiaoer.modules.setting.entity.Integral;
import xin.xiaoer.modules.setting.service.IntegralService;
import xin.xiaoer.service.CommparaService;
import xin.xiaoer.service.FileService;
import xin.xiaoer.service.SysUserService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("mobile/activity")
@ApiIgnore
public class APIActivityController {
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

    @RequestMapping(value = "/listData")
    public ResponseBean listData(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        //查询列表数据

//        String requestPageCount = request.getParameter("pageCount");
        String pageCount = params.get("pageCount").toString();
        String curPageNum = params.get("curPageNum").toString();
        String activityStatusCode = params.get("activityStatus").toString();
        String spaceId = params.get("spaceId").toString();
        if (StringUtility.isEmpty(spaceId)) {
            params.put("spaceId", 0);
        }
        params.put("limit", pageCount);
        params.put("page", curPageNum);
        params.put("state", "1");
        params.put("activityStatusCode", activityStatusCode);
        Query query = new Query(params);

        CloudStorageService cloudStorageService = OSSFactory.build();
        Map<String, Object> checkParam = new HashMap<>();
        checkParam.put("activityStatusCode", activityStatusCode);
        List<Activity> activities = activityService.getList(checkParam);
        for (Activity activity: activities){
            activityService.checkExpired(activity);
        }
        List<ActivityListItem> activityListItems = activityService.queryListData(query);
        for (ActivityListItem activityListItem : activityListItems) {
//            activityListItem.setAttendYN(false);
            Integer totalUsers = activityAttendService.getCountByActivityId(activityListItem.getActivityId());
            activityListItem.setTotalUsers(totalUsers);
            if (!StringUtility.isEmpty(activityListItem.getFeaturedImage())) {
                activityListItem.setFeaturedImage(cloudStorageService.generatePresignedUrl(activityListItem.getFeaturedImage()));
            }
        }
        int total = activityService.countListData(query);

        PageUtils pageUtil = new PageUtils(activityListItems, total, query.getLimit(), query.getPage());

        return new ResponseBean(false, "success", null, pageUtil);
    }

    @RequestMapping(value = "/detail/{activityId}/{userId}", method = RequestMethod.GET)
    public ResponseBean detail(@PathVariable("activityId") Integer activityId, @PathVariable("userId") Long userId) throws Exception {
        //查询列表数据

        Activity activity = activityService.get(activityId);
        if (!activity.getState().equals("1")) {
            return new ResponseBean(false, ResponseBean.FAILED, "空", null);
        }
        activity = activityService.checkExpired(activity);

        activity.setReadCount(activity.getReadCount() + 1);
        activityService.update(activity);

        SysUser sysUser = sysUserService.queryObject(activity.getCreateBy());

        Integer totalUsers = activityAttendService.getCountByActivityId(activityId);

        ActivityAttend activityAttend = activityAttendService.getByUserIdAndActivityId(activityId, userId);

        Like like = likeService.getByArticleAndUser(Article.ACTIVITY, Long.parseLong(Integer.toString(activityId)), userId);
        if (like == null){
            activity.setLikeYn(false);
        } else {
            activity.setLikeYn(true);
        }

        Integer reviewCount = reviewService.getCountByCodeAndId(Article.ACTIVITY, Long.parseLong(Integer.toString(activityId)));
        activity.setReviewCount(reviewCount);

        activity.setCreateUser(sysUser.getNickname());
//        activity.setContactPhone(sysUser.getPhoneNo());
        activity.setTotalUsers(totalUsers);
        if (activityAttend != null){
            activity.setAttendYN(true);
        } else {
            activity.setAttendYN(false);
        }

        ActivityUserSign activityUserSign = activityUserSignService.getByUserAndActivity(userId, activityId);
        if (activityUserSign == null){
            activity.setSignYn(false);
        } else {
            if (activityUserSign.getState().equals("1")){
                activity.setSignYn(true);
            } else {
                activity.setSignYn(false);
            }
        }
        CloudStorageService cloudStorageService = OSSFactory.build();
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

        return new ResponseBean(false, "success", null, activity);
    }

    @RequestMapping(value = "/featuredItems/{userId}/{spaceId}", method = RequestMethod.GET)
    public ResponseBean featuredItems(@PathVariable("userId") Long userId, @PathVariable("spaceId") String spaceId, HttpServletRequest request) {
        //查询列表数据
        Map<String, Object> params = new HashMap<>();
        if (StringUtility.isEmpty(spaceId)) {
            params.put("spaceId", 0);
        } else {
            params.put("spaceId", spaceId);
        }
        params.put("userId", userId);
        List<ActivityFeaturedItem> featuredItems = activityService.getFeaturedItems(params);
        CloudStorageService cloudStorageService = OSSFactory.build();
        for (ActivityFeaturedItem featuredItem : featuredItems) {
//            featuredItem.setAttendYN(false);
            Activity activity = activityService.checkExpiredByActivityId(featuredItem.getActivityId());
            featuredItem.setActivityStatus(activity.getActivityStatus());
            featuredItem.setActivityStatusCode(activity.getActivityStatusCode());
            Integer totalUsers = activityAttendService.getCountByActivityId(featuredItem.getActivityId());
            featuredItem.setTotalUsers(totalUsers);
            if (!StringUtility.isEmpty(featuredItem.getFeaturedImage())) {
                featuredItem.setFeaturedImage(cloudStorageService.generatePresignedUrl(featuredItem.getFeaturedImage()));
            }
        }

        return new ResponseBean(false, "success", null, featuredItems);
    }

    @RequestMapping(value = "/attendList", method = RequestMethod.POST)
    public ResponseBean attendList(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        //查询列表数据
        String pageCount = params.get("pageCount").toString();
        String curPageNum = params.get("curPageNum").toString();
        String activityId = params.get("activityId").toString();

        Map<String, Object> search = new HashMap<>();
        search.put("activityId", activityId);
        search.put("limit", pageCount);
        search.put("page", curPageNum);
        search.put("state", "1");

        Query query = new Query(search);
        List<ActivityAttend> activityAttendList = activityAttendService.getList(query);
        for (ActivityAttend activityAttend: activityAttendList){
            activityAttend.setAvatar(sysUserService.getAvatar(activityAttend.getAvatar()));
        }

        int total = activityAttendService.getCount(query);

        PageUtils pageUtil = new PageUtils(activityAttendList, total, query.getLimit(), query.getPage());

        return new ResponseBean(false, "success", null, pageUtil);
    }

    @RequestMapping(value = "/applyAttend", method = RequestMethod.POST)
    public ResponseBean saveAttend(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        //查询列表数据
        String activityId = params.get("activityId").toString();
        String userId = params.get("userId").toString();

        ActivityAttend attend = activityAttendService.getByUserIdAndActivityId(Integer.parseInt(activityId), Long.parseLong(userId));
        if (attend != null) {
            return new ResponseBean(false, "success", null, "already_applied","already_applied");
        } else {
            ActivityAttend activityAttend = new ActivityAttend();
            activityAttend.setActivityId(Long.parseLong(activityId));
            activityAttend.setState("1");
            activityAttend.setUserId(Long.parseLong(userId));
            activityAttendService.save(activityAttend);
        }


        return new ResponseBean(false, "success", null, null);
    }

    @RequestMapping(value = "/typeCodes/{spaceId}", method = RequestMethod.GET)
    public ResponseBean typeCodes(HttpServletRequest request) {
        //查询列表数据
        Map<String, Object> params = new HashMap<>();
        params.put("codeName", "activityType");
        List<Commpara> reportTypeCodes = commparaService.getCodeValues(params);

        return new ResponseBean(false, "success", null, reportTypeCodes);
    }

    @RequestMapping(value = "/checkSign", method = RequestMethod.POST)
    public ResponseBean checkSign(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Map<String, Object> result = new HashMap<>();

        String activityId = params.get("activityId").toString();
        String signCode = params.get("signCode").toString();
        String userId = params.get("userId").toString();

        boolean isChecked = false;
        String error = "";

        ActivityAttend activityAttend = activityAttendService.getByUserIdAndActivityId(Integer.parseInt(activityId), Long.parseLong(userId));
        if (activityAttend == null){
            return new ResponseBean(false, ResponseBean.FAILED, "你必须报名!", null);
        }
        ActivityUserSign activityUserSign = activityUserSignService.getByUserAndActivity(Long.parseLong(userId), Integer.parseInt(activityId));
        if (activityUserSign == null){
            activityUserSign = new ActivityUserSign();
        }
        ActivitySignNumber activitySignNumber = activitySignNumberService.get(Integer.parseInt(activityId));
        if (activitySignNumber == null) {
            isChecked = false;
            error = "错误代码";
        } else {
            if (activitySignNumber.getSignNumber().equals(signCode)){
                activityUserSign.setUserId(Long.parseLong(userId));
                activityUserSign.setActivityId(Integer.parseInt(activityId));
                activityUserSign.setState("1");
                if (activityUserSign.getId() == null) {
                    Map<String, Object> userParam = new HashMap<>();
                    userParam.put("articleTypeCode", Article.ACTIVITY);
                    userParam.put("userId", userId);
                    userParam.put("articleId", activityId);

                    List<Integral> integrals = integralService.getList(userParam);
                    if (integrals.size() == 0){
                        userParam.remove("articleId");
                        IntegralConfig integralConfig = IntegralConfigFactory.build();
                        Double weekIntegralTotal = integralService.getThisWeekTotal(userParam);
                        Activity activity = activityService.get(Integer.parseInt(activityId));
                        String integralValue = activity.getIntegral();
                        if (StringUtility.isEmpty(integralValue)){
                            integralValue = Integral.ACTIVITY_DEFAULT.toString();
                        }
                        Integral integral = new Integral();
                        integral.setUserId(Long.parseLong(userId));
                        integral.setArticleId(Long.parseLong(activityId));
                        integral.setArticleTypeCode(Article.ACTIVITY);
                        integral.setTitle("参加" + activity.getTitle() + "活动");
                        YunXinUtil yunXinUtil = new YunXinUtil();
                        if (weekIntegralTotal == null) {
                            integral.setIntegral(integralValue);
                            integralService.save(integral);
                            try {
                                yunXinUtil.sendSinglePush(Long.parseLong(userId), "获得积分"+integral.getTitle());
                            } catch (Exception e) {
                            }
                            result.put("integral", integralValue);
                        } else if (weekIntegralTotal < integralConfig.getWeekMaxActivityIntegral()) {
                            Double integralDefault = Double.parseDouble(integralValue);
                            if ((integralDefault + weekIntegralTotal) >= integralConfig.getWeekMaxActivityIntegral()) {
                                integralDefault = integralConfig.getWeekMaxActivityIntegral() - weekIntegralTotal;
                            }
                            integral.setIntegral(integralDefault.toString());
                            integralService.save(integral);
                            try {
                                yunXinUtil.sendSinglePush(Long.parseLong(userId), "获得积分"+integral.getTitle());
                            } catch (Exception e) {
                            }
                            result.put("integral", integralDefault);
                        }

                    }
                    activityUserSignService.save(activityUserSign);
                }
                isChecked = true;
            } else {
                isChecked = false;
                error = "错误代码";
            }
        }

        result.put("isChecked", isChecked);
        return new ResponseBean(false, "success", error, result);
    }

    @RequestMapping(value = "/checkUserSign", method = RequestMethod.POST)
    public ResponseBean checkUserSign(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Map<String, Object> result = new HashMap<>();

        String activityId = params.get("activityId").toString();
        String userId = params.get("userId").toString();

        boolean isSigned = false;
        String error = "";

        ActivityUserSign activityUserSign = activityUserSignService.getByUserAndActivity(Long.parseLong(userId), Integer.parseInt(activityId));
        if (activityUserSign == null){
            isSigned = false;
        } else {
            if (activityUserSign.getState().equals("1")){
                isSigned = true;
            } else {
                isSigned = false;
            }
        }
        result.put("isSigned", isSigned);
        return new ResponseBean(false, "success", error, result);
    }

    @RequestMapping(value = "/autoSearch", method = RequestMethod.POST)
    public ResponseBean autoSearchActivity(@RequestParam Map<String, Object> params, HttpServletRequest request) throws IOException {

        Double lat = Double.parseDouble(params.get("lat").toString());
        Double lng = Double.parseDouble(params.get("lng").toString());
        String userIdString = params.get("userId").toString();
        Long userId = Long.parseLong(userIdString);

        Activity activity = null;
        Double maxLat = lat + (double)1/300;
        Double minLat = lat - (double)1/300;
        Double maxLng = lng + (double)1/300;
        Double minLng = lng - (double)1/300;
        Map<String, Object> searchParams = new HashMap<>();
        searchParams.put("userId", userId);
        searchParams.put("maxLat", maxLat);
        searchParams.put("minLat", minLat);
        searchParams.put("maxLng", maxLng);
        searchParams.put("minLng", minLng);

        List<Activity> activities = activityService.getByLatLng(searchParams);
        // Sorting
        Collections.sort(activities, new Comparator<Activity>() {
            @Override
            public int compare(Activity activity1, Activity activity2)
            {
                Double distance1 = getDistanceInMeters(Double.parseDouble(activity1.getLatitude()), Double.parseDouble(activity1.getLongitude()),
                            lat, lng);
                Double distance2 = getDistanceInMeters(Double.parseDouble(activity2.getLatitude()), Double.parseDouble(activity2.getLongitude()),
                        lat, lng);
                return  distance1.compareTo(distance2);
            }
        });
        if (activities.size() > 0) {
            Activity activity1 = activities.get(0);
            double distance = getDistanceInMeters(Double.parseDouble(activity1.getLatitude()), Double.parseDouble(activity1.getLongitude()), lat, lng);
            if (distance < 50) {
                activity = activity1;
            }
        }
        if (activity != null) {
            activity = activityService.checkExpired(activity);
            activity.setReadCount(activity.getReadCount() + 1);
            activityService.update(activity);

            Integer activityId = activity.getActivityId();
            SysUser sysUser = sysUserService.queryObject(activity.getCreateBy());

            Integer totalUsers = activityAttendService.getCountByActivityId(activityId);

            ActivityAttend activityAttend = activityAttendService.getByUserIdAndActivityId(activityId, userId);

            Like like = likeService.getByArticleAndUser(Article.ACTIVITY, Long.parseLong(Integer.toString(activityId)), userId);
            if (like == null){
                activity.setLikeYn(false);
            } else {
                activity.setLikeYn(true);
            }

            Integer reviewCount = reviewService.getCountByCodeAndId(Article.ACTIVITY, Long.parseLong(Integer.toString(activityId)));
            activity.setReviewCount(reviewCount);

            activity.setCreateUser(sysUser.getNickname());
//        activity.setContactPhone(sysUser.getPhoneNo());
            activity.setTotalUsers(totalUsers);
            if (activityAttend != null){
                activity.setAttendYN(true);
            } else {
                activity.setAttendYN(false);
            }

            ActivityUserSign activityUserSign = activityUserSignService.getByUserAndActivity(userId, activityId);
            if (activityUserSign == null){
                activity.setSignYn(false);
            } else {
                if (activityUserSign.getState().equals("1")){
                    activity.setSignYn(true);
                } else {
                    activity.setSignYn(false);
                }
            }
            CloudStorageService cloudStorageService = OSSFactory.build();
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
        }
        return new ResponseBean(false,"success", null, activity);
    }


    @RequestMapping(value = "/genSign", method = RequestMethod.GET)
    public ResponseBean genSign(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Map<String, Object> result = new HashMap<>();

        List<Activity> activityList = activityService.getListAll(null);

        for (Activity activity: activityList){
            ActivitySignNumber activitySignNumber1 = activitySignNumberService.get(activity.getActivityId());
            if (activitySignNumber1 == null) {
                ActivitySignNumber activitySignNumber = new ActivitySignNumber();
                activitySignNumber.setActivityId(activity.getActivityId());
                activitySignNumber.setSignNumber(Integer.toString(UUID.getNumber(4)));
                activitySignNumberService.save(activitySignNumber);
            }
        }

        boolean isChecked = true;
        result.put("isChecked", isChecked);
        return new ResponseBean(false, "success", null, result);
    }

    private double getDistanceInMeters(double lat1, double lon1, double lat2, double lon2) {
        double R = 6378.137; // Radius of earth in KM
        double dLat = lat2 * Math.PI / 180 - lat1 * Math.PI / 180;
        double dLon = lon2 * Math.PI / 180 - lon1 * Math.PI / 180;
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(lat1 * Math.PI / 180) * Math.cos(lat2 * Math.PI / 180) *
                        Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = R * c;
        return Math.abs(d * 1000); // meters
    }
}
