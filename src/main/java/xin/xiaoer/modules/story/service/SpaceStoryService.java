package xin.xiaoer.modules.story.service;

import xin.xiaoer.modules.story.entity.SpaceStory;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-07-27 13:23:11
 */
public interface SpaceStoryService {
	
	SpaceStory get(Long storyId);

	SpaceStory getDetail(Long storyId, Long userId);

	List<SpaceStory> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(SpaceStory spaceStory);
	
	void update(SpaceStory spaceStory);
	
	void delete(Long storyId);
	
	void deleteBatch(Long[] storyIds);

    void updateState(String[] ids, String stateValue);

	List<SpaceStory> getAPIList(Map<String, Object> map);

	int getAPICount(Map<String, Object> map);

	int getreviewcount(Map<String, Object> map);

    List<SpaceStory> getHotStory(Map<String,Object> params);

	List getreviewList(Map<String,Object> params);

	int getlikeCount(Map<String, Object> map);

	List getlikeList(Map<String, Object> map);

	List getstoryList(Map<String, Object> map);

	int getstoryCount(Map<String, Object> map);

	List getmyStoryList(Map<String, Object> map);

	int getmyStoryCount(Map<String, Object> map);
}
