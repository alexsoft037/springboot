package xin.xiaoer.modules.sysusersns.controller;

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

import xin.xiaoer.modules.sysusersns.entity.SysUserSns;
import xin.xiaoer.modules.sysusersns.service.SysUserSnsService;
import xin.xiaoer.common.utils.PageUtils;
import xin.xiaoer.common.utils.Query;
import xin.xiaoer.common.utils.R;


/**
 * 
 * 
 * @author chenyi
 * @email 228112142@qq.com
 * @date 2018-07-08 17:49:46
 */
@Controller
@RequestMapping("sysusersns")
public class SysUserSnsController {
	@Autowired
	private SysUserSnsService sysUserSnsService;
	
    /**
     * 跳转到列表页
     */
    @RequestMapping("/list")
    @RequiresPermissions("sysusersns:list")
    public String list() {
        return "sysusersns/list";
    }
    
	/**
	 * 列表数据
	 */
    @ResponseBody
	@RequestMapping("/listData")
	@RequiresPermissions("sysusersns:list")
	public R listData(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<SysUserSns> sysUserSnsList = sysUserSnsService.getList(query);
		int total = sysUserSnsService.getCount(query);
		
		PageUtils pageUtil = new PageUtils(sysUserSnsList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}

    /**
     * 跳转到新增页面
     **/
    @RequestMapping("/add")
    @RequiresPermissions("sysusersns:save")
    public String add(){
        return "sysusersns/add";
    }

    /**
     *   跳转到修改页面
     **/
    @RequestMapping("/edit/{id}")
    @RequiresPermissions("sysusersns:update")
    public String edit(Model model, @PathVariable("id") String id){
		SysUserSns sysUserSns = sysUserSnsService.get(Long.parseLong(id));
        model.addAttribute("model",sysUserSns);
        return "sysusersns/edit";
    }

	/**
	 * 信息
	 */
    @ResponseBody
    @RequestMapping("/info/{userId}")
    @RequiresPermissions("sysusersns:info")
    public R info(@PathVariable("userId") Long userId){
        SysUserSns sysUserSns = sysUserSnsService.get(userId);
        return R.ok().put("sysUserSns", sysUserSns);
    }

    /**
	 * 保存
	 */
    @ResponseBody
    @SysLog("保存")
	@RequestMapping("/save")
	@RequiresPermissions("sysusersns:save")
	public R save(@RequestBody SysUserSns sysUserSns){
		sysUserSnsService.save(sysUserSns);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
    @ResponseBody
    @SysLog("修改")
	@RequestMapping("/update")
	@RequiresPermissions("sysusersns:update")
	public R update(@RequestBody SysUserSns sysUserSns){
		sysUserSnsService.update(sysUserSns);
		
		return R.ok();
	}

    /**
     * 启用
     */
    @ResponseBody
    @SysLog("启用")
    @RequestMapping("/enable")
    @RequiresPermissions("sysusersns:update")
    public R enable(@RequestBody String[] ids){
        String stateValue=StateEnum.ENABLE.getCode();
		sysUserSnsService.updateState(ids,stateValue);
        return R.ok();
    }
    /**
     * 禁用
     */
    @ResponseBody
    @SysLog("禁用")
    @RequestMapping("/limit")
    @RequiresPermissions("sysusersns:update")
    public R limit(@RequestBody String[] ids){
        String stateValue=StateEnum.LIMIT.getCode();
		sysUserSnsService.updateState(ids,stateValue);
        return R.ok();
    }
	
	/**
	 * 删除
	 */
    @ResponseBody
    @SysLog("删除")
	@RequestMapping("/delete")
	@RequiresPermissions("sysusersns:delete")
	public R delete(@RequestBody Long[] userIds){
		sysUserSnsService.deleteBatch(userIds);
		
		return R.ok();
	}
	
}
