package xin.xiaoer.modules.classroom.service;

import xin.xiaoer.modules.classroom.entity.StudyRoom;
import xin.xiaoer.modules.mobile.entity.StudyRoomListItem;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-08-20 13:04:31
 */
public interface StudyRoomService {
	
	StudyRoom get(Integer roomId);
	
	List<StudyRoom> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(StudyRoom studyRoom);
	
	void update(StudyRoom studyRoom);
	
	void delete(Integer roomId);
	
	void deleteBatch(Integer[] roomIds);

    void updateState(String[] ids, String stateValue);

	List<StudyRoomListItem> getListData(Map<String, Object> params);

	int getCountData(Map<String, Object> params);

	List<StudyRoomListItem> getListDataByUser(Map<String, Object> params);

	int getCountDataByUser(Map<String, Object> params);

	StudyRoomListItem getListItemOjbect(Integer roomId);

	//新加
	List<StudyRoom> getFeaturedList(Map<String, Object> params);

	List getstudyList(Map<String, Object> params);

	int getstudyCount(Map<String, Object> params);
}
