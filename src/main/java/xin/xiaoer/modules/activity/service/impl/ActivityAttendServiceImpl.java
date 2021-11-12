package xin.xiaoer.modules.activity.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import xin.xiaoer.modules.activity.dao.ActivityAttendDao;
import xin.xiaoer.modules.activity.entity.ActivityAttend;
import xin.xiaoer.modules.activity.service.ActivityAttendService;




@Service("activityAttendService")
@Transactional
public class ActivityAttendServiceImpl implements ActivityAttendService {
	@Autowired
	private ActivityAttendDao activityAttendDao;
	
	@Override
	public ActivityAttend get(Long activityId){
		return activityAttendDao.get(activityId);
	}

	@Override
	public List<ActivityAttend> getList(Map<String, Object> map){
		return activityAttendDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return activityAttendDao.getCount(map);
	}

	@Override
	public void save(ActivityAttend activityAttend){
		activityAttendDao.save(activityAttend);
	}

	@Override
	public void update(ActivityAttend activityAttend){
		activityAttendDao.update(activityAttend);
	}

	@Override
	public void delete(Long activityId){
		activityAttendDao.delete(activityId);
	}

	@Override
	public void deleteBatch(Long[] activityIds){
		activityAttendDao.deleteBatch(activityIds);
	}

    @Override
    public void updateState(String[] ids,String stateValue) {
        for (String id:ids){
			ActivityAttend activityAttend=get(Long.parseLong(id));
			activityAttend.setState(stateValue);
            update(activityAttend);
        }
    }

	@Override
	public ActivityAttend getByUserIdAndActivityId(Integer activityId, Long userId){
		return activityAttendDao.getByUserIdAndActivityId(activityId, userId);
	}

	@Override
	public Integer getCountByActivityId(Integer activityId){
		return activityAttendDao.getCountByActivityId(activityId);
	}

	@Override
	public int deleteByActivityId(Object id){
		return activityAttendDao.deleteByActivityId(id);
	}

	@Override
	public int queryAttendTotal(){
		return activityAttendDao.queryAttendTotal();
	}
}
