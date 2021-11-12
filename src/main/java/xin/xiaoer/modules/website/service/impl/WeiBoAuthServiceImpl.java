package xin.xiaoer.modules.website.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.HtmlUtils;
import xin.xiaoer.common.oss.CloudStorageService;
import xin.xiaoer.common.oss.OSSFactory;
import xin.xiaoer.dao.FileDao;
import xin.xiaoer.entity.File;
import xin.xiaoer.modules.mobile.bean.ResponseBean;
import xin.xiaoer.modules.sysusersns.dao.SysUserSnsDao;
import xin.xiaoer.modules.sysusersns.entity.SysUserSns;
import xin.xiaoer.modules.website.service.WeiBoAuthService;

import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;

/**
 * @ClassName WeiBoAuthServiceImpl
 * @Description TODO
 * @Author XiaoDong
 **/
@Service("WeiBoService")
public class WeiBoAuthServiceImpl extends AuthServiceImpl implements WeiBoAuthService {
    //获取授权
    private final static String ACCESS_TOKEN_URL = "https://api.weibo.com/oauth2/access_token";

    //    //获取openid
//    private static final String OPEN_ID_URL = "https://graph.qq.com/oauth2.0/me?access_token=%s";
//
//    //获取用户信息
    private static final String USER_INFO_URL = "https://api.weibo.com/2/users/show.json?access_token=%s&uid=%s";

    //登陆成功后回调方法
    private static final String CALLBACK_URL = "http://srkj.guoshanchina.com/oauth/callback/type/weibo";

    @Value("${weibo.appId}")
    private String appId;

    @Value("${weibo.appKey}")
    private String appKey;

    @Autowired
    private FileDao fileDao;

    @Autowired
    private SysUserSnsDao sysUserSnsDao;


    @Override
    public ResponseBean getAccessToken(String code) {
        RestTemplate restTemplate = new RestTemplate();
        LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", appId);
        map.add("client_secret", appKey);
        map.add("grant_type", "authorization_code");
        map.add("code", code);
        map.add("redirect_uri", CALLBACK_URL);
        String result = null;
        try {
            result = restTemplate.postForObject(ACCESS_TOKEN_URL, map, String.class);
        } catch (Exception e) {
            return new ResponseBean(false, ResponseBean.FAILED, null, 0);
        }
        return new ResponseBean(false, ResponseBean.SUCCESS, null, result);
    }

    @Override
    public ResponseBean getOpenId(String accessToken) {
        return null;
    }

    @Override
    public JSONObject getUserInfo(String accessToken, String openId) {
        String url = String.format(USER_INFO_URL, accessToken, openId);
        RestTemplate restTemplate = new RestTemplate();
        String forObject = restTemplate.getForObject(url, String.class);
        JSONObject jsonObject = JSON.parseObject(forObject);
        return jsonObject;
    }

    @Override
    public void update(SysUserSns weiboSns, String weiboToken, JSONObject weiboUserInfo) {
        weiboSns.setAccessToken(weiboToken);
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
        CloudStorageService cloudStorageService = OSSFactory.build();//上传保存头像
        String imageId = UUID.randomUUID().toString();
        try {
            URL weixinUrl = new URL(HtmlUtils.htmlUnescape(weiboUserInfo.getString("profile_image_url")));
            URLConnection urlConnection = weixinUrl.openConnection();
            String weiboImageUrl = cloudStorageService.uploadSuffix(urlConnection.getInputStream(), ".jpg");
            File imageFile = new File();
            imageFile.setUploadId(imageId);
            imageFile.setUrl(weiboImageUrl);
            imageFile.setFileName(weiboSns.getUserName() + "_avatar.jpg");
            imageFile.setOssYn("Y");
            imageFile.setFileType("image");
            imageFile.setFileSize(Integer.toString(urlConnection.getContentLength()));
            fileDao.save(imageFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        weiboSns.setAvatar(imageId);
        sysUserSnsDao.update(weiboSns);
    }
}
