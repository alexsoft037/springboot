package xin.xiaoer.modules.advert.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.swagger.models.auth.In;
import xin.xiaoer.common.enumresource.StateEnum;
import xin.xiaoer.common.exception.MyException;
import xin.xiaoer.common.log.SysLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import javax.servlet.http.HttpServletRequest;

import xin.xiaoer.common.oss.CloudStorageService;
import xin.xiaoer.common.oss.OSSFactory;
import xin.xiaoer.common.shiro.ShiroUtils;
import xin.xiaoer.common.utils.StringUtility;
import xin.xiaoer.entity.File;
import xin.xiaoer.modules.activity.entity.Activity;
import xin.xiaoer.modules.activity.service.ActivityService;
import xin.xiaoer.modules.activityreport.entity.ActivityReport;
import xin.xiaoer.modules.activityreport.service.ActivityReportService;
import xin.xiaoer.modules.advert.entity.XeAdvert;
import xin.xiaoer.modules.advert.service.XeAdvertService;
import xin.xiaoer.common.utils.PageUtils;
import xin.xiaoer.common.utils.Query;
import xin.xiaoer.common.utils.R;
import xin.xiaoer.modules.book.entity.Book;
import xin.xiaoer.modules.book.service.BookService;
import xin.xiaoer.modules.classroom.entity.SharingLesson;
import xin.xiaoer.modules.classroom.entity.StudyRoom;
import xin.xiaoer.modules.classroom.service.SharingLessonService;
import xin.xiaoer.modules.classroom.service.StudyRoomService;
import xin.xiaoer.modules.donatespace.entity.DonateSpace;
import xin.xiaoer.modules.donatespace.service.DonateSpaceService;
import xin.xiaoer.modules.spacehaedline.entity.SpaceHeadline;
import xin.xiaoer.modules.spacehaedline.service.SpaceHeadlineService;
import xin.xiaoer.modules.story.entity.SpaceStory;
import xin.xiaoer.modules.story.service.SpaceStoryService;
import xin.xiaoer.modules.xebanner.entity.BannerArticle;
import xin.xiaoer.service.FileService;


/**
 * 
 * 
 * @author chenyi
 * @email 228112142@qq.com
 * @date 2018-07-18 11:36:03
 */
@Controller
@RequestMapping("xeadvert")
public class XeAdvertController {
	@Autowired
	private XeAdvertService xeAdvertService;

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
    private FileService fileService;

    @Autowired
    private SharingLessonService sharingLessonService;

    @Autowired
    private StudyRoomService studyRoomService;

    /**
     * 跳转到列表页
     */
    @RequestMapping("/list")
    @RequiresPermissions("xeadvert:list")
    public String list() {
        return "xeadvert/list";
    }
    
	/**
	 * 列表数据
	 */
    @ResponseBody
	@RequestMapping("/listData")
	@RequiresPermissions("xeadvert:list")
	public R listData(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<XeAdvert> xeAdvertList = xeAdvertService.getList(query);
        CloudStorageService cloudStorageService = OSSFactory.build();
		for (XeAdvert xeAdvert: xeAdvertList){
            if (xeAdvert.getArticleId() != null) {
                String articleType = xeAdvert.getArticleType();
                if (articleType == null){

                } else if (articleType.equals("AT0001")) {
                    DonateSpace donateSpace = donateSpaceService.get(xeAdvert.getArticleId());
                    if (donateSpace != null) {
                        xeAdvert.setArticleName(donateSpace.getTitle());
                    }
                } else if (articleType.equals("AT0002")) {
                    SpaceHeadline spaceHeadline = spaceHeadlineService.get(Integer.parseInt(xeAdvert.getArticleId().toString()));
                    if (spaceHeadline != null) {
                        xeAdvert.setArticleName(spaceHeadline.getTitle());
                    }
                } else if (articleType.equals("AT0003")) {
                    ActivityReport activityReport = activityReportService.get(Integer.parseInt(xeAdvert.getArticleId().toString()));
                    if (activityReport != null) {
                        xeAdvert.setArticleName(activityReport.getTitle());
                    }
                } else if (articleType.equals("AT0004")) {
                    Activity activity = activityService.get(Integer.parseInt(xeAdvert.getArticleId().toString()));
                    activity = activityService.checkExpired(activity);
                    if (activity != null) {
                        xeAdvert.setArticleName(activity.getTitle());
                        xeAdvert.setArticleStatus(activity.getActivityStatus());
                    }
                } else if (articleType.equals("AT0005")) {
                    SpaceStory spaceStory = spaceStoryService.get(xeAdvert.getArticleId());
                    if (spaceStory != null) {
                        xeAdvert.setArticleName(spaceStory.getTitle());
                    }
                } else if (articleType.equals("AT0006")) {
                    Book book = bookService.get(xeAdvert.getArticleId());
                    if (book != null) {
                        xeAdvert.setArticleName(book.getSubject());
                    }
                } else if (articleType.equals("AT0008")) {
                    SharingLesson sharingLesson = sharingLessonService.get(Integer.parseInt(xeAdvert.getArticleId().toString()));
                    if (sharingLesson != null) {
                        xeAdvert.setArticleName(sharingLesson.getTitle());
                    }
                } else if (articleType.equals("AT0009")) {
                    StudyRoom studyRoom = studyRoomService.get(Integer.parseInt(xeAdvert.getArticleId().toString()));
                    if (studyRoom != null) {
                        xeAdvert.setArticleName(studyRoom.getTitle());
                        xeAdvert.setArticleStatus(studyRoom.getSrStatusName());
                    }
                }
            }
            if (!StringUtility.isEmpty(xeAdvert.getAdImagePath())){
                List<File> files = fileService.getByRelationId(xeAdvert.getAdImagePath());
                if (files.size() > 0){
                    xeAdvert.setAdImagePath(cloudStorageService.generatePresignedUrl(files.get(0).getUrl()));
                } else {
                    xeAdvert.setAdImagePath("");
                }
            }
        }
		int total = xeAdvertService.getCount(query);
		
		PageUtils pageUtil = new PageUtils(xeAdvertList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}

    /**
     * 跳转到新增页面
     **/
    @RequestMapping("/add")
    @RequiresPermissions("xeadvert:save")
    public String add(){
        return "xeadvert/add";
    }

    /**
     *   跳转到修改页面
     **/
    @RequestMapping("/edit/{id}")
    @RequiresPermissions("xeadvert:update")
    public String edit(Model model, @PathVariable("id") String id){
		XeAdvert xeAdvert = xeAdvertService.get(Integer.parseInt(id));
        String articleType = xeAdvert.getArticleType();

        List<BannerArticle> articles = new ArrayList<>();
        if (articleType == null){

        } else if (articleType.equals("AT0001")) {
            List<DonateSpace> donateSpaces = donateSpaceService.getList(null);
            for (DonateSpace donateSpace : donateSpaces) {
                BannerArticle article = new BannerArticle();
                article.setArticleType(articleType);
                article.setArticleId(donateSpace.getItemId().toString());
                article.setArticleTitle(donateSpace.getTitle());
                articles.add(article);
            }
        } else if (articleType.equals("AT0002")) {
            List<SpaceHeadline> spaceHeadlines = spaceHeadlineService.getList(null);
            for (SpaceHeadline spaceHeadline : spaceHeadlines) {
                BannerArticle article = new BannerArticle();
                article.setArticleType(articleType);
                article.setArticleId(spaceHeadline.getHeadlineId().toString());
                article.setArticleTitle(spaceHeadline.getTitle());
                articles.add(article);
            }
        } else if (articleType.equals("AT0003")) {
            List<ActivityReport> activityReports = activityReportService.getList(null);
            for (ActivityReport activityReport : activityReports) {
                BannerArticle article = new BannerArticle();
                article.setArticleType(articleType);
                article.setArticleId(activityReport.getReportId().toString());
                article.setArticleTitle(activityReport.getTitle());
                articles.add(article);
            }
        } else if (articleType.equals("AT0004")) {
            List<Activity> activities = activityService.getList(null);
            for (Activity activity : activities) {
                BannerArticle article = new BannerArticle();
                article.setArticleType(articleType);
                article.setArticleId(activity.getActivityId().toString());
                article.setArticleTitle(activity.getTitle());
                articles.add(article);
            }
        } else if (articleType.equals("AT0005")) {
            List<SpaceStory> spaceStories = spaceStoryService.getList(null);
            for (SpaceStory SpaceStory : spaceStories) {
                BannerArticle article = new BannerArticle();
                article.setArticleType(articleType);
                article.setArticleId(SpaceStory.getStoryId().toString());
                article.setArticleTitle(SpaceStory.getTitle());
                articles.add(article);
            }
        } else if (articleType.equals("AT0006")) {
            List<Book> books = bookService.getList(null);
            for (Book book : books) {
                BannerArticle article = new BannerArticle();
                article.setArticleType(articleType);
                article.setArticleId(book.getBookId().toString());
                article.setArticleTitle(book.getSubject());
                articles.add(article);
            }
        } else if (articleType.equals("AT0008")) {
            List<SharingLesson> sharingLessons = sharingLessonService.getList(null);
            for (SharingLesson sharingLesson: sharingLessons){
                BannerArticle article = new BannerArticle();
                article.setArticleType(articleType);
                article.setArticleId(sharingLesson.getLessonId().toString());
                article.setArticleTitle(sharingLesson.getTitle());
                articles.add(article);
            }
        } else if (articleType.equals("AT0009")) {
            List<StudyRoom> studyRooms = studyRoomService.getList(null);
            for (StudyRoom studyRoom: studyRooms){
                BannerArticle article = new BannerArticle();
                article.setArticleType(articleType);
                article.setArticleId(studyRoom.getRoomId().toString());
                article.setArticleTitle(studyRoom.getTitle());
                articles.add(article);
            }
        }

        model.addAttribute("model",xeAdvert);
        if (xeAdvert.getArticleId() != null){
            model.addAttribute("articleId",xeAdvert.getArticleId().toString());
        }
        model.addAttribute("articles",articles);
        return "xeadvert/edit";
    }

	/**
	 * 信息
	 */
    @ResponseBody
    @RequestMapping("/info/{id}")
    @RequiresPermissions("xeadvert:info")
    public R info(@PathVariable("id") Integer id){
        XeAdvert xeAdvert = xeAdvertService.get(id);
        return R.ok().put("xeAdvert", xeAdvert);
    }

    /**
	 * 保存
	 */
    @ResponseBody
    @SysLog("保存广告图")
	@RequestMapping("/save")
	@RequiresPermissions("xeadvert:save")
	public R save(@RequestBody XeAdvert xeAdvert){
        List<File> fileList = fileService.getByRelationId(xeAdvert.getAdImagePath());
        if (fileList.size() < 1) {
            throw new MyException("图片不能为空！");
        }
        if (fileList.size() > 1) {
            throw new MyException("图片不能超过1！");
        }
        xeAdvert.setCreateBy(ShiroUtils.getUserId());
        xeAdvert.setUpdateBy(ShiroUtils.getUserId());
		xeAdvertService.save(xeAdvert);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
    @ResponseBody
    @SysLog("修改广告图")
	@RequestMapping("/update")
	@RequiresPermissions("xeadvert:update")
	public R update(@RequestBody XeAdvert xeAdvert){
        List<File> fileList = fileService.getByRelationId(xeAdvert.getAdImagePath());
        if (fileList.size() < 1) {
            throw new MyException("图片不能为空！");
        }
        if (fileList.size() > 1) {
            throw new MyException("图片不能超过1！");
        }
        xeAdvert.setUpdateBy(ShiroUtils.getUserId());
		xeAdvertService.update(xeAdvert);
		
		return R.ok();
	}

    /**
     * 启用
     */
    @ResponseBody
    @SysLog("启用广告图")
    @RequestMapping("/enable")
    @RequiresPermissions("xeadvert:update")
    public R enable(@RequestBody String[] ids){
        String stateValue=StateEnum.ENABLE.getCode();
		xeAdvertService.updateState(ids,stateValue);
        return R.ok();
    }
    /**
     * 禁用
     */
    @ResponseBody
    @SysLog("禁用广告图")
    @RequestMapping("/limit")
    @RequiresPermissions("xeadvert:update")
    public R limit(@RequestBody String[] ids){
        String stateValue=StateEnum.LIMIT.getCode();
		xeAdvertService.updateState(ids,stateValue);
        return R.ok();
    }
	
	/**
	 * 删除
	 */
    @ResponseBody
    @SysLog("删除广告图")
	@RequestMapping("/delete")
	@RequiresPermissions("xeadvert:delete")
	public R delete(@RequestBody Integer[] ids){

        for (Integer id: ids){
            XeAdvert xeAdvert = xeAdvertService.get(id);
            if (xeAdvert != null){
                fileService.deleteByRelationId(xeAdvert.getAdImagePath());
            }
        }

		xeAdvertService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
