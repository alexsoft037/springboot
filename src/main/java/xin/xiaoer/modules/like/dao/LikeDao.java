package xin.xiaoer.modules.like.dao;

import org.apache.ibatis.annotations.Param;
import xin.xiaoer.dao.BaseDao;
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
public interface LikeDao extends BaseDao<Like> {

	int deleteByArticle(@Param("articleType") String articleType, @Param("articleId") Long articleId);

	int getCountByCodeAndId(@Param("articleType") String articleType, @Param("articleId") Long articleId);

	Like getByArticleAndUser(@Param("articleType") String articleType, @Param("articleId") Long articleId, @Param("userId") Long userId);

	int deleteByArticleAndUser(@Param("articleType") String articleType, @Param("articleId") Long articleId, @Param("userId") Long userId);
}
