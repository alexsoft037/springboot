package xin.xiaoer.modules.xebanner.service;

import xin.xiaoer.modules.xebanner.entity.XeBanner;
import xin.xiaoer.modules.xebanner.entity.XeBannerStory;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author chenyi
 * @email 228112142@qq.com
 * @date 2018-07-12 18:47:51
 */
public interface XeBannerService {
	
	XeBanner get(Integer id);
	
	List<XeBanner> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(XeBanner xeBanner);
	
	void update(XeBanner xeBanner);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);

    void updateState(String[] ids, String stateValue);

	int removeArticle(String articleType, Long articleId);

    List<XeBannerStory> getListStory();
}
