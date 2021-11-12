package xin.xiaoer.modules.setting.service;

import xin.xiaoer.modules.setting.entity.Notification;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-10-16 22:21:41
 */
public interface NotificationService {
	
	Notification get(Long noteId);
	
	List<Notification> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(Notification notification);
	
	void update(Notification notification);
	
	void delete(Long noteId);
	
	void deleteBatch(Long[] noteIds);

    void updateState(String[] ids, String stateValue);
}
