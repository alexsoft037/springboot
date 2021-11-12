package xin.xiaoer.modules.website.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xin.xiaoer.modules.mobile.bean.ResponseBean;
import xin.xiaoer.modules.space.entity.Space;
import xin.xiaoer.modules.space.service.SpaceService;

import java.util.Map;

/**
 * created by casey
 * 前台选择不同的城市，将city存入session，点击其他模块先读取session中的city，再取值
 */
@RestController
@RequestMapping("website/citychange")
public class WebAPICityChangeController {

    @Autowired
    private SpaceService spaceService;
    //点击切换城市按钮
    @RequestMapping(value = "/city", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseBean city(@RequestParam Map<String,Object> params) {
        //System.out.println("+++++++++++"+City);
//        HttpSession session = request.getSession(true);
//        session.setAttribute("spaceId", spaceId);
        //黄小东修改
        if ( params.get("city") == null ||StringUtils.isBlank(params.get("city").toString())){
            return new ResponseBean(false,ResponseBean.FAILED,null,"参数异常",null);
        }
        Space byAddress = spaceService.getByAddress(params);
        params.clear();
        params.put("city",byAddress.getCity());
        params.put("spaceId",byAddress.getSpaceId());
        return new ResponseBean(false, "success", null, params);
    }
}
