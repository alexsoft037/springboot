package xin.xiaoer.modules.activityreport.controller;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import io.swagger.models.auth.In;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import xin.xiaoer.common.enumresource.StateEnum;
import xin.xiaoer.common.exception.MyException;
import xin.xiaoer.common.log.SysLog;
import xin.xiaoer.common.oss.CloudStorageService;
import xin.xiaoer.common.oss.OSSFactory;
import xin.xiaoer.common.shiro.ShiroUtils;
import xin.xiaoer.common.utils.PageUtils;
import xin.xiaoer.common.utils.Query;
import xin.xiaoer.common.utils.R;
import xin.xiaoer.common.utils.StringUtility;
import xin.xiaoer.entity.Article;
import xin.xiaoer.entity.File;
import xin.xiaoer.entity.SysUser;
import xin.xiaoer.modules.activityreport.entity.ActivityReport;
import xin.xiaoer.modules.activityreport.service.ActivityReportService;
import xin.xiaoer.modules.advert.service.XeAdvertService;
import xin.xiaoer.modules.comment.service.CommentService;
import xin.xiaoer.modules.favourite.service.FavouriteService;
import xin.xiaoer.modules.like.service.LikeService;
import xin.xiaoer.modules.review.service.ReviewService;
import xin.xiaoer.modules.xebanner.service.XeBannerService;
import xin.xiaoer.service.FileService;
import xin.xiaoer.service.SysUserService;

import java.net.URLDecoder;
import java.util.List;
import java.util.Map;


/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-07-24 17:57:36
 */
@Controller
@RequestMapping("activityreport")
public class ActivityReportController {
	@Autowired
	private ActivityReportService activityReportService;

    @Autowired
    private XeBannerService xeBannerService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private FavouriteService favouriteService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private XeAdvertService xeAdvertService;

    @Autowired
    private FileService fileService;

    @Autowired
    private SysUserService sysUserService;
    /**
     * ??????????????????
     */
    @RequestMapping("/list")
    @RequiresPermissions("activityreport:list")
    public String list() {
        return "activityreport/list";
    }
    
	/**
	 * ????????????
	 */
    @ResponseBody
	@RequestMapping("/listData")
	@RequiresPermissions("activityreport:list")
	public R listData(@RequestParam Map<String, Object> params){
		//??????????????????
        Query query = new Query(params);

        SysUser admin = ShiroUtils.getUserEntity();
        if (admin.getUserId() != 1L){
            query.put("spaceId", admin.getSpaceId());
        }

		List<ActivityReport> activityReportList = activityReportService.getList(query);
        CloudStorageService cloudStorageService = OSSFactory.build();
		for (ActivityReport activityReport: activityReportList){
            List<File> files = fileService.getByRelationId(activityReport.getFeaturedImage());
            if (files.size() > 0){
                activityReport.setFeaturedImage(cloudStorageService.generatePresignedUrl(files.get(0).getUrl()));
            }
        }

		int total = activityReportService.getCount(query);
		
		PageUtils pageUtil = new PageUtils(activityReportList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}

    /**
     * ?????????????????????
     **/
    @RequestMapping("/add")
    @RequiresPermissions("activityreport:save")
    public String add(Model model){
        ActivityReport activityReport = new ActivityReport();
        SysUser sysUser = sysUserService.queryObject(ShiroUtils.getUserId());
        if (sysUser != null){
            activityReport.setCreateUser(sysUser.getNickname());
            if (sysUser.getUserClassCode() != null){
                if (sysUser.getUserClassCode().equals(SysUser.NEWSMAN)) {
                    activityReport.setByNewsMan(true);
                } else {
                    activityReport.setByNewsMan(false);
                }
            } else {
                activityReport.setByNewsMan(false);
            }
        } else {
            activityReport.setByNewsMan(false);
        }

        model.addAttribute("admin",ShiroUtils.getUserEntity());

        model.addAttribute("model",activityReport);
        return "activityreport/add";
    }

    /**
     *   ?????????????????????
     **/
    @RequestMapping("/edit/{id}")
    @RequiresPermissions("activityreport:update")
    public String edit(Model model, @PathVariable("id") String id){
		ActivityReport activityReport = activityReportService.get(Integer.parseInt(id));
        SysUser sysUser = sysUserService.queryObject(activityReport.getCreateBy());
        if (sysUser != null){
            activityReport.setCreateUser(sysUser.getNickname());
            if (sysUser.getUserClassCode() != null){
                if (sysUser.getUserClassCode().equals(SysUser.NEWSMAN)) {
                    activityReport.setByNewsMan(true);
                } else {
                    activityReport.setByNewsMan(false);
                }
            } else {
                activityReport.setByNewsMan(false);
            }
        } else {
            activityReport.setByNewsMan(false);
        }
        model.addAttribute("admin",ShiroUtils.getUserEntity());
        model.addAttribute("model",activityReport);
        return "activityreport/edit";
    }

	/**
	 * ??????
	 */
    @ResponseBody
    @RequestMapping("/info/{reportId}")
    @RequiresPermissions("activityreport:info")
    public R info(@PathVariable("reportId") Integer reportId){
        ActivityReport activityReport = activityReportService.get(reportId);
        return R.ok().put("activityReport", activityReport);
    }

    /**
	 * ??????
	 */
    @ResponseBody
    @SysLog("????????????????????????")
	@RequestMapping("/save")
	@RequiresPermissions("activityreport:save")
	public R save(@RequestBody Map<String, Object> params){

        Gson gson = new Gson();
        JsonElement jsonElement = gson.toJsonTree(params);
        ActivityReport activityReport = gson.fromJson(jsonElement, ActivityReport.class);

        if (ShiroUtils.getUserId() == 1L){
            String spaceId = params.get("spaceId1").toString();
            if (StringUtility.isEmpty(spaceId) || !StringUtils.isNumeric(spaceId)) {
                throw new MyException("???????????????????????????");
            }
            activityReport.setSpaceId(Integer.parseInt(spaceId));
        } else {
            activityReport.setSpaceId(ShiroUtils.getSpaceId());
        }

        try {
            activityReport.setContent(URLDecoder.decode(activityReport.getContent(), "UTF-8"));
        } catch (Exception e){
            e.printStackTrace();
        }
        if (activityReport.getReportTypeCode().isEmpty()) {
            throw new MyException("?????????????????????");
        }
        List<File> fileList = fileService.getByRelationId(activityReport.getFeaturedImage());
        if (fileList.size() < 1) {
            throw new MyException("???????????????????????????");
        }
        if (fileList.size() > 1) {
            throw new MyException("????????????????????????1???");
        }
        if (Jsoup.parse(activityReport.getContent()).text().isEmpty()) {
            throw new MyException("?????????????????????");
        }

        activityReport.setCreateBy(ShiroUtils.getUserId());
        activityReport.setUpdateBy(ShiroUtils.getUserId());
		activityReportService.save(activityReport);
		
		return R.ok();
	}
	
	/**
	 * ??????
	 */
    @ResponseBody
    @SysLog("????????????????????????")
	@RequestMapping("/update")
	@RequiresPermissions("activityreport:update")
	public R update(@RequestBody Map<String, Object> params){

        Gson gson = new Gson();
        JsonElement jsonElement = gson.toJsonTree(params);
        ActivityReport activityReport = gson.fromJson(jsonElement, ActivityReport.class);

        if (ShiroUtils.getUserId() == 1L){
            String spaceId = params.get("spaceId1").toString();
            if (StringUtility.isEmpty(spaceId) || !StringUtils.isNumeric(spaceId)) {
                throw new MyException("???????????????????????????");
            }
            activityReport.setSpaceId(Integer.parseInt(spaceId));
        } else {
            activityReport.setSpaceId(ShiroUtils.getSpaceId());
        }

        try {
            activityReport.setContent(URLDecoder.decode(activityReport.getContent(), "UTF-8"));
        } catch (Exception e){
            e.printStackTrace();
        }
        if (activityReport.getReportTypeCode().isEmpty()) {
            throw new MyException("?????????????????????");
        }
        List<File> fileList = fileService.getByRelationId(activityReport.getFeaturedImage());
        if (fileList.size() < 1) {
            throw new MyException("???????????????????????????");
        }
        if (fileList.size() > 1) {
            throw new MyException("????????????????????????1???");
        }
        if (Jsoup.parse(activityReport.getContent()).text().isEmpty()) {
            throw new MyException("?????????????????????");
        }

        activityReport.setUpdateBy(ShiroUtils.getUserId());
		activityReportService.update(activityReport);
		
		return R.ok();
	}

    /**
     * ??????
     */
    @ResponseBody
    @SysLog("????????????????????????")
    @RequestMapping("/enable")
    @RequiresPermissions("activityreport:update")
    public R enable(@RequestBody String[] ids){
        String stateValue=StateEnum.ENABLE.getCode();
		activityReportService.updateState(ids,stateValue);
        return R.ok();
    }
    /**
     * ??????
     */
    @ResponseBody
    @SysLog("????????????????????????")
    @RequestMapping("/limit")
    @RequiresPermissions("activityreport:update")
    public R limit(@RequestBody String[] ids){
        String stateValue=StateEnum.LIMIT.getCode();
		activityReportService.updateState(ids,stateValue);
        return R.ok();
    }
	
	/**
	 * ??????
	 */
    @ResponseBody
    @SysLog("????????????????????????")
	@RequestMapping("/delete")
	@RequiresPermissions("activityreport:delete")
	public R delete(@RequestBody Integer[] reportIds){

        for (Integer reportId: reportIds) {
            ActivityReport activityReport = activityReportService.get(reportId);
            if (activityReport != null) {
                fileService.deleteByRelationId(activityReport.getFeaturedImage());
            }
            xeBannerService.removeArticle(Article.ACTIVITY_REPORT, Long.parseLong(reportId.toString()));
            xeAdvertService.removeArticle(Article.ACTIVITY_REPORT, Long.parseLong(reportId.toString()));
            commentService.deleteByArticle(Article.ACTIVITY, Long.parseLong(reportId.toString()));
            favouriteService.deleteByArticle(Article.ACTIVITY, Long.parseLong(reportId.toString()));
            likeService.deleteByArticle(Article.ACTIVITY, Long.parseLong(reportId.toString()));
            reviewService.deleteByArticle(Article.ACTIVITY, Long.parseLong(reportId.toString()));
        }

		activityReportService.deleteBatch(reportIds);
		
		return R.ok();
	}
	
}
