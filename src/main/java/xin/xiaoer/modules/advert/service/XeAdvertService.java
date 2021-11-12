package xin.xiaoer.modules.advert.service;

import xin.xiaoer.modules.advert.entity.XeAdvert;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author chenyi
 * @email 228112142@qq.com
 * @date 2018-07-18 11:36:03
 */
public interface XeAdvertService {
	
	XeAdvert get(Integer id);
	
	List<XeAdvert> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(XeAdvert xeAdvert);
	
	void update(XeAdvert xeAdvert);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);

    void updateState(String[] ids, String stateValue);

	int removeArticle(String articleType, Long articleId);
}
