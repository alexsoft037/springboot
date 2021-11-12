package xin.xiaoer.modules.website.controller;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;
import xin.xiaoer.common.integralConfig.IntegralConfig;
import xin.xiaoer.common.integralConfig.IntegralConfigFactory;
import xin.xiaoer.common.oss.CloudStorageService;
import xin.xiaoer.common.oss.OSSFactory;
import xin.xiaoer.common.utils.PageUtils;
import xin.xiaoer.common.utils.Query;
import xin.xiaoer.common.utils.StringUtility;
import xin.xiaoer.common.utils.YunXinUtil;
import xin.xiaoer.entity.Article;
import xin.xiaoer.entity.File;
import xin.xiaoer.entity.SysUser;
import xin.xiaoer.modules.activity.entity.Activity;
import xin.xiaoer.modules.activity.service.ActivityService;
import xin.xiaoer.modules.like.entity.Like;
import xin.xiaoer.modules.like.service.LikeService;
import xin.xiaoer.modules.mobile.bean.ResponseBean;
import xin.xiaoer.modules.review.entity.Review;
import xin.xiaoer.modules.review.service.ReviewService;
import xin.xiaoer.modules.setting.entity.Integral;
import xin.xiaoer.modules.setting.service.IntegralService;
import xin.xiaoer.modules.spaceshow.entity.ActivityShow;
import xin.xiaoer.modules.spaceshow.entity.SpaceShow;
import xin.xiaoer.modules.spaceshow.service.ActivityShowService;
import xin.xiaoer.modules.spaceshow.service.SpaceShowService;
import xin.xiaoer.service.FileService;
import xin.xiaoer.service.SysUserService;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("website/spaceShow")
@ApiIgnore
public class WebAPISpaceShowController {
    @Autowired
    private SpaceShowService spaceShowService;

    @Autowired
    private FileService fileService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private ActivityShowService activityShowService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private IntegralService integralService;
    //新加
    @Autowired
    private ActivityService activityService;

    //以创建时间排序的朋友圈列表信息
    @RequestMapping(value = "/recentList", method = RequestMethod.POST)
    public ResponseBean recentList(@RequestParam Map<String, Object> params, HttpServletRequest request) {

//        String createBy = request.getParameter("createBy");
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
        Query query = new Query(params);

        CloudStorageService cloudStorageService = OSSFactory.build();

        List<SpaceShow> spaceShows = spaceShowService.getRecentList(query);
        for (SpaceShow spaceShow : spaceShows) {

            Map<String, Object> reviewParams = new HashMap<>();
            reviewParams.put("articleId", spaceShow.getShowId());
//            reviewParams.put("userId", userId);
            reviewParams.put("articleTypeCode", Article.SPACE_SHOW);
            reviewParams.put("limit", 3);
            reviewParams.put("page", 1);
            reviewParams.put("state", 1);
            Query reviewQuery = new Query(reviewParams);
            List<Review> reviewList = reviewService.getList(reviewQuery);
            spaceShow.setReviews(reviewList);

            byte[] contentBytes = Base64.decodeBase64(spaceShow.getContent().getBytes());
            spaceShow.setContent(new String(contentBytes));
            spaceShow.setAvatar(sysUserService.getAvatar(spaceShow.getAvatar()));
            Like like = likeService.getByArticleAndUser("AT0010", spaceShow.getShowId(), Long.parseLong(userId));
            if (like != null) {
                spaceShow.setLikeYn(true);
            } else {
                spaceShow.setLikeYn(false);
            }

            List<File> files = fileService.getByRelationId(spaceShow.getImage());
            List<String> imageFiles = new ArrayList<>();
            for (File file : files) {
                String url = cloudStorageService.generatePresignedUrlByWith(file.getUrl(), "480");
                imageFiles.add(url);
            }
            spaceShow.setImageFiles(imageFiles);

            List<File> vFiles = fileService.getByRelationId(spaceShow.getVideo());
            List<Map<String, String>> videoFiles = new ArrayList<>();
            for (File file : vFiles) {
                Map<String, String> videoMap = new HashMap<>();
                String url = cloudStorageService.generatePresignedUrl(file.getUrl());
                String thumbnail = cloudStorageService.videoThumbnail(file.getUrl(), "600", "400");
                videoMap.put("video", url);
                videoMap.put("thumbnail", thumbnail);
                videoFiles.add(videoMap);
            }
            spaceShow.setVideoFiles(videoFiles);
        }
        int total = spaceShowService.getRecentCount(query);

        PageUtils pageUtil = new PageUtils(spaceShows, total, query.getLimit(), query.getPage());

        return new ResponseBean(false, "success", null, pageUtil);
    }

    //以评论数，like数，分享数排序的朋友圈列表信息
    @RequestMapping(value = "/popularList", method = RequestMethod.POST)
    public ResponseBean popularList(@RequestParam Map<String, Object> params, HttpServletRequest request) {
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
        Query query = new Query(params);

        CloudStorageService cloudStorageService = OSSFactory.build();

        List<SpaceShow> spaceShows = spaceShowService.getPopularList(query);
        for (SpaceShow spaceShow : spaceShows) {

            Map<String, Object> reviewParams = new HashMap<>();
            reviewParams.put("articleId", spaceShow.getShowId());
//            reviewParams.put("userId", userId);
            reviewParams.put("articleTypeCode", Article.SPACE_SHOW);
            reviewParams.put("limit", 3);
            reviewParams.put("page", 1);
            reviewParams.put("state", 1);
            Query reviewQuery = new Query(reviewParams);
            List<Review> reviewList = reviewService.getList(reviewQuery);
            spaceShow.setReviews(reviewList);

            spaceShow.setAvatar(sysUserService.getAvatar(spaceShow.getAvatar()));
            Like like = likeService.getByArticleAndUser("AT0010", spaceShow.getShowId(), Long.parseLong(userId));
            if (like != null) {
                spaceShow.setLikeYn(true);
            } else {
                spaceShow.setLikeYn(false);
            }

            byte[] contentBytes = Base64.decodeBase64(spaceShow.getContent().getBytes());
            spaceShow.setContent(new String(contentBytes));

            List<File> files = fileService.getByRelationId(spaceShow.getImage());
            List<String> imageFiles = new ArrayList<>();
            for (File file : files) {
                String url = cloudStorageService.generatePresignedUrlByWith(file.getUrl(), "480");
                imageFiles.add(url);
            }
            spaceShow.setImageFiles(imageFiles);

            List<File> vFiles = fileService.getByRelationId(spaceShow.getVideo());
            List<Map<String, String>> videoFiles = new ArrayList<>();
            for (File file : vFiles) {
                Map<String, String> videoMap = new HashMap<>();
                String url = cloudStorageService.generatePresignedUrl(file.getUrl());
                String thumbnail = cloudStorageService.videoThumbnail(file.getUrl(), "600", "400");
                videoMap.put("video", url);
                videoMap.put("thumbnail", thumbnail);
                videoFiles.add(videoMap);
            }
            spaceShow.setVideoFiles(videoFiles);
        }
        int total = spaceShowService.getPopularCount(query);

        PageUtils pageUtil = new PageUtils(spaceShows, total, query.getLimit(), query.getPage());

        return new ResponseBean(false, "success", null, pageUtil);
    }

    //可按条件查询活动秀信息，默认以热度创建时间排序
    @RequestMapping(value = "/activityShowList", method = RequestMethod.POST)
    public ResponseBean activityShowList(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        //查询列表数据
        String city = (String)request.getSession().getAttribute("city");
//        String searchTitle = request.getParameter("searchTitle");
//        String searchFrom = request.getParameter("searchFrom");
//        String searchTo = request.getParameter("searchTo");
//        String searchTypeCode = request.getParameter("searchTypeCode");
//        String createBy = request.getParameter("createBy");
//        String activityId = request.getParameter("activityId");
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
        params.put("city",city);
        Query query = new Query(params);

        CloudStorageService cloudStorageService = OSSFactory.build();

        List<SpaceShow> spaceShows = spaceShowService.getActivityShowList(query);
        for (SpaceShow spaceShow : spaceShows) {

            Map<String, Object> reviewParams = new HashMap<>();
            reviewParams.put("articleId", spaceShow.getShowId());
//            reviewParams.put("userId", userId);
            reviewParams.put("articleTypeCode", Article.SPACE_SHOW);
            reviewParams.put("limit", 3);
            reviewParams.put("page", 1);
            reviewParams.put("state", 1);
            Query reviewQuery = new Query(reviewParams);
            List<Review> reviewList = reviewService.getList(reviewQuery);
            spaceShow.setReviews(reviewList);

            spaceShow.setAvatar(sysUserService.getAvatar(spaceShow.getAvatar()));
            byte[] contentBytes = Base64.decodeBase64(spaceShow.getContent().getBytes());
            spaceShow.setContent(new String(contentBytes));

            Like like = likeService.getByArticleAndUser("AT0010", spaceShow.getShowId(), Long.parseLong(userId));
            if (like != null) {
                spaceShow.setLikeYn(true);
            } else {
                spaceShow.setLikeYn(false);
            }

            List<File> files = fileService.getByRelationId(spaceShow.getImage());
            List<String> imageFiles = new ArrayList<>();
            for (File file : files) {
                String url = cloudStorageService.generatePresignedUrlByWith(file.getUrl(), "480");
                imageFiles.add(url);
            }
            spaceShow.setImageFiles(imageFiles);

            List<File> vFiles = fileService.getByRelationId(spaceShow.getVideo());
            List<Map<String, String>> videoFiles = new ArrayList<>();
            for (File file : vFiles) {
                Map<String, String> videoMap = new HashMap<>();
                String url = cloudStorageService.generatePresignedUrl(file.getUrl());
                String thumbnail = cloudStorageService.videoThumbnail(file.getUrl(), "600", "400");
                videoMap.put("video", url);
                videoMap.put("thumbnail", thumbnail);
                videoFiles.add(videoMap);
            }
            spaceShow.setVideoFiles(videoFiles);
        }
        int total = spaceShowService.getActivityShowCount(query);

        PageUtils pageUtil = new PageUtils(spaceShows, total, query.getLimit(), query.getPage());

        return new ResponseBean(false, "success", null, pageUtil);
    }

    //查看活动秀详情信息
    @RequestMapping(value = "/detail/{showId}/{userId}", method = RequestMethod.GET)
    public ResponseBean detail(@PathVariable("showId") Long showId, @PathVariable("userId") Long userId) throws Exception {
        //查询列表数据

        SpaceShow spaceShow = spaceShowService.get(showId);
        if(spaceShow==null){
            return new ResponseBean(false, "false", "找不到对应信息", null);
        }
        if (!spaceShow.getState().equals("1")) {
            return new ResponseBean(false, ResponseBean.FAILED, "空", null);
        }
        spaceShow.setReadCount(spaceShow.getReadCount() + 1);
        spaceShowService.update(spaceShow);

        SysUser sysUser = sysUserService.queryObject(spaceShow.getCreateBy());
        spaceShow.setCreateUser(sysUser.getNickname());
        spaceShow.setAvatar(sysUserService.getAvatar(sysUser.getAvatar()));

        Map<String, Object> reviewParams = new HashMap<>();
        reviewParams.put("articleId", spaceShow.getShowId());
//            reviewParams.put("userId", userId);
        reviewParams.put("articleTypeCode", Article.SPACE_SHOW);
        reviewParams.put("limit", 10);
        reviewParams.put("page", 1);
        reviewParams.put("state", 1);
        Query reviewQuery = new Query(reviewParams);
        List<Review> reviewList = reviewService.getList(reviewQuery);
        spaceShow.setReviews(reviewList);

        CloudStorageService cloudStorageService = OSSFactory.build();
        Like like = likeService.getByArticleAndUser("AT0010", spaceShow.getShowId(), userId);
        if (like != null) {
            spaceShow.setLikeYn(true);
        } else {
            spaceShow.setLikeYn(false);
        }
        byte[] contentBytes = Base64.decodeBase64(spaceShow.getContent().getBytes());
        spaceShow.setContent(new String(contentBytes));
        List<File> files = fileService.getByRelationId(spaceShow.getImage());
        List<String> imageFiles = new ArrayList<>();
        for (File file : files) {
            String url = cloudStorageService.generatePresignedUrlByWith(file.getUrl(), "480");
            imageFiles.add(url);
        }
        spaceShow.setImageFiles(imageFiles);

        List<File> vFiles = fileService.getByRelationId(spaceShow.getVideo());
        List<Map<String, String>> videoFiles = new ArrayList<>();
        for (File file : vFiles) {
            Map<String, String> videoMap = new HashMap<>();
            String url = cloudStorageService.generatePresignedUrl(file.getUrl());
            String thumbnail = cloudStorageService.videoThumbnail(file.getUrl(), "600", "400");
            videoMap.put("video", url);
            videoMap.put("thumbnail", thumbnail);
            videoFiles.add(videoMap);
        }
        spaceShow.setVideoFiles(videoFiles);

        return new ResponseBean(false, "success", null, spaceShow);
    }

    //存储朋友圈，活动秀
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseBean save(HttpServletRequest request, @RequestParam(value = "imageFiles", required = false) MultipartFile[] imageFiles, @RequestParam(value = "videoFiles", required = false) MultipartFile videoFile) throws Exception {
        //查询列表数据

        String showType = request.getParameter("showType");

        String content = request.getParameter("content");
        String userId = request.getParameter("userId");
        String spaceId= request.getParameter("spaceId");
        String activityId=null;


        byte[] contentBytes = Base64.encodeBase64(content.getBytes());

        SpaceShow spaceShow = new SpaceShow();

        spaceShow.setShowTypeCode(showType);
        spaceShow.setContent(new String(contentBytes));
        if(showType.equals("SHT002")){
            String title = request.getParameter("title");
            activityId = request.getParameter("activityId");
            if(StringUtility.isEmpty(activityId)){
                return new ResponseBean(false, ResponseBean.FAILED, "未查询到参与的活动信息", null);
            }
            Activity activity= activityService.get(Integer.parseInt(activityId));
            String address = activity.getLocation();
            String latitude = activity.getLatitude();
            String longitude = activity.getLongitude();
            spaceId = activity.getSpaceId().toString();
            spaceShow.setTitle(title);
            spaceShow.setAddress(address);
            spaceShow.setLatitude(latitude);
            spaceShow.setLongitude(longitude);
        }
        spaceShow.setCreateBy(Long.parseLong(userId));
        spaceShow.setUpdateBy(Long.parseLong(userId));

        String imageId = UUID.randomUUID().toString();
        String videoId = UUID.randomUUID().toString();
        CloudStorageService cloudStorageService = OSSFactory.build();

        if (imageFiles != null && imageFiles.length>0){
            for (MultipartFile multipartFile : imageFiles) {
                String originalFilename = multipartFile.getOriginalFilename();
                Long fileSize = multipartFile.getSize();
                String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

                try {
                    String imageUrl = cloudStorageService.uploadSuffix(multipartFile.getInputStream(), suffix);
//            String preSingedUrl = cloudStorageService.generatePresignedUrl(featuredImageUrl);
                    File imageFile = new File();
                    imageFile.setUploadId(imageId);
                    imageFile.setUrl(imageUrl);
                    imageFile.setFileName(originalFilename);
                    imageFile.setOssYn("Y");
                    imageFile.setFileType("image");
                    imageFile.setFileSize(fileSize.toString());
                    if (StringUtils.isNumeric(spaceId)){
                        imageFile.setSpaceId(Integer.parseInt(spaceId));
                    }
                    fileService.save(imageFile);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (imageFiles.length > 0) {
                spaceShow.setImage(imageId);
            }
        }



        if (videoFile != null && !videoFile.isEmpty()) {
            String originalFilename = videoFile.getOriginalFilename();
            Long fileSize = videoFile.getSize();
            String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            try {
                String videoFileUrl = cloudStorageService.uploadSuffix(videoFile.getInputStream(), suffix);
//                String preSingedUrl = cloudStorageService.generatePresignedUrl(videoFileUrl);
                File vFile = new File();
                vFile.setUploadId(videoId);
                vFile.setUrl(videoFileUrl);
                vFile.setOssYn("Y");
                vFile.setFileName(originalFilename);
                vFile.setFileType("video");
                vFile.setFileSize(fileSize.toString());
                if (StringUtils.isNumeric(spaceId)){
                    vFile.setSpaceId(Integer.parseInt(spaceId));
                }
                fileService.save(vFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
            spaceShow.setVideo(videoId);
        }

        if (StringUtils.isNumeric(spaceId)){
            spaceShow.setSpaceId(Integer.parseInt(spaceId));
        }
        spaceShow.setState("1");
        spaceShowService.save(spaceShow);

        if (activityId != null) {
            ActivityShow activityShow = new ActivityShow();
            activityShow.setActivityId(Long.parseLong(activityId));
            activityShow.setShowId(spaceShow.getShowId());
            activityShowService.save(activityShow);
        }

        Integral integral = new Integral();
        integral.setArticleTypeCode(Article.SPACE_SHOW);
        integral.setArticleId(spaceShow.getShowId());
        integral.setUserId(Long.parseLong(userId));
        integral.setTitle("发布" + spaceShow.getTitle() + "空间秀");

        Map<String, Object> typeParams = new HashMap<>();
        typeParams.put("articleTypeCode", Article.SPACE_SHOW);
        typeParams.put("userId", userId);
        Double weekIntegral = integralService.getThisWeekTotal(typeParams);
        IntegralConfig integralConfig = IntegralConfigFactory.build();
        Double spaceShowIntegral = Integral.SPACESHOW_DEFAULT;

        YunXinUtil yunXinUtil = new YunXinUtil();
        if (weekIntegral == null){
            integral.setIntegral(spaceShowIntegral.toString());
            integralService.save(integral);
            try {
                yunXinUtil.sendSinglePush(integral.getUserId(), "获得积分"+integral.getTitle());
            } catch (Exception e){
            }
        } else if (weekIntegral < integralConfig.getWeekMaxSpaceShowIntegral()){
            Double todayIntegral = integralService.getTodayTotal(typeParams);
            if (todayIntegral == null){
                integral.setIntegral(spaceShowIntegral.toString());
                integralService.save(integral);
                try {
                    yunXinUtil.sendSinglePush(integral.getUserId(), "获得积分"+integral.getTitle());
                } catch (Exception e){
                }
            } else if (todayIntegral < Integral.SPACESHOW_DAY_MAX){
                if (todayIntegral + spaceShowIntegral >= Integral.SPACESHOW_DAY_MAX){
                    spaceShowIntegral = Integral.SPACESHOW_DAY_MAX - todayIntegral;
                }

                if (weekIntegral + spaceShowIntegral >= integralConfig.getWeekMaxSpaceShowIntegral()) {
                    spaceShowIntegral = integralConfig.getWeekMaxSpaceShowIntegral() - weekIntegral;
                }
                integral.setIntegral(spaceShowIntegral.toString());
                integralService.save(integral);
                try {
                    yunXinUtil.sendSinglePush(integral.getUserId(), "获得积分"+integral.getTitle());
                } catch (Exception e){
                }
            }
        }

        return new ResponseBean(false, ResponseBean.SUCCESS, null, null);
    }
}
