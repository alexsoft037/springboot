package xin.xiaoer.modules.monitor.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

import xin.xiaoer.modules.monitor.dao.ChildrenTrajectoryDao;
import xin.xiaoer.modules.monitor.entity.ChildrenTrajectory;
import xin.xiaoer.modules.monitor.service.ChildrenTrajectoryService;




@Service("childrenTrajectoryService")
@Transactional
public class ChildrenTrajectoryServiceImpl implements ChildrenTrajectoryService {
	@Autowired
	private ChildrenTrajectoryDao childrenTrajectoryDao;
	
	@Override
	public ChildrenTrajectory get(Long id){
		return childrenTrajectoryDao.get(id);
	}

	@Override
	public List<ChildrenTrajectory> getList(Map<String, Object> map){
		return childrenTrajectoryDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return childrenTrajectoryDao.getCount(map);
	}

	@Override
	public void save(ChildrenTrajectory childrenTrajectory){
		childrenTrajectoryDao.save(childrenTrajectory);
	}

	@Override
	public void update(ChildrenTrajectory childrenTrajectory){
		childrenTrajectoryDao.update(childrenTrajectory);
	}

	@Override
	public void delete(Long id){
		childrenTrajectoryDao.delete(id);
	}

	@Override
	public void deleteBatch(Long[] ids){
		childrenTrajectoryDao.deleteBatch(ids);
	}

    @Override
    public void updateState(String[] ids,String stateValue) {
        for (String id:ids){
			ChildrenTrajectory childrenTrajectory=get(Long.parseLong(id));
//			childrenTrajectory.setState(stateValue);
            update(childrenTrajectory);
        }
    }

    @Override
	public List<ChildrenTrajectory> getPointList(Map<String, Object> map){
		return childrenTrajectoryDao.getPointList(map);
	}

	@Override
	public List<String> getTimeList(String day){
		return childrenTrajectoryDao.getTimeList(day);
	}

	@Override
	public List<ChildrenTrajectory> getTimeDataList(String time){
		return childrenTrajectoryDao.getTimeDataList(time);
	}
}
