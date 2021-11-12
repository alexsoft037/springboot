package xin.xiaoer.modules.monitor.service;

import xin.xiaoer.modules.monitor.entity.SpaceCamera;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-09-03 18:37:09
 */
public interface SpaceCameraService {
	
	SpaceCamera get(Integer id);
	
	List<SpaceCamera> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(SpaceCamera spaceCamera);
	
	void update(SpaceCamera spaceCamera);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);

    void updateState(String[] ids, String stateValue);
}
