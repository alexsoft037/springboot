package xin.xiaoer.modules.mobile.controller;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;
import org.springframework.web.bind.annotation.*;
import xin.xiaoer.common.enumresource.StateEnum;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("mobile/spaceStory")
@ApiIgnore
public class APISpaceStoryController {
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
    public ResponseBean banner(@RequestParam Map<String, Object> params) throws IOException {

        params.put("bnTypeCode", "BNT002");
        params.put("state", "1");
        List<XeBanner> xeBanners = xeBannerService.getList(params);

        CloudStorageService cloudStorageService = OSSFactory.build();
        for (XeBanner xeBanner: xeBanners){
            String articleType = xeBanner.getArticleType();

            if (xeBanner.getArticleId() != null) {
                if (articleType == null){

                } else if (articleType.equals("AT0005")) {
                    SpaceStory spaceStory = spaceStoryService.get(xeBanner.getArticleId());
                    if (spaceStory != null){
                        if (!spaceStory.getState().equals(StateEnum.ENABLE.getCode())){
                            xeBanners.remove(xeBanner);
                            continue;
                        }
                        xeBanner.setArticleName(spaceStory.getTitle());
                    }
                }
            }
            if (!StringUtility.isEmpty(xeBanner.getBnUrl())){
                List<File> files = fileService.getByRelationId(xeBanner.getBnUrl());
                if (files.size() > 0){
                    xeBanner.setBnUrl(cloudStorageService.generatePresignedUrl(files.get(0).getUrl()));
                } else {
                    xeBanner.setBnUrl("");
                }
            }
        }

        return new ResponseBean(false,"success", null, xeBanners);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseBean add(@RequestParam Map<String, Object> params, @RequestParam(value = "videoFiles", required = false) MultipartFile[] videoFiles) throws IOException {

        String title = params.get("title").toString();
        String userId = params.get("userId").toString();
        String spaceId = params.get("spaceId").toString();
        CloudStorageService cloudStorageService = OSSFactory.build();

        SpaceStory spaceStory = new SpaceStory();
        spaceStory.setTitle(title);
        spaceStory.setState("1");
        spaceStory.setCreateBy(Long.parseLong(userId));
        spaceStory.setUpdateBy(Long.parseLong(userId));
        if (StringUtils.isNumeric(spaceId)){
            spaceStory.setSpaceId(Integer.parseInt(spaceId));
        }
        spaceStoryService.save(spaceStory);

//        String storyHtmlsString = (String) params.get("htmls");

        // upload images
        if (params.get("items") != null){
            JSONArray jsonArray = JSONArray.fromObject(params.get("items"));

            for (int i = 0; i < jsonArray.size(); i++) {
                SpaceStoryItem storyItem = new SpaceStoryItem();
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                if (jsonObject.get("itemImage") != null){
                    String itemImage =  UUID.randomUUID().toString();
                    String photoFile = jsonObject.getString("itemImage");
                    String url = FileUpload.fileUpLoadImageContent(photoFile, 320, 320);
                    File imageFile = new File();
                    imageFile.setUploadId(itemImage);
                    imageFile.setUrl(url);
                    imageFile.setOssYn("Y");
                    fileService.save(imageFile);
                    if (StringUtils.isNumeric(spaceId)){
                        imageFile.setSpaceId(Integer.parseInt(spaceId));
                    }
                    storyItem.setItemImage(itemImage);
                }

                if (jsonObject.get("itemContent") != null){
                    storyItem.setItemContent(jsonObject.getString("itemContent"));
                }

                storyItem.setType("image");

                if (!StringUtility.isEmpty(storyItem.getItemImage()) || !StringUtility.isEmpty(storyItem.getItemContent())){
                    storyItem.setStoryId(spaceStory.getStoryId());
                    spaceStoryItemService.save(storyItem);
                }
            }

        }

        // upload videos
        if (videoFiles !=null) {
            JSONArray videoDescriptions = JSONArray.fromObject(params.get("videoDescriptions"));
            for (int i=0; i<videoFiles.length; i++) {

                JSONObject videoDescObj = videoDescriptions.getJSONObject(i);

                MultipartFile multipartFile = videoFiles[i];
                String originalFilename = multipartFile.getOriginalFilename();
                Long fileSize = multipartFile.getSize();
                String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));

                try {
                    SpaceStoryItem storyItem = new SpaceStoryItem();
                    String videoFileUrl = cloudStorageService.uploadSuffix(multipartFile.getInputStream(), suffix);
                    String videoId = UUID.randomUUID().toString();

                    if (videoFileUrl != null) {
                        File vFile = new File();
                        vFile.setUploadId(videoId);
                        vFile.setUrl(videoFileUrl);
                        vFile.setOssYn("Y");
                        vFile.setFileName(originalFilename);
                        vFile.setFileType("video");
                        vFile.setFileSize(fileSize.toString());
                        if (StringUtils.isNumeric(spaceId)){
                            vFile.setSpaceId(Integer.parseInt(spaceId));
                        }
                        fileService.save(vFile);

                        if (StringUtils.isNumeric(spaceId)){
                            vFile.setSpaceId(Integer.parseInt(spaceId));
                        }
                        storyItem.setItemImage(videoId);
                    }

                    if (videoDescObj.get("itemContent") != null){
                        storyItem.setItemContent(videoDescObj.getString("itemContent"));
                    }

                    storyItem.setType("video");
                    if (!StringUtility.isEmpty(storyItem.getItemImage()) || !StringUtility.isEmpty(storyItem.getItemContent())){
                        storyItem.setStoryId(spaceStory.getStoryId());
                        spaceStoryItemService.save(storyItem);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        // html page
        if (params.get("htmls") != null){
            JSONArray jsonArray = JSONArray.fromObject(params.get("htmls"));

            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                SpaceStoryHtml spaceStoryHtml = new SpaceStoryHtml();
                if (jsonObject.get("htmlLink") == null){
                    continue;
                }
                spaceStoryHtml.setHtmlLink(jsonObject.getString("htmlLink"));
                spaceStoryHtml.setStoryId(spaceStory.getStoryId());
                spaceStoryHtmlService.save(spaceStoryHtml);
            }
        }


        return new ResponseBean(false,"success", null, null);
    }

    @RequestMapping(value = "/listData",method = RequestMethod.POST)
    public ResponseBean listData(@RequestParam Map<String, Object> params, HttpServletRequest request) {

        String pageCount = params.get("pageCount").toString();
        String curPageNum = params.get("curPageNum").toString();
        String userId = params.get("userId").toString();
        String spaceId = params.get("spaceId").toString();
        if (StringUtility.isEmpty(spaceId)) {
            params.put("spaceId", 0);
        }

        params.put("limit", pageCount);
        params.put("page", curPageNum);
        params.put("state", "1");
        Query query = new Query(params);

        List<SpaceStory> spaceStories = spaceStoryService.getAPIList(query);
        for (SpaceStory spaceStory: spaceStories){
            if (!StringUtility.isEmpty(spaceStory.getAvatar())){
                spaceStory.setAvatar(sysUserService.getAvatar(spaceStory.getAvatar()));
            }
        }

        int total = spaceStoryService.getAPICount(query);

        PageUtils pageUtil = new PageUtils(spaceStories, total, query.getLimit(), query.getPage());

        return new ResponseBean(false, "success", null, pageUtil);
    }

    @RequestMapping(value = "/detail/{storyId}/{userId}", method = RequestMethod.GET)
    public ResponseBean deatil(@PathVariable("storyId") Long storyId, @PathVariable("userId") Long userId) throws Exception {

        SpaceStory spaceStory = spaceStoryService.getDetail(storyId, userId);
        if (!spaceStory.getState().equals("1")) {
            return new ResponseBean(false, ResponseBean.FAILED, "çİş", null);
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
        for (SpaceStoryItem spaceStoryItem: storyItemList){
            if (!StringUtility.isEmpty(spaceStoryItem.getItemImage())){
                spaceStoryItem.setItemImage(cloudStorageService.generatePresignedUrl(spaceStoryItem.getItemImage()));
            }
        }
        result.put("items", storyItemList);

        List<SpaceStoryHtml> spaceStoryHtmls = spaceStoryHtmlService.getList(search);
        result.put("htmls", spaceStoryHtmls);

        return new ResponseBean(false, "success", null, result);
    }
}
