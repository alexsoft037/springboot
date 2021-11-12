package xin.xiaoer.modules.monitor.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

import xin.xiaoer.modules.mobile.entity.LostNotice;
import xin.xiaoer.modules.monitor.dao.ChildrenLostDao;
import xin.xiaoer.modules.monitor.entity.ChildrenLost;
import xin.xiaoer.modules.monitor.service.ChildrenLostService;




@Service("childrenLostService")
@Transactional
public class ChildrenLostServiceImpl implements ChildrenLostService {
	@Autowired
	private ChildrenLostDao childrenLostDao;
	
	@Override
	public ChildrenLost get(Integer id){
		return childrenLostDao.get(id);
	}

	@Override
	public List<ChildrenLost> getList(Map<String, Object> map){
		return childrenLostDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return childrenLostDao.getCount(map);
	}

	@Override
	public void save(ChildrenLost childrenLost){
		childrenLostDao.save(childrenLost);
	}

	@Override
	public void update(ChildrenLost childrenLost){
		childrenLostDao.update(childrenLost);
	}

	@Override
	public void delete(Integer id){
		childrenLostDao.delete(id);
	}

	@Override
	public void deleteBatch(Integer[] ids){
		childrenLostDao.deleteBatch(ids);
	}

    @Override
    public void updateState(String[] ids,String stateValue) {
        for (String id:ids){
			ChildrenLost childrenLost=get(Integer.parseInt(id));
			childrenLost.setState(stateValue);
            update(childrenLost);
        }
    }

    @Override
	public List<LostNotice> getNoticeList(Map<String, Object> map){
		return childrenLostDao.getNoticeList(map);
	}
}
