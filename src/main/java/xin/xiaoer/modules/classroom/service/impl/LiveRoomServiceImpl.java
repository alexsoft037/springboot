package xin.xiaoer.modules.classroom.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

import xin.xiaoer.modules.classroom.dao.LiveRoomDao;
import xin.xiaoer.modules.classroom.entity.LiveRoom;
import xin.xiaoer.modules.classroom.service.LiveRoomService;
import xin.xiaoer.modules.mobile.entity.LiveRoomListItem;


@Service("liveRoomService")
@Transactional
public class LiveRoomServiceImpl implements LiveRoomService {
	@Autowired
	private LiveRoomDao liveRoomDao;
	
	@Override
	public LiveRoom get(Integer liveId){
		return liveRoomDao.get(liveId);
	}

	@Override
	public List<LiveRoom> getList(Map<String, Object> map){
		return liveRoomDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return liveRoomDao.getCount(map);
	}

	@Override
	public void save(LiveRoom liveRoom){
		liveRoomDao.save(liveRoom);
	}

	@Override
	public void update(LiveRoom liveRoom){
		liveRoomDao.update(liveRoom);
	}

	@Override
	public void delete(Integer liveId){
		liveRoomDao.delete(liveId);
	}

	@Override
	public void deleteBatch(Integer[] liveIds){
		liveRoomDao.deleteBatch(liveIds);
	}

    @Override
    public String updateState(String[] ids,String stateValue) {
        for (String id:ids){
			LiveRoom liveRoom=get(Integer.parseInt(id));
			if(liveRoom==null){
				return id;
			}
			liveRoom.setState(stateValue);
            update(liveRoom);
        }
        return null;
    }

	@Override
	public List<LiveRoomListItem> getFeaturedList(Map<String, Object> map){
		return liveRoomDao.getFeaturedList(map);
	}

	@Override
	public List<LiveRoomListItem> getAPIList(Map<String, Object> map){
		return liveRoomDao.getAPIList(map);
	}

	@Override
	public int getAPICount(Map<String, Object> map){
		return liveRoomDao.getAPICount(map);
	}

	@Override
	public LiveRoomListItem getListItemObject(Map search){
		return liveRoomDao.getListItemObject(search);
	}

	@Override
	public LiveRoom getDetail(Integer liveId, Long userId){
		return liveRoomDao.getDetail(liveId, userId);
	}

	@Override
	public LiveRoom getUserPauseLive(Long userId){
		return liveRoomDao.getUserPauseLive(userId);
	}
}
