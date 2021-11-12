package xin.xiaoer.modules.chat.service;

import xin.xiaoer.modules.chat.entity.ChatGroup;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-09-12 18:21:56
 */
public interface ChatGroupService {
	
	ChatGroup get(Integer spaceId);
	
	List<ChatGroup> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(ChatGroup chatGroup);
	
	void update(ChatGroup chatGroup);
	
	void delete(Integer spaceId);
	
	void deleteBatch(Integer[] spaceIds);

    void updateState(String[] ids, String stateValue);

	List<ChatGroup> getListByUser(Long userId);

	List<ChatGroup> getAdminList(Map<String, Object> map);

	int getAdminCount(Map<String, Object> map);
}
