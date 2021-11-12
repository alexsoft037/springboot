package xin.xiaoer.modules.monitor.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

import xin.xiaoer.entity.CodeValue;
import xin.xiaoer.modules.monitor.dao.UserChildrenDao;
import xin.xiaoer.modules.monitor.entity.UserChildren;
import xin.xiaoer.modules.monitor.service.UserChildrenService;




@Service("userChildrenService")
@Transactional
public class UserChildrenServiceImpl implements UserChildrenService {
	@Autowired
	private UserChildrenDao userChildrenDao;
	
	@Override
	public UserChildren get(Long childrenId){
		return userChildrenDao.get(childrenId);
	}

	@Override
	public List<UserChildren> getList(Map<String, Object> map){
		return userChildrenDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return userChildrenDao.getCount(map);
	}

	@Override
	public void save(UserChildren userChildren){
		userChildrenDao.save(userChildren);
	}

	@Override
	public void update(UserChildren userChildren){
		userChildrenDao.update(userChildren);
	}

	@Override
	public void delete(Long childrenId){
		userChildrenDao.delete(childrenId);
	}

	@Override
	public void deleteBatch(Long[] childrenIds){
		userChildrenDao.deleteBatch(childrenIds);
	}

    @Override
    public void updateState(String[] ids,String stateValue) {
        for (String id:ids){
			UserChildren userChildren=get(Long.parseLong(id));
			userChildren.setState(stateValue);
            update(userChildren);
        }
    }

    @Override
	public List<CodeValue> getCodeValues(Map<String, Object> map){
		return userChildrenDao.getCodeValues(map);
	}
}
