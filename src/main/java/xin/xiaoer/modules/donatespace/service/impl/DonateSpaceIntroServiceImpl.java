package xin.xiaoer.modules.donatespace.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

import xin.xiaoer.modules.donatespace.dao.DonateSpaceIntroDao;
import xin.xiaoer.modules.donatespace.entity.DonateSpaceIntro;
import xin.xiaoer.modules.donatespace.service.DonateSpaceIntroService;




@Service("donateSpaceIntroService")
@Transactional
public class DonateSpaceIntroServiceImpl implements DonateSpaceIntroService {
	@Autowired
	private DonateSpaceIntroDao donateSpaceIntroDao;
	
	@Override
	public DonateSpaceIntro get(Long itemId){
		return donateSpaceIntroDao.get(itemId);
	}

	@Override
	public List<DonateSpaceIntro> getList(Map<String, Object> map){
		return donateSpaceIntroDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return donateSpaceIntroDao.getCount(map);
	}

	@Override
	public void save(DonateSpaceIntro donateSpaceIntro){
		donateSpaceIntroDao.save(donateSpaceIntro);
	}

	@Override
	public void update(DonateSpaceIntro donateSpaceIntro){
		donateSpaceIntroDao.update(donateSpaceIntro);
	}

	@Override
	public void delete(Long itemId){
		donateSpaceIntroDao.delete(itemId);
	}

	@Override
	public void deleteBatch(Long[] itemIds){
		donateSpaceIntroDao.deleteBatch(itemIds);
	}

    @Override
    public void updateState(String[] ids,String stateValue) {
        for (String id:ids){
			DonateSpaceIntro donateSpaceIntro=get(Long.parseLong(id));
			donateSpaceIntro.setState(stateValue);
            update(donateSpaceIntro);
        }
    }
	
}
