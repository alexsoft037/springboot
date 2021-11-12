package xin.xiaoer.modules.servicealliance.service;

import xin.xiaoer.modules.servicealliance.entity.ServiceAlliance;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author chenyi
 * @email 228112142@qq.com
 * @date 2018-07-19 09:26:06
 */
public interface ServiceAllianceService {
	
	ServiceAlliance get(Integer id);
	
	List<ServiceAlliance> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(ServiceAlliance serviceAlliance);
	
	void update(ServiceAlliance serviceAlliance);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);

    void updateState(String[] ids, String stateValue);
}
