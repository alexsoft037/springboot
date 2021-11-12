package xin.xiaoer.modules.classroom.dao;

import org.apache.ibatis.annotations.Param;
import xin.xiaoer.dao.BaseDao;
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
public interface UserLessonDao extends BaseDao<UserLesson> {

    UserLesson getByUserAndLesson(@Param("lessonId") Integer lessonId, @Param("userId") Long userId);

    List getByUser(Map<String, Object> map);

    int getUserCount(Map<String, Object> map);
}
