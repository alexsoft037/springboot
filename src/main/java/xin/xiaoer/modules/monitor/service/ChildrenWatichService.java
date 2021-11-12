package xin.xiaoer.modules.monitor.service;

import xin.xiaoer.modules.monitor.entity.ChildrenWatich;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-09-04 11:22:31
 */
public interface ChildrenWatichService {
	
	ChildrenWatich get(Long watchId);
	
	List<ChildrenWatich> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(ChildrenWatich childrenWatich);
	
	void update(ChildrenWatich childrenWatich);
	
	void delete(Long watchId);
	
	void deleteBatch(Long[] watchIds);

    void updateState(String[] ids, String stateValue);

	ChildrenWatich getByDeviceId(String deviceID);
}
