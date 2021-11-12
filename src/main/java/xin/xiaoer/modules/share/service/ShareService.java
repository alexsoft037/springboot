package xin.xiaoer.modules.share.service;

import xin.xiaoer.modules.share.entity.Share;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-10-20 21:15:57
 */
public interface ShareService {
	
	Share get(Long shareId);
	
	List<Share> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(Share share);
	
	void update(Share share);
	
	void delete(Long shareId);
	
	void deleteBatch(Long[] shareIds);

    void updateState(String[] ids, String stateValue);
}
