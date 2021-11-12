package xin.xiaoer.modules.spaceofthehall.controller;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import xin.xiaoer.common.exception.MyException;
import xin.xiaoer.common.log.SysLog;
import xin.xiaoer.common.oss.CloudStorageService;
import xin.xiaoer.common.oss.OSSFactory;
import xin.xiaoer.common.shiro.ShiroUtils;
import xin.xiaoer.common.utils.PageUtils;
import xin.xiaoer.common.utils.Query;
import xin.xiaoer.common.utils.R;
import xin.xiaoer.entity.File;
import xin.xiaoer.modules.spaceofthehall.entity.SpaceOfTheHall;
import xin.xiaoer.modules.spaceofthehall.service.SpaceOfTheHallService;
import xin.xiaoer.service.FileService;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("spaceofthehall")
@Api("空间大厅管理")
public class SpaceOfTheHallController {

    @Autowired
    private SpaceOfTheHallService spaceOfTheHallService;
    @Autowired
    private FileService fileService;


    /**
     * 跳转到列表页
     */
    @GetMapping("/list")
    @RequiresPermissions("spaceofthehall:list")
    public String list() {
        return "spaceofthehall/list";
    }

    /**
     * 列表数据
     *
     * @return
     */
    @ResponseBody
    @PostMapping("/listData")
    @ApiOperation(value = "获取空间大厅列表")
    @RequiresPermissions("spaceofthehall:list")
    public R listData(@RequestBody @ApiParam(name = "params", value = "page:当前页,limit:条数,status:是(1)否(0)启用") Map<String, Object> params) {
        Query query = new Query(params);
        if (query.get("status") != null && !"1".equals(query.get("status")) && !"0".equals(query.get("status"))) {
            query.put("status", null);
        }
//        SysUser admin = ShiroUtils.getUserEntity();
//        if (admin.getUserId() != 1L) {
//            query.put("spaceId", admin.getSpaceId());
//        }
        List<SpaceOfTheHall> list = spaceOfTheHallService.getList(params);//查询出大厅列表
        CloudStorageService cloudStorageService = OSSFactory.build();//oss服务
        for (SpaceOfTheHall spaceOfTheHall : list) {
            List<File> files = fileService.getByRelationId(spaceOfTheHall.getFeaturedImage());
            if (files.size() > 0) {
                spaceOfTheHall.setFeaturedImage(cloudStorageService.generatePresignedUrl(files.get(0).getUrl()));
            }
        }

        int total = spaceOfTheHallService.getCount(query);
        PageUtils pageUtils = new PageUtils(list, total, query.getLimit(), query.getPage());
        return R.ok().put("page", pageUtils);
    }


    /**
     * 跳转到新增页面
     **/
    @GetMapping("/add")
    @RequiresPermissions("spaceofthehall:save")
    public String add() {
        return "spaceofthehall/add";
    }

    /**
     * 保存
     *
     * @return
     */
    @ResponseBody
    @SysLog("保存空间大厅")
    @PostMapping("/save")
    @ApiOperation(value = "保存,暂定")
    @RequiresPermissions("spaceofthehall:save")
    public R save(@RequestBody @ApiParam(name = "params", value = "title:标题,url:跳转路径,featuredImage:图片") Map<String, Object> params) {
        Gson gson = new Gson();
        JsonElement jsonElement = gson.toJsonTree(params);
        SpaceOfTheHall spaceOfTheHall = gson.fromJson(jsonElement, SpaceOfTheHall.class);

        spaceOfTheHall.setStatus(0);//默认未启用
        if (spaceOfTheHall.getTitle().isEmpty()) {
            throw new MyException("标题不能为空!");
        }
        if (spaceOfTheHall.getUrl().isEmpty()) {
            throw new MyException("跳转路径不能为空!");
        }
        if (spaceOfTheHall.getFeaturedImage().isEmpty()) {
            throw new MyException("图片不能为空!");
        }
        List<File> fileList = fileService.getByRelationId(spaceOfTheHall.getFeaturedImage());
        if (fileList.size() < 1) {
            throw new MyException("图片不能为空！");
        }
        if (fileList.size() > 1) {
            throw new MyException("图片不能超过一张！");
        }

        spaceOfTheHallService.save(spaceOfTheHall);
        return R.ok();
    }

    /**
     * 删除
     *
     * @return
     */
    @ResponseBody
    @PostMapping("/delete")
    @SysLog("删除空间大厅")
    @ApiOperation(value = "删除,暂定")
    @RequiresPermissions("spaceofthehall:delete")
    public R delete(@RequestBody @ApiParam(name = "integers", value = "id数组") Integer[] integers) {
        for (Integer integer : integers) {
            SpaceOfTheHall spaceOfTheHall = spaceOfTheHallService.getById(integer);
            if (spaceOfTheHall != null) {
                spaceOfTheHallService.deleteById(spaceOfTheHall.getId());
                fileService.deleteByRelationId(spaceOfTheHall.getFeaturedImage());
            }
        }
        return R.ok();
    }


    /**
     * 跳转到修改页面
     **/
    @RequestMapping("/edit/{id}")
    @RequiresPermissions("spaceofthehall:update")
    public String edit(Model model, @PathVariable("id") Integer id) {
//        Activity activity = activityService.get(Integer.parseInt(id));
        SpaceOfTheHall byId = spaceOfTheHallService.getById(id);
//        activity = activityService.checkExpired(activity);
//        model.addAttribute("admin", ShiroUtils.getUserEntity());
        model.addAttribute("admin", ShiroUtils.getUserEntity());
//        model.addAttribute("BaiduApiKey", Constant.BaiduApiKey);
//        model.addAttribute("model", activity);
        model.addAttribute("model", byId);
        return "spaceofthehall/edit";
    }

    /**
     * 修改
     *
     * @return
     */
    @ResponseBody
    @PostMapping("/update")
    @SysLog("修改空间大厅")
    @ApiOperation(value = "修改,暂定")
    @RequiresPermissions("spaceofthehall:update")
    public R update(@RequestBody @ApiParam(name = "params", value = "id,title:标题,url:跳转路径,featuredImage:图片") Map<String, Object> params) {
        Gson gson = new Gson();
        JsonElement jsonElement = gson.toJsonTree(params);
        SpaceOfTheHall spaceOfTheHall = gson.fromJson(jsonElement, SpaceOfTheHall.class);
        if (spaceOfTheHall.getId() == null) {
            throw new MyException("id不能为空!");
        }
        if (spaceOfTheHall.getTitle().isEmpty()) {
            throw new MyException("标题不能为空!");
        }
        if (spaceOfTheHall.getUrl().isEmpty()) {
            throw new MyException("路径不能为空!");
        }
        if (spaceOfTheHall.getFeaturedImage().isEmpty()) {
            throw new MyException("图片不能为空!");
        }
        List<File> fileList = fileService.getByRelationId(spaceOfTheHall.getFeaturedImage());
        if (fileList.size() < 1) {
            throw new MyException("图片不能为空!");
        }
        if (fileList.size() > 1) {
            throw new MyException("图片不能超过一张!");
        }

        spaceOfTheHallService.update(spaceOfTheHall);
        return R.ok();
    }

    /**
     * 禁用
     */
    @ApiOperation("禁用空间大厅")
    @ResponseBody
    @SysLog("禁用空间大厅")
    @PostMapping("/forbid")
    @RequiresPermissions("spaceofthehall:update")
    public R forbid(@ApiParam(name = "ids") @RequestBody Integer[] ids) {
        spaceOfTheHallService.updateStatus(ids, 0);
        return R.ok();
    }

    /**
     * 启用
     */
    @ApiOperation("启用空间大厅")
    @ResponseBody
    @SysLog("启用空间大厅")
    @PostMapping("/initiate")
    @RequiresPermissions("spaceofthehall:update")
    public R initiate(@ApiParam(name = "ids") @RequestBody Integer[] ids) {
        spaceOfTheHallService.updateStatus(ids, 1);
        return R.ok();
    }
}
