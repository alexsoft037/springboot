package xin.xiaoer.modules.comment.controller;

import java.util.List;
import java.util.Map;

import io.swagger.models.auth.In;
import xin.xiaoer.common.enumresource.StateEnum;
import xin.xiaoer.common.log.SysLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import javax.servlet.http.HttpServletRequest;

import xin.xiaoer.modules.comment.entity.Comment;
import xin.xiaoer.modules.comment.service.CommentService;
import xin.xiaoer.common.utils.PageUtils;
import xin.xiaoer.common.utils.Query;
import xin.xiaoer.common.utils.R;


/**
 * 
 * 
 * @author chenyi
 * @email 228112142@qq.com
 * @date 2018-07-21 11:57:23
 */
@Controller
@RequestMapping("comment")
public class CommentController {
	@Autowired
	private CommentService commentService;
	
    /**
     * 跳转到列表页
     */
    @RequestMapping("/list")
    @RequiresPermissions("comment:list")
    public String list() {
        return "comment/list";
    }
    
	/**
	 * 列表数据
	 */
    @ResponseBody
	@RequestMapping("/listData")
	@RequiresPermissions("comment:list")
	public R listData(@RequestParam Map<String, Object> params) throws Exception {
		//查询列表数据
        Query query = new Query(params);

		List<Comment> commentList = commentService.getList(query);
		int total = commentService.getCount(query);
		
		PageUtils pageUtil = new PageUtils(commentList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}

    /**
     * 跳转到新增页面
     **/
    @RequestMapping("/add")
    @RequiresPermissions("comment:save")
    public String add(){
        return "comment/add";
    }

    /**
     *   跳转到修改页面
     **/
    @RequestMapping("/edit/{id}")
    @RequiresPermissions("comment:update")
    public String edit(Model model, @PathVariable("id") String id) throws Exception {
		Comment comment = commentService.get(Long.parseLong(id));
        model.addAttribute("model",comment);
        return "comment/edit";
    }

	/**
	 * 信息
	 */
    @ResponseBody
    @RequestMapping("/info/{commentId}")
    @RequiresPermissions("comment:info")
    public R info(@PathVariable("commentId") Long commentId) throws Exception {
        Comment comment = commentService.get(commentId);
        return R.ok().put("comment", comment);
    }

    /**
	 * 保存
	 */
    @ResponseBody
    @SysLog("保存")
	@RequestMapping("/save")
	@RequiresPermissions("comment:save")
	public R save(@RequestBody Comment comment) throws Exception {
		commentService.save(comment);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
    @ResponseBody
    @SysLog("修改")
	@RequestMapping("/update")
	@RequiresPermissions("comment:update")
	public R update(@RequestBody Comment comment) throws Exception {
		commentService.update(comment);
		
		return R.ok();
	}

    /**
     * 启用
     */
    @ResponseBody
    @SysLog("启用")
    @RequestMapping("/enable")
    @RequiresPermissions("comment:update")
    public R enable(@RequestBody String[] ids) throws Exception {
        String stateValue=StateEnum.ENABLE.getCode();
		commentService.updateState(ids,stateValue);
        return R.ok();
    }
    /**
     * 禁用
     */
    @ResponseBody
    @SysLog("禁用")
    @RequestMapping("/limit")
    @RequiresPermissions("comment:update")
    public R limit(@RequestBody String[] ids) throws Exception {
        String stateValue=StateEnum.LIMIT.getCode();
		commentService.updateState(ids,stateValue);
        return R.ok();
    }
	
	/**
	 * 删除
	 */
    @ResponseBody
    @SysLog("删除")
	@RequestMapping("/delete")
	@RequiresPermissions("comment:delete")
	public R delete(@RequestBody Long[] commentIds){
		commentService.deleteBatch(commentIds);
		
		return R.ok();
	}
	
}
