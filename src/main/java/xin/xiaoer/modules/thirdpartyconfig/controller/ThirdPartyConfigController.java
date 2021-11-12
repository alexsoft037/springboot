package xin.xiaoer.modules.thirdpartyconfig.controller;

import java.util.List;
import java.util.Map;

import io.swagger.models.auth.In;
import xin.xiaoer.common.enumresource.StateEnum;
import xin.xiaoer.common.log.SysLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import javax.servlet.http.HttpServletRequest;

import xin.xiaoer.common.shiro.ShiroUtils;
import xin.xiaoer.modules.thirdpartyconfig.entity.ThirdPartyConfig;
import xin.xiaoer.modules.thirdpartyconfig.service.ThirdPartyConfigService;
import xin.xiaoer.common.utils.PageUtils;
import xin.xiaoer.common.utils.Query;
import xin.xiaoer.common.utils.R;


/**
 * 
 * 
 * @author chenyi
 * @email 228112142@qq.com
 * @date 2018-07-08 18:35:30
 */
@Controller
@RequestMapping("thirdpartyconfig")
public class ThirdPartyConfigController {
	@Autowired
	private ThirdPartyConfigService thirdPartyConfigService;
	
    /**
     * 跳转到列表页
     */
    @RequestMapping("/list")
    @RequiresPermissions("thirdpartyconfig:list")
    public String list() {
        return "thirdpartyconfig/list";
    }
    
	/**
	 * 列表数据
	 */
    @ResponseBody
	@RequestMapping("/listData")
	@RequiresPermissions("thirdpartyconfig:list")
	public R listData(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<ThirdPartyConfig> thirdPartyConfigList = thirdPartyConfigService.getList(query);
		int total = thirdPartyConfigService.getCount(query);
		
		PageUtils pageUtil = new PageUtils(thirdPartyConfigList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}

    /**
     * 跳转到新增页面
     **/
    @RequestMapping("/add")
    @RequiresPermissions("thirdpartyconfig:save")
    public String add(){
        return "thirdpartyconfig/add";
    }

    /**
     *   跳转到修改页面
     **/
    @RequestMapping("/edit/{id}")
    @RequiresPermissions("thirdpartyconfig:update")
    public String edit(Model model, @PathVariable("id") String id){
		ThirdPartyConfig thirdPartyConfig = thirdPartyConfigService.get(Integer.parseInt(id));
        model.addAttribute("model",thirdPartyConfig);
        return "thirdpartyconfig/edit";
    }

	/**
	 * 信息
	 */
    @ResponseBody
    @RequestMapping("/info/{id}")
    @RequiresPermissions("thirdpartyconfig:info")
    public R info(@PathVariable("id") Integer id){
        ThirdPartyConfig thirdPartyConfig = thirdPartyConfigService.get(id);
        return R.ok().put("thirdPartyConfig", thirdPartyConfig);
    }

    /**
	 * 保存
	 */
    @ResponseBody
    @SysLog("第三方接口参数设置保存")
	@RequestMapping("/save")
	@RequiresPermissions("thirdpartyconfig:save")
	public R save(@RequestBody ThirdPartyConfig thirdPartyConfig){
        thirdPartyConfig.setCreateBy(ShiroUtils.getUserId());
        thirdPartyConfig.setUpdateBy(ShiroUtils.getUserId());
		thirdPartyConfigService.save(thirdPartyConfig);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
    @ResponseBody
    @SysLog("第三方接口参数设置修改")
	@RequestMapping("/update")
	@RequiresPermissions("thirdpartyconfig:update")
	public R update(@RequestBody ThirdPartyConfig thirdPartyConfig){
        thirdPartyConfig.setUpdateBy(ShiroUtils.getUserId());
		thirdPartyConfigService.update(thirdPartyConfig);
		
		return R.ok();
	}

    /**
     * 启用
     */
    @ResponseBody
    @SysLog("第三方接口参数设置启用")
    @RequestMapping("/enable")
    @RequiresPermissions("thirdpartyconfig:update")
    public R enable(@RequestBody String[] ids){
        String stateValue=StateEnum.ENABLE.getCode();
		thirdPartyConfigService.updateState(ids,stateValue);
        return R.ok();
    }
    /**
     * 禁用
     */
    @ResponseBody
    @SysLog("第三方接口参数设置禁用")
    @RequestMapping("/limit")
    @RequiresPermissions("thirdpartyconfig:update")
    public R limit(@RequestBody String[] ids){
        String stateValue=StateEnum.LIMIT.getCode();
		thirdPartyConfigService.updateState(ids,stateValue);
        return R.ok();
    }
	
	/**
	 * 删除
	 */
    @ResponseBody
    @SysLog("第三方接口参数设置删除")
	@RequestMapping("/delete")
	@RequiresPermissions("thirdpartyconfig:delete")
	public R delete(@RequestBody Integer[] ids){
		thirdPartyConfigService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
