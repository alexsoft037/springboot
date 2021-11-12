package xin.xiaoer.modules.mobile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;
import xin.xiaoer.modules.donatespace.service.DonateSpaceService;
import xin.xiaoer.modules.donatespace.service.DonateUserService;
import xin.xiaoer.modules.mobile.bean.ResponseBean;
import xin.xiaoer.service.FileService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("mobile/userDonate")
@ApiIgnore
public class APIUserDonateController {
    @Autowired
    private DonateSpaceService donateSpaceService;

    @Autowired
    private DonateUserService donateUserService;

    @Autowired
    private FileService fileService;

    @RequestMapping(value = "/totalResume/{userId}/{spaceId}", method = RequestMethod.GET)
    public ResponseBean totalResume(@PathVariable("userId") String userId, @PathVariable("spaceId") String spaceId){
        Map<String, Object> result = new HashMap<>();

        Double totalDonateAmount = donateUserService.getTotalDonateAmount(Long.parseLong(userId), spaceId);
        result.put("totalAmount", totalDonateAmount);

        Integer totalItemCount = donateUserService.getTotalItemCount(Long.parseLong(userId), spaceId);
        result.put("totalItemCount", totalItemCount);

        return new ResponseBean(false,ResponseBean.SUCCESS, null, result);
    }
}
