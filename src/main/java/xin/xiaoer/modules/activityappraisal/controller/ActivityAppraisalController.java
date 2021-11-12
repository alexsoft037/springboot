package xin.xiaoer.modules.activityappraisal.controller;

import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.jni.OS;
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
import xin.xiaoer.entity.SysUser;
import xin.xiaoer.modules.activityappraisal.entity.ActivityAppraisal;
import xin.xiaoer.modules.activityappraisal.service.ActivityAppraisalService;
import xin.xiaoer.common.utils.PageUtils;
import xin.xiaoer.common.utils.Query;
import xin.xiaoer.common.utils.R;
import xin.xiaoer.service.FileService;


/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-07-25 01:47:05
 */
@Controller
@RequestMapping("activityappraisal")
public class ActivityAppraisalController {
	@Autowired
	private ActivityAppraisalService activityAppraisalService;

	@Autowired
    private FileService fileService;
    /**
     * 跳转到列表页
     */
    @RequestMapping("/list")
    @RequiresPermissions("activityappraisal:list")
    public String list() {
        return "activityappraisal/list";
    }
    
	/**
	 * 列表数据
	 */
    @ResponseBody
	@RequestMapping("/listData")
	@RequiresPermissions("activityappraisal:list")
	public R listData(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

        SysUser admin = ShiroUtils.getUserEntity();
        if (admin.getUserId() != 1L){
            query.put("spaceId", admin.getSpaceId());
        }

		List<ActivityAppraisal> activityAppraisalList = activityAppraisalService.getList(query);
        CloudStorageService cloudStorageService = OSSFactory.build();
		for (ActivityAppraisal activityAppraisal: activityAppraisalList){
		    List<File> files = fileService.getByRelationId(activityAppraisal.getFeaturedImage());
		    if (files.size() > 0){
		        activityAppraisal.setFeaturedImage(cloudStorageService.generatePresignedUrl(files.get(0).getUrl()));
            }
        }
		int total = activityAppraisalService.getCount(query);
		
		PageUtils pageUtil = new PageUtils(activityAppraisalList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}

    /**
     * 跳转到新增页面
     **/
    @RequestMapping("/add")
    @RequiresPermissions("activityappraisal:save")
    public String add(Model model){
        model.addAttribute("admin",ShiroUtils.getUserEntity());
        return "activityappraisal/add";
    }

    /**
     *   跳转到修改页面
     **/
    @RequestMapping("/edit/{id}")
    @RequiresPermissions("activityappraisal:update")
    public String edit(Model model, @PathVariable("id") String id){
		ActivityAppraisal activityAppraisal = activityAppraisalService.get(Integer.parseInt(id));
        model.addAttribute("admin",ShiroUtils.getUserEntity());
        model.addAttribute("model",activityAppraisal);
        return "activityappraisal/edit";
    }

	/**
	 * 信息
	 */
    @ResponseBody
    @RequestMapping("/info/{appraisalId}")
    @RequiresPermissions("activityappraisal:info")
    public R info(@PathVariable("appraisalId") Integer appraisalId){
        ActivityAppraisal activityAppraisal = activityAppraisalService.get(appraisalId);
        return R.ok().put("activityAppraisal", activityAppraisal);
    }

    /**
	 * 保存
	 */
    @ResponseBody
    @SysLog("保存活动评选")
	@RequestMapping("/save")
	@RequiresPermissions("activityappraisal:save")
	public R save(@RequestBody Map<String, Object> params ){
        Gson gson = new Gson();
        JsonElement jsonElement = gson.toJsonTree(params);
        ActivityAppraisal activityAppraisal = gson.fromJson(jsonElement, ActivityAppraisal.class);

        String htmlId = params.get("htmlId1").toString();
        if (StringUtils.isNumeric(htmlId) && StringUtility.isNotEmpty(htmlId)) {
            activityAppraisal.setHtmlId(Integer.parseInt(htmlId));
        }

        if (ShiroUtils.getUserId() == 1L){
            String spaceId = params.get("spaceId1").toString();
            if (StringUtility.isEmpty(spaceId) || !StringUtils.isNumeric(spaceId)) {
                throw new MyException("少儿空间不能为空！");
            }
            activityAppraisal.setSpaceId(Integer.parseInt(spaceId));
        } else {
            activityAppraisal.setSpaceId(ShiroUtils.getSpaceId());
        }

        List<File> fileList = fileService.getByRelationId(activityAppraisal.getFeaturedImage());
        if (fileList.size() < 1) {
            throw new MyException("特色图片不能为空！");
        }
        if (fileList.size() > 1) {
            throw new MyException("特色图片不能超过1！");
        }
        activityAppraisal.setUpdateBy(ShiroUtils.getUserId());
        activityAppraisal.setCreateBy(ShiroUtils.getUserId());
		activityAppraisalService.save(activityAppraisal);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
    @ResponseBody
    @SysLog("修改活动评选")
	@RequestMapping("/update")
	@RequiresPermissions("activityappraisal:update")
    public R update(@RequestBody Map<String, Object> params ){
        Gson gson = new Gson();
        JsonElement jsonElement = gson.toJsonTree(params);
        ActivityAppraisal activityAppraisal = gson.fromJson(jsonElement, ActivityAppraisal.class);

        String htmlId = params.get("htmlId1").toString();
        if (StringUtils.isNumeric(htmlId) && StringUtility.isNotEmpty(htmlId)) {
            activityAppraisal.setHtmlId(Integer.parseInt(htmlId));
        }
        if (ShiroUtils.getUserId() == 1L){
            String spaceId = params.get("spaceId1").toString();
            if (StringUtility.isEmpty(spaceId) || !StringUtils.isNumeric(spaceId)) {
                throw new MyException("少儿空间不能为空！");
            }
            activityAppraisal.setSpaceId(Integer.parseInt(spaceId));
        } else {
            activityAppraisal.setSpaceId(ShiroUtils.getSpaceId());
        }

        List<File> fileList = fileService.getByRelationId(activityAppraisal.getFeaturedImage());
        if (fileList.size() < 1) {
            throw new MyException("特色图片不能为空！");
        }
        if (fileList.size() > 1) {
            throw new MyException("特色图片不能超过1！");
        }
        activityAppraisal.setUpdateBy(ShiroUtils.getUserId());
		activityAppraisalService.update(activityAppraisal);
		
		return R.ok();
	}

    /**
     * 启用
     */
    @ResponseBody
    @SysLog("启用活动评选")
    @RequestMapping("/enable")
    @RequiresPermissions("activityappraisal:update")
    public R enable(@RequestBody String[] ids){
        String stateValue=StateEnum.ENABLE.getCode();
		activityAppraisalService.updateState(ids,stateValue);
        return R.ok();
    }
    /**
     * 禁用
     */
    @ResponseBody
    @SysLog("禁用活动评选")
    @RequestMapping("/limit")
    @RequiresPermissions("activityappraisal:update")
    public R limit(@RequestBody String[] ids){
        String stateValue=StateEnum.LIMIT.getCode();
		activityAppraisalService.updateState(ids,stateValue);
        return R.ok();
    }
	
	/**
	 * 删除
	 */
    @ResponseBody
    @SysLog("删除活动评选")
	@RequestMapping("/delete")
	@RequiresPermissions("activityappraisal:delete")
	public R delete(@RequestBody Integer[] appraisalIds){
        for (Integer appraisalId: appraisalIds){
            ActivityAppraisal activityAppraisal = activityAppraisalService.get(appraisalId);
            if (activityAppraisal != null){
                fileService.deleteByRelationId(activityAppraisal.getFeaturedImage());
            }
        }
        activityAppraisalService.deleteBatch(appraisalIds);
		return R.ok();
	}
	
}
