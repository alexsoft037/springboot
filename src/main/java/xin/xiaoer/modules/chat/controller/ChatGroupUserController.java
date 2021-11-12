package xin.xiaoer.modules.chat.controller;

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

import xin.xiaoer.modules.chat.entity.ChatGroupUser;
import xin.xiaoer.modules.chat.service.ChatGroupUserService;
import xin.xiaoer.common.utils.PageUtils;
import xin.xiaoer.common.utils.Query;
import xin.xiaoer.common.utils.R;


/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-09-12 18:28:36
 */
@Controller
@RequestMapping("chatgroupuser")
public class ChatGroupUserController {
	@Autowired
	private ChatGroupUserService chatGroupUserService;
	
    /**
     * 跳转到列表页
     */
    @RequestMapping("/list")
    @RequiresPermissions("chatgroupuser:list")
    public String list() {
        return "chatgroupuser/list";
    }
    
	/**
	 * 列表数据
	 */
    @ResponseBody
	@RequestMapping("/listData")
	@RequiresPermissions("chatgroupuser:list")
	public R listData(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<ChatGroupUser> chatGroupUserList = chatGroupUserService.getList(query);
		int total = chatGroupUserService.getCount(query);
		
		PageUtils pageUtil = new PageUtils(chatGroupUserList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}

    /**
     * 跳转到新增页面
     **/
    @RequestMapping("/add")
    @RequiresPermissions("chatgroupuser:save")
    public String add(){
        return "chatgroupuser/add";
    }

    /**
     *   跳转到修改页面
     **/
    @RequestMapping("/edit/{id}")
    @RequiresPermissions("chatgroupuser:update")
    public String edit(Model model, @PathVariable("id") Long id){
		ChatGroupUser chatGroupUser = chatGroupUserService.get(id);
        model.addAttribute("model",chatGroupUser);
        return "chatgroupuser/edit";
    }

	/**
	 * 信息
	 */
    @ResponseBody
    @RequestMapping("/info/{id}")
    @RequiresPermissions("chatgroupuser:info")
    public R info(@PathVariable("id") Long id){
        ChatGroupUser chatGroupUser = chatGroupUserService.get(id);
        return R.ok().put("chatGroupUser", chatGroupUser);
    }

    /**
	 * 保存
	 */
    @ResponseBody
    @SysLog("保存")
	@RequestMapping("/save")
	@RequiresPermissions("chatgroupuser:save")
	public R save(@RequestBody ChatGroupUser chatGroupUser){
		chatGroupUserService.save(chatGroupUser);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
    @ResponseBody
    @SysLog("修改")
	@RequestMapping("/update")
	@RequiresPermissions("chatgroupuser:update")
	public R update(@RequestBody ChatGroupUser chatGroupUser){
		chatGroupUserService.update(chatGroupUser);
		
		return R.ok();
	}

    /**
     * 启用
     */
    @ResponseBody
    @SysLog("启用")
    @RequestMapping("/enable")
    @RequiresPermissions("chatgroupuser:update")
    public R enable(@RequestBody String[] ids){
        String stateValue=StateEnum.ENABLE.getCode();
		chatGroupUserService.updateState(ids,stateValue);
        return R.ok();
    }
    /**
     * 禁用
     */
    @ResponseBody
    @SysLog("禁用")
    @RequestMapping("/limit")
    @RequiresPermissions("chatgroupuser:update")
    public R limit(@RequestBody String[] ids){
        String stateValue=StateEnum.LIMIT.getCode();
		chatGroupUserService.updateState(ids,stateValue);
        return R.ok();
    }
	
	/**
	 * 删除
	 */
    @ResponseBody
    @SysLog("删除")
	@RequestMapping("/delete")
	@RequiresPermissions("chatgroupuser:delete")
	public R delete(@RequestBody Long[] ids){
		chatGroupUserService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
