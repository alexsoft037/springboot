package xin.xiaoer.modules.donatespace.controller;

import java.util.List;
import java.util.Map;
import xin.xiaoer.common.enumresource.StateEnum;
import xin.xiaoer.common.log.SysLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import xin.xiaoer.common.shiro.ShiroUtils;
import xin.xiaoer.entity.SysUser;
import xin.xiaoer.modules.donatespace.entity.DonateUser;
import xin.xiaoer.modules.donatespace.service.DonateUserService;
import xin.xiaoer.common.utils.PageUtils;
import xin.xiaoer.common.utils.Query;
import xin.xiaoer.common.utils.R;


/**
 * 
 * 
 * @author chenyi
 * @email 228112142@qq.com
 * @date 2018-07-20 00:22:00
 */
@Controller
@RequestMapping("donateuser")
public class DonateUserController {
	@Autowired
	private DonateUserService donateUserService;
	
    /**
     * 跳转到列表页
     */
    @RequestMapping("/list")
    @RequiresPermissions("donateuser:list")
    public String list(@RequestParam Map<String, Object> params, Model model) {
        String itemId = "";
        if (params.get("itemId") != null){
            itemId = params.get("itemId").toString();
        }
        model.addAttribute("itemId",itemId);
        return "donateuser/list";
    }
    
	/**
	 * 列表数据
	 */
    @ResponseBody
	@RequestMapping("/listData")
	@RequiresPermissions("donateuser:list")
	public R listData(@RequestParam Map<String, Object> params){
		//查询列表数据
        SysUser admin = ShiroUtils.getUserEntity();
        Query query = new Query(params);
        if (admin.getUserId() != 1L){
            query.put("spaceId", admin.getSpaceId());
        }
		List<DonateUser> donateUserList = donateUserService.getList(query);
		int total = donateUserService.getCount(query);
		
		PageUtils pageUtil = new PageUtils(donateUserList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}

    /**
     * 跳转到新增页面
     **/
    @RequestMapping("/add")
    @RequiresPermissions("donateuser:save")
    public String add(){
        return "donateuser/add";
    }

    /**
     *   跳转到修改页面
     **/
    @RequestMapping("/edit/{id}")
    @RequiresPermissions("donateuser:update")
    public String edit(Model model, @PathVariable("id") String id){
		DonateUser donateUser = donateUserService.get(Long.parseLong(id));
        model.addAttribute("model",donateUser);
        return "donateuser/edit";
    }

	/**
	 * 信息
	 */
    @ResponseBody
    @RequestMapping("/info/{id}")
    @RequiresPermissions("donateuser:info")
    public R info(@PathVariable("id") Long id){
        DonateUser donateUser = donateUserService.get(id);
        return R.ok().put("donateUser", donateUser);
    }

    /**
	 * 保存
	 */
    @ResponseBody
    @SysLog("保存爱心空间资金明细")
	@RequestMapping("/save")
	@RequiresPermissions("donateuser:save")
	public R save(@RequestBody DonateUser donateUser){
		donateUserService.save(donateUser);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
    @ResponseBody
    @SysLog("修改爱心空间资金明细")
	@RequestMapping("/update")
	@RequiresPermissions("donateuser:update")
	public R update(@RequestBody DonateUser donateUser){
		donateUserService.update(donateUser);
		
		return R.ok();
	}

    /**
     * 启用
     */
    @ResponseBody
    @SysLog("启用爱心空间资金明细")
    @RequestMapping("/enable")
    @RequiresPermissions("donateuser:update")
    public R enable(@RequestBody String[] ids){
        String stateValue=StateEnum.ENABLE.getCode();
		donateUserService.updateState(ids,stateValue);
        return R.ok();
    }
    /**
     * 禁用
     */
    @ResponseBody
    @SysLog("禁用爱心空间资金明细")
    @RequestMapping("/limit")
    @RequiresPermissions("donateuser:update")
    public R limit(@RequestBody String[] ids){
        String stateValue=StateEnum.LIMIT.getCode();
		donateUserService.updateState(ids,stateValue);
        return R.ok();
    }
	
	/**
	 * 删除
	 */
    @ResponseBody
    @SysLog("删除爱心空间资金明细")
	@RequestMapping("/delete")
	@RequiresPermissions("donateuser:delete")
	public R delete(@RequestBody Long[] ids){
		donateUserService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
