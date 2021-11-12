package xin.xiaoer.modules.website.service;

import com.alibaba.fastjson.JSONObject;
import xin.xiaoer.modules.sysusersns.entity.SysUserSns;

public interface WeiXinAuthService extends AuthService {
    void update(SysUserSns weixinSns, String weixinToken, JSONObject weixinUserInfo);
}
