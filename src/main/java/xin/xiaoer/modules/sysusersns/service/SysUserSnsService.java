package xin.xiaoer.modules.sysusersns.service;

import xin.xiaoer.modules.sysusersns.entity.SysUserSns;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author chenyi
 * @email 228112142@qq.com
 * @date 2018-07-08 17:49:46
 */

public interface SysUserSnsService {
	
	SysUserSns get(Long userId);
	
	List<SysUserSns> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(SysUserSns sysUserSns);
	
	void update(SysUserSns sysUserSns);
	
	void delete(Long userId);
	
	void deleteBatch(Long[] userIds);

    void updateState(String[] ids, String stateValue);

	SysUserSns getBySns(String snsUserId, String snsType);

	int updateUserId(SysUserSns sysUserSns);

	SysUserSns getBySnsUserId(String open, String snsType);
}
