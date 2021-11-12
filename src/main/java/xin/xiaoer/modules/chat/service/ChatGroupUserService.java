package xin.xiaoer.modules.chat.service;

import xin.xiaoer.modules.chat.entity.ChatGroupUser;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-09-12 18:28:36
 */
public interface ChatGroupUserService {
	
	ChatGroupUser get(Long id);
	
	List<ChatGroupUser> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(ChatGroupUser chatGroupUser);
	
	void update(ChatGroupUser chatGroupUser);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);

    void updateState(String[] ids, String stateValue);

	ChatGroupUser getBySpaceAndUser(Long userId, Integer spaceId);
}
