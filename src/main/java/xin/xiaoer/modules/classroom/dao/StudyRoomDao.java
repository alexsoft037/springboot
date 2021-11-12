package xin.xiaoer.modules.classroom.dao;

import org.apache.ibatis.annotations.Param;
import xin.xiaoer.dao.BaseDao;
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
public interface StudyRoomDao extends BaseDao<StudyRoom> {

    List<StudyRoomListItem> getListData(Map<String, Object> params);

    int getCountData(Map<String, Object> params);

    List<StudyRoomListItem> getListDataByUser(Map<String, Object> params);

    int getCountDataByUser(Map<String, Object> params);

    StudyRoomListItem getListItemOjbect(@Param("roomId") Integer roomId);

    //新加
    List<StudyRoom> getFeaturedList(Map<String, Object> params);

    List getstudyList(Map<String, Object> params);

    int getstudyCount(Map<String, Object> params);
}
