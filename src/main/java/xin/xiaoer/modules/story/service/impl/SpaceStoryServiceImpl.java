package xin.xiaoer.modules.story.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xin.xiaoer.modules.story.dao.SpaceStoryDao;
import xin.xiaoer.modules.story.entity.SpaceStory;
import xin.xiaoer.modules.story.service.SpaceStoryService;

import java.util.List;
import java.util.Map;




@Service("spaceStoryService")
@Transactional
public class SpaceStoryServiceImpl implements SpaceStoryService {
	@Autowired
	private SpaceStoryDao spaceStoryDao;
	
	@Override
	public SpaceStory get(Long storyId){
		return spaceStoryDao.get(storyId);
	}

	@Override
	public SpaceStory getDetail(Long storyId, Long userId){
		return spaceStoryDao.getDetail(storyId, userId);
	}

	@Override
	public List<SpaceStory> getList(Map<String, Object> map){
		return spaceStoryDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return spaceStoryDao.getCount(map);
	}

	@Override
	public void save(SpaceStory spaceStory){
		spaceStoryDao.save(spaceStory);
	}

	@Override
	public void update(SpaceStory spaceStory){
		spaceStoryDao.update(spaceStory);
	}

	@Override
	public void delete(Long storyId){
		spaceStoryDao.delete(storyId);
	}

	@Override
	public void deleteBatch(Long[] storyIds){
		spaceStoryDao.deleteBatch(storyIds);
	}

    @Override
    public void updateState(String[] ids,String stateValue) {
        for (String id:ids){
			SpaceStory spaceStory=get(Long.parseLong(id));
			spaceStory.setState(stateValue);
            update(spaceStory);
        }
    }

	@Override
	public List<SpaceStory> getAPIList(Map<String, Object> map){
		return spaceStoryDao.getAPIList(map);
	}

	@Override
	public int getAPICount(Map<String, Object> map){
		return spaceStoryDao.getAPICount(map);
	}

	@Override
	public int getreviewcount(Map<String, Object> map) {
		return spaceStoryDao.getreviewcount(map);
	}

	@Override
	public List<SpaceStory> getHotStory(Map<String,Object> params) {
		return spaceStoryDao.getHotStory(params);
	}

	@Override
	public List getreviewList(Map<String, Object> params) {
		return spaceStoryDao.getreviewList(params);
	}

	@Override
	public int getlikeCount(Map<String, Object> map) {
		return spaceStoryDao.getlikeCount(map);
	}

	@Override
	public List getlikeList(Map<String, Object> map) {
		return spaceStoryDao.getlikeList(map);
	}

	@Override
	public List getstoryList(Map<String, Object> map) {
		return spaceStoryDao.getstoryList(map);
	}

	@Override
	public int getstoryCount(Map<String, Object> map) {
		return spaceStoryDao.getstoryCount(map);
	}

	@Override
	public List getmyStoryList(Map<String, Object> map) {
		return spaceStoryDao.getmyStoryList(map);
	}

	@Override
	public int getmyStoryCount(Map<String, Object> map) {
		return spaceStoryDao.getmyStoryCount(map);
	}
}
