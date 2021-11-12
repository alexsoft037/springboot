package xin.xiaoer.modules.story.service;

import xin.xiaoer.modules.story.entity.SpaceStoryHtml;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-07-27 13:23:19
 */
public interface SpaceStoryHtmlService {
	
	SpaceStoryHtml get(Long storyId);
	
	List<SpaceStoryHtml> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(SpaceStoryHtml spaceStoryHtml);
	
	void update(SpaceStoryHtml spaceStoryHtml);
	
	void delete(Long storyId);
	
	void deleteBatch(Long[] storyIds);

    void updateState(String[] ids, String stateValue);
}
