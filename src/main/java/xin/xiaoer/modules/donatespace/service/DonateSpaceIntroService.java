package xin.xiaoer.modules.donatespace.service;

import xin.xiaoer.modules.donatespace.entity.DonateSpaceIntro;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author chenyi
 * @email 228112142@qq.com
 * @date 2018-07-20 00:13:18
 */
public interface DonateSpaceIntroService {
	
	DonateSpaceIntro get(Long itemId);
	
	List<DonateSpaceIntro> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(DonateSpaceIntro donateSpaceIntro);
	
	void update(DonateSpaceIntro donateSpaceIntro);
	
	void delete(Long itemId);
	
	void deleteBatch(Long[] itemIds);

    void updateState(String[] ids, String stateValue);
}
