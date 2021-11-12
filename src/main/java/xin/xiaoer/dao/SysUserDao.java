package xin.xiaoer.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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
 * @date 2016年9月18日 上午9:34:11
 */
@Mapper
public interface SysUserDao extends BaseDao<SysUser> {
	
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
	 * 修改密码
	 */
	int updatePassword(Map<String, Object> map);

	int updateSession(Map<String, Object> map);

    void deleteUserRole(Long[] userId);

    int insertSelective(SysUser member);

	int checkDuplicateUser(SysUser member);

	int queryOnlineTotal();

	SysUser getNewLogin(@Param("userId") Long userId, @Param("deviceId") String deviceId);

	List<SysUser> queryUserList(Map<String, Object> map);

	int queryUserTotal(Map<String, Object> map);

	List<SysUser> queryAdminList(Map<String, Object> map);

	int queryAdminTotal(Map<String, Object> map);

	List<SysUser> queryAdminRoleList(Map<String, Object> map);

	int queryAdminRoleTotal(Map<String, Object> map);

    int getVolunteerCount();

    Integer getNationalRanking(@Param("userId") Long userId);

	List<String> queryBySendTarget(Map<String, Object> params);

    List<SysUserIntegralRankItem> getUserIntegralRank(Query query);

    List<Map<String, String>> getvolunteerRankAtHonorList(Map<String, String> params);

    List<String> getAccidByRoleId(@Param("roleId") String s);

    List<String> getRemark(@Param("userId") Long userId);
}
