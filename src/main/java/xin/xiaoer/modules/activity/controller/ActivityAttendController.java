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

import xin.xiaoer.modules.activity.entity.ActivityAttend;
import xin.xiaoer.modules.activity.service.ActivityAttendService;
import xin.xiaoer.common.utils.PageUtils;
import xin.xiaoer.common.utils.Query;
import xin.xiaoer.common.utils.R;


/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-07-24 12:11:49
 */
@Controller
@RequestMapping("activityattend")
public class ActivityAttendController {
	@Autowired
	private ActivityAttendService activityAttendService;
	
    /**
     * 跳转到列表页
     */
    @RequestMapping("/list")
    @RequiresPermissions("activityattend:list")
    public String list(Model model, @RequestParam Map<String, Object> params) {

        String activityId = "";
        if (params.get("activityId") != null){
            activityId = params.get("activityId").toString();
        }

        model.addAttribute("activityId", activityId);

        return "activityattend/list";
    }
    
	/**
	 * 列表数据
	 */
    @ResponseBody
	@RequestMapping("/listData")
	@RequiresPermissions("activityattend:list")
	public R listData(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<ActivityAttend> activityAttendList = activityAttendService.getList(query);
		int total = activityAttendService.getCount(query);
		
		PageUtils pageUtil = new PageUtils(activityAttendList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}

    /**
     * 跳转到新增页面
     **/
    @RequestMapping("/add")
    @RequiresPermissions("activityattend:save")
    public String add(){
        return "activityattend/add";
    }

    /**
     *   跳转到修改页面
     **/
    @RequestMapping("/edit/{id}")
    @RequiresPermissions("activityattend:update")
    public String edit(Model model, @PathVariable("id") String id){
		ActivityAttend activityAttend = activityAttendService.get(Long.parseLong(id));
        model.addAttribute("model",activityAttend);
        return "activityattend/edit";
    }

	/**
	 * 信息
	 */
    @ResponseBody
    @RequestMapping("/info/{id}")
    @RequiresPermissions("activityattend:info")
    public R info(@PathVariable("id") Long id){
        ActivityAttend activityAttend = activityAttendService.get(id);
        return R.ok().put("activityAttend", activityAttend);
    }

    /**
	 * 保存
	 */
    @ResponseBody
    @SysLog("保存")
	@RequestMapping("/save")
	@RequiresPermissions("activityattend:save")
	public R save(@RequestBody ActivityAttend activityAttend){
		activityAttendService.save(activityAttend);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
    @ResponseBody
    @SysLog("修改")
	@RequestMapping("/update")
	@RequiresPermissions("activityattend:update")
	public R update(@RequestBody ActivityAttend activityAttend){
		activityAttendService.update(activityAttend);
		
		return R.ok();
	}

    /**
     * 启用
     */
    @ResponseBody
    @SysLog("启用")
    @RequestMapping("/enable")
    @RequiresPermissions("activityattend:update")
    public R enable(@RequestBody String[] ids){
        String stateValue=StateEnum.ENABLE.getCode();
		activityAttendService.updateState(ids,stateValue);
        return R.ok();
    }
    /**
     * 禁用
     */
    @ResponseBody
    @SysLog("禁用")
    @RequestMapping("/limit")
    @RequiresPermissions("activityattend:update")
    public R limit(@RequestBody String[] ids){
        String stateValue=StateEnum.LIMIT.getCode();
		activityAttendService.updateState(ids,stateValue);
        return R.ok();
    }
	
	/**
	 * 删除
	 */
    @ResponseBody
    @SysLog("删除")
	@RequestMapping("/delete")
	@RequiresPermissions("activityattend:delete")
	public R delete(@RequestBody Long[] ids){
		activityAttendService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
