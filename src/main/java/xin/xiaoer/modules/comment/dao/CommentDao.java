package xin.xiaoer.modules.comment.dao;

import org.apache.ibatis.annotations.Param;
import xin.xiaoer.dao.BaseDao;
import xin.xiaoer.modules.comment.entity.Comment;

/**
 * 
 * 
 * @author chenyi
 * @email 228112142@qq.com
 * @date 2018-07-21 11:57:23
 */
public interface CommentDao extends BaseDao<Comment> {

    int deleteByArticle(@Param("articleType") String articleType, @Param("articleId") Long articleId);

    int deleteByArticleAndUser(@Param("articleType") String articleType, @Param("articleId") Long articleId, @Param("userId") Long userId);

    Comment getByArticleAndUser(@Param("articleType") String articleType, @Param("articleId") Long articleId, @Param("userId") Long userId);

    int getCountByCodeAndId(@Param("articleType") String articleType, @Param("articleId") Long articleId);
}
