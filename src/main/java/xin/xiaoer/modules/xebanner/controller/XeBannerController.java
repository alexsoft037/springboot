package xin.xiaoer.modules.xebanner.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.swagger.models.auth.In;
import org.springframework.web.multipart.MultipartFile;
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
import xin.xiaoer.common.utils.*;
import xin.xiaoer.controller.AbstractController;
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
import xin.xiaoer.modules.spacehaedline.entity.SpaceHeadline;
import xin.xiaoer.modules.spacehaedline.service.SpaceHeadlineService;
import xin.xiaoer.modules.story.entity.SpaceStory;
import xin.xiaoer.modules.story.service.SpaceStoryService;
import xin.xiaoer.modules.xebanner.entity.BannerArticle;
import xin.xiaoer.modules.xebanner.entity.XeBanner;
import xin.xiaoer.modules.xebanner.service.XeBannerService;
import xin.xiaoer.service.FileService;


/**
 * 
 * 
 * @author chenyi
 * @email 228112142@qq.com
 * @date 2018-07-12 18:47:51
 */
@Controller
@RequestMapping("xebanner")
public class XeBannerController extends AbstractController {
	@Autowired
	private XeBannerService xeBannerService;

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
    @RequiresPermissions("xebanner:list")
    public String list() {
        return "xebanner/list";
    }
    
	/**
	 * 列表数据
	 */
    @ResponseBody
	@RequestMapping("/listData")
	@RequiresPermissions("xebanner:list")
	public R listData(@RequestParam Map<String, Object> params){
		//查询列表数据
        params.put("bnTypeCode", "BNT001");
        Query query = new Query(params);

		List<XeBanner> xeBannerList = xeBannerService.getList(query);
        CloudStorageService cloudStorageService = OSSFactory.build();
		for (XeBanner xeBanner: xeBannerList){
            if (xeBanner.getArticleId() != null) {
                String articleType = xeBanner.getArticleType();
                if (articleType == null){

                } else if (articleType.equals("AT0001")) {
                    DonateSpace donateSpace = donateSpaceService.get(xeBanner.getArticleId());
                    if (donateSpace != null) {
                        xeBanner.setArticleName(donateSpace.getTitle());
                    }
                } else if (articleType.equals("AT0002")) {
                    SpaceHeadline spaceHeadline = spaceHeadlineService.get(Integer.parseInt(xeBanner.getArticleId().toString()));
                    if (spaceHeadline != null) {
                        xeBanner.setArticleName(spaceHeadline.getTitle());
                    }
                } else if (articleType.equals("AT0003")) {
                    ActivityReport activityReport = activityReportService.get(Integer.parseInt(xeBanner.getArticleId().toString()));
                    if (activityReport != null) {
                        xeBanner.setArticleName(activityReport.getTitle());
                    }
                } else if (articleType.equals("AT0004")) {
                    Activity activity = activityService.get(Integer.parseInt(xeBanner.getArticleId().toString()));
                    activity = activityService.checkExpired(activity);
                    if (activity != null) {
                        xeBanner.setArticleName(activity.getTitle());
                        xeBanner.setArticleStatus(activity.getActivityStatus());
                    }
                } else if (articleType.equals("AT0005")) {
                    SpaceStory spaceStory = spaceStoryService.get(xeBanner.getArticleId());
                    if (spaceStory != null) {
                        xeBanner.setArticleName(spaceStory.getTitle());
                    }
                } else if (articleType.equals("AT0006")) {
                    Book book = bookService.get(xeBanner.getArticleId());
                    if (book != null) {
                        xeBanner.setArticleName(book.getSubject());
                    }
                } else if (articleType.equals("AT0008")) {
                    SharingLesson sharingLesson = sharingLessonService.get(Integer.parseInt(xeBanner.getArticleId().toString()));
                    if (sharingLesson != null) {
                        xeBanner.setArticleName(sharingLesson.getTitle());
                    }
                } else if (articleType.equals("AT0009")) {
                    StudyRoom studyRoom = studyRoomService.get(Integer.parseInt(xeBanner.getArticleId().toString()));
                    if (studyRoom != null) {
                        xeBanner.setArticleName(studyRoom.getTitle());
                        xeBanner.setArticleStatus(studyRoom.getSrStatusName());
                    }
                }
            }
            List<File> files = fileService.getByRelationId(xeBanner.getBnUrl());
            if (files.size() > 0){
                xeBanner.setBnUrl(cloudStorageService.generatePresignedUrl(files.get(0).getUrl()));
            }
        }
		int total = xeBannerService.getCount(query);
		
		PageUtils pageUtil = new PageUtils(xeBannerList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}

    /**
     * 跳转到新增页面
     **/
    @RequestMapping("/add")
    @RequiresPermissions("xebanner:save")
    public String add(){
        return "xebanner/add";
    }

    /**
     *   跳转到修改页面
     **/
    @RequestMapping("/edit/{id}")
    @RequiresPermissions("xebanner:update")
    public String edit(Model model, @PathVariable("id") String id){
		XeBanner xeBanner = xeBannerService.get(Integer.parseInt(id));
        String articleType = xeBanner.getArticleType();

        List<BannerArticle> articles = new ArrayList<>();

        if (articleType == null){

        } else if (articleType.equals("AT0001")) {
            List<DonateSpace> donateSpaces = donateSpaceService.getList(null);
            for (DonateSpace donateSpace: donateSpaces){
                BannerArticle article = new BannerArticle();
                article.setArticleType(articleType);
                article.setArticleId(donateSpace.getItemId().toString());
                article.setArticleTitle(donateSpace.getTitle());
                articles.add(article);
            }
        } else if (articleType.equals("AT0002")) {
            List<SpaceHeadline> spaceHeadlines = spaceHeadlineService.getList(null);
            for (SpaceHeadline spaceHeadline: spaceHeadlines){
                BannerArticle article = new BannerArticle();
                article.setArticleType(articleType);
                article.setArticleId(spaceHeadline.getHeadlineId().toString());
                article.setArticleTitle(spaceHeadline.getTitle());
                articles.add(article);
            }
        } else if (articleType.equals("AT0003")) {
            List<ActivityReport> activityReports = activityReportService.getList(null);
            for (ActivityReport activityReport: activityReports){
                BannerArticle article = new BannerArticle();
                article.setArticleType(articleType);
                article.setArticleId(activityReport.getReportId().toString());
                article.setArticleTitle(activityReport.getTitle());
                articles.add(article);
            }
        } else if (articleType.equals("AT0004")) {
            List<Activity> activities = activityService.getList(null);
            for (Activity activity: activities){
                BannerArticle article = new BannerArticle();
                article.setArticleType(articleType);
                article.setArticleId(activity.getActivityId().toString());
                article.setArticleTitle(activity.getTitle());
                articles.add(article);
            }
        } else if (articleType.equals("AT0005")) {
            List<SpaceStory> spaceStories = spaceStoryService.getList(null);
            for (SpaceStory SpaceStory: spaceStories){
                BannerArticle article = new BannerArticle();
                article.setArticleType(articleType);
                article.setArticleId(SpaceStory.getStoryId().toString());
                article.setArticleTitle(SpaceStory.getTitle());
                articles.add(article);
            }
        } else if (articleType.equals("AT0006")) {
            List<Book> books = bookService.getList(null);
            for (Book book: books){
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

        model.addAttribute("model",xeBanner);
        if (xeBanner.getArticleId() != null){
            model.addAttribute("articleId",xeBanner.getArticleId().toString());
        }
        model.addAttribute("articles",articles);
        return "xebanner/edit";
    }

	/**
	 * 信息
	 */
    @ResponseBody
    @RequestMapping("/info/{id}")
    @RequiresPermissions("xebanner:info")
    public R info(@PathVariable("id") Integer id){
        XeBanner xeBanner = xeBannerService.get(id);
        return R.ok().put("xeBanner", xeBanner);
    }

    /**
	 * 保存
	 */
    @ResponseBody
    @SysLog("保存Banner 图")
	@RequestMapping("/save")
	@RequiresPermissions("xebanner:save")
	public R save(@RequestBody XeBanner xeBanner){

        List<File> fileList = fileService.getByRelationId(xeBanner.getBnUrl());
        if (fileList.size() < 1) {
            throw new MyException("图片不能为空！");
        }
        if (fileList.size() > 1) {
            throw new MyException("图片不能超过1！");
        }
        SysUser user = this.getUser();
        xeBanner.setBnTypeCode("BNT001");
        xeBanner.setCreateBy(user.getUserId());
        xeBanner.setUpdateBy(user.getUserId());
        xeBannerService.save(xeBanner);
		
		return R.ok();
	}

	/**
	 * 修改
	 */
    @ResponseBody
    @SysLog("修改Banner 图")
	@RequestMapping("/update")
	@RequiresPermissions("xebanner:update")
	public R update(@RequestBody XeBanner xeBanner){
        List<File> fileList = fileService.getByRelationId(xeBanner.getBnUrl());
        if (fileList.size() < 1) {
            throw new MyException("图片不能为空！");
        }
        if (fileList.size() > 1) {
            throw new MyException("图片不能超过1！");
        }
        xeBanner.setBnTypeCode("BNT001");
        xeBanner.setUpdateBy(ShiroUtils.getUserId());
		xeBannerService.update(xeBanner);
		
		return R.ok();
	}

    /**
     * 启用
     */
    @ResponseBody
    @SysLog("启用Banner 图")
    @RequestMapping("/enable")
    @RequiresPermissions("xebanner:update")
    public R enable(@RequestBody String[] ids){
        String stateValue=StateEnum.ENABLE.getCode();
		xeBannerService.updateState(ids,stateValue);
        return R.ok();
    }
    /**
     * 禁用
     */
    @ResponseBody
    @SysLog("禁用Banner 图")
    @RequestMapping("/limit")
    @RequiresPermissions("xebanner:update")
    public R limit(@RequestBody String[] ids){
        String stateValue=StateEnum.LIMIT.getCode();
		xeBannerService.updateState(ids,stateValue);
        return R.ok();
    }
	
	/**
	 * 删除
	 */
    @ResponseBody
    @SysLog("删除Banner 图")
	@RequestMapping("/delete")
	@RequiresPermissions("xebanner:delete")
	public R delete(@RequestBody Integer[] ids){
        for (Integer id: ids){
            XeBanner xeBanner = xeBannerService.get(id);
            if (xeBanner != null){
                fileService.deleteByRelationId(xeBanner.getBnUrl());
            }
        }
		xeBannerService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
