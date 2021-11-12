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

import xin.xiaoer.modules.monitor.entity.CameraRecord;
import xin.xiaoer.modules.monitor.service.CameraRecordService;
import xin.xiaoer.common.utils.PageUtils;
import xin.xiaoer.common.utils.Query;
import xin.xiaoer.common.utils.R;


/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-11-12 07:56:35
 */
@Controller
@RequestMapping("camerarecord")
public class CameraRecordController {
	@Autowired
	private CameraRecordService cameraRecordService;
	
    /**
     * 跳转到列表页
     */
    @RequestMapping("/list")
    @RequiresPermissions("camerarecord:list")
    public String list() {
        return "camerarecord/list";
    }
    
	/**
	 * 列表数据
	 */
    @ResponseBody
	@RequestMapping("/listData")
	@RequiresPermissions("camerarecord:list")
	public R listData(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<CameraRecord> cameraRecordList = cameraRecordService.getList(query);
		int total = cameraRecordService.getCount(query);
		
		PageUtils pageUtil = new PageUtils(cameraRecordList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}

    /**
     * 跳转到新增页面
     **/
    @RequestMapping("/add")
    @RequiresPermissions("camerarecord:save")
    public String add(){
        return "camerarecord/add";
    }

    /**
     *   跳转到修改页面
     **/
    @RequestMapping("/edit/{id}")
    @RequiresPermissions("camerarecord:update")
    public String edit(Model model, @PathVariable("id") Long id){
		CameraRecord cameraRecord = cameraRecordService.get(id);
        model.addAttribute("model",cameraRecord);
        return "camerarecord/edit";
    }

	/**
	 * 信息
	 */
    @ResponseBody
    @RequestMapping("/info/{id}")
    @RequiresPermissions("camerarecord:info")
    public R info(@PathVariable("id") Long id){
        CameraRecord cameraRecord = cameraRecordService.get(id);
        return R.ok().put("cameraRecord", cameraRecord);
    }

    /**
	 * 保存
	 */
    @ResponseBody
    @SysLog("保存")
	@RequestMapping("/save")
	@RequiresPermissions("camerarecord:save")
	public R save(@RequestBody CameraRecord cameraRecord){
		cameraRecordService.save(cameraRecord);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
    @ResponseBody
    @SysLog("修改")
	@RequestMapping("/update")
	@RequiresPermissions("camerarecord:update")
	public R update(@RequestBody CameraRecord cameraRecord){
		cameraRecordService.update(cameraRecord);
		
		return R.ok();
	}

    /**
     * 启用
     */
    @ResponseBody
    @SysLog("启用")
    @RequestMapping("/enable")
    @RequiresPermissions("camerarecord:update")
    public R enable(@RequestBody String[] ids){
        String stateValue=StateEnum.ENABLE.getCode();
		cameraRecordService.updateState(ids,stateValue);
        return R.ok();
    }
    /**
     * 禁用
     */
    @ResponseBody
    @SysLog("禁用")
    @RequestMapping("/limit")
    @RequiresPermissions("camerarecord:update")
    public R limit(@RequestBody String[] ids){
        String stateValue=StateEnum.LIMIT.getCode();
		cameraRecordService.updateState(ids,stateValue);
        return R.ok();
    }
	
	/**
	 * 删除
	 */
    @ResponseBody
    @SysLog("删除")
	@RequestMapping("/delete")
	@RequiresPermissions("camerarecord:delete")
	public R delete(@RequestBody Long[] ids){
		cameraRecordService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
