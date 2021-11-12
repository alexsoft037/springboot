package xin.xiaoer.modules.mobile.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;
import org.springframework.web.bind.annotation.*;
import xin.xiaoer.common.integralConfig.IntegralConfig;
import xin.xiaoer.common.integralConfig.IntegralConfigFactory;
import xin.xiaoer.common.oss.CloudStorageService;
import xin.xiaoer.common.oss.OSSFactory;
import xin.xiaoer.common.utils.*;
import xin.xiaoer.entity.Article;
import xin.xiaoer.entity.Commpara;
import xin.xiaoer.entity.File;
import xin.xiaoer.entity.SysUser;
import xin.xiaoer.modules.activityreport.entity.ActivityReport;
import xin.xiaoer.modules.activityreport.service.ActivityReportService;
import xin.xiaoer.modules.favourite.entity.Favourite;
import xin.xiaoer.modules.favourite.service.FavouriteService;
import xin.xiaoer.modules.like.entity.Like;
import xin.xiaoer.modules.like.service.LikeService;
import xin.xiaoer.modules.mobile.bean.ResponseBean;
import xin.xiaoer.modules.mobile.entity.ActivityReportListItem;
import xin.xiaoer.modules.review.service.ReviewService;
import xin.xiaoer.modules.setting.entity.Integral;
import xin.xiaoer.modules.setting.service.IntegralService;
import xin.xiaoer.service.CommparaService;
import xin.xiaoer.service.FileService;
import xin.xiaoer.service.SysUserService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("mobile/activityReport")
@ApiIgnore
public class APIActivityReportController {
    @Autowired
    private ActivityReportService activityReportService;

    @Autowired
    private FileService fileService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private CommparaService commparaService;

    @Autowired
    private FavouriteService favouriteService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private IntegralService integralService;

    @RequestMapping(value = "/listData", method = RequestMethod.POST)
    public ResponseBean listData(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        //查询列表数据

//        String requestPageCount = request.getParameter("pageCount");
        String pageCount = params.get("pageCount").toString();
        String curPageNum = params.get("curPageNum").toString();
        String userId = params.get("userId").toString();
        String spaceId = params.get("spaceId").toString();
        if (StringUtility.isEmpty(spaceId)) {
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
            Like like = likeService.getByArticleAndUser(Article.ACTIVITY_REPORT, Long.parseLong(reportId), Long.parseLong(userId));
            if (like == null){
                reportListItem.setLikeYn(false);
            } else {
                reportListItem.setLikeYn(true);
            }
            SysUser sysUser = sysUserService.queryObject(reportListItem.getCreateBy());
            if (sysUser != null){
                reportListItem.setCreateUser(sysUser.getNickname());
                if (sysUser.getUserClassCode() != null){
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

        return new ResponseBean(false, "success", null, pageUtil);
    }

    @RequestMapping(value = "/detail/{reportId}/{userId}", method = RequestMethod.GET)
    public ResponseBean detail(@PathVariable("reportId") Integer reportId, @PathVariable("userId") Long userId) {
        //查询列表数据

        ActivityReport activityReport = activityReportService.get(reportId);
        if (!activityReport.getState().equals("1")) {
            return new ResponseBean(false, ResponseBean.FAILED, "空", null);
        }
        Like like = likeService.getByArticleAndUser(Article.ACTIVITY_REPORT, Long.parseLong(Integer.toString(reportId)), userId);
        if (like == null){
            activityReport.setLikeYn(false);
        } else {
            activityReport.setLikeYn(true);
        }
        activityReport.setReadCount(activityReport.getReadCount() + 1);
        activityReportService.update(activityReport);
        Integer reviewCount = reviewService.getCountByCodeAndId(Article.ACTIVITY_REPORT, Long.parseLong(Integer.toString(reportId)));
        activityReport.setReviewCount(reviewCount);
        Favourite favourite = favouriteService.getByArticleAndUser("AT0003", activityReport.getReportId().longValue(), userId);
        if (favourite != null) {
            activityReport.setFavouriteYn(true);
        } else {
            activityReport.setFavouriteYn(false);
        }

        CloudStorageService cloudStorageService = OSSFactory.build();
        List<File> files = fileService.getByRelationId(activityReport.getFeaturedImage());
        SysUser sysUser = sysUserService.queryObject(activityReport.getCreateBy());
        if (sysUser != null){
            if (StringUtility.isEmpty(sysUser.getNickname())){
                activityReport.setCreateUser(activityReport.getAuthorName());
            } else {
                activityReport.setCreateUser(sysUser.getNickname());
            }
            if (sysUser.getUserClassCode() != null){
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

        return new ResponseBean(false, "success", null, activityReport);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseBean save(HttpServletRequest request) throws Exception {
        //查询列表数据

        String featuredImage = request.getParameter("featuredImage");
        String reportId = request.getParameter("reportId");
        String reportTypeCode = request.getParameter("reportTypeCode");
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        content = content.replace("<img src=\"#", "<img src=\"data:");
        String userId = request.getParameter("userId");
        String spaceId = request.getParameter("spaceId");


        ActivityReport activityReport = new ActivityReport();
        if (!StringUtility.isEmpty(reportId)){
            activityReport.setReportId(Integer.parseInt(reportId));
        }

        activityReport.setReportTypeCode(reportTypeCode);
        activityReport.setTitle(title);
        activityReport.setContent(content);

        String photoFile = request.getParameter("featuredImage1");
        String url = FileUpload.fileUpLoadImageContent(photoFile, 320, 320);

        if (StringUtility.isEmpty(featuredImage)) {
            featuredImage = UUID.randomUUID().toString();
        }

        File imageFile = new File();
        imageFile.setUploadId(featuredImage);
        imageFile.setUrl(url);
        imageFile.setOssYn("Y");
        imageFile.setFileType("image");
        if (StringUtils.isNumeric(spaceId)){
            imageFile.setSpaceId(Integer.parseInt(spaceId));
            activityReport.setSpaceId(Integer.parseInt(spaceId));
        }
        fileService.save(imageFile);

        activityReport.setState("1");

        activityReport.setFeaturedImage(featuredImage);
        if (activityReport.getReportId() != null){
            if (!StringUtility.isEmpty(userId)){
                activityReport.setUpdateBy(Long.parseLong(userId));
            }

            activityReportService.update(activityReport);
        } else {
            if (!StringUtility.isEmpty(userId)){
                activityReport.setUpdateBy(Long.parseLong(userId));
                activityReport.setCreateBy(Long.parseLong(userId));
            }

            activityReportService.save(activityReport);
        }

        Map<String, Object> userParam = new HashMap<>();
        userParam.put("articleTypeCode", Article.ACTIVITY_REPORT);
        userParam.put("userId", userId);
        Double weekIntegralTotal = integralService.getThisWeekTotal(userParam);
        IntegralConfig config = IntegralConfigFactory.build();
        Double integralValue = config.getReporterPostIntegral();

        Integral integral = new Integral();
        integral.setArticleTypeCode(Article.ACTIVITY_REPORT);
        integral.setUserId(Long.parseLong(userId));
        integral.setArticleId(activityReport.getReportId().longValue());
        integral.setTitle("发布" + activityReport.getTitle() + "报道");

        YunXinUtil yunXinUtil = new YunXinUtil();
        if (weekIntegralTotal == null){
            integral.setIntegral(integralValue.toString());
            integralService.save(integral);
            try {
                yunXinUtil.sendSinglePush(integral.getUserId(), "获得积分"+integral.getTitle());
            } catch (Exception e) {
            }
        } else if (weekIntegralTotal < config.getWeekMaxReporterIntegral()){
            if (weekIntegralTotal + integralValue >= config.getWeekMaxReporterIntegral()){
                integralValue = config.getWeekMaxReporterIntegral() - weekIntegralTotal;
            }
            integral.setIntegral(integralValue.toString());
            integralService.save(integral);
            try {
                yunXinUtil.sendSinglePush(integral.getUserId(), "获得积分"+integral.getTitle());
            } catch (Exception e) {
            }
        } else {
            integralValue = 0.0;
        }

        Map<String, Object> result = new HashMap<>();
        result.put("integral", integralValue);

        return new ResponseBean(false, "success", null, result);
    }

    @RequestMapping(value = "/typeCodes/{spaceId}", method = RequestMethod.GET)
    public ResponseBean typeCodes(HttpServletRequest request) {
        //查询列表数据
        Map<String, Object> params = new HashMap<>();
        params.put("codeName", "reportType");
        List<Commpara> reportTypeCodes = commparaService.getCodeValues(params);

        return new ResponseBean(false, "success", null, reportTypeCodes);
    }

    @RequestMapping(value = "/userReportList", method = RequestMethod.POST)
    public ResponseBean userReportList(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        //查询列表数据

//        String requestPageCount = request.getParameter("pageCount");
        String pageCount = params.get("pageCount").toString();
        String curPageNum = params.get("curPageNum").toString();
        String userId = params.get("userId").toString();
        params.put("userId", userId);
        params.put("limit", pageCount);
        params.put("page", curPageNum);
        params.put("state", "1");
        Query query = new Query(params);
        CloudStorageService cloudStorageService = OSSFactory.build();

        List<ActivityReportListItem> reportListItems = activityReportService.queryListData(query);
        for (ActivityReportListItem reportListItem : reportListItems) {
            SysUser sysUser = sysUserService.queryObject(reportListItem.getCreateBy());
            if (sysUser != null){
                reportListItem.setCreateUser(sysUser.getNickname());
                if (sysUser.getUserClassCode() != null){
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

        return new ResponseBean(false, "success", null, pageUtil);
    }
}
