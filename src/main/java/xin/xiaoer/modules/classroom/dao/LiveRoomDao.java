package xin.xiaoer.modules.classroom.dao;

import org.apache.ibatis.annotations.Param;
import xin.xiaoer.dao.BaseDao;
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
public interface LiveRoomDao extends BaseDao<LiveRoom> {

    List<LiveRoomListItem> getFeaturedList(Map<String, Object> map);

    List<LiveRoomListItem> getAPIList(Map<String, Object> map);

    int getAPICount(Map<String, Object> map);

    LiveRoomListItem getListItemObject(Map search);

    LiveRoom getDetail(@Param("liveId") Integer liveId, @Param("userId") Long userId);

    LiveRoom getUserPauseLive(@Param("userId") Long userId);
}
