package xin.xiaoer.modules.donatespace.service;

import xin.xiaoer.modules.donatespace.entity.DonateSpaceProcess;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author chenyi
 * @email 228112142@qq.com
 * @date 2018-07-20 00:16:18
 */
public interface DonateSpaceProcessService {
	
	DonateSpaceProcess get(Long itemId);
	
	List<DonateSpaceProcess> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(DonateSpaceProcess donateSpaceProcess);
	
	void update(DonateSpaceProcess donateSpaceProcess);
	
	void delete(Long itemId);
	
	void deleteBatch(Long[] itemIds);

    void updateState(String[] ids, String stateValue);
}
