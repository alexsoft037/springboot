package xin.xiaoer.modules.classroom.service;

import xin.xiaoer.modules.classroom.entity.UserLesson;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-08-21 18:17:04
 */
public interface UserLessonService {
	
	UserLesson get(Integer id);
	
	List<UserLesson> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(UserLesson userLesson);
	
	void update(UserLesson userLesson);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);

    void updateState(String[] ids, String stateValue);

	UserLesson getByUserAndLesson(Integer lessonId, Long userId);

	List getByUser(Map<String, Object> map);

	int getUserCount(Map<String, Object> map);
}
