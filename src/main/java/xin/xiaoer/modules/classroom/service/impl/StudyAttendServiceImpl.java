package xin.xiaoer.modules.classroom.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

import xin.xiaoer.modules.classroom.dao.StudyAttendDao;
import xin.xiaoer.modules.classroom.entity.StudyAttend;
import xin.xiaoer.modules.classroom.service.StudyAttendService;




@Service("studyAttendService")
@Transactional
public class StudyAttendServiceImpl implements StudyAttendService {
	@Autowired
	private StudyAttendDao studyAttendDao;
	
	@Override
	public StudyAttend get(Long id){
		return studyAttendDao.get(id);
	}

	@Override
	public List<StudyAttend> getList(Map<String, Object> map){
		return studyAttendDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return studyAttendDao.getCount(map);
	}

	@Override
	public void save(StudyAttend studyAttend){
		studyAttendDao.save(studyAttend);
	}

	@Override
	public void update(StudyAttend studyAttend){
		studyAttendDao.update(studyAttend);
	}

	@Override
	public void delete(Long id){
		studyAttendDao.delete(id);
	}

	@Override
	public void deleteBatch(Long[] ids){
		studyAttendDao.deleteBatch(ids);
	}

    @Override
    public void updateState(String[] ids,String stateValue) {
        for (String id:ids){
			StudyAttend studyAttend=get(Long.parseLong(id));
            update(studyAttend);
        }
    }

    @Override
	public int getCountByRoomId(Integer roomId){
		return studyAttendDao.getCountByRoomId(roomId);
	}

	@Override
	public StudyAttend getByUserAndRoom(Integer roomId, Long userId){
		return studyAttendDao.getByUserAndRoom(roomId, userId);
	}
}
