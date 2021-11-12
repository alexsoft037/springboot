package xin.xiaoer.modules.story.controller;

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
import xin.xiaoer.common.shiro.ShiroUtils;
import xin.xiaoer.common.utils.PageUtils;
import xin.xiaoer.common.utils.Query;
import xin.xiaoer.common.utils.R;
import xin.xiaoer.common.utils.StringUtility;
import xin.xiaoer.entity.SysUser;
import xin.xiaoer.modules.story.entity.SpaceStory;
import xin.xiaoer.modules.story.entity.SpaceStoryItem;
import xin.xiaoer.modules.story.service.SpaceStoryHtmlService;
import xin.xiaoer.modules.story.service.SpaceStoryItemService;
import xin.xiaoer.modules.story.service.SpaceStoryService;
import xin.xiaoer.service.FileService;

import java.util.List;
import java.util.Map;


/**
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-07-27 13:23:11
 */
@Controller
@RequestMapping("spacestory")
public class SpaceStoryController {
    @Autowired
    private SpaceStoryService spaceStoryService;

    @Autowired
    private SpaceStoryHtmlService spaceStoryHtmlService;

    @Autowired
    private SpaceStoryItemService spaceStoryItemService;

    @Autowired
    private FileService fileService;

    /**
     * 列表数据
     */
    @ResponseBody
    @RequestMapping("/autocomplete")
    public R autocomplete(@RequestParam Map<String, Object> params) {

        String keywords = params.get("keywords").toString();
        params.put("titleVague", keywords);
        List<SpaceStory> spaceStories = spaceStoryService.getList(params);

        return R.ok().put("data", spaceStories);
    }

    /**
     * 跳转到列表页
     */
    @RequestMapping("/list")
    @RequiresPermissions("spacestory:list")
    public String list() {
        return "spacestory/list";
    }

    /**
     * 列表数据
     */
    @ResponseBody
    @RequestMapping("/listData")
    @RequiresPermissions("spacestory:list")
    public R listData(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);

        SysUser admin = ShiroUtils.getUserEntity();
        if (admin.getUserId() != 1L) {
            query.put("spaceId", admin.getSpaceId());
        }

        List<SpaceStory> spaceStoryList = spaceStoryService.getList(query);
        int total = spaceStoryService.getCount(query);

        PageUtils pageUtil = new PageUtils(spaceStoryList, total, query.getLimit(), query.getPage());

        return R.ok().put("page", pageUtil);
    }

    /**
     * 跳转到新增页面
     **/
    @RequestMapping("/add")
    @RequiresPermissions("spacestory:save")
    public String add(Model model) {
        model.addAttribute("admin", ShiroUtils.getUserEntity());
        return "spacestory/add";
    }

    /**
     * 跳转到修改页面
     **/
    @RequestMapping("/edit/{id}")
    @RequiresPermissions("spacestory:update")
    public String edit(Model model, @PathVariable("id") String id) {
        SpaceStory spaceStory = spaceStoryService.get(Long.parseLong(id));
        model.addAttribute("admin", ShiroUtils.getUserEntity());
        model.addAttribute("model", spaceStory);
        return "spacestory/edit";
    }

    /**
     * 信息
     */
    @ResponseBody
    @RequestMapping("/info/{storyId}")
    @RequiresPermissions("spacestory:info")
    public R info(@PathVariable("storyId") Long storyId) {
        SpaceStory spaceStory = spaceStoryService.get(storyId);
        return R.ok().put("spaceStory", spaceStory);
    }

    /**
     * 保存
     */
    @ResponseBody
    @SysLog("保存空间故事")
    @RequestMapping("/save")
    @RequiresPermissions("spacestory:save")
    public R save(@RequestBody Map<String, Object> params) {
        Gson gson = new Gson();
        JsonElement jsonElement = gson.toJsonTree(params);
        SpaceStory spaceStory = gson.fromJson(jsonElement, SpaceStory.class);

        if (ShiroUtils.getUserId() == 1L) {
            String spaceId = params.get("spaceId1").toString();
            if (StringUtility.isEmpty(spaceId) || !StringUtils.isNumeric(spaceId)) {
                throw new MyException("少儿空间不能为空！");
            }
            spaceStory.setSpaceId(Integer.parseInt(spaceId));
        } else {
            spaceStory.setSpaceId(ShiroUtils.getSpaceId());
        }

        spaceStory.setCreateBy(ShiroUtils.getUserId());
        spaceStory.setUpdateBy(ShiroUtils.getUserId());
        //黄小东
        if (spaceStory.getRank() == null || spaceStory.getRank() < 1 || spaceStory.getRank() > 5) {
            spaceStory.setRank(3);
        }
        spaceStoryService.save(spaceStory);

        return R.ok();
    }

    /**
     * 修改
     */
    @ResponseBody
    @SysLog("修改空间故事")
    @RequestMapping("/update")
    @RequiresPermissions("spacestory:update")
    public R update(@RequestBody Map<String, Object> params) {
        Gson gson = new Gson();
        JsonElement jsonElement = gson.toJsonTree(params);
        SpaceStory spaceStory = gson.fromJson(jsonElement, SpaceStory.class);

        if (ShiroUtils.getUserId() == 1L) {
            String spaceId = params.get("spaceId1").toString();
            if (StringUtility.isEmpty(spaceId) || !StringUtils.isNumeric(spaceId)) {
                throw new MyException("少儿空间不能为空！");
            }
            spaceStory.setSpaceId(Integer.parseInt(spaceId));
        } else {
            spaceStory.setSpaceId(ShiroUtils.getSpaceId());
        }

        spaceStory.setUpdateBy(ShiroUtils.getUserId());

        //黄小东
        if (spaceStory.getRank() == null || spaceStory.getRank() < 1 || spaceStory.getRank() > 5) {
            spaceStory.setRank(3);
        }
        spaceStoryService.update(spaceStory);

        return R.ok();
    }

    /**
     * 启用
     */
    @ResponseBody
    @SysLog("启用空间故事")
    @RequestMapping("/enable")
    @RequiresPermissions("spacestory:update")
    public R enable(@RequestBody String[] ids) {
        String stateValue = StateEnum.ENABLE.getCode();
        spaceStoryService.updateState(ids, stateValue);
        return R.ok();
    }

    /**
     * 禁用
     */
    @ResponseBody
    @SysLog("禁用空间故事")
    @RequestMapping("/limit")
    @RequiresPermissions("spacestory:update")
    public R limit(@RequestBody String[] ids) {
        String stateValue = StateEnum.LIMIT.getCode();
        spaceStoryService.updateState(ids, stateValue);
        return R.ok();
    }

    /**
     * 删除
     */
    @ResponseBody
    @SysLog("删除空间故事")
    @RequestMapping("/delete")
    @RequiresPermissions("spacestory:delete")
    public R delete(@RequestBody Long[] storyIds) {
        for (Long storyId : storyIds) {
            List<SpaceStoryItem> storyItemList = spaceStoryItemService.getListByStoryId(storyId);
            for (SpaceStoryItem spaceStoryItem : storyItemList) {
                fileService.deleteByRelationId(spaceStoryItem.getItemImage());
            }
        }
        spaceStoryItemService.deleteBatch(storyIds);
        spaceStoryHtmlService.deleteBatch(storyIds);
        spaceStoryService.deleteBatch(storyIds);

        return R.ok();
    }

}
