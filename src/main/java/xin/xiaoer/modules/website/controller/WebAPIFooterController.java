package xin.xiaoer.modules.website.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import xin.xiaoer.modules.help.entity.HelpCenter;
import xin.xiaoer.modules.help.service.HelpCenterService;
import xin.xiaoer.modules.mobile.bean.ResponseBean;

@RestController
@RequestMapping("website/pagePublic")
public class WebAPIFooterController {


    @Autowired
    private HelpCenterService helpCenterService;


    /**
     * @author DaiMingJian
     * @email 3088393266@qq.com
     * @date 2019/1/15
     */
    @RequestMapping(value = "/aboutus", method = RequestMethod.POST)
    public ResponseBean aboutUs() {
        HelpCenter helpCenter = helpCenterService.getByCategory("ABOUTUS");
        return new ResponseBean(false,ResponseBean.SUCCESS,null,helpCenter);
    }
}