
package xin.xiaoer.controller;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.routines.UrlValidator;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import xin.xiaoer.common.enumresource.DefaultEnum;
import xin.xiaoer.common.enumresource.StateEnum;
import xin.xiaoer.common.exception.MyException;
import xin.xiaoer.common.log.SysLog;
import xin.xiaoer.common.oss.CloudStorageService;
import xin.xiaoer.common.oss.OSSFactory;
import xin.xiaoer.common.shiro.ShiroUtils;
import xin.xiaoer.common.utils.*;
import xin.xiaoer.entity.File;
import xin.xiaoer.entity.SysUser;
import xin.xiaoer.service.FileService;
import xin.xiaoer.service.SysUserRoleService;
import xin.xiaoer.service.SysUserService;

import javax.servlet.http.HttpServletRequest;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 系统用户
 * 
 * @author chenyi
 * @email 228112142@qq.com
 * @date 2016年10月31日 上午10:40:10
 */
@Controller
@RequestMapping("/sys/adminRole")
public class SysAdminRoleController extends AbstractController {
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUserRoleService sysUserRoleService;

	/**
	 * 所有用户列表
	 */

	@RequestMapping("/list")
	@RequiresPermissions("sys:adminRole:list")
	public String list(){
		return "adminRole/list";
	}

	/**
	 * 所有用户列表
	 */
	@ResponseBody
	@RequestMapping("/listData")
	@RequiresPermissions("sys:adminRole:list")
	public R listData(@RequestParam Map<String, Object> params){
		//只有超级管理员，才能查看所有管理员列表
		if(getUserId() != Constant.SUPER_ADMIN){
			params.put("createUserId", getUserId());
		}

		//查询列表数据
		Query query = new Query(params);
		List<SysUser> userList = sysUserService.queryAdminRoleList(query);
		for (SysUser sysUser: userList) {
			sysUser.setAddressTxt(sysUserService.getAddressTxtFromAddressIds(sysUser.getAddress()));
			sysUser.setAvatar(sysUserService.getAvatar(sysUser.getAvatar()));
		}
		int total = sysUserService.queryAdminRoleTotal(query);

		PageUtils pageUtil = new PageUtils(userList, total, query.getLimit(), query.getPage());

		return R.ok().put("page", pageUtil);
	}
	/**
	 * @author chenyi
	 * @Description  跳转到修改页面
	 * @param
	 * @date 2017/6/27 11:17
	 **/
	@RequestMapping("/edit/{id}")
	@RequiresPermissions("sys:adminRole:update")
	public String edit(HttpServletRequest request, Model model, @PathVariable("id") Long id){
		SysUser user = sysUserService.queryObject(id);
		model.addAttribute("model",user);
		//获取所属角色
		List<Long> roleIds=sysUserRoleService.queryRoleIdList(user.getUserId());
		//将list转为字符串
		String roleIdList=StringUtils.join(roleIds.toArray(),",");
		model.addAttribute("roleIdList",roleIdList);
		return "/adminRole/edit";
	}

	/**
	 * 修改用户
	 */
	@ResponseBody
	@SysLog("修改用户")
	@RequestMapping("/update")
	@RequiresPermissions("sys:adminRole:update")
	public R update(@RequestBody Map<String, Object> params){
		Gson gson = new Gson();
		JsonElement jsonElement = gson.toJsonTree(params);
		SysUser user = gson.fromJson(jsonElement, SysUser.class);
		SysUser admin = sysUserService.queryObject(user.getUserId());
		user.setCreateUserId(admin.getCreateUserId());
		ArrayList roleIdList = new ArrayList<>();
		try {
			roleIdList = (ArrayList) params.get("roleIdList1");
			if (roleIdList.size() > 0 && StringUtility.isEmpty(roleIdList.get(0).toString())){
				roleIdList = null;
			}
		} catch (Exception e){
			e.printStackTrace();
			roleIdList = null;
		}
		user.setRoleIdList(roleIdList);
		sysUserService.update(user);
		//todo kj
		
		return R.ok();
	}
}
