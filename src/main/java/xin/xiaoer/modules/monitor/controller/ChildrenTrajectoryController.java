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

import xin.xiaoer.modules.monitor.entity.ChildrenTrajectory;
import xin.xiaoer.modules.monitor.service.ChildrenTrajectoryService;
import xin.xiaoer.common.utils.PageUtils;
import xin.xiaoer.common.utils.Query;
import xin.xiaoer.common.utils.R;


/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-09-03 12:51:23
 */
@Controller
@RequestMapping("childrentrajectory")
public class ChildrenTrajectoryController {
	@Autowired
	private ChildrenTrajectoryService childrenTrajectoryService;
	
    /**
     * 跳转到列表页
     */
    @RequestMapping("/list")
    @RequiresPermissions("childrentrajectory:list")
    public String list() {
        return "childrentrajectory/list";
    }
    
	/**
	 * 列表数据
	 */
    @ResponseBody
	@RequestMapping("/listData")
	@RequiresPermissions("childrentrajectory:list")
	public R listData(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<ChildrenTrajectory> childrenTrajectoryList = childrenTrajectoryService.getList(query);
		int total = childrenTrajectoryService.getCount(query);
		
		PageUtils pageUtil = new PageUtils(childrenTrajectoryList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}

    /**
     * 跳转到新增页面
     **/
    @RequestMapping("/add")
    @RequiresPermissions("childrentrajectory:save")
    public String add(){
        return "childrentrajectory/add";
    }

    /**
     *   跳转到修改页面
     **/
    @RequestMapping("/edit/{id}")
    @RequiresPermissions("childrentrajectory:update")
    public String edit(Model model, @PathVariable("id") Long id){
		ChildrenTrajectory childrenTrajectory = childrenTrajectoryService.get(id);
        model.addAttribute("model",childrenTrajectory);
        return "childrentrajectory/edit";
    }

	/**
	 * 信息
	 */
    @ResponseBody
    @RequestMapping("/info/{id}")
    @RequiresPermissions("childrentrajectory:info")
    public R info(@PathVariable("id") Long id){
        ChildrenTrajectory childrenTrajectory = childrenTrajectoryService.get(id);
        return R.ok().put("childrenTrajectory", childrenTrajectory);
    }

    /**
	 * 保存
	 */
    @ResponseBody
    @SysLog("保存")
	@RequestMapping("/save")
	@RequiresPermissions("childrentrajectory:save")
	public R save(@RequestBody ChildrenTrajectory childrenTrajectory){
		childrenTrajectoryService.save(childrenTrajectory);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
    @ResponseBody
    @SysLog("修改")
	@RequestMapping("/update")
	@RequiresPermissions("childrentrajectory:update")
	public R update(@RequestBody ChildrenTrajectory childrenTrajectory){
		childrenTrajectoryService.update(childrenTrajectory);
		
		return R.ok();
	}

    /**
     * 启用
     */
    @ResponseBody
    @SysLog("启用")
    @RequestMapping("/enable")
    @RequiresPermissions("childrentrajectory:update")
    public R enable(@RequestBody String[] ids){
        String stateValue=StateEnum.ENABLE.getCode();
		childrenTrajectoryService.updateState(ids,stateValue);
        return R.ok();
    }
    /**
     * 禁用
     */
    @ResponseBody
    @SysLog("禁用")
    @RequestMapping("/limit")
    @RequiresPermissions("childrentrajectory:update")
    public R limit(@RequestBody String[] ids){
        String stateValue=StateEnum.LIMIT.getCode();
		childrenTrajectoryService.updateState(ids,stateValue);
        return R.ok();
    }
	
	/**
	 * 删除
	 */
    @ResponseBody
    @SysLog("删除")
	@RequestMapping("/delete")
	@RequiresPermissions("childrentrajectory:delete")
	public R delete(@RequestBody Long[] ids){
		childrenTrajectoryService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
