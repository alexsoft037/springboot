package xin.xiaoer.modules.classroom.service;

import xin.xiaoer.modules.classroom.entity.StudyAttend;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-08-20 16:25:03
 */
public interface StudyAttendService {
	
	StudyAttend get(Long id);
	
	List<StudyAttend> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(StudyAttend studyAttend);
	
	void update(StudyAttend studyAttend);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);

    void updateState(String[] ids, String stateValue);

	int getCountByRoomId(Integer roomId);

	StudyAttend getByUserAndRoom(Integer roomId, Long userId);
}
