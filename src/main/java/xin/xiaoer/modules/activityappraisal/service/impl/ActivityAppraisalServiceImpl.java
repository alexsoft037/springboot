package xin.xiaoer.modules.activityappraisal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

import xin.xiaoer.modules.activityappraisal.dao.ActivityAppraisalDao;
import xin.xiaoer.modules.activityappraisal.entity.ActivityAppraisal;
import xin.xiaoer.modules.activityappraisal.service.ActivityAppraisalService;




@Service("activityAppraisalService")
@Transactional
public class ActivityAppraisalServiceImpl implements ActivityAppraisalService {
	@Autowired
	private ActivityAppraisalDao activityAppraisalDao;
	
	@Override
	public ActivityAppraisal get(Integer appraisalId){
		return activityAppraisalDao.get(appraisalId);
	}

	@Override
	public List<ActivityAppraisal> getList(Map<String, Object> map){
		return activityAppraisalDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return activityAppraisalDao.getCount(map);
	}

	@Override
	public void save(ActivityAppraisal activityAppraisal){
		activityAppraisalDao.save(activityAppraisal);
	}

	@Override
	public void update(ActivityAppraisal activityAppraisal){
		activityAppraisalDao.update(activityAppraisal);
	}

	@Override
	public void delete(Integer appraisalId){
		activityAppraisalDao.delete(appraisalId);
	}

	@Override
	public void deleteBatch(Integer[] appraisalIds){
		activityAppraisalDao.deleteBatch(appraisalIds);
	}

    @Override
    public void updateState(String[] ids,String stateValue) {
        for (String id:ids){
			ActivityAppraisal activityAppraisal=get(Integer.parseInt(id));
			activityAppraisal.setState(stateValue);
            update(activityAppraisal);
        }
    }
	
}
