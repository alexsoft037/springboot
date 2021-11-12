package xin.xiaoer.modules.donateweb.controller;

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
import xin.xiaoer.modules.website.entity.DonateWeb;
import xin.xiaoer.modules.website.service.DonateWebService;
import xin.xiaoer.service.FileService;
import xin.xiaoer.service.SysUserService;

import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

/**
 * @ClassName DonateWebController
 * @Description TODO 公益行动
 * @Author XiaoDong
 **/
@Controller
@RequestMapping("donateWeb")
public class DonateWebController {

    @Autowired
    private DonateWebService donateWebService;
    @Autowired
    private FileService fileService;
    @Autowired
    private SysUserService sysUserService;

    /**
     * @return java.lang.String
     * @Author XiaoDong
     * @Description TODO 跳转到列表
     * @Param []
     **/
    @RequestMapping("/list")
    @RequiresPermissions("donateweb:list")
    public String list() {
        return "donateweb/list";
    }

    /**
     * @return xin.xiaoer.common.utils.R
     * @Author XiaoDong
     * @Description TODO 列表数据
     * @Param [params]
     **/
    @ResponseBody
    @RequestMapping("/listData")
    @RequiresPermissions("donateweb:list")
    public R listData(@RequestParam Map<String, Object> params) {
        Query query = new Query(params);
        SysUser admin = ShiroUtils.getUserEntity();
        if (admin.getUserId() != 1L) {
            query.put("spaceId", admin.getSpaceId());
        }
        List<DonateWeb> donateWebs = donateWebService.getList(query);
        CloudStorageService cloudStorageService = OSSFactory.build();
        for (DonateWeb donateWeb : donateWebs) {
            List<File> files = fileService.getByRelationId(donateWeb.getFeaturedImage());
            if (files.size() > 0) {
                donateWeb.setFeaturedImage(cloudStorageService.generatePresignedUrl(files.get(0).getUrl()));
            }
            donateWeb.setCreateUser(sysUserService.get(donateWeb.getCreateBy()).getNickname());
        }
        int count = donateWebService.getCount(query);
        PageUtils pageUtils = new PageUtils(donateWebs, count, query.getLimit(), query.getPage());
        return R.ok().put("page", pageUtils);
    }


    /**
     * @return java.lang.String
     * @Author XiaoDong
     * @Description TODO 跳转到新增页面
     * @Param [model]
     **/
    @RequestMapping("/add")
    @RequiresPermissions("donateweb:save")
    public String add(Model model) {
        DonateWeb donateWeb = new DonateWeb();
        model.addAttribute("admin", ShiroUtils.getUserEntity());
        model.addAttribute("model", donateWeb);
        return "donateweb/add";
    }


    /**
     * @return xin.xiaoer.common.utils.R
     * @Author XiaoDong
     * @Description TODO 网站保存公益行动
     * @Param [params]
     **/
    @ResponseBody
    @SysLog("保存公益行动")
    @RequestMapping("/save")
    @RequiresPermissions("donateweb:save")
    public R save(@RequestBody Map<String, Object> params) {
//        Gson gson = new Gson();
//        JsonElement jsonElement = gson.toJsonTree(params);
//        DonateWeb donateWeb = gson.fromJson(jsonElement, DonateWeb.class);

        String s = FastJsonUtil.toJsonString(params);
        DonateWeb donateWeb = FastJsonUtil.toObject(s, DonateWeb.class);
        try {
            donateWeb.setContent(URLDecoder.decode(donateWeb.getContent(), "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<File> fileList = fileService.getByRelationId(donateWeb.getFeaturedImage());
        if (fileList.size() < 1) {
            throw new MyException("特色图片不能为空！");
        }
        if (fileList.size() > 1) {
            throw new MyException("特色图片不能超过1！");
        }
        String createAt = params.get("createAt").toString();
        donateWeb.setCreateAt(DateUtil.str2SDF_YMDHMS(createAt));
        donateWeb.setCreateBy(ShiroUtils.getUserId());
        donateWeb.setUpdateBy(ShiroUtils.getUserId());
        if (donateWeb.getReadCount() == null) {
            donateWeb.setReadCount(0);
        }
        if (donateWeb.getRank() == null || donateWeb.getRank() < 1 || donateWeb.getRank() > 5) {
            donateWeb.setRank(3);
        }
        donateWebService.save(donateWeb);
        return R.ok();
    }

    /**
     * @return xin.xiaoer.common.utils.R
     * @Author XiaoDong
     * @Description TODO 网站删除公益行动
     * @Param [donateWebIds]
     **/
    @ResponseBody
    @SysLog("删除公益行动")
    @RequestMapping("/delete")
    @RequiresPermissions("donateweb:delete")
    public R delete(@RequestBody Integer[] donateWebIds) {
        for (Integer donateWebId : donateWebIds) {
            DonateWeb donateWeb = donateWebService.getById(donateWebId);
            if (donateWeb != null) {
                fileService.deleteByRelationId(donateWeb.getFeaturedImage());
            }
        }
        donateWebService.deleteBatch(donateWebIds);
        return R.ok();
    }


    /**
     * @return xin.xiaoer.common.utils.R
     * @Author XiaoDong
     * @Description TODO 启用
     * @Param [ids]
     **/
    @ResponseBody
    @SysLog("启用公益行动")
    @RequestMapping("/enable")
    @RequiresPermissions("donateweb:update")
    public R enable(@RequestBody String[] ids) {
        String stateValue = StateEnum.ENABLE.getCode();
        donateWebService.updateState(ids, stateValue);
        return R.ok();
    }

    /**
     * @return xin.xiaoer.common.utils.R
     * @Author XiaoDong
     * @Description TODO 禁用
     * @Param [ids]
     **/
    @ResponseBody
    @SysLog("禁用公益行动")
    @RequestMapping("/limit")
    @RequiresPermissions("donateweb:update")
    public R limit(@RequestBody String[] ids) {
        String stateValue = StateEnum.LIMIT.getCode();
        donateWebService.updateState(ids, stateValue);
        return R.ok();
    }

    /**
     * @return java.lang.String
     * @Author XiaoDong
     * @Description TODO 跳转到修改页面
     * @Param [model, id]
     **/
    @RequestMapping("/edit/{id}")
    @RequiresPermissions("donateweb:update")
    public String edit(Model model, @PathVariable("id") String id) {
        DonateWeb donateWeb = donateWebService.getById(Integer.parseInt(id));
        SysUser sysUser = sysUserService.queryObject(donateWeb.getCreateBy());
        if (sysUser != null) {
            donateWeb.setCreateUser(sysUser.getNickname());
        }
        model.addAttribute("admin", ShiroUtils.getUserEntity());
        model.addAttribute("model", donateWeb);
        return "donateweb/edit";
    }


    /**
     * @return xin.xiaoer.common.utils.R
     * @Author XiaoDong
     * @Description TODO 修改
     * @Param [params]
     **/
    @ResponseBody
    @SysLog("修改空间活动空间头条")
    @RequestMapping("/update")
    @RequiresPermissions("spaceheadline:update")
    public R update(@RequestBody Map<String, Object> params) {
//        Gson gson = new Gson();
//        JsonElement jsonElement = gson.toJsonTree(params);
//        DonateWeb donateWeb = gson.fromJson(jsonElement, DonateWeb.class);
        String s = FastJsonUtil.toJsonString(params);
        DonateWeb donateWeb = FastJsonUtil.toObject(s, DonateWeb.class);
        try {
            donateWeb.setContent(URLDecoder.decode(donateWeb.getContent(), "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<File> fileList = fileService.getByRelationId(donateWeb.getFeaturedImage());
        if (fileList.size() < 1) {
            throw new MyException("特色图片不能为空！");
        }
        if (fileList.size() > 1) {
            throw new MyException("特色图片不能超过1！");
        }

        if (donateWeb.getRank() == null || donateWeb.getRank() < 1 || donateWeb.getRank() > 5) {
            donateWeb.setRank(3);
        }

        String createAt = params.get("createAt").toString();
        donateWeb.setCreateAt(DateUtil.str2SDF_YMDHMS(createAt));
        donateWeb.setUpdateBy(ShiroUtils.getUserId());

        donateWebService.update(donateWeb);
        return R.ok();
    }
}
