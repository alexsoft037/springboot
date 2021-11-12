package xin.xiaoer.modules.website.service.impl;/********************************************************************
 /**
 * @Project: xiaoer
 * @Package xin.xiaoer.modules.website.service.impl
 * @author DaiMingJian
 * @date 2019/1/29 16:37
 * @Copyright: 2019 www.zyht.com Inc. All rights reserved.
 * @version V1.0
 */

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import xin.xiaoer.modules.mobile.bean.ResponseBean;
import xin.xiaoer.modules.website.service.QQAuthService;

import java.net.URI;
import java.util.Map;

/**
 * @author DaiMingJian
 * @ClassName QQAuthServiceImpl
 * @Description 类描述
 * @date 2019/1/29
 */
@Service("QQAuthService")
public class QQAuthServiceImpl extends AuthServiceImpl implements QQAuthService{

    //获取授权
    private final static String ACCESS_TOKEN_URL = "https://graph.qq.com/oauth2.0/token?grant_type=authorization_code&client_id=%s&client_secret=%s&code=%s&redirect_uri=%s";

    //获取openid
    private static final String OPEN_ID_URL = "https://graph.qq.com/oauth2.0/me?access_token=%s&unionid=1";

    //获取用户qq信息
    private static final String USER_INFO_URL = "https://graph.qq.com/user/get_user_info?access_token=%s&oauth_consumer_key=%s&openid=%s";

    //登陆成功后回调方法
    private static final String CALLBACK_URL = "http://srkj.guoshanchina.com/oauth/callback/type/qq";

    @Value("${qq.appId}")
    private String appId;

    @Value("${qq.appKey}")
    private String appKey;

    //访问权限设置
    private static final String SCOPE = "get_user_info";



    @Override
    public ResponseBean getAccessToken(String code) {
        String url=String.format(ACCESS_TOKEN_URL, appId, appKey, code, CALLBACK_URL);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        URI uri = builder.build().encode().toUri();

        String resp = getRestTemplate().getForObject(uri, String.class);
        if (resp != null && resp.contains("access_token")) {
            Map<String, String> map = getParam(resp);
            String access_token = map.get("access_token");
            return new ResponseBean(false, ResponseBean.SUCCESS, null, access_token);
        }
        return new ResponseBean(false,ResponseBean.FAILED,"code无效！",null);
    }

    @Override
    public ResponseBean getOpenId(String accessToken){
        String url=String.format(OPEN_ID_URL,accessToken);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        URI uri = builder.build().encode().toUri();

        String resp = getRestTemplate().getForObject(uri, String.class);
        if (resp != null && resp.contains("unionid")) {
            JSONObject jsonObject = ConvertToJson(resp);
            String openid = jsonObject.getString("unionid");
            return new ResponseBean(false, ResponseBean.SUCCESS, null, openid);
        }
        return new ResponseBean(false,ResponseBean.FAILED,"accessToken无效！",null);
    }

    @Override
    public JSONObject getUserInfo(String accessToken, String openId) {
        String url = String.format(USER_INFO_URL, accessToken, appId, openId);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        URI uri = builder.build().encode().toUri();

        String resp = getRestTemplate().getForObject(uri, String.class);
        JSONObject data = JSONObject.parseObject(resp);
        return data;
    }

}
