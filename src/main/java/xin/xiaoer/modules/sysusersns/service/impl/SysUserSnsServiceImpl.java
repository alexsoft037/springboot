package xin.xiaoer.modules.sysusersns.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xin.xiaoer.modules.sysusersns.dao.SysUserSnsDao;
import xin.xiaoer.modules.sysusersns.entity.SysUserSns;
import xin.xiaoer.modules.sysusersns.service.SysUserSnsService;

import java.util.List;
import java.util.Map;




@Service("sysUserSnsService")
@Transactional
public class SysUserSnsServiceImpl implements SysUserSnsService {
	@Autowired
	private SysUserSnsDao sysUserSnsDao;
	
	@Override
	public SysUserSns get(Long userId){
		return sysUserSnsDao.get(userId);
	}

	@Override
	public List<SysUserSns> getList(Map<String, Object> map){
		return sysUserSnsDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return sysUserSnsDao.getCount(map);
	}

	@Override
	public void save(SysUserSns sysUserSns){
		sysUserSnsDao.save(sysUserSns);
	}

	@Override
	public void update(SysUserSns sysUserSns){
		sysUserSnsDao.update(sysUserSns);
	}

	@Override
	public void delete(Long userId){
		sysUserSnsDao.delete(userId);
	}

	@Override
	public void deleteBatch(Long[] userIds){
		sysUserSnsDao.deleteBatch(userIds);
	}

    @Override
    public void updateState(String[] ids,String stateValue) {
        for (String id:ids){
			SysUserSns sysUserSns=get(Long.parseLong(id));
			sysUserSns.setState(stateValue);
            update(sysUserSns);
        }
    }

    @Override
	public SysUserSns getBySns(String snsUserId, String snsType){
		return sysUserSnsDao.getBySns(snsUserId, snsType);
	}

	@Override
	public int updateUserId(SysUserSns sysUserSns) {
		return sysUserSnsDao.updateUserId(sysUserSns);
	}

	@Override
	public SysUserSns getBySnsUserId(String openId, String snsType) {
		return sysUserSnsDao.getBySnsUserId(openId,snsType);
	}
}
