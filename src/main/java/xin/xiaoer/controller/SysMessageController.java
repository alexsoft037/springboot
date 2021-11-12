package xin.xiaoer.controller;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import xin.xiaoer.common.shiro.ShiroUtils;
import xin.xiaoer.common.utils.R;
import xin.xiaoer.common.utils.YunXinUtil;
import xin.xiaoer.entity.SysUser;
import xin.xiaoer.modules.chat.service.ChatUserService;
import xin.xiaoer.service.PushMessageService;
import xin.xiaoer.service.SysUserService;

import java.util.List;
import java.util.Map;

/**
 * @ClassName SystemMessageController
 * @Description TODO 系统消息推送
 * @Author Administrator
 **/
@Controller
@RequestMapping("sysMessage")
public class SysMessageController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private ChatUserService chatUserService;
    @Autowired
    private PushMessageService pushMessageService;

    @GetMapping("/pushMsg")
    @RequiresPermissions("sysMessage:pushMessage")
    public String pushMsg() {
        return "pushMessage/edit";
    }

    /**
     * @return xin.xiaoer.modules.mobile.bean.ResponseBean
     * @Author XiaoDong
     * @Description TODO 推送消息
     * @Param [params]
     **/
    @PostMapping("/pushMessage")
    @ResponseBody
    @RequiresPermissions("sysMessage:pushMessage")
    public R sendMessage(@RequestParam Map<String, Object> params) {
        if (params.get("powered") == null || params.get("method") == null || params.get("msg") == null || StringUtils.isBlank(params.get("powered").toString()) || StringUtils.isBlank(params.get("method").toString()) || StringUtils.isBlank(params.get("msg").toString())) {
            return R.error("参数异常");
        }
        String power = params.get("powered").toString();//平台：1安卓，2苹果
        String method = params.get("method").toString();//推送方式：1所有人，2等级分类，3某用户
        String msg = params.get("msg").toString();//消息内容
        params.remove("rankMin");
        params.remove("rankMax");
        params.remove("phone");
        SysUser sysUser = ShiroUtils.getUserEntity();
        switch (power) {
            case "1":
                params.put("powered", "android");
                break;
            case "2":
                params.put("powered", "ios");
                break;
            default:
                params.remove("powered");
        }
        switch (method) {
            case "1"://所有人
                break;
            case "2"://等级分类
                if (params.get("rank") == null || StringUtils.isBlank(params.get("rank").toString())) {
                    return R.error("参数异常");
                }
                String rank = params.get("rank").toString();
                switch (rank) {
                    case "1":
                        params.put("rankMin", "0");
                        params.put("rankMax", "1000");
                        break;
                    case "2":
                        params.put("rankMin", "1001");
                        params.put("rankMax", "2000");
                        break;
                    case "3":
                        params.put("rankMin", "2001");
                        params.put("rankMax", "3000");
                        break;
                    case "4":
                        params.put("rankMin", "3001");
                        params.put("rankMax", "4000");
                        break;
                    case "5":
                        params.put("rankMin", "4001");
                        params.put("rankMax", "5000");
                        break;
                    default:
                        return R.error("参数异常");
                }
                break;
            case "3"://个别人
                if (params.get("phoneNumber") == null || StringUtils.isBlank(params.get("phoneNumber").toString())) {
                    return R.error("参数异常");
                }
                String phoneNumber = params.get("phoneNumber").toString();
                String[] split = phoneNumber.split(",");
                params.put("phone", split);
                params.remove("powered");
                break;
            default:
                return R.error("参数异常");
        }

        List<String> accids = sysUserService.queryBySendTarget(params);
        if (accids.size() <= 0) {
            return R.error("无有效用户");
        }
        YunXinUtil yunXinUtil = new YunXinUtil();
        try {
            yunXinUtil.sendBatchPush(accids, msg);
            //推送消息入库，解开即用
//            List<Long> userIds = chatUserService.queryByAccids(accids);
//            PushMessage pushMessage = new PushMessage();
//            pushMessage.setFromUserId(sysUser.getUserId());
//            pushMessage.setMessage(msg);
//            pushMessageService.saveMsg(pushMessage,userIds);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error("发送失败！请联系管理员！");
        }
        return R.ok("发送成功！");
    }

    /**
     * @return java.lang.String
     * @Description TODO 跳转到全部用户页面
     * @Param [model]
     * @Author XiaoDong
     **/
    @RequestMapping("/pushAll")
    @RequiresPermissions("sysMessage:pushMessage")
    public String pushAll(Model model) {
        model.addAttribute("admin", ShiroUtils.getUserEntity());
        return "pushMessage/pushAll";
    }

    /**
     * @return java.lang.String
     * @Description TODO 跳转到全部用户页面
     * @Param [model]
     * @Author XiaoDong
     **/
    @RequestMapping("/pushByPhone")
    @RequiresPermissions("sysMessage:pushMessage")
    public String pushByPhone(Model model) {
        model.addAttribute("admin", ShiroUtils.getUserEntity());
        return "pushMessage/pushByPhone";
    }

    /**
     * @return java.lang.String
     * @Description TODO 跳转到全部用户页面
     * @Param [model]
     * @Author XiaoDong
     **/
    @RequestMapping("/pushByRank")
    @RequiresPermissions("sysMessage:pushMessage")
    public String pushByRank(Model model) {
        model.addAttribute("admin", ShiroUtils.getUserEntity());
        return "pushMessage/pushByRank";
    }
}









