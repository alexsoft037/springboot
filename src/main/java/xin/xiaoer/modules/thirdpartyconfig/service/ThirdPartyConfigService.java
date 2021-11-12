package xin.xiaoer.modules.thirdpartyconfig.service;

import xin.xiaoer.modules.thirdpartyconfig.entity.ThirdPartyConfig;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author chenyi
 * @email 228112142@qq.com
 * @date 2018-07-08 18:35:30
 */
public interface ThirdPartyConfigService {
	
	ThirdPartyConfig get(Integer id);
	
	List<ThirdPartyConfig> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(ThirdPartyConfig thirdPartyConfig);
	
	void update(ThirdPartyConfig thirdPartyConfig);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);

    void updateState(String[] ids, String stateValue);
}
