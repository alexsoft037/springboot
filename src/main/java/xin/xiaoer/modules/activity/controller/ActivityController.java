package xin.xiaoer.modules.activity.controller;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
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
import xin.xiaoer.common.utils.*;
import xin.xiaoer.entity.Article;
import xin.xiaoer.entity.File;
import xin.xiaoer.entity.SysUser;
import xin.xiaoer.modules.activity.entity.Activity;
import xin.xiaoer.modules.activity.entity.ActivitySignNumber;
import xin.xiaoer.modules.activity.service.ActivityAttendService;
import xin.xiaoer.modules.activity.service.ActivityService;
import xin.xiaoer.modules.activity.service.ActivitySignNumberService;
import xin.xiaoer.modules.advert.service.XeAdvertService;
import xin.xiaoer.modules.comment.service.CommentService;
import xin.xiaoer.modules.favourite.service.FavouriteService;
import xin.xiaoer.modules.like.service.LikeService;
import xin.xiaoer.modules.message.entity.ActivityMessage;
import xin.xiaoer.modules.message.service.ActivityMessageService;
import xin.xiaoer.modules.mobile.bean.ResponseBean;
import xin.xiaoer.modules.review.service.ReviewService;
import xin.xiaoer.modules.xebanner.service.XeBannerService;
import xin.xiaoer.service.FileService;

import java.net.URLDecoder;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-07-23 20:59:06
 */
@Controller
@RequestMapping("activity")
public class ActivityController {
    @Autowired
    private ActivityService activityService;

    @Autowired
    private FileService fileService;

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
    private ActivityAttendService activityAttendService;

    @Autowired
    private ActivitySignNumberService activitySignNumberService;

	//??????
    @Autowired
    private ActivityMessageService activityMessageService;
	
    /**
     * ??????????????????
     */
    @RequestMapping("/list")
    @RequiresPermissions("activity:list")
    public String list() {
        return "activity/list";
    }

    /**
     * ????????????
     */
    @ResponseBody
    @RequestMapping("/listData")
    @RequiresPermissions("activity:list")
    public R listData(@RequestParam Map<String, Object> params) {
        //??????????????????
        Query query = new Query(params);
        SysUser admin = ShiroUtils.getUserEntity();
        if (admin.getUserId() != 1L) {
            query.put("spaceId", admin.getSpaceId());
        }

        List<Activity> activityList = activityService.getList(query);
        CloudStorageService cloudStorageService = OSSFactory.build();
        for (Activity activity : activityList) {
            List<File> files = fileService.getByRelationId(activity.getFeaturedImage());
            activity = activityService.checkExpired(activity);
            if (files.size() > 0) {
                activity.setFeaturedImage(cloudStorageService.generatePresignedUrl(files.get(0).getUrl()));
            }
            Integer totalUsers = activityAttendService.getCountByActivityId(activity.getActivityId());
            activity.setTotalUsers(totalUsers);
        }
		int total = activityService.getCount(query);
		
		PageUtils pageUtil = new PageUtils(activityList, total, query.getLimit(), query.getPage());

		return R.ok().put("page", pageUtil);
	}

    /**
     * ?????????????????????
     **/
    @RequestMapping("/add")
    @RequiresPermissions("activity:save")
    public String add(Model model) {
        model.addAttribute("admin", ShiroUtils.getUserEntity());
        model.addAttribute("BaiduApiKey", Constant.BaiduApiKey);
        return "activity/add";
    }

    /**
     * ?????????????????????
     **/
    @RequestMapping("/edit/{id}")
    @RequiresPermissions("activity:update")
    public String edit(Model model, @PathVariable("id") String id) {
        Activity activity = activityService.get(Integer.parseInt(id));
        activity = activityService.checkExpired(activity);
        model.addAttribute("admin", ShiroUtils.getUserEntity());
        model.addAttribute("BaiduApiKey", Constant.BaiduApiKey);
        model.addAttribute("model", activity);
        return "activity/edit";
    }

    /**
     * ??????
     */
    @ResponseBody
    @RequestMapping("/info/{activityId}")
    @RequiresPermissions("activity:info")
    public R info(@PathVariable("activityId") Integer activityId) {
        Activity activity = activityService.get(activityId);
        activity = activityService.checkExpired(activity);
        return R.ok().put("activity", activity);
    }

    /**
     * ??????
     */
    @ResponseBody
    @SysLog("????????????????????????")
    @RequestMapping("/save")
    @RequiresPermissions("activity:save")
    public R save(@RequestBody Map<String, Object> params) {
        Gson gson = new Gson();
        JsonElement jsonElement = gson.toJsonTree(params);
        Activity activity = gson.fromJson(jsonElement, Activity.class);

        if (ShiroUtils.getUserId() == 1L) {
            String spaceId = params.get("spaceId1").toString();
            if (StringUtility.isEmpty(spaceId) || !StringUtils.isNumeric(spaceId)) {
                throw new MyException("???????????????????????????");
            }
            activity.setSpaceId(Integer.parseInt(spaceId));
        } else {
            activity.setSpaceId(ShiroUtils.getSpaceId());
        }

        try {
            activity.setContent(URLDecoder.decode(activity.getContent(), "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (activity.getActivityTypeCode().isEmpty()) {
            throw new MyException("?????????????????????");
        }
        if (Jsoup.parse(activity.getContent()).text().isEmpty()) {
            throw new MyException("?????????????????????");
        }

        //??????
        if (activity.getFeaturedImage().isEmpty()) {
            throw new MyException("???????????????????????????");
        }
        List<File> fileList = fileService.getByRelationId(activity.getFeaturedImage());

        if (fileList.size() < 1){
            throw new MyException("???????????????????????????");
        }
        if (fileList.size() > 1) {
            throw new MyException("????????????????????????1???");
        }
        if (activity.getActivityStatusCode().isEmpty()) {
                throw new MyException("??????????????????");
        }
        String periodFrom = params.get("periodFrom1").toString();
        Date today = new Date();
        try {
            if (DateUtil.str2SDF_YMDHMS(periodFrom).before(today)) {
                throw new MyException("?????????????????????????????????!");
            }
        } catch (Exception e){
            throw new MyException("????????????????????????!");
        }
        String periodTo = params.get("periodTo1").toString();
        try {
            if (DateUtil.str2SDF_YMDHMS(periodTo).before(today)) {
                throw new MyException("?????????????????????????????????!");
            }
            if (DateUtil.str2SDF_YMDHMS(periodTo).before(DateUtil.str2SDF_YMDHMS(periodFrom))) {
                throw new MyException("???????????????????????????????????????!");
            }
        } catch (Exception e){
            throw new MyException("????????????????????????!");
        }
        String registerEnd = params.get("registerEnd1").toString();
        try {
            if (DateUtil.str2SDFYMD(registerEnd).before(today)) {
                throw new MyException("?????????????????????????????????!");
            }
            if (DateUtil.str2SDFYMD(registerEnd).after(DateUtil.str2SDF_YMDHMS(periodFrom))) {
                throw new MyException("???????????????????????????????????????!");
            }
        } catch (Exception e){
            throw new MyException("????????????????????????!");
        }
        activity.setPeriodFrom(DateUtil.str2SDF_YMDHMS(periodFrom));
        activity.setPeriodTo(DateUtil.str2SDF_YMDHMS(periodTo));
        activity.setRegisterEnd(DateUtil.str2SDFYMD(registerEnd));

        activity.setCreateBy(ShiroUtils.getUserId());
        activity.setUpdateBy(ShiroUtils.getUserId());
        activityService.save(activity);

        ActivitySignNumber activitySignNumber = new ActivitySignNumber();
        activitySignNumber.setActivityId(activity.getActivityId());
        activitySignNumber.setSignNumber(Integer.toString(UUID.getNumber(4)));
        activitySignNumberService.save(activitySignNumber);

        //??????
        ActivityMessage activityMessage = new ActivityMessage();
        activityMessage.setName(activity.getContactName());
        activityMessage.setPhoneNo(activity.getContactPhone());
        activityMessage.setContent("???????????????" + activity.getTitle() + "??????????????????" + activity.getLocation() + "??????????????????" + periodFrom + "???" + periodTo + "???????????????" + activitySignNumber.getSignNumber() + "???????????????????????????????????????????????????????????????");
        activityMessageService.save(activityMessage);
        try {
            String ret = DxtonSMSSend.sendActivityMessage(activityMessage.getPhoneNo(), activityMessage.getContent());
            if (!ret.contains("100")){
                return R.error(DxtonSMSSend.getResponseText(ret.trim()));
            }
        }catch (Exception e){
        }

        return R.ok();
    }


    @ResponseBody
    @SysLog("????????????????????????")
    @RequestMapping("/update")
    @RequiresPermissions("activity:update")
    public R update(@RequestBody Map<String, Object> params) {
        Gson gson = new Gson();
        JsonElement jsonElement = gson.toJsonTree(params);
        Activity activity = gson.fromJson(jsonElement, Activity.class);

        if (ShiroUtils.getUserId() == 1L) {
            String spaceId = params.get("spaceId1").toString();
            if (StringUtility.isEmpty(spaceId) || !StringUtils.isNumeric(spaceId)) {
                throw new MyException("???????????????????????????");
            }
            activity.setSpaceId(Integer.parseInt(spaceId));
        } else {
            activity.setSpaceId(ShiroUtils.getSpaceId());
        }

        try {
            activity.setContent(URLDecoder.decode(activity.getContent(), "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (activity.getActivityTypeCode().isEmpty()) {
            throw new MyException("?????????????????????");
        }
        if (Jsoup.parse(activity.getContent()).text().isEmpty()) {
            throw new MyException("?????????????????????");
        }

        //??????
        if (activity.getFeaturedImage().isEmpty()) {
            throw new MyException("???????????????????????????");
        }
        List<File> fileList = fileService.getByRelationId(activity.getFeaturedImage());
        if (fileList.size() < 1) {
            throw new MyException("???????????????????????????");
        }
        if (fileList.size() > 1) {
            throw new MyException("????????????????????????1???");
        }
        if (activity.getActivityStatusCode().isEmpty()) {
            throw new MyException("??????????????????");
        }
        String periodFrom = params.get("periodFrom1").toString();
        String periodTo = params.get("periodTo1").toString();
        if (DateUtil.str2SDF_YMDHMS(periodTo).before(DateUtil.str2SDF_YMDHMS(periodFrom))) {
            throw new MyException("???????????????????????????????????????!");
        }
        String registerEnd = params.get("registerEnd1").toString();
        if (DateUtil.str2SDFYMD(registerEnd).after(DateUtil.str2SDF_YMDHMS(periodFrom))) {
            throw new MyException("???????????????????????????????????????!");
        }
        activity.setPeriodFrom(DateUtil.str2SDF_YMDHMS(periodFrom));
        activity.setPeriodTo(DateUtil.str2SDF_YMDHMS(periodTo));
        activity.setRegisterEnd(DateUtil.str2SDFYMD(registerEnd));

        activity.setUpdateBy(ShiroUtils.getUserId());
        activityService.update(activity);


        //?????????
        ActivitySignNumber activitySignNumber = new ActivitySignNumber();
        activitySignNumber.setActivityId(activity.getActivityId());
        activitySignNumber.setSignNumber(Integer.toString(UUID.getNumber(4)));
        activitySignNumberService.update(activitySignNumber);

        //??????
        ActivityMessage activityMessage = new ActivityMessage();
        activityMessage.setName(activity.getContactName());
        activityMessage.setPhoneNo(activity.getContactPhone());
        activityMessage.setContent("???????????????" + activity.getTitle() + "??????????????????" + activity.getLocation() + "??????????????????" + periodFrom + "???" + periodTo + "???????????????" + activitySignNumber.getSignNumber() + "???????????????????????????????????????????????????????????????");
        activityMessageService.save(activityMessage);
        try {
            String ret = DxtonSMSSend.sendActivityMessage(activityMessage.getPhoneNo(), activityMessage.getContent());
            if (!ret.contains("100")){
                return R.error(DxtonSMSSend.getResponseText(ret.trim()));
            }
        }catch (Exception e){
        }


        return R.ok();
    }

    /**
     * ??????
     */
    @ResponseBody
    @SysLog("????????????????????????")
    @RequestMapping("/enable")
    @RequiresPermissions("activity:update")
    public R enable(@RequestBody String[] ids) {
        String stateValue = StateEnum.ENABLE.getCode();
        activityService.updateState(ids, stateValue);
        return R.ok();
    }

    /**
     * ??????
     */
    @ResponseBody
    @SysLog("????????????????????????")
    @RequestMapping("/limit")
    @RequiresPermissions("activity:update")
    public R limit(@RequestBody String[] ids) {
        String stateValue = StateEnum.LIMIT.getCode();
        activityService.updateState(ids, stateValue);
        return R.ok();
    }

    /**
     * ??????
     */
    @ResponseBody
    @SysLog("????????????????????????")
    @RequestMapping("/delete")
    @RequiresPermissions("activity:delete")
    public R delete(@RequestBody Integer[] activityIds) {
        for (Integer activityId : activityIds) {
            Activity activity = activityService.get(activityId);
            if (activity != null) {
                activityAttendService.deleteByActivityId(activityId);
                fileService.deleteByRelationId(activity.getFeaturedImage());
            }
            xeBannerService.removeArticle(Article.ACTIVITY, Long.parseLong(activityId.toString()));
            xeAdvertService.removeArticle(Article.ACTIVITY, Long.parseLong(activityId.toString()));
            commentService.deleteByArticle(Article.ACTIVITY, Long.parseLong(activityId.toString()));
            favouriteService.deleteByArticle(Article.ACTIVITY, Long.parseLong(activityId.toString()));
            likeService.deleteByArticle(Article.ACTIVITY, Long.parseLong(activityId.toString()));
            reviewService.deleteByArticle(Article.ACTIVITY, Long.parseLong(activityId.toString()));
        }
        activityService.deleteBatch(activityIds);
        activitySignNumberService.deleteBatch(activityIds);
        return R.ok();
    }

}
