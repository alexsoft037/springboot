package xin.xiaoer.modules.mobile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;
import xin.xiaoer.common.utils.IPUtils;
import xin.xiaoer.entity.SysUser;
import xin.xiaoer.modules.activity.service.ActivityAttendService;
import xin.xiaoer.modules.mobile.bean.ResponseBean;
import xin.xiaoer.service.SysUserService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("mobile/online")
@ApiIgnore
public class APIOnlineController {

    @Autowired
    SysUserService sysUserService;

    @Autowired
    ActivityAttendService activityAttendService;

    @RequestMapping(value = "/userSession", method = RequestMethod.POST)
    public ResponseBean userSession(HttpServletRequest request) throws IOException {
        Map<String, Object> map = new HashMap<>();

        String userId = request.getParameter("userId");
        String isOnline = request.getParameter("isOnline");
        String deviceId = request.getParameter("deviceId");
        String deviceType = request.getParameter("deviceType");
        map.put("userId", userId);
        map.put("isOnline", isOnline);
        map.put("deviceId", deviceId);
        map.put("deviceType", deviceType);

        SysUser sysUser = sysUserService.queryObject(Long.parseLong(userId));
        if (isOnline.equals("1")) {
            map.put("loginCount", sysUser.getLoginCount() + 1L);
            map.put("lastLoginIpaddr", IPUtils.getIpAddr(request));
            map.put("lastLoginDt", new Date());
        }
        sysUserService.updateSession(map);

        return new ResponseBean(false,"success", null, map);
    }

    @RequestMapping(value = "/getTotal", method = RequestMethod.GET)
    public ResponseBean getTotal(HttpServletRequest request) throws IOException {
        Map<String, Object> map = new HashMap<>();

        int attendTotal = activityAttendService.queryAttendTotal();
        int onlineTotal = sysUserService.queryOnlineTotal();

        map.put("onlineTotal", onlineTotal);
        map.put("attendTotal", attendTotal);
//        map.put("liveTotal", attendTotal);
//        map.put("chatTotal", attendTotal);

        return new ResponseBean(false,"success", null, map);
    }
}
