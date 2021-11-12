package xin.xiaoer.modules.website.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import xin.xiaoer.common.baiduTts.DemoException;
import xin.xiaoer.common.baiduTts.TtsUtil;
import xin.xiaoer.common.oss.CloudStorageService;
import xin.xiaoer.common.oss.OSSFactory;
import xin.xiaoer.common.utils.PageUtils;
import xin.xiaoer.common.utils.Query;
import xin.xiaoer.common.utils.YunXinUtil;
import xin.xiaoer.entity.Article;
import xin.xiaoer.entity.File;
import xin.xiaoer.entity.SysUser;
import xin.xiaoer.modules.activity.service.ActivityService;
import xin.xiaoer.modules.activityreport.entity.ActivityReport;
import xin.xiaoer.modules.activityreport.service.ActivityReportService;
import xin.xiaoer.modules.book.entity.BookChapter;
import xin.xiaoer.modules.book.service.BookChapterService;
import xin.xiaoer.modules.book.service.BookService;
import xin.xiaoer.modules.chat.entity.ChatUser;
import xin.xiaoer.modules.chat.service.ChatUserService;
import xin.xiaoer.modules.classroom.entity.SharingLesson;
import xin.xiaoer.modules.classroom.service.SharingLessonService;
import xin.xiaoer.modules.favourite.entity.Favourite;
import xin.xiaoer.modules.favourite.service.FavouriteService;
import xin.xiaoer.modules.like.entity.Like;
import xin.xiaoer.modules.like.service.LikeService;
import xin.xiaoer.modules.mobile.bean.ResponseBean;
import xin.xiaoer.modules.review.entity.Review;
import xin.xiaoer.modules.review.service.ReviewService;
import xin.xiaoer.modules.setting.entity.Integral;
import xin.xiaoer.modules.setting.service.IntegralService;
import xin.xiaoer.service.FileService;
import xin.xiaoer.service.OSSCallBackService;
import xin.xiaoer.service.SysUserService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *
 *
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-10-28 22:08:28
 */
@RestController
@RequestMapping("website/Public")
public class WebAPIPubliclController {

    @Autowired
    private ReviewService reviewService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private LikeService likeService;
    @Autowired
    private FavouriteService favouriteService;
    @Autowired
    private BookChapterService bookChapterService;
    @Autowired
    private BookService bookService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private ChatUserService chatUserService;
    @Autowired
    private ActivityReportService activityReportService;
    @Autowired
    private IntegralService integralService;
    @Autowired
    private SharingLessonService sharingLessonService;
    @Autowired
    private FileService fileService;
    @Autowired
    private OSSCallBackService ossCallBackService;

    @Value("${oss.seed}")
    private String OSSSeed;
    @Value("${oss.uid}")
    private String OSSUid;


    //评论
    @RequestMapping("/getreviewlist")
    public ResponseBean reviewlistData(@RequestParam Map<String, Object> params){
        String page =  params.get("page").toString();
        String limit =  params.get("limit").toString();
        String articleId =  params.get("articleId").toString();
        String articleTypeCode =  params.get("articleTypeCode").toString();
        params.put("page",page);
        params.put("limit",limit);
        params.put("articleId",articleId);
        params.put("articleTypeCode",articleTypeCode);
        Query query = new Query(params);
        List<Map> reviewList = reviewService.getreviewList(query);
        CloudStorageService cloudStorageService = OSSFactory.build();
        for (Map map : reviewList) {
            if (map.get("avatar") != null) {
                List<File> avatar = fileService.getByRelationId(map.get("avatar").toString());
                if (avatar.size() > 0) {
                    map.put("avatar", cloudStorageService.generatePresignedUrl(avatar.get(0).getUrl()));
                }
            }
        }
        int total = reviewService.getCount(query);
        PageUtils pageUtil = new PageUtils(reviewList, total, query.getLimit(), query.getPage());
        return new ResponseBean(false, "success", null, pageUtil);
    }

    /**
     * @Author XiaoDong
     * @Description TODO 显示点赞数，评论数，收藏数
     * @Param [params]
     * @return xin.xiaoer.modules.mobile.bean.ResponseBean
     **/
    @RequestMapping("/publicCount")
    @ApiOperation("显示点赞数，评论数，收藏数")
    public ResponseBean publicCount(@RequestParam Map<String,Object> params){
        if (params.get("articleType")==null||params.get("articleId")==null|| StringUtils.isBlank(params.get("articleType").toString())||StringUtils.isBlank(params.get("articleId").toString())){
            return new ResponseBean(false,ResponseBean.FAILED,null,"参数异常",null);
        }
        Map count=activityService.getPublicCount(params);
        return new ResponseBean(false,ResponseBean.SUCCESS,null,count);
    }

    @RequestMapping("/doreview")
    public ResponseBean doreview(@RequestParam Map<String, Object> params){
        String userId =  params.get("userId").toString();
        String content =  params.get("content").toString();
        String articleId =  params.get("articleId").toString();
        String articleTypeCode =  params.get("articleTypeCode").toString();
        Review review = new Review();
        review.setState("1");
        review.setArticleId(Long.parseLong(articleId));
        review.setArticleTypeCode(articleTypeCode);
        review.setContent(content);
        review.setUserId(Long.parseLong(userId));
        reviewService.save(review);
        return new ResponseBean(false, "success", null, null);
    }

    @RequestMapping("/deletereview")
    public ResponseBean deletereview(@RequestParam Map<String, Object> params){
        String userId =  params.get("userId").toString();
        String articleId =  params.get("articleId").toString();
        String articleType =  params.get("articleType").toString();
        reviewService.deleteByArticleAndUser(articleType,Long.parseLong(articleId),Long.parseLong(userId));
        return new ResponseBean(false, "success", null, null);
    }

    @RequestMapping("/dolike")
    public ResponseBean dolike(@RequestParam Map<String, Object> params){
//        String userId =  params.get("userId").toString();
//        String articleId =  params.get("articleId").toString();
//        String articleTypeCode =  params.get("articleTypeCode").toString();
//        Like like = new Like();
//        like.setArticleId(Long.parseLong(articleId));
//        like.setArticleTypeCode(articleTypeCode);
//        String articleType =  params.get("articleType").toString();
//        Like likeList = likeService.getByArticleAndUser(articleType,Long.parseLong(articleId),Long.parseLong(userId));
//        if (likeList != null){
//            likeService.deleteByArticleAndUser(articleType,Long.parseLong(articleId),Long.parseLong(userId));
//            return new ResponseBean(false, ResponseBean.FAILED, "该用户已取消点赞", null);
//        }
////        Like like = new Like();
////        like.setArticleId(Long.parseLong(articleId));
////        like.setArticleTypeCode(articleType);
//        like.setUserId(Long.parseLong(userId));
//        likeService.save(like);
//        return new ResponseBean(false, "success", null, null);
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

    @RequestMapping("/dofavourite")
    public ResponseBean dofavourite(@RequestParam Map<String, Object> params){
        String userId =  params.get("userId").toString();
        String articleId =  params.get("articleId").toString();
        String articleTypeCode =  params.get("articleTypeCode").toString();
        Favourite favourite = new Favourite();
        favourite.setState("1");
        favourite.setArticleId(Long.parseLong(articleId));
        favourite.setArticleTypeCode(articleTypeCode);
        String articleType =  params.get("articleType").toString();
        Favourite favouritelist = favouriteService.getByArticleAndUser(articleType,Long.parseLong(articleId),Long.parseLong(userId));
        if (favouritelist!=null){
            favouriteService.deleteByArticleAndUser(articleType,Long.parseLong(articleId),Long.parseLong(userId));
            return new ResponseBean(false, ResponseBean.FAILED, "该用户已取消收藏", null);
        }
//        Favourite favourite = new Favourite();
//        favourite.setState("1");
//        favourite.setArticleId(Long.parseLong(articleId));
//        favourite.setArticleTypeCode(articleType);
        favourite.setUserId(Long.parseLong(userId));
        favouriteService.save(favourite);
        return new ResponseBean(false, "success", null, null);
    }

    @RequestMapping("/ttsforbook/{chapterId}")
    public ResponseBean ttsforbook(@PathVariable("chapterId") Long chapterId) throws IOException, DemoException {
        BookChapter bookChapter = bookChapterService.get(chapterId);
        if(bookChapter==null){
            return new ResponseBean(false, "找不到该章节内容", null, null);
        }
       /* int chapterCount = bookChapterService.getCountByBookFile(bookChapter.getBookFile());
            Book book = bookService.get(bookChapter.getBookId());*/
           String chapterIndex = bookChapter.getChapterIndex();
           String title = bookChapter.getTitle();
           String content = bookChapter.getContent();
           String book = chapterIndex+"。"+title+"。"+content;
           int booklength = book.length();
           int length = 1024;
           int begin = 0;
           int stop = 1024;
           while (length<booklength){
               TtsUtil.read(book.substring(begin,stop));
               booklength-=1024;
               begin+=1024;
               stop+=1024;
           }
        if (booklength<length){
            TtsUtil.read(book.substring(begin));
        }
        return new ResponseBean(false, "success", null, bookChapter);
    }

    /**
     * @return xin.xiaoer.modules.mobile.bean.ResponseBean
     * @Description TODO 获取用户accid
     * @Param [userId]
     * @Author XiaoDong
     **/
    @GetMapping("/getAccid/{userId}")
    public ResponseBean getAccid(@PathVariable("userId") Long userId){
        SysUser sysUser = sysUserService.queryObject(userId);
        ChatUser errorChatUser = new ChatUser();
        errorChatUser.setAccid("-1");
        errorChatUser.setUserId(-1L);
        if (sysUser == null || sysUser.getStatus() != 1){
            return new ResponseBean(false,ResponseBean.SUCCESS,null,"无此用户或已被禁！",errorChatUser);
        }
        ChatUser chatUser = chatUserService.get(sysUser.getUserId());
        if (chatUser == null || !"1".equals(chatUser.getState())){
            return new ResponseBean(false,ResponseBean.SUCCESS,null,"无此信息或已停用！",errorChatUser);
        }
        return new ResponseBean(false,ResponseBean.SUCCESS,null,chatUser);
    }

    /**
     * @return void
     * @Description TODO oss违规检测回调
     * @Param [params]
     * @Author XiaoDong
     **/
    @RequestMapping("/OSSCallBack")
    public void OSSCallBack(@RequestParam Map<String,Object> params){
        String checksum = params.get("checksum").toString();//由用户uid + seed + content拼成字符串,并有sha256hash生成的
        if (!new Sha256Hash(OSSUid+OSSSeed+params.get("content").toString()).toHex().equals(checksum)){
            return;
        }
        JSONObject content = JSON.parseObject(params.get("content").toString());
        String fileName = content.get("object").toString();//文件名
        Integer resoureStatus =  content.getJSONObject("auditResult").getObject("resoureStatus",Integer.class);//审核后文件状态 0已删除，1已冻结，2可以访问
        String suggestion = content.getJSONObject("auditResult").getString("suggestion");//审核时给的建议，block违规，pass正常
        String stock = content.getString("stock");//是否是存量对象，true表示是存量，false表示是增量。
        String freezed = content.getString("freezed");//是否被冻结，true表示冻结，false表示未冻结
        String url = null;
        if (content.getJSONObject("scanResult").getString("url") != null){
            url = content.getJSONObject("scanResult").getString("url");
        }
        params.clear();
        params.put("fileName",fileName);
        params.put("resoureStatus",resoureStatus);
        params.put("suggestion",suggestion);
        params.put("stock",stock);
        params.put("freezed",freezed);
        params.put("url",url);
        ossCallBackService.save(params);
    }
}