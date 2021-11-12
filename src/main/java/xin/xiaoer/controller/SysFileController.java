package xin.xiaoer.controller;

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

import xin.xiaoer.common.oss.CloudStorageService;
import xin.xiaoer.common.oss.OSSFactory;
import xin.xiaoer.common.shiro.ShiroUtils;
import xin.xiaoer.common.utils.PageUtils;
import xin.xiaoer.common.utils.Query;
import xin.xiaoer.common.utils.R;
import xin.xiaoer.entity.File;
import xin.xiaoer.entity.SysUser;
import xin.xiaoer.service.FileService;


/**
 * 地产附件表
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-08-18 02:44:19
 */
@Controller
@RequestMapping("sysfile")
public class SysFileController {
	@Autowired
	private FileService fileService;
	
    /**
     * 跳转到列表页
     */
    @RequestMapping("/list")
    @RequiresPermissions("sysfile:list")
    public String list() {
        return "sysfile/list";
    }
    
	/**
	 * 列表数据
	 */
    @ResponseBody
	@RequestMapping("/listData")
	@RequiresPermissions("sysfile:list")
	public R listData(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		SysUser admin = ShiroUtils.getUserEntity();

		if (admin.getUserId() != 1L){
			query.put("spaceId", admin.getSpaceId());
		}

		List<File> sysFileList = fileService.getList(query);
        CloudStorageService cloudStorageService = OSSFactory.build();
        for (File file: sysFileList){
            file.setUrl(cloudStorageService.generatePresignedUrl(file.getUrl()));
        }
		int total = fileService.getCount(query);
		
		PageUtils pageUtil = new PageUtils(sysFileList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}

    /**
     * 跳转到新增页面
     **/
    @RequestMapping("/add")
    @RequiresPermissions("sysfile:save")
    public String add(){
        return "sysfile/add";
    }

    /**
     *   跳转到修改页面
     **/
    @RequestMapping("/edit/{id}")
    @RequiresPermissions("sysfile:update")
    public String edit(Model model, @PathVariable("id") String id){
		File sysFile = fileService.get(id);
        model.addAttribute("model",sysFile);
        return "sysfile/edit";
    }

	/**
	 * 信息
	 */
    @ResponseBody
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sysfile:info")
    public R info(@PathVariable("id") Long id){
        File sysFile = fileService.get(id.toString());
        return R.ok().put("sysFile", sysFile);
    }

    /**
	 * 保存
	 */
    @ResponseBody
    @SysLog("保存资源")
	@RequestMapping("/save")
	@RequiresPermissions("sysfile:save")
	public R save(@RequestBody File sysFile){
		fileService.save(sysFile);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
    @ResponseBody
    @SysLog("修改资源")
	@RequestMapping("/update")
	@RequiresPermissions("sysfile:update")
	public R update(@RequestBody File sysFile){
        fileService.update(sysFile);
		
		return R.ok();
	}


	/**
	 * 删除
	 */
    @ResponseBody
    @SysLog("删除资源")
	@RequestMapping("/delete")
	@RequiresPermissions("sysfile:delete")
	public R delete(@RequestBody String[] ids){
		fileService.deleteBatch(ids);

		return R.ok();
	}
	
}
