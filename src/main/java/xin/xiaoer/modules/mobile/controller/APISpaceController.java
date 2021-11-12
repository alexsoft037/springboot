package xin.xiaoer.modules.mobile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;
import org.springframework.web.bind.annotation.*;
import xin.xiaoer.modules.mobile.bean.ResponseBean;
import xin.xiaoer.modules.space.entity.Space;
import xin.xiaoer.modules.space.service.SpaceService;
import xin.xiaoer.service.FileService;
import xin.xiaoer.service.SysUserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("mobile/space")
@ApiIgnore
public class APISpaceController {
    @Autowired
    private SpaceService spaceService;

    @Autowired
    private FileService fileService;

    @Autowired
    private SysUserService sysUserService;

    @RequestMapping(value = "/listData", method = RequestMethod.POST)
    public ResponseBean listData(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        //查询列表数据

        List<Space> spaceList = spaceService.getSpaceList(params);

        return new ResponseBean(false, "success", null, spaceList);
    }

    @RequestMapping(value = "/detail/{spaceId}", method = RequestMethod.GET)
    public ResponseBean deatil(@PathVariable("spaceId") Integer spaceId) {
        //查询列表数据

        Space space = spaceService.get(spaceId);

        return new ResponseBean(false, "success", null, space);
    }

    @RequestMapping(value = "/getSpaceByAddress", method = RequestMethod.POST)
    public ResponseBean deatil(@RequestParam Map<String, Object> params) {
        //查询列表数据

//        String province = params.get("province").toString();
//        String city = params.get("city").toString();
//        String district = params.get("district").toString();
//        String street = params.get("street").toString();
//        String streetNumber = params.get("streetNumber").toString();

        Space space = spaceService.getByAddress(params);

        return new ResponseBean(false, "success", null, space);
    }
}
