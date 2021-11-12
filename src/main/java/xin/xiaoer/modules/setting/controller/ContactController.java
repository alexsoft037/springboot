package xin.xiaoer.modules.setting.controller;

import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import xin.xiaoer.common.enumresource.StateEnum;
import xin.xiaoer.common.log.SysLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import javax.servlet.http.HttpServletRequest;

import xin.xiaoer.modules.setting.entity.Contact;
import xin.xiaoer.modules.setting.service.ContactService;
import xin.xiaoer.common.utils.PageUtils;
import xin.xiaoer.common.utils.Query;
import xin.xiaoer.common.utils.R;


/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-09-23 20:48:27
 */
@Controller
@RequestMapping("contact")
public class ContactController {
	@Autowired
	private ContactService contactService;
	
    /**
     * 跳转到列表页
     */
    @RequestMapping("/list")
    @RequiresPermissions("contact:list")
    public String list() {
        return "contact/list";
    }
    
	/**
	 * 列表数据
	 */
    @ResponseBody
	@RequestMapping("/listData")
	@RequiresPermissions("contact:list")
	public R listData(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<Contact> contactList = contactService.getList(query);
		for (Contact contact:contactList){
            contact.setContent(Jsoup.parse(contact.getContent()).text());
        }
		int total = contactService.getCount(query);
		
		PageUtils pageUtil = new PageUtils(contactList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}

    /**
     * 跳转到新增页面
     **/
    @RequestMapping("/add")
    @RequiresPermissions("contact:save")
    public String add(){
        return "contact/add";
    }

    /**
     *   跳转到修改页面
     **/
    @RequestMapping("/edit/{id}")
    @RequiresPermissions("contact:update")
    public String edit(Model model, @PathVariable("id") Long id){
		Contact contact = contactService.get(id);
        model.addAttribute("model",contact);
        return "contact/edit";
    }

	/**
	 * 信息
	 */
    @RequestMapping("/info/{contactId}")
    @RequiresPermissions("contact:info")
    public String info(Model model, @PathVariable("contactId") Long contactId){
        Contact contact = contactService.get(contactId);
        model.addAttribute("model",contact);
        return "contact/info";
    }

    /**
	 * 保存
	 */
    @ResponseBody
    @SysLog("保存反馈")
	@RequestMapping("/save")
	@RequiresPermissions("contact:save")
	public R save(@RequestBody Contact contact){
		contactService.save(contact);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
    @ResponseBody
    @SysLog("修改反馈")
	@RequestMapping("/update")
	@RequiresPermissions("contact:update")
	public R update(@RequestBody Contact contact){
		contactService.update(contact);
		
		return R.ok();
	}

    /**
     * 启用
     */
    @ResponseBody
    @SysLog("启用反馈")
    @RequestMapping("/enable")
    @RequiresPermissions("contact:update")
    public R enable(@RequestBody String[] ids){
        String stateValue=StateEnum.ENABLE.getCode();
		contactService.updateState(ids,stateValue);
        return R.ok();
    }
    /**
     * 禁用
     */
    @ResponseBody
    @SysLog("禁用反馈")
    @RequestMapping("/limit")
    @RequiresPermissions("contact:update")
    public R limit(@RequestBody String[] ids){
        String stateValue=StateEnum.LIMIT.getCode();
		contactService.updateState(ids,stateValue);
        return R.ok();
    }
	
	/**
	 * 删除
	 */
    @ResponseBody
    @SysLog("删除反馈")
	@RequestMapping("/delete")
	@RequiresPermissions("contact:delete")
	public R delete(@RequestBody Long[] contactIds){
		contactService.deleteBatch(contactIds);
		
		return R.ok();
	}
	
}
