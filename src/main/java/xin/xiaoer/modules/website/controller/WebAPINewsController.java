package xin.xiaoer.modules.website.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import xin.xiaoer.common.enumresource.StateEnum;
import xin.xiaoer.common.oss.CloudStorageService;
import xin.xiaoer.common.oss.OSSFactory;
import xin.xiaoer.common.utils.PageUtils;
import xin.xiaoer.common.utils.Query;
import xin.xiaoer.common.utils.StringUtility;
import xin.xiaoer.entity.Article;
import xin.xiaoer.entity.File;
import xin.xiaoer.entity.SysUser;
import xin.xiaoer.modules.activity.entity.Activity;
import xin.xiaoer.modules.activity.service.ActivityService;
import xin.xiaoer.modules.activityreport.entity.ActivityReport;
import xin.xiaoer.modules.activityreport.service.ActivityReportService;
import xin.xiaoer.modules.book.entity.Book;
import xin.xiaoer.modules.book.service.BookService;
import xin.xiaoer.modules.classroom.entity.SharingLesson;
import xin.xiaoer.modules.classroom.entity.StudyRoom;
import xin.xiaoer.modules.classroom.service.SharingLessonService;
import xin.xiaoer.modules.classroom.service.StudyRoomService;
import xin.xiaoer.modules.donatespace.entity.DonateSpace;
import xin.xiaoer.modules.donatespace.service.DonateSpaceService;
import xin.xiaoer.modules.favourite.entity.Favourite;
import xin.xiaoer.modules.favourite.service.FavouriteService;
import xin.xiaoer.modules.like.entity.Like;
import xin.xiaoer.modules.like.service.LikeService;
import xin.xiaoer.modules.mobile.bean.ResponseBean;
import xin.xiaoer.modules.mobile.entity.HeadlineListItem;
import xin.xiaoer.modules.review.service.ReviewService;
import xin.xiaoer.modules.spacehaedline.entity.SpaceHeadline;
import xin.xiaoer.modules.spacehaedline.service.SpaceHeadlineService;
import xin.xiaoer.modules.story.entity.SpaceStory;
import xin.xiaoer.modules.story.service.SpaceStoryService;
import xin.xiaoer.modules.xebanner.entity.XeBanner;
import xin.xiaoer.modules.xebanner.service.XeBannerService;
import xin.xiaoer.service.FileService;
import xin.xiaoer.service.SpaceNewsService;
import xin.xiaoer.service.SysUserService;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api("空间新闻")
@Controller
@RequestMapping("website/news")
public class WebAPINewsController {

    @Autowired
    private SpaceHeadlineService headlineService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private LikeService likeService;
    @Autowired
    private FavouriteService favouriteService;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private FileService fileService;
    @Autowired
    private XeBannerService xeBannerService;
    @Autowired
    private DonateSpaceService donateSpaceService;
    @Autowired
    private SpaceHeadlineService spaceHeadlineService;
    @Autowired
    private ActivityReportService activityReportService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private SpaceStoryService spaceStoryService;
    @Autowired
    private BookService bookService;
    @Autowired
    private SharingLessonService sharingLessonService;
    @Autowired
    private StudyRoomService studyRoomService;
    @Autowired
    private SpaceNewsService spaceNewsService;


    /**
    *@Author dong
    *@Description //TODO 根据空间id查一周内新闻,如果不够,增加到一个月以内
    *@Param [params]
    *@return xin.xiaoer.modules.mobile.bean.ResponseBean
    **/
    @RequestMapping(value = "/spaceNews", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("空间推荐新闻")
    public ResponseBean spaceNews(@RequestParam Map<String, Object> params) {
        //查询列表数据

//        String requestPageCount = request.getParameter("pageCount");
//        String pageCount = params.get("pageCount").toString();
//        String curPageNum = params.get("curPageNum").toString();
//        String userId = params.get("userId").toString();
//        if (params.get("spaceId")==null){
//            return new ResponseBean(false,ResponseBean.FAILED,"参数异常","缺少参数",null);
//        }
//        String spaceId = params.get("spaceId").toString();//空间id
//        if (StringUtility.isEmpty(spaceId)) {
//            params.put("spaceId", 0);
//        }

        if (params.get("spaceId")==null || StringUtils.isBlank(params.get("spaceId").toString())){
            params.put("spaceId",0);
        }
        params.put("limit", "10");//默认查十条
        params.put("page", "1");
        params.put("state", "1");
        String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis() - (604800L * 1000)));//当前时间推前一周
        params.put("date", format);//当前时间推前一周
        Query query = new Query(params);//用户id,空间id,分页条件

        List<HeadlineListItem> headlineListItems = headlineService.getIntervalNews(query);
        if (headlineListItems.size()<10){//如果新闻数量不够
            query.remove("date");//移除一周限制
            query.put("left", new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis() - (2678400L * 1000))));//增加时间限制一个月内到一周前
            query.put("right",format);
            Integer s = 10 - headlineListItems.size();
            query.put("limit",s);//再查差的那么多条数
            List<HeadlineListItem> intervalNews = headlineService.getIntervalNews(query);
            headlineListItems.addAll(intervalNews);
        }
        CloudStorageService cloudStorageService = OSSFactory.build();
        for (HeadlineListItem headlineListItem : headlineListItems) {
            List<File> files = fileService.getByRelationId(headlineListItem.getFeaturedImage());
            if (files.size() > 0 ){
                headlineListItem.setFeaturedImage(cloudStorageService.generatePresignedUrl(files.get(0).getUrl()));
            }
        }
        int total = headlineService.getCount(query);

        PageUtils pageUtil = new PageUtils(headlineListItems, total, query.getLimit(), query.getPage());

//        return R.ok().put("list",pageUtil);
        return new ResponseBean(false,ResponseBean.SUCCESS,null,pageUtil);
    }

    /**
    *@Author dong
    *@Description //TODO 新闻快讯标题和id
    *@Param []
    *@return xin.xiaoer.modules.mobile.bean.ResponseBean
    **/
    @PostMapping(value = "/newsFlash")
    @ApiOperation(value = "最新新闻", notes = "最新新闻")
    @ResponseBody
    public ResponseBean newsFlash() {
        //查询列表数据

//        String pageCount = params.get("pageCount").toString();
//        String curPageNum = params.get("curPageNum").toString();
//        String userId = params.get("userId").toString();
//        String spaceId = params.get("spaceId").toString();
//        if (StringUtility.isEmpty(spaceId)) {
//            params.put("spaceId", 0);
//        }
        Map<String,Object> params=new HashMap<>();
        params.put("limit", "10");
        params.put("page", "1");
        params.put("state", "1");
        Query query = new Query(params);

        List<HeadlineListItem> headlineListItems = headlineService.getNewsFlash(query);
        int total = headlineService.countListData(query);

        PageUtils pageUtil = new PageUtils(headlineListItems, total, query.getLimit(), query.getPage());

//        return R.ok().put("list", pageUtil);
        return new ResponseBean(false,ResponseBean.SUCCESS,null,pageUtil);
    }


    /**
    *@Author dong
    *@Description //TODO 推荐新闻列表
    *@Param []
    *@return xin.xiaoer.modules.mobile.bean.ResponseBean
    **/
    @GetMapping(value = "/recommendedNews")
    @ApiOperation(value = "新闻推荐列表", notes = "新闻推荐列表")
    @ResponseBody
    public ResponseBean recommendedNews() {
        //查询列表数据

        Map<String, Object> params = new HashMap<>();
        params.put("limit", "10");
        params.put("page", "1");
        params.put("state", "1");
//        params.put("date", new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis() - (604800L * 1000))));//当前时间推前一周
        Query query = new Query(params);

//        List<HeadlineListItem> headlineListItems = headlineService.queryListData(query);//一周以内的新闻
        CloudStorageService cloudStorageService = OSSFactory.build();
        List<HeadlineListItem> headlineListItems=headlineService.getrecommendeNews(query);
        for (HeadlineListItem headlineListItem : headlineListItems) {
            headlineListItem.setFeaturedImage(cloudStorageService.generatePresignedUrl(headlineListItem.getFeaturedImage()));
        }
        int total = headlineService.countListData(query);

        PageUtils pageUtil = new PageUtils(headlineListItems, total, query.getLimit(), query.getPage());

//        return R.ok().put("list", pageUtil);
        return new ResponseBean(false,ResponseBean.SUCCESS,null,pageUtil);
    }

    /**
    *@Author dong
    *@Description //TODO 获取新闻详情,查看新闻不要求必须登录
    *@Param [headLineId, userId]
    *@return xin.xiaoer.modules.mobile.bean.ResponseBean
    **/
    @GetMapping(value = "/detail/{headLineId}")
    @ApiOperation("新闻详情")
    @ResponseBody
    public ResponseBean detail(@PathVariable("headLineId") @ApiParam(name = "headLineId", value = "新闻id", required = true) Integer headLineId,
                    @ApiParam(name = "userId", value = "用户id") Long userId) {
        //查询列表数据

        SpaceHeadline spaceHeadline = headlineService.get(headLineId);//根据新闻id查新闻信息
        if (spaceHeadline==null||!"1".equals(spaceHeadline.getState())) {//判断新闻的状态
            return new ResponseBean(false,ResponseBean.FAILED,"参数异常","无此新闻",null);
        }
        spaceHeadline.setReadCount(spaceHeadline.getReadCount() + 1);//阅读次数+1
        headlineService.update(spaceHeadline);//修改阅读次数
        SysUser sysUser = sysUserService.queryObject(spaceHeadline.getCreateBy());//查询当前新闻的创建者
        if (sysUser != null) {
            if (StringUtility.isEmpty(sysUser.getNickname())) {
                spaceHeadline.setCreateUser(spaceHeadline.getAuthorName());//如果创建者没有昵称就用作者名字
            } else {
                spaceHeadline.setCreateUser(sysUser.getNickname());//否则就用创建者昵称
            }
        }

        Map map=new HashMap();
        map.put("articleId",spaceHeadline.getHeadlineId());
        map.put("articleTypeCode",Article.SPACE_HEADLINE);
        int count = likeService.getCount(map);

        if (userId != null) {

            Favourite favourite = favouriteService.getByArticleAndUser("AT0002", spaceHeadline.getHeadlineId().longValue(), userId);
            if (favourite != null) {
                spaceHeadline.setFavouriteYn(true);
            } else {
                spaceHeadline.setFavouriteYn(false);
            }
            Like like = likeService.getByArticleAndUser(Article.SPACE_HEADLINE, Long.parseLong(Integer.toString(headLineId)), userId);
            if (like == null) {
                spaceHeadline.setLikeYn(false);
            } else {
                spaceHeadline.setLikeYn(true);
            }
        }
        spaceHeadline.setReviewCount(reviewService.getCountByCodeAndId(Article.SPACE_HEADLINE, Long.parseLong(Integer.toString(headLineId))));
        List<File> files = fileService.getByRelationId(spaceHeadline.getFeaturedImage());
        CloudStorageService cloudStorageService = OSSFactory.build();
        if (files.size() > 0) {
            if (!StringUtility.isEmpty(files.get(0).getUrl())) {
                spaceHeadline.setFeaturedImage(cloudStorageService.generatePresignedUrl(files.get(0).getUrl()));
            } else {
                spaceHeadline.setFeaturedImage("");
            }
        } else {
            spaceHeadline.setFeaturedImage("");
        }

        map.clear();
        map.put("data",spaceHeadline);
        map.put("likeCount",count);
//        return R.ok().put("list", spaceHeadline);
        return new ResponseBean(false,ResponseBean.SUCCESS,null,map);
    }


    /**
    *@Author XiaoDong
    *@Description //TODO 首页轮播图
    *@Param [params]
    *@return xin.xiaoer.modules.mobile.bean.ResponseBean
    **/
    @RequestMapping(value = "/banner", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    @ApiOperation("首页轮播图")
    public ResponseBean banner( Map<String, Object> params)throws IOException {

        params.clear();
        params.put("bnTypeCode", "BNT001");
        params.put("state", "1");
        List<XeBanner> xeBanners = xeBannerService.getList(params);

        CloudStorageService cloudStorageService = OSSFactory.build();
        for (XeBanner xeBanner: xeBanners){
            String articleType = xeBanner.getArticleType();

            if (xeBanner.getArticleId() != null) {
                if (articleType == null){

                } else if (articleType.equals("AT0001")) {
                    DonateSpace donateSpace = donateSpaceService.get(xeBanner.getArticleId());
                    if (donateSpace != null){
                        if (!donateSpace.getState().equals(StateEnum.ENABLE.getCode())){
                            xeBanners.remove(xeBanner);
                            continue;
                        }
                        xeBanner.setArticleName(donateSpace.getTitle());
                    }
                } else if (articleType.equals("AT0002")) {
                    SpaceHeadline spaceHeadline = spaceHeadlineService.get(Integer.parseInt(xeBanner.getArticleId().toString()));
                    if (spaceHeadline != null) {
                        if (!spaceHeadline.getState().equals(StateEnum.ENABLE.getCode())){
                            xeBanners.remove(xeBanner);
                            continue;
                        }
                        xeBanner.setArticleName(spaceHeadline.getTitle());
                    }
                } else if (articleType.equals("AT0003")) {
                    ActivityReport activityReport = activityReportService.get(Integer.parseInt(xeBanner.getArticleId().toString()));
                    if (activityReport != null){
                        if (!activityReport.getState().equals(StateEnum.ENABLE.getCode())){
                            xeBanners.remove(xeBanner);
                            continue;
                        }
                        xeBanner.setArticleName(activityReport.getTitle());
                    }
                } else if (articleType.equals("AT0004")) {
                    Activity activity = activityService.get(Integer.parseInt(xeBanner.getArticleId().toString()));
                    activity = activityService.checkExpired(activity);
                    if (activity != null){
                        if (!activity.getState().equals(StateEnum.ENABLE.getCode())){
                            xeBanners.remove(xeBanner);
                            continue;
                        }
                        xeBanner.setArticleName(activity.getTitle());
                        xeBanner.setArticleStatus(activity.getActivityStatusCode());
                    }
                } else if (articleType.equals("AT0005")) {
                    SpaceStory spaceStory = spaceStoryService.get(xeBanner.getArticleId());
                    if (spaceStory != null){
                        if (!spaceStory.getState().equals(StateEnum.ENABLE.getCode())){
                            xeBanners.remove(xeBanner);
                            continue;
                        }
                        xeBanner.setArticleName(spaceStory.getTitle());
                    }
                } else if (articleType.equals("AT0006")) {
                    Book book = bookService.get(xeBanner.getArticleId());
                    if (book != null){
                        if (!book.getState().equals(StateEnum.ENABLE.getCode())){
                            xeBanners.remove(xeBanner);
                            continue;
                        }
                        xeBanner.setArticleName(book.getSubject());
                    }
                } else if (articleType.equals("AT0008")) {
                    SharingLesson sharingLesson = sharingLessonService.get(Integer.parseInt(xeBanner.getArticleId().toString()));
                    if (sharingLesson != null){
                        if (!sharingLesson.getState().equals(StateEnum.ENABLE.getCode())){
                            xeBanners.remove(xeBanner);
                            continue;
                        }
                        xeBanner.setArticleName(sharingLesson.getTitle());
                    }
                } else if (articleType.equals("AT0009")) {
                    StudyRoom studyRoom = studyRoomService.get(Integer.parseInt(xeBanner.getArticleId().toString()));
                    if (studyRoom != null){
                        if (!studyRoom.getState().equals(StateEnum.ENABLE.getCode())){
                            xeBanners.remove(xeBanner);
                            continue;
                        }
                        xeBanner.setArticleName(studyRoom.getTitle());
                        xeBanner.setArticleStatus(studyRoom.getSrStatusCode());
                    }
                }
            }

            if (!StringUtility.isEmpty(xeBanner.getBnUrl())){
                List<File> files = fileService.getByRelationId(xeBanner.getBnUrl());
                if (files.size() > 0){
                    xeBanner.setBnUrl(cloudStorageService.generatePresignedUrl(files.get(0).getUrl()));
                } else {
                    xeBanner.setBnUrl("");
                }
            }
            if (!xeBanner.getBnLink().contains("http")){
                if (StringUtility.isNotEmpty(xeBanner.getBnLink())){
                    xeBanner.setBnLink(cloudStorageService.generatePresignedUrl(xeBanner.getBnLink()));
                }
            }
        }

        return new ResponseBean(false,"success", null, xeBanners);
    }
}
