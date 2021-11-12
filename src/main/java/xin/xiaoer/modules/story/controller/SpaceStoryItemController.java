package xin.xiaoer.modules.story.controller;

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

import xin.xiaoer.modules.story.entity.SpaceStoryItem;
import xin.xiaoer.modules.story.service.SpaceStoryItemService;
import xin.xiaoer.common.utils.PageUtils;
import xin.xiaoer.common.utils.Query;
import xin.xiaoer.common.utils.R;


/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-07-27 13:23:15
 */
@Controller
@RequestMapping("spacestoryitem")
public class SpaceStoryItemController {
	@Autowired
	private SpaceStoryItemService spaceStoryItemService;
	
    /**
     * 跳转到列表页
     */
    @RequestMapping("/list")
    @RequiresPermissions("spacestoryitem:list")
    public String list() {
        return "spacestoryitem/list";
    }
    
	/**
	 * 列表数据
	 */
    @ResponseBody
	@RequestMapping("/listData")
	@RequiresPermissions("spacestoryitem:list")
	public R listData(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<SpaceStoryItem> spaceStoryItemList = spaceStoryItemService.getList(query);
		int total = spaceStoryItemService.getCount(query);
		
		PageUtils pageUtil = new PageUtils(spaceStoryItemList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}

    /**
     * 跳转到新增页面
     **/
    @RequestMapping("/add")
    @RequiresPermissions("spacestoryitem:save")
    public String add(){
        return "spacestoryitem/add";
    }

    /**
     *   跳转到修改页面
     **/
    @RequestMapping("/edit/{id}")
    @RequiresPermissions("spacestoryitem:update")
    public String edit(Model model, @PathVariable("id") String id){
		SpaceStoryItem spaceStoryItem = spaceStoryItemService.get(Long.parseLong(id));
        model.addAttribute("model",spaceStoryItem);
        return "spacestoryitem/edit";
    }

	/**
	 * 信息
	 */
    @ResponseBody
    @RequestMapping("/info/{storyId}")
    @RequiresPermissions("spacestoryitem:info")
    public R info(@PathVariable("storyId") Long storyId){
        SpaceStoryItem spaceStoryItem = spaceStoryItemService.get(storyId);
        return R.ok().put("spaceStoryItem", spaceStoryItem);
    }

    /**
	 * 保存
	 */
    @ResponseBody
    @SysLog("保存")
	@RequestMapping("/save")
	@RequiresPermissions("spacestoryitem:save")
	public R save(@RequestBody SpaceStoryItem spaceStoryItem){
		spaceStoryItemService.save(spaceStoryItem);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
    @ResponseBody
    @SysLog("修改")
	@RequestMapping("/update")
	@RequiresPermissions("spacestoryitem:update")
	public R update(@RequestBody SpaceStoryItem spaceStoryItem){
		spaceStoryItemService.update(spaceStoryItem);
		
		return R.ok();
	}

    /**
     * 启用
     */
    @ResponseBody
    @SysLog("启用")
    @RequestMapping("/enable")
    @RequiresPermissions("spacestoryitem:update")
    public R enable(@RequestBody String[] ids){
        String stateValue=StateEnum.ENABLE.getCode();
		spaceStoryItemService.updateState(ids,stateValue);
        return R.ok();
    }
    /**
     * 禁用
     */
    @ResponseBody
    @SysLog("禁用")
    @RequestMapping("/limit")
    @RequiresPermissions("spacestoryitem:update")
    public R limit(@RequestBody String[] ids){
        String stateValue=StateEnum.LIMIT.getCode();
		spaceStoryItemService.updateState(ids,stateValue);
        return R.ok();
    }
	
	/**
	 * 删除
	 */
    @ResponseBody
    @SysLog("删除")
	@RequestMapping("/delete")
	@RequiresPermissions("spacestoryitem:delete")
	public R delete(@RequestBody Long[] storyIds){
		spaceStoryItemService.deleteBatch(storyIds);
		
		return R.ok();
	}
	
}
