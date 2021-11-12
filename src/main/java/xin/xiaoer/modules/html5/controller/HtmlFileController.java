package xin.xiaoer.modules.html5.controller;

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
import xin.xiaoer.modules.html5.entity.HtmlFile;
import xin.xiaoer.modules.html5.service.HtmlFileService;
import xin.xiaoer.common.utils.PageUtils;
import xin.xiaoer.common.utils.Query;
import xin.xiaoer.common.utils.R;
import xin.xiaoer.service.FileService;


/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-08-07 09:50:18
 */
@Controller
@RequestMapping("htmlfile")
public class HtmlFileController {
	@Autowired
	private HtmlFileService htmlFileService;

	@Autowired
    private FileService fileService;
    /**
     * 跳转到列表页
     */
    @RequestMapping("/list")
    @RequiresPermissions("htmlfile:list")
    public String list() {
        return "htmlfile/list";
    }
    
	/**
	 * 列表数据
	 */
    @ResponseBody
	@RequestMapping("/listData")
	@RequiresPermissions("htmlfile:list")
	public R listData(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<HtmlFile> htmlFileList = htmlFileService.getList(query);
		int total = htmlFileService.getCount(query);
		
		PageUtils pageUtil = new PageUtils(htmlFileList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}

    /**
     * 跳转到新增页面
     **/
    @RequestMapping("/add")
    @RequiresPermissions("htmlfile:save")
    public String add(){
        return "htmlfile/add";
    }

    /**
     *   跳转到修改页面
     **/
    @RequestMapping("/edit/{id}")
    @RequiresPermissions("htmlfile:update")
    public String edit(Model model, @PathVariable("id") String id){
		HtmlFile htmlFile = htmlFileService.get(Integer.parseInt(id));
        model.addAttribute("model",htmlFile);
        return "htmlfile/edit";
    }

	/**
	 * 信息
	 */
    @ResponseBody
    @RequestMapping("/info/{id}")
    @RequiresPermissions("htmlfile:info")
    public R info(@PathVariable("id") Integer id){
        HtmlFile htmlFile = htmlFileService.get(id);
        return R.ok().put("htmlFile", htmlFile);
    }

    /**
	 * 保存
	 */
    @ResponseBody
    @SysLog("保存HTML")
	@RequestMapping("/save")
	@RequiresPermissions("htmlfile:save")
	public R save(@RequestBody HtmlFile htmlFile){
        htmlFile.setCreateBy(ShiroUtils.getUserId());
        htmlFile.setUpdateBy(ShiroUtils.getUserId());
		htmlFileService.save(htmlFile);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
    @ResponseBody
    @SysLog("修改HTML")
	@RequestMapping("/update")
	@RequiresPermissions("htmlfile:update")
	public R update(@RequestBody HtmlFile htmlFile){
        htmlFile.setUpdateBy(ShiroUtils.getUserId());
		htmlFileService.update(htmlFile);
		
		return R.ok();
	}

    /**
     * 启用
     */
    @ResponseBody
    @SysLog("启用HTML")
    @RequestMapping("/enable")
    @RequiresPermissions("htmlfile:update")
    public R enable(@RequestBody String[] ids){
        String stateValue=StateEnum.ENABLE.getCode();
		htmlFileService.updateState(ids,stateValue);
        return R.ok();
    }
    /**
     * 禁用
     */
    @ResponseBody
    @SysLog("禁用HTML")
    @RequestMapping("/limit")
    @RequiresPermissions("htmlfile:update")
    public R limit(@RequestBody String[] ids){
        String stateValue=StateEnum.LIMIT.getCode();
		htmlFileService.updateState(ids,stateValue);
        return R.ok();
    }
	
	/**
	 * 删除
	 */
    @ResponseBody
    @SysLog("删除HTML")
	@RequestMapping("/delete")
	@RequiresPermissions("htmlfile:delete")
	public R delete(@RequestBody Integer[] ids){
        CloudStorageService cloudStorageService = OSSFactory.build();
        for (Integer id: ids){
            HtmlFile htmlFile = htmlFileService.get(id);
            if (htmlFile != null){
                try {
                    cloudStorageService.deleteObject(htmlFile.getFilePath());
                } catch (Exception e){

                }
            }
        }
		htmlFileService.deleteBatch(ids);
		
		return R.ok();
	}


    @ResponseBody
    @RequestMapping("/getCodeValues")
    public R getCodeValues(@RequestParam Map<String, Object> params) {
        List<HtmlFile> htmlFiles = null;
        htmlFiles = htmlFileService.getCodeValues(params);

        return R.ok().put("data", htmlFiles);
    }
	
}
