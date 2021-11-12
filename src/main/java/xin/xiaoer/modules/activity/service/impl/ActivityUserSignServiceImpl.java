package xin.xiaoer.modules.activity.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

import xin.xiaoer.modules.activity.dao.ActivityUserSignDao;
import xin.xiaoer.modules.activity.entity.ActivityUserSign;
import xin.xiaoer.modules.activity.service.ActivityUserSignService;




@Service("activityUserSignService")
@Transactional
public class ActivityUserSignServiceImpl implements ActivityUserSignService {
	@Autowired
	private ActivityUserSignDao activityUserSignDao;
	
	@Override
	public ActivityUserSign get(Long id){
		return activityUserSignDao.get(id);
	}

	@Override
	public List<ActivityUserSign> getList(Map<String, Object> map){
		return activityUserSignDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return activityUserSignDao.getCount(map);
	}

	@Override
	public void save(ActivityUserSign activityUserSign){
		activityUserSignDao.save(activityUserSign);
	}

	@Override
	public void update(ActivityUserSign activityUserSign){
		activityUserSignDao.update(activityUserSign);
	}

	@Override
	public void delete(Long id){
		activityUserSignDao.delete(id);
	}

	@Override
	public void deleteBatch(Long[] ids){
		activityUserSignDao.deleteBatch(ids);
	}

    @Override
    public void updateState(String[] ids,String stateValue) {
        for (String id:ids){
			ActivityUserSign activityUserSign=get(Long.parseLong(id));
			activityUserSign.setState(stateValue);
            update(activityUserSign);
        }
    }

    @Override
	public ActivityUserSign getByUserAndActivity(Long userId, Integer activityId){
		return activityUserSignDao.getByUserAndActivity(userId, activityId);
	}
}
