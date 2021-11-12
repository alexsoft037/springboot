package xin.xiaoer.modules.help.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import xin.xiaoer.common.enumresource.StateEnum;
import xin.xiaoer.common.log.SysLog;
import xin.xiaoer.common.shiro.ShiroUtils;
import xin.xiaoer.common.utils.PageUtils;
import xin.xiaoer.common.utils.Query;
import xin.xiaoer.common.utils.R;
import xin.xiaoer.modules.help.entity.HelpCenter;
import xin.xiaoer.modules.help.service.HelpCenterService;

import java.net.URLDecoder;
import java.util.List;
import java.util.Map;


/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-09-23 17:02:07
 */
@Controller
@RequestMapping("aboutus")
public class AboutUsController {
	@Autowired
	private HelpCenterService helpCenterService;
	
    /**
     * 跳转到列表页
     */
    @RequestMapping("/list")
    @RequiresPermissions("aboutus:list")
    public String list() {
        return "aboutus/list";
    }
    
	/**
	 * 列表数据
	 */
    @ResponseBody
	@RequestMapping("/listData")
	@RequiresPermissions("aboutus:list")
	public R listData(@RequestParam Map<String, Object> params){
		//查询列表数据
        params.put("category", "ABOUTUS");
        Query query = new Query(params);

		List<HelpCenter> helpCenterList = helpCenterService.getList(query);
		for (HelpCenter helpCenter: helpCenterList){
            helpCenter.setContent(Jsoup.parse(helpCenter.getContent()).text());
        }

		int total = helpCenterService.getCount(query);
		
		PageUtils pageUtil = new PageUtils(helpCenterList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}

    /**
     * 跳转到新增页面
     **/
    @RequestMapping("/add")
    @RequiresPermissions("aboutus:save")
    public String add(Model model, @RequestParam Map<String, Object> params){
        HelpCenter helpCenter = helpCenterService.getByCategory("ABOUTUS");
        model.addAttribute("model",helpCenter);
        return "aboutus/add";
    }

    /**
     *   跳转到修改页面
     **/
    @RequestMapping("/edit/{id}")
    @RequiresPermissions("aboutus:update")
    public String edit(Model model, @PathVariable("id") Integer id){
		HelpCenter helpCenter = helpCenterService.get(id);
        model.addAttribute("model",helpCenter);
        return "aboutus/edit";
    }

	/**
	 * 信息
	 */
    @ResponseBody
    @RequestMapping("/info/{helpId}")
    @RequiresPermissions("aboutus:info")
    public R info(@PathVariable("helpId") Integer helpId){
        HelpCenter helpCenter = helpCenterService.get(helpId);
        return R.ok().put("helpCenter", helpCenter);
    }

    /**
	 * 保存
	 */
    @ResponseBody
    @SysLog("关于我们保存")
	@RequestMapping("/save")
	@RequiresPermissions("aboutus:save")
	public R save(@RequestBody HelpCenter helpCenter){

        try {
            helpCenter.setContent(URLDecoder.decode(helpCenter.getContent(), "UTF-8"));
        } catch (Exception e){
            e.printStackTrace();
        }
        helpCenter.setCategory("ABOUTUS");
        helpCenter.setUpdateBy(ShiroUtils.getUserId());
        helpCenter.setCreateBy(ShiroUtils.getUserId());
        if (helpCenter.getHelpId() == 0){
            helpCenterService.save(helpCenter);
        } else {
            helpCenterService.update(helpCenter);
        }

		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
    @ResponseBody
    @SysLog("关于我们修改")
	@RequestMapping("/update")
	@RequiresPermissions("aboutus:update")
	public R update(@RequestBody HelpCenter helpCenter){
        try {
            helpCenter.setContent(URLDecoder.decode(helpCenter.getContent(), "UTF-8"));
        } catch (Exception e){
            e.printStackTrace();
        }
        helpCenter.setCategory("ABOUTUS");
        helpCenter.setUpdateBy(ShiroUtils.getUserId());
        helpCenter.setCreateBy(ShiroUtils.getUserId());
		helpCenterService.update(helpCenter);
		
		return R.ok();
	}

    /**
     * 启用
     */
    @ResponseBody
    @SysLog("关于我们启用")
    @RequestMapping("/enable")
    @RequiresPermissions("aboutus:update")
    public R enable(@RequestBody String[] ids){
        String stateValue=StateEnum.ENABLE.getCode();
		helpCenterService.updateState(ids,stateValue);
        return R.ok();
    }
    /**
     * 禁用
     */
    @ResponseBody
    @SysLog("关于我们禁用")
    @RequestMapping("/limit")
    @RequiresPermissions("aboutus:update")
    public R limit(@RequestBody String[] ids){
        String stateValue=StateEnum.LIMIT.getCode();
		helpCenterService.updateState(ids,stateValue);
        return R.ok();
    }
	
	/**
	 * 删除
	 */
    @ResponseBody
    @SysLog("关于我们删除")
	@RequestMapping("/delete")
	@RequiresPermissions("aboutus:delete")
	public R delete(@RequestBody Integer[] helpIds){
		helpCenterService.deleteBatch(helpIds);
		
		return R.ok();
	}
	
}
