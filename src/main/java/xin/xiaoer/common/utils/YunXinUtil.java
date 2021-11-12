package xin.xiaoer.common.utils;

import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.web.client.RestTemplate;
import xin.xiaoer.entity.PushAdmin;
import xin.xiaoer.entity.SysUser;
import xin.xiaoer.modules.chat.entity.ChatGroup;
import xin.xiaoer.modules.chat.entity.ChatGroupUser;
import xin.xiaoer.modules.chat.entity.ChatUser;
import xin.xiaoer.modules.chat.service.ChatGroupService;
import xin.xiaoer.modules.chat.service.ChatGroupUserService;
import xin.xiaoer.modules.chat.service.ChatUserService;
import xin.xiaoer.modules.space.entity.Space;
import xin.xiaoer.service.SysConfigService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class YunXinUtil {
    //网易云信分配的密钥，请替换你在管理后台应用下申请的appSecret
    public static final String APP_SECRET="64bccaa7dfae";

    //网易云信分配的账号，请替换你在管理后台应用下申请的Appkey
    public static final String APP_KEY="aeca7c075400e50e87738a5dbb6eca80";

    //随机数
    public static final String NONCE="123456";

    //短信模板ID
    public static final String TEMPLATEID="3119062";

    //验证码长度，范围4～10，默认为4
    public static final String CODELEN="6";

    private static String INVITE_STRING = "您已被添加到测试组";

    private static String GROUP_OWNER = "xiaoer";

    private static RestTemplate restTemplate = new RestTemplate();

    private static ChatUserService chatUserService;

    private static ChatGroupService chatGroupService;

    private static ChatGroupUserService chatGroupUserService;

    private static SysConfigService sysConfigService;

    private PushAdmin pushAdmin;

    static {
        YunXinUtil.chatUserService = (ChatUserService) SpringContextUtils.getBean("chatUserService");
        YunXinUtil.chatGroupService = (ChatGroupService) SpringContextUtils.getBean("chatGroupService");
        YunXinUtil.chatGroupUserService = (ChatGroupUserService) SpringContextUtils.getBean("chatGroupUserService");
        YunXinUtil.sysConfigService = (SysConfigService) SpringContextUtils.getBean("sysConfigService");
    }

    public PushAdmin getPushAdmin() {
        pushAdmin = sysConfigService.getConfigClassObject(ConfigConstant.PUSH_ADMIN_CONFIG_KEY, PushAdmin.class);
        if (pushAdmin.getToken() == null){
            try {
                this.createAdminUser();
            } catch (Exception e){
            }
        }
        return pushAdmin;
    }

    public void setPushAdmin(PushAdmin pushAdmin) {
        this.pushAdmin = pushAdmin;
    }

    public com.alibaba.fastjson.JSONObject createUser(SysUser user) throws Exception {

        DefaultHttpClient httpClient = new DefaultHttpClient();
        String url = "https://api.netease.im/nimserver/user/create.action";
        HttpPost httpPost = new HttpPost(url);

        String appKey = APP_KEY;
        String appSecret = APP_SECRET;
        String nonce =  NONCE;
        String curTime = String.valueOf((new Date()).getTime() / 1000L);
        String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce ,curTime);//参考 计算CheckSum的java代码

        // 设置请求的header
        httpPost.addHeader("AppKey", appKey);
        httpPost.addHeader("Nonce", nonce);
        httpPost.addHeader("CurTime", curTime);
        httpPost.addHeader("CheckSum", checkSum);
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        String accid = generateAccid();
        // 设置请求的参数
        String nickName;
        if (StringUtility.isEmpty(user.getNickname())){
            nickName = user.getPhoneNo();
        } else {
            nickName = user.getNickname();
        }
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("accid", accid));
        nvps.add(new BasicNameValuePair("name", nickName));
        nvps.add(new BasicNameValuePair("icon", user.getAvatar()));
        nvps.add(new BasicNameValuePair("email", user.getEmail()));
        nvps.add(new BasicNameValuePair("mobile", user.getPhoneNo()));
        nvps.add(new BasicNameValuePair("gender", user.getGender()));
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));

        // 执行请求
        HttpResponse response = httpClient.execute(httpPost);

        com.alibaba.fastjson.JSONObject json = com.alibaba.fastjson.JSONObject.parseObject(EntityUtils.toString(response.getEntity(), "utf-8"));
        String code = json.getString("code");
        if (code.equals("200")){
            String token = json.getJSONObject("info").getString("token");
            ChatUser chatUser = new ChatUser();
            chatUser.setUserId(user.getUserId());
            chatUser.setToken(token);
            chatUser.setState("1");
            chatUser.setAccid(accid);

            chatUserService.save(chatUser);
        } else {
            throw new RRException(json.get("code").toString());
        }

        return json;
    }

    public com.alibaba.fastjson.JSONObject createAdminUser() throws Exception {

        DefaultHttpClient httpClient = new DefaultHttpClient();
        String url = "https://api.netease.im/nimserver/user/create.action";
        HttpPost httpPost = new HttpPost(url);

        String appKey = APP_KEY;
        String appSecret = APP_SECRET;
        String nonce =  NONCE;
        String curTime = String.valueOf((new Date()).getTime() / 1000L);
        String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce ,curTime);//参考 计算CheckSum的java代码

        // 设置请求的header
        httpPost.addHeader("AppKey", appKey);
        httpPost.addHeader("Nonce", nonce);
        httpPost.addHeader("CurTime", curTime);
        httpPost.addHeader("CheckSum", checkSum);
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        String accid = generateAccid();
        // 设置请求的参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("accid", accid));
        nvps.add(new BasicNameValuePair("name", ConfigConstant.XIAOER_PUSH_ADMIN));
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));

        // 执行请求
        HttpResponse response = httpClient.execute(httpPost);

        com.alibaba.fastjson.JSONObject json = com.alibaba.fastjson.JSONObject.parseObject(EntityUtils.toString(response.getEntity(), "utf-8"));
        String code = json.getString("code");
        if (code.equals("200")) {
            String token = json.getJSONObject("info").getString("token");
            accid = json.getJSONObject("info").getString("accid");
            String name = json.getJSONObject("info").getString("name");
            pushAdmin.setToken(token);
            pushAdmin.setAccid(accid);
            pushAdmin.setName(name);
            sysConfigService.updateValueByKey(ConfigConstant.PUSH_ADMIN_CONFIG_KEY, new Gson().toJson(pushAdmin));
        } else {
            throw new RRException(json.get("code").toString());
        }

        return json;
    }

    public com.alibaba.fastjson.JSONObject updateUinfo(SysUser user, ChatUser chatUser) throws Exception {

        DefaultHttpClient httpClient = new DefaultHttpClient();
        String url = "https://api.netease.im/nimserver/user/updateUinfo.action";
        HttpPost httpPost = new HttpPost(url);

        String appKey = APP_KEY;
        String appSecret = APP_SECRET;
        String nonce =  NONCE;
        String curTime = String.valueOf((new Date()).getTime() / 1000L);
        String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce ,curTime);//参考 计算CheckSum的java代码

        // 设置请求的header
        httpPost.addHeader("AppKey", appKey);
        httpPost.addHeader("Nonce", nonce);
        httpPost.addHeader("CurTime", curTime);
        httpPost.addHeader("CheckSum", checkSum);
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        // 设置请求的参数
        String nickName;
        if (StringUtility.isEmpty(user.getNickname())){
            nickName = user.getPhoneNo();
        } else {
            nickName = user.getNickname();
        }
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("accid", chatUser.getAccid()));
        nvps.add(new BasicNameValuePair("name", nickName));
        nvps.add(new BasicNameValuePair("icon", user.getAvatar()));
        nvps.add(new BasicNameValuePair("email", user.getEmail()));
        nvps.add(new BasicNameValuePair("mobile", user.getPhoneNo()));
        nvps.add(new BasicNameValuePair("gender", user.getGender()));
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));

        // 执行请求
        HttpResponse response = httpClient.execute(httpPost);

        com.alibaba.fastjson.JSONObject json = com.alibaba.fastjson.JSONObject.parseObject(EntityUtils.toString(response.getEntity(), "utf-8"));
        String code = json.getString("code");
        if (code.equals("200")){
        } else {
            throw new RRException(json.get("code").toString());
        }

        return json;
    }

    private String generateAccid(){
        String tenChar = RandomCharUtil.generateUUID();
        tenChar = tenChar.substring(0,28);
        return tenChar;
    }

    public com.alibaba.fastjson.JSONObject createGroup(Space space, ChatUser chatUser) throws Exception {

        String members = "[\"" + chatUser.getAccid() + "\"]";
        DefaultHttpClient httpClient = new DefaultHttpClient();
        String url = "https://api.netease.im/nimserver/team/create.action";
        HttpPost httpPost = new HttpPost(url);

        String appKey = APP_KEY;
        String appSecret = APP_SECRET;
        String nonce =  NONCE;
        String curTime = String.valueOf((new Date()).getTime() / 1000L);
        String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce ,curTime);//参考 计算CheckSum的java代码

        // 设置请求的header
        httpPost.addHeader("AppKey", appKey);
        httpPost.addHeader("Nonce", nonce);
        httpPost.addHeader("CurTime", curTime);
        httpPost.addHeader("CheckSum", checkSum);
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        // 设置请求的参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("tname", space.getSpaceName()+"群"));
        nvps.add(new BasicNameValuePair("owner", chatUser.getAccid()));
        nvps.add(new BasicNameValuePair("members", members));
        nvps.add(new BasicNameValuePair("msg", INVITE_STRING));
        nvps.add(new BasicNameValuePair("magree", "0"));
        nvps.add(new BasicNameValuePair("joinmode", "0"));
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));

        // 执行请求
        HttpResponse response = httpClient.execute(httpPost);

        com.alibaba.fastjson.JSONObject json = com.alibaba.fastjson.JSONObject.parseObject(EntityUtils.toString(response.getEntity(), "utf-8"));
        String code = json.getString("code");
        if (code.equals("200")){
            String tid = json.getString("tid");
            ChatGroup chatGroup = new ChatGroup();
            chatGroup.setSpaceId(space.getSpaceId());
            chatGroup.setOwner(chatUser.getAccid());
            chatGroup.setGroupName(space.getSpaceName()+"空间群");
            chatGroup.setTid(tid);
            chatGroup.setState("1");

            chatGroupService.save(chatGroup);
        } else {
            throw new RRException(json.get("code").toString());
        }

        return json;
    }

    public com.alibaba.fastjson.JSONObject updateGroup(ChatGroup chatGroup) throws Exception {

        DefaultHttpClient httpClient = new DefaultHttpClient();
        String url = "https://api.netease.im/nimserver/team/update.action";
        HttpPost httpPost = new HttpPost(url);

        String appKey = APP_KEY;
        String appSecret = APP_SECRET;
        String nonce =  NONCE;
        String curTime = String.valueOf((new Date()).getTime() / 1000L);
        String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce ,curTime);//参考 计算CheckSum的java代码

        // 设置请求的header
        httpPost.addHeader("AppKey", appKey);
        httpPost.addHeader("Nonce", nonce);
        httpPost.addHeader("CurTime", curTime);
        httpPost.addHeader("CheckSum", checkSum);
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        // 设置请求的参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("tid", chatGroup.getTid()));
        nvps.add(new BasicNameValuePair("tname", chatGroup.getGroupName()));
        nvps.add(new BasicNameValuePair("owner", chatGroup.getOwner()));
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));

        // 执行请求
        HttpResponse response = httpClient.execute(httpPost);

        com.alibaba.fastjson.JSONObject json = com.alibaba.fastjson.JSONObject.parseObject(EntityUtils.toString(response.getEntity(), "utf-8"));
        String code = json.getString("code");
        if (code.equals("200")){
        } else {
            throw new RRException(json.get("code").toString());
        }

        return json;
    }

    public com.alibaba.fastjson.JSONObject addGroup(ChatGroup chatGroup, ChatUser user) throws Exception {

        DefaultHttpClient httpClient = new DefaultHttpClient();
        String url = "https://api.netease.im/nimserver/team/add.action";
        HttpPost httpPost = new HttpPost(url);

        String appKey = APP_KEY;
        String appSecret = APP_SECRET;
        String nonce =  NONCE;
        String curTime = String.valueOf((new Date()).getTime() / 1000L);
        String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce ,curTime);//参考 计算CheckSum的java代码

        // 设置请求的header
        httpPost.addHeader("AppKey", appKey);
        httpPost.addHeader("Nonce", nonce);
        httpPost.addHeader("CurTime", curTime);
        httpPost.addHeader("CheckSum", checkSum);
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        // 设置请求的参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("tid", chatGroup.getTid()));
        nvps.add(new BasicNameValuePair("owner", chatGroup.getOwner()));
        nvps.add(new BasicNameValuePair("members", "[\"" + user.getAccid() + "\"]"));
        nvps.add(new BasicNameValuePair("msg", INVITE_STRING));
        nvps.add(new BasicNameValuePair("magree", "0"));
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));

        // 执行请求
        HttpResponse response = httpClient.execute(httpPost);

        com.alibaba.fastjson.JSONObject json = com.alibaba.fastjson.JSONObject.parseObject(EntityUtils.toString(response.getEntity(), "utf-8"));
        String code = json.getString("code");
        if (code.equals("200")){
            ChatGroupUser chatGroupUser = new ChatGroupUser();
            chatGroupUser.setSpaceId(chatGroup.getSpaceId());
            chatGroupUser.setUserId(user.getUserId());

            chatGroupUserService.save(chatGroupUser);
        } else {
            throw new RRException(json.get("code").toString());
        }

        return json;
    }

    public com.alibaba.fastjson.JSONObject sendMessage(ChatUser from, ChatUser to, String message) throws Exception {

        DefaultHttpClient httpClient = new DefaultHttpClient();
        String url = "https://api.netease.im/nimserver/msg/sendMsg.action";
        HttpPost httpPost = new HttpPost(url);

        String appKey = APP_KEY;
        String appSecret = APP_SECRET;
        String nonce =  NONCE;
        String curTime = String.valueOf((new Date()).getTime() / 1000L);
        String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce ,curTime);//参考 计算CheckSum的java代码

        // 设置请求的header
        httpPost.addHeader("AppKey", appKey);
        httpPost.addHeader("Nonce", nonce);
        httpPost.addHeader("CurTime", curTime);
        httpPost.addHeader("CheckSum", checkSum);
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        // 设置请求的参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("from", from.getAccid()));
        nvps.add(new BasicNameValuePair("ope", "0"));
        nvps.add(new BasicNameValuePair("to", to.getAccid()));
        nvps.add(new BasicNameValuePair("type", "0"));
        nvps.add(new BasicNameValuePair("option", "{\"push\":true}"));
        nvps.add(new BasicNameValuePair("pushcontent", message));
        nvps.add(new BasicNameValuePair("body", "{\"msg\":\"" + message +"\"}"));
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));

        // 执行请求
        HttpResponse response = httpClient.execute(httpPost);

        com.alibaba.fastjson.JSONObject json = com.alibaba.fastjson.JSONObject.parseObject(EntityUtils.toString(response.getEntity(), "utf-8"));
        String code = json.getString("code");
        if (code.equals("200")){
        } else {
            throw new RRException(json.get("code").toString());
        }

        return json;
    }

    public com.alibaba.fastjson.JSONObject sendSinglePush(Long userId, String message) throws Exception {
        this.getPushAdmin();
        ChatUser chatUser = chatUserService.get(userId);
        DefaultHttpClient httpClient = new DefaultHttpClient();
        String url = "https://api.netease.im/nimserver/msg/sendMsg.action";
        HttpPost httpPost = new HttpPost(url);

        String appKey = APP_KEY;
        String appSecret = APP_SECRET;
        String nonce =  NONCE;
        String curTime = String.valueOf((new Date()).getTime() / 1000L);
        String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce ,curTime);//参考 计算CheckSum的java代码

        // 设置请求的header
        httpPost.addHeader("AppKey", appKey);
        httpPost.addHeader("Nonce", nonce);
        httpPost.addHeader("CurTime", curTime);
        httpPost.addHeader("CheckSum", checkSum);
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        // 设置请求的参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("from", pushAdmin.getAccid()));
        nvps.add(new BasicNameValuePair("ope", "0"));
        nvps.add(new BasicNameValuePair("to", chatUser.getAccid()));
        nvps.add(new BasicNameValuePair("type", "0"));
        nvps.add(new BasicNameValuePair("option", "{\"push\":true}"));
        nvps.add(new BasicNameValuePair("pushcontent", message));
        nvps.add(new BasicNameValuePair("body", "{\"msg\":\"" + message +"\"}"));
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));

        // 执行请求
        HttpResponse response = httpClient.execute(httpPost);

        com.alibaba.fastjson.JSONObject json = com.alibaba.fastjson.JSONObject.parseObject(EntityUtils.toString(response.getEntity(), "utf-8"));
        String code = json.getString("code");
        if (code.equals("200")){
        } else {
            throw new RRException(json.get("code").toString());
        }

        return json;
    }

    public com.alibaba.fastjson.JSONObject sendBatchPush(List<String> toAccids, String message) throws Exception {
        this.getPushAdmin();
        DefaultHttpClient httpClient = new DefaultHttpClient();
        String url = "https://api.netease.im/nimserver/msg/sendBatchMsg.action";
        HttpPost httpPost = new HttpPost(url);

        String appKey = APP_KEY;
        String appSecret = APP_SECRET;
        String nonce =  NONCE;
        String curTime = String.valueOf((new Date()).getTime() / 1000L);
        String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce ,curTime);//参考 计算CheckSum的java代码

        // 设置请求的header
        httpPost.addHeader("AppKey", appKey);
        httpPost.addHeader("Nonce", nonce);
        httpPost.addHeader("CurTime", curTime);
        httpPost.addHeader("CheckSum", checkSum);
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        // 设置请求的参数
        String accids = "[\"";
        accids += String.join("\",\"",toAccids);
        accids += "\"]";
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("fromAccid", pushAdmin.getAccid()));
        nvps.add(new BasicNameValuePair("toAccids", accids));
        nvps.add(new BasicNameValuePair("type", "0"));
        nvps.add(new BasicNameValuePair("option", "{\"push\":true}"));
        nvps.add(new BasicNameValuePair("pushcontent", message));
        nvps.add(new BasicNameValuePair("body", "{\"msg\":\"" + message +"\"}"));
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));

        // 执行请求
        HttpResponse response = httpClient.execute(httpPost);

        com.alibaba.fastjson.JSONObject json = com.alibaba.fastjson.JSONObject.parseObject(EntityUtils.toString(response.getEntity(), "utf-8"));
        String code = json.getString("code");
        if (code.equals("200")) {
        } else {
            throw new RRException(json.get("code").toString());
        }

        return json;
    }

    public com.alibaba.fastjson.JSONObject queryGroupDetail(ChatGroup chatGroup) throws Exception {

        DefaultHttpClient httpClient = new DefaultHttpClient();
        String url = "https://api.netease.im/nimserver/team/queryDetail.action";
        HttpPost httpPost = new HttpPost(url);

        String appKey = APP_KEY;
        String appSecret = APP_SECRET;
        String nonce =  NONCE;
        String curTime = String.valueOf((new Date()).getTime() / 1000L);
        String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce ,curTime);//参考 计算CheckSum的java代码

        // 设置请求的header
        httpPost.addHeader("AppKey", appKey);
        httpPost.addHeader("Nonce", nonce);
        httpPost.addHeader("CurTime", curTime);
        httpPost.addHeader("CheckSum", checkSum);
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        // 设置请求的参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("tid", chatGroup.getTid()));
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));

        // 执行请求
        HttpResponse response = httpClient.execute(httpPost);

        com.alibaba.fastjson.JSONObject json = com.alibaba.fastjson.JSONObject.parseObject(EntityUtils.toString(response.getEntity(), "utf-8"));
        String code = json.getString("code");
        if (code.equals("200")){
        } else {
            throw new RRException(json.get("code").toString());
        }
        return json;
    }

    public com.alibaba.fastjson.JSONObject subscribeQuery(ChatUser from, ChatUser to) throws Exception {

        DefaultHttpClient httpClient = new DefaultHttpClient();
        String url = "https://api.netease.im/nimserver/event/subscribe/query.action";
//        String url = "https://api.netease.im/nimserver/event/subscribe/add.action";
        HttpPost httpPost = new HttpPost(url);

        String appKey = APP_KEY;
        String appSecret = APP_SECRET;
        String nonce =  NONCE;
        String curTime = String.valueOf((new Date()).getTime() / 1000L);
        String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce ,curTime);//参考 计算CheckSum的java代码

        // 设置请求的header
        httpPost.addHeader("AppKey", appKey);
        httpPost.addHeader("Nonce", nonce);
        httpPost.addHeader("CurTime", curTime);
        httpPost.addHeader("CheckSum", checkSum);
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        // 设置请求的参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("accid", from.getAccid()));
        nvps.add(new BasicNameValuePair("eventType", "1"));
        nvps.add(new BasicNameValuePair("ttl", "2592000"));
        nvps.add(new BasicNameValuePair("publisherAccids", "[\"" + to.getAccid() + "\"]"));
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));

        // 执行请求
        HttpResponse response = httpClient.execute(httpPost);

        com.alibaba.fastjson.JSONObject json = com.alibaba.fastjson.JSONObject.parseObject(EntityUtils.toString(response.getEntity(), "utf-8"));
        String code = json.getString("code");
        if (code.equals("200")){
        } else {
            return json;
//            throw new RRException(json.get("code").toString());
        }

        return json;
    }

    /**
     * @return com.alibaba.fastjson.JSONObject
     * @Description TODO 禁用云信账户，临时用一下
     * @Param [userId, needKick]
     * @Author XiaoDong
     **/
    public com.alibaba.fastjson.JSONObject blockUser(String accid, String needKick) throws Exception {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        String url = "https://api.netease.im/nimserver/user/block.action";
        HttpPost httpPost = new HttpPost(url);

        String appKey = APP_KEY;
        String appSecret = APP_SECRET;
        String nonce =  NONCE;
        String curTime = String.valueOf((new Date()).getTime() / 1000L);
        String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce ,curTime);//参考 计算CheckSum的java代码

        // 设置请求的header
        httpPost.addHeader("AppKey", appKey);
        httpPost.addHeader("Nonce", nonce);
        httpPost.addHeader("CurTime", curTime);
        httpPost.addHeader("CheckSum", checkSum);
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        // 设置请求的参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("accid", accid));
        nvps.add(new BasicNameValuePair("needkick", needKick));
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));

        // 执行请求
        HttpResponse response = httpClient.execute(httpPost);

        com.alibaba.fastjson.JSONObject json = com.alibaba.fastjson.JSONObject.parseObject(EntityUtils.toString(response.getEntity(), "utf-8"));
        String code = json.getString("code");
        if (code.equals("200")){
        } else {
            throw new RRException(json.get("code").toString());
        }

        return json;
    }
}
