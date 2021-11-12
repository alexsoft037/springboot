package xin.xiaoer.modules.guoshan.controller;

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

import xin.xiaoer.modules.guoshan.entity.GuoshanUser;
import xin.xiaoer.modules.guoshan.service.GuoshanUserService;
import xin.xiaoer.common.utils.PageUtils;
import xin.xiaoer.common.utils.Query;
import xin.xiaoer.common.utils.R;


/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-08-03 12:44:53
 */
@Controller
@RequestMapping("guoshanuser")
public class GuoshanUserController {
	@Autowired
	private GuoshanUserService guoshanUserService;
	
    /**
     * 跳转到列表页
     */
    @RequestMapping("/list")
    @RequiresPermissions("guoshanuser:list")
    public String list() {
        return "guoshanuser/list";
    }
    
	/**
	 * 列表数据
	 */
    @ResponseBody
	@RequestMapping("/listData")
	@RequiresPermissions("guoshanuser:list")
	public R listData(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<GuoshanUser> guoshanUserList = guoshanUserService.getList(query);
		int total = guoshanUserService.getCount(query);
		
		PageUtils pageUtil = new PageUtils(guoshanUserList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}

    /**
     * 跳转到新增页面
     **/
    @RequestMapping("/add")
    @RequiresPermissions("guoshanuser:save")
    public String add(){
        return "guoshanuser/add";
    }

    /**
     *   跳转到修改页面
     **/
    @RequestMapping("/edit/{id}")
    @RequiresPermissions("guoshanuser:update")
    public String edit(Model model, @PathVariable("id") String id){
		GuoshanUser guoshanUser = guoshanUserService.get(Long.parseLong(id));
        model.addAttribute("model",guoshanUser);
        return "guoshanuser/edit";
    }

	/**
	 * 信息
	 */
    @ResponseBody
    @RequestMapping("/info/{userId}")
    @RequiresPermissions("guoshanuser:info")
    public R info(@PathVariable("userId") Long userId){
        GuoshanUser guoshanUser = guoshanUserService.get(userId);
        return R.ok().put("guoshanUser", guoshanUser);
    }

    /**
	 * 保存
	 */
    @ResponseBody
    @SysLog("保存")
	@RequestMapping("/save")
	@RequiresPermissions("guoshanuser:save")
	public R save(@RequestBody GuoshanUser guoshanUser){
		guoshanUserService.save(guoshanUser);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
    @ResponseBody
    @SysLog("修改")
	@RequestMapping("/update")
	@RequiresPermissions("guoshanuser:update")
	public R update(@RequestBody GuoshanUser guoshanUser){
		guoshanUserService.update(guoshanUser);
		
		return R.ok();
	}

    /**
     * 启用
     */
    @ResponseBody
    @SysLog("启用")
    @RequestMapping("/enable")
    @RequiresPermissions("guoshanuser:update")
    public R enable(@RequestBody String[] ids){
        String stateValue=StateEnum.ENABLE.getCode();
		guoshanUserService.updateState(ids,stateValue);
        return R.ok();
    }
    /**
     * 禁用
     */
    @ResponseBody
    @SysLog("禁用")
    @RequestMapping("/limit")
    @RequiresPermissions("guoshanuser:update")
    public R limit(@RequestBody String[] ids){
        String stateValue=StateEnum.LIMIT.getCode();
		guoshanUserService.updateState(ids,stateValue);
        return R.ok();
    }
	
	/**
	 * 删除
	 */
    @ResponseBody
    @SysLog("删除")
	@RequestMapping("/delete")
	@RequiresPermissions("guoshanuser:delete")
	public R delete(@RequestBody Long[] userIds){
		guoshanUserService.deleteBatch(userIds);
		
		return R.ok();
	}
	
}
