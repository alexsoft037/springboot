package xin.xiaoer.modules.activity.service;

import xin.xiaoer.modules.activity.entity.ActivitySignNumber;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-10-04 00:05:33
 */
public interface ActivitySignNumberService {
	
	ActivitySignNumber get(Integer activityId);
	
	List<ActivitySignNumber> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(ActivitySignNumber activitySignNumber);
	
	void update(ActivitySignNumber activitySignNumber);
	
	void delete(Integer activityId);
	
	void deleteBatch(Integer[] activityIds);

    void updateState(String[] ids, String stateValue);
}
