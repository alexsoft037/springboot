package xin.xiaoer.modules.classroom.controller;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import xin.xiaoer.common.enumresource.StateEnum;
import xin.xiaoer.common.exception.MyException;
import xin.xiaoer.common.log.SysLog;
import xin.xiaoer.common.oss.CloudStorageService;
import xin.xiaoer.common.oss.OSSFactory;
import xin.xiaoer.common.shiro.ShiroUtils;
import xin.xiaoer.common.utils.PageUtils;
import xin.xiaoer.common.utils.Query;
import xin.xiaoer.common.utils.R;
import xin.xiaoer.common.utils.StringUtility;
import xin.xiaoer.entity.SysUser;
import xin.xiaoer.modules.classroom.entity.LiveRoom;
import xin.xiaoer.modules.classroom.service.LiveRoomService;

import java.util.List;
import java.util.Map;


/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-09-19 10:27:23
 */
@Controller
@RequestMapping("liveroom")
public class LiveRoomController {
	@Autowired
	private LiveRoomService liveRoomService;
	
    /**
     * 跳转到列表页
     */
    @RequestMapping("/list")
    @RequiresPermissions("liveroom:list")
    public String list() {
        return "liveroom/list";
    }
    
	/**
	 * 列表数据
	 */
    @ResponseBody
	@RequestMapping("/listData")
	@RequiresPermissions("liveroom:list")
	public R listData(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

//        SysUser admin = ShiroUtils.getUserEntity();
//        if (admin.getUserId() != 1L){
//            query.put("spaceId", admin.getSpaceId());
//        }

		List<LiveRoom> liveRoomList = liveRoomService.getList(query);

        CloudStorageService cloudStorageService = OSSFactory.build();
		for (LiveRoom liveRoom: liveRoomList) {
            liveRoom.setFeaturedUrl(cloudStorageService.generatePresignedUrl(liveRoom.getFeaturedUrl()));
        }

		int total = liveRoomService.getCount(query);
		
		PageUtils pageUtil = new PageUtils(liveRoomList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}

    /**
     * 跳转到新增页面
     **/
    @RequestMapping("/add")
    @RequiresPermissions("liveroom:save")
    public String add(Model model){
        model.addAttribute("admin",ShiroUtils.getUserEntity());
        return "liveroom/add";
    }

    /**
     *   跳转到修改页面
     **/
    @RequestMapping("/edit/{id}")
    @RequiresPermissions("liveroom:update")
    public String edit(Model model, @PathVariable("id") Integer id){
		LiveRoom liveRoom = liveRoomService.get(id);
        model.addAttribute("admin",ShiroUtils.getUserEntity());
        model.addAttribute("model",liveRoom);
        return "liveroom/edit";
    }

	/**
	 * 信息
	 */
    @ResponseBody
    @RequestMapping("/info/{liveId}")
    @RequiresPermissions("liveroom:info")
    public R info(@PathVariable("liveId") Integer liveId){
        LiveRoom liveRoom = liveRoomService.get(liveId);
        return R.ok().put("liveRoom", liveRoom);
    }

    /**
	 * 保存
	 */
    @ResponseBody
    @SysLog("保存少儿云课堂直播")
	@RequestMapping("/save")
	@RequiresPermissions("liveroom:save")
	public R save(@RequestBody Map<String, Object> params){
        Gson gson = new Gson();
        JsonElement jsonElement = gson.toJsonTree(params);
        LiveRoom liveRoom = gson.fromJson(jsonElement, LiveRoom.class);

//        if (ShiroUtils.getUserId() == 1L){
//            String spaceId = params.get("spaceId1").toString();
//            if (StringUtility.isEmpty(spaceId) || !StringUtils.isNumeric(spaceId)) {
//                throw new MyException("少儿空间不能为空！");
//            }
//            liveRoom.setSpaceId(Integer.parseInt(spaceId));
//        } else {
//            liveRoom.setSpaceId(ShiroUtils.getSpaceId());
//        }

		liveRoomService.save(liveRoom);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
    @ResponseBody
    @SysLog("修改少儿云课堂直播")
	@RequestMapping("/update")
	@RequiresPermissions("liveroom:update")
	public R update(@RequestBody Map<String, Object> params){
        Gson gson = new Gson();
        JsonElement jsonElement = gson.toJsonTree(params);
        LiveRoom liveRoom = gson.fromJson(jsonElement, LiveRoom.class);

//        if (ShiroUtils.getUserId() == 1L){
//            String spaceId = params.get("spaceId1").toString();
//            if (StringUtility.isEmpty(spaceId) || !StringUtils.isNumeric(spaceId)) {
//                throw new MyException("少儿空间不能为空！");
//            }
//            liveRoom.setSpaceId(Integer.parseInt(spaceId));
//        } else {
//            liveRoom.setSpaceId(ShiroUtils.getSpaceId());
//        }

		liveRoomService.update(liveRoom);
		
		return R.ok();
	}

    /**
     * 启用
     */
    @ResponseBody
    @SysLog("启用少儿云课堂直播")
    @RequestMapping("/enable")
    @RequiresPermissions("liveroom:update")
    public R enable(@RequestBody String[] ids){
        String stateValue=StateEnum.ENABLE.getCode();
		liveRoomService.updateState(ids,stateValue);
        return R.ok();
    }
    /**
     * 禁用
     */
    @ResponseBody
    @SysLog("禁用少儿云课堂直播")
    @RequestMapping("/limit")
    @RequiresPermissions("liveroom:update")
    public R limit(@RequestBody String[] ids){
        String stateValue=StateEnum.LIMIT.getCode();
		liveRoomService.updateState(ids,stateValue);
        return R.ok();
    }
	
	/**
	 * 删除
	 */
    @ResponseBody
    @SysLog("删除少儿云课堂直播")
	@RequestMapping("/delete")
	@RequiresPermissions("liveroom:delete")
	public R delete(@RequestBody Integer[] liveIds){
		liveRoomService.deleteBatch(liveIds);
		
		return R.ok();
	}
	
}
