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

@Api("????????????")
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
    *@Description //TODO ????????????id??????????????????,????????????,????????????????????????
    *@Param [params]
    *@return xin.xiaoer.modules.mobile.bean.ResponseBean
    **/
    @RequestMapping(value = "/spaceNews", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation("??????????????????")
    public ResponseBean spaceNews(@RequestParam Map<String, Object> params) {
        //??????????????????

//        String requestPageCount = request.getParameter("pageCount");
//        String pageCount = params.get("pageCount").toString();
//        String curPageNum = params.get("curPageNum").toString();
//        String userId = params.get("userId").toString();
//        if (params.get("spaceId")==null){
//            return new ResponseBean(false,ResponseBean.FAILED,"????????????","????????????",null);
//        }
//        String spaceId = params.get("spaceId").toString();//??????id
//        if (StringUtility.isEmpty(spaceId)) {
//            params.put("spaceId", 0);
//        }

        if (params.get("spaceId")==null || StringUtils.isBlank(params.get("spaceId").toString())){
            params.put("spaceId",0);
        }
        params.put("limit", "10");//???????????????
        params.put("page", "1");
        params.put("state", "1");
        String format = new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis() - (604800L * 1000)));//????????????????????????
        params.put("date", format);//????????????????????????
        Query query = new Query(params);//??????id,??????id,????????????

        List<HeadlineListItem> headlineListItems = headlineService.getIntervalNews(query);
        if (headlineListItems.size()<10){//????????????????????????
            query.remove("date");//??????????????????
            query.put("left", new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis() - (2678400L * 1000))));//??????????????????????????????????????????
            query.put("right",format);
            Integer s = 10 - headlineListItems.size();
            query.put("limit",s);//???????????????????????????
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
    *@Description //TODO ?????????????????????id
    *@Param []
    *@return xin.xiaoer.modules.mobile.bean.ResponseBean
    **/
    @PostMapping(value = "/newsFlash")
    @ApiOperation(value = "????????????", notes = "????????????")
    @ResponseBody
    public ResponseBean newsFlash() {
        //??????????????????

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
    *@Description //TODO ??????????????????
    *@Param []
    *@return xin.xiaoer.modules.mobile.bean.ResponseBean
    **/
    @GetMapping(value = "/recommendedNews")
    @ApiOperation(value = "??????????????????", notes = "??????????????????")
    @ResponseBody
    public ResponseBean recommendedNews() {
        //??????????????????

        Map<String, Object> params = new HashMap<>();
        params.put("limit", "10");
        params.put("page", "1");
        params.put("state", "1");
//        params.put("date", new SimpleDateFormat("yyyy-MM-dd").format(new Date(System.currentTimeMillis() - (604800L * 1000))));//????????????????????????
        Query query = new Query(params);

//        List<HeadlineListItem> headlineListItems = headlineService.queryListData(query);//?????????????????????
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
    *@Description //TODO ??????????????????,?????????????????????????????????
    *@Param [headLineId, userId]
    *@return xin.xiaoer.modules.mobile.bean.ResponseBean
    **/
    @GetMapping(value = "/detail/{headLineId}")
    @ApiOperation("????????????")
    @ResponseBody
    public ResponseBean detail(@PathVariable("headLineId") @ApiParam(name = "headLineId", value = "??????id", required = true) Integer headLineId,
                    @ApiParam(name = "userId", value = "??????id") Long userId) {
        //??????????????????

        SpaceHeadline spaceHeadline = headlineService.get(headLineId);//????????????id???????????????
        if (spaceHeadline==null||!"1".equals(spaceHeadline.getState())) {//?????????????????????
            return new ResponseBean(false,ResponseBean.FAILED,"????????????","????????????",null);
        }
        spaceHeadline.setReadCount(spaceHeadline.getReadCount() + 1);//????????????+1
        headlineService.update(spaceHeadline);//??????????????????
        SysUser sysUser = sysUserService.queryObject(spaceHeadline.getCreateBy());//??????????????????????????????
        if (sysUser != null) {
            if (StringUtility.isEmpty(sysUser.getNickname())) {
                spaceHeadline.setCreateUser(spaceHeadline.getAuthorName());//?????????????????????????????????????????????
            } else {
                spaceHeadline.setCreateUser(sysUser.getNickname());//???????????????????????????
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
    *@Description //TODO ???????????????
    *@Param [params]
    *@return xin.xiaoer.modules.mobile.bean.ResponseBean
    **/
    @RequestMapping(value = "/banner", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    @ApiOperation("???????????????")
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
