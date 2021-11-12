package xin.xiaoer.modules.classroom.controller;

import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import io.swagger.models.auth.In;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import xin.xiaoer.common.enumresource.StateEnum;
import xin.xiaoer.common.exception.MyException;
import xin.xiaoer.common.log.SysLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import javax.servlet.http.HttpServletRequest;

import xin.xiaoer.common.oss.CloudStorageService;
import xin.xiaoer.common.oss.OSSFactory;
import xin.xiaoer.common.shiro.ShiroUtils;
import xin.xiaoer.common.utils.*;
import xin.xiaoer.entity.File;
import xin.xiaoer.entity.SysUser;
import xin.xiaoer.modules.classroom.entity.StudyRoom;
import xin.xiaoer.modules.classroom.service.StudyAttendService;
import xin.xiaoer.modules.classroom.service.StudyRoomService;
import xin.xiaoer.service.FileService;


/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-08-20 13:04:31
 */
@Controller
@RequestMapping("studyroom")
public class StudyRoomController {
	@Autowired
	private StudyRoomService studyRoomService;

	@Autowired
    private FileService fileService;

	@Autowired
    private StudyAttendService studyAttendService;

    /**
     * 跳转到列表页
     */
    @RequestMapping("/list")
    @RequiresPermissions("studyroom:list")
    public String list() {
        return "studyroom/list";
    }
    
	/**
	 * 列表数据
	 */
    @ResponseBody
	@RequestMapping("/listData")
	@RequiresPermissions("studyroom:list")
	public R listData(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

        SysUser admin = ShiroUtils.getUserEntity();
        if (admin.getUserId() != 1L){
            query.put("spaceId", admin.getSpaceId());
        }

		List<StudyRoom> studyRoomList = studyRoomService.getList(query);
        CloudStorageService cloudStorageService = OSSFactory.build();
		for (StudyRoom studyRoom: studyRoomList){
            List<File> files = fileService.getByRelationId(studyRoom.getFeaturedImage());
            if (files.size() > 0) {
                studyRoom.setFeaturedImage(cloudStorageService.generatePresignedUrl(files.get(0).getUrl()));
            }
            int totalAttends = studyAttendService.getCountByRoomId(studyRoom.getRoomId());
            studyRoom.setTotalAttends(totalAttends);
        }
		int total = studyRoomService.getCount(query);
		
		PageUtils pageUtil = new PageUtils(studyRoomList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}

    /**
     * 跳转到新增页面
     **/
    @RequestMapping("/add")
    @RequiresPermissions("studyroom:save")
    public String add(Model model){
        model.addAttribute("admin",ShiroUtils.getUserEntity());
        model.addAttribute("BaiduApiKey", Constant.BaiduApiKey);
        return "studyroom/add";
    }

    /**
     *   跳转到修改页面
     **/
    @RequestMapping("/edit/{id}")
    @RequiresPermissions("studyroom:update")
    public String edit(Model model, @PathVariable("id") String id){
		StudyRoom studyRoom = studyRoomService.get(Integer.parseInt(id));
        model.addAttribute("admin",ShiroUtils.getUserEntity());
        model.addAttribute("BaiduApiKey", Constant.BaiduApiKey);
        model.addAttribute("model",studyRoom);
        return "studyroom/edit";
    }

	/**
	 * 信息
	 */
    @ResponseBody
    @RequestMapping("/info/{roomId}")
    @RequiresPermissions("studyroom:info")
    public R info(@PathVariable("roomId") Integer roomId){
        StudyRoom studyRoom = studyRoomService.get(roomId);
        return R.ok().put("studyRoom", studyRoom);
    }

    /**
	 * 保存
	 */
    @ResponseBody
    @SysLog("保存空间学习室")
	@RequestMapping("/save")
	@RequiresPermissions("studyroom:save")
	public R save(@RequestBody Map<String, Object> params) {
        Gson gson = new Gson();
        JsonElement jsonElement = gson.toJsonTree(params);
        StudyRoom studyRoom = gson.fromJson(jsonElement, StudyRoom.class);

        if (ShiroUtils.getUserId() == 1L){
            String spaceId = params.get("spaceId1").toString();
            if (StringUtility.isEmpty(spaceId) || !StringUtils.isNumeric(spaceId)) {
                throw new MyException("少儿空间不能为空！");
            }
            studyRoom.setSpaceId(Integer.parseInt(spaceId));
        } else {
            studyRoom.setSpaceId(ShiroUtils.getSpaceId());
        }

        try {
            studyRoom.setContent(URLDecoder.decode(studyRoom.getContent(), "UTF-8"));
        } catch (Exception e){
            e.printStackTrace();
        }
        if (studyRoom.getSrTypeCode().isEmpty()) {
            throw new MyException("分类不能为空！");
        }
        if (Jsoup.parse(studyRoom.getContent()).text().isEmpty()) {
            throw new MyException("内容不能为空！");
        }
        List<File> fileList = fileService.getByRelationId(studyRoom.getFeaturedImage());
        if (fileList.size() < 1) {
            throw new MyException("特色图片不能为空！");
        }
        if (fileList.size() > 1) {
            throw new MyException("特色图片不能超过1！");
        }
        if (studyRoom.getSrStatusCode().isEmpty()) {
            throw new MyException("进度不能为空");
        }
        String startTime = params.get("startTime1").toString();
        studyRoom.setStartTime(DateUtil.str2SDF_YMDHMS(startTime));
        studyRoom.setCreateBy(ShiroUtils.getUserId());
        studyRoom.setUpdateBy(ShiroUtils.getUserId());
		studyRoomService.save(studyRoom);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
    @ResponseBody
    @SysLog("修改空间学习室")
	@RequestMapping("/update")
	@RequiresPermissions("studyroom:update")
	public R update(@RequestBody Map<String, Object> params){
        Gson gson = new Gson();
        JsonElement jsonElement = gson.toJsonTree(params);
        StudyRoom studyRoom = gson.fromJson(jsonElement, StudyRoom.class);

        if (ShiroUtils.getUserId() == 1L){
            String spaceId = params.get("spaceId1").toString();
            if (StringUtility.isEmpty(spaceId) || !StringUtils.isNumeric(spaceId)) {
                throw new MyException("少儿空间不能为空！");
            }
            studyRoom.setSpaceId(Integer.parseInt(spaceId));
        } else {
            studyRoom.setSpaceId(ShiroUtils.getSpaceId());
        }

        try {
            studyRoom.setContent(URLDecoder.decode(studyRoom.getContent(), "UTF-8"));
        } catch (Exception e){
            e.printStackTrace();
        }
        if (studyRoom.getSrTypeCode().isEmpty()) {
            throw new MyException("分类不能为空！");
        }
        if (Jsoup.parse(studyRoom.getContent()).text().isEmpty()) {
            throw new MyException("内容不能为空！");
        }
        List<File> fileList = fileService.getByRelationId(studyRoom.getFeaturedImage());
        if (fileList.size() < 1) {
            throw new MyException("特色图片不能为空！");
        }
        if (fileList.size() > 1) {
            throw new MyException("特色图片不能超过1！");
        }
        if (studyRoom.getSrStatusCode().isEmpty()) {
            throw new MyException("进度不能为空");
        }
        String startTime = params.get("startTime1").toString();
        studyRoom.setStartTime(DateUtil.str2SDF_YMDHMS(startTime));
        studyRoom.setUpdateBy(ShiroUtils.getUserId());
		studyRoomService.update(studyRoom);
		
		return R.ok();
	}

    /**
     * 启用
     */
    @ResponseBody
    @SysLog("启用空间学习室")
    @RequestMapping("/enable")
    @RequiresPermissions("studyroom:update")
    public R enable(@RequestBody String[] ids){
        String stateValue=StateEnum.ENABLE.getCode();
		studyRoomService.updateState(ids,stateValue);
        return R.ok();
    }
    /**
     * 禁用
     */
    @ResponseBody
    @SysLog("禁用空间学习室")
    @RequestMapping("/limit")
    @RequiresPermissions("studyroom:update")
    public R limit(@RequestBody String[] ids){
        String stateValue=StateEnum.LIMIT.getCode();
		studyRoomService.updateState(ids,stateValue);
        return R.ok();
    }
	
	/**
	 * 删除
	 */
    @ResponseBody
    @SysLog("删除空间学习室")
	@RequestMapping("/delete")
	@RequiresPermissions("studyroom:delete")
	public R delete(@RequestBody Integer[] roomIds){
        for (Integer roomId: roomIds){
            StudyRoom studyRoom = studyRoomService.get(roomId);
            if (studyRoom != null){
                fileService.deleteByRelationId(studyRoom.getFeaturedImage());
            }
        }
		studyRoomService.deleteBatch(roomIds);
		return R.ok();
	}
	
}
