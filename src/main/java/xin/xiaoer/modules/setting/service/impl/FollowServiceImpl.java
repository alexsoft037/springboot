package xin.xiaoer.modules.setting.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

import xin.xiaoer.modules.setting.dao.FollowDao;
import xin.xiaoer.modules.setting.entity.Follow;
import xin.xiaoer.modules.setting.service.FollowService;




@Service("followService")
@Transactional
public class FollowServiceImpl implements FollowService {
	@Autowired
	private FollowDao followDao;
	
	@Override
	public Follow get(Long id){
		return followDao.get(id);
	}

	@Override
	public List<Follow> getList(Map<String, Object> map){
		return followDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return followDao.getCount(map);
	}

	@Override
	public void save(Follow follow){
		followDao.save(follow);
	}

	@Override
	public void update(Follow follow){
		followDao.update(follow);
	}

	@Override
	public void delete(Long id){
		followDao.delete(id);
	}

	@Override
	public void deleteBatch(Long[] ids){
		followDao.deleteBatch(ids);
	}

    @Override
    public void updateState(String[] ids,String stateValue) {
        for (String id:ids){
			Follow follow=get(Long.parseLong(id));
//			follow.setState(stateValue);
            update(follow);
        }
    }

	@Override
	public Follow getByUserIdAndFollowId(Long userId, Long followId) {
		return followDao.getByUserIdAndFollowId(userId, followId);
	}

	@Override
	public int deleteByUserIdAndFollowId(Long userId, Long followId){
		return followDao.deleteByUserIdAndFollowId(userId, followId);
	}

	@Override
	public List<Follow> getListByFollowId(Map<String, Object> map){
		return followDao.getListByFollowId(map);
	}

	@Override
	public List<Follow> getListByUserId(Map<String, Object> map){
		return followDao.getListByUserId(map);
	}

	@Override
	public List<String> getAccountListByFollowId(Map<String, Object> map){
		return followDao.getAccountListByFollowId(map);
	}
}
