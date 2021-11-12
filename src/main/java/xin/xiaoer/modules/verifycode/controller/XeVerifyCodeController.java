package xin.xiaoer.modules.verifycode.controller;

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

import xin.xiaoer.modules.verifycode.entity.XeVerifyCode;
import xin.xiaoer.modules.verifycode.service.XeVerifyCodeService;
import xin.xiaoer.common.utils.PageUtils;
import xin.xiaoer.common.utils.Query;
import xin.xiaoer.common.utils.R;


/**
 * 
 * 
 * @author chenyi
 * @email 228112142@qq.com
 * @date 2018-07-17 17:19:51
 */
@Controller
@RequestMapping("xeverifycode")
public class XeVerifyCodeController {
	@Autowired
	private XeVerifyCodeService xeVerifyCodeService;
	
    /**
     * 跳转到列表页
     */
    @RequestMapping("/list")
    @RequiresPermissions("xeverifycode:list")
    public String list() {
        return "xeverifycode/list";
    }
    
	/**
	 * 列表数据
	 */
    @ResponseBody
	@RequestMapping("/listData")
	@RequiresPermissions("xeverifycode:list")
	public R listData(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<XeVerifyCode> xeVerifyCodeList = xeVerifyCodeService.getList(query);
		int total = xeVerifyCodeService.getCount(query);
		
		PageUtils pageUtil = new PageUtils(xeVerifyCodeList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}

    /**
     * 跳转到新增页面
     **/
    @RequestMapping("/add")
    @RequiresPermissions("xeverifycode:save")
    public String add(){
        return "xeverifycode/add";
    }

    /**
     *   跳转到修改页面
     **/
    @RequestMapping("/edit/{id}")
    @RequiresPermissions("xeverifycode:update")
    public String edit(Model model, @PathVariable("id") String id){
		XeVerifyCode xeVerifyCode = xeVerifyCodeService.get(Long.parseLong(id));
        model.addAttribute("model",xeVerifyCode);
        return "xeverifycode/edit";
    }

	/**
	 * 信息
	 */
    @ResponseBody
    @RequestMapping("/info/{id}")
    @RequiresPermissions("xeverifycode:info")
    public R info(@PathVariable("id") Long id){
        XeVerifyCode xeVerifyCode = xeVerifyCodeService.get(id);
        return R.ok().put("xeVerifyCode", xeVerifyCode);
    }

    /**
	 * 保存
	 */
    @ResponseBody
    @SysLog("保存")
	@RequestMapping("/save")
	@RequiresPermissions("xeverifycode:save")
	public R save(@RequestBody XeVerifyCode xeVerifyCode){
		xeVerifyCodeService.save(xeVerifyCode);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
    @ResponseBody
    @SysLog("修改")
	@RequestMapping("/update")
	@RequiresPermissions("xeverifycode:update")
	public R update(@RequestBody XeVerifyCode xeVerifyCode){
		xeVerifyCodeService.update(xeVerifyCode);
		
		return R.ok();
	}

    /**
     * 启用
     */
    @ResponseBody
    @SysLog("启用")
    @RequestMapping("/enable")
    @RequiresPermissions("xeverifycode:update")
    public R enable(@RequestBody String[] ids){
        String stateValue=StateEnum.ENABLE.getCode();
		xeVerifyCodeService.updateState(ids,stateValue);
        return R.ok();
    }
    /**
     * 禁用
     */
    @ResponseBody
    @SysLog("禁用")
    @RequestMapping("/limit")
    @RequiresPermissions("xeverifycode:update")
    public R limit(@RequestBody String[] ids){
        String stateValue=StateEnum.LIMIT.getCode();
		xeVerifyCodeService.updateState(ids,stateValue);
        return R.ok();
    }
	
	/**
	 * 删除
	 */
    @ResponseBody
    @SysLog("删除")
	@RequestMapping("/delete")
	@RequiresPermissions("xeverifycode:delete")
	public R delete(@RequestBody Long[] ids){
		xeVerifyCodeService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
