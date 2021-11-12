package xin.xiaoer.modules.advert.dao;

import org.apache.ibatis.annotations.Param;
import xin.xiaoer.dao.BaseDao;
import xin.xiaoer.modules.advert.entity.XeAdvert;

/**
 * 
 * 
 * @author chenyi
 * @email 228112142@qq.com
 * @date 2018-07-18 11:36:03
 */
public interface XeAdvertDao extends BaseDao<XeAdvert> {

    int removeArticle(@Param("articleType") String articleType, @Param("articleId") Long articleId);
}
