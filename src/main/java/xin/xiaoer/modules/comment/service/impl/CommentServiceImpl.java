package xin.xiaoer.modules.comment.service.impl;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

import xin.xiaoer.modules.comment.dao.CommentDao;
import xin.xiaoer.modules.comment.entity.Comment;
import xin.xiaoer.modules.comment.service.CommentService;




@Service("commentService")
@Transactional
public class CommentServiceImpl implements CommentService {
	@Autowired
	private CommentDao commentDao;
	
	@Override
	public Comment get(Long commentId) throws Exception {
		Comment comment = commentDao.get(commentId);
		byte[] valueDecoded = Base64.decodeBase64(comment.getContent().getBytes("UTF-8"));
		String contentDecoded = new String(valueDecoded, "UTF-8");
		comment.setContent(contentDecoded);
		return comment;
	}

	@Override
	public List<Comment> getList(Map<String, Object> map) throws Exception{
		List<Comment> commentList = commentDao.getList(map);
		for (Comment comment: commentList){
			byte[] valueDecoded = Base64.decodeBase64(comment.getContent().getBytes("UTF-8"));
			String contentDecoded = new String(valueDecoded, "UTF-8");
			comment.setContent(contentDecoded);
		}
		return commentList;
	}

	@Override
	public int getCount(Map<String, Object> map){
		return commentDao.getCount(map);
	}

	@Override
	public void save(Comment comment) throws Exception {
		byte[] bytesEncoded = Base64.encodeBase64(comment.getContent().getBytes("UTF-8"));
		String contentEncoded = new String(bytesEncoded, "UTF-8");
		comment.setContent(contentEncoded);
		commentDao.save(comment);
	}

	@Override
	public void update(Comment comment) throws Exception {
		byte[] bytesEncoded = Base64.encodeBase64(comment.getContent().getBytes("UTF-8"));
		String contentEncoded = new String(bytesEncoded, "UTF-8");
		comment.setContent(contentEncoded);
		commentDao.update(comment);
	}

	@Override
	public void delete(Long commentId){
		commentDao.delete(commentId);
	}

	@Override
	public void deleteBatch(Long[] commentIds){
		commentDao.deleteBatch(commentIds);
	}

    @Override
    public void updateState(String[] ids,String stateValue) throws Exception {
        for (String id:ids){
			Comment comment= get(Long.parseLong(id)) ;
			comment.setState(stateValue);
            update(comment);
        }
    }

	@Override
	public int deleteByArticle(String articleType, Long articleId){
		return commentDao.deleteByArticle(articleType, articleId);
	}

	@Override
	public int getCountByCodeAndId(String articleType, Long articleId){
		return commentDao.getCountByCodeAndId(articleType, articleId);
	}

	@Override
	public int deleteByArticleAndUser(String articleType, Long articleId, Long userId){
		return commentDao.deleteByArticleAndUser(articleType, articleId, userId);
	}

	@Override
	public Comment getByArticleAndUser(String articleType, Long articleId, Long userId){
		return commentDao.getByArticleAndUser(articleType, articleId, userId);
	}
}
