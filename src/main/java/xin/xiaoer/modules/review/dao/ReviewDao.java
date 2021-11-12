package xin.xiaoer.modules.review.dao;

import org.apache.ibatis.annotations.Param;
import xin.xiaoer.dao.BaseDao;
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
public interface ReviewDao extends BaseDao<Review> {

    int deleteByArticle(@Param("articleType") String articleType, @Param("articleId") Long articleId);

    int getCountByCodeAndId(@Param("articleType") String articleType, @Param("articleId") Long articleId);

    Review getByArticleAndUser(@Param("articleType") String articleType, @Param("articleId") Long articleId, @Param("userId") Long userId);

    int deleteByArticleAndUser(@Param("articleType") String articleType, @Param("articleId") Long articleId, @Param("userId") Long userId);

    List<Review> getArticleList(Map<String, Object> map);

    int getArticleCount(Map<String, Object> map);

    int getCountByArticle(Map<String, Object> map);

    List<Review> getAdminList(Map<String, Object> map);

    int getAdminCount(Map<String, Object> map);

    Review getDetail(Long reviewId);

    List getreviewList(Map<String, Object> map);

    int getreviewCount(Map<String, Object> map);
}
