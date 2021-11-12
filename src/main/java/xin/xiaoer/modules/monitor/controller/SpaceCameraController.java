package xin.xiaoer.modules.monitor.controller;

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
import xin.xiaoer.common.utils.*;
import xin.xiaoer.entity.File;
import xin.xiaoer.entity.SpaceNews;
import xin.xiaoer.entity.SysUser;
import xin.xiaoer.modules.monitor.entity.SpaceCamera;
import xin.xiaoer.modules.monitor.service.SpaceCameraService;
import xin.xiaoer.service.FileService;


/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-09-03 18:37:09
 */
@Controller
@RequestMapping("spacecamera")
public class SpaceCameraController {
	@Autowired
	private SpaceCameraService spaceCameraService;

    @Autowired
    private FileService fileService;
    /**
     * 跳转到列表页
     */
    @RequestMapping("/list")
    @RequiresPermissions("spacecamera:list")
    public String list() {
        return "spacecamera/list";
    }
    
	/**
	 * 列表数据
	 */
    @ResponseBody
	@RequestMapping("/listData")
	@RequiresPermissions("spacecamera:list")
	public R listData(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

        SysUser admin = ShiroUtils.getUserEntity();
        if (admin.getUserId() != 1L){
            query.put("spaceId", admin.getSpaceId());
        }

		List<SpaceCamera> spaceCameraList = spaceCameraService.getList(query);
        CloudStorageService cloudStorageService = OSSFactory.build();
        for (SpaceCamera spaceCamera: spaceCameraList){
            List<File> files = fileService.getByRelationId(spaceCamera.getImg());
            if (files.size() > 0){
                spaceCamera.setImg(cloudStorageService.generatePresignedUrl(files.get(0).getUrl()));
            } else {
                spaceCamera.setImg("");
            }
        }
		int total = spaceCameraService.getCount(query);
		
		PageUtils pageUtil = new PageUtils(spaceCameraList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}

    /**
     * 跳转到新增页面
     **/
    @RequestMapping("/add")
    @RequiresPermissions("spacecamera:save")
    public String add(Model model){
        model.addAttribute("admin",ShiroUtils.getUserEntity());
        model.addAttribute("BaiduApiKey", Constant.BaiduApiKey);
        return "spacecamera/add";
    }

    /**
     *   跳转到修改页面
     **/
    @RequestMapping("/edit/{id}")
    @RequiresPermissions("spacecamera:update")
    public String edit(Model model, @PathVariable("id") Integer id){
		SpaceCamera spaceCamera = spaceCameraService.get(id);
        model.addAttribute("admin",ShiroUtils.getUserEntity());
        model.addAttribute("BaiduApiKey", Constant.BaiduApiKey);
        model.addAttribute("model",spaceCamera);
        return "spacecamera/edit";
    }

	/**
	 * 信息
	 */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("spacecamera:info")
    public String info(Model model, @PathVariable("id") Integer id){
        SpaceCamera spaceCamera = spaceCameraService.get(id);
        model.addAttribute("model",spaceCamera);
        return "spacecamera/info";
    }

    /**
	 * 保存
	 */
    @ResponseBody
    @SysLog("保存监控实时")
	@RequestMapping("/save")
	@RequiresPermissions("spacecamera:save")
	public R save(@RequestBody Map<String, Object> params){
        Gson gson = new Gson();
        JsonElement jsonElement = gson.toJsonTree(params);
        SpaceCamera spaceCamera = gson.fromJson(jsonElement, SpaceCamera.class);

        if (ShiroUtils.getUserId() == 1L){
            String spaceId = params.get("spaceId1").toString();
            if (StringUtility.isEmpty(spaceId) || !StringUtils.isNumeric(spaceId)) {
                throw new MyException("少儿空间不能为空！");
            }
            spaceCamera.setSpaceId(Integer.parseInt(spaceId));
        } else {
            spaceCamera.setSpaceId(ShiroUtils.getSpaceId());
        }

        spaceCamera.setCreateBy(ShiroUtils.getUserId());
        spaceCamera.setUpdateBy(ShiroUtils.getUserId());
		spaceCameraService.save(spaceCamera);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
    @ResponseBody
    @SysLog("修改监控实时")
	@RequestMapping("/update")
	@RequiresPermissions("spacecamera:update")
	public R update(@RequestBody Map<String, Object> params){
        Gson gson = new Gson();
        JsonElement jsonElement = gson.toJsonTree(params);
        SpaceCamera spaceCamera = gson.fromJson(jsonElement, SpaceCamera.class);

        if (ShiroUtils.getUserId() == 1L){
            String spaceId = params.get("spaceId1").toString();
            if (StringUtility.isEmpty(spaceId) || !StringUtils.isNumeric(spaceId)) {
                throw new MyException("少儿空间不能为空！");
            }
            spaceCamera.setSpaceId(Integer.parseInt(spaceId));
        } else {
            spaceCamera.setSpaceId(ShiroUtils.getSpaceId());
        }

        spaceCamera.setUpdateBy(ShiroUtils.getUserId());
		spaceCameraService.update(spaceCamera);
		
		return R.ok();
	}

    /**
     * 启用
     */
    @ResponseBody
    @SysLog("启用监控实时")
    @RequestMapping("/enable")
    @RequiresPermissions("spacecamera:update")
    public R enable(@RequestBody String[] ids){
        String stateValue=StateEnum.ENABLE.getCode();
		spaceCameraService.updateState(ids,stateValue);
        return R.ok();
    }
    /**
     * 禁用
     */
    @ResponseBody
    @SysLog("禁用监控实时")
    @RequestMapping("/limit")
    @RequiresPermissions("spacecamera:update")
    public R limit(@RequestBody String[] ids){
        String stateValue=StateEnum.LIMIT.getCode();
		spaceCameraService.updateState(ids,stateValue);
        return R.ok();
    }
	
	/**
	 * 删除
	 */
    @ResponseBody
    @SysLog("删除监控实时")
	@RequestMapping("/delete")
	@RequiresPermissions("spacecamera:delete")
	public R delete(@RequestBody Integer[] ids){
        for (Integer id: ids){
            SpaceCamera spaceCamera = spaceCameraService.get(id);
            fileService.deleteByRelationId(spaceCamera.getImg());
        }
		spaceCameraService.deleteBatch(ids);
		return R.ok();
	}
	
}
