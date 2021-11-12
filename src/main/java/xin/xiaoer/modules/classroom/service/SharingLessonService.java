package xin.xiaoer.modules.classroom.service;

import xin.xiaoer.modules.classroom.entity.SharingLesson;
import xin.xiaoer.modules.mobile.entity.VideoLessonListItem;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-08-15 16:13:22
 */
public interface SharingLessonService {
	
	SharingLesson get(Integer lessonId);
	
	List<SharingLesson> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(SharingLesson sharingLesson);
	
	void update(SharingLesson sharingLesson);
	
	void delete(Integer lessonId);
	
	void deleteBatch(Integer[] lessonIds);

    void updateState(String[] ids, String stateValue);

	int removeCategoryCode(String categoryCode);

	List<VideoLessonListItem> getApiListData(Map<String, Object> map);

	int getApiCount(Map<String, Object> map);

	List<VideoLessonListItem> getFeaturedList(Map<String, Object> map);

	List<VideoLessonListItem> getApiListDataByUser(Map<String, Object> map);

	int getApiCountByUser(Map<String, Object> map);

	VideoLessonListItem getListItemObject(Map search);

	SharingLesson getDetail(Integer lessonId);

	List getshareList(Map<String, Object> map);

	int getshareCount(Map<String, Object> map);
}
