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

import xin.xiaoer.modules.donatespace.entity.DonateSpaceIntro;
import xin.xiaoer.modules.donatespace.service.DonateSpaceIntroService;
import xin.xiaoer.common.utils.PageUtils;
import xin.xiaoer.common.utils.Query;
import xin.xiaoer.common.utils.R;


/**
 * 
 * 
 * @author chenyi
 * @email 228112142@qq.com
 * @date 2018-07-20 00:13:18
 */
@Controller
@RequestMapping("donatespaceintro")
public class DonateSpaceIntroController {
	@Autowired
	private DonateSpaceIntroService donateSpaceIntroService;
	
    /**
     * 跳转到列表页
     */
    @RequestMapping("/list")
    @RequiresPermissions("donatespaceintro:list")
    public String list() {
        return "donatespaceintro/list";
    }
    
	/**
	 * 列表数据
	 */
    @ResponseBody
	@RequestMapping("/listData")
	@RequiresPermissions("donatespaceintro:list")
	public R listData(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<DonateSpaceIntro> donateSpaceIntroList = donateSpaceIntroService.getList(query);
		int total = donateSpaceIntroService.getCount(query);
		
		PageUtils pageUtil = new PageUtils(donateSpaceIntroList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}

    /**
     * 跳转到新增页面
     **/
    @RequestMapping("/add")
    @RequiresPermissions("donatespaceintro:save")
    public String add(){
        return "donatespaceintro/add";
    }

    /**
     *   跳转到修改页面
     **/
    @RequestMapping("/edit/{id}")
    @RequiresPermissions("donatespaceintro:update")
    public String edit(Model model, @PathVariable("id") String id){
		DonateSpaceIntro donateSpaceIntro = donateSpaceIntroService.get(Long.parseLong(id));
        model.addAttribute("model",donateSpaceIntro);
        return "donatespaceintro/edit";
    }

	/**
	 * 信息
	 */
    @ResponseBody
    @RequestMapping("/info/{itemId}")
    @RequiresPermissions("donatespaceintro:info")
    public R info(@PathVariable("itemId") Long itemId){
        DonateSpaceIntro donateSpaceIntro = donateSpaceIntroService.get(itemId);
        return R.ok().put("donateSpaceIntro", donateSpaceIntro);
    }

    /**
	 * 保存
	 */
    @ResponseBody
    @SysLog("保存")
	@RequestMapping("/save")
	@RequiresPermissions("donatespaceintro:save")
	public R save(@RequestBody DonateSpaceIntro donateSpaceIntro){
		donateSpaceIntroService.save(donateSpaceIntro);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
    @ResponseBody
    @SysLog("修改")
	@RequestMapping("/update")
	@RequiresPermissions("donatespaceintro:update")
	public R update(@RequestBody DonateSpaceIntro donateSpaceIntro){
		donateSpaceIntroService.update(donateSpaceIntro);
		
		return R.ok();
	}

    /**
     * 启用
     */
    @ResponseBody
    @SysLog("启用")
    @RequestMapping("/enable")
    @RequiresPermissions("donatespaceintro:update")
    public R enable(@RequestBody String[] ids){
        String stateValue=StateEnum.ENABLE.getCode();
		donateSpaceIntroService.updateState(ids,stateValue);
        return R.ok();
    }
    /**
     * 禁用
     */
    @ResponseBody
    @SysLog("禁用")
    @RequestMapping("/limit")
    @RequiresPermissions("donatespaceintro:update")
    public R limit(@RequestBody String[] ids){
        String stateValue=StateEnum.LIMIT.getCode();
		donateSpaceIntroService.updateState(ids,stateValue);
        return R.ok();
    }
	
	/**
	 * 删除
	 */
    @ResponseBody
    @SysLog("删除")
	@RequestMapping("/delete")
	@RequiresPermissions("donatespaceintro:delete")
	public R delete(@RequestBody Long[] itemIds){
		donateSpaceIntroService.deleteBatch(itemIds);
		
		return R.ok();
	}
	
}
