package xin.xiaoer.modules.classroom.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

import xin.xiaoer.modules.classroom.dao.StudyRoomDao;
import xin.xiaoer.modules.classroom.entity.StudyRoom;
import xin.xiaoer.modules.classroom.service.StudyRoomService;
import xin.xiaoer.modules.mobile.entity.StudyRoomListItem;


@Service("studyRoomService")
@Transactional
public class StudyRoomServiceImpl implements StudyRoomService {
	@Autowired
	private StudyRoomDao studyRoomDao;
	
	@Override
	public StudyRoom get(Integer roomId){
		return studyRoomDao.get(roomId);
	}

	@Override
	public List<StudyRoom> getList(Map<String, Object> map){
		return studyRoomDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return studyRoomDao.getCount(map);
	}

	@Override
	public void save(StudyRoom studyRoom){
		studyRoomDao.save(studyRoom);
	}

	@Override
	public void update(StudyRoom studyRoom){
		studyRoomDao.update(studyRoom);
	}

	@Override
	public void delete(Integer roomId){
		studyRoomDao.delete(roomId);
	}

	@Override
	public void deleteBatch(Integer[] roomIds){
		studyRoomDao.deleteBatch(roomIds);
	}

    @Override
    public void updateState(String[] ids,String stateValue) {
        for (String id:ids){
			StudyRoom studyRoom=get(Integer.parseInt(id));
			studyRoom.setState(stateValue);
            update(studyRoom);
        }
    }

    @Override
	public List<StudyRoomListItem> getListData(Map<String, Object> params){
		return studyRoomDao.getListData(params);
	}

	@Override
	public int getCountData(Map<String, Object> params){
		return studyRoomDao.getCountData(params);
	}

	@Override
	public List<StudyRoomListItem> getListDataByUser(Map<String, Object> params){
		return studyRoomDao.getListDataByUser(params);
	}

	@Override
	public int getCountDataByUser(Map<String, Object> params){
		return studyRoomDao.getCountDataByUser(params);
	}

	@Override
	public StudyRoomListItem getListItemOjbect(Integer roomId){
		return studyRoomDao.getListItemOjbect(roomId);
	}

	//新加
	@Override
	public List<StudyRoom> getFeaturedList(Map<String, Object> params){
		return  studyRoomDao.getFeaturedList(params);
	}

	@Override
	public List getstudyList(Map<String, Object> params) {
		return studyRoomDao.getstudyList(params);
	}

	@Override
	public int getstudyCount(Map<String, Object> params) {
		return studyRoomDao.getstudyCount(params);
	}
}
