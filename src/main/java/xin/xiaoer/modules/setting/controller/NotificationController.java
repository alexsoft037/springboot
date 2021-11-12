package xin.xiaoer.modules.setting.controller;

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

import xin.xiaoer.modules.setting.entity.Notification;
import xin.xiaoer.modules.setting.service.NotificationService;
import xin.xiaoer.common.utils.PageUtils;
import xin.xiaoer.common.utils.Query;
import xin.xiaoer.common.utils.R;


/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-10-16 22:21:41
 */
@Controller
@RequestMapping("notification")
public class NotificationController {
	@Autowired
	private NotificationService notificationService;
	
    /**
     * 跳转到列表页
     */
    @RequestMapping("/list")
    @RequiresPermissions("notification:list")
    public String list() {
        return "notification/list";
    }
    
	/**
	 * 列表数据
	 */
    @ResponseBody
	@RequestMapping("/listData")
	@RequiresPermissions("notification:list")
	public R listData(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<Notification> notificationList = notificationService.getList(query);
		int total = notificationService.getCount(query);
		
		PageUtils pageUtil = new PageUtils(notificationList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}

    /**
     * 跳转到新增页面
     **/
    @RequestMapping("/add")
    @RequiresPermissions("notification:save")
    public String add(){
        return "notification/add";
    }

    /**
     *   跳转到修改页面
     **/
    @RequestMapping("/edit/{id}")
    @RequiresPermissions("notification:update")
    public String edit(Model model, @PathVariable("id") Long id){
		Notification notification = notificationService.get(id);
        model.addAttribute("model",notification);
        return "notification/edit";
    }

	/**
	 * 信息
	 */
    @ResponseBody
    @RequestMapping("/info/{noteId}")
    @RequiresPermissions("notification:info")
    public R info(@PathVariable("noteId") Long noteId){
        Notification notification = notificationService.get(noteId);
        return R.ok().put("notification", notification);
    }

    /**
	 * 保存
	 */
    @ResponseBody
    @SysLog("保存")
	@RequestMapping("/save")
	@RequiresPermissions("notification:save")
	public R save(@RequestBody Notification notification){
		notificationService.save(notification);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
    @ResponseBody
    @SysLog("修改")
	@RequestMapping("/update")
	@RequiresPermissions("notification:update")
	public R update(@RequestBody Notification notification){
		notificationService.update(notification);
		
		return R.ok();
	}

    /**
     * 启用
     */
    @ResponseBody
    @SysLog("启用")
    @RequestMapping("/enable")
    @RequiresPermissions("notification:update")
    public R enable(@RequestBody String[] ids){
        String stateValue=StateEnum.ENABLE.getCode();
		notificationService.updateState(ids,stateValue);
        return R.ok();
    }
    /**
     * 禁用
     */
    @ResponseBody
    @SysLog("禁用")
    @RequestMapping("/limit")
    @RequiresPermissions("notification:update")
    public R limit(@RequestBody String[] ids){
        String stateValue=StateEnum.LIMIT.getCode();
		notificationService.updateState(ids,stateValue);
        return R.ok();
    }
	
	/**
	 * 删除
	 */
    @ResponseBody
    @SysLog("删除")
	@RequestMapping("/delete")
	@RequiresPermissions("notification:delete")
	public R delete(@RequestBody Long[] noteIds){
		notificationService.deleteBatch(noteIds);
		
		return R.ok();
	}
	
}
