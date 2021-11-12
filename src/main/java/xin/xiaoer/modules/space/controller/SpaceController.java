package xin.xiaoer.modules.space.controller;

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

import xin.xiaoer.common.shiro.ShiroUtils;
import xin.xiaoer.common.utils.Constant;
import xin.xiaoer.entity.CodeValue;
import xin.xiaoer.modules.space.entity.Space;
import xin.xiaoer.modules.space.service.SpaceService;
import xin.xiaoer.common.utils.PageUtils;
import xin.xiaoer.common.utils.Query;
import xin.xiaoer.common.utils.R;


/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-08-02 21:56:04
 */
@Controller
@RequestMapping("space")
public class SpaceController {
	@Autowired
	private SpaceService spaceService;
	
    /**
     * 跳转到列表页
     */
    @RequestMapping("/list")
    @RequiresPermissions("space:list")
    public String list(Model model) {
        model.addAttribute("BaiduApiKey", Constant.BaiduApiKey);
        return "space/list";
    }

    @ResponseBody
    @RequestMapping("/getCodeValues")
    public R getCodeValues(@RequestParam Map<String, Object> params) {

        List<CodeValue> categoryList = spaceService.getCodeValues(null);

        return R.ok().put("data", categoryList);
    }

	/**
	 * 列表数据
	 */
    @ResponseBody
	@RequestMapping("/listData")
	@RequiresPermissions("space:list")
	public R listData(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<Space> spaceList = spaceService.getList(query);
		int total = spaceService.getCount(query);
		
		PageUtils pageUtil = new PageUtils(spaceList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}

    /**
     * 列表数据
     */
    @ResponseBody
    @RequestMapping("/allListData")
    @RequiresPermissions("space:list")
    public R allListData(@RequestParam Map<String, Object> params){
        //查询列表数据

        List<Space> spaceList = spaceService.getList(params);

        return R.ok().put("page", spaceList);
    }
    /**
     * 跳转到新增页面
     **/
    @RequestMapping("/add")
    @RequiresPermissions("space:save")
    public String add(Model model){
        model.addAttribute("BaiduApiKey", Constant.BaiduApiKey);
        return "space/add";
    }

    /**
     *   跳转到修改页面
     **/
    @RequestMapping("/edit/{id}")
    @RequiresPermissions("space:update")
    public String edit(Model model, @PathVariable("id") String id){
		Space space = spaceService.get(Integer.parseInt(id));
        model.addAttribute("model",space);
        model.addAttribute("BaiduApiKey", Constant.BaiduApiKey);
        return "space/edit";
    }

	/**
	 * 信息
	 */
    @ResponseBody
    @RequestMapping("/info/{spaceId}")
    @RequiresPermissions("space:info")
    public R info(@PathVariable("spaceId") Integer spaceId){
        Space space = spaceService.get(spaceId);
        return R.ok().put("space", space);
    }

    /**
	 * 保存
	 */
    @ResponseBody
    @SysLog("保存线下少儿空间")
	@RequestMapping("/save")
	@RequiresPermissions("space:save")
	public R save(@RequestBody Space space){
        space.setCreateBy(ShiroUtils.getUserId());
        space.setUpdateBy(ShiroUtils.getUserId());
		spaceService.save(space);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
    @ResponseBody
    @SysLog("修改线下少儿空间")
	@RequestMapping("/update")
	@RequiresPermissions("space:update")
	public R update(@RequestBody Space space){
        space.setUpdateBy(ShiroUtils.getUserId());
		spaceService.update(space);
		
		return R.ok();
	}

    /**
     * 启用
     */
    @ResponseBody
    @SysLog("启用线下少儿空间")
    @RequestMapping("/enable")
    @RequiresPermissions("space:update")
    public R enable(@RequestBody String[] ids){
        String stateValue=StateEnum.ENABLE.getCode();
		spaceService.updateState(ids,stateValue);
        return R.ok();
    }
    /**
     * 禁用
     */
    @ResponseBody
    @SysLog("禁用线下少儿空间")
    @RequestMapping("/limit")
    @RequiresPermissions("space:update")
    public R limit(@RequestBody String[] ids){
        String stateValue=StateEnum.LIMIT.getCode();
		spaceService.updateState(ids,stateValue);
        return R.ok();
    }
	
	/**
	 * 删除
	 */
    @ResponseBody
    @SysLog("删除线下少儿空间")
	@RequestMapping("/delete")
	@RequiresPermissions("space:delete")
	public R delete(@RequestBody Integer[] spaceIds){
		spaceService.deleteBatch(spaceIds);
		
		return R.ok();
	}
	
}
