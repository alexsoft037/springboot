package xin.xiaoer.modules.activity.service;

import xin.xiaoer.modules.activity.entity.Activity;
import xin.xiaoer.modules.mobile.entity.ActivityFeaturedItem;
import xin.xiaoer.modules.mobile.entity.ActivityListItem;
import xin.xiaoer.modules.mobile.entity.PersonalActivity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-07-23 20:59:06
 */
public interface ActivityService {
	
	Activity get(Integer activityId);
	
	List<Activity> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(Activity activity);
	
	void update(Activity activity);
	
	void delete(Integer activityId);
	
	void deleteBatch(Integer[] activityIds);

    void updateState(String[] ids, String stateValue);

	List<ActivityFeaturedItem> getFeaturedItems(Map<String, Object> params);

	List<ActivityListItem> queryListData(Map<String, Object> search);

	Integer countListData(Map<String, Object> search);

	List<PersonalActivity> getPersonalList(Map<String, Object> params);

	int getPersonalCount(Map<String, Object> params);

	int getreviewcount(Map<String, Object> params);

	Activity checkExpired(Activity activity);

	Activity checkExpiredByActivityId(Integer activityId);

	ActivityListItem getListItemObject(Map<String, Object> params);

	Activity getByAddress(String address, Long useId);

	Activity getDetail(Integer activityId);

	List<Activity> getByLatLng(Map<String, Object> params);

	List<Activity> getListAll(Map<String, Object> params);

	List getHotActivity(Map<String, String> params);

	List<Activity> getSplendidActivity(Integer spaceId);

	List getreviewList(Map<String, Object> params);

	int getlikeCount(Map<String, Object> map);

	List getlikeList(Map<String, Object> map);

	List getActivityAppraisal(Map<String, Object> map);

	Map getPublicCount(Map<String, Object> params);

	int gethotacCount(Map<String, Object> map);

	int getappraisalCount(Map<String, Object> map);

	List getActivityAppraisalDetail(Map<String, Object> params);

	List getpassedactivityList(Map<String, Object> params);

	int getpassedactivityCount(Map<String, Object> map);

	List getfinishactivityList(Map<String, Object> params);

	int getfinishactivityCount(Map<String, Object> map);
}
