package xin.xiaoer.modules.website.service;

import com.alibaba.fastjson.JSONObject;
import xin.xiaoer.modules.sysusersns.entity.SysUserSns;

public interface WeiBoAuthService extends AuthService {
    void update(SysUserSns weiboSns, String weiboToken, JSONObject weiboUserInfo);
}
