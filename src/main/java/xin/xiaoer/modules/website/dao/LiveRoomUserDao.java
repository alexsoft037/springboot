package xin.xiaoer.modules.website.dao;

import org.apache.ibatis.annotations.Param;
import xin.xiaoer.dao.BaseDao;
import xin.xiaoer.entity.SysUser;
import xin.xiaoer.modules.website.entity.LiveRoomUser;
import xin.xiaoer.modules.website.entity.LiveRoomUserListItem;

import java.util.List;
import java.util.Map;


/**
 *
 *
 * @author daimingjian
 * @email 3088393266@qq.com
 * @date 2019-1-15 10:39:46
 */
public interface LiveRoomUserDao extends BaseDao<LiveRoomUser> {
    int getCountById(@Param("liveId") Long liveId);

    void deleteById(@Param("userId") Long userId,@Param("liveId") Long liveId);

    List<LiveRoomUserListItem> getUserList(Map<String, Object> map);

    LiveRoomUser getDetail(@Param("liveId") Long liveId,@Param("userId") Long userId);
}
