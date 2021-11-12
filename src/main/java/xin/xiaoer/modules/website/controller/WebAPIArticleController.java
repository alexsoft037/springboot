package xin.xiaoer.modules.website.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xin.xiaoer.common.enumresource.StateEnum;
import xin.xiaoer.common.oss.CloudStorageService;
import xin.xiaoer.common.oss.OSSFactory;
import xin.xiaoer.common.utils.*;
import xin.xiaoer.entity.Article;
import xin.xiaoer.modules.activityreport.entity.ActivityReport;
import xin.xiaoer.modules.activityreport.service.ActivityReportService;
import xin.xiaoer.modules.classroom.entity.SharingLesson;
import xin.xiaoer.modules.classroom.service.SharingLessonService;
import xin.xiaoer.modules.comment.entity.Comment;
import xin.xiaoer.modules.comment.service.CommentService;
import xin.xiaoer.modules.favourite.entity.Favourite;
import xin.xiaoer.modules.favourite.service.FavouriteService;
import xin.xiaoer.modules.like.entity.Like;
import xin.xiaoer.modules.like.service.LikeService;
import xin.xiaoer.modules.mobile.bean.ResponseBean;
import xin.xiaoer.modules.review.entity.Review;
import xin.xiaoer.modules.review.service.ReviewService;
import xin.xiaoer.modules.setting.entity.Integral;
import xin.xiaoer.modules.setting.service.IntegralService;
import xin.xiaoer.modules.share.entity.Share;
import xin.xiaoer.modules.share.service.ShareService;
import xin.xiaoer.service.SysUserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("website/")
@Api(value = "/WebAPIArticleController",description = "文章操作")
public class WebAPIArticleController {
    @Autowired
    private LikeService likeService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private FavouriteService favouriteService;

    @Autowired
    private ShareService shareService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private IntegralService integralService;

    @Autowired
    private ActivityReportService activityReportService;

    @Autowired
    private SharingLessonService sharingLessonService;

    @RequestMapping(value = "/like",method = RequestMethod.POST)
    @ApiOperation(value = "点赞")
    public ResponseBean like(@RequestParam Map<String, Object> params){

        String articleTypeCode = params.get("articleType").toString();
        String articleId = params.get("articleId").toString();
        String userId = params.get("userId").toString();
        Like like = likeService.getByArticleAndUser(articleTypeCode, Long.parseLong(articleId), Long.parseLong(userId));

        boolean likeResult;
        if (like != null) {
            likeService.delete(like.getLikeId());
            likeResult = false;
        } else {
            like = new Like();
            like.setArticleId(Long.parseLong(articleId));
            like.setArticleTypeCode(articleTypeCode);
            like.setUserId(Long.parseLong(userId));
            likeService.save(like);
            likeResult =true;

            YunXinUtil yunXinUtil = new YunXinUtil();
            if (articleTypeCode.equals(Article.ACTIVITY_REPORT)) {
                ActivityReport activityReport = activityReportService.get(Integer.parseInt(articleId));
                int likeCount = likeService.getCountByCodeAndId(articleTypeCode, Long.parseLong(articleId));
                if (likeCount != 0 && likeCount % 10 == 0) {
                    Map<String, Object> searchParams = new HashMap<>();
                    searchParams.put("articleTypeCode", Article.REPORT_LIKE);
                    searchParams.put("articleId", articleId);
                    Double totalIntegral = integralService.getTotalByUserAndArticle(searchParams);


                    Integral integral = new Integral();
                    integral.setUserId(activityReport.getCreateBy());
                    integral.setArticleId(Long.parseLong(articleId));
                    integral.setArticleTypeCode(Article.REPORT_LIKE);
                    integral.setIntegral("1");
                    integral.setTitle("点赞"+ activityReport.getTitle());
                    if (totalIntegral == null){
                        integralService.save(integral);
                        try {
                            yunXinUtil.sendSinglePush(integral.getUserId(), "获得积分"+integral.getTitle());
                        } catch (Exception e){
                        }
                    } else  if (totalIntegral < 5) {
                        if (likeCount/10 == totalIntegral + 1){
                            integralService.save(integral);
                            try {
                                yunXinUtil.sendSinglePush(integral.getUserId(), "获得积分"+integral.getTitle());
                            } catch (Exception e){
                            }
                        }
                    }
                }
            } else if (articleTypeCode.equals(Article.SHARE_LESSON)) {
                SharingLesson sharingLesson = sharingLessonService.get(Integer.parseInt(articleId));
                Integral CheckIntegral = integralService.getByUserAndArticle(Long.parseLong(userId), articleTypeCode, Long.parseLong(articleId));
                if (CheckIntegral == null) {
                    Integral integral = new Integral();
                    integral.setUserId(sharingLesson.getCreateBy());
                    integral.setArticleId(Long.parseLong(articleId));
                    integral.setArticleTypeCode(Article.SHARE_LIKE);
                    integral.setIntegral("0.01");
                    integral.setTitle("视频点赞" + sharingLesson.getTitle());
                    integralService.save(integral);
                    try {
                        yunXinUtil.sendSinglePush(integral.getUserId(), "获得积分"+integral.getTitle());
                    } catch (Exception e) {
                    }
                }
            }
        }

        return new ResponseBean(false,"success", null, likeResult);
    }

    @RequestMapping(value = "/share",method = RequestMethod.POST)
    public ResponseBean share(@RequestParam Map<String, Object> params){

        String articleTypeCode = params.get("articleType").toString();
        String articleId = params.get("articleId").toString();
        String userId = params.get("userId").toString();

        Share share = new Share();
        share.setArticleId(Long.parseLong(articleId));
        share.setArticleTypeCode(articleTypeCode);
        share.setUserId(Long.parseLong(userId));

        shareService.save(share);

        return new ResponseBean(false,"success", null, null);
    }

    @RequestMapping("/saveComment")
    public ResponseBean saveComment(@RequestParam Map<String, Object> params) throws Exception {

        String articleTypeCode = params.get("articleType").toString();
        String articleId = params.get("articleId").toString();
        String userId = params.get("userId").toString();
        String content = params.get("content").toString();

//        Comment comment = commentService.getByArticleAndUser(articleTypeCode, Long.parseLong(articleId), Long.parseLong(userId));
//        if (comment != null){
//            comment.setContent(content);
//            comment.setUpdateBy(Long.parseLong(userId));
//            comment.setState("1");
//            commentService.update(comment);
//        } else {
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setArticleId(Long.parseLong(articleId));
        comment.setArticleTypeCode(articleTypeCode);
        comment.setUserId(Long.parseLong(userId));
        comment.setCreateBy(Long.parseLong(userId));
        comment.setUpdateBy(Long.parseLong(userId));
        comment.setState("1");
        comment.setAvatar(sysUserService.getAvatar(comment.getAvatar()));
        commentService.save(comment);
//        }

        return new ResponseBean(false,"success", null, comment);
    }

    //
    @RequestMapping("/listComment")
    public ResponseBean listComment(@RequestParam Map<String, Object> params) throws Exception {

        String articleTypeCode = params.get("articleType").toString();
        String articleId = params.get("articleId").toString();
        String pageCount = params.get("pageCount").toString();
        String curPageNum = params.get("curPageNum").toString();

        CloudStorageService cloudStorageService = OSSFactory.build();

        Map<String, Object> search = new HashMap<>();
        search.put("limit", pageCount);
        search.put("page", curPageNum);
        search.put("articleId", articleId);
        search.put("state", "1");
        search.put("articleTypeCode", articleTypeCode);
        Query query = new Query(search);

        List<Comment> commentList = commentService.getList(query);
        for (Comment comment: commentList){
            if (!StringUtility.isEmpty(comment.getAvatar())){
                comment.setAvatar(sysUserService.getAvatar(comment.getAvatar()));
            }
        }

        int total = commentService.getCount(query);

        PageUtils pageUtil = new PageUtils(commentList, total, query.getLimit(), query.getPage());

        return new ResponseBean(false,"success", null, pageUtil);
    }

    @RequestMapping("/saveReview")
    public ResponseBean saveReview(@RequestParam Map<String, Object> params) throws Exception {

        String articleTypeCode = params.get("articleType").toString();
        String articleId = params.get("articleId").toString();
        String userId = params.get("userId").toString();
        String subject = "";
        if (params.get("subject") != null){
            subject = params.get("subject").toString();
        }
        String content = params.get("content").toString();

//        Review review = reviewService.getByArticleAndUser(articleTypeCode, Long.parseLong(articleId), Long.parseLong(userId));
//
//        if (review != null){
//            review.setContent(content);
//            review.setSubject(subject);
//            reviewService.update(review);
//        } else {
        Review review = new Review();
        review.setArticleId(Long.parseLong(articleId));
        review.setArticleTypeCode(articleTypeCode);
        review.setSubject(subject);
        review.setContent(content);
        review.setState("1");
        review.setUserId(Long.parseLong(userId));
        review.setAvatar(sysUserService.getAvatar(review.getAvatar()));
        reviewService.save(review);
        YunXinUtil yunXinUtil = new YunXinUtil();
        if (articleTypeCode.equals(Article.ACTIVITY_REPORT)){
            ActivityReport activityReport = activityReportService.get(Integer.parseInt(articleId));
            Map<String, Object> searchParams1 = new HashMap<>();
            searchParams1.put("articleTypeCode", Article.ACTIVITY_REPORT);
            searchParams1.put("articleId", articleId);
            searchParams1.put("groupBy", "userId");
            int reviewCount = reviewService.getCountByArticle(searchParams1);
            if (reviewCount != 0 && reviewCount % 5 == 0) {
                Map<String, Object> searchParams2 = new HashMap<>();
                searchParams2.put("articleTypeCode", Article.REPORT_REVIEW);
                searchParams2.put("articleId", articleId);
                Double totalIntegral = integralService.getTotalByUserAndArticle(searchParams2);

                Integral integral = new Integral();
                integral.setUserId(activityReport.getCreateBy());
                integral.setArticleId(Long.parseLong(articleId));
                integral.setArticleTypeCode(Article.REPORT_REVIEW);
                integral.setIntegral("1");
                integral.setTitle("评论" + activityReport.getTitle());
                if (totalIntegral == null){
                    integralService.save(integral);
                    try {
                        yunXinUtil.sendSinglePush(integral.getUserId(), "获得积分"+integral.getTitle());
                    } catch (Exception e){
                    }
                } else  if (totalIntegral < 5) {
                    if (reviewCount/5 == totalIntegral + 1){
                        integralService.save(integral);
                        try {
                            yunXinUtil.sendSinglePush(integral.getUserId(), "获得积分"+integral.getTitle());
                        } catch (Exception e){
                        }
                    }
                }
            }
        } else if (articleTypeCode.equals(Article.SHARE_LESSON)) {
            SharingLesson sharingLesson = sharingLessonService.get(Integer.parseInt(articleId));
            Integral integral = new Integral();
            integral.setUserId(sharingLesson.getCreateBy());
            integral.setArticleId(Long.parseLong(articleId));
            integral.setArticleTypeCode(Article.SHARE_REVIEW);
            integral.setIntegral("0.01");
            integral.setTitle("视频评论" + sharingLesson.getTitle());
            integralService.save(integral);
            try {
                yunXinUtil.sendSinglePush(integral.getUserId(), "获得积分"+integral.getTitle());
            } catch (Exception e) {
            }
        }
//        }

        return new ResponseBean(false,"success", null, review);
    }

    //获取评论列表信息
    @RequestMapping(value = "/listReview",method = RequestMethod.POST)
    public ResponseBean listReview(@RequestParam Map<String, Object> params) throws Exception {

        String articleTypeCode = params.get("articleType").toString();
        String articleId = params.get("articleId").toString();
        String pageCount = params.get("pageCount").toString();
        String curPageNum = params.get("curPageNum").toString();

        Map<String, Object> search = new HashMap<>();
        search.put("limit", pageCount);
        search.put("page", curPageNum);
        search.put("articleId", articleId);
        search.put("state", "1");
        search.put("articleTypeCode", articleTypeCode);
        Query query = new Query(search);

        List<Review> reviewList = reviewService.getList(query);
        for (Review review: reviewList){
            if (!StringUtility.isEmpty(review.getAvatar())){
                review.setAvatar(sysUserService.getAvatar(review.getAvatar()));
            }
        }

        int total = reviewService.getCount(query);

        PageUtils pageUtil = new PageUtils(reviewList, total, query.getLimit(), query.getPage());

        return new ResponseBean(false,"success", null, pageUtil);
    }

    @RequestMapping("/favourite")
    public ResponseBean favourite(@RequestParam Map<String, Object> params){

        String articleTypeCode = params.get("articleType").toString();
        String articleId = params.get("articleId").toString();
        String userId = params.get("userId").toString();
        Favourite favourite = favouriteService.getByArticleAndUser(articleTypeCode, Long.parseLong(articleId), Long.parseLong(userId));

        boolean isFavourite;
        if (favourite != null){
            favouriteService.delete(favourite.getFvId());
            isFavourite = false;
        } else {
            favourite = new Favourite();
            favourite.setArticleId(Long.parseLong(articleId));
            favourite.setArticleTypeCode(articleTypeCode);
            favourite.setUserId(Long.parseLong(userId));
            favourite.setState(StateEnum.ENABLE.getCode());
            favouriteService.save(favourite);
            isFavourite =true;
        }

        Map<String, Object> result = new HashMap<>();
        result.put("isFavourite", isFavourite);
        return new ResponseBean(false,"success", null, result);
    }
}
