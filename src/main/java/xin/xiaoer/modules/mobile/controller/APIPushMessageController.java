package xin.xiaoer.modules.mobile.controller;

import com.google.gson.Gson;
import com.tencent.xinge.Message;
import com.tencent.xinge.XingeApp;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;
import xin.xiaoer.common.utils.YunXinUtil;
import xin.xiaoer.modules.mobile.bean.ResponseBean;
import xin.xiaoer.modules.setting.entity.Follow;
import xin.xiaoer.modules.setting.service.FollowService;
import xin.xiaoer.service.SysUserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("mobile/pushMessage")
@ApiIgnore
public class APIPushMessageController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private FollowService followService;

    private int oneDayTime = 86400;
    private XingeApp xinge = new XingeApp(2100313373, "b177878baf2f3327f001379086d9bbda");

//    @RequestMapping("pushSingle")
//    public ResponseBean pushSingle(@RequestParam Map<String, Object> params) {
//        String title = params.get("title").toString();
//        String content = params.get("content").toString();
//        Message message = new Message();
//        message.setTitle(title);
//        message.setContent(content);
//        message.setType(Message.TYPE_NOTIFICATION);
//        message.setExpireTime(oneDayTime*3);
//        JSONObject ret = xinge.pushSingleAccount(1, "user1", message);
//        String result = ret.toString();
//        return new ResponseBean(false,"success", null, result);
//    }
//
//    @RequestMapping("pushLiveRoom/{userId}")
//    public ResponseBean pushLiveRoom(@PathVariable("userId") Long userId) throws Exception {
//        Map<String, Object> followParams = new HashMap<>();
//        followParams.put("followId", userId);
//        followParams.put("limit", 100);
//        int accountCount = followService.getCount(followParams);
//        List<Follow> follows = followService.getList(followParams);
//        List<String> accountList;
//        Map<String, Object> result = new HashMap<>();
//        YunXinUtil yunXinUtil = new YunXinUtil();
//        int totalPages = (int)Math.ceil((double)accountCount/100);
//        result.put("follows", follows);
////        for (int i = 1; i <= totalPages; i++){
////            followParams.put("page", i);
////            Query query = new Query(followParams);
////            accountList = followService.getAccountListByFollowId(query);
////            com.alibaba.fastjson.JSONObject ret = yunXinUtil.sendBatchPush(accountList, "您的关注的人Name正在发布直播频道。");
////            result.put(Integer.toString(i), ret.toString());
////        }
//
//        return new ResponseBean(false,"success", null, result);
//    }
//
//    @RequestMapping("bachAccids")
//    public ResponseBean bachAccids(@RequestParam Map<String, Object> params) throws Exception {
//        Map<String, Object> followParams = new HashMap<>();
//        followParams.put("followId", 2);
//        followParams.put("limit", 100);
//        List<String> accountList = followService.getAccountListByFollowId(followParams);
//        String accids = new Gson().toJson(accountList);
//        return new ResponseBean(false,"success", null, accids);
//    }
//
//    @RequestMapping("integral/{userId}")
//    public ResponseBean integral(@PathVariable("userId") Long userId) throws Exception {
//        YunXinUtil yunXinUtil = new YunXinUtil();
//        com.alibaba.fastjson.JSONObject ret =  yunXinUtil.sendSinglePush(userId, "获得积分");
//        return new ResponseBean(false,"success", null, ret.toString());
//    }
}
