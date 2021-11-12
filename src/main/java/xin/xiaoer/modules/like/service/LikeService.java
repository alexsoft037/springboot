package xin.xiaoer.modules.like.service;

import xin.xiaoer.modules.like.entity.Like;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-07-23 09:58:53
 */
public interface LikeService {
	
	Like get(Long likeId);
	
	List<Like> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(Like like);
	
	void update(Like like);
	
	void delete(Long likeId);
	
	void deleteBatch(Long[] likeIds);

    void updateState(String[] ids, String stateValue);

	int deleteByArticle(String articleType, Long articleId);

	int getCountByCodeAndId(String articleType, Long articleId);

	Like getByArticleAndUser(String articleType, Long articleId, Long userId);

	int deleteByArticleAndUser(String articleType, Long articleId, Long userId);
}
