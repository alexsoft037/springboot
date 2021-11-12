package xin.xiaoer.modules.website.service.impl;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import xin.xiaoer.common.oss.CloudStorageService;
import xin.xiaoer.common.oss.OSSFactory;
import xin.xiaoer.dao.FileDao;
import xin.xiaoer.entity.File;
import xin.xiaoer.modules.mobile.bean.ResponseBean;
import xin.xiaoer.modules.sysusersns.dao.SysUserSnsDao;
import xin.xiaoer.modules.sysusersns.entity.SysUserSns;
import xin.xiaoer.modules.website.service.WeiXinAuthService;

import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;

/**
 * @ClassName WeiXinAuthServiceImpl
 * @Description TODO
 * @Author XiaoDong
 **/
@Service("WeiXinAuthService")
public class WeiXinAuthServiceImpl extends AuthServiceImpl implements WeiXinAuthService {

    //获取授权
    private final static String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";

    //获取用户信息
    private static final String USER_INFO_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s";

    //登陆成功后回调方法
    private static final String CALLBACK_URL = "http://srkj.guoshanchina.com/oauth/callback/type/weixin";

    @Value("${weixin.appId}")
    private String appId;

    @Value("${weixin.appKey}")
    private String appKey;

    @Autowired
    private FileDao fileDao;

    @Autowired
    private SysUserSnsDao sysUserSnsDao;

    @Override
    public ResponseBean getAccessToken(String code) {
        String url = String.format(ACCESS_TOKEN_URL, appId, appKey, code);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        URI uri = builder.build().encode().toUri();

        String resp = getRestTemplate().getForObject(uri, String.class);
        if (resp != null && resp.contains("access_token")) {
            return new ResponseBean(false, ResponseBean.SUCCESS, null, resp);
        }
        return new ResponseBean(false, ResponseBean.FAILED, "code无效！", null);
    }

    @Override
    public ResponseBean getOpenId(String accessToken) {
        return null;
    }

    @Override
    public JSONObject getUserInfo(String accessToken, String openId) {
        String url = String.format(USER_INFO_URL, accessToken, openId);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        URI uri = builder.build().encode().toUri();

        String resp = getRestTemplate().getForObject(uri, String.class);
        JSONObject jsonObject = JSONObject.parseObject(resp);
        return jsonObject;
    }

    @Override
    public void update(SysUserSns weixinSns, String weixinToken, JSONObject weixinUserInfo) {
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
            fileDao.save(imageFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        weixinSns.setAvatar(imageId);
        sysUserSnsDao.update(weixinSns);
    }
}
