package xin.xiaoer.modules.mobile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;
import xin.xiaoer.common.utils.YunXinUtil;
import xin.xiaoer.entity.SysUser;
import xin.xiaoer.modules.chat.entity.ChatGroup;
import xin.xiaoer.modules.chat.entity.ChatGroupUser;
import xin.xiaoer.modules.chat.entity.ChatUser;
import xin.xiaoer.modules.chat.service.ChatGroupService;
import xin.xiaoer.modules.chat.service.ChatGroupUserService;
import xin.xiaoer.modules.chat.service.ChatUserService;
import xin.xiaoer.modules.mobile.bean.ResponseBean;
import xin.xiaoer.modules.space.entity.Space;
import xin.xiaoer.modules.space.service.SpaceService;
import xin.xiaoer.service.FileService;
import xin.xiaoer.service.SysUserService;

import java.util.List;

@RestController
@RequestMapping("mobile/chat")
@ApiIgnore
public class APIChatController {

    @Autowired
    private FileService fileService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private ChatUserService chatUserService;

    @Autowired
    private ChatGroupService chatGroupService;

    @Autowired
    private SpaceService spaceService;

    @Autowired
    private ChatGroupUserService chatGroupUserService;

//    @RequestMapping(value = "/create")
//    public ResponseBean create(@RequestParam Map<String, Object> params) {
//
//        YunXinUtil yunXinUtil = new YunXinUtil();
//        SysUser sysUser = sysUserService.queryObject(1L);
//        List<SysUser> userList = sysUserService.queryList(null);
//        for (SysUser user: userList){
//            try {
//                yunXinUtil.createUser(user);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        return new ResponseBean(false,"success", null, null);
//    }
//
//    @RequestMapping(value = "/update")
//    public ResponseBean update(@RequestParam Map<String, Object> params) {
//
//        YunXinUtil yunXinUtil = new YunXinUtil();
//        SysUser sysUser = sysUserService.queryObject(1L);
//        List<SysUser> userList = sysUserService.queryList(null);
//
//        for (SysUser user: userList){
//            try {
//                ChatUser chatUser = chatUserService.get(user.getUserId());
//                yunXinUtil.updateUinfo(user, chatUser);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        return new ResponseBean(false,"success", null, null);
//    }

//    @RequestMapping(value = "/createGroup")
//    public ResponseBean createGroup(@RequestParam Map<String, Object> params) {
//
//        com.alibaba.fastjson.JSONObject jsonObject = new com.alibaba.fastjson.JSONObject();
//        YunXinUtil yunXinUtil = new YunXinUtil();
//        ChatUser admin = chatUserService.get(1L);
//        Space space = spaceService.get(1);
//        try {
//            jsonObject = yunXinUtil.createGroup(space, admin);
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//
//        return new ResponseBean(false,"success", null, jsonObject);
//    }

//    @RequestMapping(value = "/sendMessage")
//    public ResponseBean sendMessage(@RequestParam Map<String, Object> params) {
//
//        com.alibaba.fastjson.JSONObject jsonObject = new com.alibaba.fastjson.JSONObject();
//        YunXinUtil yunXinUtil = new YunXinUtil();
//        ChatUser from = chatUserService.get(1L);
//        ChatUser to = chatUserService.get(30L);
//        Space space = spaceService.get(1);
//        try {
//            jsonObject = yunXinUtil.sendMessage(from, to, "this is a test message");
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//
//        return new ResponseBean(false,"success", null, jsonObject);
//    }

//    @RequestMapping(value = "/subscribeQuery")
//    public ResponseBean subscribeQuery(@RequestParam Map<String, Object> params) {
//
//        com.alibaba.fastjson.JSONObject jsonObject = new com.alibaba.fastjson.JSONObject();
//        YunXinUtil yunXinUtil = new YunXinUtil();
//        ChatUser from = chatUserService.get(1L);
//        ChatUser to = chatUserService.get(30L);
//        Space space = spaceService.get(1);
//        try {
//            jsonObject = yunXinUtil.subscribeQuery(to, from);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return new ResponseBean(false,"success", null, jsonObject);
//    }

//    @RequestMapping(value = "/addGroup")
//    public ResponseBean addGroup(@RequestParam Map<String, Object> params) {
//
//        com.alibaba.fastjson.JSONObject jsonObject = new com.alibaba.fastjson.JSONObject();
//        YunXinUtil yunXinUtil = new YunXinUtil();
//        ChatUser admin = chatUserService.get(1L);
//        ChatGroup chatGroup = chatGroupService.get(1);
//        ChatUser user = chatUserService.get(2L);
//
//        try {
//            jsonObject = yunXinUtil.addGroup(chatGroup, user);
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//
//        return new ResponseBean(false,"success", null, jsonObject);
//    }

    @RequestMapping(value = "/queryGroupDetail/{spaceId}", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseBean queryGroupDetail( @PathVariable("spaceId") Integer spaceId) {

        com.alibaba.fastjson.JSONObject jsonObject = new com.alibaba.fastjson.JSONObject();
        YunXinUtil yunXinUtil = new YunXinUtil();
        ChatGroup chatGroup = chatGroupService.get(spaceId);

        try {
            jsonObject = yunXinUtil.queryGroupDetail(chatGroup);
        } catch (Exception e){
            e.printStackTrace();
        }

        return new ResponseBean(false,"success", null, jsonObject);
    }


    @RequestMapping(value = "/getToken/{spaceId}/{userId}", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseBean getToken(@PathVariable("userId") Long userId, @PathVariable("spaceId") Integer spaceId) {

        YunXinUtil yunXinUtil = new YunXinUtil();
        ChatUser chatUser = chatUserService.get(userId);

        try {
            if (chatUser == null){
                SysUser user = sysUserService.queryObject(userId);
                yunXinUtil.createUser(user);
                chatUser = chatUserService.get(userId);
            }
            ChatGroup chatGroup = chatGroupService.get(spaceId);
            Space space = spaceService.get(spaceId);
            if (chatGroup == null){
                yunXinUtil.createGroup(space, chatUser);
                chatGroup = chatGroupService.get(spaceId);
            }

            ChatGroupUser chatGroupUser = chatGroupUserService.getBySpaceAndUser(userId, spaceId);
            if (chatGroupUser == null){
                yunXinUtil.addGroup(chatGroup, chatUser);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        return new ResponseBean(false,"success", null, chatUser);
    }

    @RequestMapping(value = "/getGroupList/{userId}", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseBean getGroupList(@PathVariable("userId") Long userId) {

        List<ChatGroup> chatGroups = chatGroupService.getListByUser(userId);

        return new ResponseBean(false,"success", null, chatGroups);
    }

    @RequestMapping(value = "/getGroupUserList/{spaceId}", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseBean getGroupUserList(@PathVariable("spaceId") Integer spaceId) {

        List<ChatUser> chatUsers = chatUserService.getGroupUserList(spaceId);

        return new ResponseBean(false,"success", null, chatUsers);
    }
}
