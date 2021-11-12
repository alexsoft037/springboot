package xin.xiaoer.modules.classroom.service;

import xin.xiaoer.modules.classroom.entity.LiveRoomMessage;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-10-06 13:14:35
 */
public interface LiveRoomMessageService {
	
	LiveRoomMessage get(Integer liveId);
	
	List<LiveRoomMessage> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(LiveRoomMessage liveRoomMessage);
	
	void update(LiveRoomMessage liveRoomMessage);
	
	void delete(Integer liveId);
	
	void deleteBatch(Integer[] liveIds);

    void updateState(String[] ids, String stateValue);

	int getCountByLive(Long liveId);
}
