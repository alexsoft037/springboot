package xin.xiaoer.modules.story.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

import xin.xiaoer.modules.story.dao.SpaceStoryHtmlDao;
import xin.xiaoer.modules.story.entity.SpaceStoryHtml;
import xin.xiaoer.modules.story.service.SpaceStoryHtmlService;




@Service("spaceStoryHtmlService")
@Transactional
public class SpaceStoryHtmlServiceImpl implements SpaceStoryHtmlService {
	@Autowired
	private SpaceStoryHtmlDao spaceStoryHtmlDao;
	
	@Override
	public SpaceStoryHtml get(Long storyId){
		return spaceStoryHtmlDao.get(storyId);
	}

	@Override
	public List<SpaceStoryHtml> getList(Map<String, Object> map){
		return spaceStoryHtmlDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return spaceStoryHtmlDao.getCount(map);
	}

	@Override
	public void save(SpaceStoryHtml spaceStoryHtml){
		spaceStoryHtmlDao.save(spaceStoryHtml);
	}

	@Override
	public void update(SpaceStoryHtml spaceStoryHtml){
		spaceStoryHtmlDao.update(spaceStoryHtml);
	}

	@Override
	public void delete(Long storyId){
		spaceStoryHtmlDao.delete(storyId);
	}

	@Override
	public void deleteBatch(Long[] storyIds){
		spaceStoryHtmlDao.deleteBatch(storyIds);
	}

    @Override
    public void updateState(String[] ids,String stateValue) {
        for (String id:ids){
			SpaceStoryHtml spaceStoryHtml=get(Long.parseLong(id));
//			spaceStoryHtml.setState(stateValue);
            update(spaceStoryHtml);
        }
    }
	
}
