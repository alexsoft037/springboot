package xin.xiaoer.modules.website.controller;

import com.alibaba.fastjson.JSON;
import com.google.code.kaptcha.Producer;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.HtmlUtils;
import xin.xiaoer.common.api.annotation.IgnoreAuth;
import xin.xiaoer.common.enumresource.SexEnum;
import xin.xiaoer.common.integralConfig.IntegralConfig;
import xin.xiaoer.common.integralConfig.IntegralConfigFactory;
import xin.xiaoer.common.oss.CloudStorageService;
import xin.xiaoer.common.oss.OSSFactory;
import xin.xiaoer.common.utils.*;
import xin.xiaoer.entity.*;
import xin.xiaoer.modules.chat.entity.ChatUser;
import xin.xiaoer.modules.chat.service.ChatUserService;
import xin.xiaoer.modules.guoshan.entity.GuoshanUser;
import xin.xiaoer.modules.guoshan.service.GuoshanUserService;
import xin.xiaoer.modules.mobile.bean.ResponseBean;
import xin.xiaoer.modules.setting.entity.Integral;
import xin.xiaoer.modules.setting.service.IntegralService;
import xin.xiaoer.modules.space.service.SpaceService;
import xin.xiaoer.modules.sysusersns.entity.SysUserSns;
import xin.xiaoer.modules.sysusersns.service.SysUserSnsService;
import xin.xiaoer.modules.verifycode.entity.XeVerifyCode;
import xin.xiaoer.modules.verifycode.service.XeVerifyCodeService;
import xin.xiaoer.modules.website.service.QQAuthService;
import xin.xiaoer.modules.website.service.WeiBoAuthService;
import xin.xiaoer.modules.website.service.WeiXinAuthService;
import xin.xiaoer.service.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.UUID;

/**
 * 登录相关
 *
 * @author chenyi
 * @email 228112142@qq.com
 * @date 2016年11月10日 下午1:15:31
 */
@Controller
public class WebAPILoginController {
    @Value("${guoshan.siteUrl}")
    private String guoshanUrl;

    @Autowired
    private Producer producer;

    @Autowired
    private SysUserService userService;

    @Autowired
    private SysUserSnsService sysUserSnsService;

    @Autowired
    private XeVerifyCodeService xeVerifyCodeService;

    @Autowired
    private GuoshanUserService guoshanUserService;

    @Autowired
    private SpaceService spaceService;

    @Autowired
    private FileService fileService;

    @Autowired
    private BlockDeviceService blockDeviceService;

    @Autowired
    private IntegralService integralService;

    @Autowired
    private SysConfigService sysConfigService;

    //新加
    @Autowired
    private TokenService tokenService;

    @Autowired
    private QQAuthService qqAuthService;

    @Autowired
    private WeiXinAuthService weiXinAuthService;

    @Autowired
    private WeiBoAuthService weiBoAuthService;

    @Autowired
    private ChatUserService chatUserService;

    private static RestTemplate restTemplate = new RestTemplate();

    /**
     * 登录
     */
    @ResponseBody
    @IgnoreAuth
    @RequestMapping(value = "/website/login", method = RequestMethod.POST)
    public ResponseBean login(@RequestParam Map<String, Object> params,HttpServletRequest request) throws IOException {
        String userName = params.get("userName").toString();
        String password = params.get("password").toString();
        String deviceId = params.get("deviceId").toString();
        String deviceType = params.get("deviceType").toString();
        String pushToken = params.get("token").toString();

        //新加
        Token tokenEntity = null;

        String snsUserId = null;
        String snsType = null;
        if (params.get("snsUserId") != null && params.get("snsType") != null) {
            snsUserId = params.get("snsUserId").toString();
            snsType = params.get("snsType").toString();
        }
//		System.out.println(userName +"_"+password);

        JSONObject jsonObject = new JSONObject();
        //根据用户名查询用户
        SysUser mobileUser = userService.queryByPhone(userName);
        String error = null;

        if (mobileUser == null) {
            GuoshanUser guoshanUser = guoshanUserService.getByVolunteerId(userName);
            if (guoshanUser == null) {
                //既不是用户账号也不是志愿者号
                error = "name_empty";
                return new ResponseBean(false, ResponseBean.FAILED, error, null);
            }
            if (!password.equals(guoshanUser.getPassword())) {
                error = "password_incorrect";
                return new ResponseBean(false, ResponseBean.FAILED, error, null);
            }

            //如果是小记者或志愿者
            //调用外部接口,确定志愿者账号可登且返回信息，这里是以id，密码，账号类型完成登录的不是账号，可能有问题
            guoshanUrl += "?action=volunteerOauth&volunteerId=" + userName + "&password=" + password + "&type=" + guoshanUser.getType();
            String faren = restTemplate.getForEntity(guoshanUrl, String.class).getBody();
            com.alibaba.fastjson.JSONObject json = com.alibaba.fastjson.JSONObject.parseObject(faren);

            String code = json.get("code").toString();
            if (code.equals("0")) {
                mobileUser = userService.queryObject(guoshanUser.getUserId());
                if (mobileUser.getStatus() != 1) {
                    return new ResponseBean(false, ResponseBean.FAILED, "您的帐户已暂停!", "您的帐户已暂停!");
                }
                if (guoshanUser.getType().equals("1")) {
                    jsonObject.put("userType", "volunteer");
                    mobileUser.setUserType("volunteer");
                } else {
                    mobileUser.setUserType("newsman");
                    jsonObject.put("userType", "newsman");
                }
                blockDeviceService.deleteByUserAndDevice(mobileUser.getUserId(), deviceId);
                if (StringUtility.isNotEmpty(mobileUser.getDeviceId())) {
                    if (!mobileUser.getDeviceId().equals(deviceId)) {
                        BlockDevice blockDevice = new BlockDevice();
                        blockDevice.setDeviceId(mobileUser.getDeviceId());
                        blockDevice.setUserId(mobileUser.getUserId());
                        blockDeviceService.save(blockDevice);
                    }
                }
                mobileUser.setIsOnline("1");
                mobileUser.setDeviceId(deviceId);

                //获取已有token
                if (StringUtil.isNullOrEmpty(pushToken)) {
                    pushToken = (String) (tokenService.createToken(String.valueOf(mobileUser.getUserId())).get("token"));
                } else {
                    //新加
                    tokenEntity = tokenService.queryByToken(pushToken);
                    if (tokenEntity.getUserId().equals(mobileUser.getUserId().toString())) {
                        if (tokenEntity == null || tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()) {
                            pushToken = (String) (tokenService.createToken(String.valueOf(mobileUser.getUserId())).get("token"));
                        }
                    } else {
                        pushToken = (String) (tokenService.createToken(String.valueOf(mobileUser.getUserId())).get("token"));
                    }
                }

                mobileUser.setPushToken(pushToken);
                Map<String, Object> sessionParams = new HashMap<>();
                sessionParams.put("deviceId", deviceId);
                sessionParams.put("userId", mobileUser.getUserId());
                sessionParams.put("pushToken", pushToken);
                sessionParams.put("isOnline", "1");
                sessionParams.put("lastLoginDt", new Date());
                sessionParams.put("deviceType", deviceType);
                userService.updateSession(sessionParams);

                mobileUser.setRemark(userService.getRemark(mobileUser.getUserId()));
                jsonObject.put("member", mobileUser);
            } else {
                error = json.get("msg").toString();
                return new ResponseBean(false, ResponseBean.FAILED, error, null);
            }
            if (StringUtil.isNullOrEmpty(snsUserId) || StringUtil.isNullOrEmpty(snsType)) {

            } else {
                SysUserSns sysUserSns = sysUserSnsService.getBySns(snsUserId, snsType);
                SysUser existUser = userService.queryObject(sysUserSns.getUserId());
                if (existUser != null) {
                    return new ResponseBean(false, ResponseBean.FAILED, "已经联合登录!", "已经联合登录!");
                }
                if (sysUserSns != null) {
                    sysUserSns.setUserId(mobileUser.getUserId());
                    sysUserSnsService.updateUserId(sysUserSns);
                }
            }

            List<String> accids = userService.getAccidByRoleId("4");
            if (accids != null && accids.size() > 0) {
                String ipAddr = IPUtils.getIp(request);
                if (ipAddr!=null){
                    String msg = mobileUser.getNickname()+"|"+IPUtils.getAddress(ipAddr)+"|"+IPUtils.getPoint(ipAddr);
                    YunXinUtil yunXinUtil = new YunXinUtil();
                    try {
                        yunXinUtil.sendBatchPush(accids, "网站登录|" + msg);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            return new ResponseBean(false, ResponseBean.SUCCESS, "", jsonObject);
        }
        //使用普通账号
        blockDeviceService.deleteByUserAndDevice(mobileUser.getUserId(), deviceId);
        if (StringUtility.isNotEmpty(mobileUser.getDeviceId())) {
            if (!mobileUser.getDeviceId().equals(deviceId)) {
                BlockDevice blockDevice = new BlockDevice();
                blockDevice.setDeviceId(mobileUser.getDeviceId());
                blockDevice.setUserId(mobileUser.getUserId());
                blockDeviceService.save(blockDevice);
            }
        }
        mobileUser.setIsOnline("1");
        mobileUser.setDeviceId(deviceId);

        //新加
        if (StringUtil.isNullOrEmpty(pushToken)) {
            pushToken = (String) (tokenService.createToken(String.valueOf(mobileUser.getUserId())).get("token"));
        } else {
            //新加
            tokenEntity = tokenService.queryByToken(pushToken);
            if (tokenEntity.getUserId().equals(mobileUser.getUserId().toString())) {
                if (tokenEntity == null || tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()) {
                    pushToken = (String) (tokenService.createToken(String.valueOf(mobileUser.getUserId())).get("token"));
                }
            } else {
                pushToken = (String) (tokenService.createToken(String.valueOf(mobileUser.getUserId())).get("token"));
            }
        }

        mobileUser.setPushToken(pushToken);
        Map<String, Object> sessionParams = new HashMap<>();
        sessionParams.put("deviceId", deviceId);
        sessionParams.put("userId", mobileUser.getUserId());
        sessionParams.put("pushToken", pushToken);
        sessionParams.put("isOnline", "1");
        sessionParams.put("lastLoginDt", new Date());
        sessionParams.put("deviceType", deviceType);
        userService.updateSession(sessionParams);
        if (mobileUser.getStatus() != 1) {
            return new ResponseBean(false, ResponseBean.FAILED, "您的帐户已暂停!", "您的帐户已暂停!");
        }
        password = new Sha256Hash(password).toHex();

        //密码错误
        if (!password.equals(mobileUser.getPassword())) {
            error = "password_incorrect";
            return new ResponseBean(false, ResponseBean.FAILED, error, null);
        }

        Integer userClassCode = mobileUser.getUserClassCode();

        switch (userClassCode) {
            case 1:
                mobileUser.setUserType("admin");
                jsonObject.put("userType", "admin");
                break;
            case 2:
                mobileUser.setUserType("volunteer");
                jsonObject.put("userType", "volunteer");
                break;
            case 3:
                mobileUser.setUserType("newsman");
                jsonObject.put("userType", "newsman");
                break;
            case 9:
                mobileUser.setUserType("user");
                jsonObject.put("userType", "user");
                break;
            default:
                error = "error";
                break;
        }
        if (StringUtil.isNullOrEmpty(snsUserId) || StringUtil.isNullOrEmpty(snsType)) {

        } else {
            SysUserSns sysUserSns = sysUserSnsService.getBySns(snsUserId, snsType);
            SysUser existUser = userService.queryObject(sysUserSns.getUserId());
            if (existUser != null) {
                return new ResponseBean(false, ResponseBean.FAILED, "已经联合登录!", "已经联合登录!");
            }
            if (sysUserSns != null) {
                sysUserSns.setUserId(mobileUser.getUserId());
                sysUserSnsService.updateUserId(sysUserSns);
            }
        }
        mobileUser.setLastLoginDt(DateUtility.getCurrentTime());
        mobileUser.setPassword("");
        mobileUser.setRemark(userService.getRemark(mobileUser.getUserId()));
        jsonObject.put("member", mobileUser);

        List<String> accids = userService.getAccidByRoleId("4");
        if (accids != null && accids.size() > 0) {
            String ipAddr = IPUtils.getIp(request);
            if (ipAddr!=null){
                String msg = mobileUser.getNickname()+"|"+IPUtils.getAddress(ipAddr)+"|"+IPUtils.getPoint(ipAddr);
                YunXinUtil yunXinUtil = new YunXinUtil();
                try {
                    yunXinUtil.sendBatchPush(accids, "网站登录|" + msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return new ResponseBean(false, ResponseBean.SUCCESS, error, jsonObject);
    }


    @ResponseBody
    @IgnoreAuth
    @RequestMapping(value = "/website/loginWithWeixin1", method = RequestMethod.POST)
    public ResponseBean loginWithWeixin1(@RequestParam Map<String, Object> params) {
        SysUserSns sysUserSns = new SysUserSns();
        SysUser sysUser = new SysUser();

        //新加
        Token tokenEntity = null;

        Map<String, Object> result = new HashMap<>();
        try {

            String snsUserId = params.get("userId").toString();
            String accessToken = params.get("accessToken").toString();
            String userName = params.get("userName").toString();
            String userGender = params.get("userGender").toString();
            String userAvatar = params.get("userAvatar").toString();
            userAvatar = HtmlUtils.htmlUnescape(userAvatar);
            String snsType = params.get("snsType").toString();

            SysUserSns existSnsUser = sysUserSnsService.getBySns(snsUserId, snsType);
            if (existSnsUser != null) {
                sysUserSns = existSnsUser;
                sysUser = userService.queryObject(existSnsUser.getUserId());
                if (sysUser != null && sysUser.getStatus() != 1) {
                    return new ResponseBean(false, ResponseBean.FAILED, "您的帐户已暂停!", "您的帐户已暂停!");
                }
                if (sysUser == null) {
                    sysUser = new SysUser();
                }
            }
            //代表该第三方未有网站用户关联
            if (sysUser.getUserId() == null) {
                sysUserSns.setUserId(-1L);
            }

//            String imageId = UUID.randomUUID().toString();
//            CloudStorageService cloudStorageService = OSSFactory.build();
//            try {
//                URL url = new URL(userAvatar);
//                URLConnection conn = url.openConnection();
//
//                String imageUrl = cloudStorageService.uploadSuffix(conn.getInputStream(), ".jpg");
//                File imageFile = new File();
//                imageFile.setUploadId(imageId);
//                imageFile.setUrl(imageUrl);
//                imageFile.setFileName(userName + "_avatar.jpg");
//                imageFile.setOssYn("Y");
//                imageFile.setFileType("image");
//                imageFile.setFileSize(Integer.toString(conn.getContentLength()));
//
//                fileService.save(imageFile);
//                sysUserSns.setAvatar(imageId);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
            sysUserSns.setSnsType(snsType);
            sysUserSns.setSnsUserId(snsUserId);

            //新加
            //获取已有token
            if (params.get("accessToken") != null) {
                accessToken = params.get("accessToken").toString();
                //新加
                tokenEntity = tokenService.queryByToken(accessToken);
                if (tokenEntity != null && tokenEntity.getUserId().equals(existSnsUser.getUserId())) {
                    if (tokenEntity == null || tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()) {
                        accessToken = (String) (tokenService.createToken(String.valueOf(existSnsUser.getUserId())).get("token"));
                    }
                } else {
                    accessToken = (String) (tokenService.createToken(String.valueOf(existSnsUser.getUserId())).get("token"));
                }
            } else {
                accessToken = (String) (tokenService.createToken(String.valueOf(existSnsUser.getUserId())).get("token"));
            }

            sysUserSns.setAccessToken(accessToken);
            sysUserSns.setGender(userGender);
            sysUserSns.setUserName(userName);
            sysUserSns.setAvatar(userAvatar);
            //第三方用户的信息改变会更新
            if (existSnsUser != null) {
                sysUserSnsService.update(sysUserSns);
            } else {
                sysUserSnsService.save(sysUserSns);
            }

            Integer userClassCode = sysUser.getUserClassCode();
            if (userClassCode == null) {
                userClassCode = -1;
            }
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
            sysUser.setPassword("");
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (sysUser == null) {
            sysUser = new SysUser();
        }

        if (sysUser.getUserId() == null) {
            result.put("isUserExist", false);
        } else {
            result.put("isUserExist", true);
        }

        result.put("member", sysUser);
        result.put("weixin", sysUserSns);

        return new ResponseBean(false, ResponseBean.SUCCESS, null, result);
    }

    /**
     * 注册
     */
    //注册时完成推送的身份信息注册
//    @ResponseBody
//    @IgnoreAuth
//    @RequestMapping(value = "/website/register", method = RequestMethod.POST)
    public ResponseBean register(@RequestParam Map<String, Object> params, HttpServletRequest request) throws IOException {

        String userName = params.get("userName").toString();
        String verifyCode = params.get("verifyCode").toString();
        String password = params.get("password").toString();
        //新加
        String repassword = params.get("repassword").toString();
        String snsUserId = null;
        String snsType = null;
        if (!password.equals(repassword)) {
            return new ResponseBean(false, ResponseBean.FAILED, "确认密码与密码不一致", null);
        }
        if (params.get("snsUserId") != null && params.get("snsType") != null) {
            snsUserId = params.get("snsUserId").toString();
            snsType = params.get("snsType").toString();
        } else {//第三方登录回调方法可能把openid和type存到cookie
//            Cookie[] cookies = request.getCookies();
//            if (cookies != null && cookies.length > 0) {
//                for (Cookie cookie : cookies) {
//                    if ("snsUserId".equals(cookie.getName())) {
//                        snsUserId = cookie.getValue();
//                    }
//                    if ("snsType".equals(cookie.getName())) {
//                        snsType = cookie.getValue();
//                    }
//                }
//            }
            if (request.getSession().getAttribute("snsUserId") != null && request.getSession().getAttribute("snsType") != null) {
                snsUserId = request.getSession().getAttribute("snsUserId").toString();
                snsType = request.getSession().getAttribute("snsType").toString();
            }
        }
        password = new Sha256Hash(password).toHex();

        SysUser sysUser = new SysUser();
        SysUserSns sysUserSns = new SysUserSns();

        //如果用第三方账号注册，判定第三方账号是否已有用户信息，没有将第三方信息填入用户信息开始注册
        if (StringUtil.isNullOrEmpty(snsUserId) || StringUtil.isNullOrEmpty(snsType)) {

        } else {
            sysUserSns = sysUserSnsService.getBySns(snsUserId, snsType);

            String userGender = sysUserSns.getGender();
            if (userGender.equals(SexEnum.MEN.getValue())) {
                sysUser.setGender(SexEnum.MEN.getCode());
            } else if (userGender.equals(SexEnum.WOMEN.getValue())) {
                sysUser.setGender(SexEnum.WOMEN.getCode());
            } else {
                sysUser.setGender(SexEnum.UNKNOWN.getCode());
            }
            sysUser.setAvatar(sysUserSns.getAvatar());
            //?
            sysUser.setUsername(sysUserSns.getUserName());
            sysUser.setName(sysUserSns.getUserName());
            //sns的用户id和普通用户id是对应的
            SysUser existUser = userService.queryObject(sysUserSns.getUserId());
            if (existUser != null) {
                return new ResponseBean(false, ResponseBean.FAILED, "user_exist", null);
            }
        }

        //注册角色是用户
        sysUser.setRoleId(2L);
        //统一创建者为admin
        sysUser.setCreateUserId(1L);
        sysUser.setStatus(1);
        sysUser.setUserClassCode(SysUser.USER);
        sysUser.setPassword(password);
        sysUser.setPhoneNo(userName);

        XeVerifyCode xeVerifyCode = xeVerifyCodeService.getVerifyCode(userName, XeVerifyCode.REGISTER);

        if (xeVerifyCode == null) {
            return new ResponseBean(false, ResponseBean.FAILED, "invalid_code", null);
        }

        if (!xeVerifyCode.getCode().equals(verifyCode)) {
            return new ResponseBean(false, ResponseBean.FAILED, "invalid_code", null);
        }

        if (!xeVerifyCode.checkValidation()) {
            return new ResponseBean(false, ResponseBean.FAILED, "expired_code", null);
        }

        if (userService.checkDuplicateUser(sysUser)) {
            return new ResponseBean(false, ResponseBean.FAILED, "user_exist", null);
        }

        userService.insert(sysUser);

        IntegralConfig integralConfig = IntegralConfigFactory.build();
        YunXinUtil yunXinUtil = new YunXinUtil();
        try {
            yunXinUtil.createUser(sysUser);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Integral integral = new Integral();
        integral.setArticleId(1L);//这个id没得意义，纯粹不让他为null，服务器上会报sql错误
        integral.setArticleTypeCode(Article.USE_APP);
        integral.setUserId(sysUser.getUserId());
        integral.setTitle("首次使用app");
        integral.setIntegral(integralConfig.getUseAppIntegral().toString());
        integralService.save(integral);
        try {
            yunXinUtil.sendSinglePush(integral.getUserId(), "获得积分" + integral.getTitle());
        } catch (Exception e) {
        }

        if (StringUtil.isNullOrEmpty(snsUserId) || StringUtil.isNullOrEmpty(snsType)) {

        } else {
            sysUserSns.setUserId(sysUser.getUserId());
            sysUserSns.setSnsType(snsType);
            sysUserSnsService.updateUserId(sysUserSns);
        }
        xeVerifyCodeService.delete(xeVerifyCode.getId());

        SysUser registeredUser = userService.queryObject(sysUser.getUserId());
        Map<String, Object> map = new HashMap<>();
        registeredUser.setPassword("");
        map.put("user", registeredUser);

        return new ResponseBean(false, ResponseBean.SUCCESS, null, map);
    }


    @ResponseBody
    @IgnoreAuth
    @RequestMapping(value = "/website/loginWithWeixin", method = RequestMethod.POST)
    public ResponseBean loginWithWeixin(@RequestParam(value = "code") String code, HttpServletRequest request) {
        ResponseBean tokenResponse = qqAuthService.getAccessToken(code);
        if (tokenResponse.getStatus().equals("success")) {
            ResponseBean openidResponse = qqAuthService.getOpenId(tokenResponse.getData().toString());
            if (openidResponse.getStatus().equals("success")) {
                qqAuthService.getUserInfo(tokenResponse.getData().toString(), openidResponse.getData().toString());
                int i = 1;
            }
        }

        return new ResponseBean(false, ResponseBean.SUCCESS, null, null);
    }


    @ResponseBody
    @IgnoreAuth
    @RequestMapping(value = "/website/register/sendcode/{userName}", method = RequestMethod.GET)
    public ResponseBean registerSendCode(@PathVariable("userName") String userName) throws IOException {

        SysUser sysUser = new SysUser();
        sysUser.setPhoneNo(userName);
        if (userService.checkDuplicateUser(sysUser)) {
            return new ResponseBean(false, ResponseBean.FAILED, "user_exist", null);
        }

        String verifyCode = RandomCharUtil.getSixNumRand();

        XeVerifyCode xeVerifyCode = xeVerifyCodeService.getVerifyCode(userName, XeVerifyCode.REGISTER);

        if (xeVerifyCode != null && xeVerifyCode.checkValidation()) {
            return new ResponseBean(false, ResponseBean.FAILED, "already_sent", null);
        }

        if (xeVerifyCode == null) {
            xeVerifyCode = new XeVerifyCode();
            xeVerifyCode.setVerifyTypeCode(XeVerifyCode.REGISTER);
            xeVerifyCode.setPhone(userName);
        }

        xeVerifyCode.setCode(verifyCode);
        xeVerifyCode.setState("1");

        String ret = DxtonSMSSend.sendVerifyCode(userName, verifyCode);
        if (!ret.contains("100")) {
            return new ResponseBean(false, ResponseBean.FAILED, DxtonSMSSend.getResponseText(ret.trim()), null);
        }
        if (xeVerifyCode.getId() != null) {
            xeVerifyCodeService.update(xeVerifyCode);
        } else {
            xeVerifyCodeService.save(xeVerifyCode);
        }

        return new ResponseBean(false, ResponseBean.SUCCESS, null, DxtonSMSSend.getResponseText(ret.trim()));
    }

    @ResponseBody
    @IgnoreAuth
    @RequestMapping(value = "/website/forget/sendcode/{userName}", method = RequestMethod.GET)
    public ResponseBean forgetSendCode(@PathVariable("userName") String userName) throws IOException {

        SysUser sysUser = new SysUser();
        sysUser.setPhoneNo(userName);
        if (!userService.checkDuplicateUser(sysUser)) {
            return new ResponseBean(false, ResponseBean.FAILED, "invalid_phone", null);
        }

        String verifyCode = RandomCharUtil.getSixNumRand();

        XeVerifyCode xeVerifyCode = xeVerifyCodeService.getVerifyCode(userName, XeVerifyCode.FORGET_PASSWORD);

        if (xeVerifyCode != null && xeVerifyCode.checkValidation()) {
            return new ResponseBean(false, ResponseBean.FAILED, "already_sent", null);
        }
        if (xeVerifyCode == null) {
            xeVerifyCode = new XeVerifyCode();
            xeVerifyCode.setVerifyTypeCode(XeVerifyCode.FORGET_PASSWORD);
            xeVerifyCode.setPhone(userName);
        }

        xeVerifyCode.setCode(verifyCode);
        xeVerifyCode.setState("1");

        String ret = DxtonSMSSend.sendVerifyCode(userName, verifyCode);

        if (!ret.contains("100")) {
            return new ResponseBean(false, ResponseBean.FAILED, DxtonSMSSend.getResponseText(ret.trim()), null);
        }
        if (xeVerifyCode.getId() != null) {
            xeVerifyCodeService.update(xeVerifyCode);
        } else {
            xeVerifyCodeService.save(xeVerifyCode);
        }

        return new ResponseBean(false, ResponseBean.SUCCESS, null, DxtonSMSSend.getResponseText(ret.trim()));
    }

    @ResponseBody
    @IgnoreAuth
    @RequestMapping(value = "/website/forget", method = RequestMethod.POST)
    public ResponseBean forget(@RequestParam Map<String, Object> params) throws IOException {

        String userName = params.get("userName").toString();
        String newPassword = params.get("newPassword").toString();
        String verifyCode = params.get("verifyCode").toString();

        SysUser sysUser = userService.queryByPhone(userName);

        if (sysUser == null) {
            return new ResponseBean(false, ResponseBean.FAILED, "invalid_phone", null);
        }

        newPassword = new Sha256Hash(newPassword).toHex();

        XeVerifyCode xeVerifyCode = xeVerifyCodeService.getVerifyCode(userName, XeVerifyCode.FORGET_PASSWORD);

        if (xeVerifyCode == null) {
            return new ResponseBean(false, ResponseBean.FAILED, "invalid_code", null);
        }

        if (!xeVerifyCode.getCode().equals(verifyCode)) {
            return new ResponseBean(false, ResponseBean.FAILED, "invalid_code", null);
        }

        if (!xeVerifyCode.checkValidation()) {
            return new ResponseBean(false, ResponseBean.FAILED, "expired_code", null);
        }

        userService.updatePassword(sysUser.getUserId(), sysUser.getPassword(), newPassword);

        xeVerifyCodeService.delete(xeVerifyCode.getId());
        sysUser.setPassword("");
        return new ResponseBean(false, ResponseBean.SUCCESS, null, sysUser);
    }

    @ResponseBody
    @RequestMapping(value = "/website/logout/{userId}", method = RequestMethod.GET)
    public ResponseBean logout(@PathVariable("userId") Long userId,HttpServletRequest request) {
        SysUser sysUser = userService.queryObject(userId);
        sysUser.setDeviceId("");
        sysUser.setIsOnline("0");
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("pushToken", "");
        params.put("isOnline", "0");
        userService.updateSession(params);
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("activeNav") || cookie.getName().equals("spaceId")) {
                    continue;
                }
                cookie.setMaxAge(0);
            }
        }
        return new ResponseBean(false, ResponseBean.SUCCESS, null, null);
    }

    @ResponseBody
    @RequestMapping("/website/checkLogin")
    public ResponseBean checkLogin(@RequestParam Map<String, Object> params) {

        String userId = params.get("userId").toString();
        String deviceId = params.get("deviceId").toString();
        SysUser sysUser = userService.getNewLogin(Long.parseLong(userId), deviceId);
        boolean newLogin;
        Map<String, Object> result = new HashMap<>();
        if (sysUser == null) {
            newLogin = false;
        } else {
            newLogin = true;
//			blockDeviceService.deleteByUserAndDevice(Long.parseLong(userId), deviceId);
            result.put("newLoginDt", sysUser.getLastLoginDt());
            result.put("newDeviceId", sysUser.getDeviceId());
            result.put("newDeviceType", sysUser.getDeviceType());
        }


        PushAdmin pushAdmin = sysConfigService.getConfigClassObject(ConfigConstant.PUSH_ADMIN_CONFIG_KEY, PushAdmin.class);
        result.put("newLogin", newLogin);
        result.put("adminChatId", pushAdmin.getAccid());

        return new ResponseBean(false, ResponseBean.SUCCESS, null, result);
    }

    /**
     * @return void
     * @Description TODO 第三方登录回调方法
     * @Param [type, code]
     * @Author XiaoDong
     **/
    @GetMapping("/oauth/callback/type/{type}")
    public String callBack(@PathVariable("type") String type, String code, HttpServletRequest request, HttpServletResponse response) {
        Token tokenEntity = null;
        String pushToken = null;
        List<Cookie> cookies = new ArrayList<>();
        switch (type) {
            case "qq":
                String qqToken = qqAuthService.getAccessToken(code).getData().toString();//token
                String qqOpenId = qqAuthService.getOpenId(qqToken).getData().toString();//openID
                com.alibaba.fastjson.JSONObject qqUserInfo = qqAuthService.getUserInfo(qqToken, qqOpenId);//第三方用户详情
                SysUserSns qqUserSns = sysUserSnsService.getBySnsUserId(qqOpenId, "SNS002");//查看有没有第三方openid信息
                if (qqUserSns != null) {
                    qqUserSns.setAccessToken(qqToken);
                    sysUserSnsService.update(qqUserSns);
                }
                if (qqUserSns == null) {//如果没有就新建入库
                    qqUserSns = new SysUserSns();
                    qqUserSns.setUserId(-1L);
                    qqUserSns.setSnsType("SNS002");
                    qqUserSns.setAccessToken(qqToken);
                    qqUserSns.setAvatar(qqUserInfo.getString("figureurl_qq_2"));
                    qqUserSns.setGender(qqUserInfo.getString("gender"));
                    qqUserSns.setUserName(qqUserInfo.getString("nickname"));
                    qqUserSns.setSnsUserId(qqOpenId);

                    String imageId = UUID.randomUUID().toString();//保存头像
                    CloudStorageService cloudStorageService = OSSFactory.build();
                    try {
                        URL url = new URL(qqUserSns.getAvatar());
                        URLConnection conn = url.openConnection();

                        String imageUrl = cloudStorageService.uploadSuffix(conn.getInputStream(), ".jpg");
                        File imageFile = new File();
                        imageFile.setUploadId(imageId);
                        imageFile.setUrl(imageUrl);
                        imageFile.setFileName(qqUserSns.getUserName() + "_avatar.jpg");
                        imageFile.setOssYn("Y");
                        imageFile.setFileType("image");
                        imageFile.setFileSize(Integer.toString(conn.getContentLength()));

                        fileService.save(imageFile);
                        qqUserSns.setAvatar(imageId);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    sysUserSnsService.save(qqUserSns);
                    //然后跳转到注册页面
                    cookies.clear();
                    cookies.add(new Cookie("snsType", "SNS002"));
                    cookies.add(new Cookie("snsUserId", qqUserSns.getSnsUserId()));
                    for (Cookie cookie : cookies) {
                        cookie.setPath("/");
                        cookie.setDomain("guoshanchina.com");
                        response.addCookie(cookie);
                    }
                    return "redirect:http://app.guoshanchina.com/bind.html";
                }

                if (qqUserSns.getUserId() == -1) {//没有绑定账户
                    cookies.clear();
                    cookies.add(new Cookie("snsType", "SNS002"));
                    cookies.add(new Cookie("snsUserId", qqUserSns.getSnsUserId()));
                    for (Cookie cookie : cookies) {
                        cookie.setPath("/");
                        cookie.setDomain("guoshanchina.com");
                        response.addCookie(cookie);
                    }
                    return "redirect:http://app.guoshanchina.com/bind.html";
                }
                //有绑定账户
                //跳转到首页或者其他
                //新加
                if (StringUtil.isNullOrEmpty(pushToken)) {
                    pushToken = (String) (tokenService.createToken(String.valueOf(qqUserSns.getUserId())).get("token"));
                } else {
                    //新加
                    tokenEntity = tokenService.queryByToken(pushToken);
                    if (tokenEntity.getUserId().equals(qqUserSns.getUserId().toString())) {
                        if (tokenEntity == null || tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()) {
                            pushToken = (String) (tokenService.createToken(String.valueOf(qqUserSns.getUserId())).get("token"));
                        }
                    } else {
                        pushToken = (String) (tokenService.createToken(String.valueOf(qqUserSns.getUserId())).get("token"));
                    }
                }

                SysUser qqUser = userService.queryObject(qqUserSns.getUserId());
                if (qqUser != null && qqUser.getStatus() == 1) {
                    qqUser.setPushToken(pushToken);
                    cookies.clear();
                    cookies.add(new Cookie("remark",userService.getRemark(qqUser.getUserId())));
                    cookies.add(new Cookie("user_userId", qqUser.getUserId().toString()));
                    cookies.add(new Cookie("user_avatar", qqUser.getAvatar()));
                    cookies.add(new Cookie("user_nickname", qqUser.getNickname()));
                    cookies.add(new Cookie("user_address", qqUser.getAddress()));
                    cookies.add(new Cookie("user_userType", qqUser.getUserType()));
                    if (qqUser.getVolunteerId() != null) {
                        cookies.add(new Cookie("user_volunteerId", qqUser.getVolunteerId().toString()));
                    }
                    cookies.add(new Cookie("user_username", qqUser.getUsername()));
                    cookies.add(new Cookie("user_token", qqUser.getPushToken()));

                    ChatUser weixinChatUser = chatUserService.get(qqUser.getUserId());
                    if (weixinChatUser != null) {
                        cookies.add(new Cookie("yunxin_accid", weixinChatUser.getAccid()));
                        cookies.add(new Cookie("yunxin_token", weixinChatUser.getToken()));
                    }

                    for (Cookie cookie : cookies) {
                        try {
                            cookie.setPath("/");
                            cookie.setDomain("guoshanchina.com");
                            response.addCookie(cookie);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    return "redirect:http://app.guoshanchina.com/home.html";
                }
            case "weixin"://TODO 微信*************************************************************
                String weixinResult = weiXinAuthService.getAccessToken(code).getData().toString();
                com.alibaba.fastjson.JSONObject weixin = JSON.parseObject(weixinResult);//微信可以直接通过code获取token和openid
                String weixinToken = weixin.getString("access_token");
                String weixinOpenId = weixin.getString("unionid");
                com.alibaba.fastjson.JSONObject weixinUserInfo = weiXinAuthService.getUserInfo(weixinToken, weixinOpenId);
                SysUserSns weixinSns = sysUserSnsService.getBySnsUserId(weixinOpenId, "SNS003");
                if (weixinSns != null) {
                    weixinSns.setAccessToken(weixinToken);
                    sysUserSnsService.update(weixinSns);
                }
                if (weixinSns == null) {//没有第三方信息
                    weixinSns = new SysUserSns();
                    weixinSns.setSnsUserId(weixinOpenId);
                    weixinSns.setUserId(-1L);//没有关联账号
                    weixinSns.setSnsType("SNS003");//第三方方式，微信
                    weixinSns.setAccessToken(weixinToken);
                    weixinSns.setUserName(weixinUserInfo.getString("nickname"));
                    switch (weixinUserInfo.getString("sex")) {
                        case "1":
                            weixinSns.setGender("男");
                            break;
                        case "2":
                            weixinSns.setGender("女");
                            break;
                    }
                    CloudStorageService cloudStorageService = OSSFactory.build();//上传保存头像
                    String imageId = UUID.randomUUID().toString();
                    try {
                        URL weixinUrl = new URL(weixinUserInfo.getString("headimgurl"));
                        URLConnection urlConnection = weixinUrl.openConnection();
                        String weixinImageUrl = cloudStorageService.uploadSuffix(urlConnection.getInputStream(), ".jpg");
                        File imageFile = new File();
                        imageFile.setUploadId(imageId);
                        imageFile.setUrl(weixinImageUrl);
                        imageFile.setFileName(weixinSns.getUserName() + "_avatar.jpg");
                        imageFile.setOssYn("Y");
                        imageFile.setFileType("image");
                        imageFile.setFileSize(Integer.toString(urlConnection.getContentLength()));
                        fileService.save(imageFile);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    weixinSns.setAvatar(imageId);
                    sysUserSnsService.save(weixinSns);
                    cookies.clear();
                    cookies.add(new Cookie("snsType", "SNS003"));
                    cookies.add(new Cookie("snsUserId", weixinSns.getSnsUserId()));
                    for (Cookie cookie : cookies) {
                        cookie.setPath("/");
                        cookie.setDomain("guoshanchina.com");
                        response.addCookie(cookie);
                    }
                    return "redirect:http://app.guoshanchina.com/bind.html";//绑定操作
                }
                if (weixinSns.getUserId() == null || weixinSns.getUserId() < 0) {//有信息但是没有绑定

                    weiXinAuthService.update(weixinSns, weixinToken, weixinUserInfo);

                    cookies.clear();
                    cookies.add(new Cookie("snsType", "SNS003"));
                    cookies.add(new Cookie("snsUserId", weixinSns.getSnsUserId()));
                    for (Cookie cookie : cookies) {
                        cookie.setPath("/");
                        cookie.setDomain("guoshanchina.com");
                        response.addCookie(cookie);
                    }
                    return "redirect:http://app.guoshanchina.com/bind.html";//绑定操作
                }

                //新加
                if (StringUtil.isNullOrEmpty(pushToken)) {
                    pushToken = (String) (tokenService.createToken(String.valueOf(weixinSns.getUserId())).get("token"));
                } else {
                    //新加
                    tokenEntity = tokenService.queryByToken(pushToken);
                    if (tokenEntity.getUserId().equals(weixinSns.getUserId().toString())) {
                        if (tokenEntity == null || tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()) {
                            pushToken = (String) (tokenService.createToken(String.valueOf(weixinSns.getUserId())).get("token"));
                        }
                    } else {
                        pushToken = (String) (tokenService.createToken(String.valueOf(weixinSns.getUserId())).get("token"));
                    }
                }

                weiXinAuthService.update(weixinSns, weixinToken, weixinUserInfo);

                SysUser weixinUser = userService.queryObject(weixinSns.getUserId());
                if (weixinUser != null) {
                    weixinUser.setPushToken(pushToken);
                    cookies.clear();
                    cookies.add(new Cookie("remark",userService.getRemark(weixinUser.getUserId())));
                    cookies.add(new Cookie("user_userId", weixinUser.getUserId().toString()));
                    cookies.add(new Cookie("user_avatar", weixinUser.getAvatar()));
                    cookies.add(new Cookie("user_nickname", weixinUser.getNickname()));
                    cookies.add(new Cookie("user_address", weixinUser.getAddress()));
                    cookies.add(new Cookie("user_userType", weixinUser.getUserType()));
                    if (weixinUser.getVolunteerId() != null) {
                        cookies.add(new Cookie("user_volunteerId", weixinUser.getVolunteerId().toString()));
                    }
                    cookies.add(new Cookie("user_username", weixinUser.getUsername()));
                    cookies.add(new Cookie("user_token", weixinUser.getPushToken()));

                    ChatUser weixinChatUser = chatUserService.get(weixinUser.getUserId());
                    if (weixinChatUser != null) {
                        cookies.add(new Cookie("yunxin_accid", weixinChatUser.getAccid()));
                        cookies.add(new Cookie("yunxin_token", weixinChatUser.getToken()));
                    }

                    for (Cookie cookie : cookies) {
                        try {
                            cookie.setPath("/");
                            cookie.setDomain("guoshanchina.com");
                            response.addCookie(cookie);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    return "redirect:http://app.guoshanchina.com/home.html";//这里做登录操作
                }
            case "weibo"://TODO 新浪微博*************************************************************
                String weiboResult = weiBoAuthService.getAccessToken(code).getData().toString();
                if ("0".equals(weiboResult)) {
                    //获取信息失败，code重复使用
                    return "redirect:http://app.guoshanchina.com/bind.html";
                }
                com.alibaba.fastjson.JSONObject weibo = JSON.parseObject(weiboResult);
                String weiboToken = weibo.getString("access_token");
                String weiboOpenId = weibo.getString("uid");

                com.alibaba.fastjson.JSONObject weiboUserInfo = weiBoAuthService.getUserInfo(weiboToken, weiboOpenId);
                SysUserSns weiboSns = sysUserSnsService.getBySnsUserId(weiboOpenId, "SNS001");
                if (weiboSns != null) {
                    weiboSns.setAccessToken(weiboToken);
                    sysUserSnsService.update(weiboSns);
                }
                if (weiboSns == null) {
                    weiboSns = new SysUserSns();
                    weiboSns.setAccessToken(weiboToken);
                    weiboSns.setUserId(-1L);
                    weiboSns.setSnsUserId(weiboOpenId);
                    weiboSns.setSnsType("SNS001");
                    weiboSns.setUserName(weiboUserInfo.getString("screen_name"));
                    switch (weiboUserInfo.getString("gender")) {
                        case "m":
                            weiboSns.setGender("男");
                            break;
                        case "f":
                            weiboSns.setGender("女");
                            break;
                        case "n":
                            weiboSns.setGender("未知");
                            break;
                    }
                    String avataUrl = HtmlUtils.htmlUnescape(weiboUserInfo.getString("profile_image_url"));
                    CloudStorageService cloudStorageService = OSSFactory.build();//上传保存头像
                    String imageId = UUID.randomUUID().toString();
                    try {
                        URL weiboUrl = new URL(avataUrl);
                        URLConnection urlConnection = weiboUrl.openConnection();
                        String weiboImageUrl = cloudStorageService.uploadSuffix(urlConnection.getInputStream(), ".jpg");
                        File imageFile = new File();
                        imageFile.setUploadId(imageId);
                        imageFile.setUrl(weiboImageUrl);
                        imageFile.setFileName(weiboSns.getUserName() + "_avatar.jpg");
                        imageFile.setOssYn("Y");
                        imageFile.setFileType("image");
                        imageFile.setFileSize(Integer.toString(urlConnection.getContentLength()));
                        fileService.save(imageFile);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    weiboSns.setAvatar(imageId);
                    sysUserSnsService.save(weiboSns);

                    cookies.clear();
                    cookies.add(new Cookie("snsType", "SNS001"));
                    cookies.add(new Cookie("snsUserId", weiboSns.getSnsUserId()));
                    for (Cookie cookie : cookies) {
                        cookie.setPath("/");
                        cookie.setDomain("guoshanchina.com");
                        response.addCookie(cookie);
                    }
                    return "redirect:http://app.guoshanchina.com/bind.html";//绑定操作
                }

                if (weiboSns.getUserId() == null || weiboSns.getUserId() < 0) {
                    weiBoAuthService.update(weiboSns, weiboToken, weiboUserInfo);
                    cookies.clear();
                    cookies.add(new Cookie("snsType", "SNS001"));
                    cookies.add(new Cookie("snsUserId", weiboSns.getSnsUserId()));
                    for (Cookie cookie : cookies) {
                        cookie.setPath("/");
                        cookie.setDomain("guoshanchina.com");
                        response.addCookie(cookie);
                    }
                    return "redirect:http://app.guoshanchina.com/bind.html";//绑定操作
                }

                //新加
                if (StringUtil.isNullOrEmpty(pushToken)) {
                    pushToken = (String) (tokenService.createToken(String.valueOf(weiboSns.getUserId())).get("token"));
                } else {
                    //新加
                    tokenEntity = tokenService.queryByToken(pushToken);
                    if (tokenEntity.getUserId().equals(weiboSns.getUserId().toString())) {
                        if (tokenEntity == null || tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()) {
                            pushToken = (String) (tokenService.createToken(String.valueOf(weiboSns.getUserId())).get("token"));
                        }
                    } else {
                        pushToken = (String) (tokenService.createToken(String.valueOf(weiboSns.getUserId())).get("token"));
                    }
                }

                weiBoAuthService.update(weiboSns, weiboToken, weiboUserInfo);

                SysUser weiboUser = userService.queryObject(weiboSns.getUserId());
                if (weiboUser != null) {
                    weiboUser.setPushToken(pushToken);
                    cookies.clear();
                    cookies.add(new Cookie("remark",userService.getRemark(weiboUser.getUserId())));
                    cookies.add(new Cookie("user_userId", weiboUser.getUserId().toString()));
                    cookies.add(new Cookie("user_avatar", weiboUser.getAvatar()));
                    cookies.add(new Cookie("user_nickname", weiboUser.getNickname()));
                    cookies.add(new Cookie("user_address", weiboUser.getAddress()));
                    cookies.add(new Cookie("user_userType", weiboUser.getUserType()));
                    if (weiboUser.getVolunteerId() != null) {
                        cookies.add(new Cookie("user_volunteerId", weiboUser.getVolunteerId().toString()));
                    }
                    cookies.add(new Cookie("user_username", weiboUser.getUsername()));
                    cookies.add(new Cookie("user_token", weiboUser.getPushToken()));

                    ChatUser weiboChatUser = chatUserService.get(weiboUser.getUserId());
                    if (weiboChatUser != null) {
                        cookies.add(new Cookie("yunxin_accid", weiboChatUser.getAccid()));
                        cookies.add(new Cookie("yunxin_token", weiboChatUser.getToken()));
                    }

                    for (Cookie cookie : cookies) {
                        try {
                            cookie.setPath("/");
                            cookie.setDomain("guoshanchina.com");
                            response.addCookie(cookie);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    return "redirect:http://app.guoshanchina.com/home.html";//这里做登录操作
                }
        }
        return "redirect:http://app.guoshanchina.com/bind.html";
    }

    /**
     * @return xin.xiaoer.modules.mobile.bean.ResponseBean
     * @Description TODO 第三方绑定用户
     * @Param [params, request]
     * @Author XiaoDong
     **/
    @PostMapping("/website/userBind")
    @ResponseBody
    public ResponseBean userBind(@RequestParam Map<String, String> params) {
        String snsType = params.get("snsType");
        String snsUserId = params.get("snsUserId");
        String userName = params.get("userName");
        String password = params.get("password");
        String error = null;

        if (StringUtils.isBlank(userName) || StringUtils.isBlank(password)) {//验证参数
            error = "账号密码不能为空";
            return new ResponseBean(false, ResponseBean.FAILED, error, null);
        }
        SysUser sysUser = userService.queryByPhone(userName);
        if (sysUser == null || sysUser.getStatus() != 1) {
            error = "无此账号或已停用";
            return new ResponseBean(false, ResponseBean.FAILED, error, null);
        }

        password = new Sha256Hash(password).toHex();
        if (!password.equals(sysUser.getPassword())) {
            error = "密码错误";
            return new ResponseBean(false, ResponseBean.FAILED, error, null);
        }
        SysUserSns snsUser = sysUserSnsService.getBySnsUserId(snsUserId, snsType);
        if (snsUser != null && StringUtils.isBlank(snsUser.getAccessToken())) {
            snsUser.setAccessToken("-1");
        }
        if (snsUser == null) {
            snsUser = new SysUserSns();
            snsUser.setUserId(sysUser.getUserId());
            snsUser.setSnsUserId(snsUserId);
            snsUser.setAccessToken("-1");
            snsUser.setSnsType(snsType);
            sysUserSnsService.save(snsUser);
            sysUser.setPassword("");
            return new ResponseBean(false, ResponseBean.SUCCESS, null, "绑定成功", sysUser);
        }

        if (snsUser.getUserId() > -1L) {
            error = "此账户已绑定";
            return new ResponseBean(false, ResponseBean.FAILED, error, null);
        }
        if (snsUser.getUserId() == -1L) {
            snsUser.setUserId(sysUser.getUserId());
            sysUserSnsService.updateUserId(snsUser);
        }

        sysUser.setPassword("");
        return new ResponseBean(false, ResponseBean.SUCCESS, null, "绑定成功", sysUser);
    }
}
