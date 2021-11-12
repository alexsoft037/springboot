package xin.xiaoer.modules.favourite.controller;

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
import xin.xiaoer.modules.favourite.entity.Favourite;
import xin.xiaoer.modules.favourite.service.FavouriteService;
import xin.xiaoer.common.utils.PageUtils;
import xin.xiaoer.common.utils.Query;
import xin.xiaoer.common.utils.R;


/**
 * 
 * 
 * @author chenyi
 * @email 228112142@qq.com
 * @date 2018-07-20 00:43:14
 */
@Controller
@RequestMapping("favourite")
public class FavouriteController {
	@Autowired
	private FavouriteService favouriteService;
	
    /**
     * 跳转到列表页
     */
    @RequestMapping("/list")
    @RequiresPermissions("favourite:list")
    public String list() {
        return "favourite/list";
    }
    
	/**
	 * 列表数据
	 */
    @ResponseBody
	@RequestMapping("/listData")
	@RequiresPermissions("favourite:list")
	public R listData(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<Favourite> favouriteList = favouriteService.getList(query);
		int total = favouriteService.getCount(query);
		
		PageUtils pageUtil = new PageUtils(favouriteList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}

    /**
     * 跳转到新增页面
     **/
    @RequestMapping("/add")
    @RequiresPermissions("favourite:save")
    public String add(){
        return "favourite/add";
    }

    /**
     *   跳转到修改页面
     **/
    @RequestMapping("/edit/{id}")
    @RequiresPermissions("favourite:update")
    public String edit(Model model, @PathVariable("id") String id){
		Favourite favourite = favouriteService.get(Long.parseLong(id));
        model.addAttribute("model",favourite);
        return "favourite/edit";
    }

	/**
	 * 信息
	 */
    @ResponseBody
    @RequestMapping("/info/{fvId}")
    @RequiresPermissions("favourite:info")
    public R info(@PathVariable("fvId") Long fvId){
        Favourite favourite = favouriteService.get(fvId);
        return R.ok().put("favourite", favourite);
    }

    /**
	 * 保存
	 */
    @ResponseBody
    @SysLog("保存")
	@RequestMapping("/save")
	@RequiresPermissions("favourite:save")
	public R save(@RequestBody Favourite favourite){
        favourite.setCreateBy(ShiroUtils.getUserId());
        favourite.setUpdateBy(ShiroUtils.getUserId());
		favouriteService.save(favourite);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
    @ResponseBody
    @SysLog("修改")
	@RequestMapping("/update")
	@RequiresPermissions("favourite:update")
	public R update(@RequestBody Favourite favourite){
        favourite.setUpdateBy(ShiroUtils.getUserId());
		favouriteService.update(favourite);
		
		return R.ok();
	}

    /**
     * 启用
     */
    @ResponseBody
    @SysLog("启用")
    @RequestMapping("/enable")
    @RequiresPermissions("favourite:update")
    public R enable(@RequestBody String[] ids){
        String stateValue=StateEnum.ENABLE.getCode();
		favouriteService.updateState(ids,stateValue);
        return R.ok();
    }
    /**
     * 禁用
     */
    @ResponseBody
    @SysLog("禁用")
    @RequestMapping("/limit")
    @RequiresPermissions("favourite:update")
    public R limit(@RequestBody String[] ids){
        String stateValue=StateEnum.LIMIT.getCode();
		favouriteService.updateState(ids,stateValue);
        return R.ok();
    }
	
	/**
	 * 删除
	 */
    @ResponseBody
    @SysLog("删除")
	@RequestMapping("/delete")
	@RequiresPermissions("favourite:delete")
	public R delete(@RequestBody Long[] fvIds){
		favouriteService.deleteBatch(fvIds);
		
		return R.ok();
	}
	
}
