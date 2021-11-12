package xin.xiaoer.modules.story.controller;

import java.util.List;
import java.util.Map;

import io.swagger.models.auth.In;
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
import xin.xiaoer.common.utils.PageUtils;
import xin.xiaoer.common.utils.Query;
import xin.xiaoer.common.utils.R;
import xin.xiaoer.controller.AbstractController;
import xin.xiaoer.entity.File;
import xin.xiaoer.entity.SysUser;
import xin.xiaoer.modules.story.entity.SpaceStory;
import xin.xiaoer.modules.story.service.SpaceStoryService;
import xin.xiaoer.modules.xebanner.entity.XeBanner;
import xin.xiaoer.modules.xebanner.service.XeBannerService;
import xin.xiaoer.service.FileService;


/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-07-27 10:10:44
 */
@Controller
@RequestMapping("storySlider")
public class StorySliderController extends AbstractController {
    @Autowired
    private XeBannerService xeBannerService;

    @Autowired
    private SpaceStoryService spaceStoryService;

    @Autowired
    private FileService fileService;
    /**
     * 跳转到列表页
     */
    @RequestMapping("/list")
    @RequiresPermissions("xebanner:list")
    public String list() {
        return "storyslider/list";
    }

    /**
     * 列表数据
     */
    @ResponseBody
    @RequestMapping("/listData")
    @RequiresPermissions("xebanner:list")
    public R listData(@RequestParam Map<String, Object> params){
        //查询列表数据
        params.put("bnTypeCode", "BNT002");
        Query query = new Query(params);

        List<XeBanner> xeBannerList = xeBannerService.getList(query);
        CloudStorageService cloudStorageService = OSSFactory.build();
        for (XeBanner xeBanner: xeBannerList){
            List<File> files = fileService.getByRelationId(xeBanner.getBnUrl());
            if (files.size() > 0){
                xeBanner.setBnUrl(cloudStorageService.generatePresignedUrl(files.get(0).getUrl()));
            }
            if (xeBanner.getArticleId() != null){
                SpaceStory spaceStory = spaceStoryService.get(xeBanner.getArticleId());
                if (spaceStory != null){
                    xeBanner.setArticleName(spaceStory.getTitle());
                }
            }

        }
        int total = xeBannerService.getCount(query);

        PageUtils pageUtil = new PageUtils(xeBannerList, total, query.getLimit(), query.getPage());

        return R.ok().put("page", pageUtil);
    }

    /**
     * 跳转到新增页面
     **/
    @RequestMapping("/add")
    @RequiresPermissions("xebanner:save")
    public String add(){
        return "storyslider/add";
    }

    /**
     *   跳转到修改页面
     **/
    @RequestMapping("/edit/{id}")
    @RequiresPermissions("xebanner:update")
    public String edit(Model model, @PathVariable("id") String id){
        XeBanner xeBanner = xeBannerService.get(Integer.parseInt(id));
        if (xeBanner.getArticleId() != null){
            SpaceStory spaceStory = spaceStoryService.get(xeBanner.getArticleId());
            if (spaceStory != null){
                xeBanner.setArticleName(spaceStory.getTitle());
            }
        }
        model.addAttribute("model",xeBanner);
        return "storyslider/edit";
    }

    /**
     * 信息
     */
    @ResponseBody
    @RequestMapping("/info/{id}")
    @RequiresPermissions("xebanner:info")
    public R info(@PathVariable("id") Integer id){
        XeBanner xeBanner = xeBannerService.get(id);
        return R.ok().put("xeBanner", xeBanner);
    }

    /**
     * 保存
     */
    @ResponseBody
    @SysLog("保存空间故事轮播图")
    @RequestMapping("/save")
    @RequiresPermissions("xebanner:save")
    public R save(@RequestBody XeBanner xeBanner){

        List<File> fileList = fileService.getByRelationId(xeBanner.getBnUrl());
        if (fileList.size() < 1) {
            throw new MyException("图片不能为空！");
        }
        if (fileList.size() > 1) {
            throw new MyException("图片不能超过1！");
        }

        SysUser user = this.getUser();
        xeBanner.setBnTypeCode("BNT002");
        xeBanner.setCreateBy(user.getUserId());
        xeBanner.setUpdateBy(user.getUserId());
        xeBannerService.save(xeBanner);

        return R.ok();
    }

    /**
     * 修改
     */
    @ResponseBody
    @SysLog("修改空间故事轮播图")
    @RequestMapping("/update")
    @RequiresPermissions("xebanner:update")
    public R update(@RequestBody XeBanner xeBanner){
        List<File> fileList = fileService.getByRelationId(xeBanner.getBnUrl());
        if (fileList.size() < 1) {
            throw new MyException("图片不能为空！");
        }
        if (fileList.size() > 1) {
            throw new MyException("图片不能超过1！");
        }
        xeBanner.setBnTypeCode("BNT002");
        xeBanner.setUpdateBy(ShiroUtils.getUserId());
        xeBannerService.update(xeBanner);

        return R.ok();
    }

    /**
     * 启用
     */
    @ResponseBody
    @SysLog("启用空间故事轮播图")
    @RequestMapping("/enable")
    @RequiresPermissions("xebanner:update")
    public R enable(@RequestBody String[] ids){
        String stateValue=StateEnum.ENABLE.getCode();
        xeBannerService.updateState(ids,stateValue);
        return R.ok();
    }
    /**
     * 禁用
     */
    @ResponseBody
    @SysLog("禁用空间故事轮播图")
    @RequestMapping("/limit")
    @RequiresPermissions("xebanner:update")
    public R limit(@RequestBody String[] ids){
        String stateValue=StateEnum.LIMIT.getCode();
        xeBannerService.updateState(ids,stateValue);
        return R.ok();
    }

    /**
     * 删除
     */
    @ResponseBody
    @SysLog("删除空间故事轮播图")
    @RequestMapping("/delete")
    @RequiresPermissions("xebanner:delete")
    public R delete(@RequestBody Integer[] ids){
        for (Integer id: ids){
            XeBanner xeBanner = xeBannerService.get(id);
            if (xeBanner != null){
                fileService.deleteByRelationId(xeBanner.getBnUrl());
            }
        }
        xeBannerService.deleteBatch(ids);

        return R.ok();
    }
	
}
