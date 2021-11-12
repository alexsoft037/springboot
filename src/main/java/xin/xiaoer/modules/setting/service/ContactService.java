package xin.xiaoer.modules.setting.service;

import xin.xiaoer.modules.setting.entity.Contact;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-09-23 20:48:27
 */
public interface ContactService {
	
	Contact get(Long contactId);
	
	List<Contact> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(Contact contact);
	
	void update(Contact contact);
	
	void delete(Long contactId);
	
	void deleteBatch(Long[] contactIds);

    void updateState(String[] ids, String stateValue);
}
