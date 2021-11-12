package xin.xiaoer.modules.monitor.service;

import xin.xiaoer.modules.monitor.entity.CameraRecord;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-11-12 07:56:35
 */
public interface CameraRecordService {
	
	CameraRecord get(Long id);
	
	List<CameraRecord> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(CameraRecord cameraRecord);
	
	void update(CameraRecord cameraRecord);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);

    void updateState(String[] ids, String stateValue);
}
