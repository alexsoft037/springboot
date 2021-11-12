package xin.xiaoer.modules.monitor.service;

import xin.xiaoer.modules.mobile.entity.LostNotice;
import xin.xiaoer.modules.monitor.entity.ChildrenLost;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-09-03 12:51:23
 */
public interface ChildrenLostService {
	
	ChildrenLost get(Integer id);
	
	List<ChildrenLost> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(ChildrenLost childrenLost);
	
	void update(ChildrenLost childrenLost);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);

    void updateState(String[] ids, String stateValue);

	List<LostNotice> getNoticeList(Map<String, Object> map);
}
