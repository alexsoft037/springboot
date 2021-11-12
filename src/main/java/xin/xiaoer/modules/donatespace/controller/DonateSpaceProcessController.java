package xin.xiaoer.modules.donatespace.controller;

import java.util.List;
import java.util.Map;
import xin.xiaoer.common.enumresource.StateEnum;
import xin.xiaoer.common.log.SysLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import xin.xiaoer.modules.donatespace.entity.DonateSpaceProcess;
import xin.xiaoer.modules.donatespace.service.DonateSpaceProcessService;
import xin.xiaoer.common.utils.PageUtils;
import xin.xiaoer.common.utils.Query;
import xin.xiaoer.common.utils.R;


/**
 * 
 * 
 * @author chenyi
 * @email 228112142@qq.com
 * @date 2018-07-20 00:16:18
 */
@Controller
@RequestMapping("donatespaceprocess")
public class DonateSpaceProcessController {
	@Autowired
	private DonateSpaceProcessService donateSpaceProcessService;
	
    /**
     * 跳转到列表页
     */
    @RequestMapping("/list")
    @RequiresPermissions("donatespaceprocess:list")
    public String list() {
        return "donatespaceprocess/list";
    }
    
	/**
	 * 列表数据
	 */
    @ResponseBody
	@RequestMapping("/listData")
	@RequiresPermissions("donatespaceprocess:list")
	public R listData(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<DonateSpaceProcess> donateSpaceProcessList = donateSpaceProcessService.getList(query);
		int total = donateSpaceProcessService.getCount(query);
		
		PageUtils pageUtil = new PageUtils(donateSpaceProcessList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}

    /**
     * 跳转到新增页面
     **/
    @RequestMapping("/add")
    @RequiresPermissions("donatespaceprocess:save")
    public String add(){
        return "donatespaceprocess/add";
    }

    /**
     *   跳转到修改页面
     **/
    @RequestMapping("/edit/{id}")
    @RequiresPermissions("donatespaceprocess:update")
    public String edit(Model model, @PathVariable("id") String id){
		DonateSpaceProcess donateSpaceProcess = donateSpaceProcessService.get(Long.parseLong(id));
        model.addAttribute("model",donateSpaceProcess);
        return "donatespaceprocess/edit";
    }

	/**
	 * 信息
	 */
    @ResponseBody
    @RequestMapping("/info/{itemId}")
    @RequiresPermissions("donatespaceprocess:info")
    public R info(@PathVariable("itemId") Long itemId){
        DonateSpaceProcess donateSpaceProcess = donateSpaceProcessService.get(itemId);
        return R.ok().put("donateSpaceProcess", donateSpaceProcess);
    }

    /**
	 * 保存
	 */
    @ResponseBody
    @SysLog("保存")
	@RequestMapping("/save")
	@RequiresPermissions("donatespaceprocess:save")
	public R save(@RequestBody DonateSpaceProcess donateSpaceProcess){
		donateSpaceProcessService.save(donateSpaceProcess);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
    @ResponseBody
    @SysLog("修改")
	@RequestMapping("/update")
	@RequiresPermissions("donatespaceprocess:update")
	public R update(@RequestBody DonateSpaceProcess donateSpaceProcess){
		donateSpaceProcessService.update(donateSpaceProcess);
		
		return R.ok();
	}

    /**
     * 启用
     */
    @ResponseBody
    @SysLog("启用")
    @RequestMapping("/enable")
    @RequiresPermissions("donatespaceprocess:update")
    public R enable(@RequestBody String[] ids){
        String stateValue=StateEnum.ENABLE.getCode();
		donateSpaceProcessService.updateState(ids,stateValue);
        return R.ok();
    }
    /**
     * 禁用
     */
    @ResponseBody
    @SysLog("禁用")
    @RequestMapping("/limit")
    @RequiresPermissions("donatespaceprocess:update")
    public R limit(@RequestBody String[] ids){
        String stateValue=StateEnum.LIMIT.getCode();
		donateSpaceProcessService.updateState(ids,stateValue);
        return R.ok();
    }
	
	/**
	 * 删除
	 */
    @ResponseBody
    @SysLog("删除")
	@RequestMapping("/delete")
	@RequiresPermissions("donatespaceprocess:delete")
	public R delete(@RequestBody Long[] itemIds){
		donateSpaceProcessService.deleteBatch(itemIds);
		
		return R.ok();
	}
	
}
