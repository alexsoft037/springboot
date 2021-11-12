package xin.xiaoer.modules.classroom.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

import xin.xiaoer.modules.classroom.dao.UserLessonDao;
import xin.xiaoer.modules.classroom.entity.UserLesson;
import xin.xiaoer.modules.classroom.service.UserLessonService;




@Service("userLessonService")
@Transactional
public class UserLessonServiceImpl implements UserLessonService {
	@Autowired
	private UserLessonDao userLessonDao;
	
	@Override
	public UserLesson get(Integer id){
		return userLessonDao.get(id);
	}

	@Override
	public List<UserLesson> getList(Map<String, Object> map){
		return userLessonDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return userLessonDao.getCount(map);
	}

	@Override
	public void save(UserLesson userLesson){
		userLessonDao.save(userLesson);
	}

	@Override
	public void update(UserLesson userLesson){
		userLessonDao.update(userLesson);
	}

	@Override
	public void delete(Integer id){
		userLessonDao.delete(id);
	}

	@Override
	public void deleteBatch(Integer[] ids){
		userLessonDao.deleteBatch(ids);
	}

    @Override
    public void updateState(String[] ids,String stateValue) {
        for (String id:ids){
			UserLesson userLesson=get(Integer.parseInt(id));
            update(userLesson);
        }
    }

    @Override
	public UserLesson getByUserAndLesson(Integer lessonId, Long userId){
		return userLessonDao.getByUserAndLesson(lessonId, userId);
	}

	@Override
	public List getByUser(Map<String, Object> map) {
		return userLessonDao.getByUser(map);
	}

	@Override
	public int getUserCount(Map<String, Object> map) {
		return userLessonDao.getUserCount(map);
	}
}
