package xin.xiaoer.modules.activity.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

import xin.xiaoer.modules.activity.dao.ActivitySignNumberDao;
import xin.xiaoer.modules.activity.entity.ActivitySignNumber;
import xin.xiaoer.modules.activity.service.ActivitySignNumberService;




@Service("activitySignNumberService")
@Transactional
public class ActivitySignNumberServiceImpl implements ActivitySignNumberService {
	@Autowired
	private ActivitySignNumberDao activitySignNumberDao;
	
	@Override
	public ActivitySignNumber get(Integer activityId){
		return activitySignNumberDao.get(activityId);
	}

	@Override
	public List<ActivitySignNumber> getList(Map<String, Object> map){
		return activitySignNumberDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return activitySignNumberDao.getCount(map);
	}

	@Override
	public void save(ActivitySignNumber activitySignNumber){
		activitySignNumberDao.save(activitySignNumber);
	}

	@Override
	public void update(ActivitySignNumber activitySignNumber){
		activitySignNumberDao.update(activitySignNumber);
	}

	@Override
	public void delete(Integer activityId){
		activitySignNumberDao.delete(activityId);
	}

	@Override
	public void deleteBatch(Integer[] activityIds){
		activitySignNumberDao.deleteBatch(activityIds);
	}

    @Override
    public void updateState(String[] ids,String stateValue) {
        for (String id:ids){
			ActivitySignNumber activitySignNumber=get(Integer.parseInt(id));
//			activitySignNumber.setState(stateValue);
            update(activitySignNumber);
        }
    }
	
}
