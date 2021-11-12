package xin.xiaoer.modules.help.service;

import xin.xiaoer.modules.help.entity.HelpCenter;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-09-23 17:02:07
 */
public interface HelpCenterService {
	
	HelpCenter get(Integer helpId);
	
	List<HelpCenter> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(HelpCenter helpCenter);
	
	void update(HelpCenter helpCenter);
	
	void delete(Integer helpId);
	
	void deleteBatch(Integer[] helpIds);

    void updateState(String[] ids, String stateValue);

	HelpCenter getByCategory(String category);
}
