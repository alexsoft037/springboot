package xin.xiaoer.common.utils;

import com.alibaba.fastjson.JSONObject;
import io.swagger.models.auth.In;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.web.client.RestTemplate;
import xin.xiaoer.entity.SysUser;
import xin.xiaoer.modules.chat.entity.ChatGroup;
import xin.xiaoer.modules.chat.entity.ChatGroupUser;
import xin.xiaoer.modules.chat.entity.ChatUser;
import xin.xiaoer.modules.chat.service.ChatGroupService;
import xin.xiaoer.modules.chat.service.ChatGroupUserService;
import xin.xiaoer.modules.chat.service.ChatUserService;
import xin.xiaoer.modules.space.entity.Space;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class LiveRoomUtil {
    public static final String USER_PREF="user";

    private String iLiveAuthServer="";

    private String appid="";

    public LiveRoomUtil(String iLiveAuthServer, String appid){
        this.iLiveAuthServer = iLiveAuthServer;
        this.appid = appid;
    }

    //由用户信息创建，将直播服务器信息以json形式返回
    public com.alibaba.fastjson.JSONObject createUser(SysUser user) throws Exception {

        DefaultHttpClient httpClient = new DefaultHttpClient();
        String url = iLiveAuthServer + "/index.php?svc=account&cmd=regist";
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-Type", "application/json;charset=utf-8");

        com.alibaba.fastjson.JSONObject data = new JSONObject();
        data.put("id", USER_PREF + user.getUserId());
        data.put("pwd", user.getPassword());
        StringEntity params = new StringEntity(data.toString());
        httpPost.setEntity(params);

        // 执行请求
        HttpResponse response = httpClient.execute(httpPost);

        com.alibaba.fastjson.JSONObject json = com.alibaba.fastjson.JSONObject.parseObject(EntityUtils.toString(response.getEntity(), "utf-8"));
        String code = json.getString("errorCode");
        if (code.equals("0")){
        } else {
            throw new RRException(json.get("errorInfo").toString());
        }

        return json;
    }

    //由用户信息登录，将直播服务器信息以json形式返回
    public com.alibaba.fastjson.JSONObject getUserSig(SysUser user) throws Exception {

        DefaultHttpClient httpClient = new DefaultHttpClient();
        String url = iLiveAuthServer + "/index.php?svc=account&cmd=login";
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-Type", "application/json;charset=utf-8");

        com.alibaba.fastjson.JSONObject data = new JSONObject();
        data.put("id", USER_PREF + user.getUserId());
        data.put("pwd", user.getPassword());
        data.put("appid", appid);
        StringEntity params = new StringEntity(data.toString());
        httpPost.setEntity(params);
        // 执行请求
        HttpResponse response = httpClient.execute(httpPost);

        com.alibaba.fastjson.JSONObject json = com.alibaba.fastjson.JSONObject.parseObject(EntityUtils.toString(response.getEntity(), "utf-8"));
        String code = json.getString("errorCode");
        if (code.equals("0")){
        } else {
            throw new RRException(json.get("errorInfo").toString());
        }

        return json;
    }

    //返回直播间信息
    public com.alibaba.fastjson.JSONObject getRoomList(String token, String index, String size) throws Exception {

        DefaultHttpClient httpClient = new DefaultHttpClient();
        String url = iLiveAuthServer + "/index.php?svc=live&cmd=roomlist";
        HttpPost httpPost = new HttpPost(url);
        httpPost.addHeader("Content-Type", "application/json;charset=utf-8");

        com.alibaba.fastjson.JSONObject data = new JSONObject();
        data.put("token", token);
        data.put("type", "live");
        data.put("index", Integer.parseInt(index));
        data.put("size", Integer.parseInt(size));
        data.put("appid", appid);
        StringEntity params = new StringEntity(data.toString());
        httpPost.setEntity(params);
        // 执行请求
        HttpResponse response = httpClient.execute(httpPost);

        com.alibaba.fastjson.JSONObject json = com.alibaba.fastjson.JSONObject.parseObject(EntityUtils.toString(response.getEntity(), "utf-8"));
        String code = json.getString("errorCode");
        if (code.equals("0")){
        } else {
            throw new RRException(json.get("errorInfo").toString());
        }

        return json;
    }
}
