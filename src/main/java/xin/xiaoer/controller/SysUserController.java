
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
@RequestMapping("/sys/user")
public class SysUserController extends AbstractController {
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUserRoleService sysUserRoleService;
	@Autowired
	private FileService fileService;

	/**
	 * 所有用户列表
	 */

	@RequestMapping("/list")
	@RequiresPermissions("sys:user:list")
	public String list(){
		return "user/list";
	}

	/**
	 * 所有用户列表
	 */
	@ResponseBody
	@RequestMapping("/listData")
	@RequiresPermissions("sys:user:list")
	public R listData(@RequestParam Map<String, Object> params){
		//只有超级管理员，才能查看所有管理员列表
		if(getUserId() != Constant.SUPER_ADMIN){
			params.put("createUserId", getUserId());
		}

		//查询列表数据
		Query query = new Query(params);
		List<SysUser> userList = sysUserService.queryUserList(query);
		for (SysUser sysUser: userList) {
			sysUser.setAddressTxt(sysUserService.getAddressTxtFromAddressIds(sysUser.getAddress()));
			sysUser.setAvatar(sysUserService.getAvatar(sysUser.getAvatar()));
		}
		int total = sysUserService.queryUserTotal(query);

		PageUtils pageUtil = new PageUtils(userList, total, query.getLimit(), query.getPage());

		return R.ok().put("page", pageUtil);
	}


	/**
	 * 跳转到添加页面
	 */
	@RequestMapping("/add")
	public String add(){
		return "user/add";
	}
	/**
	 * @author chenyi
	 * @Description  跳转到修改页面
	 * @param
	 * @date 2017/6/27 11:17
	 **/
	@RequestMapping("/edit/{id}")
	@RequiresPermissions("sys:user:update")
	public String edit(HttpServletRequest request, Model model, @PathVariable("id") Long id){
		SysUser user = sysUserService.queryObject(id);
		UrlValidator urlValidator = new UrlValidator();
		String imageId = UUID.randomUUID().toString();
		CloudStorageService cloudStorageService = OSSFactory.build();
		if (StringUtility.isNotEmpty(user.getAvatar()) && urlValidator.isValid(user.getAvatar())) {
			try {
				URL url = new URL(user.getAvatar());
				URLConnection conn = url.openConnection();

				String imageUrl = cloudStorageService.uploadSuffix(conn.getInputStream(), ".jpg");
				File imageFile = new File();
				imageFile.setUploadId(imageId);
				imageFile.setUrl(imageUrl);
				imageFile.setFileName(user.getNickname() + "_avatar.jpg");
				imageFile.setOssYn("Y");
				imageFile.setFileType("image");
				imageFile.setFileSize(Integer.toString(conn.getContentLength()));
				if (ShiroUtils.getUserId() != 1L){
					imageFile.setSpaceId(ShiroUtils.getSpaceId());
				}
				fileService.save(imageFile);
				user.setAvatar(imageId);
				sysUserService.updateData(user);
			} catch (Exception e){

			}
		}
		model.addAttribute("model",user);
		//获取所属角色
		List<Long> roleIds=sysUserRoleService.queryRoleIdList(user.getUserId());
		//将list转为字符串
		String roleIdList=StringUtils.join(roleIds.toArray(),",");
		model.addAttribute("roleIdList",roleIdList);
		return "/user/edit";
	}
	/**
	 * 获取登录的用户信息
	 */
	@ResponseBody
	@RequestMapping("/info")
	public R info(){
		return R.ok().put("user", getUser());
	}
	
	/**
	 * 修改登录用户密码
	 */
	@ResponseBody
	@SysLog("修改用户密码")
	@RequestMapping("/password")
	public R password(String password, String newPassword){

		
		//sha256加密
		password = new Sha256Hash(password).toHex();
		//sha256加密
		newPassword = new Sha256Hash(newPassword).toHex();
				
		//更新密码
		int count = sysUserService.updatePassword(getUserId(), password, newPassword);
		if(count == 0){
			return R.error("原密码不正确");
		}
		
		//退出
		ShiroUtils.logout();
		
		return R.ok();
	}
	
	/**
	 * 用户信息
	 */
	@ResponseBody
	@RequestMapping("/info/{userId}")
	@RequiresPermissions("sys:user:info")
	public R info(@PathVariable("userId") Long userId){
		SysUser user = sysUserService.queryObject(userId);
		
		//获取用户所属的角色列表
		List<Long> roleIdList = sysUserRoleService.queryRoleIdList(userId);
		user.setRoleIdList(roleIdList);
		
		return R.ok().put("user", user);
	}
	
	/**
	 * 保存用户
	 */
	@ResponseBody
	@SysLog("保存用户")
	@RequestMapping("/save")
	@RequiresPermissions("sys:user:save")
	public R save(@RequestBody Map<String, Object> params){
		Gson gson = new Gson();
		JsonElement jsonElement = gson.toJsonTree(params);
		SysUser user = gson.fromJson(jsonElement, SysUser.class);

		List<String> areaCodeList = (ArrayList) params.get("areaCode");
		String address = String.join(",", areaCodeList);
		List<Long> roleIdList = new ArrayList<>();
		try {
			roleIdList = (ArrayList) params.get("roleIdList1");
			if (roleIdList.size() > 0 && StringUtility.isEmpty(roleIdList.get(0).toString())){
				roleIdList = null;
			}
		} catch (Exception e){
			roleIdList = null;
		}
		user.setRoleIdList(roleIdList);
		user.setAddress(address);
		verifyForm(user);
		SysUser existUser = sysUserService.queryByUserName(user.getUsername());
		if(existUser!=null){
			return R.error("用户名已存在!");
		}
		SysUser phoneUser = sysUserService.queryByPhone(user.getPhoneNo());
		if(phoneUser!=null){
			return R.error("手机号已存在!");
		}
		user.setStatus(1);
		user.setUserClassCode(SysUser.USER);
		user.setPassword(new Sha256Hash(DefaultEnum.PASSWORD.getCode()).toHex());
		user.setCreateUserId(getUserId());
		sysUserService.save(user);
		
		return R.ok();
	}
	
	/**
	 * 修改用户
	 */
	@ResponseBody
	@SysLog("修改用户")
	@RequestMapping("/update")
	@RequiresPermissions("sys:user:update")
	public R update(@RequestBody Map<String, Object> params){
		Gson gson = new Gson();
		JsonElement jsonElement = gson.toJsonTree(params);
		SysUser user = gson.fromJson(jsonElement, SysUser.class);

		List<String> areaCodeList = (ArrayList) params.get("areaCode");
		String address = String.join(",", areaCodeList);
		List<Long> roleIdList = new ArrayList<>();
		try {
			roleIdList = (ArrayList) params.get("roleIdList1");
			if (roleIdList.size() > 0 && StringUtility.isEmpty(roleIdList.get(0).toString())){
				roleIdList = null;
			}
		} catch (Exception e){
			roleIdList = null;
		}
		user.setRoleIdList(roleIdList);
		user.setAddress(address);
//		verifyForm(user);
		user.setUserClassCode(SysUser.USER);
		user.setCreateUserId(getUserId());
		sysUserService.update(user);
		//todo kj
		
		return R.ok();
	}
	
	/**
	 * 删除用户
	 */
	@ResponseBody
	@SysLog("删除用户")
	@RequestMapping("/delete")
	@RequiresPermissions("sys:user:delete")
	public R delete(@RequestBody Long[] userIds){
		if(ArrayUtils.contains(userIds, 1L)){
			return R.error("系统管理员不能删除");
		}
		
		if(ArrayUtils.contains(userIds, getUserId())){
			return R.error("当前用户不能删除");
		}
		
		sysUserService.deleteBatch(userIds);
		
		return R.ok();
	}

	/**
	 * 初始化密码
	 */
	@ResponseBody
	@SysLog("初始化用户密码")
	@RequestMapping("/initPassword")
	@RequiresPermissions("sys:user:list")
	public R initPassword(@RequestBody Long[] userIds){
		if(ArrayUtils.contains(userIds, 1L)){
			return R.error("系统管理员不能初始化密码");
		}
		sysUserService.initPassword(userIds);

		return R.ok();
	}

	/**
	 * 验证参数是否正确
	 */
	private void verifyForm(SysUser user){

		if(StringUtils.isEmpty(user.getPhoneNo())){
			throw new MyException("手机号不能为空");
		}

//		if(StringUtils.isEmpty(user.getEmail())){
//			throw new MyException("邮箱不能为空");
//		}
	}

	/**
	 * 启用
	 */
	@ResponseBody
	@SysLog("启用用户")
	@RequestMapping("/enable")
	@RequiresPermissions("sys:user:update")
	public R enable(@RequestBody String[] ids){
		String stateValue= StateEnum.ENABLE.getCode();
		sysUserService.updateState(ids,stateValue);
		return R.ok();
	}
	/**
	 * 禁用
	 */
	@ResponseBody
	@SysLog("禁用用户")
	@RequestMapping("/limit")
	@RequiresPermissions("sys:user:update")
	public R limit(@RequestBody String[] ids){
		String stateValue=StateEnum.LIMIT.getCode();
		sysUserService.updateState(ids,stateValue);
		return R.ok();
	}
}
