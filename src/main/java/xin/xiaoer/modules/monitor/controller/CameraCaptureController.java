package xin.xiaoer.modules.monitor.controller;

import java.util.List;
import java.util.Map;
import xin.xiaoer.common.enumresource.StateEnum;
import xin.xiaoer.common.log.SysLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import javax.servlet.http.HttpServletRequest;

import xin.xiaoer.modules.monitor.entity.CameraCapture;
import xin.xiaoer.modules.monitor.service.CameraCaptureService;
import xin.xiaoer.common.utils.PageUtils;
import xin.xiaoer.common.utils.Query;
import xin.xiaoer.common.utils.R;


/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-09-06 13:29:33
 */
@Controller
@RequestMapping("cameracapture")
public class CameraCaptureController {
	@Autowired
	private CameraCaptureService cameraCaptureService;
	
    /**
     * 跳转到列表页
     */
    @RequestMapping("/list")
    @RequiresPermissions("cameracapture:list")
    public String list() {
        return "cameracapture/list";
    }
    
	/**
	 * 列表数据
	 */
    @ResponseBody
	@RequestMapping("/listData")
	@RequiresPermissions("cameracapture:list")
	public R listData(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<CameraCapture> cameraCaptureList = cameraCaptureService.getList(query);
		int total = cameraCaptureService.getCount(query);
		
		PageUtils pageUtil = new PageUtils(cameraCaptureList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}

    /**
     * 跳转到新增页面
     **/
    @RequestMapping("/add")
    @RequiresPermissions("cameracapture:save")
    public String add(){
        return "cameracapture/add";
    }

    /**
     *   跳转到修改页面
     **/
    @RequestMapping("/edit/{id}")
    @RequiresPermissions("cameracapture:update")
    public String edit(Model model, @PathVariable("id") Long id){
		CameraCapture cameraCapture = cameraCaptureService.get(id);
        model.addAttribute("model",cameraCapture);
        return "cameracapture/edit";
    }

	/**
	 * 信息
	 */
    @ResponseBody
    @RequestMapping("/info/{id}")
    @RequiresPermissions("cameracapture:info")
    public R info(@PathVariable("id") Long id){
        CameraCapture cameraCapture = cameraCaptureService.get(id);
        return R.ok().put("cameraCapture", cameraCapture);
    }

    /**
	 * 保存
	 */
    @ResponseBody
    @SysLog("保存")
	@RequestMapping("/save")
	@RequiresPermissions("cameracapture:save")
	public R save(@RequestBody CameraCapture cameraCapture){
		cameraCaptureService.save(cameraCapture);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
    @ResponseBody
    @SysLog("修改")
	@RequestMapping("/update")
	@RequiresPermissions("cameracapture:update")
	public R update(@RequestBody CameraCapture cameraCapture){
		cameraCaptureService.update(cameraCapture);
		
		return R.ok();
	}

    /**
     * 启用
     */
    @ResponseBody
    @SysLog("启用")
    @RequestMapping("/enable")
    @RequiresPermissions("cameracapture:update")
    public R enable(@RequestBody String[] ids){
        String stateValue=StateEnum.ENABLE.getCode();
		cameraCaptureService.updateState(ids,stateValue);
        return R.ok();
    }
    /**
     * 禁用
     */
    @ResponseBody
    @SysLog("禁用")
    @RequestMapping("/limit")
    @RequiresPermissions("cameracapture:update")
    public R limit(@RequestBody String[] ids){
        String stateValue=StateEnum.LIMIT.getCode();
		cameraCaptureService.updateState(ids,stateValue);
        return R.ok();
    }
	
	/**
	 * 删除
	 */
    @ResponseBody
    @SysLog("删除")
	@RequestMapping("/delete")
	@RequiresPermissions("cameracapture:delete")
	public R delete(@RequestBody Long[] ids){
		cameraCaptureService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
