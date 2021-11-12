package xin.xiaoer.modules.spacehaedline.controller;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.jsoup.Jsoup;
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
import xin.xiaoer.modules.spacehaedline.entity.SpaceHeadline;
import xin.xiaoer.modules.spacehaedline.service.SpaceHeadlineService;
import xin.xiaoer.service.FileService;
import xin.xiaoer.service.SysUserService;

import java.net.URLDecoder;
import java.util.List;
import java.util.Map;


/**
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-07-24 09:07:02
 */
@Controller
@RequestMapping("spaceheadline")
public class SpaceHeadlineController {
    @Autowired
    private SpaceHeadlineService spaceHeadlineService;

    @Autowired
    private FileService fileService;

    @Autowired
    private SysUserService sysUserService;

    /**
     * 跳转到列表页
     */
    @RequestMapping("/list")
    @RequiresPermissions("spaceheadline:list")
    public String list() {
        return "spaceheadline/list";
    }

    /**
     * 列表数据
     */
    @ResponseBody
    @RequestMapping("/listData")
    @RequiresPermissions("spaceheadline:list")
    public R listData(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);

        SysUser admin = ShiroUtils.getUserEntity();
        if (admin.getUserId() != 1L) {
            query.put("spaceId", admin.getSpaceId());
        }

        List<SpaceHeadline> spaceHeadlineList = spaceHeadlineService.getList(query);
        CloudStorageService cloudStorageService = OSSFactory.build();
        for (SpaceHeadline spaceHeadline : spaceHeadlineList) {

            spaceHeadline.setContent(Jsoup.parse(spaceHeadline.getContent()).text());
            List<File> files = fileService.getByRelationId(spaceHeadline.getFeaturedImage());
            if (files.size() > 0) {
                spaceHeadline.setFeaturedImage(cloudStorageService.generatePresignedUrl(files.get(0).getUrl()));
            }
        }
        int total = spaceHeadlineService.getCount(query);

        PageUtils pageUtil = new PageUtils(spaceHeadlineList, total, query.getLimit(), query.getPage());

        return R.ok().put("page", pageUtil);
    }

    /**
     * 跳转到新增页面
     **/
    @RequestMapping("/add")
    @RequiresPermissions("spaceheadline:save")
    public String add(Model model) {
        SpaceHeadline spaceHeadline = new SpaceHeadline();
        SysUser sysUser = sysUserService.queryObject(ShiroUtils.getUserId());
        if (sysUser != null) {
            spaceHeadline.setCreateUser(sysUser.getNickname());
        }
        model.addAttribute("admin", ShiroUtils.getUserEntity());
        model.addAttribute("model", spaceHeadline);
        return "spaceheadline/add";
    }

    /**
     * 跳转到修改页面
     **/
    @RequestMapping("/edit/{id}")
    @RequiresPermissions("spaceheadline:update")
    public String edit(Model model, @PathVariable("id") String id) {
        SpaceHeadline spaceHeadline = spaceHeadlineService.get(Integer.parseInt(id));
        SysUser sysUser = sysUserService.queryObject(spaceHeadline.getCreateBy());
        if (sysUser != null) {
            spaceHeadline.setCreateUser(sysUser.getNickname());
        }
        model.addAttribute("admin", ShiroUtils.getUserEntity());
        model.addAttribute("model", spaceHeadline);
        return "spaceheadline/edit";
    }

    /**
     * 信息
     */
    @ResponseBody
    @RequestMapping("/info/{headlineId}")
    @RequiresPermissions("spaceheadline:info")
    public R info(@PathVariable("headlineId") Integer headlineId) {
        SpaceHeadline spaceHeadline = spaceHeadlineService.get(headlineId);
        return R.ok().put("spaceHeadline", spaceHeadline);
    }

    /**
     * 保存
     */
    @ResponseBody
    @SysLog("保存空间活动空间头条")
    @RequestMapping("/save")
    @RequiresPermissions("spaceheadline:save")
    public R save(@RequestBody Map<String, Object> params) {
        Gson gson = new Gson();
        JsonElement jsonElement = gson.toJsonTree(params);
        SpaceHeadline spaceHeadline = gson.fromJson(jsonElement, SpaceHeadline.class);

        if (ShiroUtils.getUserId() == 1L) {
            String spaceId = params.get("spaceId1").toString();
            if (StringUtility.isEmpty(spaceId) || !StringUtils.isNumeric(spaceId)) {
                throw new MyException("少儿空间不能为空！");
            }
            spaceHeadline.setSpaceId(Integer.parseInt(spaceId));
        } else {
            spaceHeadline.setSpaceId(ShiroUtils.getSpaceId());
        }

        try {
            spaceHeadline.setContent(URLDecoder.decode(spaceHeadline.getContent(), "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<File> fileList = fileService.getByRelationId(spaceHeadline.getFeaturedImage());
        if (fileList.size() < 1) {
            throw new MyException("特色图片不能为空！");
        }
        if (fileList.size() > 1) {
            throw new MyException("特色图片不能超过1！");
        }
        String createAt = params.get("createAt1").toString();
        spaceHeadline.setCreateAt(DateUtil.str2SDF_YMDHMS(createAt));
        spaceHeadline.setUpdateBy(ShiroUtils.getUserId());
        spaceHeadline.setCreateBy(ShiroUtils.getUserId());

        if (spaceHeadline.getRank() == null || spaceHeadline.getRank() < 1 || spaceHeadline.getRank() > 5) {
            spaceHeadline.setRank(3);
        }
        spaceHeadlineService.save(spaceHeadline);

        return R.ok();
    }

    /**
     * 修改
     */
    @ResponseBody
    @SysLog("修改空间活动空间头条")
    @RequestMapping("/update")
    @RequiresPermissions("spaceheadline:update")
    public R update(@RequestBody Map<String, Object> params) {
        Gson gson = new Gson();
        JsonElement jsonElement = gson.toJsonTree(params);
        SpaceHeadline spaceHeadline = gson.fromJson(jsonElement, SpaceHeadline.class);

        if (ShiroUtils.getUserId() == 1L) {
            String spaceId = params.get("spaceId1").toString();
            if (StringUtility.isEmpty(spaceId) || !StringUtils.isNumeric(spaceId)) {
                throw new MyException("少儿空间不能为空！");
            }
            spaceHeadline.setSpaceId(Integer.parseInt(spaceId));
        } else {
            spaceHeadline.setSpaceId(ShiroUtils.getSpaceId());
        }

        try {
            spaceHeadline.setContent(URLDecoder.decode(spaceHeadline.getContent(), "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<File> fileList = fileService.getByRelationId(spaceHeadline.getFeaturedImage());
        if (fileList.size() < 1) {
            throw new MyException("特色图片不能为空！");
        }
        if (fileList.size() > 1) {
            throw new MyException("特色图片不能超过1！");
        }
        String createAt = params.get("createAt1").toString();
        spaceHeadline.setCreateAt(DateUtil.str2SDF_YMDHMS(createAt));
        spaceHeadline.setUpdateBy(ShiroUtils.getUserId());

        //黄小东
        if (spaceHeadline.getRank() == null || spaceHeadline.getRank() < 1 || spaceHeadline.getRank() > 5) {
            spaceHeadline.setRank(3);
        }
        spaceHeadlineService.update(spaceHeadline);

        return R.ok();
    }

    /**
     * 启用
     */
    @ResponseBody
    @SysLog("启用空间活动空间头条")
    @RequestMapping("/enable")
    @RequiresPermissions("spaceheadline:update")
    public R enable(@RequestBody String[] ids) {
        String stateValue = StateEnum.ENABLE.getCode();
        spaceHeadlineService.updateState(ids, stateValue);
        return R.ok();
    }

    /**
     * 禁用
     */
    @ResponseBody
    @SysLog("禁用空间活动空间头条")
    @RequestMapping("/limit")
    @RequiresPermissions("spaceheadline:update")
    public R limit(@RequestBody String[] ids) {
        String stateValue = StateEnum.LIMIT.getCode();
        spaceHeadlineService.updateState(ids, stateValue);
        return R.ok();
    }

    /**
     * 删除
     */
    @ResponseBody
    @SysLog("删除空间活动空间头条")
    @RequestMapping("/delete")
    @RequiresPermissions("spaceheadline:delete")
    public R delete(@RequestBody Integer[] headlineIds) {
        for (Integer headLineId : headlineIds) {
            SpaceHeadline spaceHeadline = spaceHeadlineService.get(headLineId);
            if (spaceHeadline != null) {
                fileService.deleteByRelationId(spaceHeadline.getFeaturedImage());
            }
        }
        spaceHeadlineService.deleteBatch(headlineIds);

        return R.ok();
    }

}
