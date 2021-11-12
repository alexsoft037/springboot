package xin.xiaoer.controller;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xin.xiaoer.common.enumresource.StateEnum;
import xin.xiaoer.common.exception.MyException;
import xin.xiaoer.common.shiro.ShiroUtils;
import xin.xiaoer.common.utils.*;
import xin.xiaoer.entity.*;
import xin.xiaoer.modules.activity.entity.Activity;
import xin.xiaoer.modules.activity.service.ActivityService;
import xin.xiaoer.modules.activityreport.entity.ActivityReport;
import xin.xiaoer.modules.activityreport.service.ActivityReportService;
import xin.xiaoer.modules.book.entity.Book;
import xin.xiaoer.modules.book.entity.BookChapter;
import xin.xiaoer.modules.book.service.BookChapterService;
import xin.xiaoer.modules.book.service.BookService;
import xin.xiaoer.modules.classroom.entity.SharingLesson;
import xin.xiaoer.modules.classroom.entity.StudyRoom;
import xin.xiaoer.modules.classroom.service.SharingLessonService;
import xin.xiaoer.modules.classroom.service.StudyRoomService;
import xin.xiaoer.modules.donatespace.entity.DonateSpace;
import xin.xiaoer.modules.donatespace.service.DonateSpaceService;
import xin.xiaoer.modules.spacehaedline.entity.SpaceHeadline;
import xin.xiaoer.modules.spacehaedline.service.SpaceHeadlineService;
import xin.xiaoer.modules.spaceshow.entity.SpaceShow;
import xin.xiaoer.modules.spaceshow.service.SpaceShowService;
import xin.xiaoer.modules.story.entity.SpaceStory;
import xin.xiaoer.modules.story.service.SpaceStoryService;
import xin.xiaoer.modules.xebanner.entity.BannerArticle;
import xin.xiaoer.service.CommparaService;
import xin.xiaoer.service.FileService;
import xin.xiaoer.service.SysOssService;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by 陈熠
 * 2017/7/19.
 */
@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private CommparaService commparaService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private ActivityReportService activityReportService;

    @Autowired
    private DonateSpaceService donateSpaceService;

    @Autowired
    private SpaceHeadlineService spaceHeadlineService;

    @Autowired
    private SpaceStoryService spaceStoryService;

    @Autowired
    private BookService bookService;

    @Autowired
    private SharingLessonService sharingLessonService;

    @Autowired
    private StudyRoomService studyRoomService;

    @Autowired
    private SpaceShowService spaceShowService;
    /**
     * 列表数据
     */
    @ResponseBody
    @RequestMapping("/autocomplete")
    public R autocomplete(@RequestParam Map<String, Object> params){

        String articleType = params.get("articleType").toString();
        params.put("state", 1);
        List<BannerArticle> articles = new ArrayList<>();
//        SysUser admin = ShiroUtils.getUserEntity();
//        if (admin.getUserId() != 1L){
//            params.put("spaceId", admin.getSpaceId());
//        }
        if (articleType.equals("AT0001")) {
            List<DonateSpace> donateSpaces = donateSpaceService.getList(params);
            for (DonateSpace donateSpace: donateSpaces){
                BannerArticle article = new BannerArticle();
                article.setArticleType(articleType);
                article.setArticleId(donateSpace.getItemId().toString());
                article.setArticleTitle(donateSpace.getTitle());
                articles.add(article);
            }
        } else if (articleType.equals("AT0002")) {
            List<SpaceHeadline> spaceHeadlines = spaceHeadlineService.getList(params);
            for (SpaceHeadline spaceHeadline: spaceHeadlines){
                BannerArticle article = new BannerArticle();
                article.setArticleType(articleType);
                article.setArticleId(spaceHeadline.getHeadlineId().toString());
                article.setArticleTitle(spaceHeadline.getTitle());
                articles.add(article);
            }
        } else if (articleType.equals("AT0003")) {
            List<ActivityReport> activityReports = activityReportService.getList(params);
            for (ActivityReport activityReport: activityReports){
                BannerArticle article = new BannerArticle();
                article.setArticleType(articleType);
                article.setArticleId(activityReport.getReportId().toString());
                article.setArticleTitle(activityReport.getTitle());
                articles.add(article);
            }
        } else if (articleType.equals("AT0004")) {
            List<Activity> activities = activityService.getList(params);
            for (Activity activity: activities){
                BannerArticle article = new BannerArticle();
                article.setArticleType(articleType);
                article.setArticleId(activity.getActivityId().toString());
                article.setArticleTitle(activity.getTitle());
                articles.add(article);
            }
        } else if (articleType.equals("AT0005")) {
            List<SpaceStory> spaceStories = spaceStoryService.getList(params);
            for (SpaceStory SpaceStory: spaceStories){
                BannerArticle article = new BannerArticle();
                article.setArticleType(articleType);
                article.setArticleId(SpaceStory.getStoryId().toString());
                article.setArticleTitle(SpaceStory.getTitle());
                articles.add(article);
            }
        } else if (articleType.equals("AT0006")) {
            List<Book> books = bookService.getList(params);
            for (Book book: books){
                BannerArticle article = new BannerArticle();
                article.setArticleType(articleType);
                article.setArticleId(book.getBookId().toString());
                article.setArticleTitle(book.getSubject());
                articles.add(article);
            }
        } else if (articleType.equals("AT0008")) {
            List<SharingLesson> sharingLessons = sharingLessonService.getList(params);
            for (SharingLesson sharingLesson: sharingLessons){
                BannerArticle article = new BannerArticle();
                article.setArticleType(articleType);
                article.setArticleId(sharingLesson.getLessonId().toString());
                article.setArticleTitle(sharingLesson.getTitle());
                articles.add(article);
            }
        } else if (articleType.equals("AT0009")) {
            List<StudyRoom> studyRooms = studyRoomService.getList(params);
            for (StudyRoom studyRoom: studyRooms){
                BannerArticle article = new BannerArticle();
                article.setArticleType(articleType);
                article.setArticleId(studyRoom.getRoomId().toString());
                article.setArticleTitle(studyRoom.getTitle());
                articles.add(article);
            }
        } else if (articleType.equals("AT0010")) {
            List<SpaceShow> spaceShows = spaceShowService.getList(params);
            for (SpaceShow spaceShow: spaceShows){
                BannerArticle article = new BannerArticle();
                article.setArticleType(articleType);
                article.setArticleId(spaceShow.getShowId().toString());
                article.setArticleTitle(spaceShow.getTitle());
                articles.add(article);
            }
        }

        return R.ok().put("data", articles);
    }

}
