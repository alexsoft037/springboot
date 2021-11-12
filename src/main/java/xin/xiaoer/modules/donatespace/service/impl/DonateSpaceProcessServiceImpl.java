package xin.xiaoer.modules.donatespace.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

import xin.xiaoer.modules.donatespace.dao.DonateSpaceProcessDao;
import xin.xiaoer.modules.donatespace.entity.DonateSpaceProcess;
import xin.xiaoer.modules.donatespace.service.DonateSpaceProcessService;




@Service("donateSpaceProcessService")
@Transactional
public class DonateSpaceProcessServiceImpl implements DonateSpaceProcessService {
	@Autowired
	private DonateSpaceProcessDao donateSpaceProcessDao;
	
	@Override
	public DonateSpaceProcess get(Long itemId){
		return donateSpaceProcessDao.get(itemId);
	}

	@Override
	public List<DonateSpaceProcess> getList(Map<String, Object> map){
		return donateSpaceProcessDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return donateSpaceProcessDao.getCount(map);
	}

	@Override
	public void save(DonateSpaceProcess donateSpaceProcess){
		donateSpaceProcessDao.save(donateSpaceProcess);
	}

	@Override
	public void update(DonateSpaceProcess donateSpaceProcess){
		donateSpaceProcessDao.update(donateSpaceProcess);
	}

	@Override
	public void delete(Long itemId){
		donateSpaceProcessDao.delete(itemId);
	}

	@Override
	public void deleteBatch(Long[] itemIds){
		donateSpaceProcessDao.deleteBatch(itemIds);
	}

    @Override
    public void updateState(String[] ids,String stateValue) {
        for (String id:ids){
			DonateSpaceProcess donateSpaceProcess=get(Long.parseLong(id));
			donateSpaceProcess.setState(stateValue);
            update(donateSpaceProcess);
        }
    }
	
}
