package xin.xiaoer.modules.help.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

import xin.xiaoer.modules.help.dao.HelpCenterDao;
import xin.xiaoer.modules.help.entity.HelpCenter;
import xin.xiaoer.modules.help.service.HelpCenterService;




@Service("helpCenterService")
@Transactional
public class HelpCenterServiceImpl implements HelpCenterService {
	@Autowired
	private HelpCenterDao helpCenterDao;
	
	@Override
	public HelpCenter get(Integer helpId){
		return helpCenterDao.get(helpId);
	}

	@Override
	public List<HelpCenter> getList(Map<String, Object> map){
		return helpCenterDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return helpCenterDao.getCount(map);
	}

	@Override
	public void save(HelpCenter helpCenter){
		helpCenterDao.save(helpCenter);
	}

	@Override
	public void update(HelpCenter helpCenter){
		helpCenterDao.update(helpCenter);
	}

	@Override
	public void delete(Integer helpId){
		helpCenterDao.delete(helpId);
	}

	@Override
	public void deleteBatch(Integer[] helpIds){
		helpCenterDao.deleteBatch(helpIds);
	}

    @Override
    public void updateState(String[] ids,String stateValue) {
        for (String id:ids){
			HelpCenter helpCenter=get(Integer.parseInt(id));
			helpCenter.setState(stateValue);
            update(helpCenter);
        }
    }

    @Override
	public HelpCenter getByCategory(String category){
		return helpCenterDao.getByCategory(category);
	}
}
