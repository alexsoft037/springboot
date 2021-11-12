package xin.xiaoer.modules.story.service;

import xin.xiaoer.modules.story.entity.SpaceStoryItem;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-07-27 13:23:15
 */
public interface SpaceStoryItemService {
	
	SpaceStoryItem get(Long storyId);
	
	List<SpaceStoryItem> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(SpaceStoryItem spaceStoryItem);
	
	void update(SpaceStoryItem spaceStoryItem);
	
	void delete(Long storyId);
	
	void deleteBatch(Long[] storyIds);

    void updateState(String[] ids, String stateValue);

	List<SpaceStoryItem> getListByStoryId(Long storyId);
}
