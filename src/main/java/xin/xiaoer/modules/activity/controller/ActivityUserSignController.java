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

import xin.xiaoer.modules.activity.entity.ActivityUserSign;
import xin.xiaoer.modules.activity.service.ActivityUserSignService;
import xin.xiaoer.common.utils.PageUtils;
import xin.xiaoer.common.utils.Query;
import xin.xiaoer.common.utils.R;


/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-10-08 01:01:48
 */
@Controller
@RequestMapping("activityusersign")
public class ActivityUserSignController {
	@Autowired
	private ActivityUserSignService activityUserSignService;
	
    /**
     * 跳转到列表页
     */
    @RequestMapping("/list")
    @RequiresPermissions("activityusersign:list")
    public String list() {
        return "activityusersign/list";
    }
    
	/**
	 * 列表数据
	 */
    @ResponseBody
	@RequestMapping("/listData")
	@RequiresPermissions("activityusersign:list")
	public R listData(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<ActivityUserSign> activityUserSignList = activityUserSignService.getList(query);
		int total = activityUserSignService.getCount(query);
		
		PageUtils pageUtil = new PageUtils(activityUserSignList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}

    /**
     * 跳转到新增页面
     **/
    @RequestMapping("/add")
    @RequiresPermissions("activityusersign:save")
    public String add(){
        return "activityusersign/add";
    }

    /**
     *   跳转到修改页面
     **/
    @RequestMapping("/edit/{id}")
    @RequiresPermissions("activityusersign:update")
    public String edit(Model model, @PathVariable("id") Long id){
		ActivityUserSign activityUserSign = activityUserSignService.get(id);
        model.addAttribute("model",activityUserSign);
        return "activityusersign/edit";
    }

	/**
	 * 信息
	 */
    @ResponseBody
    @RequestMapping("/info/{id}")
    @RequiresPermissions("activityusersign:info")
    public R info(@PathVariable("id") Long id){
        ActivityUserSign activityUserSign = activityUserSignService.get(id);
        return R.ok().put("activityUserSign", activityUserSign);
    }

    /**
	 * 保存
	 */
    @ResponseBody
    @SysLog("保存")
	@RequestMapping("/save")
	@RequiresPermissions("activityusersign:save")
	public R save(@RequestBody ActivityUserSign activityUserSign){
		activityUserSignService.save(activityUserSign);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
    @ResponseBody
    @SysLog("修改")
	@RequestMapping("/update")
	@RequiresPermissions("activityusersign:update")
	public R update(@RequestBody ActivityUserSign activityUserSign){
		activityUserSignService.update(activityUserSign);
		
		return R.ok();
	}

    /**
     * 启用
     */
    @ResponseBody
    @SysLog("启用")
    @RequestMapping("/enable")
    @RequiresPermissions("activityusersign:update")
    public R enable(@RequestBody String[] ids){
        String stateValue=StateEnum.ENABLE.getCode();
		activityUserSignService.updateState(ids,stateValue);
        return R.ok();
    }
    /**
     * 禁用
     */
    @ResponseBody
    @SysLog("禁用")
    @RequestMapping("/limit")
    @RequiresPermissions("activityusersign:update")
    public R limit(@RequestBody String[] ids){
        String stateValue=StateEnum.LIMIT.getCode();
		activityUserSignService.updateState(ids,stateValue);
        return R.ok();
    }
	
	/**
	 * 删除
	 */
    @ResponseBody
    @SysLog("删除")
	@RequestMapping("/delete")
	@RequiresPermissions("activityusersign:delete")
	public R delete(@RequestBody Long[] ids){
		activityUserSignService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
