package xin.xiaoer.modules.monitor.service;

import xin.xiaoer.modules.monitor.entity.ChildrenTrajectory;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-09-03 12:51:23
 */
public interface ChildrenTrajectoryService {
	
	ChildrenTrajectory get(Long id);
	
	List<ChildrenTrajectory> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(ChildrenTrajectory childrenTrajectory);
	
	void update(ChildrenTrajectory childrenTrajectory);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);

    void updateState(String[] ids, String stateValue);

	List<ChildrenTrajectory> getPointList(Map<String, Object> map);

	List<String> getTimeList(String day);

	List<ChildrenTrajectory> getTimeDataList(String time);
}
