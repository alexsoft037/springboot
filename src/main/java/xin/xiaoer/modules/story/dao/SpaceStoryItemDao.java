package xin.xiaoer.modules.story.dao;

import xin.xiaoer.dao.BaseDao;
import xin.xiaoer.modules.story.entity.SpaceStoryItem;

import java.util.List;

/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-07-27 13:23:15
 */
public interface SpaceStoryItemDao extends BaseDao<SpaceStoryItem> {

    List<SpaceStoryItem> getListByStoryId(Long storyId);
}
