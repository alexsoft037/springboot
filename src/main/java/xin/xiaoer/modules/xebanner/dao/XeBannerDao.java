package xin.xiaoer.modules.xebanner.dao;

import org.apache.ibatis.annotations.Param;
import xin.xiaoer.dao.BaseDao;
import xin.xiaoer.modules.xebanner.entity.XeBanner;
import xin.xiaoer.modules.xebanner.entity.XeBannerStory;

import java.util.List;

/**
 * 
 * 
 * @author chenyi
 * @email 228112142@qq.com
 * @date 2018-07-12 18:47:51
 */
public interface XeBannerDao extends BaseDao<XeBanner> {
	int removeArticle(@Param("articleType") String articleType, @Param("articleId") Long articleId);

    List<XeBannerStory> getListStory();
}
