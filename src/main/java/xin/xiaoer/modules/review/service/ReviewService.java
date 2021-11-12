package xin.xiaoer.modules.review.service;

import xin.xiaoer.modules.review.entity.Review;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-07-27 21:24:14
 */
public interface ReviewService {
	
	Review get(Long reviewId);
	
	List<Review> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);

	int getCountByCodeAndId(String articleType, Long articleId);
	
	void save(Review review);
	
	void update(Review review);
	
	void delete(Long reviewId);
	
	void deleteBatch(Long[] reviewIds);

    void updateState(String[] ids, String stateValue);

	int deleteByArticle(String articleType, Long articleId);

	Review getByArticleAndUser(String articleType, Long articleId, Long userId);

	int deleteByArticleAndUser(String articleType, Long articleId, Long userId);

	List<Review> getArticleList(Map<String, Object> map);

	int getArticleCount(Map<String, Object> map);

	int getCountByArticle(Map<String, Object> map);

	Review filterReviewData(Review review);

	List<Review> getAdminList(Map<String, Object> map);

	int getAdminCount(Map<String, Object> map);

	Review getDetail(Long reviewId);

	List getreviewList(Map<String, Object> map);

	int getreviewCount(Map<String, Object> map);
}
