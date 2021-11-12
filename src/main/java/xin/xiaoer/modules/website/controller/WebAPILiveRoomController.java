package xin.xiaoer.modules.website.controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
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
import xin.xiaoer.modules.like.entity.Like;
import xin.xiaoer.modules.like.service.LikeService;
import xin.xiaoer.modules.mobile.bean.ResponseBean;
import xin.xiaoer.modules.mobile.entity.LiveRoomListItem;
import xin.xiaoer.modules.setting.entity.Integral;
import xin.xiaoer.modules.setting.service.FollowService;
import xin.xiaoer.modules.setting.service.IntegralService;
import xin.xiaoer.modules.website.entity.LiveRoomUser;
import xin.xiaoer.modules.website.entity.LiveRoomUserListItem;
import xin.xiaoer.modules.website.service.LiveRoomUserService;
import xin.xiaoer.service.CategoryService;
import xin.xiaoer.service.FileService;
import xin.xiaoer.service.SysUserService;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.UUID;

@RestController
@RequestMapping("website/liveRoom")
public class WebAPILiveRoomController {

    @Value("${ilvb.iLiveAuthServer}")
    private String iLiveAuthServer;

    @Value("${ilvb.appid}")
    private String appid;

    //新加
    @Autowired
    private LiveRoomUserService liveRoomUserService;

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

    //新加
    @Autowired
    private LikeService likeService;

    //根据用户id获取直播间信息，有就查直播间，没就创
    @RequestMapping(value="/getUserSig/{userId}",method = RequestMethod.GET)
    public ResponseBean getUserSig(@PathVariable("userId") Long userId) throws Exception {

        String accountId = LiveRoomUtil.USER_PREF + userId;
        Account account = accountService.get(accountId);
        SysUser sysUser = sysUserService.queryObject(userId);

        JSONObject json = new JSONObject();
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

        JSONObject result = json.getJSONObject("data");
        result.put("uid", accountId);
        return new ResponseBean(false, "success", null, result);
    }

    //根据分类属性查下一级分类
    @RequestMapping(value="/typeCodes/{upperCode}",method = RequestMethod.GET)
    public ResponseBean typeCodes(@PathVariable String upperCode,HttpServletRequest request) {
        //查询列表数据
        Map<String, Object> params = new HashMap<>();
        params.put("upperCode", upperCode);
        params.put("state", "1");
        List<Category> categories = categoryService.getList(params);
        return new ResponseBean(false, "success", null, categories);
    }

    //存入创建好的直播间信息并完成对已关注者的信息推送
    @RequestMapping(value="/save",method = RequestMethod.POST)
    public ResponseBean save(HttpServletRequest request, @RequestParam(value = "featuredImage", required = false) MultipartFile featuredImage) throws Exception {
        //查询列表数据

        String typeCode = request.getParameter("typeCode");
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String userId = request.getParameter("userId");
        String spaceId = request.getParameter("spaceId");
        if(request.getParameter("spaceId")==null || StringUtils.isBlank(request.getParameter("spaceId").toString())){
            spaceId = "0";
        }

        LiveRoom liveRoom = new LiveRoom();

        liveRoom.setLiveTypeCode(typeCode);
        liveRoom.setTitle(title);
        liveRoom.setContent(content);
//        liveRoom.setRoomId(roomId);
        liveRoom.setCreateBy(Long.parseLong(userId));
        liveRoom.setSpaceId(Integer.parseInt(spaceId));

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

        liveRoom.setState("0");
        liveRoomService.save(liveRoom);
        liveRoom.setFeaturedImage("");

        return new ResponseBean(false, "success", null, liveRoom);
    }

    //分页直播间信息列表
    @RequestMapping(value="/listData/{categoryCode}",method = RequestMethod.POST)
    public ResponseBean listData(@RequestParam Map<String, Object> params, HttpServletRequest request) {

        String pageCount = params.get("pageCount").toString();
        String curPageNum = params.get("curPageNum").toString();
//        String userId = params.get("userId").toString();
        params.put("limit", pageCount);
        params.put("page", curPageNum);
        Query query = new Query(params);

        CloudStorageService cloudStorageService = OSSFactory.build();
        List<LiveRoomListItem> liveRoomListItems = liveRoomService.getAPIList(query);
        for (LiveRoomListItem liveRoomListItem : liveRoomListItems) {
            liveRoomListItem.setAvatar(sysUserService.getAvatar(liveRoomListItem.getAvatar()));
            if (StringUtility.isNotEmpty(liveRoomListItem.getFeaturedImage())) {
                liveRoomListItem.setFeaturedImage(cloudStorageService.generatePresignedUrlWithResize(liveRoomListItem.getFeaturedImage(), "400", "500"));
            }
        }

        int total = liveRoomService.getAPICount(query);

        PageUtils pageUtil = new PageUtils(liveRoomListItems, total, query.getLimit(), query.getPage());
        liveRoomListItems=(List<LiveRoomListItem>)pageUtil.getList();
        for (LiveRoomListItem liveRoomListItem : liveRoomListItems) {
            liveRoomListItem.setOnlineCount(liveRoomUserService.getCountById(liveRoomListItem.getLiveId().longValue()));
        }
        pageUtil.setList(liveRoomListItems);

        return new ResponseBean(false, "success", null, pageUtil);
    }

    //查直播间信息（基础信息）
    @RequestMapping(value="/detail/{liveId}/{userId}",method = RequestMethod.POST)
    public ResponseBean detail(@PathVariable("liveId") Integer liveId, @PathVariable("userId") Long userId,@RequestParam Map<String, Object> params, HttpServletRequest request) {
        Double liveDuration = Double.parseDouble(params.get("liveDuration").toString());

        LiveRoomUser liveRoomUser=liveRoomUserService.getDetail(liveId.longValue(),userId);
        if(liveRoomUser==null){
            liveRoomUser =new LiveRoomUser();
            liveRoomUser.setLiveId(liveId);
            liveRoomUser.setUserId(userId);
            liveRoomUser.setStatus(LiveRoomUser.ONLINE);
            liveRoomUser.setLiveRoleId(LiveRoomUser.NORMAL_USER);
            liveRoomUserService.save(liveRoomUser);
        }else {
            liveRoomUser.setStatus(LiveRoomUser.ONLINE);
            liveRoomUserService.update(liveRoomUser);
        }
        //需添加获取用户权限所对应的可做操作

        LiveRoom liveRoom = liveRoomService.getDetail(liveId, userId);
        if(liveRoom==null){
            return new ResponseBean(false, "false", "该直播间还未创建或已被销毁", null);
        }

        Like like = likeService.getByArticleAndUser(Article.LIVE_LESSON, liveId.longValue(), userId);
        if (like == null) {
            liveRoom.setLikeYn(false);
        } else {
            liveRoom.setLikeYn(true);
        }

        liveRoom.setLiveDuration(liveDuration);

        YunXinUtil yunXinUtil=new YunXinUtil();
        SysUser sysUser=sysUserService.queryObject(userId);
        try {
            yunXinUtil.sendSinglePush(liveRoomService.get(liveId).getCreateBy(),sysUser.getNickname()+"已进入直播间");
        } catch (Exception e) {
        }

        return new ResponseBean(false, "success", null, liveRoom);
    }

    //动态的请求like数和在线数
    @RequestMapping(value="/lazyLoad/{liveId}",method = RequestMethod.GET)
    public ResponseBean lazyLoad(@PathVariable("liveId") Integer liveId, HttpServletRequest request) {
        int likeCount = likeService.getCountByCodeAndId("AT0007",liveId.longValue());
        int onlineCount= liveRoomUserService.getCountById(liveId.longValue()) ;
        Map<String, Object> map = new HashMap<>();
        map.put("likeCount", likeCount);
        map.put("onlineCount", onlineCount);
        return new ResponseBean(false,"success",null,map);
    }

    //关闭时会根据直播时长，点赞数，弹幕数获取积分
    @RequestMapping(value="/getClose/{liveId}",method = RequestMethod.POST)
    public ResponseBean getClose(@PathVariable("liveId") String liveId, @RequestParam Map<String, Object> params) {

        String[] liveIds = {liveId};
        liveId=liveRoomService.updateState(liveIds, "0");
        if(StringUtil.isNullOrEmpty(liveId)) {


            LiveRoom liveRoom = liveRoomService.get(Integer.parseInt(liveId));
            Double liveDuration = 0.0;
            Integer likeCount = 0;
            Integer onlineCount = 0;

            if (params.get("liveDuration") != null) {
                liveDuration = Double.parseDouble(params.get("liveDuration").toString());
            }
            if (params.get("likeCount") != null) {
                likeCount = Integer.parseInt(params.get("likeCount").toString());
            }
            if (params.get("onlineCount") != null) {
                onlineCount = Integer.parseInt(params.get("onlineCount").toString());
            }
            int messageCount = liveRoomMessageService.getCountByLive(Long.parseLong(liveId));
            Double integralValue = liveDuration * (1 + messageCount * 0.01 + likeCount * 0.001 + onlineCount * 0.001);

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
                        yunXinUtil.sendSinglePush(integral.getUserId(), "获得积分" + integral.getTitle());
                    } catch (Exception e) {
                    }
                } else if (weekIntegralTotal < config.getWeekMaxLiveIntegral()) {
                    if (weekIntegralTotal + integralValue >= config.getWeekMaxLiveIntegral()) {
                        integralValue = config.getWeekMaxLiveIntegral() - weekIntegralTotal;
                    }
                    integral.setIntegral(integralValue.toString());
                    integralService.save(integral);
                    try {
                        yunXinUtil.sendSinglePush(integral.getUserId(), "获得积分" + integral.getTitle());
                    } catch (Exception e) {
                    }
                }
            }

            liveRoomMessageService.delete(Integer.parseInt(liveId));
            likeService.deleteByArticle("AT0007", Long.parseLong(liveId));

            return new ResponseBean(false, ResponseBean.SUCCESS, null, liveRoom);
        }else {
            return new ResponseBean(false, ResponseBean.FAILED, "找不到对应直播间", null);
        }
    }

    //获取热门直播间信息，只获取用户关注者的直播信息以时间倒叙
    @RequestMapping(value = "/featuredList/{categoryCode}",method = RequestMethod.POST)
    public ResponseBean featuredList(@PathVariable("categoryCode") String categoryCode,@RequestParam Map<String, Object> params, HttpServletRequest request) {

        CloudStorageService cloudStorageService = OSSFactory.build();

        if(params.get("limit")==null || StringUtils.isBlank(params.get("limit").toString())){
            params.put("limit","10");
        }
        if(params.get("spaceId")==null || StringUtils.isBlank(params.get("spaceId").toString())){
            params.put("spaceId","0");
        }
        params.put("categoryCode",categoryCode);
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

    //发送直播弹幕，直播信息表用于显示直播弹幕
    @RequestMapping(value="/sendMessage",method = RequestMethod.POST)
    public ResponseBean sendMessage(@RequestParam Map<String, Object> params, HttpServletRequest request) {

        String liveId = params.get("liveId").toString();
        String senderId = params.get("userId").toString();
        String message = params.get("message").toString();

        LiveRoomMessage liveRoomMessage = new LiveRoomMessage();
        liveRoomMessage.setLiveId(Integer.parseInt(liveId));
        liveRoomMessage.setSenderId(Long.parseLong(senderId));
        liveRoomMessage.setMessage(message);

        liveRoomMessageService.save(liveRoomMessage);

        return new ResponseBean(false, ResponseBean.SUCCESS, null, null);
    }

    //动态获取成员列表
    @RequestMapping(value="/userList",method = RequestMethod.POST)
    public ResponseBean userList(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        Integer liveId=Integer.parseInt(params.get("liveId").toString());
        Map<String, Object> map = new HashMap<>();
        map.put("liveId", liveId);
        List<LiveRoomUserListItem> liveRoomUserListItems= liveRoomUserService.getUserList(map);
        return new ResponseBean(false, ResponseBean.SUCCESS, null, liveRoomUserListItems);
    }

    //获取队列弹幕
    @RequestMapping(value="/pollingMessage",method = RequestMethod.POST)
    public ResponseBean pollingMessage(@RequestParam Map<String, Object> params, HttpServletRequest request) {

        List<LiveRoomMessage> messageList = liveRoomMessageService.getList(params);
        for (LiveRoomMessage liveRoomMessage: messageList){
            liveRoomMessage.setAvatar(sysUserService.getAvatar(liveRoomMessage.getAvatar()));
        }
        return new ResponseBean(false, ResponseBean.SUCCESS, null, messageList);
    }

    //暂停直播
    @RequestMapping(value = "/getPauseLive",method = RequestMethod.POST)
    public ResponseBean getPauseLive(@RequestParam Map<String, Object> params){
        String liveId = params.get("liveId").toString();
        LiveRoom liveRoom = liveRoomService.get(Integer.parseInt(liveId));
        if(liveRoom==null){
            return new ResponseBean(false, ResponseBean.FAILED, "找不到对应直播间", null);
        }
        liveRoom.setState("0");
        liveRoomService.update(liveRoom);

        return new ResponseBean(false, ResponseBean.SUCCESS, null, liveRoom);
    }

    //新加，开启直播
    @RequestMapping(value = "/getOpen/{userId}/{liveId}",method = RequestMethod.GET)
    public ResponseBean getOpen(@PathVariable("userId") Long userId,@PathVariable("liveId") Integer liveId){
        LiveRoom liveRoom = liveRoomService.get(liveId);
        if(liveRoom==null){
            return new ResponseBean(false, ResponseBean.FAILED, "找不到对应直播间", null);
        }
        liveRoom.setState("1");
        liveRoomService.update(liveRoom);

        Map<String, Object> followParams = new HashMap<>();
        followParams.put("followId", userId);
        followParams.put("limit", 450);
        int accountCount = followService.getCount(followParams);
        List<String> accountList;
        YunXinUtil yunXinUtil = new YunXinUtil();
        int totalPages = (int)Math.ceil((double)accountCount/450);
        SysUser sysUser = sysUserService.queryObject(userId);
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
                yunXinUtil.sendBatchPush(accountList, "您的关注的人" + nickName + "正在直播。");
            } catch (Exception e) {
            }
        }
        return new ResponseBean(false, ResponseBean.SUCCESS, null, liveRoom);
    }

    //注销直播间
    @RequestMapping(value = "/close/{liveId}",method = RequestMethod.GET)
    public ResponseBean close(@PathVariable("liveId") Integer liveId){
        if(liveRoomService.get(liveId)==null){
            return new ResponseBean(false, ResponseBean.FAILED, "找不到指定信息", null);
        }

        liveRoomService.delete(liveId);
        return new ResponseBean(false, ResponseBean.SUCCESS, null, null);
    }

    //用户退出直播间
    @RequestMapping(value = "/logout/{liveId}/{userId}",method = RequestMethod.GET)
    public ResponseBean logout(@PathVariable("userId") Long userId,@PathVariable("liveId") Integer liveId){
        if(liveRoomUserService.getDetail(liveId.longValue(),userId)==null){
            return new ResponseBean(false, ResponseBean.FAILED, "找不到指定信息", null);
        }

        liveRoomUserService.deleteById(userId,liveId.longValue());

        YunXinUtil yunXinUtil=new YunXinUtil();
        SysUser sysUser=sysUserService.queryObject(userId);
        try {
            yunXinUtil.sendSinglePush(liveRoomService.get(liveId).getCreateBy(),sysUser.getNickname()+"已退出直播间");
        } catch (Exception e) {
        }

        return new ResponseBean(false, ResponseBean.SUCCESS, null, null);
    }
}
