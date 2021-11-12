package xin.xiaoer.modules.website.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xin.xiaoer.common.utils.PageUtils;
import xin.xiaoer.common.utils.Query;
import xin.xiaoer.modules.donatespace.entity.DonateSpace;
import xin.xiaoer.modules.donatespace.service.DonateSpaceService;
import xin.xiaoer.modules.mobile.bean.ResponseBean;
import xin.xiaoer.modules.space.service.SpaceService;
import xin.xiaoer.service.SysUserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-10-28 22:08:28
 */
@RestController
@RequestMapping("website/honorlist")
public class WebAPIHonorListController {

	@Autowired
	private DonateSpaceService donateSpaceService;
    @Autowired
    private SpaceService spaceService;
    @Autowired
    private SysUserService sysUserService;

    @RequestMapping("/honorbyregion")
    /***
     * 按照区域查询志愿组织的荣誉榜单
     */
    public ResponseBean listData(@RequestParam Map<String, Object> params,HttpServletRequest request){
        //查询列表数据
        String page = params.get("page").toString();
        String limit = params.get("limit").toString();
        String regionName = params.get("regionName").toString();
        params.put("page",page);
        params.put("limit",limit);
        params.put("regionName",regionName);
        Query query = new Query(params);
        List<DonateSpace> donateSpaceList = donateSpaceService.gethonorbyregion(query);
        int total = donateSpaceService.getCount(query);
        PageUtils pageUtil = new PageUtils(donateSpaceList, total, query.getLimit(), query.getPage());
        return new ResponseBean(false, "success", null, pageUtil);
    }

    @RequestMapping("/honorbygroup")
    /***
     * 查询全区域的志愿组织的荣誉榜单(不限)
     */
    public ResponseBean honorbygroup(@RequestParam Map<String, Object> params,HttpServletRequest request){
        //查询列表数据
        String page = params.get("page").toString();
        String limit = params.get("limit").toString();
        params.put("page",page);
        params.put("limit",limit);
        Query query = new Query(params);
        List<DonateSpace> donateSpaceList = donateSpaceService.gethonorbygroup(query);
        int total = donateSpaceService.getCount(query);
        PageUtils pageUtil = new PageUtils(donateSpaceList, total, query.getLimit(), query.getPage());
        return new ResponseBean(false, "success", null, pageUtil);
    }

    @RequestMapping("/honorbyvolunteer")
    /***
     * 查询志愿者的荣誉榜单
     */
    public ResponseBean honorbyvolunteer(@RequestParam Map<String, Object> params,HttpServletRequest request){
        //查询列表数据
        String page = params.get("page").toString();
        String limit = params.get("limit").toString();
        params.put("page",page);
        params.put("limit",limit);
        Query query = new Query(params);
        List<DonateSpace> donateSpaceList = donateSpaceService.gethonorbyvolunteer(query);
        int total = donateSpaceService.getCount(query);
        PageUtils pageUtil = new PageUtils(donateSpaceList, total, query.getLimit(), query.getPage());
        return new ResponseBean(false, "success", null, pageUtil);
    }

    @RequestMapping("/honorbysearch")
    /***
     * 荣誉榜单搜索框
     */
    public ResponseBean honorbysearch(@RequestParam Map<String, Object> params,HttpServletRequest request){
        //查询列表数据
        String page = params.get("page").toString();
        String limit = params.get("limit").toString();
        params.put("page",page);
        params.put("limit",limit);
        Query query = new Query(params);
        List<DonateSpace> donateSpaceList = donateSpaceService.gethonorbysearch(query);
        int total = donateSpaceService.getsearchCount(query);
        PageUtils pageUtil = new PageUtils(donateSpaceList, total, query.getLimit(), query.getPage());
        return new ResponseBean(false, "success", null, pageUtil);
    }

    /**
     * @return xin.xiaoer.modules.mobile.bean.ResponseBean
     * @Description TODO 荣誉榜单-组织排名
     * @Param [params]
     * @Author XiaoDong
     **/
    @PostMapping("/spaceRank")
    public ResponseBean spaceRank(@RequestParam Map<String,String> params){
//        1-华东 2-华北 3-华中 4-华南 5-东北 6-西北 7-西南
        if (StringUtils.isBlank(params.get("page")) || StringUtils.isBlank(params.get("limit"))){
            return new ResponseBean(false,ResponseBean.FAILED,null,"参数异常",null);
        }
        PageHelper.startPage(Integer.parseInt(params.get("page")),Integer.parseInt(params.get("limit")));
        List<Map<String,String>> rankInfo = spaceService.getSpaceRankAtHonorList(params);
        for (Map<String, String> map : rankInfo) {
            switch (map.get("region")){
                case "1":
                    map.put("region","华东");
                    break;
                case "2":
                    map.put("region","华北");
                    break;
                case "3":
                    map.put("region","华中");
                    break;
                case "4":
                    map.put("region","华南");
                    break;
                case "5":
                    map.put("region","东北");
                    break;
                case "6":
                    map.put("region","西北");
                    break;
                case "7":
                    map.put("region","西南");
                    break;
            }
        }
        PageInfo<Map<String,String>> pageInfo = new PageInfo<>(rankInfo);
        return new ResponseBean(false,ResponseBean.SUCCESS,null,pageInfo);
    }

    /**
     * @return xin.xiaoer.modules.mobile.bean.ResponseBean
     * @Description TODO 荣誉榜单-志愿者排名
     * @Param [params]
     * @Author XiaoDong
     **/
    @PostMapping("/volunteerRank")
    public ResponseBean volunteerRank(@RequestParam Map<String,String> params){
        if (StringUtils.isBlank(params.get("page")) || StringUtils.isBlank(params.get("limit"))){
            return new ResponseBean(false,ResponseBean.FAILED,null,"参数异常",null);
        }
        PageHelper.startPage(Integer.parseInt(params.get("page")),Integer.parseInt(params.get("limit")));
        List<Map<String,String>> rankInfo = sysUserService.getvolunteerRankAtHonorList(params);
        PageInfo<Map<String,String>> pageInfo = new PageInfo<>(rankInfo);
        return new ResponseBean(false,ResponseBean.SUCCESS,null,pageInfo);
    }
}
