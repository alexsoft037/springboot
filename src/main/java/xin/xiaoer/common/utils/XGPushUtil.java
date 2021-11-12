package xin.xiaoer.common.utils;

import com.tencent.xinge.Message;
import com.tencent.xinge.XingeApp;
import org.json.JSONException;
import org.json.JSONObject;
import xin.xiaoer.entity.SysUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class XGPushUtil {
    private XingeApp xinge;
    private int oneDayTime = 86400;
    private Long accessId = Constant.PushAccessId;
    private String secretKey = Constant.PushSecretKey;

    public XGPushUtil() {
        xinge = new XingeApp(accessId, secretKey);
    }

    public JSONObject pushSingleAccount(SysUser account, String title, String content) {
        Message message = new Message();
        message.setTitle(title);
        message.setContent(content);
        message.setType(Message.TYPE_NOTIFICATION);
        message.setExpireTime(oneDayTime*3);
        JSONObject ret = xinge.pushSingleAccount(0, "user"+account.getUserId(), message);
        return ret;
    }

    public JSONObject pushSingleDevice(String token, String title, String content) {
        Message message = new Message();
        message.setTitle(title);
        message.setContent(content);
        message.setType(Message.TYPE_NOTIFICATION);
        message.setExpireTime(oneDayTime*3);
        JSONObject ret = xinge.pushSingleDevice(token, message);
        return ret;
    }

    public JSONObject pushLogout(SysUser sysUser) {
        Message message = new Message();
        message.setTitle("PUSH_LOGOUT");
        message.setContent("您已从其他设备登录");
        message.setType(Message.TYPE_MESSAGE);
        message.setExpireTime(60);
        JSONObject ret = xinge.pushSingleDevice(sysUser.getPushToken(), message);
        return ret;
    }

    //下发多个账号
    public JSONObject pushAccountList(List<String> accountList, String title, String content) {
        Message message = new Message();
        message.setExpireTime(oneDayTime*3);
        message.setTitle(title);
        message.setContent(content);
        message.setType(Message.TYPE_NOTIFICATION);
        JSONObject ret = xinge.pushAccountList(0, accountList, message);
        return ret;
    }
}
