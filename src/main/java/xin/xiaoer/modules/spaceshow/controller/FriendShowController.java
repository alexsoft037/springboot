package xin.xiaoer.modules.spaceshow.controller;

import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import xin.xiaoer.common.enumresource.StateEnum;
import xin.xiaoer.common.exception.MyException;
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
import xin.xiaoer.common.utils.StringUtility;
import xin.xiaoer.entity.File;
import xin.xiaoer.entity.SysUser;
import xin.xiaoer.modules.spaceshow.entity.SpaceShow;
import xin.xiaoer.modules.spaceshow.service.SpaceShowService;
import xin.xiaoer.common.utils.PageUtils;
import xin.xiaoer.common.utils.Query;
import xin.xiaoer.common.utils.R;
import xin.xiaoer.service.FileService;
import xin.xiaoer.service.SysUserService;


/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-08-24 23:03:45
 */
@Controller
@RequestMapping("friendShow")
public class FriendShowController {
	@Autowired
	private SpaceShowService spaceShowService;

	@Autowired
    private SysUserService sysUserService;

	@Autowired
    private FileService fileService;
    /**
     * 跳转到列表页
     */
    @RequestMapping("/list")
    @RequiresPermissions("friendShow:list")
    public String list() {
        return "friendShow/list";
    }
    
	/**
	 * 列表数据
	 */
    @ResponseBody
	@RequestMapping("/listData")
	@RequiresPermissions("friendShow:list")
	public R listData(@RequestParam Map<String, Object> params){
		//查询列表数据
        params.put("showTypeCode", "SHT001");
        Query query = new Query(params);

        SysUser admin = ShiroUtils.getUserEntity();
        if (admin.getUserId() != 1L){
            query.put("spaceId", admin.getSpaceId());
        }

		List<SpaceShow> spaceShowList = spaceShowService.getList(query);
        CloudStorageService cloudStorageService = OSSFactory.build();
        for (SpaceShow spaceShow: spaceShowList) {
            byte[] contentBytes = Base64.decodeBase64(spaceShow.getContent().getBytes());
            spaceShow.setContent(new String(contentBytes));
            List<File> files = fileService.getByRelationId(spaceShow.getImage());
            if (files.size() > 0 && StringUtility.isNotEmpty(files.get(0).getUrl())) {
                spaceShow.setImage(cloudStorageService.generatePresignedUrl(files.get(0).getUrl()));
            } else {
                spaceShow.setImage("");
            }
        }
		int total = spaceShowService.getCount(query);
		
		PageUtils pageUtil = new PageUtils(spaceShowList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}

    /**
     * 跳转到新增页面
     **/
    @RequestMapping("/add")
    @RequiresPermissions("friendShow:save")
    public String add(Model model){
        model.addAttribute("admin",ShiroUtils.getUserEntity());
        return "friendShow/add";
    }

    /**
     *   跳转到修改页面
     **/
    @RequestMapping("/edit/{id}")
    @RequiresPermissions("friendShow:update")
    public String edit(Model model, @PathVariable("id") String id){
		SpaceShow spaceShow = spaceShowService.get(Long.parseLong(id));
        byte[] contentBytes = Base64.decodeBase64(spaceShow.getContent().getBytes());
        spaceShow.setContent(new String(contentBytes));
        model.addAttribute("admin",ShiroUtils.getUserEntity());
        model.addAttribute("model",spaceShow);
        return "friendShow/edit";
    }

	/**
	 * 信息
	 */
    @ResponseBody
    @RequestMapping("/info/{showId}")
    @RequiresPermissions("friendShow:info")
    public R info(@PathVariable("showId") Long showId){
        SpaceShow spaceShow = spaceShowService.get(showId);
        byte[] contentBytes = Base64.decodeBase64(spaceShow.getContent().getBytes());
        spaceShow.setContent(new String(contentBytes));
        return R.ok().put("spaceShow", spaceShow);
    }

    /**
	 * 保存
	 */
    @ResponseBody
    @SysLog("保存空间秀场朋友圈")
	@RequestMapping("/save")
	@RequiresPermissions("friendShow:save")
	public R save(@RequestBody Map<String, Object> params) {
        Gson gson = new Gson();
        JsonElement jsonElement = gson.toJsonTree(params);
        SpaceShow spaceShow = gson.fromJson(jsonElement, SpaceShow.class);

        if (ShiroUtils.getUserId() == 1L){
            String spaceId = params.get("spaceId1").toString();
            if (StringUtility.isEmpty(spaceId) || !StringUtils.isNumeric(spaceId)) {
                throw new MyException("少儿空间不能为空！");
            }
            spaceShow.setSpaceId(Integer.parseInt(spaceId));
        } else {
            spaceShow.setSpaceId(ShiroUtils.getSpaceId());
        }

        spaceShow.setCreateBy(ShiroUtils.getUserId());
        spaceShow.setUpdateBy(ShiroUtils.getUserId());
		spaceShowService.save(spaceShow);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
    @ResponseBody
    @SysLog("修改空间秀场朋友圈")
	@RequestMapping("/update")
	@RequiresPermissions("friendShow:update")
	public R update(@RequestBody Map<String, Object> params) {
        Gson gson = new Gson();
        JsonElement jsonElement = gson.toJsonTree(params);
        SpaceShow spaceShow = gson.fromJson(jsonElement, SpaceShow.class);

        if (ShiroUtils.getUserId() == 1L){
            String spaceId = params.get("spaceId1").toString();
            if (StringUtility.isEmpty(spaceId) || !StringUtils.isNumeric(spaceId)) {
                throw new MyException("少儿空间不能为空！");
            }
            spaceShow.setSpaceId(Integer.parseInt(spaceId));
        } else {
            spaceShow.setSpaceId(ShiroUtils.getSpaceId());
        }

        spaceShow.setUpdateBy(ShiroUtils.getUserId());
		spaceShowService.update(spaceShow);
		
		return R.ok();
	}

    /**
     * 启用
     */
    @ResponseBody
    @SysLog("启用空间秀场朋友圈")
    @RequestMapping("/enable")
    @RequiresPermissions("friendShow:update")
    public R enable(@RequestBody String[] ids){
        String stateValue=StateEnum.ENABLE.getCode();
		spaceShowService.updateState(ids,stateValue);
        return R.ok();
    }
    /**
     * 禁用
     */
    @ResponseBody
    @SysLog("禁用空间秀场朋友圈")
    @RequestMapping("/limit")
    @RequiresPermissions("friendShow:update")
    public R limit(@RequestBody String[] ids) {
        String stateValue=StateEnum.LIMIT.getCode();
		spaceShowService.updateState(ids,stateValue);
        return R.ok();
    }
	
	/**
	 * 删除
	 */
    @ResponseBody
    @SysLog("删除空间秀场朋友圈")
	@RequestMapping("/delete")
	@RequiresPermissions("friendShow:delete")
	public R delete(@RequestBody Long[] showIds) {
        for (Long showId: showIds){
            SpaceShow spaceShow = spaceShowService.get(showId);
            fileService.deleteByRelationId(spaceShow.getVideo());
            fileService.deleteByRelationId(spaceShow.getImage());
        }
		spaceShowService.deleteBatch(showIds);
		return R.ok();
	}
}
