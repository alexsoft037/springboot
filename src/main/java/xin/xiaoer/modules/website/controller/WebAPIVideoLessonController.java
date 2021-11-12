package xin.xiaoer.modules.website.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xin.xiaoer.common.integralConfig.IntegralConfig;
import xin.xiaoer.common.integralConfig.IntegralConfigFactory;
import xin.xiaoer.common.oss.CloudStorageService;
import xin.xiaoer.common.oss.OSSFactory;
import xin.xiaoer.common.utils.*;
import xin.xiaoer.entity.*;
import xin.xiaoer.modules.classroom.entity.SharingLesson;
import xin.xiaoer.modules.classroom.entity.UserLesson;
import xin.xiaoer.modules.classroom.service.SharingLessonService;
import xin.xiaoer.modules.classroom.service.UserLessonService;
import xin.xiaoer.modules.like.entity.Like;
import xin.xiaoer.modules.like.service.LikeService;
import xin.xiaoer.modules.mobile.bean.ResponseBean;
import xin.xiaoer.modules.mobile.entity.VideoLessonListItem;
import xin.xiaoer.modules.review.service.ReviewService;
import xin.xiaoer.modules.setting.entity.Integral;
import xin.xiaoer.modules.setting.service.IntegralService;
import xin.xiaoer.modules.website.entity.VideoBarrage;
import xin.xiaoer.modules.website.service.VideoBarrageService;
import xin.xiaoer.service.CategoryService;
import xin.xiaoer.service.FileService;
import xin.xiaoer.service.SysUserService;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.UUID;

@RestController
@RequestMapping("website/videoLesson")
public class WebAPIVideoLessonController {

    @Autowired
    private SharingLessonService sharingLessonService;

    @Autowired
    private FileService fileService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserLessonService userLessonService;

    @Autowired
    private IntegralService integralService;

    @Autowired
    private VideoBarrageService videoBarrageService;

    //条件查询课程
    @RequestMapping(value="/listData",method = RequestMethod.POST)
    public ResponseBean listData(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        //查询列表数据

//        String requestPageCount = request.getParameter("pageCount");
        String pageCount = params.get("pageCount").toString();
        String curPageNum = params.get("curPageNum").toString();
        params.put("limit", pageCount);
        params.put("page", curPageNum);
        params.put("state", "1");
        Query query = new Query(params);

        CloudStorageService cloudStorageService = OSSFactory.build();
        List<VideoLessonListItem> sharingLessons = sharingLessonService.getApiListData(query);
        for (VideoLessonListItem sharingLesson : sharingLessons) {
//            SysUser sysUser = sysUserService.queryObject(sharingLesson.getCreateBy());
//            if (sysUser != null) {
//                sharingLesson.setCreateUser(sysUser.getNickname());
//            }
            sharingLesson.setAvatar(cloudStorageService.generatePresignedUrl(sharingLesson.getAvatar()));
            sharingLesson.setFeaturedImage(cloudStorageService.generatePresignedUrlWithResize(sharingLesson.getFeaturedImage(), "400", "200"));
        }

        int total = sharingLessonService.getApiCount(query);

        PageUtils pageUtil = new PageUtils(sharingLessons, total, query.getLimit(), query.getPage());

        return new ResponseBean(false, "success", null, pageUtil);
    }

    //以点击数播放数点赞数总数来推荐
    @RequestMapping(value="/featuredList/{spaceId}", method = RequestMethod.POST)
    public ResponseBean featuredList(@PathVariable("spaceId") String spaceId, @RequestParam Map<String, Object> params, HttpServletRequest request) {

        //新加
        if (params.get("limit") == null){
            params.put("limit", 3);
        }

        //查询列表数据
        CloudStorageService cloudStorageService = OSSFactory.build();
        List<VideoLessonListItem> sharingLessons = sharingLessonService.getFeaturedList(params);
        for (VideoLessonListItem sharingLesson : sharingLessons) {
            sharingLesson.setAvatar(cloudStorageService.generatePresignedUrl(sharingLesson.getAvatar()));
            sharingLesson.setFeaturedImage(cloudStorageService.generatePresignedUrlWithResize(sharingLesson.getFeaturedImage(), "200", "260"));
        }

        return new ResponseBean(false, "success", null, sharingLessons);
    }

    //视频的播放页
    @RequestMapping(value="/detail/{lessonId}/{userId}", method = RequestMethod.GET)
    public ResponseBean detail(@PathVariable("lessonId") Integer lessonId, @PathVariable("userId") Long userId) {
        //查询列表数据

        SharingLesson sharingLesson = sharingLessonService.get(lessonId);
        if (!sharingLesson.getState().equals("1")) {
            return new ResponseBean(false, ResponseBean.FAILED, "空", null);
        }
        sharingLesson.setReadCount(sharingLesson.getReadCount() + 1);
        sharingLessonService.update(sharingLesson);

        CloudStorageService cloudStorageService = OSSFactory.build();
        List<File> files = fileService.getByRelationId(sharingLesson.getFeaturedImage());
        SysUser sysUser = sysUserService.queryObject(sharingLesson.getCreateBy());
        if (sysUser != null) {
            sharingLesson.setCreateUser(sysUser.getNickname());
            sharingLesson.setAvatar(sysUserService.getAvatar(sysUser.getAvatar()));
        }
        if (files.size() > 0) {
            if (!StringUtility.isEmpty(files.get(0).getUrl())) {
                sharingLesson.setFeaturedImage(cloudStorageService.generatePresignedUrl(files.get(0).getUrl()));
            } else {
                sharingLesson.setFeaturedImage("");
            }
        } else {
            sharingLesson.setFeaturedImage("");
        }

        List<String> videoFiles = new ArrayList<>();

        List<File> videoFileList = fileService.getByRelationId(sharingLesson.getVideoFile());
        int count = 0;
        sharingLesson.setVideoFile("");
        for (File file : videoFileList) {
            String videoUrl = cloudStorageService.generatePresignedUrl(file.getUrl());
            if (count == 0) {
                sharingLesson.setVideoFile(videoUrl);
            }
            videoFiles.add(videoUrl);
        }
        sharingLesson.setVideoFiles(videoFiles);

        int likeCount = likeService.getCountByCodeAndId("AT0008", sharingLesson.getLessonId().longValue());
        sharingLesson.setLikeCount(likeCount);
        int reviewCount = reviewService.getCountByCodeAndId("AT0008", sharingLesson.getLessonId().longValue());
        sharingLesson.setReviewCount(reviewCount);

        Like like = likeService.getByArticleAndUser(Article.SHARE_LESSON, lessonId.longValue(), userId);
        if (like == null) {
            sharingLesson.setLikeYn(false);
        } else {
            sharingLesson.setLikeYn(true);
        }

        return new ResponseBean(false, "success", null, sharingLesson);
    }

    //上传视屏
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public ResponseBean save(HttpServletRequest request, @RequestParam(value = "featuredImage", required = false) MultipartFile featuredImage, @RequestParam("videoFiles") MultipartFile[] videoFiles) throws Exception {
        //查询列表数据

        String categoryCode = request.getParameter("categoryCode");
        String title = request.getParameter("title");
        String introduction = request.getParameter("introduction");
        String userId = request.getParameter("userId");

        SharingLesson sharingLesson = new SharingLesson();

        sharingLesson.setCategoryCode(categoryCode);
        sharingLesson.setTitle(title);
        sharingLesson.setIntroduction(introduction);
        sharingLesson.setCreateBy(Long.parseLong(userId));
        sharingLesson.setUpdateBy(Long.parseLong(userId));

        String featuredImageId = UUID.randomUUID().toString();
        String videoFileId = UUID.randomUUID().toString();

        CloudStorageService cloudStorageService = OSSFactory.build();
        Long fileSize = 0L;
        String suffix = "";
        String fileOriginalFilename = "";

        if (featuredImage != null){
            fileOriginalFilename = featuredImage.getOriginalFilename();
            fileSize = featuredImage.getSize();
            suffix = fileOriginalFilename.substring(fileOriginalFilename.lastIndexOf("."));


            try {
                String featuredImageUrl = cloudStorageService.uploadSuffix(featuredImage.getInputStream(), suffix);
//            String preSingedUrl = cloudStorageService.generatePresignedUrl(featuredImageUrl);
                File imageFile = new File();
                imageFile.setUploadId(featuredImageId);
                imageFile.setUrl(featuredImageUrl);
                imageFile.setFileName(fileOriginalFilename);
                imageFile.setOssYn("Y");
                imageFile.setFileType("image");
                imageFile.setFileSize(fileSize.toString());
                fileService.save(imageFile);
                sharingLesson.setFeaturedImage(featuredImageId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        for (MultipartFile multipartFile : videoFiles) {
            fileOriginalFilename = multipartFile.getOriginalFilename();
            fileSize = multipartFile.getSize();
            suffix = fileOriginalFilename.substring(fileOriginalFilename.lastIndexOf("."));
            try {
                String videoFileUrl = cloudStorageService.uploadSuffix(multipartFile.getInputStream(), suffix);
//                String preSingedUrl = cloudStorageService.generatePresignedUrl(videoFileUrl);
                File videoFile = new File();
                videoFile.setUploadId(videoFileId);
                videoFile.setUrl(videoFileUrl);
                videoFile.setOssYn("Y");
                videoFile.setFileName(fileOriginalFilename);
                videoFile.setFileType("video");
                videoFile.setFileSize(fileSize.toString());
                fileService.save(videoFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        sharingLesson.setVideoFile(videoFileId);
        sharingLesson.setState("0");
        sharingLessonService.save(sharingLesson);

        IntegralConfig integralConfig = IntegralConfigFactory.build();

        Integral integral = new Integral();
        integral.setArticleTypeCode(Article.SHARE_LESSON);
        integral.setUserId(Long.parseLong(userId));
        integral.setArticleId(sharingLesson.getLessonId().longValue());
        integral.setTitle("视频上传" + sharingLesson.getTitle());
        Double integralValue = integralConfig.getUploadVideoIntegral();
        integral.setIntegral(integralValue.toString());
        integralService.save(integral);
        YunXinUtil yunXinUtil = new YunXinUtil();
        try {
            yunXinUtil.sendSinglePush(integral.getUserId(), "获得积分"+integral.getTitle());
        } catch (Exception e) {
        }

        Map<String, Object> result = new HashMap<>();
        result.put("integral", integralValue);

        return new ResponseBean(false, "success", null, null);
    }

    //返回类别信息
    @RequestMapping(value="/categories/{upperCode}", method = RequestMethod.GET)
    public ResponseBean typeCodes(@PathVariable String upperCode,HttpServletRequest request) {
        //查询列表数据
        Map<String, Object> params = new HashMap<>();

        params.put("upperCode", upperCode);
        params.put("state", "1");
        List<Category> categories = categoryService.getList(params);
        return new ResponseBean(false, "success", null, categories);
    }

    //记录点击和加积分,?
    @RequestMapping("/playCount/{lessonId}/{userId}")
    public ResponseBean playCount(@PathVariable("lessonId") Integer lessonId, @PathVariable("userId") Long userId) {
        SharingLesson sharingLesson = sharingLessonService.get(lessonId);
        if (!sharingLesson.getState().equals("1")) {
            return new ResponseBean(false, ResponseBean.FAILED, "空", null);
        }
        sharingLesson.setPlayCount(sharingLesson.getPlayCount() + 1);
        sharingLessonService.update(sharingLesson);

        UserLesson userLesson = userLessonService.getByUserAndLesson(lessonId, userId);
        if (userLesson == null) {
            userLesson = new UserLesson();
        }
        userLesson.setReadCount(userLesson.getReadCount() + 1);
        if (userLesson.getId() == null) {
            userLesson.setUserId(userId);
            userLesson.setLessonId(lessonId);
            userLessonService.save(userLesson);
        } else {
            userLessonService.update(userLesson);
        }

        Integral integral = new Integral();
        integral.setUserId(sharingLesson.getCreateBy());
        integral.setArticleId(lessonId.longValue());
        integral.setArticleTypeCode(Article.SHARE_READ);
        integral.setIntegral("0.01");
        integral.setTitle(null);
        integralService.save(integral);

        return new ResponseBean(false, "success", null, sharingLesson.getPlayCount());
    }

    //相关推荐
    @RequestMapping(value = "/suggestion",method = RequestMethod.POST)
    public ResponseBean suggestion(@RequestParam Map<String, Object> params){
        //新加
        if (params.get("limit") == null){
            params.put("limit", "7");
        }
        int num=Integer.parseInt((String)params.get("limit"));
        int count=sharingLessonService.getCount(params);
        //查询列表数据
        CloudStorageService cloudStorageService = OSSFactory.build();
        List<SharingLesson> sharingLessons=sharingLessonService.getList(params);
        if(count!=0){
            if(count>num){
                List<SharingLesson> sharingLessonsTemp=sharingLessonService.getList(params);
                int [] rand= RandomUtil.randomArray(1,count,num);
                sharingLessons.clear();
                for(int i=0;i<rand.length;i++){
                    SharingLesson sharingLesson=sharingLessonsTemp.get(rand[i]-1);
                    sharingLessons.add(sharingLesson);
                }
            }
            for (SharingLesson sharingLesson : sharingLessons) {
                sharingLesson.setAvatar(cloudStorageService.generatePresignedUrl(sharingLesson.getAvatar()));
                sharingLesson.setFeaturedImage(cloudStorageService.generatePresignedUrlWithResize(sharingLesson.getFeaturedImage(), "200", "260"));
            }
        }


        return new ResponseBean(false, "success", null, sharingLessons);
    }

    /**
     * @return xin.xiaoer.modules.mobile.bean.ResponseBean
     * @Description TODO 保存视频弹幕
     * @Param [params]
     * @Author XiaoDong
     **/
    @PostMapping("/saveBarrage")
    public ResponseBean saveBarrage(@RequestParam Map<String,Object> params){
        String json = FastJsonUtil.toJsonString(params);
        VideoBarrage videoBarrage = FastJsonUtil.toObject(json, VideoBarrage.class);
        if (videoBarrage == null || videoBarrage.getUserId() == null || videoBarrage.getVideoId() == null || videoBarrage.getBarrageContent() == null){
            return new ResponseBean(false,ResponseBean.FAILED,null,"参数异常",null);
        }
        //不知道什么参数临时判断
        if (videoBarrage.getBarrageStyle() == null || StringUtils.isBlank(videoBarrage.getBarrageStyle())){
            videoBarrage.setBarrageStyle("DIYgod");
        }
        if (videoBarrage.getBarrageId() == null){
            videoBarrage.setBarrageId(111111);
        }
        if (videoBarrage.getBarrageState() == null){
            videoBarrage.setBarrageState(0);
        }
        if (videoBarrage.getBarrageTime() == null){
            videoBarrage.setBarrageTime(0.0);
        }
        videoBarrage.setCreateAt(null);

        videoBarrageService.save(videoBarrage);
<<<<<<< HEAD
        System.out.println(videoBarrage.getId());
=======
>>>>>>> bffa852aa66f0af94d501daf5bebe1f0cf0d7df6
        return new ResponseBean(false,ResponseBean.SUCCESS,null,videoBarrage);
    }

    /**
     * @return xin.xiaoer.modules.mobile.bean.ResponseBean
     * @Description TODO 获取视频弹幕列表
     * @Param [videoId]
     * @Author XiaoDong
     **/
    @GetMapping("/getVideoBarrageList/{videoId}")
    public ResponseBean getVideoBarrageList(@PathVariable("videoId") Integer videoId){
        List<VideoBarrage> barrages = videoBarrageService.getByVideoId(videoId);
<<<<<<< HEAD
        return new ResponseBean(false,ResponseBean.SUCCESS,null,barrages);
=======
        if (barrages == null){
            return new ResponseBean(false,ResponseBean.SUCCESS,null,null);
        }
        List list = new ArrayList();
        for (VideoBarrage barrage : barrages) {
            ArrayList<Object> arr = new ArrayList<>();
            arr.add(barrage.getBarrageTime());
            arr.add(barrage.getBarrageState());
            arr.add(barrage.getBarrageId());
            arr.add(barrage.getBarrageStyle());
            arr.add(barrage.getBarrageContent());
            list.add(arr);
        }
        return new ResponseBean(false,ResponseBean.SUCCESS,null,list);
>>>>>>> bffa852aa66f0af94d501daf5bebe1f0cf0d7df6
    }


}
