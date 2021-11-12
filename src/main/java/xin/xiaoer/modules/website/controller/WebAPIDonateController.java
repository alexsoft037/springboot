package xin.xiaoer.modules.website.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xin.xiaoer.common.oss.CloudStorageService;
import xin.xiaoer.common.oss.OSSFactory;
import xin.xiaoer.common.utils.PageUtils;
import xin.xiaoer.common.utils.Query;
import xin.xiaoer.common.utils.StringUtility;
import xin.xiaoer.entity.File;
import xin.xiaoer.modules.activity.service.ActivityService;
import xin.xiaoer.modules.donatespace.entity.DonateSpace;
import xin.xiaoer.modules.donatespace.service.DonateSpaceIntroService;
import xin.xiaoer.modules.donatespace.service.DonateSpaceProcessService;
import xin.xiaoer.modules.donatespace.service.DonateSpaceService;
import xin.xiaoer.modules.donatespace.service.DonateUserService;
import xin.xiaoer.modules.mobile.bean.ResponseBean;
import xin.xiaoer.modules.review.service.ReviewService;
import xin.xiaoer.modules.setting.service.IntegralService;
import xin.xiaoer.modules.space.service.SpaceService;
import xin.xiaoer.service.CommparaService;
import xin.xiaoer.service.FileService;
import xin.xiaoer.service.SysUserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("website/donate")
@Api("爱心空间")
public class WebAPIDonateController {
    @Autowired
    private DonateSpaceService donateSpaceService;

    @Autowired
    private DonateSpaceIntroService donateSpaceIntroService;

    @Autowired
    private DonateSpaceProcessService donateSpaceProcessService;

    @Autowired
    private DonateUserService donateUserService;

    @Autowired
    private FileService fileService;

    @Autowired
    private CommparaService commparaService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private IntegralService integralService;
    @Autowired
    private SpaceService spaceService;

    @Autowired
    private ActivityService activityService;


    /**
    *@Author dong
    *@Description //TODO 公益概况
    *@Param []
    *@return xin.xiaoer.modules.mobile.bean.ResponseBean
    **/
    @ApiOperation("公益概况")
    @GetMapping("/donateSimpleAndClear")
    @ResponseBody
    public ResponseBean donateSimpleAndClear(){
        Map map=new HashMap();//数据容器
        int volunteerCount=sysUserService.getVolunteerCount();//全国志愿者人数
        int attendDonateCount=donateUserService.getAttendDonateCount();//全国参与公益人数
        int spaceCount=spaceService.getCount(map);//空间个数
        int donateCount=donateSpaceService.getCount(map);//公益项目数
        double totalMoney=donateUserService.getTotalDonateAmount(null,null);
        map.put("volunteerCount",volunteerCount+6008026);
        map.put("attendDonateCount",attendDonateCount);
        map.put("spaceCount",spaceCount);
        map.put("donateCount",donateCount);
        map.put("totalMoney",totalMoney);
        return new ResponseBean(false,ResponseBean.SUCCESS,null,map);
    }

    /**
    *@Author dong
    *@Description //TODO 最新公益
    *@Param [params]
    *@return xin.xiaoer.modules.mobile.bean.ResponseBean
    **/
    @ApiOperation("最新公益")
    @ResponseBody
    @RequestMapping(value = "/newDonate",method = RequestMethod.POST)
    public ResponseBean newDonate(@RequestParam Map<String,Object> params){
        if (params.get("spaceId")==null||StringUtils.isBlank(params.get("spaceId").toString())){
//            return R.error("缺少参数:spaceId");
            params.put("spaceId",null);
        }
//        String spaceId = params.get("spaceId").toString();
//        if (StringUtils.isBlank(spaceId)){
//            params.put("spaceId",null);
//        }
        params.put("page","1");
        params.put("limit","10");
        Query query = new Query(params);
        List<DonateSpace> list=donateSpaceService.getNewDonate(query);
//        return R.ok().put("list",list);
        return new ResponseBean(false,ResponseBean.SUCCESS,null,list);
    }

    /**
    *@Author dong
    *@Description //TODO 捐赠份数
    *@Param []
    *@return xin.xiaoer.modules.mobile.bean.ResponseBean
    **/
    @ApiOperation(value = "捐赠份数")
    @RequestMapping(value = "/donateCount", method = RequestMethod.GET)
    public ResponseBean donateCount() {
        Integer count = donateUserService.getDonateCount();
//        return R.ok().put("list", count);
        return new ResponseBean(false,ResponseBean.SUCCESS,null,count);
    }

    /**
    *@Author dong
    *@Description //TODO 公益/互助列表
    *@Param [params]
    *@return xin.xiaoer.modules.mobile.bean.ResponseBean
    **/
    @ApiOperation("公益/互助列表")
    @RequestMapping(value = "/listData", method = RequestMethod.POST)
    @ResponseBody
    public ResponseBean listData(@RequestParam Map<String, Object> params){
        //查询列表数据

        if (params.get("pageCount")==null||params.get("typeCode")==null||params.get("curPageNum")==null||params.get("spaceId")==null||StringUtils.isBlank(params.get("pageCount").toString())||StringUtils.isBlank(params.get("curPageNum").toString())){
//            return R.error("参数有误");
            return new ResponseBean(false,ResponseBean.FAILED,"参数异常","缺少参数",null);
        }
//        String requestPageCount = request.getParameter("pageCount");
        String pageCount = params.get("pageCount").toString();//条数
        String typeCode = params.get("typeCode").toString();//dst001公益互助,dst002空间公益
        String curPageNum = params.get("curPageNum").toString();//当前页
        String spaceId = params.get("spaceId").toString();//id
        if (StringUtility.isEmpty(spaceId)) {//如果不指定就查全部
            params.put("spaceId", 0);
        }
        params.put("limit", pageCount);
        params.put("page", curPageNum);
        params.put("state", "1");
        params.put("dsTypeCode", typeCode);
        if (params.get("limit") == null){
            params.put("limit", 10);
        }
        if (params.get("page") == null){
            params.put("page", 1);
        }
        Query query = new Query(params);

//        List<DSpaceListItem> donateSpaceList = donateSpaceService.getDSpaceListData(query);
        List<Map> donateSpaceList = donateSpaceService.getTitles(query);//返回字段feturedImage,item_id,reviewCount,donateCount,title,create_at,ds_type_code,space_id,
        CloudStorageService cloudStorageService = OSSFactory.build();
        query.put("articleType",typeCode);
        for (Map dSpaceListItem : donateSpaceList){
            List<File> files = fileService.getByRelationId(dSpaceListItem.get("feturedImage").toString());
            if (files.size()>0){
                dSpaceListItem.put("feturedImage",cloudStorageService.generatePresignedUrl(files.get(0).getUrl()));
            }
//            if (dSpaceListItem.get("feturedImage") != null && !dSpaceListItem.get("feturedImage").equals("")){
//                dSpaceListItem.put("feturedImage",cloudStorageService.generatePresignedUrl(dSpaceListItem.get("feturedImage").toString()));
//            }
//            query.put("articleId",dSpaceListItem.getItemId());
//            dSpaceListItem.setPublicCount(activityService.getPublicCount(query));
        }
        int total = donateSpaceService.getCountDSpace(query);

        PageUtils pageUtil = new PageUtils(donateSpaceList, total, query.getLimit(), query.getPage());

//        return R.ok().put("list",pageUtil);
        return new ResponseBean(false,ResponseBean.SUCCESS,null,pageUtil);
    }
}
