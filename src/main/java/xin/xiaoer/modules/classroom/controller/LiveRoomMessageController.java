package xin.xiaoer.modules.classroom.controller;

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

import xin.xiaoer.modules.classroom.entity.LiveRoomMessage;
import xin.xiaoer.modules.classroom.service.LiveRoomMessageService;
import xin.xiaoer.common.utils.PageUtils;
import xin.xiaoer.common.utils.Query;
import xin.xiaoer.common.utils.R;


/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-10-06 13:14:35
 */
@Controller
@RequestMapping("liveroommessage")
public class LiveRoomMessageController {
	@Autowired
	private LiveRoomMessageService liveRoomMessageService;
	
    /**
     * 跳转到列表页
     */
    @RequestMapping("/list")
    @RequiresPermissions("liveroommessage:list")
    public String list() {
        return "liveroommessage/list";
    }
    
	/**
	 * 列表数据
	 */
    @ResponseBody
	@RequestMapping("/listData")
	@RequiresPermissions("liveroommessage:list")
	public R listData(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<LiveRoomMessage> liveRoomMessageList = liveRoomMessageService.getList(query);
		int total = liveRoomMessageService.getCount(query);
		
		PageUtils pageUtil = new PageUtils(liveRoomMessageList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}

    /**
     * 跳转到新增页面
     **/
    @RequestMapping("/add")
    @RequiresPermissions("liveroommessage:save")
    public String add(){
        return "liveroommessage/add";
    }

    /**
     *   跳转到修改页面
     **/
    @RequestMapping("/edit/{id}")
    @RequiresPermissions("liveroommessage:update")
    public String edit(Model model, @PathVariable("id") Integer id){
		LiveRoomMessage liveRoomMessage = liveRoomMessageService.get(id);
        model.addAttribute("model",liveRoomMessage);
        return "liveroommessage/edit";
    }

	/**
	 * 信息
	 */
    @ResponseBody
    @RequestMapping("/info/{liveId}")
    @RequiresPermissions("liveroommessage:info")
    public R info(@PathVariable("liveId") Integer liveId){
        LiveRoomMessage liveRoomMessage = liveRoomMessageService.get(liveId);
        return R.ok().put("liveRoomMessage", liveRoomMessage);
    }

    /**
	 * 保存
	 */
    @ResponseBody
    @SysLog("保存")
	@RequestMapping("/save")
	@RequiresPermissions("liveroommessage:save")
	public R save(@RequestBody LiveRoomMessage liveRoomMessage){
		liveRoomMessageService.save(liveRoomMessage);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
    @ResponseBody
    @SysLog("修改")
	@RequestMapping("/update")
	@RequiresPermissions("liveroommessage:update")
	public R update(@RequestBody LiveRoomMessage liveRoomMessage){
		liveRoomMessageService.update(liveRoomMessage);
		
		return R.ok();
	}

    /**
     * 启用
     */
    @ResponseBody
    @SysLog("启用")
    @RequestMapping("/enable")
    @RequiresPermissions("liveroommessage:update")
    public R enable(@RequestBody String[] ids){
        String stateValue=StateEnum.ENABLE.getCode();
		liveRoomMessageService.updateState(ids,stateValue);
        return R.ok();
    }
    /**
     * 禁用
     */
    @ResponseBody
    @SysLog("禁用")
    @RequestMapping("/limit")
    @RequiresPermissions("liveroommessage:update")
    public R limit(@RequestBody String[] ids){
        String stateValue=StateEnum.LIMIT.getCode();
		liveRoomMessageService.updateState(ids,stateValue);
        return R.ok();
    }
	
	/**
	 * 删除
	 */
    @ResponseBody
    @SysLog("删除")
	@RequestMapping("/delete")
	@RequiresPermissions("liveroommessage:delete")
	public R delete(@RequestBody Integer[] liveIds){
		liveRoomMessageService.deleteBatch(liveIds);
		
		return R.ok();
	}
	
}
