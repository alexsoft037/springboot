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

import xin.xiaoer.common.utils.YunXinUtil;
import xin.xiaoer.modules.chat.entity.ChatGroup;
import xin.xiaoer.modules.chat.service.ChatGroupService;
import xin.xiaoer.common.utils.PageUtils;
import xin.xiaoer.common.utils.Query;
import xin.xiaoer.common.utils.R;


/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-09-12 18:21:56
 */
@Controller
@RequestMapping("chatgroup")
public class ChatGroupController {
	@Autowired
	private ChatGroupService chatGroupService;
	
    /**
     * 跳转到列表页
     */
    @RequestMapping("/list")
    @RequiresPermissions("chatgroup:list")
    public String list() {
        return "chatgroup/list";
    }
    
	/**
	 * 列表数据
	 */
    @ResponseBody
	@RequestMapping("/listData")
	@RequiresPermissions("chatgroup:list")
	public R listData(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<ChatGroup> chatGroupList = chatGroupService.getAdminList(query);
		int total = chatGroupService.getAdminCount(query);
		
		PageUtils pageUtil = new PageUtils(chatGroupList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}

    /**
     * 跳转到新增页面
     **/
    @RequestMapping("/add")
    @RequiresPermissions("chatgroup:save")
    public String add(){
        return "chatgroup/add";
    }

    /**
     *   跳转到修改页面
     **/
    @RequestMapping("/edit/{id}")
    @RequiresPermissions("chatgroup:update")
    public String edit(Model model, @PathVariable("id") Integer id){
		ChatGroup chatGroup = chatGroupService.get(id);
        model.addAttribute("model",chatGroup);
        return "chatgroup/edit";
    }

	/**
	 * 信息
	 */
    @ResponseBody
    @RequestMapping("/info/{spaceId}")
    @RequiresPermissions("chatgroup:info")
    public R info(@PathVariable("spaceId") Integer spaceId){
        ChatGroup chatGroup = chatGroupService.get(spaceId);
        return R.ok().put("chatGroup", chatGroup);
    }

    /**
	 * 保存
	 */
    @ResponseBody
    @SysLog("保存")
	@RequestMapping("/save")
	@RequiresPermissions("chatgroup:save")
	public R save(@RequestBody ChatGroup chatGroup){
		chatGroupService.save(chatGroup);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
    @ResponseBody
    @SysLog("修改")
	@RequestMapping("/update")
	@RequiresPermissions("chatgroup:update")
	public R update(@RequestBody ChatGroup chatGroup){
		chatGroupService.update(chatGroup);
        chatGroup = chatGroupService.get(chatGroup.getSpaceId());
        YunXinUtil yunXinUtil = new YunXinUtil();
        try {
            yunXinUtil.updateGroup(chatGroup);
        }catch (Exception e){
            e.printStackTrace();
        }

		return R.ok();
	}

    /**
     * 启用
     */
    @ResponseBody
    @SysLog("启用")
    @RequestMapping("/enable")
    @RequiresPermissions("chatgroup:update")
    public R enable(@RequestBody String[] ids){
        String stateValue=StateEnum.ENABLE.getCode();
		chatGroupService.updateState(ids,stateValue);
        return R.ok();
    }
    /**
     * 禁用
     */
    @ResponseBody
    @SysLog("禁用")
    @RequestMapping("/limit")
    @RequiresPermissions("chatgroup:update")
    public R limit(@RequestBody String[] ids){
        String stateValue=StateEnum.LIMIT.getCode();
		chatGroupService.updateState(ids,stateValue);
        return R.ok();
    }
	
	/**
	 * 删除
	 */
    @ResponseBody
    @SysLog("删除")
	@RequestMapping("/delete")
	@RequiresPermissions("chatgroup:delete")
	public R delete(@RequestBody Integer[] spaceIds){
		chatGroupService.deleteBatch(spaceIds);
		
		return R.ok();
	}
	
}
