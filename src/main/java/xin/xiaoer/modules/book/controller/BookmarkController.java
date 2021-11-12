package xin.xiaoer.modules.book.controller;

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

import xin.xiaoer.modules.book.entity.Bookmark;
import xin.xiaoer.modules.book.service.BookmarkService;
import xin.xiaoer.common.utils.PageUtils;
import xin.xiaoer.common.utils.Query;
import xin.xiaoer.common.utils.R;


/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-08-23 16:24:08
 */
@Controller
@RequestMapping("bookmark")
public class BookmarkController {
	@Autowired
	private BookmarkService bookmarkService;
	
    /**
     * 跳转到列表页
     */
    @RequestMapping("/list")
    @RequiresPermissions("bookmark:list")
    public String list() {
        return "bookmark/list";
    }
    
	/**
	 * 列表数据
	 */
    @ResponseBody
	@RequestMapping("/listData")
	@RequiresPermissions("bookmark:list")
	public R listData(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<Bookmark> bookmarkList = bookmarkService.getList(query);
		int total = bookmarkService.getCount(query);
		
		PageUtils pageUtil = new PageUtils(bookmarkList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}

    /**
     * 跳转到新增页面
     **/
    @RequestMapping("/add")
    @RequiresPermissions("bookmark:save")
    public String add(){
        return "bookmark/add";
    }

    /**
     *   跳转到修改页面
     **/
    @RequestMapping("/edit/{id}")
    @RequiresPermissions("bookmark:update")
    public String edit(Model model, @PathVariable("id") Integer id){
		Bookmark bookmark = bookmarkService.get(id);
        model.addAttribute("model",bookmark);
        return "bookmark/edit";
    }

	/**
	 * 信息
	 */
    @ResponseBody
    @RequestMapping("/info/{id}")
    @RequiresPermissions("bookmark:info")
    public R info(@PathVariable("id") Integer id){
        Bookmark bookmark = bookmarkService.get(id);
        return R.ok().put("bookmark", bookmark);
    }

    /**
	 * 保存
	 */
    @ResponseBody
    @SysLog("保存")
	@RequestMapping("/save")
	@RequiresPermissions("bookmark:save")
	public R save(@RequestBody Bookmark bookmark){
		bookmarkService.save(bookmark);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
    @ResponseBody
    @SysLog("修改")
	@RequestMapping("/update")
	@RequiresPermissions("bookmark:update")
	public R update(@RequestBody Bookmark bookmark){
		bookmarkService.update(bookmark);
		
		return R.ok();
	}

    /**
     * 启用
     */
    @ResponseBody
    @SysLog("启用")
    @RequestMapping("/enable")
    @RequiresPermissions("bookmark:update")
    public R enable(@RequestBody String[] ids){
        String stateValue=StateEnum.ENABLE.getCode();
		bookmarkService.updateState(ids,stateValue);
        return R.ok();
    }
    /**
     * 禁用
     */
    @ResponseBody
    @SysLog("禁用")
    @RequestMapping("/limit")
    @RequiresPermissions("bookmark:update")
    public R limit(@RequestBody String[] ids){
        String stateValue=StateEnum.LIMIT.getCode();
		bookmarkService.updateState(ids,stateValue);
        return R.ok();
    }
	
	/**
	 * 删除
	 */
    @ResponseBody
    @SysLog("删除")
	@RequestMapping("/delete")
	@RequiresPermissions("bookmark:delete")
	public R delete(@RequestBody Integer[] ids){
		bookmarkService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
