package xin.xiaoer.modules.website.service;

import com.alibaba.fastjson.JSONObject;
import xin.xiaoer.modules.mobile.bean.ResponseBean;

/**
 *
 *
 * @author daimingjian
 * @email 3088393266@qq.com
 * @date 2019-01-29
 */
public interface AuthService {

    ResponseBean getAccessToken(String code);

    ResponseBean getOpenId(String accessToken);

    JSONObject getUserInfo(String accessToken, String openId);
}
