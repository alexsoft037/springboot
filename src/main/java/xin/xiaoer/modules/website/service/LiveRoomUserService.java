package xin.xiaoer.modules.website.service;

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
public interface LiveRoomUserService {
    void save(LiveRoomUser liveRoomUser);

    void deleteById(Long userId,Long liveId);

    List<LiveRoomUser> getList(Map<String, Object> map);

    List<LiveRoomUserListItem> getUserList(Map<String, Object> map);

    int getCountById(Long liveId);

    LiveRoomUser getDetail(Long liveId,Long userId);

    void update(LiveRoomUser liveRoomUser);
}
