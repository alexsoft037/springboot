package xin.xiaoer.modules.favourite.dao;

import org.apache.ibatis.annotations.Param;
import xin.xiaoer.dao.BaseDao;
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
public interface FavouriteDao extends BaseDao<Favourite> {

    int deleteByArticle(@Param("articleType") String articleType, @Param("articleId") Long articleId);

    Favourite getByArticleAndUser(@Param("articleType") String articleType, @Param("articleId") Long articleId, @Param("userId") Long userId);

    int deleteByArticleAndUser(@Param("articleType") String articleType, @Param("articleId") Long articleId, @Param("userId") Long userId);

    List<PersonalFavourite> getPersonalList(Map<String, Object> map);

    int getPersonalCount(Map<String, Object> map);

    int getfavouCount(Map<String, Object> map);

    Integer getCountByCodeAndId(@Param("articleType")String donateWeb, @Param("articleId")Long parseLong);
}
