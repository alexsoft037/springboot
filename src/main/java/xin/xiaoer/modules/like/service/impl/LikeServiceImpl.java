package xin.xiaoer.modules.like.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

import xin.xiaoer.modules.like.dao.LikeDao;
import xin.xiaoer.modules.like.entity.Like;
import xin.xiaoer.modules.like.service.LikeService;




@Service("likeService")
@Transactional
public class LikeServiceImpl implements LikeService {
	@Autowired
	private LikeDao likeDao;
	
	@Override
	public Like get(Long likeId){
		return likeDao.get(likeId);
	}

	@Override
	public List<Like> getList(Map<String, Object> map){
		return likeDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return likeDao.getCount(map);
	}

	@Override
	public void save(Like like){
		likeDao.save(like);
	}

	@Override
	public void update(Like like){
		likeDao.update(like);
	}

	@Override
	public void delete(Long likeId){
		likeDao.delete(likeId);
	}

	@Override
	public void deleteBatch(Long[] likeIds){
		likeDao.deleteBatch(likeIds);
	}

    @Override
    public void updateState(String[] ids,String stateValue) {
        for (String id:ids){
			Like like=get(Long.parseLong(id));
//			like.setState(stateValue);
            update(like);
        }
    }

	@Override
	public int deleteByArticle(String articleType, Long articleId){
		return likeDao.deleteByArticle(articleType, articleId);
	}

	@Override
	public int getCountByCodeAndId(String articleType, Long articleId){
		return likeDao.getCountByCodeAndId(articleType, articleId);
	}

	@Override
	public Like getByArticleAndUser(String articleType, Long articleId, Long userId){
		return likeDao.getByArticleAndUser(articleType, articleId, userId);
	}

	@Override
	public int deleteByArticleAndUser(String articleType, Long articleId, Long userId){
		return likeDao.deleteByArticleAndUser(articleType, articleId, userId);
	}
}
