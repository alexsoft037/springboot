package xin.xiaoer.modules.setting.service;

import xin.xiaoer.modules.setting.entity.ClickCard;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-10-04 01:18:30
 */
public interface ClickCardService {
	
	ClickCard get(Long userId);
	
	List<ClickCard> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(ClickCard clickCard);
	
	void update(ClickCard clickCard);
	
	void delete(Long userId);
	
	void deleteBatch(Long[] userIds);

    void updateState(String[] ids, String stateValue);
}
