package xin.xiaoer.modules.mobile.controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;
import xin.xiaoer.common.integralConfig.IntegralConfig;
import xin.xiaoer.common.integralConfig.IntegralConfigFactory;
import xin.xiaoer.common.oss.CloudStorageService;
import xin.xiaoer.common.oss.OSSFactory;
import xin.xiaoer.common.utils.*;
import xin.xiaoer.entity.*;
import xin.xiaoer.modules.classroom.entity.Account;
import xin.xiaoer.modules.classroom.entity.LiveRoom;
import xin.xiaoer.modules.classroom.entity.LiveRoomMessage;
import xin.xiaoer.modules.classroom.service.AccountService;
import xin.xiaoer.modules.classroom.service.LiveRoomMessageService;
import xin.xiaoer.modules.classroom.service.LiveRoomService;
import xin.xiaoer.modules.mobile.bean.ResponseBean;
import xin.xiaoer.modules.mobile.entity.LiveRoomListItem;
import xin.xiaoer.modules.setting.entity.Integral;
import xin.xiaoer.modules.setting.service.FollowService;
import xin.xiaoer.modules.setting.service.IntegralService;
import xin.xiaoer.service.CategoryService;
import xin.xiaoer.service.FileService;
import xin.xiaoer.service.SysUserService;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;
import java.util.*;

@RestController
@RequestMapping("mobile/liveRoom")
@ApiIgnore
public class APILiveRoomController {

    @Value("${ilvb.iLiveAuthServer}")
    private String iLiveAuthServer;

    @Value("${ilvb.appid}")
    private String appid;

    @Autowired
    private AccountService accountService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private FileService fileService;

    @Autowired
    private LiveRoomService liveRoomService;

    @Autowired
    private LiveRoomMessageService liveRoomMessageService;

    @Autowired
    private FollowService followService;

    @Autowired
    private IntegralService integralService;

    @RequestMapping(value = "/getUserSig/{userId}", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseBean getUserSig(@PathVariable("userId") Long userId) throws Exception {

        String accountId = LiveRoomUtil.USER_PREF + userId;
        Account account = accountService.get(accountId);
        SysUser sysUser = sysUserService.queryObject(userId);

        com.alibaba.fastjson.JSONObject json = new JSONObject();
        LiveRoomUtil liveRoomUtil = new LiveRoomUtil(iLiveAuthServer, appid);
        try {
            if (account == null) {
                liveRoomUtil.createUser(sysUser);
            }
            json = liveRoomUtil.getUserSig(sysUser);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseBean(false, ResponseBean.FAILED, null, e.getMessage());
        }

        com.alibaba.fastjson.JSONObject result = json.getJSONObject("data");
        result.put("uid", accountId);
        return new ResponseBean(false, "success", null, result);
    }

    @RequestMapping(value = "/typeCodes", method = {RequestMethod.POST,RequestMethod.GET})
    public ResponseBean typeCodes(HttpServletRequest request) {
        //查询列表数据
        Map<String, Object> params = new HashMap<>();

        params.put("upperCode", "CRT");
        params.put("state", "1");
        List<Category> categories = categoryService.getList(params);
        List<CodeValue> codeValues = new ArrayList<>();
        for (Category category : categories) {
            List<CodeValue> subCategories = categoryService.getCodeValues(category.getCategoryCode(), "1");
            codeValues.addAll(subCategories);
        }
        return new ResponseBean(false, "success", null, codeValues);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ResponseBean save(HttpServletRequest request, @RequestParam(value = "featuredImage", required = false) MultipartFile featuredImage) throws Exception {
        //查询列表数据

        String typeCode = request.getParameter("typeCode");
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String userId = request.getParameter("userId");

        LiveRoom liveRoom = new LiveRoom();

        liveRoom.setLiveTypeCode(typeCode);
        liveRoom.setTitle(title);
        liveRoom.setContent(content);
//        liveRoom.setRoomId(roomId);
        liveRoom.setCreateBy(Long.parseLong(userId));

        if (featuredImage != null) {
            String featuredImageId = UUID.randomUUID().toString();

            String fileOriginalFilename = featuredImage.getOriginalFilename();
            Long fileSize = featuredImage.getSize();
            String suffix = fileOriginalFilename.substring(fileOriginalFilename.lastIndexOf("."));
            CloudStorageService cloudStorageService = OSSFactory.build();

            try {
                String featuredImageUrl = cloudStorageService.uploadSuffix(featuredImage.getInputStream(), suffix);
                File imageFile = new File();
                imageFile.setUploadId(featuredImageId);
                imageFile.setUrl(featuredImageUrl);
                imageFile.setFileName(fileOriginalFilename);
                imageFile.setFileType("image");
                imageFile.setOssYn("Y");
                imageFile.setFileSize(fileSize.toString());
                fileService.save(imageFile);

                liveRoom.setFeaturedImage(featuredImageId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        liveRoom.setState("1");
        liveRoomService.save(liveRoom);
        liveRoom.setFeaturedImage("");
        Map<String, Object> followParams = new HashMap<>();
        followParams.put("followId", userId);
        followParams.put("limit", 450);
        int accountCount = followService.getCount(followParams);
        List<String> accountList;
        YunXinUtil yunXinUtil = new YunXinUtil();
        int totalPages = (int)Math.ceil((double)accountCount/450);
        SysUser sysUser = sysUserService.queryObject(Long.parseLong(userId));
        for (int i = 1; i <= totalPages; i++) {
            followParams.put("page", i);
            Query query = new Query(followParams);
            accountList = followService.getAccountListByFollowId(query);
            try {
                String nickName;
                if (StringUtility.isEmpty(sysUser.getNickname())){
                    nickName = sysUser.getPhoneNo();
                } else {
                    nickName = sysUser.getNickname();
                }
                yunXinUtil.sendBatchPush(accountList, "您的关注的人" + nickName + "正在发布直播频道。");
            } catch (Exception e) {
            }
        }
        return new ResponseBean(false, "success", null, liveRoom);
    }

    @RequestMapping(value = "/listData", method = RequestMethod.POST)
    public ResponseBean listData(@RequestParam Map<String, Object> params, HttpServletRequest request) {

        String pageCount = params.get("pageCount").toString();
        String curPageNum = params.get("curPageNum").toString();
        String userId = params.get("userId").toString();
        params.put("limit", pageCount);
        params.put("page", curPageNum);
        Query query = new Query(params);

        CloudStorageService cloudStorageService = OSSFactory.build();
        List<LiveRoomListItem> liveRoomListItems = liveRoomService.getAPIList(query);
        UrlValidator urlValidator = new UrlValidator();
        for (LiveRoomListItem liveRoomListItem : liveRoomListItems) {
            liveRoomListItem.setAvatar(sysUserService.getAvatar(liveRoomListItem.getAvatar()));
            if (StringUtility.isNotEmpty(liveRoomListItem.getFeaturedImage())) {
                liveRoomListItem.setFeaturedImage(cloudStorageService.generatePresignedUrlWithResize(liveRoomListItem.getFeaturedImage(), "400", "500"));
            }
        }

        int total = liveRoomService.getAPICount(query);

        PageUtils pageUtil = new PageUtils(liveRoomListItems, total, query.getLimit(), query.getPage());

        return new ResponseBean(false, "success", null, pageUtil);
    }

    @RequestMapping(value = "/detail/{liveId}/{userId}",method = RequestMethod.GET)
    public ResponseBean detail(@PathVariable("liveId") Integer liveId, @PathVariable("userId") Long userId, HttpServletRequest request) {

        LiveRoom liveRoom = liveRoomService.getDetail(liveId, userId);

        return new ResponseBean(false, "success", null, liveRoom);
    }

    @RequestMapping(value = "/close/{liveId}/{state}", method = RequestMethod.POST)
    public ResponseBean close(@PathVariable("liveId") String liveId, @PathVariable("state") String state, @RequestParam Map<String, Object> params) {

        String[] liveIds = {liveId};
        liveRoomService.updateState(liveIds, state);
        LiveRoom liveRoom = liveRoomService.get(Integer.parseInt(liveId));
        Double liveDuration = 0.0;
        Integer likeCount = 0;
        Integer onlineCount = 0;
        if (state.equals("0")) {
            if (params.get("liveDuration") != null){
                liveDuration = Double.parseDouble(params.get("liveDuration").toString());
            }
            if (params.get("likeCount") != null){
                likeCount = Integer.parseInt(params.get("likeCount").toString());
            }
            if (params.get("onlineCount") != null){
                onlineCount = Integer.parseInt(params.get("onlineCount").toString());
            }
            int messageCount = liveRoomMessageService.getCountByLive(Long.parseLong(liveId));
            Double integralValue = liveDuration * ( 1 + messageCount * 0.01 + likeCount * 0.001 + onlineCount * 0.001);
            if (integralValue > 0) {
                Map<String, Object> userParam = new HashMap<>();
                userParam.put("articleTypeCode", Article.LIVE_LESSON);
                userParam.put("userId", liveRoom.getCreateBy());
                Double weekIntegralTotal = integralService.getThisWeekTotal(userParam);
                IntegralConfig config = IntegralConfigFactory.build();

                Integral integral = new Integral();
                integral.setArticleTypeCode(Article.LIVE_LESSON);
                integral.setUserId(liveRoom.getCreateBy());
                integral.setArticleId(liveRoom.getLiveId().longValue());
                integral.setTitle("直播" + liveRoom.getTitle());

                YunXinUtil yunXinUtil = new YunXinUtil();
                if (weekIntegralTotal == null) {
                    integral.setIntegral(integralValue.toString());
                    integralService.save(integral);
                    try {
                        yunXinUtil.sendSinglePush(integral.getUserId(), "获得积分"+integral.getTitle());
                    } catch (Exception e) {
                    }
                } else if (weekIntegralTotal < config.getWeekMaxLiveIntegral()) {
                    if (weekIntegralTotal + integralValue >= config.getWeekMaxLiveIntegral()){
                        integralValue = config.getWeekMaxLiveIntegral() - weekIntegralTotal;
                    }
                    integral.setIntegral(integralValue.toString());
                    integralService.save(integral);
                    try {
                        yunXinUtil.sendSinglePush(integral.getUserId(), "获得积分"+integral.getTitle());
                    } catch (Exception e) {
                    }
                }
            }
        }

        return new ResponseBean(false, ResponseBean.SUCCESS, null, null);
    }

    @RequestMapping(value = "/featuredList/{userId}/{spaceId}", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseBean featuredList(@PathVariable("userId") Long userId, @PathVariable("spaceId") String spaceId, HttpServletRequest request) {

        CloudStorageService cloudStorageService = OSSFactory.build();
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        List<LiveRoomListItem> liveRoomListItems = liveRoomService.getFeaturedList(params);
        UrlValidator urlValidator = new UrlValidator();
        for (LiveRoomListItem liveRoomListItem : liveRoomListItems) {
            liveRoomListItem.setAvatar(sysUserService.getAvatar(liveRoomListItem.getAvatar()));
            if (StringUtility.isNotEmpty(liveRoomListItem.getFeaturedImage())) {
                liveRoomListItem.setFeaturedImage(cloudStorageService.generatePresignedUrlWithResize(liveRoomListItem.getFeaturedImage(), "400", "500"));
            }
        }

        return new ResponseBean(false, "success", null, liveRoomListItems);
    }

    @RequestMapping(value = "/sendMessage", method = RequestMethod.POST)
    public ResponseBean sendMessage(@RequestParam Map<String, Object> params, HttpServletRequest request) {

        String liveId = params.get("liveId").toString();
        String senderId = params.get("senderId").toString();
        String message = params.get("message").toString();

        LiveRoomMessage liveRoomMessage = new LiveRoomMessage();
        liveRoomMessage.setLiveId(Integer.parseInt(liveId));
        liveRoomMessage.setSenderId(Long.parseLong(senderId));
        liveRoomMessage.setMessage(message);

        liveRoomMessageService.save(liveRoomMessage);

        return new ResponseBean(false, ResponseBean.SUCCESS, null, null);
    }

    @RequestMapping(value = "/pollingMessage", method = RequestMethod.POST)
    public ResponseBean pollingMessage(@RequestParam Map<String, Object> params, HttpServletRequest request) {

        List<LiveRoomMessage> messageList = liveRoomMessageService.getList(params);
        for (LiveRoomMessage liveRoomMessage: messageList){
            liveRoomMessage.setAvatar(sysUserService.getAvatar(liveRoomMessage.getAvatar()));
        }
        return new ResponseBean(false, ResponseBean.SUCCESS, null, messageList);
    }

    @RequestMapping(value = "/getPauseLive/{userId}", method = RequestMethod.GET)
    public ResponseBean getPauseLive(@PathVariable("userId") Long userId){

        LiveRoom liveRoom = liveRoomService.getUserPauseLive(userId);

        if (liveRoom != null) {
            liveRoomService.delete(liveRoom.getLiveId());
            liveRoom.setLiveId(null);
            liveRoomService.save(liveRoom);
            liveRoom = liveRoomService.get(liveRoom.getLiveId());
        }

        return new ResponseBean(false, ResponseBean.SUCCESS, null, liveRoom);
    }
}
