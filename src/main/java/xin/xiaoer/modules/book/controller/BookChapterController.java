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

import xin.xiaoer.modules.book.entity.BookChapter;
import xin.xiaoer.modules.book.service.BookChapterService;
import xin.xiaoer.common.utils.PageUtils;
import xin.xiaoer.common.utils.Query;
import xin.xiaoer.common.utils.R;


/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-08-09 10:26:24
 */
@Controller
@RequestMapping("bookchapter")
public class BookChapterController {
	@Autowired
	private BookChapterService bookChapterService;
	
    /**
     * 跳转到列表页
     */
    @RequestMapping("/list")
    @RequiresPermissions("bookchapter:list")
    public String list() {
        return "bookchapter/list";
    }
    
	/**
	 * 列表数据
	 */
    @ResponseBody
	@RequestMapping("/listData")
	@RequiresPermissions("bookchapter:list")
	public R listData(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<BookChapter> bookChapterList = bookChapterService.getList(query);
		int total = bookChapterService.getCount(query);
		
		PageUtils pageUtil = new PageUtils(bookChapterList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}

    /**
     * 跳转到新增页面
     **/
    @RequestMapping("/add")
    @RequiresPermissions("bookchapter:save")
    public String add(){
        return "bookchapter/add";
    }

    /**
     *   跳转到修改页面
     **/
    @RequestMapping("/edit/{id}")
    @RequiresPermissions("bookchapter:update")
    public String edit(Model model, @PathVariable("id") String id){
		BookChapter bookChapter = bookChapterService.get(Long.parseLong(id));
        model.addAttribute("model",bookChapter);
        return "bookchapter/edit";
    }

	/**
	 * 信息
	 */
    @ResponseBody
    @RequestMapping("/info/{chapterId}")
    @RequiresPermissions("bookchapter:info")
    public R info(@PathVariable("chapterId") Long chapterId){
        BookChapter bookChapter = bookChapterService.get(chapterId);
        return R.ok().put("bookChapter", bookChapter);
    }

    /**
	 * 保存
	 */
    @ResponseBody
    @SysLog("保存")
	@RequestMapping("/save")
	@RequiresPermissions("bookchapter:save")
	public R save(@RequestBody BookChapter bookChapter){
		bookChapterService.save(bookChapter);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
    @ResponseBody
    @SysLog("修改")
	@RequestMapping("/update")
	@RequiresPermissions("bookchapter:update")
	public R update(@RequestBody BookChapter bookChapter){
		bookChapterService.update(bookChapter);
		
		return R.ok();
	}

    /**
     * 启用
     */
    @ResponseBody
    @SysLog("启用")
    @RequestMapping("/enable")
    @RequiresPermissions("bookchapter:update")
    public R enable(@RequestBody String[] ids){
        String stateValue=StateEnum.ENABLE.getCode();
		bookChapterService.updateState(ids,stateValue);
        return R.ok();
    }
    /**
     * 禁用
     */
    @ResponseBody
    @SysLog("禁用")
    @RequestMapping("/limit")
    @RequiresPermissions("bookchapter:update")
    public R limit(@RequestBody String[] ids){
        String stateValue=StateEnum.LIMIT.getCode();
		bookChapterService.updateState(ids,stateValue);
        return R.ok();
    }
	
	/**
	 * 删除
	 */
    @ResponseBody
    @SysLog("删除")
	@RequestMapping("/delete")
	@RequiresPermissions("bookchapter:delete")
	public R delete(@RequestBody Long[] chapterIds){
		bookChapterService.deleteBatch(chapterIds);
		
		return R.ok();
	}
	
}
