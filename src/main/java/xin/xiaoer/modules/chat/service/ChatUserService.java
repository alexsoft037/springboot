package xin.xiaoer.modules.chat.service;

import xin.xiaoer.modules.chat.entity.ChatUser;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-09-11 22:35:19
 */
public interface ChatUserService {
	
	ChatUser get(Long userId);
	
	List<ChatUser> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(ChatUser chatUser);
	
	void update(ChatUser chatUser);
	
	void delete(Long userId);
	
	void deleteBatch(Long[] userIds);

    void updateState(String[] ids, String stateValue);

	List<ChatUser> getGroupUserList(Integer spaceId);

    List<Long> queryByAccids(List<String> accids);
}
