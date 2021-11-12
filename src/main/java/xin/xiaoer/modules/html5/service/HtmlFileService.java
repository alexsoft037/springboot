package xin.xiaoer.modules.html5.service;

import xin.xiaoer.modules.html5.entity.HtmlFile;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-08-07 09:50:18
 */
public interface HtmlFileService {
	
	HtmlFile get(Integer id);
	
	List<HtmlFile> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(HtmlFile htmlFile);
	
	void update(HtmlFile htmlFile);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);

    void updateState(String[] ids, String stateValue);

	List<HtmlFile> getCodeValues(Map<String, Object> map);
}
