package xin.xiaoer.modules.monitor.service;

import xin.xiaoer.modules.monitor.entity.CameraCapture;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-09-06 13:29:33
 */
public interface CameraCaptureService {
	
	CameraCapture get(Long id);
	
	List<CameraCapture> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(CameraCapture cameraCapture);
	
	void update(CameraCapture cameraCapture);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);

    void updateState(String[] ids, String stateValue);

	List<String> getDateList(Map<String, Object> map);

	List<CameraCapture> getCaptureList(Map<String, Object> map);
}
