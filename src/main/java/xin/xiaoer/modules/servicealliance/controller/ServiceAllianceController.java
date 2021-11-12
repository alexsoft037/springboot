package xin.xiaoer.modules.servicealliance.controller;

import java.util.List;
import java.util.Map;
import xin.xiaoer.common.enumresource.StateEnum;
import xin.xiaoer.common.exception.MyException;
import xin.xiaoer.common.log.SysLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import xin.xiaoer.common.oss.CloudStorageService;
import xin.xiaoer.common.oss.OSSFactory;
import xin.xiaoer.common.shiro.ShiroUtils;
import xin.xiaoer.common.utils.StringUtility;
import xin.xiaoer.entity.File;
import xin.xiaoer.modules.servicealliance.entity.ServiceAlliance;
import xin.xiaoer.modules.servicealliance.service.ServiceAllianceService;
import xin.xiaoer.common.utils.PageUtils;
import xin.xiaoer.common.utils.Query;
import xin.xiaoer.common.utils.R;
import xin.xiaoer.service.FileService;


/**
 * 
 * 
 * @author chenyi
 * @email 228112142@qq.com
 * @date 2018-07-19 09:26:06
 */
@Controller
@RequestMapping("xeservicealliance")
public class ServiceAllianceController {
	@Autowired
	private ServiceAllianceService serviceAllianceService;

    @Autowired
    private FileService fileService;
    /**
     * 跳转到列表页
     */
    @RequestMapping("/list")
    @RequiresPermissions("xeservicealliance:list")
    public String list() {
        return "xeservicealliance/list";
    }
    
	/**
	 * 列表数据
	 */
    @ResponseBody
	@RequestMapping("/listData")
	@RequiresPermissions("xeservicealliance:list")
	public R listData(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<ServiceAlliance> serviceAllianceList = serviceAllianceService.getList(query);
        CloudStorageService cloudStorageService = OSSFactory.build();
		for (ServiceAlliance serviceAlliance: serviceAllianceList){
            if (!StringUtility.isEmpty(serviceAlliance.getImgPath())){
                List<File> files = fileService.getByRelationId(serviceAlliance.getImgPath());
                if (files.size() > 0){
                    serviceAlliance.setImgPath(cloudStorageService.generatePresignedUrl(files.get(0).getUrl()));
                }
            }
            if (!StringUtility.isEmpty(serviceAlliance.getContent())){
                List<File> files = fileService.getByRelationId(serviceAlliance.getContent());
                if (files.size() > 0){
                    serviceAlliance.setContent(cloudStorageService.generatePresignedUrl(files.get(0).getUrl()));
                } else {
                    serviceAlliance.setContent("");
                }
            }
        }
		int total = serviceAllianceService.getCount(query);
		
		PageUtils pageUtil = new PageUtils(serviceAllianceList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}

    /**
     * 跳转到新增页面
     **/
    @RequestMapping("/add")
    @RequiresPermissions("xeservicealliance:save")
    public String add(){
        return "xeservicealliance/add";
    }

    /**
     *   跳转到修改页面
     **/
    @RequestMapping("/edit/{id}")
    @RequiresPermissions("xeservicealliance:update")
    public String edit(Model model, @PathVariable("id") String id){
		ServiceAlliance serviceAlliance = serviceAllianceService.get(Integer.parseInt(id));
        model.addAttribute("model", serviceAlliance);
        return "xeservicealliance/edit";
    }

	/**
	 * 信息
	 */
    @ResponseBody
    @RequestMapping("/info/{id}")
    @RequiresPermissions("xeservicealliance:info")
    public R info(@PathVariable("id") Integer id){
        ServiceAlliance serviceAlliance = serviceAllianceService.get(id);
        return R.ok().put("serviceAlliance", serviceAlliance);
    }

    /**
	 * 保存
	 */
    @ResponseBody
    @SysLog("保存服务联盟")
	@RequestMapping("/save")
	@RequiresPermissions("xeservicealliance:save")
	public R save(@RequestBody ServiceAlliance serviceAlliance){
        List<File> fileList = fileService.getByRelationId(serviceAlliance.getImgPath());
        if (fileList.size() < 1) {
            throw new MyException("图片不能为空！");
        }
        if (fileList.size() > 1) {
            throw new MyException("图片不能超过1！");
        }
        serviceAlliance.setCreateBy(ShiroUtils.getUserId());
        serviceAlliance.setUpdateBy(ShiroUtils.getUserId());
		serviceAllianceService.save(serviceAlliance);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
    @ResponseBody
    @SysLog("修改服务联盟")
	@RequestMapping("/update")
	@RequiresPermissions("xeservicealliance:update")
	public R update(@RequestBody ServiceAlliance serviceAlliance){
        List<File> fileList = fileService.getByRelationId(serviceAlliance.getImgPath());
        if (fileList.size() < 1) {
            throw new MyException("图片不能为空！");
        }
        if (fileList.size() > 1) {
            throw new MyException("图片不能超过1！");
        }
        serviceAlliance.setUpdateBy(ShiroUtils.getUserId());
		serviceAllianceService.update(serviceAlliance);
		
		return R.ok();
	}

    /**
     * 启用
     */
    @ResponseBody
    @SysLog("启用服务联盟")
    @RequestMapping("/enable")
    @RequiresPermissions("xeservicealliance:update")
    public R enable(@RequestBody String[] ids){
        String stateValue=StateEnum.ENABLE.getCode();
		serviceAllianceService.updateState(ids,stateValue);
        return R.ok();
    }
    /**
     * 禁用
     */
    @ResponseBody
    @SysLog("禁用服务联盟")
    @RequestMapping("/limit")
    @RequiresPermissions("xeservicealliance:update")
    public R limit(@RequestBody String[] ids){
        String stateValue=StateEnum.LIMIT.getCode();
		serviceAllianceService.updateState(ids,stateValue);
        return R.ok();
    }
	
	/**
	 * 删除
	 */
    @ResponseBody
    @SysLog("删除服务联盟")
	@RequestMapping("/delete")
	@RequiresPermissions("xeservicealliance:delete")
	public R delete(@RequestBody Integer[] ids){
        for (Integer id:ids){
            ServiceAlliance serviceAlliance = serviceAllianceService.get(id);
            if (serviceAlliance != null){
                fileService.deleteByRelationId(serviceAlliance.getImgPath());
                fileService.deleteByRelationId(serviceAlliance.getContent());
            }
        }
		serviceAllianceService.deleteBatch(ids);
		
		return R.ok();
	}
	
}
