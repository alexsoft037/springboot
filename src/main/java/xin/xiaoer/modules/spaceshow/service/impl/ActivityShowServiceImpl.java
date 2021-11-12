package xin.xiaoer.modules.spaceshow.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

import xin.xiaoer.modules.spaceshow.dao.ActivityShowDao;
import xin.xiaoer.modules.spaceshow.entity.ActivityShow;
import xin.xiaoer.modules.spaceshow.service.ActivityShowService;




@Service("activityShowService")
@Transactional
public class ActivityShowServiceImpl implements ActivityShowService {
	@Autowired
	private ActivityShowDao activityShowDao;
	
	@Override
	public ActivityShow get(Integer id){
		return activityShowDao.get(id);
	}

	@Override
	public List<ActivityShow> getList(Map<String, Object> map){
		return activityShowDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return activityShowDao.getCount(map);
	}

	@Override
	public void save(ActivityShow activityShow){
		activityShowDao.save(activityShow);
	}

	@Override
	public void update(ActivityShow activityShow){
		activityShowDao.update(activityShow);
	}

	@Override
	public void delete(Integer id){
		activityShowDao.delete(id);
	}

	@Override
	public void deleteBatch(Integer[] ids){
		activityShowDao.deleteBatch(ids);
	}

    @Override
    public void updateState(String[] ids,String stateValue) {
        for (String id:ids){
			ActivityShow activityShow=get(Integer.parseInt(id));
//			activityShow.setState(stateValue);
            update(activityShow);
        }
    }
	
}
