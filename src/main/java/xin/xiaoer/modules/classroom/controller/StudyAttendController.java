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

import xin.xiaoer.modules.classroom.entity.StudyAttend;
import xin.xiaoer.modules.classroom.service.StudyAttendService;
import xin.xiaoer.common.utils.PageUtils;
import xin.xiaoer.common.utils.Query;
import xin.xiaoer.common.utils.R;


/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-08-20 16:25:03
 */
@Controller
@RequestMapping("studyattend")
public class StudyAttendController {
	@Autowired
	private StudyAttendService studyAttendService;
	
    /**
     * 跳转到列表页
     */
    @RequestMapping("/list")
    @RequiresPermissions("studyattend:list")
    public String list() {
        return "studyattend/list";
    }
    
	/**
	 * 列表数据
	 */
    @ResponseBody
	@RequestMapping("/listData")
	@RequiresPermissions("studyattend:list")
	public R listData(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<StudyAttend> studyAttendList = studyAttendService.getList(query);
		int total = studyAttendService.getCount(query);
		
		PageUtils pageUtil = new PageUtils(studyAttendList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}

    /**
     * 跳转到新增页面
     **/
    @RequestMapping("/add")
    @RequiresPermissions("studyattend:save")
    public String add(){
        return "studyattend/add";
    }

    /**
     *   跳转到修改页面
     **/
    @RequestMapping("/edit/{id}")
    @RequiresPermissions("studyattend:update")
    public String edit(Model model, @PathVariable("id") Long id){
		StudyAttend studyAttend = studyAttendService.get(id);
        model.addAttribute("model",studyAttend);
        return "studyattend/edit";
    }

	/**
	 * 信息
	 */
    @ResponseBody
    @RequestMapping("/info/{id}")
    @RequiresPermissions("studyattend:info")
    public R info(@PathVariable("id") Long id){
        StudyAttend studyAttend = studyAttendService.get(id);
        return R.ok().put("studyAttend", studyAttend);
    }

    /**
	 * 保存
	 */
    @ResponseBody
    @SysLog("保存空间课堂预约")
	@RequestMapping("/save")
	@RequiresPermissions("studyattend:save")
	public R save(@RequestBody StudyAttend studyAttend){
		studyAttendService.save(studyAttend);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
    @ResponseBody
    @SysLog("修改空间课堂预约")
	@RequestMapping("/update")
	@RequiresPermissions("studyattend:update")
	public R update(@RequestBody StudyAttend studyAttend){
		studyAttendService.update(studyAttend);
		
		return R.ok();
	}

    /**
     * 启用
     */
    @ResponseBody
    @SysLog("启用空间课堂预约")
    @RequestMapping("/enable")
    @RequiresPermissions("studyattend:update")
    public R enable(@RequestBody String[] ids){
        String stateValue=StateEnum.ENABLE.getCode();
		studyAttendService.updateState(ids,stateValue);
        return R.ok();
    }
    /**
     * 禁用
     */
    @ResponseBody
    @SysLog("禁用空间课堂预约")
    @RequestMapping("/limit")
    @RequiresPermissions("studyattend:update")
    public R limit(@RequestBody String[] ids){
        String stateValue=StateEnum.LIMIT.getCode();
		studyAttendService.updateState(ids,stateValue);
        return R.ok();
    }
	
	/**
	 * 删除
	 */
    @ResponseBody
    @SysLog("删除空间课堂预约")
	@RequestMapping("/delete")
	@RequiresPermissions("studyattend:delete")
	public R delete(@RequestBody Long[] ids){
		studyAttendService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
