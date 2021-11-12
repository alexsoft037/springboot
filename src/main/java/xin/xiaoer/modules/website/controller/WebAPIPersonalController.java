package xin.xiaoer.modules.website.controller;

import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xin.xiaoer.common.oss.CloudStorageService;
import xin.xiaoer.common.oss.OSSFactory;
import xin.xiaoer.common.utils.*;
import xin.xiaoer.entity.File;
import xin.xiaoer.entity.SysUser;
import xin.xiaoer.entity.UserLevel;
import xin.xiaoer.modules.activity.service.ActivityService;
import xin.xiaoer.modules.book.service.BookService;
import xin.xiaoer.modules.chat.entity.ChatUser;
import xin.xiaoer.modules.chat.service.ChatUserService;
import xin.xiaoer.modules.classroom.service.SharingLessonService;
import xin.xiaoer.modules.classroom.service.StudyRoomService;
import xin.xiaoer.modules.classroom.service.UserLessonService;
import xin.xiaoer.modules.donatespace.service.DonateSpaceService;
import xin.xiaoer.modules.donatespace.service.DonateUserService;
import xin.xiaoer.modules.guoshan.service.GuoshanUserService;
import xin.xiaoer.modules.mobile.bean.ResponseBean;
import xin.xiaoer.modules.mobile.entity.PersonalBook;
import xin.xiaoer.modules.setting.entity.Integral;
import xin.xiaoer.modules.setting.service.IntegralService;
import xin.xiaoer.modules.spaceshow.service.SpaceShowService;
import xin.xiaoer.modules.story.service.SpaceStoryService;
import xin.xiaoer.modules.website.entity.WebAccount;
import xin.xiaoer.service.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 * 
 * 
 * @author Casey
 *
 */
@RestController
@RequestMapping("/website/personal")
public class WebAPIPersonalController {
	@Autowired
	private SpaceNewsService spaceNewsService;
	@Autowired
	private DonateSpaceService donateSpaceService;
	@Autowired
	private ActivityService activityService;
	@Autowired
	private StudyRoomService studyRoomService;
	@Autowired
	private UserLessonService userLessonService;
    @Autowired
	private SpaceShowService spaceShowService;
    @Autowired
    private SpaceStoryService spaceStoryService;
    @Autowired
    private SharingLessonService sharingLessonService;
    @Autowired
    private GuoshanUserService guoshanUserService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private IntegralService integralService;
    @Autowired
    private FileService fileService;
    @Autowired
    private ChatUserService chatUserService;
    @Autowired
    private DonateUserService donateUserService;
    @Autowired
    private SysConfigService sysConfigService;
    @Autowired
    private BookService bookService;
    @Autowired
    private AreaService areaService;
	//我评论过的新闻列表
    @RequestMapping("/getNewsReviewList")
    public ResponseBean newsReviewListData(@RequestParam Map<String, Object> params){
        String page = params.get("page").toString();
        String limit = params.get("limit").toString();
        String userId = params.get("userId").toString();
        String sort = params.get("sort").toString();
        if (StringUtility.isEmpty(sort)) {
            params.put("sort", "desc");
        }

        params.put("page",page);
        params.put("limit",limit);
        params.put("userId",userId);
        Query query = new Query(params);
        List<Map> spaceNewsList = spaceNewsService.getreviewList(query);
        if(spaceNewsList.size() == 0){
            return new ResponseBean(false, "failed", "该用户还没有评论过空间新闻", null);
        }
        CloudStorageService cloudStorageService = OSSFactory.build();
        for (Map map : spaceNewsList) {
            List<File> files = fileService.getByRelationId(map.get("featured_image").toString());
            if (files.size() > 0 ){
                map.put("featured_image",cloudStorageService.generatePresignedUrl(files.get(0).getUrl()));
            }
//            map.put("featured_image",cloudStorageService.generatePresignedUrl((String) map.get("featured_image")));
        }
        int total = spaceNewsService.getreviewcount(query);
        PageUtils pageUtil = new PageUtils(spaceNewsList, total, query.getLimit(), query.getPage());

        return new ResponseBean(false, "success", null, pageUtil);
    }

    //我点赞过的空间新闻
    @RequestMapping("/getNewsLikeList")
    public ResponseBean newslikelistData(@RequestParam Map<String, Object> params){
        String page = params.get("page").toString();
        String limit = params.get("limit").toString();
        String userId = params.get("userId").toString();
        String sort = params.get("sort").toString();
        if (StringUtility.isEmpty(sort)) {
            params.put("sort", "desc");
        }
        params.put("page",page);
        params.put("limit",limit);
        params.put("userId",userId);
        Query query = new Query(params);
        List<Map> spaceNewsList = spaceNewsService.getlikeList(query);
        if(spaceNewsList.size() == 0){
            return new ResponseBean(false, "failed", "该用户还没有点赞过空间新闻", null);
        }
        CloudStorageService cloudStorageService = OSSFactory.build();
        for (Map map : spaceNewsList) {
            List<File> files = fileService.getByRelationId(map.get("featured_image").toString());
            if (files.size() > 0 ){
                map.put("featured_image",cloudStorageService.generatePresignedUrl(files.get(0).getUrl()));
            }
//            map.put("featured_image",cloudStorageService.generatePresignedUrl((String) map.get("featured_image")));
        }
        int total = spaceNewsService.getlikeCount(query);
        PageUtils pageUtil = new PageUtils(spaceNewsList, total, query.getLimit(), query.getPage());
        return new ResponseBean(false, "success", null, pageUtil);
    }
    //我评论过的爱心捐赠列表
    @RequestMapping("/getDonateReviewList")
    public ResponseBean donateReviewListData(@RequestParam Map<String, Object> params){
        String page = params.get("page").toString();
        String limit = params.get("limit").toString();
        String userId = params.get("userId").toString();
        String sort = params.get("sort").toString();
        if (StringUtility.isEmpty(sort)) {
            params.put("sort", "desc");
        }

        params.put("page",page);
        params.put("limit",limit);
        params.put("userId",userId);
        Query query = new Query(params);
        List<Map> donateList = donateSpaceService.getreviewList(query);
        if(donateList.size() == 0){
            return new ResponseBean(false, "failed", "该用户还没有评论过爱心捐赠", null);
        }
        CloudStorageService cloudStorageService = OSSFactory.build();
        for (Map map : donateList) {
            List<File> files = fileService.getByRelationId(map.get("space_image").toString());
            if (files.size() > 0 ){
                map.put("space_image",cloudStorageService.generatePresignedUrl(files.get(0).getUrl()));
            }
//            map.put("space_image",cloudStorageService.generatePresignedUrl((String) map.get("space_image")));
        }
        int total = donateSpaceService.getreviewcount(query);
        PageUtils pageUtil = new PageUtils(donateList, total, query.getLimit(), query.getPage());

        return new ResponseBean(false, "success", null, pageUtil);
    }

    //我捐赠过的爱心捐赠列表
    @RequestMapping("/getMyDonateList")
    public ResponseBean myDonateListData(@RequestParam Map<String, Object> params){
        String page = params.get("page").toString();
        String limit = params.get("limit").toString();
        String userId = params.get("userId").toString();
        String sort = params.get("sort").toString();
        if (StringUtility.isEmpty(sort)) {
            params.put("sort", "desc");
        }
        params.put("page",page);
        params.put("limit",limit);
        params.put("userId",userId);
        Query query = new Query(params);
        List<Map> donateList = donateSpaceService.getdonatedList(query);
        if(donateList.size() == 0){
            return new ResponseBean(false, "failed", "该用户还没有捐赠过", null);
        }
        CloudStorageService cloudStorageService = OSSFactory.build();
        for (Map map : donateList) {
            List<File> files = fileService.getByRelationId(map.get("space_image").toString());
            if (files.size() > 0 ){
                map.put("space_image",cloudStorageService.generatePresignedUrl(files.get(0).getUrl()));
            }
//            map.put("space_image",cloudStorageService.generatePresignedUrl((String) map.get("space_image")));
        }
        int total = donateSpaceService.getdonatedcount(query);
        PageUtils pageUtil = new PageUtils(donateList, total, query.getLimit(), query.getPage());

        return new ResponseBean(false, "success", null, pageUtil);
    }

    //我评论过的空间活动列表
    @RequestMapping("/getActivityReviewList")
    public ResponseBean activityReviewListData(@RequestParam Map<String, Object> params){
        String page = params.get("page").toString();
        String limit = params.get("limit").toString();
        String userId = params.get("userId").toString();
        String sort = params.get("sort").toString();
        if (StringUtility.isEmpty(sort)) {
            params.put("sort", "desc");
        }
        params.put("page",page);
        params.put("limit",limit);
        params.put("userId",userId);
        Query query = new Query(params);
        List<Map> activityListList = activityService.getreviewList(query);
        if(activityListList.size() == 0){
            return new ResponseBean(false, "failed", "该用户还没有评论过空间活动", null);
        }
        CloudStorageService cloudStorageService = OSSFactory.build();
        for (Map map : activityListList) {
            List<File> files = fileService.getByRelationId(map.get("featured_image").toString());
            if (files.size() > 0 ){
                map.put("featured_image",cloudStorageService.generatePresignedUrl(files.get(0).getUrl()));
            }
//            map.put("featured_image",cloudStorageService.generatePresignedUrl((String) map.get("featured_image")));
        }
        int total = activityService.getreviewcount(query);
        PageUtils pageUtil = new PageUtils(activityListList, total, query.getLimit(), query.getPage());

        return new ResponseBean(false, "success", null, pageUtil);
    }

    //我点赞过的空间活动
    @RequestMapping("/getActivityLikeList")
    public ResponseBean activitylikelistData(@RequestParam Map<String, Object> params){
        String page = params.get("page").toString();
        String limit = params.get("limit").toString();
        String userId = params.get("userId").toString();
        String sort = params.get("sort").toString();
        if (StringUtility.isEmpty(sort)) {
            params.put("sort", "desc");
        }
        params.put("page",page);
        params.put("limit",limit);
        params.put("userId",userId);
        Query query = new Query(params);
        List<Map> activityListList = activityService.getlikeList(params);
        if(activityListList.size() == 0){
            return new ResponseBean(false, "failed", "该用户还没有点赞过空间活动", null);
        }
        CloudStorageService cloudStorageService = OSSFactory.build();
        for (Map map : activityListList) {
            List<File> files = fileService.getByRelationId(map.get("featured_image").toString());
            if (files.size() > 0 ){
                map.put("featured_image",cloudStorageService.generatePresignedUrl(files.get(0).getUrl()));
            }
//            map.put("featured_image",cloudStorageService.generatePresignedUrl((String) map.get("featured_image")));
        }
        int total = activityService.getlikeCount(query);
        PageUtils pageUtil = new PageUtils(activityListList, total, query.getLimit(), query.getPage());
        return new ResponseBean(false, "success", null, pageUtil);
    }
    //我评论过的空间故事列表
    @RequestMapping("/getStoryReviewList")
    public ResponseBean storyReviewListData(@RequestParam Map<String, Object> params){
        String page = params.get("page").toString();
        String limit = params.get("limit").toString();
        String userId = params.get("userId").toString();
        String sort = params.get("sort").toString();
        if (StringUtility.isEmpty(sort)) {
            params.put("sort", "desc");
        }
        params.put("page",page);
        params.put("limit",limit);
        params.put("userId",userId);
        Query query = new Query(params);
        List<Map> spaceStoryList = spaceStoryService.getreviewList(query);
        if(spaceStoryList.size() == 0){
            return new ResponseBean(false, "failed", "该用户还没有评论过空间故事", null);
        }
        CloudStorageService cloudStorageService = OSSFactory.build();
        for (Map map : spaceStoryList) {
            changeImage(map,cloudStorageService,"item_image");
            changeImage(map,cloudStorageService,"avatar");
//            map.put("item_image",cloudStorageService.generatePresignedUrl((String) map.get("item_image")));
//            map.put("avatar",cloudStorageService.generatePresignedUrl((String) map.get("avatar")));
        }
        int total = spaceStoryService.getreviewcount(query);
        PageUtils pageUtil = new PageUtils(spaceStoryList, total, query.getLimit(), query.getPage());

        return new ResponseBean(false, "success", null, pageUtil);
    }

    //我点赞过的空间故事
    @RequestMapping("/getStoryLikeList")
    public ResponseBean storylikelistData(@RequestParam Map<String, Object> params){
        String page = params.get("page").toString();
        String limit = params.get("limit").toString();
        String userId = params.get("userId").toString();
        String sort = params.get("sort").toString();
        if (StringUtility.isEmpty(sort)) {
            params.put("sort", "desc");
        }
        params.put("page",page);
        params.put("limit",limit);
        params.put("userId",userId);
        Query query = new Query(params);
        List<Map> spaceStoryList = spaceStoryService.getlikeList(query);
        if(spaceStoryList.size() == 0){
            return new ResponseBean(false, "failed", "该用户还没有点赞过空间故事", null);
        }
        CloudStorageService cloudStorageService = OSSFactory.build();
        for (Map map : spaceStoryList) {
            changeImage(map,cloudStorageService,"item_image");
            changeImage(map,cloudStorageService,"avatar");
//            map.put("item_image",cloudStorageService.generatePresignedUrl((String) map.get("item_image")));
//            map.put("avatar",cloudStorageService.generatePresignedUrl((String) map.get("avatar")));
        }
        int total = spaceStoryService.getlikeCount(query);
        PageUtils pageUtil = new PageUtils(spaceStoryList, total, query.getLimit(), query.getPage());
        return new ResponseBean(false, "success", null, pageUtil);
    }
    //我评论过的空间秀场列表
    @RequestMapping("/getShowReviewList")
    public ResponseBean showReviewListData(@RequestParam Map<String, Object> params){
        String page = params.get("page").toString();
        String limit = params.get("limit").toString();
        String userId = params.get("userId").toString();
        String sort = params.get("sort").toString();
        if (StringUtility.isEmpty(sort)) {
            params.put("sort", "desc");
        }
        params.put("page",page);
        params.put("limit",limit);
        params.put("userId",userId);
        Query query = new Query(params);
        List<Map> spaceShowList = spaceShowService.getreviewList(query);
        if(spaceShowList.size() == 0){
            return new ResponseBean(false, "failed", "该用户还没有评论过空间秀", null);
        }
        CloudStorageService cloudStorageService = OSSFactory.build();
        for (Map map : spaceShowList) {
            changeImage(map,cloudStorageService,"image");
            changeImage(map,cloudStorageService,"avatar");
            byte[] contents = Base64.decodeBase64(map.get("content").toString().getBytes());
            map.put("content",new String(contents));
//            map.put("image",cloudStorageService.generatePresignedUrl((String) map.get("image")));
//            map.put("avatar",cloudStorageService.generatePresignedUrl((String) map.get("avatar")));
        }
        int total = spaceShowService.getreviewcount(query);
        PageUtils pageUtil = new PageUtils(spaceShowList, total, query.getLimit(), query.getPage());

        return new ResponseBean(false, "success", null, pageUtil);
    }

    //我点赞过的空间秀
    @RequestMapping("/getShowLikeList")
    public ResponseBean showlikelistData(@RequestParam Map<String, Object> params){
        String page = params.get("page").toString();
        String limit = params.get("limit").toString();
        String userId = params.get("userId").toString();
        String sort = params.get("sort").toString();
        if (StringUtility.isEmpty(sort)) {
            params.put("sort", "desc");
        }
        params.put("page",page);
        params.put("limit",limit);
        params.put("userId",userId);
        Query query = new Query(params);
        List<Map> spaceShowList = spaceShowService.getlikeList(query);
        if(spaceShowList.size() == 0){
            return new ResponseBean(false, "failed", "该用户还没有点赞过空间秀", null);
        }
        CloudStorageService cloudStorageService = OSSFactory.build();
        for (Map map : spaceShowList) {
            changeImage(map,cloudStorageService,"image");
            changeImage(map,cloudStorageService,"avatar");
//            map.put("image",cloudStorageService.generatePresignedUrl((String) map.get("image")));
//            map.put("avatar",cloudStorageService.generatePresignedUrl((String) map.get("avatar")));
        }
        int total = spaceShowService.getlikeCount(query);
        PageUtils pageUtil = new PageUtils(spaceShowList, total, query.getLimit(), query.getPage());
        return new ResponseBean(false, "success", null, pageUtil);
    }

    //我收藏的空间新闻
    @RequestMapping("/getNewsCollectionList")
    public ResponseBean collectionlistData(@RequestParam Map<String, Object> params){
        String page = params.get("page").toString();
        String limit = params.get("limit").toString();
        String userId = params.get("userId").toString();
        String sort = params.get("sort").toString();
        if (StringUtility.isEmpty(sort)) {
            params.put("sort", "desc");
        }
        params.put("page",page);
        params.put("limit",limit);
        params.put("userId",userId);
        Query query = new Query(params);
        List<Map> spaceNewsList = spaceNewsService.getcollectionList(query);
        if(spaceNewsList.size() == 0){
            return new ResponseBean(false, "failed", "该用户还没有收藏过空间新闻", null);
        }
        CloudStorageService cloudStorageService = OSSFactory.build();
        for (Map map : spaceNewsList) {
            List<File> files = fileService.getByRelationId(map.get("featured_image").toString());
            if (files.size() > 0 ){
                map.put("featured_image",cloudStorageService.generatePresignedUrl(files.get(0).getUrl()));
            }
//            map.put("featured_image",cloudStorageService.generatePresignedUrl((String) map.get("featured_image")));
        }
        int total = spaceNewsService.getcollectionCount(query);
        PageUtils pageUtil = new PageUtils(spaceNewsList, total, query.getLimit(), query.getPage());
        return new ResponseBean(false, "success", null, pageUtil);
    }
    //我的学习记录
    @RequestMapping("/getStudyList")
    public ResponseBean studylistData(@RequestParam Map<String, Object> params){
        String page = params.get("page").toString();
        String limit = params.get("limit").toString();
        String userId = params.get("userId").toString();
        params.put("page",page);
        params.put("limit",limit);
        params.put("userId",userId);
        Query query = new Query(params);
        List<Map> studyRoomList = studyRoomService.getstudyList(query);
        if(studyRoomList.size() == 0){
            return new ResponseBean(false, "failed", "该用户还没有学习记录", null);
        }
        CloudStorageService cloudStorageService = OSSFactory.build();
        for (Map map : studyRoomList) {
            map.put("featured_image",cloudStorageService.generatePresignedUrl((String) map.get("featured_image")));
            map.put("avatar",cloudStorageService.generatePresignedUrl((String) map.get("avatar")));

        }
        int total = studyRoomService.getstudyCount(query);
        PageUtils pageUtil = new PageUtils(studyRoomList, total, query.getLimit(), query.getPage());
        return new ResponseBean(false, "success", null, pageUtil);
    }

    //我的发布
    @RequestMapping("/getPersonalShow")
    public ResponseBean personalshowlistData(@RequestParam Map<String, Object> params){
        String page = params.get("page").toString();
        String limit = params.get("limit").toString();
        String userId =params.get("userId").toString();
        String sort = params.get("sort").toString();
        if (StringUtility.isEmpty(sort)) {
            params.put("sort", "desc");
        }
        params.put("page",page);
        params.put("limit",limit);
        params.put("userId",userId);
        Query query = new Query(params);
        List<Map> personalList = spaceShowService.getSpaceShowByUser(query);
        if(personalList.size() == 0){
            return new ResponseBean(false, "failed", "该用户还没有发表过空间秀", null);
        }
        CloudStorageService cloudStorageService = OSSFactory.build();
        for (Map map : personalList) {
            changeImage(map,cloudStorageService,"image");
            changeImage(map,cloudStorageService,"avatar");
            changeImage(map,cloudStorageService,"video");
//            map.put("image",cloudStorageService.generatePresignedUrl((String) map.get("image")));
//            map.put("avatar",cloudStorageService.generatePresignedUrl((String) map.get("avatar")));
//            map.put("video",cloudStorageService.generatePresignedUrl((String) map.get("video")));

        }
        int total = spaceShowService.getShowCount(query);
        PageUtils pageUtil = new PageUtils(personalList, total, query.getLimit(), query.getPage());
        return new ResponseBean(false, "success", null, pageUtil);
    }

    //我的秀
    @RequestMapping("/getMyShow")
    public ResponseBean getMyShow(@RequestParam Map<String, Object> params){
        String page = params.get("page").toString();
        String limit = params.get("limit").toString();
        String userId =params.get("userId").toString();
        String sort = params.get("sort").toString();
        if (StringUtility.isEmpty(sort)) {
            params.put("sort", "desc");
        }
        params.put("page",page);
        params.put("limit",limit);
        params.put("userId",userId);
        Query query = new Query(params);
        List<Map> personalList = spaceShowService.getMyShowByUser(query);
        if(personalList.size() == 0){
            return new ResponseBean(false, "failed", "该用户还没有发表过空间秀", null);
        }
        CloudStorageService cloudStorageService = OSSFactory.build();
        for (Map map : personalList) {
            changeImage(map,cloudStorageService,"image");
            changeImage(map,cloudStorageService,"avatar");
            changeImage(map,cloudStorageService,"video");
//            map.put("image",cloudStorageService.generatePresignedUrl((String) map.get("image")));
//            map.put("avatar",cloudStorageService.generatePresignedUrl((String) map.get("avatar")));
//            map.put("video",cloudStorageService.generatePresignedUrl((String) map.get("video")));

        }
        int total = spaceShowService.getMyShowCount(query);
        PageUtils pageUtil = new PageUtils(personalList, total, query.getLimit(), query.getPage());
        return new ResponseBean(false, "success", null, pageUtil);
    }

    //我的预约课程记录
    @RequestMapping("/getLessonList")
    public ResponseBean lessonlistData(@RequestParam Map<String, Object> params){
        String page = params.get("page").toString();
        String limit = params.get("limit").toString();
        String userId = params.get("userId").toString();
        params.put("page",page);
        params.put("limit",limit);
        params.put("userId",userId);
        Query query = new Query(params);
        List<Map> userLessonList = userLessonService.getByUser(query);
        if(userLessonList.size() == 0){
            return new ResponseBean(false, "failed", "该用户还没有预约课程", null);
        }
        CloudStorageService cloudStorageService = OSSFactory.build();
        for (Map map : userLessonList) {
            map.put("featured_image",cloudStorageService.generatePresignedUrl((String) map.get("featured_image")));
        }
        int total = userLessonService.getUserCount(query);
        PageUtils pageUtil = new PageUtils(userLessonList, total, query.getLimit(), query.getPage());
        return new ResponseBean(false, "success", null, pageUtil);
    }

    //我的分享课程
    @RequestMapping("/getShareLesson")
    public ResponseBean booklessonlistData(@RequestParam Map<String, Object> params){
        String page = params.get("page").toString();
        String limit = params.get("limit").toString();
        String userId = params.get("userId").toString();
        params.put("page",page);
        params.put("limit",limit);
        params.put("userId",userId);
        Query query = new Query(params);
        List<Map> shareLessonList = sharingLessonService.getshareList(query);
        if(shareLessonList.size() == 0){
            return new ResponseBean(false, "failed", "该用户还没有分享课程", null);
        }
        CloudStorageService cloudStorageService = OSSFactory.build();
        for (Map map : shareLessonList) {
            map.put("featured_image",cloudStorageService.generatePresignedUrl((String) map.get("featured_image")));
        }
        int total = sharingLessonService.getshareCount(query);
        PageUtils pageUtil = new PageUtils(shareLessonList, total, query.getLimit(), query.getPage());
        return new ResponseBean(false, "success", null, pageUtil);
    }


    //我的故事
    @RequestMapping(value = "/myStory")
    public ResponseBean myStory(@RequestParam Map<String, Object> params) throws Exception {
        String userId = params.get("userId").toString();
        String pageCount = params.get("limit").toString();
        String curPageNum = params.get("page").toString();
        String sort = params.get("sort").toString();
        if (StringUtility.isEmpty(sort)) {
            params.put("sort", "desc");
        }
        params.put("limit", pageCount);
        params.put("page", curPageNum);
        params.put("userId",userId);
        Query query = new Query(params);
        List<Map> spaceStories = spaceStoryService.getmyStoryList(query);
        CloudStorageService cloudStorageService = OSSFactory.build();
        for (Map donatemap : spaceStories) {
            changeImage(donatemap,cloudStorageService,"item_image");
//            donatemap.put("item_image",cloudStorageService.generatePresignedUrl((String) donatemap.get("item_image")));
            //System.out.println("++++++++++"+donatemap.get("avatar"));
            changeImage(donatemap,cloudStorageService,"avatar");
//            donatemap.put("avatar",cloudStorageService.generatePresignedUrl((String) donatemap.get("avatar")));
        }
        int total = spaceStoryService.getmyStoryCount(query);
        PageUtils pageUtil = new PageUtils(spaceStories, total, query.getLimit(), query.getPage());
        return new ResponseBean(false, "success", null, pageUtil);
    }
    //个人报名已通过的空间活动
    @RequestMapping("/getPassedActivity")
    public ResponseBean PassedActivity(@RequestParam Map<String, Object> params){
        String page = params.get("page").toString();
        String limit = params.get("limit").toString();
        String userId = params.get("userId").toString();
        String sort = params.get("sort").toString();
        if (StringUtility.isEmpty(sort)) {
            params.put("sort", "desc");
        }
        params.put("page",page);
        params.put("limit",limit);
        params.put("userId",userId);
        Query query = new Query(params);
        List<Map> passedList = activityService.getpassedactivityList(query);
        if(passedList.size() == 0){
            return new ResponseBean(false, "failed", "该用户还没有分享课程", null);
        }
        CloudStorageService cloudStorageService = OSSFactory.build();
        for (Map map : passedList) {
            List<File> files = fileService.getByRelationId(map.get("featured_image").toString());
            if (files.size() > 0 ){
                map.put("featured_image",cloudStorageService.generatePresignedUrl(files.get(0).getUrl()));
            }
//            map.put("featured_image",cloudStorageService.generatePresignedUrl((String) map.get("featured_image")));
        }
        int total = activityService.getpassedactivityCount(query);
        PageUtils pageUtil = new PageUtils(passedList, total, query.getLimit(), query.getPage());
        return new ResponseBean(false, "success", null, pageUtil);
    }


    //已结束的空间活动
    @RequestMapping("/getFinishActivity")
    public ResponseBean FinishActivity(@RequestParam Map<String, Object> params){
        String page = params.get("page").toString();
        String limit = params.get("limit").toString();
        String userId = params.get("userId").toString();
        String sort = params.get("sort").toString();
        if (StringUtility.isEmpty(sort)) {
            params.put("sort", "desc");
        }
        params.put("page",page);
        params.put("limit",limit);
        params.put("userId",userId);
        Query query = new Query(params);
        List<Map> finishList = activityService.getfinishactivityList(query);
        if(finishList.size() == 0){
            return new ResponseBean(false, "failed", "该用户还没有分享课程", null);
        }
        CloudStorageService cloudStorageService = OSSFactory.build();
        for (Map map : finishList) {
            changeImage(map,cloudStorageService,"featured_image");
//            map.put("featured_image",cloudStorageService.generatePresignedUrl((String) map.get("featured_image")));
        }
        int total = activityService.getfinishactivityCount(query);
        PageUtils pageUtil = new PageUtils(finishList, total, query.getLimit(), query.getPage());
        return new ResponseBean(false, "success", null, pageUtil);
    }

    //我的书架
    @RequestMapping("/getMyBook")
    public ResponseBean MyBook(@RequestParam Map<String, Object> params){
        String page = params.get("page").toString();
        String limit = params.get("limit").toString();
        String userId = params.get("userId").toString();
        params.put("page",page);
        params.put("limit",limit);
        params.put("userId",userId);
        Query query = new Query(params);
        List<PersonalBook> bookList = bookService.getPersonalList(query);
        if(bookList.size() == 0){
            return new ResponseBean(false, "failed", "该用户书架是空的", null);
        }
        CloudStorageService cloudStorageService = OSSFactory.build();
        for (PersonalBook map : bookList) {
            map.setCover(cloudStorageService.generatePresignedUrl(map.getCover()));
        }
        int total = bookService.getPersonalCount(query);
        PageUtils pageUtil = new PageUtils(bookList, total, query.getLimit(), query.getPage());
        return new ResponseBean(false, "success", null, pageUtil);
    }



    @RequestMapping("/volunteercount")
    public ResponseBean getVolunteerNum(){
        Map map=new HashMap();//数据容器
        int num = sysUserService.getVolunteerCount();
        map.put("Volunteer",num);
        return new ResponseBean(false, "success", null, map);
    }

    /**
     * @return xin.xiaoer.modules.mobile.bean.ResponseBean
     * @Author XiaoDong
     * @Description //TODO 个人信息
     * @Param [params, request]
     **/
    @GetMapping("/getPersonalData/{userId}")
    @ApiOperation("个人信息")
    @ResponseBody
    public ResponseBean getPersonalData(@PathVariable("userId") Long userId, HttpServletRequest request) {

        SysUser sysUser = sysUserService.queryObject(userId);
        Map<String, Object> result = new HashMap<>();
        String addressTxt = sysUserService.getAddressTxtFromAddressIds(sysUser.getAddress());
        sysUser.setAddressTxt(addressTxt);
        sysUser.setPassword("");
        Double userIntegral = integralService.getUserIntegralTotal(userId);
        Integer userRanking = integralService.getUserRanking(userId);
        sysUser.setRanking(userRanking);
        sysUser.setIntegral(userIntegral);
        Integer userClassCode = sysUser.getUserClassCode();
        switch (userClassCode) {
            case 1:
                sysUser.setUserType("admin");
                break;
            case 2:
                sysUser.setUserType("volunteer");
                break;
            case 3:
                sysUser.setUserType("newsman");
                break;
            case 9:
                sysUser.setUserType("user");
                break;
            default:
                break;
        }
        result.put("member", sysUser);
        return new ResponseBean(false, ResponseBean.SUCCESS, null, result);
    }


    /**
     * @return xin.xiaoer.modules.mobile.bean.ResponseBean
     * @Author XiaoDong
     * @Description //TODO 修改头像
     * @Param [userId, avatar]
     **/
    @RequestMapping(value = "/updateAvatar/{userId}", method = RequestMethod.POST)
    @ApiOperation("修改头像")
    @ResponseBody
    public ResponseBean onlineUserTotal(@RequestParam(value = "avatar") MultipartFile avatar, @PathVariable("userId") Long userId) throws IOException {

        String avatarId = UUID.randomUUID().toString();

        String fileOriginalFilename = avatar.getOriginalFilename();
        Long fileSize = avatar.getSize();
        String suffix = fileOriginalFilename.substring(fileOriginalFilename.lastIndexOf("."));
        CloudStorageService cloudStorageService = OSSFactory.build();

        String avatarUrl = "";
        try {
            avatarUrl = cloudStorageService.uploadSuffix(avatar.getInputStream(), suffix);

            File imageFile = new File();
            imageFile.setUploadId(avatarId);
            imageFile.setUrl(avatarUrl);
            imageFile.setFileName(fileOriginalFilename);
            imageFile.setOssYn("Y");
            imageFile.setFileType("image");
            imageFile.setFileSize(fileSize.toString());
            fileService.save(imageFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

        SysUser sysUser = sysUserService.queryObject(userId);
        sysUser.setAvatar(avatarId);

        sysUserService.update(sysUser);
        ChatUser chatUser = chatUserService.get(userId);
        YunXinUtil yunXinUtil = new YunXinUtil();
        try {
            yunXinUtil.updateUinfo(sysUser, chatUser);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Integer userClassCode = sysUser.getUserClassCode();
        switch (userClassCode) {
            case 1:
                sysUser.setUserType("admin");
                break;
            case 2:
                sysUser.setUserType("volunteer");
                break;
            case 3:
                sysUser.setUserType("newsman");
                break;
            case 9:
                sysUser.setUserType("user");
                break;
            default:
                break;
        }

        Map<String, Object> result = new HashMap<>();
        sysUser.setPassword("");
        result.put("member", sysUser);
        return new ResponseBean(false, "success", null, result);
    }


    /**
     * @return xin.xiaoer.modules.mobile.bean.ResponseBean
     * @Author XiaoDong
     * @Description //TODO 修改个人信息
     * @Param [userId, params]
     **/
    @RequestMapping(value = "/updateUserInfo/{userId}", method = RequestMethod.POST)
    @ApiOperation("修改个人信息")
    @ResponseBody
    public ResponseBean updateUserInfo(@PathVariable("userId") Long userId, @RequestParam Map<String, Object> params) throws IOException {

        SysUser sysUser = sysUserService.queryObject(userId);//id查用户

        if (params.get("nickname") != null) {
            String nickname = params.get("nickname").toString();
            sysUser.setNickname(nickname);//修改昵称
        }
        if (params.get("address") != null) {
            String address = params.get("address").toString();
            String levelArea = areaService.getLevelByName(address);//地区代码串
            sysUser.setAddress(levelArea);//修改地址
        }
        if (params.get("gender") != null) {
            String gender = params.get("gender").toString();
            sysUser.setGender(gender);//修改性别
        }
        if (params.get("personality") != null) {
            String personality = params.get("personality").toString();
            sysUser.setPersonality(personality);//修改个性签名
        }
        sysUserService.update(sysUser);//更新数据库
        ChatUser chatUser = chatUserService.get(userId);
        YunXinUtil yunXinUtil = new YunXinUtil();
        try {
            yunXinUtil.updateUinfo(sysUser, chatUser);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Integer userClassCode = sysUser.getUserClassCode();
        switch (userClassCode) {
            case 1:
                sysUser.setUserType("admin");
                break;
            case 2:
                sysUser.setUserType("volunteer");
                break;
            case 3:
                sysUser.setUserType("newsman");
                break;
            case 9:
                sysUser.setUserType("user");
                break;
            default:
                break;
        }

        Map<String, Object> result = new HashMap<>();
        sysUser.setPassword("");
        result.put("member", sysUser);

        return new ResponseBean(false, "success", null, result);
    }


    /**
     *@Author XiaoDong
     *@Description //TODO 我的账户概况
     *@Param [userId]
     *@return xin.xiaoer.modules.mobile.bean.ResponseBean
     **/
    @ApiOperation("我的账户")
    @GetMapping("/myAccount/{userId}")
    @ResponseBody
    public ResponseBean myAccount(@PathVariable("userId") Long userId) {
        SysUser sysUser = sysUserService.queryObject(userId);
        if (sysUser == null || sysUser.getStatus() != 1) {
            return new ResponseBean(false, ResponseBean.FAILED, null, "无此账号或账号已停用",null);
        }
        Map result = new HashMap();
        Integer nationalRanking = sysUserService.getNationalRanking(sysUser.getUserId());//全国排名
        if (nationalRanking != null) {
            result.put("nationalRanking", nationalRanking);

        } else {
            result.put("nationalRanking", "暂无排名");
        }
        String sumIntegral = integralService.getSumIntegral(sysUser.getUserId());//累计积分
        if (sumIntegral != null) {
            result.put("sumIntegral", sumIntegral);
        } else {
            result.put("sumIntegral", "0");
        }
        String sumDonateAccount = donateUserService.getSumAmount(sysUser.getUserId());//累计支出(捐赠)
        if (sumDonateAccount != null) {
            result.put("sumDonateAccount", sumDonateAccount);
        } else {
            result.put("sumAmount", "0");
        }
        result.put("balance","2000");//余额,暂时没有金额,返回假数据
        result.put("income","1000");//收入
        return new ResponseBean(false, ResponseBean.SUCCESS, null, result);
    }


    /**
     *@Author XiaoDong
     *@Description //TODO 积分来源
     *@Param [userId, params]
     *@return xin.xiaoer.modules.mobile.bean.ResponseBean
     **/
    @ApiOperation("积分来源")
    @ResponseBody
    @PostMapping("/integralSource/{userId}")
    public ResponseBean integralSource(@PathVariable("userId") Long userId,@RequestParam Map<String,Object> params){
        SysUser sysUser = sysUserService.queryObject(userId);
        if (sysUser == null || sysUser.getStatus() != 1) {
            return new ResponseBean(false, ResponseBean.FAILED, null, "无此账号或账号已停用",null);
        }
        params.put("userId",sysUser.getUserId());
        if (params.get("page")==null||params.get("limit")==null|| StringUtils.isBlank(params.get("page").toString())||StringUtils.isBlank(params.get("limit").toString())){
            return new ResponseBean(false,ResponseBean.FAILED,null,"参数异常",null);
        }
        Query query = new Query(params);
        List<Integral> list = integralService.getList(query);
        for (Integral integral : list) {
            if (integral.getTitle()==null){
                integral.setTitle("未知来源");
            }
        }
        int count = integralService.getCount(query);
        PageUtils pageUtils = new PageUtils(list, count, query.getLimit(), query.getPage());
        return new ResponseBean(false,ResponseBean.SUCCESS,null,pageUtils);
    }


    /**
     *@Author XiaoDong
     *@Description //TODO 等级说明
     *@Param []
     *@return xin.xiaoer.modules.mobile.bean.ResponseBean
     **/
    @ApiOperation("等级说明")
    @ResponseBody
    @GetMapping("/gradesDescription")
    public ResponseBean myGrades(){

        UserLevel userLevel = sysConfigService.getConfigClassObject(ConfigConstant.USER_LEVEL_CONFIG_KEY, UserLevel.class);//等级说明
        return new ResponseBean(false,ResponseBean.SUCCESS,null,userLevel);
    }

    /**
     *@Author XiaoDong
     *@Description //TODO 账目支出,没有收入
     *@Param [userId, params]
     *@return xin.xiaoer.modules.mobile.bean.ResponseBean
     **/
    @ApiOperation("账目信息")
    @ResponseBody
    @PostMapping("/myAccountSource/{userId}")
    public ResponseBean myAccountSource(@PathVariable("userId") Long userId,@RequestParam Map<String,Object> params){
        SysUser sysUser = sysUserService.queryObject(userId);
        if (sysUser == null || sysUser.getStatus() != 1) {
            return new ResponseBean(false, ResponseBean.FAILED, null, "无此账号或账号已停用");
        }
        params.put("userId",sysUser.getUserId());
        if (params.get("page")==null||params.get("limit")==null|| StringUtils.isBlank(params.get("page").toString())||StringUtils.isBlank(params.get("limit").toString())){
            return new ResponseBean(false,ResponseBean.FAILED,null,"参数异常",null);
        }
        params.put("userId",userId);
        params.put("state","1");
        Query query = new Query(params);
        List<WebAccount> list=donateUserService.getListAccountByUserId(query);
        int count = donateUserService.getCount(query);
        PageUtils pageUtils = new PageUtils(list, count, query.getLimit(), query.getPage());
        return new ResponseBean(false,ResponseBean.SUCCESS,null,pageUtils);
    }

    private void changeImage(Map map,CloudStorageService cloudStorageService,String name){
        List<File> files = fileService.getByRelationId(map.get(name).toString());
        if (files.size() > 0 ){
            map.put(name,cloudStorageService.generatePresignedUrl(files.get(0).getUrl()));
        }
    }
}
