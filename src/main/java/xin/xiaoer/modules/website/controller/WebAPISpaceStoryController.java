package xin.xiaoer.modules.website.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xin.xiaoer.common.oss.CloudStorageService;
import xin.xiaoer.common.oss.OSSFactory;
import xin.xiaoer.common.utils.FileUpload;
import xin.xiaoer.common.utils.PageUtils;
import xin.xiaoer.common.utils.Query;
import xin.xiaoer.common.utils.StringUtility;
import xin.xiaoer.entity.File;
import xin.xiaoer.modules.mobile.bean.ResponseBean;
import xin.xiaoer.modules.story.entity.SpaceStory;
import xin.xiaoer.modules.story.entity.SpaceStoryHtml;
import xin.xiaoer.modules.story.entity.SpaceStoryItem;
import xin.xiaoer.modules.story.service.SpaceStoryHtmlService;
import xin.xiaoer.modules.story.service.SpaceStoryItemService;
import xin.xiaoer.modules.story.service.SpaceStoryService;
import xin.xiaoer.modules.xebanner.entity.XeBanner;
import xin.xiaoer.modules.xebanner.service.XeBannerService;
import xin.xiaoer.service.FileService;
import xin.xiaoer.service.SysUserService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("website/spaceStory")
@Api("空间故事")
public class WebAPISpaceStoryController {
    @Autowired
    private XeBannerService xeBannerService;

    @Autowired
    private FileService fileService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SpaceStoryService spaceStoryService;

    @Autowired
    private SpaceStoryItemService spaceStoryItemService;

    @Autowired
    private SpaceStoryHtmlService spaceStoryHtmlService;

    @RequestMapping(value = "/slider", method = RequestMethod.GET)
    public ResponseBean banner() {
        Map params = new HashMap();
        params.put("bnTypeCode", "BNT002");
        params.put("state", "1");
        List<XeBanner> banners = xeBannerService.getList(params);
        CloudStorageService cloudStorageService = OSSFactory.build();
        if (banners != null) {
            for (XeBanner banner : banners) {
                List<File> files = fileService.getByRelationId(banner.getBnUrl());
                if (files.size() > 0) {
                    banner.setBnUrl(cloudStorageService.generatePresignedUrl(files.get(0).getUrl()));
                }
            }
        }
        return new ResponseBean(false, ResponseBean.SUCCESS, null, banners);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseBean add(@RequestParam Map<String, Object> params, HttpServletRequest request) throws IOException {

        String title = params.get("title").toString();
        String userId = params.get("userId").toString();
        String spaceId = params.get("spaceId").toString();

        SpaceStory spaceStory = new SpaceStory();
        spaceStory.setTitle(title);
        spaceStory.setState("0");
        spaceStory.setCreateBy(Long.parseLong(userId));
        spaceStory.setUpdateBy(Long.parseLong(userId));
        if (StringUtils.isNumeric(spaceId)) {
            spaceStory.setSpaceId(Integer.parseInt(spaceId));
        }
        spaceStoryService.save(spaceStory);

//        String storyHtmlsString = (String) params.get("htmls");

        if (params.get("items") != null) {
            JSONArray jsonArray = JSONArray.fromObject(params.get("items"));

            for (int i = 0; i < jsonArray.size(); i++) {
                SpaceStoryItem storyItem = new SpaceStoryItem();
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                if (jsonObject.get("itemImage") != null) {
                    String itemImage = UUID.randomUUID().toString();
                    String photoFile = jsonObject.getString("itemImage");
                    String url = FileUpload.fileUpLoadImageContent(photoFile, 320, 320);
                    File imageFile = new File();
                    imageFile.setUploadId(itemImage);
                    imageFile.setUrl(url);
                    imageFile.setOssYn("Y");
                    fileService.save(imageFile);
                    if (StringUtils.isNumeric(spaceId)) {
                        imageFile.setSpaceId(Integer.parseInt(spaceId));
                    }
                    storyItem.setItemImage(itemImage);
                }

                if (jsonObject.get("itemContent") != null) {
                    storyItem.setItemContent(jsonObject.getString("itemContent"));
                }

                if (!StringUtility.isEmpty(storyItem.getItemImage()) || !StringUtility.isEmpty(storyItem.getItemContent())) {
                    storyItem.setStoryId(spaceStory.getStoryId());
                    spaceStoryItemService.save(storyItem);
                }
            }

        }

        if (params.get("htmls") != null) {
            JSONArray jsonArray = JSONArray.fromObject(params.get("htmls"));

            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                SpaceStoryHtml spaceStoryHtml = new SpaceStoryHtml();
                if (jsonObject.get("htmlLink") == null) {
                    continue;
                }
                spaceStoryHtml.setHtmlLink(jsonObject.getString("htmlLink"));
                spaceStoryHtml.setStoryId(spaceStory.getStoryId());
                spaceStoryHtmlService.save(spaceStoryHtml);
            }
        }


        return new ResponseBean(false, "success", null, null);
    }


    /**
     * @return xin.xiaoer.modules.mobile.bean.ResponseBean
     * @Author dong
     * @Description //TODO 热门故事
     * @Param [spaceId]
     **/
    @ApiOperation("热门故事")
    @GetMapping("/hotStory")
    @ResponseBody
    public ResponseBean hotStory(Integer spaceId) {
        Map<String, Object> params = new HashMap<>();
        String date = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis() - 604800L * 1000);//时间推前一周
        params.put("date", date);
        if (spaceId != null) {
            params.put("spaceId", spaceId);
        }
        List<SpaceStory> hotStory = spaceStoryService.getHotStory(params);
        if (hotStory.size() < 10) {//如果内容不够十条
            params.remove("date");//移除周限制
            params.put("right", date);
            params.put("left", new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis() - 2592000L * 1000));//时间推前一月
            List<SpaceStory> addStory = spaceStoryService.getHotStory(params);
            hotStory.addAll(addStory);
        }
        return new ResponseBean(false, ResponseBean.SUCCESS, null, hotStory);
    }

    @RequestMapping(value = "/getStoryList")
    public ResponseBean listData(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        //查询列表数据
        String pageCount = params.get("limit").toString();
        String curPageNum = params.get("page").toString();
        params.put("limit", pageCount);
        params.put("page", curPageNum);
        Query query = new Query(params);
        List<Map> spaceStories = spaceStoryService.getstoryList(query);
        CloudStorageService cloudStorageService = OSSFactory.build();
        if (spaceStories != null) {
            for (Map donatemap : spaceStories) {
                if (donatemap.get("item_image") != null && donatemap.get("item_image").toString().length() < 37) {
                    List<File> files = fileService.getByRelationId(donatemap.get("item_image").toString());
                    if (files.size() > 0) {
                        donatemap.put("item_image", cloudStorageService.generatePresignedUrl(files.get(0).getUrl()));
                    } else {
                        donatemap.put("item_image", null);
                    }
                }
                if (donatemap.get("avatar") != null && donatemap.get("avatar").toString().length() < 37) {
                    List<File> avatarFile = fileService.getByRelationId(donatemap.get("avatar").toString());
                    if (avatarFile.size() > 0) {
                        donatemap.put("avatar", cloudStorageService.generatePresignedUrl(avatarFile.get(0).getUrl()));
                    } else {
                        donatemap.put("avatar", null);
                    }
                }
            }
        }
        int total = spaceStoryService.getstoryCount(query);
        PageUtils pageUtil = new PageUtils(spaceStories, total, query.getLimit(), query.getPage());

        return new ResponseBean(false, "success", null, pageUtil);
    }

    //故事详情页
    @RequestMapping(value = "/detail/{storyId}/{userId}", method = RequestMethod.GET)
    public ResponseBean deatil(@PathVariable("storyId") Long storyId, @PathVariable("userId") Long userId) throws Exception {

        SpaceStory spaceStory = spaceStoryService.getDetail(storyId, userId);
        if (spaceStory == null) {
            return new ResponseBean(false, ResponseBean.FAILED, "不存在该故事", null);
        }
        if (!spaceStory.getState().equals("1")) {
            return new ResponseBean(false, ResponseBean.FAILED, "空", null);
        }
        spaceStory.setReadCount(spaceStory.getReadCount() + 1);
        spaceStoryService.update(spaceStory);
        spaceStory.setAvatar(sysUserService.getAvatar(spaceStory.getAvatar()));
        Map<String, Object> result = new HashMap<>();
        result.put("story", spaceStory);

        Map<String, Object> search = new HashMap<>();
        search.put("storyId", spaceStory.getStoryId());

        List<SpaceStoryItem> storyItemList = spaceStoryItemService.getList(search);
        CloudStorageService cloudStorageService = OSSFactory.build();
        for (SpaceStoryItem spaceStoryItem : storyItemList) {
            if (!StringUtility.isEmpty(spaceStoryItem.getItemImage())) {
                spaceStoryItem.setItemImage(cloudStorageService.generatePresignedUrl(spaceStoryItem.getItemImage()));
            }
        }
        result.put("items", storyItemList);

        List<SpaceStoryHtml> spaceStoryHtmls = spaceStoryHtmlService.getList(search);
        result.put("htmls", spaceStoryHtmls);

        return new ResponseBean(false, "success", null, result);
    }


}
