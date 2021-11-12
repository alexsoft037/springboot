package xin.xiaoer.modules.guoshan.service;

import xin.xiaoer.modules.guoshan.entity.GuoshanUser;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-08-03 12:44:53
 */
public interface GuoshanUserService {
	
	GuoshanUser get(Long userId);
	
	List<GuoshanUser> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(GuoshanUser guoshanUser);
	
	void update(GuoshanUser guoshanUser);
	
	void delete(Long userId);
	
	void deleteBatch(Long[] userIds);

    void updateState(String[] ids, String stateValue);

	GuoshanUser getByVolunteerId(String volunteerId);

	int getVolCount();

}
