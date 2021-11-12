package xin.xiaoer.service;

import xin.xiaoer.entity.SpaceNews;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-10-28 22:08:28
 */
public interface SpaceNewsService {
	
	SpaceNews get(Integer id);
	
	List<SpaceNews> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(SpaceNews spaceNews);
	
	void update(SpaceNews spaceNews);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);

    void updateState(String[] ids, String stateValue);

	List<SpaceNews> getNewsList(Map<String, Object> map);

	List getreviewList(Map<String, Object> map);

	List getcollectionList(Map<String, Object> map);

	int getreviewcount(Map<String, Object> map);

	int getlikeCount(Map<String, Object> map);

	List getlikeList(Map<String, Object> map);

	int getcollectionCount(Map<String, Object> map);

	int getNewsCount(Map<String, Object> map);

	List getnewsbylocation(Map<String, Object> map);

	int getallnewscount(Map<String, Object> map);

	List getallspacenews(Map<String, Object> map);
}
