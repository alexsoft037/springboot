package xin.xiaoer.modules.mobile.controller;

import com.google.code.kaptcha.Producer;
import net.sf.json.JSONObject;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.annotations.ApiIgnore;
import xin.xiaoer.common.api.annotation.IgnoreAuth;
import xin.xiaoer.common.enumresource.SexEnum;
import xin.xiaoer.common.integralConfig.IntegralConfig;
import xin.xiaoer.common.integralConfig.IntegralConfigFactory;
import xin.xiaoer.common.oss.CloudStorageService;
import xin.xiaoer.common.oss.OSSFactory;
import xin.xiaoer.common.utils.*;
import xin.xiaoer.entity.*;
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
import xin.xiaoer.service.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;
import java.util.*;

/**
 * 登录相关
 *
 * @author chenyi
 * @email 228112142@qq.com
 * @date 2016年11月10日 下午1:15:31
 */
@Controller
@ApiIgnore
public class APILoginController {
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

    private static RestTemplate restTemplate = new RestTemplate();

    /**
     * 登录
     */
    @ResponseBody
    @IgnoreAuth
    @RequestMapping(value = "/mobile/login", method = RequestMethod.POST)
    public ResponseBean login(@RequestParam Map<String, Object> params, HttpServletRequest request) throws IOException {
        String userName = params.get("userName").toString();
        String password = params.get("password").toString();
        String deviceId = params.get("deviceId").toString();
        String deviceType = params.get("deviceType").toString();
        String pushToken = "";

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
                if (params.get("pushToken") != null) {
                    pushToken = params.get("pushToken").toString();
                    //新加
                    tokenEntity = tokenService.queryByToken(pushToken);
                    if (tokenEntity == null || tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()) {
                        pushToken = (String) (tokenService.createToken(String.valueOf(mobileUser.getUserId())).get("token"));
                    }
                } else {
                    pushToken = (String) (tokenService.createToken(String.valueOf(mobileUser.getUserId())).get("token"));
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

                jsonObject.put("member", mobileUser);
            } else {
                error = json.get("msg").toString();
                return new ResponseBean(false, ResponseBean.FAILED, error, null);
            }
            if (snsUserId != null && snsType != null) {
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
                        yunXinUtil.sendBatchPush(accids, "APP登录|" + msg);
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
        if (params.get("pushToken") != null) {
            pushToken = params.get("pushToken").toString();
            tokenEntity = tokenService.queryByToken(pushToken);
            if (tokenEntity == null || tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()) {
                pushToken = (String) (tokenService.createToken(String.valueOf(mobileUser.getUserId())).get("token"));
            }
        } else {
            pushToken = (String) (tokenService.createToken(String.valueOf(mobileUser.getUserId())).get("token"));
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
        if (snsUserId != null && snsType != null) {
            SysUserSns sysUserSns = sysUserSnsService.getBySns(snsUserId, snsType);
            SysUser existUser = null;
            if (sysUserSns != null) {
                existUser = userService.queryObject(sysUserSns.getUserId());
            }
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
        jsonObject.put("member", mobileUser);

        List<String> accids = userService.getAccidByRoleId("4");
        if (accids != null && accids.size() > 0) {
            String ipAddr = IPUtils.getIp(request);
            if (ipAddr!=null){
                String msg = mobileUser.getNickname()+"|"+IPUtils.getAddress(ipAddr)+"|"+IPUtils.getPoint(ipAddr);
                YunXinUtil yunXinUtil = new YunXinUtil();
                try {
                    yunXinUtil.sendBatchPush(accids, "APP登录|" + msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return new ResponseBean(false, ResponseBean.SUCCESS, error, jsonObject);
    }

    /**
     * 登录
     */
    @ResponseBody
    @IgnoreAuth
    @RequestMapping(value = "/mobile/loginWithWeixin", method = RequestMethod.POST)
    public ResponseBean loginWithWeixin(@RequestParam Map<String, Object> params ,HttpServletRequest request) {
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

            if (sysUser.getUserId() == null) {
                sysUserSns.setUserId(-1L);
            }

            String imageId = UUID.randomUUID().toString();
            CloudStorageService cloudStorageService = OSSFactory.build();
            try {
                URL url = new URL(userAvatar);
                URLConnection conn = url.openConnection();

                String imageUrl = cloudStorageService.uploadSuffix(conn.getInputStream(), ".jpg");
                File imageFile = new File();
                imageFile.setUploadId(imageId);
                imageFile.setUrl(imageUrl);
                imageFile.setFileName(userName + "_avatar.jpg");
                imageFile.setOssYn("Y");
                imageFile.setFileType("image");
                imageFile.setFileSize(Integer.toString(conn.getContentLength()));

                fileService.save(imageFile);
                sysUserSns.setAvatar(imageId);
            } catch (Exception e) {

            }
            sysUserSns.setSnsType(snsType);
            sysUserSns.setSnsUserId(snsUserId);

            //新加
            //获取已有token
            if (params.get("accessToken") != null) {
                accessToken = params.get("accessToken").toString();
                //新加
                tokenEntity = tokenService.queryByToken(accessToken);
                if (accessToken == null || tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()) {
                    accessToken = (String) (tokenService.createToken(String.valueOf(existSnsUser.getUserId())).get("token"));
                }
            } else {
                accessToken = (String) (tokenService.createToken(String.valueOf(existSnsUser.getUserId())).get("token"));
            }

            sysUserSns.setAccessToken(accessToken);
            sysUserSns.setGender(userGender);
            sysUserSns.setUserName(userName);

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

        List<String> accids = userService.getAccidByRoleId("4");
        if (accids != null && accids.size() > 0) {
            String ipAddr = IPUtils.getIp(request);
            if (ipAddr!=null){
                String msg = sysUser.getNickname()+"|"+IPUtils.getAddress(ipAddr)+"|"+IPUtils.getPoint(ipAddr);
                YunXinUtil yunXinUtil = new YunXinUtil();
                try {
                    yunXinUtil.sendBatchPush(accids, "APP登录|" + msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return new ResponseBean(false, ResponseBean.SUCCESS, null, result);
    }

    /**
     * 登录
     */
//	@ResponseBody
//	@IgnoreAuth
//	@RequestMapping(value = "/mobile/register", method = RequestMethod.POST)
    public ResponseBean register(@RequestParam Map<String, Object> params) throws IOException {

        String userName = params.get("userName").toString();
        String verifyCode = params.get("verifyCode").toString();
        String password = params.get("password").toString();
        String snsUserId = null;
        String snsType = null;
        if (params.get("snsUserId") != null && params.get("snsType") != null) {
            snsUserId = params.get("snsUserId").toString();
            snsType = params.get("snsType").toString();
        }
        password = new Sha256Hash(password).toHex();

        SysUser sysUser = new SysUser();
        SysUserSns sysUserSns = new SysUserSns();

        if (snsUserId != null && snsType != null) {
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
            sysUser.setUsername(sysUserSns.getUserName());
            sysUser.setName(sysUserSns.getUserName());

            SysUser existUser = userService.queryObject(sysUserSns.getUserId());
            if (existUser != null) {
                return new ResponseBean(false, ResponseBean.FAILED, "user_exist", null);
            }
        }

        sysUser.setRoleId(2L);
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
        integral.setArticleTypeCode(Article.USE_APP);
        integral.setUserId(sysUser.getUserId());
        integral.setTitle("首次使用app");
        integral.setIntegral(integralConfig.getUseAppIntegral().toString());
        integralService.save(integral);
        try {
            yunXinUtil.sendSinglePush(integral.getUserId(), "获得积分" + integral.getTitle());
        } catch (Exception e) {
        }

        if (snsUserId != null && snsType != null) {
            sysUserSns.setUserId(sysUser.getUserId());
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
    @RequestMapping(value = "/mobile/register/sendcode/{userName}", method = RequestMethod.GET)
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

        String ret = DxtonSMSSend.sendVerifyCode(userName, verifyCode);
        if (!ret.contains("100")) {
            return new ResponseBean(false, ResponseBean.FAILED, DxtonSMSSend.getResponseText(ret.trim()), null);
        }
        xeVerifyCode.setState("1");
        if (xeVerifyCode.getId() != null) {
            xeVerifyCodeService.update(xeVerifyCode);
        } else {
            xeVerifyCodeService.save(xeVerifyCode);
        }

        return new ResponseBean(false, ResponseBean.SUCCESS, null, DxtonSMSSend.getResponseText(ret.trim()));
    }

    @ResponseBody
    @IgnoreAuth
    @RequestMapping(value = "/mobile/forget/sendcode/{userName}", method = RequestMethod.GET)
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

        String ret = DxtonSMSSend.sendVerifyCode(userName, verifyCode);

        if (!ret.contains("100")) {
            return new ResponseBean(false, ResponseBean.FAILED, DxtonSMSSend.getResponseText(ret.trim()), null);
        }
        xeVerifyCode.setState("1");
        if (xeVerifyCode.getId() != null) {
            xeVerifyCodeService.update(xeVerifyCode);
        } else {
            xeVerifyCodeService.save(xeVerifyCode);
        }

        return new ResponseBean(false, ResponseBean.SUCCESS, null, DxtonSMSSend.getResponseText(ret.trim()));
    }

    @ResponseBody
    @IgnoreAuth
    @RequestMapping(value = "/mobile/forget", method = RequestMethod.POST)
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
    @RequestMapping(value = "/mobile/logout/{userId}", method = RequestMethod.GET)
    public ResponseBean logout(@PathVariable("userId") Long userId) {
        SysUser sysUser = userService.queryObject(userId);
        sysUser.setDeviceId("");
        sysUser.setIsOnline("0");
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("pushToken", "");
        params.put("isOnline", "0");
        userService.updateSession(params);
        return new ResponseBean(false, ResponseBean.SUCCESS, null, null);
    }

    @ResponseBody
    @RequestMapping(value = "/mobile/checkLogin", method = RequestMethod.POST)
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
}
