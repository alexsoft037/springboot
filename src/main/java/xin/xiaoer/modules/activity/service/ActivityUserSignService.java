package xin.xiaoer.modules.activity.service;

import xin.xiaoer.modules.activity.entity.ActivityUserSign;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-10-08 01:01:48
 */
public interface ActivityUserSignService {
	
	ActivityUserSign get(Long id);
	
	List<ActivityUserSign> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(ActivityUserSign activityUserSign);
	
	void update(ActivityUserSign activityUserSign);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);

    void updateState(String[] ids, String stateValue);

	ActivityUserSign getByUserAndActivity(Long userId, Integer activityId);
}
