package xin.xiaoer.modules.spaceshow.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

import xin.xiaoer.modules.spaceshow.dao.SpaceShowDao;
import xin.xiaoer.modules.spaceshow.entity.SpaceShow;
import xin.xiaoer.modules.spaceshow.service.SpaceShowService;




@Service("spaceShowService")
@Transactional
public class SpaceShowServiceImpl implements SpaceShowService {
	@Autowired
	private SpaceShowDao spaceShowDao;
	
	@Override
	public SpaceShow get(Long showId){
		return spaceShowDao.get(showId);
	}

	@Override
	public List<SpaceShow> getList(Map<String, Object> map){
		return spaceShowDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return spaceShowDao.getCount(map);
	}

	@Override
	public void save(SpaceShow spaceShow){
		spaceShowDao.save(spaceShow);
	}

	@Override
	public void update(SpaceShow spaceShow){
		spaceShowDao.update(spaceShow);
	}

	@Override
	public void delete(Long showId){
		spaceShowDao.delete(showId);
	}

	@Override
	public void deleteBatch(Long[] showIds){
		spaceShowDao.deleteBatch(showIds);
	}

    @Override
    public void updateState(String[] ids,String stateValue) {
        for (String id:ids){
			SpaceShow spaceShow=get(Long.parseLong(id));
			spaceShow.setState(stateValue);
            update(spaceShow);
        }
    }

	@Override
	public List<SpaceShow> getRecentList(Map<String, Object> map){
		return spaceShowDao.getRecentList(map);
	}

	@Override
	public int getRecentCount(Map<String, Object> map){
		return spaceShowDao.getRecentCount(map);
	}

	@Override
	public List<SpaceShow> getPopularList(Map<String, Object> map){
		return spaceShowDao.getPopularList(map);
	}

	@Override
	public int getPopularCount(Map<String, Object> map){
		return spaceShowDao.getPopularCount(map);
	}

	@Override
	public List<SpaceShow> getActivityShowList(Map<String, Object> map){
		return spaceShowDao.getActivityShowList(map);
	}

	@Override
	public int getActivityShowCount(Map<String, Object> map){
		return spaceShowDao.getActivityShowCount(map);
	}

	@Override
	public int getreviewcount(Map<String, Object> map) {
		return spaceShowDao.getreviewcount(map);
	}

	@Override
	public List getSpaceShowByUser(Map<String, Object> map) {
		return spaceShowDao.getSpaceShowByUser(map);
	}

	@Override
	public List getreviewList(Map<String, Object> map) {
		return spaceShowDao.getreviewList(map);
	}

	@Override
	public int getlikeCount(Map<String, Object> map) {
		return spaceShowDao.getlikeCount(map);
	}

	@Override
	public List getlikeList(Map<String, Object> map) {
		return spaceShowDao.getlikeList(map);
	}

	@Override
	public int getShowCount(Map<String, Object> map) {
		return spaceShowDao.getShowCount(map);
	}

	@Override
	public List getMyShowByUser(Map<String, Object> map) {
		return spaceShowDao.getMyShowByUser(map);
	}

	@Override
	public int getMyShowCount(Map<String, Object> map) {
		return spaceShowDao.getMyShowCount(map);
	}
}
