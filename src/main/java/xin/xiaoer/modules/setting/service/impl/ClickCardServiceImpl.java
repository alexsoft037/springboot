package xin.xiaoer.modules.setting.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

import xin.xiaoer.modules.setting.dao.ClickCardDao;
import xin.xiaoer.modules.setting.entity.ClickCard;
import xin.xiaoer.modules.setting.service.ClickCardService;




@Service("clickCardService")
@Transactional
public class ClickCardServiceImpl implements ClickCardService {
	@Autowired
	private ClickCardDao clickCardDao;
	
	@Override
	public ClickCard get(Long userId){
		return clickCardDao.get(userId);
	}

	@Override
	public List<ClickCard> getList(Map<String, Object> map){
		return clickCardDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return clickCardDao.getCount(map);
	}

	@Override
	public void save(ClickCard clickCard){
		clickCardDao.save(clickCard);
	}

	@Override
	public void update(ClickCard clickCard){
		clickCardDao.update(clickCard);
	}

	@Override
	public void delete(Long userId){
		clickCardDao.delete(userId);
	}

	@Override
	public void deleteBatch(Long[] userIds){
		clickCardDao.deleteBatch(userIds);
	}

    @Override
    public void updateState(String[] ids,String stateValue) {
        for (String id:ids){
			ClickCard clickCard=get(Long.parseLong(id));
//			clickCard.setState(stateValue);
            update(clickCard);
        }
    }
	
}
