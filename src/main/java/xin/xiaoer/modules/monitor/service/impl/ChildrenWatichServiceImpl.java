package xin.xiaoer.modules.monitor.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

import xin.xiaoer.modules.monitor.dao.ChildrenWatichDao;
import xin.xiaoer.modules.monitor.entity.ChildrenWatich;
import xin.xiaoer.modules.monitor.service.ChildrenWatichService;




@Service("childrenWatichService")
@Transactional
public class ChildrenWatichServiceImpl implements ChildrenWatichService {
	@Autowired
	private ChildrenWatichDao childrenWatichDao;
	
	@Override
	public ChildrenWatich get(Long watchId){
		return childrenWatichDao.get(watchId);
	}

	@Override
	public List<ChildrenWatich> getList(Map<String, Object> map){
		return childrenWatichDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return childrenWatichDao.getCount(map);
	}

	@Override
	public void save(ChildrenWatich childrenWatich){
		childrenWatichDao.save(childrenWatich);
	}

	@Override
	public void update(ChildrenWatich childrenWatich){
		childrenWatichDao.update(childrenWatich);
	}

	@Override
	public void delete(Long watchId){
		childrenWatichDao.delete(watchId);
	}

	@Override
	public void deleteBatch(Long[] watchIds){
		childrenWatichDao.deleteBatch(watchIds);
	}

    @Override
    public void updateState(String[] ids,String stateValue) {
        for (String id:ids){
			ChildrenWatich childrenWatich=get(Long.parseLong(id));
			childrenWatich.setState(stateValue);
            update(childrenWatich);
        }
    }

    @Override
	public ChildrenWatich getByDeviceId(String deviceID){
		return childrenWatichDao.getByDeviceId(deviceID);
	}
}
