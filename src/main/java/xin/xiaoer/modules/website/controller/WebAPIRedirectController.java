package xin.xiaoer.modules.website.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import xin.xiaoer.common.oss.CloudStorageService;
import xin.xiaoer.common.oss.OSSFactory;
import xin.xiaoer.common.utils.Query;
import xin.xiaoer.common.utils.StringUtility;
import xin.xiaoer.entity.File;
import xin.xiaoer.modules.mobile.bean.ResponseBean;
import xin.xiaoer.modules.servicealliance.entity.ServiceAlliance;
import xin.xiaoer.modules.servicealliance.service.ServiceAllianceService;
import xin.xiaoer.modules.spaceofthehall.entity.SpaceOfTheHall;
import xin.xiaoer.modules.spaceofthehall.service.SpaceOfTheHallService;
import xin.xiaoer.service.FileService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("website/redirect")
@Controller
@Api("跳转")
public class WebAPIRedirectController {

    @Autowired
    private ServiceAllianceService serviceAllianceService;
    @Autowired
    private FileService fileService;
    @Autowired
    private SpaceOfTheHallService spaceOfTheHallService;


    /**
    *@Author dong
    *@Description //TODO 空间大厅模块列表,包括连接
    *@Param []
    *@return xin.xiaoer.modules.mobile.bean.ResponseBean
    **/
    @ApiOperation("空间大厅模块列表")
    @GetMapping("/getSpaceOfTheHall")
    @ResponseBody
    public ResponseBean getSpaceOfTheHall(){
        Map<String,Object> params= new HashMap<>();
        params.put("page",1);
        params.put("limit",20);
        params.put("status",1);
        Query query = new Query(params);
        List<SpaceOfTheHall> list = spaceOfTheHallService.getList(query);
        CloudStorageService oss = OSSFactory.build();
        for (SpaceOfTheHall spaceOfTheHall : list) {
            List<File> files = fileService.getByRelationId(spaceOfTheHall.getThumbnailImage());//貌似数据库微缩图用不了,暂时在spaceofthehall加个微缩图字段
            if (files.size()>0){
                String image = oss.generatePresignedUrl(files.get(0).getUrl());
                spaceOfTheHall.setThumbnailImage(image);
                spaceOfTheHall.setFeaturedImage(image);
            }

        }

//        return R.ok().put("list",list);
        return new ResponseBean(false,ResponseBean.SUCCESS,null,list);
    }




    /**
    *@Author dong
    *@Description //TODO 展示服务联盟信息,包括连接
    *@Param []
    *@return xin.xiaoer.modules.mobile.bean.ResponseBean
    **/
    @RequestMapping(value = "/fuwulianmeng", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    @ApiOperation("服务联盟信息列表")
    public ResponseBean fuwulianmeng() {
        Map<String,Object> params =new HashMap<>();
        params.put("state", "1");
        List<ServiceAlliance> serviceAlliances = serviceAllianceService.getList(params);

        CloudStorageService cloudStorageService = OSSFactory.build();
        for (ServiceAlliance serviceAlliance : serviceAlliances) {
            if (!StringUtility.isEmpty(serviceAlliance.getImgPath())) {
                List<File> files = fileService.getByRelationId(serviceAlliance.getImgPath());
                if (files.size() > 0) {
                    serviceAlliance.setImgPath(cloudStorageService.generatePresignedUrl(files.get(0).getUrl()));
                } else {
                    serviceAlliance.setImgPath("");
                }
            }
            if (!StringUtility.isEmpty(serviceAlliance.getContent())) {
                List<File> files = fileService.getByRelationId(serviceAlliance.getContent());
                if (files.size() > 0) {
                    serviceAlliance.setContent(cloudStorageService.generatePresignedUrl(files.get(0).getUrl()));
                } else {
                    serviceAlliance.setContent("");
                }
            }
        }

//        return R.ok().put("list",serviceAlliances);
        return new ResponseBean(false,ResponseBean.SUCCESS,null,serviceAlliances);
    }

}
