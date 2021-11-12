package xin.xiaoer.modules.mobile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;
import xin.xiaoer.common.utils.ConfigConstant;
import xin.xiaoer.entity.AndroidVersion;
import xin.xiaoer.entity.IphoneVersion;
import xin.xiaoer.modules.mobile.bean.ResponseBean;
import xin.xiaoer.service.SysConfigService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("mobile/checkUpdate")
@ApiIgnore
public class APICheckUpdateController {

    @Autowired
    private SysConfigService sysConfigService;

    @RequestMapping(value = "android", method = RequestMethod.GET)
    public ResponseBean android(HttpServletRequest request) {
        AndroidVersion androidVersion = sysConfigService.getConfigClassObject(ConfigConstant.ANDROID_VERSION_CONFIG_KEY, AndroidVersion.class);
        return new ResponseBean(false,"success", null, androidVersion);
    }

    @RequestMapping(value = "iphone", method = RequestMethod.GET)
    public ResponseBean iphone(HttpServletRequest request) {
        IphoneVersion iphoneVersion = sysConfigService.getConfigClassObject(ConfigConstant.IPHONE_VERSION_CONFIG_KEY, IphoneVersion.class);
        return new ResponseBean(false,"success", null, iphoneVersion);
    }
}
