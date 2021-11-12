package xin.xiaoer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xin.xiaoer.dao.SpaceNewsDao;
import xin.xiaoer.entity.SpaceNews;
import xin.xiaoer.service.SpaceNewsService;

import java.util.List;
import java.util.Map;




@Service("spaceNewsService")
@Transactional
public class SpaceNewsServiceImpl implements SpaceNewsService {
	@Autowired
	private SpaceNewsDao spaceNewsDao;
	
	@Override
	public SpaceNews get(Integer id){
		return spaceNewsDao.get(id);
	}

	@Override
	public List<SpaceNews> getList(Map<String, Object> map){
		return spaceNewsDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return spaceNewsDao.getCount(map);
	}

	@Override
	public void save(SpaceNews spaceNews){
		spaceNewsDao.save(spaceNews);
	}

	@Override
	public void update(SpaceNews spaceNews){
		spaceNewsDao.update(spaceNews);
	}

	@Override
	public void delete(Integer id){
		spaceNewsDao.delete(id);
	}

	@Override
	public void deleteBatch(Integer[] ids){
		spaceNewsDao.deleteBatch(ids);
	}

    @Override
    public void updateState(String[] ids,String stateValue) {
        for (String id:ids){
			SpaceNews spaceNews=get(Integer.parseInt(id));
			spaceNews.setState(stateValue);
            update(spaceNews);
        }
    }

    @Override
	public List<SpaceNews> getNewsList(Map<String, Object> map){
		return spaceNewsDao.getNewsList(map);
	}

	@Override
	public List getreviewList(Map<String, Object> map) {
		return spaceNewsDao.getreviewList(map);
	}

	@Override
	public List getlikeList(Map<String, Object> map) {
		return spaceNewsDao.getlikeList(map);
	}

	@Override
	public int getcollectionCount(Map<String, Object> map) {
		return spaceNewsDao.getcollectionCount(map);
	}

	@Override
	public int getNewsCount(Map<String, Object> map) {
		return spaceNewsDao.getNewsCount(map);
	}

	@Override
	public List getnewsbylocation(Map<String, Object> map) {
		return spaceNewsDao.getnewsbylocation(map);
	}

	@Override
	public int getallnewscount(Map<String, Object> map) {
		return spaceNewsDao.getallnewscount(map);
	}

	@Override
	public List getallspacenews(Map<String, Object> map) {
		return spaceNewsDao.getallspacenews(map);
	}

	@Override
	public List getcollectionList(Map<String, Object> map) {
		return spaceNewsDao.getcollectionList(map);
	}

	@Override
	public int getreviewcount(Map<String, Object> map) {
		return spaceNewsDao.getreviewcount(map);
	}

	@Override
	public int getlikeCount(Map<String, Object> map) {
		return spaceNewsDao.getlikeCount(map);
	}
}
