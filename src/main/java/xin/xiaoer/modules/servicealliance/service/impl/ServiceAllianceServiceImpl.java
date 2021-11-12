package xin.xiaoer.modules.servicealliance.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

import xin.xiaoer.modules.servicealliance.dao.ServiceAllianceDao;
import xin.xiaoer.modules.servicealliance.entity.ServiceAlliance;
import xin.xiaoer.modules.servicealliance.service.ServiceAllianceService;




@Service("xeServiceAllianceService")
@Transactional
public class ServiceAllianceServiceImpl implements ServiceAllianceService {
	@Autowired
	private ServiceAllianceDao serviceAllianceDao;
	
	@Override
	public ServiceAlliance get(Integer id){
		return serviceAllianceDao.get(id);
	}

	@Override
	public List<ServiceAlliance> getList(Map<String, Object> map){
		return serviceAllianceDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return serviceAllianceDao.getCount(map);
	}

	@Override
	public void save(ServiceAlliance serviceAlliance){
		serviceAllianceDao.save(serviceAlliance);
	}

	@Override
	public void update(ServiceAlliance serviceAlliance){
		serviceAllianceDao.update(serviceAlliance);
	}

	@Override
	public void delete(Integer id){
		serviceAllianceDao.delete(id);
	}

	@Override
	public void deleteBatch(Integer[] ids){
		serviceAllianceDao.deleteBatch(ids);
	}

    @Override
    public void updateState(String[] ids,String stateValue) {
        for (String id:ids){
			ServiceAlliance serviceAlliance =get(Integer.parseInt(id));
			serviceAlliance.setState(stateValue);
            update(serviceAlliance);
        }
    }
	
}
