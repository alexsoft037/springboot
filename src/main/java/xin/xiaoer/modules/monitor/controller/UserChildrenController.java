package xin.xiaoer.modules.monitor.controller;

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

import xin.xiaoer.entity.CodeValue;
import xin.xiaoer.modules.monitor.entity.UserChildren;
import xin.xiaoer.modules.monitor.service.UserChildrenService;
import xin.xiaoer.common.utils.PageUtils;
import xin.xiaoer.common.utils.Query;
import xin.xiaoer.common.utils.R;
import xin.xiaoer.service.FileService;


/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-09-03 12:51:23
 */
@Controller
@RequestMapping("userchildren")
public class UserChildrenController {
	@Autowired
	private UserChildrenService userChildrenService;

	@Autowired
    private FileService fileService;
    /**
     * 跳转到列表页
     */
    @RequestMapping("/list")
    @RequiresPermissions("userchildren:list")
    public String list() {
        return "userchildren/list";
    }

    /**
     * 列表数据
     */
    @ResponseBody
    @RequestMapping("/autocomplete")
    public R autocomplete(@RequestParam Map<String, Object> params){

//        String keywords = params.get("keywords").toString();
//        params.put("titleVague", keywords);
        List<CodeValue> codeValues = userChildrenService.getCodeValues(params);

        return R.ok().put("data", codeValues);
    }
	/**
	 * 列表数据
	 */
    @ResponseBody
	@RequestMapping("/listData")
	@RequiresPermissions("userchildren:list")
	public R listData(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<UserChildren> userChildrenList = userChildrenService.getList(query);
		int total = userChildrenService.getCount(query);
		
		PageUtils pageUtil = new PageUtils(userChildrenList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}

    /**
     * 跳转到新增页面
     **/
    @RequestMapping("/add")
    @RequiresPermissions("userchildren:save")
    public String add(){
        return "userchildren/add";
    }

    /**
     *   跳转到修改页面
     **/
    @RequestMapping("/edit/{id}")
    @RequiresPermissions("userchildren:update")
    public String edit(Model model, @PathVariable("id") Long id){
		UserChildren userChildren = userChildrenService.get(id);
        model.addAttribute("model",userChildren);
        return "userchildren/edit";
    }

	/**
	 * 信息
	 */
    @ResponseBody
    @RequestMapping("/info/{childrenId}")
    @RequiresPermissions("userchildren:info")
    public R info(@PathVariable("childrenId") Long childrenId){
        UserChildren userChildren = userChildrenService.get(childrenId);
        return R.ok().put("userChildren", userChildren);
    }

    /**
	 * 保存
	 */
    @ResponseBody
    @SysLog("保存")
	@RequestMapping("/save")
	@RequiresPermissions("userchildren:save")
	public R save(@RequestBody UserChildren userChildren){
		userChildrenService.save(userChildren);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
    @ResponseBody
    @SysLog("修改")
	@RequestMapping("/update")
	@RequiresPermissions("userchildren:update")
	public R update(@RequestBody UserChildren userChildren){
		userChildrenService.update(userChildren);
		
		return R.ok();
	}

    /**
     * 启用
     */
    @ResponseBody
    @SysLog("启用")
    @RequestMapping("/enable")
    @RequiresPermissions("userchildren:update")
    public R enable(@RequestBody String[] ids){
        String stateValue=StateEnum.ENABLE.getCode();
		userChildrenService.updateState(ids,stateValue);
        return R.ok();
    }
    /**
     * 禁用
     */
    @ResponseBody
    @SysLog("禁用")
    @RequestMapping("/limit")
    @RequiresPermissions("userchildren:update")
    public R limit(@RequestBody String[] ids){
        String stateValue=StateEnum.LIMIT.getCode();
		userChildrenService.updateState(ids,stateValue);
        return R.ok();
    }
	
	/**
	 * 删除
	 */
    @ResponseBody
    @SysLog("删除")
	@RequestMapping("/delete")
	@RequiresPermissions("userchildren:delete")
	public R delete(@RequestBody Long[] childrenIds) {
        for (Long id: childrenIds) {
            UserChildren userChildren = userChildrenService.get(id);
            fileService.deleteByRelationId(userChildren.getPhoto());
        }
		userChildrenService.deleteBatch(childrenIds);
		return R.ok();
	}
	
}
