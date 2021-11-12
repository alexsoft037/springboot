package xin.xiaoer.modules.story.dao;

import org.apache.ibatis.annotations.Param;
import xin.xiaoer.dao.BaseDao;
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
public interface SpaceStoryDao extends BaseDao<SpaceStory> {

	SpaceStory getDetail(@Param("storyId") Long storyId, @Param("userId") Long userId);

	List<SpaceStory> getAPIList(Map<String, Object> map);

	int getAPICount(Map<String, Object> map);

    List<SpaceStory> getHotStory(Map<String,Object> params);

	List getreviewList(Map<String,Object> params);

	int getreviewcount(Map<String, Object> map);

	int getlikeCount(Map<String, Object> map);

	List getlikeList(Map<String, Object> map);

	List getstoryList(Map<String, Object> map);

	int getstoryCount(Map<String, Object> map);

	List getmyStoryList(Map<String, Object> map);

	int getmyStoryCount(Map<String, Object> map);
}
