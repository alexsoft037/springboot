package xin.xiaoer.modules.setting.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

import xin.xiaoer.modules.setting.dao.ContactDao;
import xin.xiaoer.modules.setting.entity.Contact;
import xin.xiaoer.modules.setting.entity.Follow;
import xin.xiaoer.modules.setting.service.ContactService;




@Service("contactService")
@Transactional
public class ContactServiceImpl implements ContactService {
	@Autowired
	private ContactDao contactDao;
	
	@Override
	public Contact get(Long contactId){
		return contactDao.get(contactId);
	}

	@Override
	public List<Contact> getList(Map<String, Object> map){
		return contactDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return contactDao.getCount(map);
	}

	@Override
	public void save(Contact contact){
		contactDao.save(contact);
	}

	@Override
	public void update(Contact contact){
		contactDao.update(contact);
	}

	@Override
	public void delete(Long contactId){
		contactDao.delete(contactId);
	}

	@Override
	public void deleteBatch(Long[] contactIds){
		contactDao.deleteBatch(contactIds);
	}

    @Override
    public void updateState(String[] ids,String stateValue) {
        for (String id:ids){
			Contact contact=get(Long.parseLong(id));
			contact.setState(stateValue);
            update(contact);
        }
    }

}
