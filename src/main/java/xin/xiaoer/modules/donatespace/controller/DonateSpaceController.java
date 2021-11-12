package xin.xiaoer.modules.donatespace.controller;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
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
import xin.xiaoer.entity.SysUser;
import xin.xiaoer.modules.comment.service.CommentService;
import xin.xiaoer.modules.donatespace.entity.DonateSpace;
import xin.xiaoer.modules.donatespace.entity.DonateUser;
import xin.xiaoer.modules.donatespace.service.DonateSpaceService;
import xin.xiaoer.common.utils.PageUtils;
import xin.xiaoer.common.utils.Query;
import xin.xiaoer.common.utils.R;
import xin.xiaoer.modules.donatespace.entity.DonateSpaceIntro;
import xin.xiaoer.modules.donatespace.service.DonateSpaceIntroService;
import xin.xiaoer.modules.donatespace.entity.DonateSpaceProcess;
import xin.xiaoer.modules.donatespace.service.DonateSpaceProcessService;
import xin.xiaoer.modules.donatespace.service.DonateUserService;
import xin.xiaoer.modules.mobile.entity.DonateUserResume;
import xin.xiaoer.service.FileService;


/**
 * 
 * 
 * @author chenyi
 * @email 228112142@qq.com
 * @date 2018-07-20 00:07:30
 */
@Controller
@RequestMapping("donatespace")
public class DonateSpaceController {
	@Autowired
	private DonateSpaceService donateSpaceService;

	@Autowired
    private DonateSpaceIntroService donateSpaceIntroService;

	@Autowired
    private DonateSpaceProcessService donateSpaceProcessService;

	@Autowired
    private DonateUserService donateUserService;

	@Autowired
    private CommentService commentService;

    @Autowired
    private FileService fileService;
    /**
     * 跳转到列表页
     */
    @RequestMapping("/list")
    @RequiresPermissions("donatespace:list")
    public String list() {
        return "donatespace/list";
    }
    
	/**
	 * 列表数据
	 */
    @ResponseBody
	@RequestMapping("/listData")
	@RequiresPermissions("donatespace:list")
	public R listData(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
        SysUser admin = ShiroUtils.getUserEntity();
        if (admin.getUserId() != 1L){
            query.put("spaceId", admin.getSpaceId());
        }
		List<DonateSpace> donateSpaceList = donateSpaceService.getList(query);
        CloudStorageService cloudStorageService = OSSFactory.build();
		for (DonateSpace donateSpace: donateSpaceList){
            List<File> files = fileService.getByRelationId(donateSpace.getSpaceImage());
            if (files.size() > 0){
                donateSpace.setSpaceImage(cloudStorageService.generatePresignedUrl(files.get(0).getUrl()));
            }
            Map<String, Object> search = new HashMap<>();
            search.put("articleId", donateSpace.getItemId());
            search.put("articleTypeCode", "AT0001");

            Integer commentCount = commentService.getCount(search);
            donateSpace.setCommentCount(commentCount);

            DonateUserResume donateUserResume = donateUserService.getDonateResumeByItemId(donateSpace.getItemId());
            if (donateUserResume != null){
                donateSpace.setDonateAmount(donateUserResume.getDonateAmount());
                donateSpace.setDonateUserCount(donateUserResume.getDonateUserCount());
            }
        }
		int total = donateSpaceService.getCount(query);
		
		PageUtils pageUtil = new PageUtils(donateSpaceList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}

    /**
     * 跳转到新增页面
     **/
    @RequestMapping("/add")
    @RequiresPermissions("donatespace:save")
    public String add(Model model){
        model.addAttribute("admin",ShiroUtils.getUserEntity());
        return "donatespace/add";
    }

    /**
     *   跳转到修改页面
     **/
    @RequestMapping("/edit/{id}")
    @RequiresPermissions("donatespace:update")
    public String edit(Model model, @PathVariable("id") String id){
		DonateSpace donateSpace = donateSpaceService.get(Long.parseLong(id));
		try {
            donateSpace.setIntroContent(URLDecoder.decode(donateSpace.getIntroContent(), "UTF-8"));
            donateSpace.setProcessContent(URLDecoder.decode(donateSpace.getProcessContent(), "UTF-8"));
        } catch (Exception e){
		    e.printStackTrace();
        }
        model.addAttribute("admin",ShiroUtils.getUserEntity());
        model.addAttribute("model",donateSpace);
        return "donatespace/edit";
    }

	/**
	 * 信息
	 */
    @ResponseBody
    @RequestMapping("/info/{itemId}")
    @RequiresPermissions("donatespace:info")
    public R info(@PathVariable("itemId") Long itemId){
        DonateSpace donateSpace = donateSpaceService.get(itemId);
        return R.ok().put("donateSpace", donateSpace);
    }

    /**
	 * 保存
	 */
    @ResponseBody
    @SysLog("保存爱心空间项目")
	@RequestMapping("/save")
	@RequiresPermissions("donatespace:save")
	public R save(@RequestBody Map<String, Object> params){
        Gson gson = new Gson();
        JsonElement jsonElement = gson.toJsonTree(params);
        DonateSpace donateSpace = gson.fromJson(jsonElement, DonateSpace.class);

        if (ShiroUtils.getUserId() == 1L){
            String spaceId = params.get("spaceId1").toString();
            if (StringUtility.isEmpty(spaceId) || !StringUtils.isNumeric(spaceId)) {
                throw new MyException("少儿空间不能为空！");
            }
            donateSpace.setSpaceId(Integer.parseInt(spaceId));
        } else {
            donateSpace.setSpaceId(ShiroUtils.getSpaceId());
        }

        String introContent = "";
        String processContent = "";
        try {
            introContent =URLDecoder.decode(donateSpace.getIntroContent(), "UTF-8");
        } catch (Exception e){
            e.printStackTrace();
        }
        try {
            processContent = URLDecoder.decode(donateSpace.getProcessContent(), "UTF-8");
        } catch (Exception e){
            e.printStackTrace();
        }
        if (donateSpace.getDsTypeCode().isEmpty()) {
            throw new MyException("分类不能为空！");
        }
        if (Jsoup.parse(introContent).text().isEmpty()) {
            throw new MyException("项目介绍不能为空！");
        }
        if (Jsoup.parse(processContent).text().isEmpty()) {
            throw new MyException("项目进展不能为空！");
        }
        if (donateSpace.getSpaceImage().isEmpty()) {
            throw new MyException("特色图片不能为空！");
        }
        List<File> fileList = fileService.getByRelationId(donateSpace.getSpaceImage());
        if (fileList.size() < 1) {
            throw new MyException("特色图片不能为空！");
        }
        if (donateSpace.getDsStatusCode().isEmpty()) {
            throw new MyException("进度不能为空");
        }
        donateSpace.setCreateBy(ShiroUtils.getUserId());
        donateSpace.setUpdateBy(ShiroUtils.getUserId());
		donateSpaceService.save(donateSpace);

        DonateSpaceIntro donateSpaceIntro = new DonateSpaceIntro();
        donateSpaceIntro.setItemId(donateSpace.getItemId());
        donateSpaceIntro.setIntroImage(donateSpace.getIntroImage());
        donateSpaceIntro.setIntroContent(introContent);
        donateSpaceIntro.setState("1");
        donateSpaceIntro.setCreateBy(ShiroUtils.getUserId());
        donateSpaceIntro.setUpdateBy(ShiroUtils.getUserId());
        donateSpaceIntroService.save(donateSpaceIntro);

        DonateSpaceProcess donateSpaceProcess = new DonateSpaceProcess();
        donateSpaceProcess.setItemId(donateSpace.getItemId());
        donateSpaceProcess.setProcessImage(donateSpace.getProcessImage());
        donateSpaceProcess.setProcessContent(processContent);
        donateSpaceProcess.setState("1");
        donateSpaceProcess.setCreateBy(ShiroUtils.getUserId());
        donateSpaceProcess.setUpdateBy(ShiroUtils.getUserId());
        donateSpaceProcessService.save(donateSpaceProcess);

		return R.ok();
	}
	
	/**
	 * 修改
	 */
    @ResponseBody
    @SysLog("修改爱心空间项目")
	@RequestMapping("/update")
	@RequiresPermissions("donatespace:update")
    public R update(@RequestBody Map<String, Object> params){
        Gson gson = new Gson();
        JsonElement jsonElement = gson.toJsonTree(params);
        DonateSpace donateSpace = gson.fromJson(jsonElement, DonateSpace.class);
        if (ShiroUtils.getUserId() == 1L){
            String spaceId = params.get("spaceId1").toString();
            if (StringUtility.isEmpty(spaceId) || !StringUtils.isNumeric(spaceId)) {
                throw new MyException("少儿空间不能为空！");
            }
            donateSpace.setSpaceId(Integer.parseInt(spaceId));
        } else {
            donateSpace.setSpaceId(ShiroUtils.getSpaceId());
        }

        String introContent = "";
        String processContent = "";
        try {
            introContent =URLDecoder.decode(donateSpace.getIntroContent(), "UTF-8");
        } catch (Exception e){
            e.printStackTrace();
        }
        try {
            processContent = URLDecoder.decode(donateSpace.getProcessContent(), "UTF-8");
        } catch (Exception e){
            e.printStackTrace();
        }
        if (donateSpace.getDsTypeCode().isEmpty()) {
            throw new MyException("分类不能为空！");
        }
        if (Jsoup.parse(introContent).text().isEmpty()) {
            throw new MyException("项目介绍不能为空！");
        }
        if (Jsoup.parse(processContent).text().isEmpty()) {
            throw new MyException("项目进展不能为空！");
        }
        if (donateSpace.getSpaceImage().isEmpty()) {
            throw new MyException("特色图片不能为空！");
        }
        List<File> fileList = fileService.getByRelationId(donateSpace.getSpaceImage());
        if (fileList.size() < 1) {
            throw new MyException("特色图片不能为空！");
        }
        if (donateSpace.getDsStatusCode().isEmpty()) {
            throw new MyException("进度不能为空");
        }
        donateSpace.setUpdateBy(ShiroUtils.getUserId());
		donateSpaceService.update(donateSpace);

        DonateSpaceIntro donateSpaceIntro = donateSpaceIntroService.get(donateSpace.getItemId());
        if (donateSpaceIntro == null){
            donateSpaceIntro = new DonateSpaceIntro();
        }
        donateSpaceIntro.setIntroImage(donateSpace.getIntroImage());
        donateSpaceIntro.setIntroContent(introContent);
        donateSpaceIntro.setUpdateBy(ShiroUtils.getUserId());
        if (donateSpaceIntro.getItemId() == null){
            donateSpaceIntro.setItemId(donateSpace.getItemId());
            donateSpaceIntro.setCreateBy(ShiroUtils.getUserId());
            donateSpaceIntroService.save(donateSpaceIntro);
        }else {
            donateSpaceIntroService.update(donateSpaceIntro);
        }

        DonateSpaceProcess donateSpaceProcess = donateSpaceProcessService.get(donateSpace.getItemId());
        if (donateSpaceProcess == null){
            donateSpaceProcess = new DonateSpaceProcess();
        }
        donateSpaceProcess.setProcessImage(donateSpace.getProcessImage());
        donateSpaceProcess.setProcessContent(processContent);
        donateSpaceProcess.setUpdateBy(ShiroUtils.getUserId());
        if (donateSpaceProcess.getItemId() == null){
            donateSpaceProcess.setItemId(donateSpace.getItemId());
            donateSpaceProcess.setCreateBy(ShiroUtils.getUserId());
            donateSpaceProcessService.save(donateSpaceProcess);
        } else {
            donateSpaceProcessService.update(donateSpaceProcess);
        }

		return R.ok();
	}

    /**
     * 启用
     */
    @ResponseBody
    @SysLog("启用爱心空间项目")
    @RequestMapping("/enable")
    @RequiresPermissions("donatespace:update")
    public R enable(@RequestBody String[] ids){
        String stateValue=StateEnum.ENABLE.getCode();
		donateSpaceService.updateState(ids,stateValue);
		donateSpaceIntroService.updateState(ids,stateValue);
		donateSpaceProcessService.updateState(ids,stateValue);
        return R.ok();
    }
    /**
     * 禁用
     */
    @ResponseBody
    @SysLog("禁用爱心空间项目")
    @RequestMapping("/limit")
    @RequiresPermissions("donatespace:update")
    public R limit(@RequestBody String[] ids){
        String stateValue=StateEnum.LIMIT.getCode();
		donateSpaceService.updateState(ids,stateValue);
        donateSpaceIntroService.updateState(ids,stateValue);
        donateSpaceProcessService.updateState(ids,stateValue);
        return R.ok();
    }
	
	/**
	 * 删除
	 */
    @ResponseBody
    @SysLog("删除爱心空间项目")
	@RequestMapping("/delete")
	@RequiresPermissions("donatespace:delete")
	public R delete(@RequestBody Long[] itemIds){
        for (Long itemId: itemIds){
            DonateSpace donateSpace = donateSpaceService.get(itemId);
            if (donateSpace != null){
                fileService.deleteByRelationId(donateSpace.getSpaceImage());
            }
        }
        donateSpaceService.deleteBatch(itemIds);
        donateSpaceIntroService.deleteBatch(itemIds);
        donateSpaceProcessService.deleteBatch(itemIds);
		return R.ok();
	}
	
}
