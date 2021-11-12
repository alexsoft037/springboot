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

import xin.xiaoer.modules.monitor.entity.ChildrenWatich;
import xin.xiaoer.modules.monitor.service.ChildrenWatichService;
import xin.xiaoer.common.utils.PageUtils;
import xin.xiaoer.common.utils.Query;
import xin.xiaoer.common.utils.R;


/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-09-04 11:22:31
 */
@Controller
@RequestMapping("childrenwatich")
public class ChildrenWatichController {
	@Autowired
	private ChildrenWatichService childrenWatichService;
	
    /**
     * 跳转到列表页
     */
    @RequestMapping("/list")
    @RequiresPermissions("childrenwatich:list")
    public String list() {
        return "childrenwatich/list";
    }
    
	/**
	 * 列表数据
	 */
    @ResponseBody
	@RequestMapping("/listData")
	@RequiresPermissions("childrenwatich:list")
	public R listData(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<ChildrenWatich> childrenWatichList = childrenWatichService.getList(query);
		int total = childrenWatichService.getCount(query);
		
		PageUtils pageUtil = new PageUtils(childrenWatichList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}

    /**
     * 跳转到新增页面
     **/
    @RequestMapping("/add")
    @RequiresPermissions("childrenwatich:save")
    public String add(){
        return "childrenwatich/add";
    }

    /**
     *   跳转到修改页面
     **/
    @RequestMapping("/edit/{id}")
    @RequiresPermissions("childrenwatich:update")
    public String edit(Model model, @PathVariable("id") String id){
		ChildrenWatich childrenWatich = childrenWatichService.get(Long.parseLong(id));
        model.addAttribute("model",childrenWatich);
        return "childrenwatich/edit";
    }

	/**
	 * 信息
	 */
    @ResponseBody
    @RequestMapping("/info/{watchId}")
    @RequiresPermissions("childrenwatich:info")
    public R info(@PathVariable("watchId") Long watchId){
        ChildrenWatich childrenWatich = childrenWatichService.get(watchId);
        return R.ok().put("childrenWatich", childrenWatich);
    }

    /**
	 * 保存
	 */
    @ResponseBody
    @SysLog("保存")
	@RequestMapping("/save")
	@RequiresPermissions("childrenwatich:save")
	public R save(@RequestBody ChildrenWatich childrenWatich){
		childrenWatichService.save(childrenWatich);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
    @ResponseBody
    @SysLog("修改")
	@RequestMapping("/update")
	@RequiresPermissions("childrenwatich:update")
	public R update(@RequestBody ChildrenWatich childrenWatich){
		childrenWatichService.update(childrenWatich);
		
		return R.ok();
	}

    /**
     * 启用
     */
    @ResponseBody
    @SysLog("启用")
    @RequestMapping("/enable")
    @RequiresPermissions("childrenwatich:update")
    public R enable(@RequestBody String[] ids){
        String stateValue=StateEnum.ENABLE.getCode();
		childrenWatichService.updateState(ids,stateValue);
        return R.ok();
    }
    /**
     * 禁用
     */
    @ResponseBody
    @SysLog("禁用")
    @RequestMapping("/limit")
    @RequiresPermissions("childrenwatich:update")
    public R limit(@RequestBody String[] ids){
        String stateValue=StateEnum.LIMIT.getCode();
		childrenWatichService.updateState(ids,stateValue);
        return R.ok();
    }
	
	/**
	 * 删除
	 */
    @ResponseBody
    @SysLog("删除")
	@RequestMapping("/delete")
	@RequiresPermissions("childrenwatich:delete")
	public R delete(@RequestBody Long[] watchIds){
		childrenWatichService.deleteBatch(watchIds);
		
		return R.ok();
	}
	
}
