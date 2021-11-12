package xin.xiaoer.modules.classroom.dao;

import org.apache.ibatis.annotations.Param;
import xin.xiaoer.dao.BaseDao;
import xin.xiaoer.modules.classroom.entity.StudyAttend;

import java.util.Map;

/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-08-20 16:25:03
 */
public interface StudyAttendDao extends BaseDao<StudyAttend> {

    int getCountByRoomId(@Param("roomId") Integer roomId);

    StudyAttend getByUserAndRoom(@Param("roomId") Integer roomId, @Param("userId") Long userId);
}
