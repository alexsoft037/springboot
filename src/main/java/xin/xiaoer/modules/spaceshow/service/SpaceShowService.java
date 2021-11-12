package xin.xiaoer.modules.spaceshow.service;

import xin.xiaoer.modules.spaceshow.entity.SpaceShow;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-08-24 23:03:45
 */
public interface SpaceShowService {
	
	SpaceShow get(Long showId);
	
	List<SpaceShow> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(SpaceShow spaceShow);
	
	void update(SpaceShow spaceShow);
	
	void delete(Long showId);
	
	void deleteBatch(Long[] showIds);

    void updateState(String[] ids, String stateValue);

	List<SpaceShow> getRecentList(Map<String, Object> map);

	int getRecentCount(Map<String, Object> map);

	List<SpaceShow> getPopularList(Map<String, Object> map);

	int getPopularCount(Map<String, Object> map);

	List<SpaceShow> getActivityShowList(Map<String, Object> map);

	int getActivityShowCount(Map<String, Object> map);

	int getreviewcount(Map<String, Object> map);

	List getSpaceShowByUser(Map<String, Object> map);

	List getreviewList(Map<String, Object> map);

	int getlikeCount(Map<String, Object> map);

	List getlikeList(Map<String, Object> map);

	int getShowCount(Map<String, Object> map);

	List getMyShowByUser(Map<String, Object> map);

	int getMyShowCount(Map<String, Object> map);
}
