package xin.xiaoer.modules.classroom.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

import xin.xiaoer.modules.classroom.dao.LiveRoomMessageDao;
import xin.xiaoer.modules.classroom.entity.LiveRoomMessage;
import xin.xiaoer.modules.classroom.service.LiveRoomMessageService;




@Service("liveRoomMessageService")
@Transactional
public class LiveRoomMessageServiceImpl implements LiveRoomMessageService {
	@Autowired
	private LiveRoomMessageDao liveRoomMessageDao;
	
	@Override
	public LiveRoomMessage get(Integer liveId){
		return liveRoomMessageDao.get(liveId);
	}

	@Override
	public List<LiveRoomMessage> getList(Map<String, Object> map){
		return liveRoomMessageDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return liveRoomMessageDao.getCount(map);
	}

	@Override
	public void save(LiveRoomMessage liveRoomMessage){
		liveRoomMessageDao.save(liveRoomMessage);
	}

	@Override
	public void update(LiveRoomMessage liveRoomMessage){
		liveRoomMessageDao.update(liveRoomMessage);
	}

	@Override
	public void delete(Integer liveId){
		liveRoomMessageDao.delete(liveId);
	}

	@Override
	public void deleteBatch(Integer[] liveIds){
		liveRoomMessageDao.deleteBatch(liveIds);
	}

    @Override
    public void updateState(String[] ids,String stateValue) {
        for (String id:ids){
			LiveRoomMessage liveRoomMessage=get(Integer.parseInt(id));
//			liveRoomMessage.setState(stateValue);
            update(liveRoomMessage);
        }
    }
	@Override
	public int getCountByLive(Long liveId){
		return liveRoomMessageDao.getCountByLive(liveId);
	}
}
