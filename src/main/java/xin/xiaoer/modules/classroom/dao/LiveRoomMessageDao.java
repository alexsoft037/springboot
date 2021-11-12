package xin.xiaoer.modules.classroom.dao;

import xin.xiaoer.dao.BaseDao;
import xin.xiaoer.modules.classroom.entity.LiveRoomMessage;

/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-10-06 13:14:35
 */
public interface LiveRoomMessageDao extends BaseDao<LiveRoomMessage> {

	int getCountByLive(Long liveId);
}
