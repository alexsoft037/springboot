package xin.xiaoer.modules.setting.controller;

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

import xin.xiaoer.modules.setting.entity.ClickCard;
import xin.xiaoer.modules.setting.service.ClickCardService;
import xin.xiaoer.common.utils.PageUtils;
import xin.xiaoer.common.utils.Query;
import xin.xiaoer.common.utils.R;


/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-10-04 01:18:30
 */
@Controller
@RequestMapping("clickcard")
public class ClickCardController {
	@Autowired
	private ClickCardService clickCardService;
	
    /**
     * 跳转到列表页
     */
    @RequestMapping("/list")
    @RequiresPermissions("clickcard:list")
    public String list() {
        return "clickcard/list";
    }
    
	/**
	 * 列表数据
	 */
    @ResponseBody
	@RequestMapping("/listData")
	@RequiresPermissions("clickcard:list")
	public R listData(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<ClickCard> clickCardList = clickCardService.getList(query);
		int total = clickCardService.getCount(query);
		
		PageUtils pageUtil = new PageUtils(clickCardList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}

    /**
     * 跳转到新增页面
     **/
    @RequestMapping("/add")
    @RequiresPermissions("clickcard:save")
    public String add(){
        return "clickcard/add";
    }

    /**
     *   跳转到修改页面
     **/
    @RequestMapping("/edit/{id}")
    @RequiresPermissions("clickcard:update")
    public String edit(Model model, @PathVariable("id") Long id){
		ClickCard clickCard = clickCardService.get(id);
        model.addAttribute("model",clickCard);
        return "clickcard/edit";
    }

	/**
	 * 信息
	 */
    @ResponseBody
    @RequestMapping("/info/{userId}")
    @RequiresPermissions("clickcard:info")
    public R info(@PathVariable("userId") Long userId){
        ClickCard clickCard = clickCardService.get(userId);
        return R.ok().put("clickCard", clickCard);
    }

    /**
	 * 保存
	 */
    @ResponseBody
    @SysLog("保存")
	@RequestMapping("/save")
	@RequiresPermissions("clickcard:save")
	public R save(@RequestBody ClickCard clickCard){
		clickCardService.save(clickCard);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
    @ResponseBody
    @SysLog("修改")
	@RequestMapping("/update")
	@RequiresPermissions("clickcard:update")
	public R update(@RequestBody ClickCard clickCard){
		clickCardService.update(clickCard);
		
		return R.ok();
	}

    /**
     * 启用
     */
    @ResponseBody
    @SysLog("启用")
    @RequestMapping("/enable")
    @RequiresPermissions("clickcard:update")
    public R enable(@RequestBody String[] ids){
        String stateValue=StateEnum.ENABLE.getCode();
		clickCardService.updateState(ids,stateValue);
        return R.ok();
    }
    /**
     * 禁用
     */
    @ResponseBody
    @SysLog("禁用")
    @RequestMapping("/limit")
    @RequiresPermissions("clickcard:update")
    public R limit(@RequestBody String[] ids){
        String stateValue=StateEnum.LIMIT.getCode();
		clickCardService.updateState(ids,stateValue);
        return R.ok();
    }
	
	/**
	 * 删除
	 */
    @ResponseBody
    @SysLog("删除")
	@RequestMapping("/delete")
	@RequiresPermissions("clickcard:delete")
	public R delete(@RequestBody Long[] userIds){
		clickCardService.deleteBatch(userIds);
		
		return R.ok();
	}
	
}
