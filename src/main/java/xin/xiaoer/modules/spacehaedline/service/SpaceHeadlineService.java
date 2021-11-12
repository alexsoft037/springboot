package xin.xiaoer.modules.spacehaedline.service;

import xin.xiaoer.common.utils.Query;
import xin.xiaoer.modules.mobile.entity.HeadlineListItem;
import xin.xiaoer.modules.spacehaedline.entity.SpaceHeadline;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-07-24 09:07:02
 */
public interface SpaceHeadlineService {
	
	SpaceHeadline get(Integer headlineId);
	
	List<SpaceHeadline> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(SpaceHeadline spaceHeadline);
	
	void update(SpaceHeadline spaceHeadline);
	
	void delete(Integer headlineId);
	
	void deleteBatch(Integer[] headlineIds);

    void updateState(String[] ids, String stateValue);

	List<HeadlineListItem> queryListData(Map<String, Object> search);

	Integer countListData(Map<String, Object> search);

	HeadlineListItem getListItemObject(Map search);

    List<HeadlineListItem> getNewsFlash(Query query);

	List<HeadlineListItem> getIntervalNews(Query query);

	List<HeadlineListItem> getrecommendeNews(Query query);
}
