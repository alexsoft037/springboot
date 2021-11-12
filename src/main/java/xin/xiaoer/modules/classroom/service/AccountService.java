package xin.xiaoer.modules.classroom.service;

import xin.xiaoer.modules.classroom.entity.Account;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-09-18 19:00:52
 */
public interface AccountService {
	
	Account get(String uid);
	
	List<Account> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(Account account);
	
	void update(Account account);
	
	void delete(String uid);
	
	void deleteBatch(String[] uids);

    void updateState(String[] ids, String stateValue);
}
