package xin.xiaoer.modules.activity.service;

import io.swagger.models.auth.In;
import xin.xiaoer.modules.activity.entity.ActivityAttend;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-07-24 12:11:49
 */
public interface ActivityAttendService {
	
	ActivityAttend get(Long activityId);

	ActivityAttend getByUserIdAndActivityId(Integer activityId, Long userId);

	List<ActivityAttend> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(ActivityAttend activityAttend);
	
	void update(ActivityAttend activityAttend);
	
	void delete(Long activityId);
	
	void deleteBatch(Long[] activityIds);

    void updateState(String[] ids, String stateValue);

	Integer getCountByActivityId(Integer activityId);

	int deleteByActivityId(Object id);

	int queryAttendTotal();
}
