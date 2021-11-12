package xin.xiaoer.modules.activity.controller;

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

import xin.xiaoer.modules.activity.entity.ActivitySignNumber;
import xin.xiaoer.modules.activity.service.ActivitySignNumberService;
import xin.xiaoer.common.utils.PageUtils;
import xin.xiaoer.common.utils.Query;
import xin.xiaoer.common.utils.R;


/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-10-04 00:05:33
 */
@Controller
@RequestMapping("activitysignnumber")
public class ActivitySignNumberController {
	@Autowired
	private ActivitySignNumberService activitySignNumberService;
	
    /**
     * 跳转到列表页
     */
    @RequestMapping("/list")
    @RequiresPermissions("activitysignnumber:list")
    public String list() {
        return "activitysignnumber/list";
    }
    
	/**
	 * 列表数据
	 */
    @ResponseBody
	@RequestMapping("/listData")
	@RequiresPermissions("activitysignnumber:list")
	public R listData(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<ActivitySignNumber> activitySignNumberList = activitySignNumberService.getList(query);
		int total = activitySignNumberService.getCount(query);
		
		PageUtils pageUtil = new PageUtils(activitySignNumberList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}

    /**
     * 跳转到新增页面
     **/
    @RequestMapping("/add")
    @RequiresPermissions("activitysignnumber:save")
    public String add(){
        return "activitysignnumber/add";
    }

    /**
     *   跳转到修改页面
     **/
    @RequestMapping("/edit/{id}")
    @RequiresPermissions("activitysignnumber:update")
    public String edit(Model model, @PathVariable("id") Integer id){
		ActivitySignNumber activitySignNumber = activitySignNumberService.get(id);
        model.addAttribute("model",activitySignNumber);
        return "activitysignnumber/edit";
    }

	/**
	 * 信息
	 */
    @ResponseBody
    @RequestMapping("/info/{activityId}")
    @RequiresPermissions("activitysignnumber:info")
    public R info(@PathVariable("activityId") Integer activityId){
        ActivitySignNumber activitySignNumber = activitySignNumberService.get(activityId);
        return R.ok().put("activitySignNumber", activitySignNumber);
    }

    /**
	 * 保存
	 */
    @ResponseBody
    @SysLog("保存")
	@RequestMapping("/save")
	@RequiresPermissions("activitysignnumber:save")
	public R save(@RequestBody ActivitySignNumber activitySignNumber){
		activitySignNumberService.save(activitySignNumber);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
    @ResponseBody
    @SysLog("修改")
	@RequestMapping("/update")
	@RequiresPermissions("activitysignnumber:update")
	public R update(@RequestBody ActivitySignNumber activitySignNumber){
		activitySignNumberService.update(activitySignNumber);
		
		return R.ok();
	}

    /**
     * 启用
     */
    @ResponseBody
    @SysLog("启用")
    @RequestMapping("/enable")
    @RequiresPermissions("activitysignnumber:update")
    public R enable(@RequestBody String[] ids){
        String stateValue=StateEnum.ENABLE.getCode();
		activitySignNumberService.updateState(ids,stateValue);
        return R.ok();
    }
    /**
     * 禁用
     */
    @ResponseBody
    @SysLog("禁用")
    @RequestMapping("/limit")
    @RequiresPermissions("activitysignnumber:update")
    public R limit(@RequestBody String[] ids){
        String stateValue=StateEnum.LIMIT.getCode();
		activitySignNumberService.updateState(ids,stateValue);
        return R.ok();
    }
	
	/**
	 * 删除
	 */
    @ResponseBody
    @SysLog("删除")
	@RequestMapping("/delete")
	@RequiresPermissions("activitysignnumber:delete")
	public R delete(@RequestBody Integer[] activityIds){
		activitySignNumberService.deleteBatch(activityIds);
		
		return R.ok();
	}
	
}
