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

import xin.xiaoer.modules.story.entity.SpaceStoryHtml;
import xin.xiaoer.modules.story.service.SpaceStoryHtmlService;
import xin.xiaoer.common.utils.PageUtils;
import xin.xiaoer.common.utils.Query;
import xin.xiaoer.common.utils.R;


/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-07-27 13:23:19
 */
@Controller
@RequestMapping("spacestoryhtml")
public class SpaceStoryHtmlController {
	@Autowired
	private SpaceStoryHtmlService spaceStoryHtmlService;
	
    /**
     * 跳转到列表页
     */
    @RequestMapping("/list")
    @RequiresPermissions("spacestoryhtml:list")
    public String list() {
        return "spacestoryhtml/list";
    }
    
	/**
	 * 列表数据
	 */
    @ResponseBody
	@RequestMapping("/listData")
	@RequiresPermissions("spacestoryhtml:list")
	public R listData(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<SpaceStoryHtml> spaceStoryHtmlList = spaceStoryHtmlService.getList(query);
		int total = spaceStoryHtmlService.getCount(query);
		
		PageUtils pageUtil = new PageUtils(spaceStoryHtmlList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}

    /**
     * 跳转到新增页面
     **/
    @RequestMapping("/add")
    @RequiresPermissions("spacestoryhtml:save")
    public String add(){
        return "spacestoryhtml/add";
    }

    /**
     *   跳转到修改页面
     **/
    @RequestMapping("/edit/{id}")
    @RequiresPermissions("spacestoryhtml:update")
    public String edit(Model model, @PathVariable("id") String id){
		SpaceStoryHtml spaceStoryHtml = spaceStoryHtmlService.get(Long.parseLong(id));
        model.addAttribute("model",spaceStoryHtml);
        return "spacestoryhtml/edit";
    }

	/**
	 * 信息
	 */
    @ResponseBody
    @RequestMapping("/info/{storyId}")
    @RequiresPermissions("spacestoryhtml:info")
    public R info(@PathVariable("storyId") Long storyId){
        SpaceStoryHtml spaceStoryHtml = spaceStoryHtmlService.get(storyId);
        return R.ok().put("spaceStoryHtml", spaceStoryHtml);
    }

    /**
	 * 保存
	 */
    @ResponseBody
    @SysLog("保存")
	@RequestMapping("/save")
	@RequiresPermissions("spacestoryhtml:save")
	public R save(@RequestBody SpaceStoryHtml spaceStoryHtml){
		spaceStoryHtmlService.save(spaceStoryHtml);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
    @ResponseBody
    @SysLog("修改")
	@RequestMapping("/update")
	@RequiresPermissions("spacestoryhtml:update")
	public R update(@RequestBody SpaceStoryHtml spaceStoryHtml){
		spaceStoryHtmlService.update(spaceStoryHtml);
		
		return R.ok();
	}

    /**
     * 启用
     */
    @ResponseBody
    @SysLog("启用")
    @RequestMapping("/enable")
    @RequiresPermissions("spacestoryhtml:update")
    public R enable(@RequestBody String[] ids){
        String stateValue=StateEnum.ENABLE.getCode();
		spaceStoryHtmlService.updateState(ids,stateValue);
        return R.ok();
    }
    /**
     * 禁用
     */
    @ResponseBody
    @SysLog("禁用")
    @RequestMapping("/limit")
    @RequiresPermissions("spacestoryhtml:update")
    public R limit(@RequestBody String[] ids){
        String stateValue=StateEnum.LIMIT.getCode();
		spaceStoryHtmlService.updateState(ids,stateValue);
        return R.ok();
    }
	
	/**
	 * 删除
	 */
    @ResponseBody
    @SysLog("删除")
	@RequestMapping("/delete")
	@RequiresPermissions("spacestoryhtml:delete")
	public R delete(@RequestBody Long[] storyIds){
		spaceStoryHtmlService.deleteBatch(storyIds);
		
		return R.ok();
	}
	
}
