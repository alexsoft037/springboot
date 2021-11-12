package xin.xiaoer.modules.comment.service;

import xin.xiaoer.modules.comment.entity.Comment;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author chenyi
 * @email 228112142@qq.com
 * @date 2018-07-21 11:57:23
 */
public interface CommentService {
	
	Comment get(Long commentId) throws Exception;
	
	List<Comment> getList(Map<String, Object> map) throws Exception;
	
	int getCount(Map<String, Object> map);
	
	void save(Comment comment) throws Exception ;
	
	void update(Comment comment) throws Exception ;
	
	void delete(Long commentId);
	
	void deleteBatch(Long[] commentIds);

    void updateState(String[] ids, String stateValue) throws Exception ;

	int deleteByArticle(String articleType, Long articleId);

	int getCountByCodeAndId(String articleType, Long articleId);

	int deleteByArticleAndUser(String articleType, Long articleId, Long userId);

	Comment getByArticleAndUser(String articleType, Long articleId, Long userId);
}
