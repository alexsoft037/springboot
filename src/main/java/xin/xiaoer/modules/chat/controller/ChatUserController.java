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

import xin.xiaoer.modules.chat.entity.ChatUser;
import xin.xiaoer.modules.chat.service.ChatUserService;
import xin.xiaoer.common.utils.PageUtils;
import xin.xiaoer.common.utils.Query;
import xin.xiaoer.common.utils.R;


/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-09-11 22:35:19
 */
@Controller
@RequestMapping("chatuser")
public class ChatUserController {
	@Autowired
	private ChatUserService chatUserService;
	
    /**
     * 跳转到列表页
     */
    @RequestMapping("/list")
    @RequiresPermissions("chatuser:list")
    public String list() {
        return "chatuser/list";
    }
    
	/**
	 * 列表数据
	 */
    @ResponseBody
	@RequestMapping("/listData")
	@RequiresPermissions("chatuser:list")
	public R listData(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<ChatUser> chatUserList = chatUserService.getList(query);
		int total = chatUserService.getCount(query);
		
		PageUtils pageUtil = new PageUtils(chatUserList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}

    /**
     * 跳转到新增页面
     **/
    @RequestMapping("/add")
    @RequiresPermissions("chatuser:save")
    public String add(){
        return "chatuser/add";
    }

    /**
     *   跳转到修改页面
     **/
    @RequestMapping("/edit/{id}")
    @RequiresPermissions("chatuser:update")
    public String edit(Model model, @PathVariable("id") String id){
		ChatUser chatUser = chatUserService.get(Long.parseLong(id));
        model.addAttribute("model",chatUser);
        return "chatuser/edit";
    }

	/**
	 * 信息
	 */
    @ResponseBody
    @RequestMapping("/info/{userId}")
    @RequiresPermissions("chatuser:info")
    public R info(@PathVariable("userId") Long userId){
        ChatUser chatUser = chatUserService.get(userId);
        return R.ok().put("chatUser", chatUser);
    }

    /**
	 * 保存
	 */
    @ResponseBody
    @SysLog("保存")
	@RequestMapping("/save")
	@RequiresPermissions("chatuser:save")
	public R save(@RequestBody ChatUser chatUser){
		chatUserService.save(chatUser);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
    @ResponseBody
    @SysLog("修改")
	@RequestMapping("/update")
	@RequiresPermissions("chatuser:update")
	public R update(@RequestBody ChatUser chatUser){
		chatUserService.update(chatUser);
		
		return R.ok();
	}

    /**
     * 启用
     */
    @ResponseBody
    @SysLog("启用")
    @RequestMapping("/enable")
    @RequiresPermissions("chatuser:update")
    public R enable(@RequestBody String[] ids){
        String stateValue=StateEnum.ENABLE.getCode();
		chatUserService.updateState(ids,stateValue);
        return R.ok();
    }
    /**
     * 禁用
     */
    @ResponseBody
    @SysLog("禁用")
    @RequestMapping("/limit")
    @RequiresPermissions("chatuser:update")
    public R limit(@RequestBody String[] ids){
        String stateValue=StateEnum.LIMIT.getCode();
		chatUserService.updateState(ids,stateValue);
        return R.ok();
    }
	
	/**
	 * 删除
	 */
    @ResponseBody
    @SysLog("删除")
	@RequestMapping("/delete")
	@RequiresPermissions("chatuser:delete")
	public R delete(@RequestBody Long[] userIds){
		chatUserService.deleteBatch(userIds);
		
		return R.ok();
	}
	
}
