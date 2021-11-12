package xin.xiaoer.modules.classroom.service;

import xin.xiaoer.modules.classroom.entity.LiveRoom;
import xin.xiaoer.modules.mobile.entity.LiveRoomListItem;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-09-19 10:27:23
 */
public interface LiveRoomService {
	
	LiveRoom get(Integer liveId);
	
	List<LiveRoom> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(LiveRoom liveRoom);
	
	void update(LiveRoom liveRoom);
	
	void delete(Integer liveId);
	
	void deleteBatch(Integer[] liveIds);

    String updateState(String[] ids, String stateValue);

	List<LiveRoomListItem> getFeaturedList(Map<String, Object> map);

	List<LiveRoomListItem> getAPIList(Map<String, Object> map);

	int getAPICount(Map<String, Object> map);

	LiveRoomListItem getListItemObject(Map search);

	LiveRoom getDetail(Integer liveId, Long userId);

	LiveRoom getUserPauseLive(Long userId);
}
