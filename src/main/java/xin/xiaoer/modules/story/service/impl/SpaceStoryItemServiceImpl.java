package xin.xiaoer.modules.story.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

import xin.xiaoer.modules.story.dao.SpaceStoryItemDao;
import xin.xiaoer.modules.story.entity.SpaceStoryItem;
import xin.xiaoer.modules.story.service.SpaceStoryItemService;




@Service("spaceStoryItemService")
@Transactional
public class SpaceStoryItemServiceImpl implements SpaceStoryItemService {
	@Autowired
	private SpaceStoryItemDao spaceStoryItemDao;
	
	@Override
	public SpaceStoryItem get(Long storyId){
		return spaceStoryItemDao.get(storyId);
	}

	@Override
	public List<SpaceStoryItem> getList(Map<String, Object> map){
		return spaceStoryItemDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return spaceStoryItemDao.getCount(map);
	}

	@Override
	public void save(SpaceStoryItem spaceStoryItem){
		spaceStoryItemDao.save(spaceStoryItem);
	}

	@Override
	public void update(SpaceStoryItem spaceStoryItem){
		spaceStoryItemDao.update(spaceStoryItem);
	}

	@Override
	public void delete(Long storyId){
		spaceStoryItemDao.delete(storyId);
	}

	@Override
	public void deleteBatch(Long[] storyIds){
		spaceStoryItemDao.deleteBatch(storyIds);
	}

    @Override
    public void updateState(String[] ids,String stateValue) {
        for (String id:ids){
			SpaceStoryItem spaceStoryItem=get(Long.parseLong(id));
//			spaceStoryItem.setState(stateValue);
            update(spaceStoryItem);
        }
    }

    @Override
	public List<SpaceStoryItem> getListByStoryId(Long storyId){
		return spaceStoryItemDao.getListByStoryId(storyId);
	}
}
