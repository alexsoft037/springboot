package xin.xiaoer.modules.classroom.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

import xin.xiaoer.modules.classroom.dao.AccountDao;
import xin.xiaoer.modules.classroom.entity.Account;
import xin.xiaoer.modules.classroom.service.AccountService;




@Service("accountService")
@Transactional
public class AccountServiceImpl implements AccountService {
	@Autowired
	private AccountDao accountDao;
	
	@Override
	public Account get(String uid){
		return accountDao.get(uid);
	}

	@Override
	public List<Account> getList(Map<String, Object> map){
		return accountDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return accountDao.getCount(map);
	}

	@Override
	public void save(Account account){
		accountDao.save(account);
	}

	@Override
	public void update(Account account){
		accountDao.update(account);
	}

	@Override
	public void delete(String uid){
		accountDao.delete(uid);
	}

	@Override
	public void deleteBatch(String[] uids){
		accountDao.deleteBatch(uids);
	}

    @Override
    public void updateState(String[] ids,String stateValue) {
        for (String id:ids){
			Account account=get(id);
            update(account);
        }
    }
	
}
