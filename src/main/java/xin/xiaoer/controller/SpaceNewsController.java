package xin.xiaoer.controller;

import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import org.apache.commons.lang.StringUtils;
import xin.xiaoer.common.enumresource.StateEnum;
import xin.xiaoer.common.exception.MyException;
import xin.xiaoer.common.log.SysLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import javax.servlet.http.HttpServletRequest;

import xin.xiaoer.common.shiro.ShiroUtils;
import xin.xiaoer.common.utils.StringUtility;
import xin.xiaoer.entity.SpaceNews;
import xin.xiaoer.entity.SysUser;
import xin.xiaoer.service.SpaceNewsService;
import xin.xiaoer.common.utils.PageUtils;
import xin.xiaoer.common.utils.Query;
import xin.xiaoer.common.utils.R;


/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-10-28 22:08:28
 */
@Controller
@RequestMapping("spacenews")
public class SpaceNewsController {
	@Autowired
	private SpaceNewsService spaceNewsService;
	
    /**
     * 跳转到列表页
     */
    @RequestMapping("/list")
    @RequiresPermissions("spacenews:list")
    public String list() {
        return "spacenews/list";
    }
    
	/**
	 * 列表数据
	 */
    @ResponseBody
	@RequestMapping("/listData")
	@RequiresPermissions("spacenews:list")
	public R listData(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

        SysUser admin = ShiroUtils.getUserEntity();
        if (admin.getUserId() != 1L){
            query.put("spaceId", admin.getSpaceId());
        }

		List<SpaceNews> spaceNewsList = spaceNewsService.getList(query);
		int total = spaceNewsService.getCount(query);
		
		PageUtils pageUtil = new PageUtils(spaceNewsList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}

    /**
     * 跳转到新增页面
     **/
    @RequestMapping("/add")
    @RequiresPermissions("spacenews:save")
    public String add(Model model){
        model.addAttribute("admin",ShiroUtils.getUserEntity());
        return "spacenews/add";
    }

    /**
     *   跳转到修改页面
     **/
    @RequestMapping("/edit/{id}")
    @RequiresPermissions("spacenews:update")
    public String edit(Model model, @PathVariable("id") String id){
		SpaceNews spaceNews = spaceNewsService.get(Integer.parseInt(id));
        model.addAttribute("admin",ShiroUtils.getUserEntity());
        model.addAttribute("model",spaceNews);
        return "spacenews/edit";
    }

	/**
	 * 信息
	 */
    @ResponseBody
    @RequestMapping("/info/{id}")
    @RequiresPermissions("spacenews:info")
    public R info(@PathVariable("id") Integer id){
        SpaceNews spaceNews = spaceNewsService.get(id);
        return R.ok().put("spaceNews", spaceNews);
    }

    /**
	 * 保存
	 */
    @ResponseBody
    @SysLog("保存空间快讯")
	@RequestMapping("/save")
	@RequiresPermissions("spacenews:save")
	public R save(@RequestBody Map<String, Object> params) {
        Gson gson = new Gson();
        JsonElement jsonElement = gson.toJsonTree(params);
        SpaceNews spaceNews = gson.fromJson(jsonElement, SpaceNews.class);

        if (ShiroUtils.getUserId() == 1L){
            String spaceId = params.get("spaceId1").toString();
            if (StringUtility.isEmpty(spaceId) || !StringUtils.isNumeric(spaceId)) {
                throw new MyException("少儿空间不能为空！");
            }
            spaceNews.setSpaceId(Integer.parseInt(spaceId));
        } else {
            spaceNews.setSpaceId(ShiroUtils.getSpaceId());
        }

        spaceNews.setCreateBy(ShiroUtils.getUserId());
        spaceNews.setUpdateBy(ShiroUtils.getUserId());
		spaceNewsService.save(spaceNews);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
    @ResponseBody
    @SysLog("修改空间快讯")
	@RequestMapping("/update")
	@RequiresPermissions("spacenews:update")
	public R update(@RequestBody Map<String, Object> params){
        Gson gson = new Gson();
        JsonElement jsonElement = gson.toJsonTree(params);
        SpaceNews spaceNews = gson.fromJson(jsonElement, SpaceNews.class);

        if (ShiroUtils.getUserId() == 1L){
            String spaceId = params.get("spaceId1").toString();
            if (StringUtility.isEmpty(spaceId) || !StringUtils.isNumeric(spaceId)) {
                throw new MyException("少儿空间不能为空！");
            }
            spaceNews.setSpaceId(Integer.parseInt(spaceId));
        } else {
            spaceNews.setSpaceId(ShiroUtils.getSpaceId());
        }

        spaceNews.setUpdateBy(ShiroUtils.getUserId());
        spaceNewsService.update(spaceNews);
		return R.ok();
	}

    /**
     * 启用
     */
    @ResponseBody
    @SysLog("启用空间快讯")
    @RequestMapping("/enable")
    @RequiresPermissions("spacenews:update")
    public R enable(@RequestBody String[] ids){
        String stateValue=StateEnum.ENABLE.getCode();
		spaceNewsService.updateState(ids,stateValue);
        return R.ok();
    }
    /**
     * 禁用
     */
    @ResponseBody
    @SysLog("禁用空间快讯")
    @RequestMapping("/limit")
    @RequiresPermissions("spacenews:update")
    public R limit(@RequestBody String[] ids){
        String stateValue=StateEnum.LIMIT.getCode();
		spaceNewsService.updateState(ids,stateValue);
        return R.ok();
    }
	
	/**
	 * 删除
	 */
    @ResponseBody
    @SysLog("删除空间快讯")
	@RequestMapping("/delete")
	@RequiresPermissions("spacenews:delete")
	public R delete(@RequestBody Integer[] ids){
		spaceNewsService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
