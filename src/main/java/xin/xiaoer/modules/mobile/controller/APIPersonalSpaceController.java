package xin.xiaoer.modules.mobile.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.binary.Base64;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;
import xin.xiaoer.common.oss.CloudStorageService;
import xin.xiaoer.common.oss.OSSFactory;
import xin.xiaoer.common.utils.PageUtils;
import xin.xiaoer.common.utils.Query;
import xin.xiaoer.common.utils.StringUtility;
import xin.xiaoer.entity.Article;
import xin.xiaoer.entity.File;
import xin.xiaoer.entity.SysUser;
import xin.xiaoer.modules.activity.service.ActivityAttendService;
import xin.xiaoer.modules.activity.service.ActivityService;
import xin.xiaoer.modules.activityreport.service.ActivityReportService;
import xin.xiaoer.modules.book.service.BookService;
import xin.xiaoer.modules.classroom.service.LiveRoomService;
import xin.xiaoer.modules.classroom.service.SharingLessonService;
import xin.xiaoer.modules.classroom.service.StudyAttendService;
import xin.xiaoer.modules.classroom.service.StudyRoomService;
import xin.xiaoer.modules.donatespace.service.DonateSpaceService;
import xin.xiaoer.modules.favourite.service.FavouriteService;
import xin.xiaoer.modules.like.entity.Like;
import xin.xiaoer.modules.like.service.LikeService;
import xin.xiaoer.modules.mobile.bean.ResponseBean;
import xin.xiaoer.modules.mobile.entity.*;
import xin.xiaoer.modules.review.entity.Review;
import xin.xiaoer.modules.review.service.ReviewService;
import xin.xiaoer.modules.servicealliance.service.ServiceAllianceService;
import xin.xiaoer.modules.spacehaedline.service.SpaceHeadlineService;
import xin.xiaoer.modules.spaceshow.entity.SpaceShow;
import xin.xiaoer.modules.spaceshow.service.SpaceShowService;
import xin.xiaoer.modules.story.entity.SpaceStory;
import xin.xiaoer.modules.story.service.SpaceStoryService;
import xin.xiaoer.service.FileService;
import xin.xiaoer.service.SysUserService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("mobile/personalSpace")
@ApiIgnore
public class APIPersonalSpaceController {
    @Autowired
    private DonateSpaceService donateSpaceService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private ActivityAttendService activityAttendService;

    @Autowired
    private BookService bookService;

    @Autowired
    private FavouriteService favouriteService;

    @Autowired
    private SpaceStoryService spaceStoryService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ServiceAllianceService serviceAllianceService;

    @Autowired
    private ActivityReportService activityReportService;

    @Autowired
    private SharingLessonService sharingLessonService;

    @Autowired
    private StudyRoomService studyRoomService;

    @Autowired
    private SpaceHeadlineService spaceHeadlineService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private LiveRoomService liveRoomService;

    @Autowired
    private StudyAttendService studyAttendService;

    @Autowired
    private SpaceShowService spaceShowService;

    @Autowired
    private FileService fileService;


    @RequestMapping(value = "/donateList", method = RequestMethod.POST)
    public ResponseBean donateList(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        //查询列表数据
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
        if (params.get("limit") == null){
            params.put("limit", 10);
        }
        if (params.get("page") == null){
            params.put("page", 1);
        }
        Query query = new Query(params);

        List<PersonalDonate> personalDonates = donateSpaceService.getPersonalList(query);
        CloudStorageService cloudStorageService = OSSFactory.build();
        for (PersonalDonate personalDonate : personalDonates){
            if (StringUtility.isNotEmpty(personalDonate.getFeaturedImage())){
                personalDonate.setFeaturedImage(cloudStorageService.generatePresignedUrl(personalDonate.getFeaturedImage()));
            }
        }
        int total = donateSpaceService.getPersonalCount(query);

        PageUtils pageUtil = new PageUtils(personalDonates, total, query.getLimit(), query.getPage());

        return new ResponseBean(false,"success", null, pageUtil);
    }

    @RequestMapping(value = "/activityList", method = RequestMethod.POST)
    public ResponseBean activityList(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        //查询列表数据

        String pageCount = params.get("pageCount").toString();
        String curPageNum = params.get("curPageNum").toString();
        String activityStatusCode = params.get("activityStatus").toString();
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
        Map<String, Object> checkParams = new HashMap<>();
        checkParams.put("userId", userId);
        checkParams.put("activityStatus", activityStatusCode);
        List<PersonalActivity> chechPpersonalActivities = activityService.getPersonalList(checkParams);
        for (PersonalActivity personalActivity : chechPpersonalActivities) {
            activityService.checkExpiredByActivityId(personalActivity.getActivityId());
        }
        List<PersonalActivity> personalActivities = activityService.getPersonalList(query);
        for (PersonalActivity personalActivity : personalActivities) {
            Integer totalUsers = activityAttendService.getCountByActivityId(personalActivity.getActivityId());
            personalActivity.setTotalUsers(totalUsers);
            if (activityStatusCode.equals("AVS003")){
                personalActivity.setIntegral(100);
            }
            if (!StringUtility.isEmpty(personalActivity.getFeaturedImage())) {
                personalActivity.setFeaturedImage(cloudStorageService.generatePresignedUrl(personalActivity.getFeaturedImage()));
            }
        }
        int total = activityService.getPersonalCount(query);

        PageUtils pageUtil = new PageUtils(personalActivities, total, query.getLimit(), query.getPage());

        return new ResponseBean(false, "success", null, pageUtil);
    }

    @RequestMapping(value = "/bookList", method = RequestMethod.POST)
    public ResponseBean bookList(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        //查询列表数据

        String pageCount = params.get("pageCount").toString();
        String curPageNum = params.get("curPageNum").toString();
        String userId = params.get("userId").toString();
        params.put("limit", pageCount);
        params.put("page", curPageNum);
        params.put("state", "1");
        Query query = new Query(params);

        CloudStorageService cloudStorageService = OSSFactory.build();
        List<PersonalBook> personalBooks = bookService.getPersonalList(query);
        for (PersonalBook personalBook : personalBooks) {
            if (!StringUtility.isEmpty(personalBook.getCover())) {
                personalBook.setCover(cloudStorageService.generatePresignedUrl(personalBook.getCover()));
            }
            if (!StringUtility.isEmpty(personalBook.getBookFile())) {
                personalBook.setBookFile(cloudStorageService.generatePresignedUrl(personalBook.getBookFile()));
            }
        }
        int total = bookService.getPersonalCount(query);

        PageUtils pageUtil = new PageUtils(personalBooks, total, query.getLimit(), query.getPage());

        return new ResponseBean(false, "success", null, pageUtil);
    }

    @RequestMapping(value = "/favouriteList", method = RequestMethod.POST)
    public ResponseBean favouriteList(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        //查询列表数据

        String pageCount = params.get("pageCount").toString();
        String curPageNum = params.get("curPageNum").toString();
        String userId = params.get("userId").toString();
        params.put("limit", pageCount);
        params.put("page", curPageNum);
        params.put("state", "1");
        Query query = new Query(params);

        CloudStorageService cloudStorageService = OSSFactory.build();
        List<PersonalFavourite> personalFavourites = favouriteService.getPersonalList(query);
        for (PersonalFavourite personalFavourite : personalFavourites) {
            personalFavourite.setContent(Jsoup.parse(personalFavourite.getContent()).text());
            if (!StringUtility.isEmpty(personalFavourite.getFeaturedImage())) {
                personalFavourite.setFeaturedImage(cloudStorageService.generatePresignedUrl(personalFavourite.getFeaturedImage()));
            }
        }
        int total = favouriteService.getPersonalCount(query);

        PageUtils pageUtil = new PageUtils(personalFavourites, total, query.getLimit(), query.getPage());

        return new ResponseBean(false, "success", null, pageUtil);
    }

    @RequestMapping(value = "/storyList", method = RequestMethod.POST)
    public ResponseBean storyList(@RequestParam Map<String, Object> params, HttpServletRequest request) {

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
        params.put("createBy", userId);

        SysUser sysUser = sysUserService.queryObject(Long.parseLong(userId));
        String createUser = "";
        if (sysUser != null){
            createUser = sysUser.getNickname();
        }

        Query query = new Query(params);
        List<SpaceStory> spaceStories = spaceStoryService.getList(query);
        for (SpaceStory spaceStory: spaceStories){
            spaceStory.setCreateUser(createUser);
            if (!StringUtility.isEmpty(spaceStory.getAvatar())){
                spaceStory.setAvatar(sysUserService.getAvatar(spaceStory.getAvatar()));
            }
        }

        int total = spaceStoryService.getCount(query);
        PageUtils pageUtil = new PageUtils(spaceStories, total, query.getLimit(), query.getPage());

        return new ResponseBean(false, "success", null, pageUtil);
    }

    @RequestMapping(value = "/reviewList", method = RequestMethod.POST)
    public ResponseBean reviewList(@RequestParam Map<String, Object> params, HttpServletRequest request) {

        ObjectMapper oMapper = new ObjectMapper();

        String pageCount = params.get("pageCount").toString();
        String curPageNum = params.get("curPageNum").toString();
        String userId = params.get("userId").toString();

        params.put("limit", pageCount);
        params.put("page", curPageNum);
        params.put("state", "1");

        Query query = new Query(params);
        List<Review> reviewList = reviewService.getArticleList(query);
        List<Map<String, Object>> pinglun = new ArrayList<>();
        CloudStorageService cloudStorageService = OSSFactory.build();
        for (Review review: reviewList) {
            if (!StringUtility.isEmpty(review.getAvatar())) {
                review.setAvatar(sysUserService.getAvatar(review.getAvatar()));
            }
            Map<String, Object> item = new HashMap<>();
            Map<String, Object> search = new HashMap<>();
            item.put("articleTypeCode", review.getArticleTypeCode());
            item.put("articleId", review.getArticleId());
            if (review.getArticleTypeCode().equals("AT0001")){
                search.put("itemId", review.getArticleId());
                DSpaceListItem dSpaceListItem = donateSpaceService.getListItemObject(search);
                if (dSpaceListItem.getFeturedImage() != null && !dSpaceListItem.getFeturedImage().equals("")){
                    dSpaceListItem.setFeturedImage(cloudStorageService.generatePresignedUrlByWith(dSpaceListItem.getFeturedImage(), "100"));
                }
                item.put("article", dSpaceListItem);
            } else if (review.getArticleTypeCode().equals("AT0002")) {
                search.put("headlineId",review.getArticleId().intValue());
                HeadlineListItem headlineListItem = spaceHeadlineService.getListItemObject(search);
                if (headlineListItem == null){
                    continue;
                }
                SysUser sysUser = sysUserService.queryObject(headlineListItem.getCreateBy());
                String headLineId = Integer.toString(headlineListItem.getHeadlineId());
                Like like = likeService.getByArticleAndUser(Article.SPACE_HEADLINE, Long.parseLong(headLineId), Long.parseLong(userId));
                if (like == null){
                    headlineListItem.setLikeYn(false);
                } else {
                    headlineListItem.setLikeYn(true);
                }
                headlineListItem.setCreateUser(sysUser.getNickname());
                if (!StringUtility.isEmpty(headlineListItem.getFeaturedImage())) {
                    headlineListItem.setFeaturedImage(cloudStorageService.generatePresignedUrlByWith(headlineListItem.getFeaturedImage(), "100"));
                }
                item.put("article", headlineListItem);
            } else if (review.getArticleTypeCode().equals("AT0003")){
                search.put("reportId",review.getArticleId().intValue());
                search.put("userId",Long.parseLong(userId));
                ActivityReportListItem reportListItem = activityReportService.getListItemObject(search);
                if (reportListItem == null){
                    continue;
                }
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
                    reportListItem.setFeaturedImage(cloudStorageService.generatePresignedUrlByWith(reportListItem.getFeaturedImage(), "100"));
                }
                item.put("article", reportListItem);
            } else if (review.getArticleTypeCode().equals("AT0004")){
                search.put("activityId", review.getArticleId());
                search.put("userId", userId);
                ActivityListItem activityListItem = activityService.getListItemObject(search);
                if (activityListItem == null){
                    continue;
                }
                Integer totalUsers = activityAttendService.getCountByActivityId(activityListItem.getActivityId());
                activityListItem.setTotalUsers(totalUsers);
                if (!StringUtility.isEmpty(activityListItem.getFeaturedImage())) {
                    activityListItem.setFeaturedImage(cloudStorageService.generatePresignedUrlByWith(activityListItem.getFeaturedImage(), "100"));
                }
                item.put("article", activityListItem);
            } else if (review.getArticleTypeCode().equals("AT0005")){
                SpaceStory spaceStory = spaceStoryService.getDetail(review.getArticleId(), Long.parseLong(userId));
                if (spaceStory == null){
                    continue;
                }
                if (!StringUtility.isEmpty(spaceStory.getAvatar())){
                    spaceStory.setAvatar(sysUserService.getAvatar(spaceStory.getAvatar()));
                }
                item.put("article", spaceStory);
            } else if (review.getArticleTypeCode().equals("AT0006")){
                search.put("bookId",review.getArticleId());
                BookListItem bookListItem = bookService.getListItemObject(search);
                if (bookListItem == null){
                    continue;
                }
                if (!StringUtility.isEmpty(bookListItem.getCover())) {
                    bookListItem.setCover(cloudStorageService.generatePresignedUrlByWith(bookListItem.getCover(),"100"));
                }
                item.put("article", bookListItem);
            } else if (review.getArticleTypeCode().equals("AT0007")){
                search.put("liveId",review.getArticleId().intValue());
                LiveRoomListItem liveRoomListItem = liveRoomService.getListItemObject(search);
                if (liveRoomListItem == null){
                    continue;
                }
                liveRoomListItem.setAvatar(sysUserService.getAvatar(liveRoomListItem.getAvatar()));
                if (StringUtility.isNotEmpty(liveRoomListItem.getFeaturedImage())){
                    liveRoomListItem.setFeaturedImage(cloudStorageService.generatePresignedUrlByWith(liveRoomListItem.getFeaturedImage(), "100"));
                }
                item.put("article", liveRoomListItem);
            } else if (review.getArticleTypeCode().equals("AT0008")){
                search.put("lessonId",review.getArticleId().intValue());
                VideoLessonListItem videoLessonListItem = sharingLessonService.getListItemObject(search);
                if (videoLessonListItem == null){
                    continue;
                }
                SysUser sysUser = sysUserService.queryObject(videoLessonListItem.getCreateBy());
                if (sysUser != null) {
                    videoLessonListItem.setCreateUser(sysUser.getNickname());
                }
                videoLessonListItem.setFeaturedImage(cloudStorageService.generatePresignedUrlByWith(videoLessonListItem.getFeaturedImage(), "100"));
                item.put("article", videoLessonListItem);
            } else if (review.getArticleTypeCode().equals("AT0009")){
                StudyRoomListItem studyRoomListItem = studyRoomService.getListItemOjbect(review.getArticleId().intValue());
                if (studyRoomListItem == null){
                    continue;
                }
                Integer totalTotalAttends = studyAttendService.getCountByRoomId(studyRoomListItem.getRoomId());
                studyRoomListItem.setTotalAttends(totalTotalAttends);
                if (!StringUtility.isEmpty(studyRoomListItem.getFeaturedImage())) {
                    studyRoomListItem.setFeaturedImage(cloudStorageService.generatePresignedUrlByWith(studyRoomListItem.getFeaturedImage(), "100"));
                }
                item.put("article", studyRoomListItem);
            } else if (review.getArticleTypeCode().equals(Article.SPACE_SHOW)){
                SpaceShow spaceShow = spaceShowService.get(review.getArticleId());
                if (spaceShow == null){
                    continue;
                }
                if (!spaceShow.getState().equals("1")) {
                    return new ResponseBean(false, ResponseBean.FAILED, "空", null);
                }
                spaceShow.setReadCount(spaceShow.getReadCount() + 1);
                spaceShowService.update(spaceShow);

                SysUser sysUser = sysUserService.queryObject(spaceShow.getCreateBy());
                spaceShow.setCreateUser(sysUser.getNickname());
                spaceShow.setAvatar(sysUserService.getAvatar(sysUser.getAvatar()));

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
                    String url = cloudStorageService.generatePresignedUrlByWith(file.getUrl(), "100");
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
                item.put("article", spaceShow);
            }
            pinglun.add(item);
        }

        int total = reviewService.getArticleCount(query);
        PageUtils pageUtil = new PageUtils(pinglun, total, query.getLimit(), query.getPage());

        return new ResponseBean(false, "success", null, pageUtil);
    }
}
