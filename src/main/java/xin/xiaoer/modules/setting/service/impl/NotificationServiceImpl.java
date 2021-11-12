package xin.xiaoer.modules.setting.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

import xin.xiaoer.modules.setting.dao.NotificationDao;
import xin.xiaoer.modules.setting.entity.Notification;
import xin.xiaoer.modules.setting.service.NotificationService;




@Service("notificationService")
@Transactional
public class NotificationServiceImpl implements NotificationService {
	@Autowired
	private NotificationDao notificationDao;
	
	@Override
	public Notification get(Long noteId){
		return notificationDao.get(noteId);
	}

	@Override
	public List<Notification> getList(Map<String, Object> map){
		return notificationDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return notificationDao.getCount(map);
	}

	@Override
	public void save(Notification notification){
		notificationDao.save(notification);
	}

	@Override
	public void update(Notification notification){
		notificationDao.update(notification);
	}

	@Override
	public void delete(Long noteId){
		notificationDao.delete(noteId);
	}

	@Override
	public void deleteBatch(Long[] noteIds){
		notificationDao.deleteBatch(noteIds);
	}

    @Override
    public void updateState(String[] ids,String stateValue) {
        for (String id:ids){
			Notification notification=get(Long.parseLong(id));
//			notification.setState(stateValue);
            update(notification);
        }
    }
	
}
