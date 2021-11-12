package xin.xiaoer.modules.spacehaedline.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xin.xiaoer.common.utils.Query;
import xin.xiaoer.modules.mobile.entity.HeadlineListItem;
import xin.xiaoer.modules.spacehaedline.dao.SpaceHeadlineDao;
import xin.xiaoer.modules.spacehaedline.entity.SpaceHeadline;
import xin.xiaoer.modules.spacehaedline.service.SpaceHeadlineService;

import java.util.List;
import java.util.Map;




@Service("spaceHeadlineService")
@Transactional
public class SpaceHeadlineServiceImpl implements SpaceHeadlineService {
	@Autowired
	private SpaceHeadlineDao spaceHeadlineDao;
	
	@Override
	public SpaceHeadline get(Integer headlineId){
		return spaceHeadlineDao.get(headlineId);
	}

	@Override
	public List<SpaceHeadline> getList(Map<String, Object> map){
		return spaceHeadlineDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return spaceHeadlineDao.getCount(map);
	}

	@Override
	public void save(SpaceHeadline spaceHeadline){
		spaceHeadlineDao.save(spaceHeadline);
	}

	@Override
	public void update(SpaceHeadline spaceHeadline){
		spaceHeadlineDao.update(spaceHeadline);
	}

	@Override
	public void delete(Integer headlineId){
		spaceHeadlineDao.delete(headlineId);
	}

	@Override
	public void deleteBatch(Integer[] headlineIds){
		spaceHeadlineDao.deleteBatch(headlineIds);
	}

    @Override
    public void updateState(String[] ids,String stateValue) {
        for (String id:ids){
			SpaceHeadline spaceHeadline=get(Integer.parseInt(id));
			spaceHeadline.setState(stateValue);
            update(spaceHeadline);
        }
    }

	@Override
	public List<HeadlineListItem> queryListData(Map<String, Object> search){
		return spaceHeadlineDao.queryListData(search);
	}

	@Override
	public Integer countListData(Map<String, Object> search){
		return spaceHeadlineDao.countListData(search);
	}

	@Override
	public HeadlineListItem getListItemObject(Map search){
		return spaceHeadlineDao.getListItemObject(search);
	}

	@Override
	public List<HeadlineListItem> getNewsFlash(Query query) {
		return spaceHeadlineDao.getNewsFlash(query);
	}

	@Override
	public List<HeadlineListItem> getIntervalNews(Query query) {
		return spaceHeadlineDao.getIntervalNews(query);
	}

	@Override
	public List<HeadlineListItem> getrecommendeNews(Query query) {
		return spaceHeadlineDao.getrecommendeNews(query);
	}
}
