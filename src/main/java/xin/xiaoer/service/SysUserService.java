package xin.xiaoer.service;

import xin.xiaoer.common.utils.Query;
import xin.xiaoer.entity.SysUser;
import xin.xiaoer.modules.website.entity.SysUserIntegralRankItem;

import java.util.List;
import java.util.Map;


/**
 * 系统用户
 * 
 * @author chenyi
 * @email 228112142@qq.com
 * @date 2016年9月18日 上午9:43:39
 */
public interface SysUserService {
	
	/**
	 * 查询用户的所有权限
	 * @param userId  用户ID
	 */
	List<String> queryAllPerms(Long userId);
	
	/**
	 * 查询用户的所有菜单ID
	 */
	List<Long> queryAllMenuId(Long userId);
	
	/**
	 * 根据用户名，查询系统用户
	 */
	SysUser queryByUserName(String username);

	SysUser queryAdminByUserName(String username);

	SysUser queryByPhone(String phoneNo);
	/**
	 * 根据用户ID，查询用户
	 * @param userId
	 * @return
	 */
	SysUser queryObject(Long userId);
	
	/**
	 * 查询用户列表
	 */
	List<SysUser> queryList(Map<String, Object> map);
	
	/**
	 * 查询总数
	 */
	int queryTotal(Map<String, Object> map);
	
	/**
	 * 保存用户
	 */
	void save(SysUser user);

	/**
	 * 保存用户
	 */
	void insert(SysUser user);
	
	/**
	 * 修改用户
	 */
	void update(SysUser user);

	/**
	 * 修改用户
	 */
	void updateData(SysUser user);
	
	/**
	 * 删除用户
	 */
	void deleteBatch(Long[] userIds);
	
	/**
	 * 修改密码
	 * @param userId       用户ID
	 * @param password     原密码
	 * @param newPassword  新密码
	 */
	int updatePassword(Long userId, String password, String newPassword);

	int updateSession(Map<String, Object> map);

    void initPassword(Long[] userIds);

	int insertSelective(SysUser member);

	public boolean checkDuplicateUser(SysUser user);

	void updateState(String[] ids, String stateValue);

	int queryOnlineTotal();

	String getAvatar(SysUser sysUser);

	String getAvatar(String avatar);

	String getAddressTxtFromAddressIds(String addressIds);

	SysUser getNewLogin(Long userId, String deviceId);

	List<SysUser> queryUserList(Map<String, Object> map);

	int queryUserTotal(Map<String, Object> map);

	List<SysUser> queryAdminList(Map<String, Object> map);

	int queryAdminTotal(Map<String, Object> map);

	List<SysUser> queryAdminRoleList(Map<String, Object> map);

	int queryAdminRoleTotal(Map<String, Object> map);

    int getVolunteerCount();

    Integer getNationalRanking(Long userId);

	List<String> queryBySendTarget(Map<String, Object> params);

    List<SysUserIntegralRankItem> getUserIntegralRank(Query query);

	int getCount(Query query);

    SysUser get(Long userId);

    List<Map<String, String>> getvolunteerRankAtHonorList(Map<String, String> params);

    List<String> getAccidByRoleId(String s);

    String getRemark(Long userId);
}
