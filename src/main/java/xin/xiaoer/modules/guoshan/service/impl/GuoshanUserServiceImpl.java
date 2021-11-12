package xin.xiaoer.modules.guoshan.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

import xin.xiaoer.modules.guoshan.dao.GuoshanUserDao;
import xin.xiaoer.modules.guoshan.entity.GuoshanUser;
import xin.xiaoer.modules.guoshan.service.GuoshanUserService;




@Service("guoshanUserService")
@Transactional
public class GuoshanUserServiceImpl implements GuoshanUserService {
	@Autowired
	private GuoshanUserDao guoshanUserDao;
	
	@Override
	public GuoshanUser get(Long userId){
		return guoshanUserDao.get(userId);
	}

	@Override
	public List<GuoshanUser> getList(Map<String, Object> map){
		return guoshanUserDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return guoshanUserDao.getCount(map);
	}

	@Override
	public void save(GuoshanUser guoshanUser){
		guoshanUserDao.save(guoshanUser);
	}

	@Override
	public void update(GuoshanUser guoshanUser){
		guoshanUserDao.update(guoshanUser);
	}

	@Override
	public void delete(Long userId){
		guoshanUserDao.delete(userId);
	}

	@Override
	public void deleteBatch(Long[] userIds){
		guoshanUserDao.deleteBatch(userIds);
	}

    @Override
    public void updateState(String[] ids,String stateValue) {
        for (String id:ids){
			GuoshanUser guoshanUser=get(Long.parseLong(id));
			guoshanUser.setState(stateValue);
            update(guoshanUser);
        }
    }


    @Override
	public GuoshanUser getByVolunteerId(String volunteerId){
		return guoshanUserDao.getByVolunteerId(volunteerId);
	}

	@Override
	public int getVolCount() {
		return guoshanUserDao.getVolCount();
	}
}
