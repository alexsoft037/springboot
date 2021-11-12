package xin.xiaoer.modules.classroom.controller;

import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import org.apache.commons.lang.StringUtils;
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
import xin.xiaoer.modules.classroom.entity.SharingLesson;
import xin.xiaoer.modules.classroom.service.SharingLessonService;
import xin.xiaoer.common.utils.PageUtils;
import xin.xiaoer.common.utils.Query;
import xin.xiaoer.common.utils.R;
import xin.xiaoer.service.FileService;


/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-08-15 16:13:22
 */
@Controller
@RequestMapping("sharinglesson")
public class SharingLessonController {
	@Autowired
	private SharingLessonService sharingLessonService;

	@Autowired
    private FileService fileService;
    /**
     * 跳转到列表页
     */
    @RequestMapping("/list")
    @RequiresPermissions("sharinglesson:list")
    public String list() {
        return "sharinglesson/list";
    }
    
	/**
	 * 列表数据
	 */
    @ResponseBody
	@RequestMapping("/listData")
	@RequiresPermissions("sharinglesson:list")
	public R listData(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

//        SysUser admin = ShiroUtils.getUserEntity();
//        if (admin.getUserId() != 1L){
//            query.put("spaceId", admin.getSpaceId());
//        }

		List<SharingLesson> sharingLessonList = sharingLessonService.getList(query);
        CloudStorageService cloudStorageService = OSSFactory.build();
		for (SharingLesson sharingLesson: sharingLessonList){
            sharingLesson.setFeaturedImage(cloudStorageService.generatePresignedUrl(sharingLesson.getFeaturedImage()));
        }
		int total = sharingLessonService.getCount(query);
		
		PageUtils pageUtil = new PageUtils(sharingLessonList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}

    /**
     * 跳转到新增页面
     **/
    @RequestMapping("/add")
    @RequiresPermissions("sharinglesson:save")
    public String add(Model model){
        model.addAttribute("admin",ShiroUtils.getUserEntity());
        return "sharinglesson/add";
    }

    /**
     *   跳转到修改页面
     **/
    @RequestMapping("/edit/{id}")
    @RequiresPermissions("sharinglesson:update")
    public String edit(Model model, @PathVariable("id") String id){
		SharingLesson sharingLesson = sharingLessonService.get(Integer.parseInt(id));
        model.addAttribute("admin",ShiroUtils.getUserEntity());
        model.addAttribute("model",sharingLesson);
        return "sharinglesson/edit";
    }

	/**
	 * 信息
	 */
    @ResponseBody
    @RequestMapping("/info/{lessonId}")
    @RequiresPermissions("sharinglesson:info")
    public R info(@PathVariable("lessonId") Integer lessonId){
        SharingLesson sharingLesson = sharingLessonService.get(lessonId);
        return R.ok().put("sharingLesson", sharingLesson);
    }

    /**
	 * 保存
	 */
    @ResponseBody
    @SysLog("保存少儿云课堂视频")
	@RequestMapping("/save")
	@RequiresPermissions("sharinglesson:save")
	public R save(@RequestBody Map<String, Object> params){
        Gson gson = new Gson();
        JsonElement jsonElement = gson.toJsonTree(params);
        SharingLesson sharingLesson = gson.fromJson(jsonElement, SharingLesson.class);

//        if (ShiroUtils.getUserId() == 1L){
//            String spaceId = params.get("spaceId1").toString();
//            if (StringUtility.isEmpty(spaceId) || !StringUtils.isNumeric(spaceId)) {
//                throw new MyException("少儿空间不能为空！");
//            }
//            sharingLesson.setSpaceId(Integer.parseInt(spaceId));
//        } else {
//            sharingLesson.setSpaceId(ShiroUtils.getSpaceId());
//        }

        sharingLesson.setCreateBy(ShiroUtils.getUserId());
        sharingLesson.setUpdateBy(ShiroUtils.getUserId());
		sharingLessonService.save(sharingLesson);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
    @ResponseBody
    @SysLog("修改少儿云课堂视频")
	@RequestMapping("/update")
	@RequiresPermissions("sharinglesson:update")
	public R update(@RequestBody Map<String, Object> params){
        Gson gson = new Gson();
        JsonElement jsonElement = gson.toJsonTree(params);
        SharingLesson sharingLesson = gson.fromJson(jsonElement, SharingLesson.class);

//        if (ShiroUtils.getUserId() == 1L){
//            String spaceId = params.get("spaceId1").toString();
//            if (StringUtility.isEmpty(spaceId) || !StringUtils.isNumeric(spaceId)) {
//                throw new MyException("少儿空间不能为空！");
//            }
//            sharingLesson.setSpaceId(Integer.parseInt(spaceId));
//        } else {
//            sharingLesson.setSpaceId(ShiroUtils.getSpaceId());
//        }

        sharingLesson.setUpdateBy(ShiroUtils.getUserId());
		sharingLessonService.update(sharingLesson);
		
		return R.ok();
	}

    /**
     * 启用
     */
    @ResponseBody
    @SysLog("启用少儿云课堂视频")
    @RequestMapping("/enable")
    @RequiresPermissions("sharinglesson:update")
    public R enable(@RequestBody String[] ids){
        String stateValue=StateEnum.ENABLE.getCode();
		sharingLessonService.updateState(ids,stateValue);
        return R.ok();
    }
    /**
     * 禁用
     */
    @ResponseBody
    @SysLog("禁用少儿云课堂视频")
    @RequestMapping("/limit")
    @RequiresPermissions("sharinglesson:update")
    public R limit(@RequestBody String[] ids){
        String stateValue=StateEnum.LIMIT.getCode();
		sharingLessonService.updateState(ids,stateValue);
        return R.ok();
    }
	
	/**
	 * 删除
	 */
    @ResponseBody
    @SysLog("删除少儿云课堂视频")
	@RequestMapping("/delete")
	@RequiresPermissions("sharinglesson:delete")
	public R delete(@RequestBody Integer[] lessonIds){
        for (Integer lessonId: lessonIds){
            SharingLesson sharingLesson = sharingLessonService.get(lessonId);
            if (sharingLesson != null){
                fileService.deleteByRelationId(sharingLesson.getFeaturedImage());
                fileService.deleteByRelationId(sharingLesson.getVideoFile());
            }
        }
		sharingLessonService.deleteBatch(lessonIds);
		return R.ok();
	}
	
}
