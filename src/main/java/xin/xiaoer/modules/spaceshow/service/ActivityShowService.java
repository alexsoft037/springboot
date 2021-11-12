package xin.xiaoer.modules.spaceshow.service;

import xin.xiaoer.modules.spaceshow.entity.ActivityShow;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-08-24 23:03:45
 */
public interface ActivityShowService {
	
	ActivityShow get(Integer id);
	
	List<ActivityShow> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(ActivityShow activityShow);
	
	void update(ActivityShow activityShow);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);

    void updateState(String[] ids, String stateValue);
}
