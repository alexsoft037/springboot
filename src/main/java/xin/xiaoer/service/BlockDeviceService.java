package xin.xiaoer.service;

import xin.xiaoer.entity.BlockDevice;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-10-29 10:39:46
 */
public interface BlockDeviceService {
	
	BlockDevice get(Long id);
	
	List<BlockDevice> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(BlockDevice blockDevice);
	
	void update(BlockDevice blockDevice);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);

    void updateState(String[] ids, String stateValue);

	BlockDevice getByUserAndDevice(Long userId, String deviceId);

	int deleteByUserAndDevice(Long userId, String deviceId);
}
