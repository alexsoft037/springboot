package xin.xiaoer.modules.website.controller;

import net.glxn.qrgen.QRCode;
import org.apache.commons.io.FileUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;
import xin.xiaoer.common.oss.CloudStorageService;
import xin.xiaoer.common.oss.OSSFactory;
import xin.xiaoer.common.utils.ConfigConstant;
import xin.xiaoer.common.utils.PageUtils;
import xin.xiaoer.common.utils.Query;
import xin.xiaoer.common.utils.YunXinUtil;
import xin.xiaoer.entity.Article;
import xin.xiaoer.entity.File;
import xin.xiaoer.entity.SysUser;
import xin.xiaoer.entity.UserLevel;
import xin.xiaoer.modules.activity.entity.Activity;
import xin.xiaoer.modules.activity.service.ActivityService;
import xin.xiaoer.modules.activityreport.entity.ActivityReport;
import xin.xiaoer.modules.activityreport.service.ActivityReportService;
import xin.xiaoer.modules.book.entity.Book;
import xin.xiaoer.modules.book.service.BookService;
import xin.xiaoer.modules.chat.entity.ChatUser;
import xin.xiaoer.modules.chat.service.ChatUserService;
import xin.xiaoer.modules.classroom.entity.LiveRoom;
import xin.xiaoer.modules.classroom.entity.SharingLesson;
import xin.xiaoer.modules.classroom.service.LiveRoomService;
import xin.xiaoer.modules.classroom.service.SharingLessonService;
import xin.xiaoer.modules.donatespace.entity.DonateSpace;
import xin.xiaoer.modules.donatespace.entity.DonateUser;
import xin.xiaoer.modules.donatespace.entity.DonateUserDetail;
import xin.xiaoer.modules.donatespace.service.DonateSpaceService;
import xin.xiaoer.modules.donatespace.service.DonateUserService;
import xin.xiaoer.modules.mobile.bean.ResponseBean;
import xin.xiaoer.modules.setting.entity.Integral;
import xin.xiaoer.modules.setting.entity.IntegralDetail;
import xin.xiaoer.modules.setting.service.IntegralService;
import xin.xiaoer.modules.spaceshow.entity.SpaceShow;
import xin.xiaoer.modules.spaceshow.service.SpaceShowService;
import xin.xiaoer.service.FileService;
import xin.xiaoer.service.SysConfigService;
import xin.xiaoer.service.SysUserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("website/user")
@ApiIgnore
public class WebAPIUserSettingController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private FileService fileService;

    @Autowired
    private DonateUserService donateUserService;

    @Autowired
    private IntegralService integralService;

    @Autowired
    private ChatUserService chatUserService;

    @Autowired
    private DonateSpaceService donateSpaceService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private ActivityReportService activityReportService;

    @Autowired
    private BookService bookService;

    @Autowired
    private SharingLessonService sharingLessonService;

    @Autowired
    private LiveRoomService liveRoomService;

    @Autowired
    private SpaceShowService spaceShowService;

    @Autowired
    private SysConfigService sysConfigService;


    @RequestMapping(value = "/getUserInfo/{userId}", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseBean getUserInfo(@PathVariable("userId") Long userId, HttpServletRequest request) throws IOException {

        SysUser sysUser = sysUserService.queryObject(userId);

        Map<String, Object> result = new HashMap<>();

        String addressTxt = sysUserService.getAddressTxtFromAddressIds(sysUser.getAddress());
        sysUser.setAddressTxt(addressTxt);
        sysUser.setPassword("");
        Double userIntegral = integralService.getUserIntegralTotal(userId);
        Integer userRanking = integralService.getUserRanking(userId);
        sysUser.setRanking(userRanking);
        sysUser.setIntegral(userIntegral);
        Integer userClassCode = sysUser.getUserClassCode();
        switch (userClassCode) {
            case 1:
                sysUser.setUserType("admin");
                break;
            case 2:
                sysUser.setUserType("volunteer");
                break;
            case 3:
                sysUser.setUserType("newsman");
                break;
            case 9:
                sysUser.setUserType("user");
                break;
            default:
                break;
        }
        result.put("member", sysUser);

        return new ResponseBean(false,"success", null, result);
    }

    @RequestMapping(value = "/getQRCode/{userId}", method = RequestMethod.GET)
    public ResponseBean getQRCode(@PathVariable("userId") Long userId, HttpServletResponse response) throws IOException {

//        SysUser sysUser = sysUserService.queryObject(userId);
//        ServletOutputStream outputStream = response.getOutputStream();
//        QRCode.from("xiaoer_" + userId).to(ImageType.PNG).writeTo(outputStream);
        ChatUser chatUser = chatUserService.get(userId);
        java.io.File file = QRCode.from(chatUser.getAccid()).withSize(512, 512).file();
        String encodedBase64 = null;
        try {
            byte[] fileContent = FileUtils.readFileToByteArray(file);
            encodedBase64 = Base64.getEncoder().encodeToString(fileContent);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseBean(false,"success", null, encodedBase64);
    }

    @RequestMapping(value = "/updateAvatar/{userId}", method = RequestMethod.POST)
    public ResponseBean onlineUserTotal(@PathVariable("userId") Long userId, @RequestParam("avatar") MultipartFile avatar) throws IOException {

        String avatarId = UUID.randomUUID().toString();

        String fileOriginalFilename = avatar.getOriginalFilename();
        Long fileSize = avatar.getSize();
        String suffix = fileOriginalFilename.substring(fileOriginalFilename.lastIndexOf("."));
        CloudStorageService cloudStorageService = OSSFactory.build();

        String avatarUrl = "";
        try {
            avatarUrl = cloudStorageService.uploadSuffix(avatar.getInputStream(), suffix);

            File imageFile = new File();
            imageFile.setUploadId(avatarId);
            imageFile.setUrl(avatarUrl);
            imageFile.setFileName(fileOriginalFilename);
            imageFile.setOssYn("Y");
            imageFile.setFileType("image");
            imageFile.setFileSize(fileSize.toString());
            fileService.save(imageFile);
        } catch (Exception e) {
            e.printStackTrace();
        }

        SysUser sysUser = sysUserService.queryObject(userId);
        sysUser.setAvatar(avatarId);

        sysUserService.update(sysUser);
        ChatUser chatUser = chatUserService.get(userId);
        YunXinUtil yunXinUtil = new YunXinUtil();
        try {
            yunXinUtil.updateUinfo(sysUser, chatUser);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Integer userClassCode = sysUser.getUserClassCode();
        switch (userClassCode) {
            case 1:
                sysUser.setUserType("admin");
                break;
            case 2:
                sysUser.setUserType("volunteer");
                break;
            case 3:
                sysUser.setUserType("newsman");
                break;
            case 9:
                sysUser.setUserType("user");
                break;
            default:
                break;
        }

        Map<String, Object> result = new HashMap<>();
        result.put("member", sysUser);
        return new ResponseBean(false,"success", null, result);
    }

    @RequestMapping(value = "/updateUserInfo/{userId}", method = RequestMethod.POST)
    public ResponseBean updateUserInfo(@PathVariable("userId") Long userId, @RequestParam Map<String, Object> params) throws IOException {

        SysUser sysUser = sysUserService.queryObject(userId);

        if (params.get("nickname") != null){
            String nickname = params.get("nickname").toString();
            sysUser.setNickname(nickname);
        }
        if (params.get("address") != null){
            String address = params.get("address").toString();
            sysUser.setAddress(address);
        }
        if (params.get("gender") != null){
            String gender = params.get("gender").toString();
            sysUser.setGender(gender);
        }
        if (params.get("personality") != null){
            String personality = params.get("personality").toString();
            sysUser.setPersonality(personality);
        }
        sysUserService.update(sysUser);
        ChatUser chatUser = chatUserService.get(userId);
        YunXinUtil yunXinUtil = new YunXinUtil();
        try {
            yunXinUtil.updateUinfo(sysUser, chatUser);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Integer userClassCode = sysUser.getUserClassCode();
        switch (userClassCode) {
            case 1:
                sysUser.setUserType("admin");
                break;
            case 2:
                sysUser.setUserType("volunteer");
                break;
            case 3:
                sysUser.setUserType("newsman");
                break;
            case 9:
                sysUser.setUserType("user");
                break;
            default:
                break;
        }

        Map<String, Object> result = new HashMap<>();
        sysUser.setPassword("");
        result.put("member", sysUser);

        return new ResponseBean(false,"success", null, result);
    }

    @RequestMapping(value = "/updatePassword/{userId}", method = RequestMethod.POST)
    public ResponseBean updatePassword(@PathVariable("userId") Long userId, HttpServletRequest request) throws IOException {

        String oldPassword = request.getParameter("oldPassword");
        String newPassword = request.getParameter("newPassword");

        oldPassword = new Sha256Hash(oldPassword).toHex();
        newPassword = new Sha256Hash(newPassword).toHex();

        SysUser sysUser = sysUserService.queryObject(userId);
        if(!oldPassword.equals(sysUser.getPassword())) {
            return new ResponseBean(false, ResponseBean.FAILED, "password_incorrect", null);
        }

        sysUserService.updatePassword(userId, oldPassword, newPassword);

        return new ResponseBean(false,"success", null, "");
    }

    @RequestMapping(value = "/donates", method = RequestMethod.POST)
    public ResponseBean donates(@RequestParam Map<String, Object> params, HttpServletRequest request) throws IOException {

        String pageCount = params.get("pageCount").toString();
        String curPageNum = params.get("curPageNum").toString();
        params.put("limit", pageCount);
        params.put("page", curPageNum);

        Query query = new Query(params);
        List<DonateUser> donateUsers = donateUserService.getList(query);

        int total = donateUserService.getCount(query);

        PageUtils pageUtil = new PageUtils(donateUsers, total, query.getLimit(), query.getPage());

        return new ResponseBean(false, ResponseBean.SUCCESS, null, pageUtil);
    }

    @RequestMapping(value = "/donateDetail/{id}", method = RequestMethod.GET)
    public ResponseBean donateDetail(@PathVariable("id") Long id, HttpServletRequest request) throws IOException {

        DonateUserDetail detail = donateUserService.getDetail(id);
        CloudStorageService cloudStorageService = OSSFactory.build();
        detail.setFeaturedImage(cloudStorageService.generatePresignedUrl(detail.getFeaturedImage()));
        return new ResponseBean(false, ResponseBean.SUCCESS, null, detail);
    }

    @RequestMapping(value = "/integrals", method = RequestMethod.POST)
    public ResponseBean integrals(@RequestParam Map<String, Object> params, HttpServletRequest request) throws IOException {

        String pageCount = params.get("pageCount").toString();
        String curPageNum = params.get("curPageNum").toString();
        params.put("limit", pageCount);
        params.put("page", curPageNum);

        Query query = new Query(params);
        List<Integral> integrals = integralService.getIntegralListByUser(query);
        int total = integralService.getIntegralCountByUser(query);

        PageUtils pageUtil = new PageUtils(integrals, total, query.getLimit(), query.getPage());

        return new ResponseBean(false, ResponseBean.SUCCESS, null, pageUtil);
    }

    @RequestMapping(value = "/integralDetail/{integralId}", method = RequestMethod.GET)
    public ResponseBean integralDetail(@PathVariable("integralId") Long integralId, HttpServletRequest request) throws IOException {

        IntegralDetail integral = integralService.getDetail(integralId);
        CloudStorageService cloudStorageService = OSSFactory.build();

        if (integral.getArticleTypeCode().equals(Article.DONATE_SPACE)){
            DonateSpace donateSpace = donateSpaceService.getDetail(integral.getArticleId());
            integral.setArticleTitle(donateSpace.getTitle());
            integral.setArticleContent(donateSpace.getDescription());
            integral.setFeaturedImage(cloudStorageService.generatePresignedUrlByWith(donateSpace.getSpaceImage(), "100"));
        } else if (integral.getArticleTypeCode().equals(Article.ACTIVITY)){
            Activity activity= activityService.getDetail(integral.getArticleId().intValue());
            integral.setArticleTitle(activity.getTitle());
            integral.setArticleContent(activity.getContent());
            integral.setFeaturedImage(cloudStorageService.generatePresignedUrlByWith(activity.getFeaturedImage(), "100"));
        } else if (integral.getArticleTypeCode().equals(Article.ACTIVITY_REPORT)){
            ActivityReport activityReport = activityReportService.getDetail(integral.getArticleId().intValue());
            integral.setArticleTitle(activityReport.getTitle());
            integral.setArticleContent(activityReport.getContent());
            integral.setFeaturedImage(cloudStorageService.generatePresignedUrlByWith(activityReport.getFeaturedImage(), "100"));
        } else if (integral.getArticleTypeCode().equals(Article.BOOK)){
            Book book = bookService.getDetail(integral.getArticleId());
            integral.setArticleTitle(book.getSubject());
            integral.setArticleContent(book.getIntroduction());
            integral.setFeaturedImage(cloudStorageService.generatePresignedUrlByWith(book.getCover(), "100"));
        } else if (integral.getArticleTypeCode().equals(Article.SHARE_LESSON)){
            SharingLesson sharingLesson = sharingLessonService.getDetail(integral.getArticleId().intValue());
            integral.setArticleTitle(sharingLesson.getTitle());
            integral.setArticleContent(sharingLesson.getIntroduction());
            integral.setFeaturedImage(cloudStorageService.generatePresignedUrlByWith(sharingLesson.getFeaturedImage(), "100"));
        } else if (integral.getArticleTypeCode().equals(Article.LIVE_LESSON)){
            LiveRoom liveRoom = liveRoomService.getDetail(integral.getArticleId().intValue(), 1L);
            integral.setArticleTitle(liveRoom.getTitle());
            integral.setArticleContent(liveRoom.getContent());
            integral.setFeaturedImage(cloudStorageService.generatePresignedUrlByWith(liveRoom.getFeaturedImage(), "100"));
        } else if (integral.getArticleTypeCode().equals(Article.SPACE_SHOW)){
            SpaceShow spaceShow = spaceShowService.get(integral.getArticleId());
            integral.setArticleTitle(spaceShow.getTitle());
            integral.setArticleContent(spaceShow.getContent());
            List<File> files = fileService.getByRelationId(spaceShow.getImage());
            if (files != null){
                if (files.size() > 0){
                    integral.setFeaturedImage(cloudStorageService.generatePresignedUrlByWith(files.get(0).getUrl(), "100"));
                }
            }
        }

        return new ResponseBean(false, ResponseBean.SUCCESS, null, integral);
    }

    @RequestMapping(value = "/totalIntegral/{userId}", method = RequestMethod.GET)
    public ResponseBean totalIntegrals(@PathVariable("userId") Long userId, HttpServletRequest request) throws IOException {

        Double totalIntegral = integralService.getUserIntegralTotal(userId);

        Map<String, Object> result = new HashMap<>();
        result.put("total", totalIntegral);

        return new ResponseBean(false,"success", null, result);
    }

//    @RequestMapping(value = "/moveAvatar")
//    public ResponseBean moveAvatar(HttpServletRequest request) {
//
//        List<SysUser> userList = sysUserService.queryList(null);
//        CloudStorageService cloudStorageService = OSSFactory.build();
//        UrlValidator urlValidator = new UrlValidator();
//        for (SysUser sysUser: userList) {
//            String imageId = UUID.randomUUID().toString();
//            if (StringUtility.isNotEmpty(sysUser.getAvatar()) && urlValidator.isValid(sysUser.getAvatar())) {
//                try {
//                    URL url = new URL(sysUser.getAvatar());
//                    URLConnection conn = url.openConnection();
//
//                    String imageUrl = cloudStorageService.uploadSuffix(conn.getInputStream(), ".jpg");
//                    File imageFile = new File();
//                    imageFile.setUploadId(imageId);
//                    imageFile.setUrl(imageUrl);
//                    imageFile.setFileName(sysUser.getNickname() + "_avatar.jpg");
//                    imageFile.setOssYn("Y");
//                    imageFile.setFileType("image");
//                    imageFile.setFileSize(Integer.toString(conn.getContentLength()));
//
//                    fileService.save(imageFile);
//                    sysUser.setAvatar(imageId);
//                    sysUserService.updateData(sysUser);
//                } catch (Exception e){
//
//                }
//            }
//
//        }
//
//        return new ResponseBean(false,"success", null, null);
//    }
//

    @RequestMapping(value = "/levelConfig", method = RequestMethod.GET)
    public ResponseBean levelConfig(HttpServletRequest request) {

        UserLevel userLevel = sysConfigService.getConfigClassObject(ConfigConstant.USER_LEVEL_CONFIG_KEY, UserLevel.class);

        return new ResponseBean(false,"success", null, userLevel);
    }
}
