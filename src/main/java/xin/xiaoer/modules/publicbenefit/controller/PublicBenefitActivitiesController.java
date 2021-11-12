package xin.xiaoer.modules.publicbenefit.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import xin.xiaoer.common.api.annotation.IgnoreAuth;
import xin.xiaoer.common.utils.PageUtils;
import xin.xiaoer.common.utils.Query;
import xin.xiaoer.common.utils.R;
import xin.xiaoer.modules.publicbenefit.service.PublicBenefitActivitiesService;

import java.util.List;
import java.util.Map;

@Controller
@Api("公益活动")
@RequestMapping("publicBenefit")
public class PublicBenefitActivitiesController {
    @Autowired
    private PublicBenefitActivitiesService publicBenefitService;

    @GetMapping("list")
    @RequiresPermissions("publicBenefit:list")
    @ApiIgnore
    public String list(){return "publicBenefit/list";}

    @IgnoreAuth
    @ResponseBody
    @ApiOperation(value = "获取公益活动列表",notes = "获取公益活动列表")
    @PostMapping("/listData")
    public R listData(@ApiParam(name = "params",value = "page:当前页,limit:显示条数,hot:是(1)否热门,ornew:是(1)否最新") @RequestBody Map<String,Object> params){
        Query query = new Query(params);//防止sql注入
        if (params.get("ornew")!=null&&"1".equals(params.get("ornew"))){
            query.put("ornew","desc");
        }else {
            query.put("ornew","asc");
        }
        List list=publicBenefitService.getList(query);//公益活动
        int total=publicBenefitService.getCount(query);//总记录数
        PageUtils pageUtils = new PageUtils(list, total, query.getLimit(), query.getPage());
        return R.ok().put("page",pageUtils);
    }
}
