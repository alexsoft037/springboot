package xin.xiaoer.modules.monitor.controller;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import xin.xiaoer.common.enumresource.StateEnum;
import xin.xiaoer.common.exception.MyException;
import xin.xiaoer.common.log.SysLog;
import xin.xiaoer.common.oss.CloudStorageService;
import xin.xiaoer.common.oss.OSSFactory;
import xin.xiaoer.common.shiro.ShiroUtils;
import xin.xiaoer.common.utils.*;
import xin.xiaoer.entity.File;
import xin.xiaoer.entity.SysUser;
import xin.xiaoer.modules.monitor.entity.ChildrenLost;
import xin.xiaoer.modules.monitor.service.ChildrenLostService;
import xin.xiaoer.service.FileService;

import java.util.List;
import java.util.Map;


/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-09-03 12:51:23
 */
@Controller
@RequestMapping("childrenlost")
public class ChildrenLostController {
	@Autowired
	private ChildrenLostService childrenLostService;

	@Autowired
    private FileService fileService;
    /**
     * 跳转到列表页
     */
    @RequestMapping("/list")
    @RequiresPermissions("childrenlost:list")
    public String list() {
        return "childrenlost/list";
    }
    
	/**
	 * 列表数据
	 */
    @ResponseBody
	@RequestMapping("/listData")
	@RequiresPermissions("childrenlost:list")
	public R listData(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

        SysUser admin = ShiroUtils.getUserEntity();
        if (admin.getUserId() != 1L){
            query.put("spaceId", admin.getSpaceId());
        }

		List<ChildrenLost> childrenLostList = childrenLostService.getList(query);
        CloudStorageService cloudStorageService = OSSFactory.build();
		for (ChildrenLost childrenLost: childrenLostList){
		    List<File> files = fileService.getByRelationId(childrenLost.getPhoto());
		    if (files.size() > 0){
		        childrenLost.setPhoto(cloudStorageService.generatePresignedUrl(files.get(0).getUrl()));
            } else {
		        childrenLost.setPhoto("");
            }
        }
		int total = childrenLostService.getCount(query);
		
		PageUtils pageUtil = new PageUtils(childrenLostList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}

    /**
     * 跳转到新增页面
     **/
    @RequestMapping("/add")
    @RequiresPermissions("childrenlost:save")
    public String add(Model model){
        model.addAttribute("admin",ShiroUtils.getUserEntity());
        model.addAttribute("BaiduApiKey", Constant.BaiduApiKey);
        return "childrenlost/add";
    }

    /**
     *   跳转到修改页面
     **/
    @RequestMapping("/edit/{id}")
    @RequiresPermissions("childrenlost:update")
    public String edit(Model model, @PathVariable("id") String id){
		ChildrenLost childrenLost = childrenLostService.get(Integer.parseInt(id));
        model.addAttribute("admin",ShiroUtils.getUserEntity());
        model.addAttribute("BaiduApiKey", Constant.BaiduApiKey);
        model.addAttribute("model",childrenLost);
        return "childrenlost/edit";
    }

	/**
	 * 信息
	 */
    @ResponseBody
    @RequestMapping("/info/{id}")
    @RequiresPermissions("childrenlost:info")
    public R info(@PathVariable("id") Integer id){
        ChildrenLost childrenLost = childrenLostService.get(id);
        return R.ok().put("childrenLost", childrenLost);
    }

    /**
	 * 保存
	 */
    @ResponseBody
    @SysLog("保存监控丢失通知")
	@RequestMapping("/save")
	@RequiresPermissions("childrenlost:save")
	public R save(@RequestBody Map<String, Object> params){
        Gson gson = new Gson();
        JsonElement jsonElement = gson.toJsonTree(params);
        ChildrenLost childrenLost = gson.fromJson(jsonElement, ChildrenLost.class);

        if (ShiroUtils.getUserId() == 1L){
            String spaceId = params.get("spaceId1").toString();
            if (StringUtility.isEmpty(spaceId) || !StringUtils.isNumeric(spaceId)) {
                throw new MyException("少儿空间不能为空！");
            }
            childrenLost.setSpaceId(Integer.parseInt(spaceId));
        } else {
            childrenLost.setSpaceId(ShiroUtils.getSpaceId());
        }

        String childrenId = params.get("childrenId1").toString();
        if (!StringUtility.isNumeric(childrenId)) {
            throw new MyException("姓名不能为空！");
        }
        childrenLost.setChildrenId(Long.parseLong(childrenId));
        String lostTime = params.get("lostTime1").toString();
        childrenLost.setLostTime(DateUtil.str2SDF_YMDHMS(lostTime));
        childrenLost.setCreateBy(ShiroUtils.getUserId());
		childrenLostService.save(childrenLost);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
    @ResponseBody
    @SysLog("修改监控丢失通知")
	@RequestMapping("/update")
	@RequiresPermissions("childrenlost:update")
	public R update(@RequestBody Map<String, Object> params){
        Gson gson = new Gson();
        JsonElement jsonElement = gson.toJsonTree(params);
        ChildrenLost childrenLost = gson.fromJson(jsonElement, ChildrenLost.class);

        if (ShiroUtils.getUserId() == 1L){
            String spaceId = params.get("spaceId1").toString();
            if (StringUtility.isEmpty(spaceId) || !StringUtils.isNumeric(spaceId)) {
                throw new MyException("少儿空间不能为空！");
            }
            childrenLost.setSpaceId(Integer.parseInt(spaceId));
        } else {
            childrenLost.setSpaceId(ShiroUtils.getSpaceId());
        }

        String childrenId = params.get("childrenId1").toString();
        if (!StringUtility.isNumeric(childrenId)) {
            throw new MyException("姓名不能为空！");
        }
        childrenLost.setChildrenId(Long.parseLong(childrenId));
        String lostTime = params.get("lostTime1").toString();
        childrenLost.setLostTime(DateUtil.str2SDF_YMDHMS(lostTime));
        childrenLost.setCreateBy(ShiroUtils.getUserId());
		childrenLostService.update(childrenLost);
		
		return R.ok();
	}

    /**
     * 启用
     */
    @ResponseBody
    @SysLog("启用监控丢失通知")
    @RequestMapping("/enable")
    @RequiresPermissions("childrenlost:update")
    public R enable(@RequestBody String[] ids){
        String stateValue=StateEnum.ENABLE.getCode();
		childrenLostService.updateState(ids,stateValue);
        return R.ok();
    }
    /**
     * 禁用
     */
    @ResponseBody
    @SysLog("禁用监控丢失通知")
    @RequestMapping("/limit")
    @RequiresPermissions("childrenlost:update")
    public R limit(@RequestBody String[] ids){
        String stateValue=StateEnum.LIMIT.getCode();
		childrenLostService.updateState(ids,stateValue);
        return R.ok();
    }
	
	/**
	 * 删除
	 */
    @ResponseBody
    @SysLog("删除监控丢失通知")
	@RequestMapping("/delete")
	@RequiresPermissions("childrenlost:delete")
	public R delete(@RequestBody Integer[] ids){
        for (Integer id: ids){
            ChildrenLost childrenLost = childrenLostService.get(id);
            fileService.deleteByRelationId(childrenLost.getPhoto());
        }

		childrenLostService.deleteBatch(ids);

		return R.ok();
	}
	
}
