package xin.xiaoer.modules.favourite.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xin.xiaoer.modules.favourite.dao.FavouriteDao;
import xin.xiaoer.modules.favourite.entity.Favourite;
import xin.xiaoer.modules.favourite.service.FavouriteService;
import xin.xiaoer.modules.mobile.entity.PersonalFavourite;

import java.util.List;
import java.util.Map;


@Service("favouriteService")
@Transactional
public class FavouriteServiceImpl implements FavouriteService {
	@Autowired
	private FavouriteDao favouriteDao;
	
	@Override
	public Favourite get(Long fvId){
		return favouriteDao.get(fvId);
	}

	@Override
	public List<Favourite> getList(Map<String, Object> map){
		return favouriteDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return favouriteDao.getCount(map);
	}

	@Override
	public void save(Favourite favourite){
		favouriteDao.save(favourite);
	}

	@Override
	public void update(Favourite favourite){
		favouriteDao.update(favourite);
	}

	@Override
	public void delete(Long fvId){
		favouriteDao.delete(fvId);
	}

	@Override
	public void deleteBatch(Long[] fvIds){
		favouriteDao.deleteBatch(fvIds);
	}

    @Override
    public void updateState(String[] ids,String stateValue) {
        for (String id:ids){
			Favourite favourite=get(Long.parseLong(id));
			favourite.setState(stateValue);
            update(favourite);
        }
    }

    @Override
	public int deleteByArticle(String articleType, Long articleId){
		return favouriteDao.deleteByArticle(articleType, articleId);
	}

	@Override
	public Favourite getByArticleAndUser(String articleType, Long articleId, Long userId){
		return favouriteDao.getByArticleAndUser(articleType, articleId, userId);
	}

	@Override
	public int deleteByArticleAndUser(String articleType, Long articleId, Long userId){
		return favouriteDao.deleteByArticleAndUser(articleType, articleId, userId);
	}

	@Override
	public List<PersonalFavourite> getPersonalList(Map<String, Object> map){
		return favouriteDao.getPersonalList(map);
	}

	@Override
	public int getPersonalCount(Map<String, Object> map){
		return favouriteDao.getPersonalCount(map);
	}

	@Override
	public int getfavouCount(Map<String, Object> map) {
		return favouriteDao.getfavouCount(map);
	}

	@Override
	public Integer getCountByCodeAndId(String donateWeb, Long parseLong) {
		return favouriteDao.getCountByCodeAndId(donateWeb,parseLong);
	}
}
