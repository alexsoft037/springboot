package xin.xiaoer.modules.website.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xin.xiaoer.common.utils.PageUtils;
import xin.xiaoer.common.utils.Query;
import xin.xiaoer.modules.mobile.bean.ResponseBean;
import xin.xiaoer.modules.space.service.SpaceService;
import xin.xiaoer.modules.website.entity.SpaceRankItem;
import xin.xiaoer.modules.website.entity.SysUserIntegralRankItem;
import xin.xiaoer.service.SysUserService;

import java.util.List;
import java.util.Map;

/**
 * @ClassName WebAPISpaceController
 * @Description TODO
 * @Author XiaoDong
 **/
@RestController
@RequestMapping("website/spaceWeb")
@Api("空间")
public class WebAPISpaceController {
    @Autowired
    private SpaceService spaceService;
    @Autowired
    private SysUserService sysUserService;

    /**
     * @Author XiaoDong
     * @Description TODO 获取空间排名
     * @Param [params]
     * @return xin.xiaoer.modules.mobile.bean.ResponseBean
     **/
    @PostMapping("/spaceRank")
    @ApiOperation("获取空间排名")
    public ResponseBean spaceRank(@RequestParam Map<String,Object> params){
        if (params.get("page")==null||params.get("limit")==null|| StringUtils.isBlank(params.get("page").toString())||StringUtils.isBlank(params.get("limit").toString())){
            return new ResponseBean(false,ResponseBean.FAILED,null,"参数异常",null);
        }
        Query query = new Query(params);
        List<SpaceRankItem> list=spaceService.getSpaceRank(query);
        int count = spaceService.getCount(query);
        PageUtils pageUtils = new PageUtils(list, count, query.getLimit(), query.getPage());
        return new ResponseBean(false,ResponseBean.SUCCESS,null,pageUtils);
    }

    /**
     * @Author XiaoDong
     * @Description TODO 获取个人积分排名
     * @Param [params]
     * @return xin.xiaoer.modules.mobile.bean.ResponseBean
     **/
    @PostMapping("/userIntegralRank")
    @ApiOperation("获取志愿者积分排名")
    public ResponseBean userIntegralRank(@RequestParam Map<String,Object> params){
        if (params.get("page")==null||params.get("limit")==null||StringUtils.isBlank(params.get("page").toString())||StringUtils.isBlank(params.get("limit").toString())){
            return new ResponseBean(false,ResponseBean.FAILED,null,"参数异常",null);
        }
        params.put("status","1");
        Query query = new Query(params);
        List<SysUserIntegralRankItem> list =sysUserService.getUserIntegralRank(query);
        int count=sysUserService.getCount(query);
        PageUtils pageUtils = new PageUtils(list, count, query.getLimit(), query.getPage());
        return new ResponseBean(false,ResponseBean.SUCCESS,null,pageUtils);
    }
}
