package xin.xiaoer.controller;

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

import xin.xiaoer.entity.BlockDevice;
import xin.xiaoer.service.BlockDeviceService;
import xin.xiaoer.common.utils.PageUtils;
import xin.xiaoer.common.utils.Query;
import xin.xiaoer.common.utils.R;


/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-10-29 10:39:46
 */
@Controller
@RequestMapping("blockdevice")
public class BlockDeviceController {
	@Autowired
	private BlockDeviceService blockDeviceService;
	
    /**
     * 跳转到列表页
     */
    @RequestMapping("/list")
    @RequiresPermissions("blockdevice:list")
    public String list() {
        return "blockdevice/list";
    }
    
	/**
	 * 列表数据
	 */
    @ResponseBody
	@RequestMapping("/listData")
	@RequiresPermissions("blockdevice:list")
	public R listData(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<BlockDevice> blockDeviceList = blockDeviceService.getList(query);
		int total = blockDeviceService.getCount(query);
		
		PageUtils pageUtil = new PageUtils(blockDeviceList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}

    /**
     * 跳转到新增页面
     **/
    @RequestMapping("/add")
    @RequiresPermissions("blockdevice:save")
    public String add(){
        return "blockdevice/add";
    }

    /**
     *   跳转到修改页面
     **/
    @RequestMapping("/edit/{id}")
    @RequiresPermissions("blockdevice:update")
    public String edit(Model model, @PathVariable("id") String id){
		BlockDevice blockDevice = blockDeviceService.get(Long.parseLong(id));
        model.addAttribute("model",blockDevice);
        return "blockdevice/edit";
    }

	/**
	 * 信息
	 */
    @ResponseBody
    @RequestMapping("/info/{id}")
    @RequiresPermissions("blockdevice:info")
    public R info(@PathVariable("id") Long id){
        BlockDevice blockDevice = blockDeviceService.get(id);
        return R.ok().put("blockDevice", blockDevice);
    }

    /**
	 * 保存
	 */
    @ResponseBody
    @SysLog("保存")
	@RequestMapping("/save")
	@RequiresPermissions("blockdevice:save")
	public R save(@RequestBody BlockDevice blockDevice){
		blockDeviceService.save(blockDevice);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
    @ResponseBody
    @SysLog("修改")
	@RequestMapping("/update")
	@RequiresPermissions("blockdevice:update")
	public R update(@RequestBody BlockDevice blockDevice){
		blockDeviceService.update(blockDevice);
		
		return R.ok();
	}

    /**
     * 启用
     */
    @ResponseBody
    @SysLog("启用")
    @RequestMapping("/enable")
    @RequiresPermissions("blockdevice:update")
    public R enable(@RequestBody String[] ids){
        String stateValue=StateEnum.ENABLE.getCode();
		blockDeviceService.updateState(ids,stateValue);
        return R.ok();
    }
    /**
     * 禁用
     */
    @ResponseBody
    @SysLog("禁用")
    @RequestMapping("/limit")
    @RequiresPermissions("blockdevice:update")
    public R limit(@RequestBody String[] ids){
        String stateValue=StateEnum.LIMIT.getCode();
		blockDeviceService.updateState(ids,stateValue);
        return R.ok();
    }
	
	/**
	 * 删除
	 */
    @ResponseBody
    @SysLog("删除")
	@RequestMapping("/delete")
	@RequiresPermissions("blockdevice:delete")
	public R delete(@RequestBody Long[] ids){
		blockDeviceService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
