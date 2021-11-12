package xin.xiaoer.modules.classroom.controller;

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

import xin.xiaoer.modules.classroom.entity.UserLesson;
import xin.xiaoer.modules.classroom.service.UserLessonService;
import xin.xiaoer.common.utils.PageUtils;
import xin.xiaoer.common.utils.Query;
import xin.xiaoer.common.utils.R;


/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-08-21 18:17:04
 */
@Controller
@RequestMapping("userlesson")
public class UserLessonController {
	@Autowired
	private UserLessonService userLessonService;
	
    /**
     * 跳转到列表页
     */
    @RequestMapping("/list")
    @RequiresPermissions("userlesson:list")
    public String list() {
        return "userlesson/list";
    }
    
	/**
	 * 列表数据
	 */
    @ResponseBody
	@RequestMapping("/listData")
	@RequiresPermissions("userlesson:list")
	public R listData(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<UserLesson> userLessonList = userLessonService.getList(query);
		int total = userLessonService.getCount(query);
		
		PageUtils pageUtil = new PageUtils(userLessonList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}

    /**
     * 跳转到新增页面
     **/
    @RequestMapping("/add")
    @RequiresPermissions("userlesson:save")
    public String add(){
        return "userlesson/add";
    }

    /**
     *   跳转到修改页面
     **/
    @RequestMapping("/edit/{id}")
    @RequiresPermissions("userlesson:update")
    public String edit(Model model, @PathVariable("id") String id){
		UserLesson userLesson = userLessonService.get(Integer.parseInt(id));
        model.addAttribute("model",userLesson);
        return "userlesson/edit";
    }

	/**
	 * 信息
	 */
    @ResponseBody
    @RequestMapping("/info/{id}")
    @RequiresPermissions("userlesson:info")
    public R info(@PathVariable("id") Integer id){
        UserLesson userLesson = userLessonService.get(id);
        return R.ok().put("userLesson", userLesson);
    }

    /**
	 * 保存
	 */
    @ResponseBody
    @SysLog("保存")
	@RequestMapping("/save")
	@RequiresPermissions("userlesson:save")
	public R save(@RequestBody UserLesson userLesson){
		userLessonService.save(userLesson);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
    @ResponseBody
    @SysLog("修改")
	@RequestMapping("/update")
	@RequiresPermissions("userlesson:update")
	public R update(@RequestBody UserLesson userLesson){
		userLessonService.update(userLesson);
		
		return R.ok();
	}

    /**
     * 启用
     */
    @ResponseBody
    @SysLog("启用")
    @RequestMapping("/enable")
    @RequiresPermissions("userlesson:update")
    public R enable(@RequestBody String[] ids){
        String stateValue=StateEnum.ENABLE.getCode();
		userLessonService.updateState(ids,stateValue);
        return R.ok();
    }
    /**
     * 禁用
     */
    @ResponseBody
    @SysLog("禁用")
    @RequestMapping("/limit")
    @RequiresPermissions("userlesson:update")
    public R limit(@RequestBody String[] ids){
        String stateValue=StateEnum.LIMIT.getCode();
		userLessonService.updateState(ids,stateValue);
        return R.ok();
    }
	
	/**
	 * 删除
	 */
    @ResponseBody
    @SysLog("删除")
	@RequestMapping("/delete")
	@RequiresPermissions("userlesson:delete")
	public R delete(@RequestBody Integer[] ids){
		userLessonService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
