package xin.xiaoer.modules.website.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xin.xiaoer.entity.SysUser;
import xin.xiaoer.modules.website.dao.LiveRoomUserDao;
import xin.xiaoer.modules.website.entity.LiveRoomUser;
import xin.xiaoer.modules.website.entity.LiveRoomUserListItem;
import xin.xiaoer.modules.website.service.LiveRoomUserService;

import java.util.List;
import java.util.Map;


/**
 *
 *
 * @author daimingjian
 * @email 3088393266@qq.com
 * @date 2019-1-15 10:39:46
 */
@Service("LiveRoomUserService")
public class LiveRoomUserServiceImpl implements LiveRoomUserService{
    @Autowired
    private LiveRoomUserDao liveRoomUserDao;

    @Override
    public void save(LiveRoomUser liveRoomUser){
        liveRoomUserDao.save(liveRoomUser);
    }

    public void deleteById(Long userId,Long liveId){
        liveRoomUserDao.deleteById(userId,liveId);
    }

    public List<LiveRoomUser> getList(Map<String, Object> map){
        return null;
    }

    public List<LiveRoomUserListItem> getUserList(Map<String, Object> map){
        return liveRoomUserDao.getUserList(map);
    }


    public int getCountById(Long liveId){
        return liveRoomUserDao.getCountById(liveId);
    }

    public LiveRoomUser getDetail(Long liveId,Long userId){
        return liveRoomUserDao.getDetail(liveId,userId);
    }

    public void update(LiveRoomUser liveRoomUser){
        liveRoomUserDao.update(liveRoomUser);
    }

}
