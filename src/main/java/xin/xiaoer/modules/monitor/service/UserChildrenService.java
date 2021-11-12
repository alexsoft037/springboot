package xin.xiaoer.modules.monitor.service;

import xin.xiaoer.entity.CodeValue;
import xin.xiaoer.modules.monitor.entity.UserChildren;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-09-03 12:51:23
 */
public interface UserChildrenService {
	
	UserChildren get(Long childrenId);
	
	List<UserChildren> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(UserChildren userChildren);
	
	void update(UserChildren userChildren);
	
	void delete(Long childrenId);
	
	void deleteBatch(Long[] childrenIds);

    void updateState(String[] ids, String stateValue);

	List<CodeValue> getCodeValues(Map<String, Object> map);
}
