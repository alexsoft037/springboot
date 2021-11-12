package xin.xiaoer.modules.favourite.service;

import xin.xiaoer.modules.favourite.entity.Favourite;
import xin.xiaoer.modules.mobile.entity.PersonalFavourite;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author chenyi
 * @email 228112142@qq.com
 * @date 2018-07-20 00:43:14
 */
public interface FavouriteService {
	
	Favourite get(Long fvId);
	
	List<Favourite> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(Favourite favourite);
	
	void update(Favourite favourite);
	
	void delete(Long fvId);
	
	void deleteBatch(Long[] fvIds);

    void updateState(String[] ids, String stateValue);

	int deleteByArticle(String articleType, Long articleId);

	Favourite getByArticleAndUser(String articleType, Long articleId, Long userId);

	int deleteByArticleAndUser(String articleType, Long articleId, Long userId);

	List<PersonalFavourite> getPersonalList(Map<String, Object> map);

	int getPersonalCount(Map<String, Object> map);

	int getfavouCount(Map<String, Object> map);

    Integer getCountByCodeAndId(String donateWeb, Long parseLong);
}
