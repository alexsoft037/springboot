package xin.xiaoer.modules.activity.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xin.xiaoer.modules.activity.dao.ActivityDao;
import xin.xiaoer.modules.activity.entity.Activity;
import xin.xiaoer.modules.activity.service.ActivityService;
import xin.xiaoer.modules.mobile.entity.ActivityFeaturedItem;
import xin.xiaoer.modules.mobile.entity.ActivityListItem;
import xin.xiaoer.modules.mobile.entity.PersonalActivity;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Service("activityService")
@Transactional
public class ActivityServiceImpl implements ActivityService {
	@Autowired
	private ActivityDao activityDao;
	
	@Override
	public Activity get(Integer activityId){
		return activityDao.get(activityId);
	}

	@Override
	public List<Activity> getList(Map<String, Object> map){
		return activityDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return activityDao.getCount(map);
	}

	@Override
	public void save(Activity activity){
		activityDao.save(activity);
	}

	@Override
	public void update(Activity activity){
		activityDao.update(activity);
	}

	@Override
	public void delete(Integer activityId){
		activityDao.delete(activityId);
	}

	@Override
	public void deleteBatch(Integer[] activityIds){
		activityDao.deleteBatch(activityIds);
	}

    @Override
    public void updateState(String[] ids,String stateValue) {
        for (String id:ids){
			Activity activity =get(Integer.parseInt(id));
			activity.setState(stateValue);
            update(activity);
        }
    }

	@Override
	public List<ActivityFeaturedItem> getFeaturedItems(Map<String, Object> params){
		return activityDao.getFeaturedItems(params);
	}

	@Override
	public List<ActivityListItem> queryListData(Map<String, Object> search){
		return activityDao.queryListData(search);
	}

	@Override
	public Integer countListData(Map<String, Object> search){
		return activityDao.countListData(search);
	}

	@Override
	public List<PersonalActivity> getPersonalList(Map<String, Object> params){
		return activityDao.getPersonalList(params);
	}

	@Override
	public int getPersonalCount(Map<String, Object> params){
		return activityDao.getPersonalCount(params);
	}

	@Override
	public int getreviewcount(Map<String, Object> params) {
		return activityDao.getreviewcount(params);
	}

	@Override
	public Activity checkExpired(Activity activity){
		Date today = new Date();
		if (activity.getPeriodTo().before(today)) {
			activity.setActivityStatusCode("AVS003");
			activityDao.update(activity);
			activity.setActivityStatus("已结束");
		}

		if (activity.getActivityStatusCode().equals("AVS001") && ( activity.getRegisterEnd().before(today) || activity.getPeriodFrom().before(today))){
			activity.setActivityStatusCode("AVS002");
			activityDao.update(activity);
			activity.setActivityStatus("进行中");
		}
		return activity;
	}

	@Override
	public Activity checkExpiredByActivityId(Integer activityId){
		Activity activity = get(activityId);
		activity = checkExpired(activity);
		return activity;
	}

	@Override
	public ActivityListItem getListItemObject(Map<String, Object> params){
		return activityDao.getListItemObject(params);
	}

	@Override
	public Activity getByAddress(String address, Long useId){
		return activityDao.getByAddress(address, useId);
	}

	@Override
	public Activity getDetail(Integer activityId) {
		return activityDao.getDetail(activityId);
	}

	@Override
	public List<Activity> getByLatLng(Map<String, Object> params){
		return activityDao.getByLatLng(params);
	}

	@Override
	public List<Activity> getListAll(Map<String, Object> params){
		return activityDao.getListAll(params);
	}

	@Override
	public List getHotActivity(Map<String, String> params) {
		return activityDao.getHotActivity(params);
	}


	@Override
	public List<Activity> getSplendidActivity(Integer spaceId) {
		return activityDao.getSplendidActivity(spaceId);
	}

	@Override
	public List getreviewList(Map<String, Object> params) {
		return activityDao.getreviewList(params);
	}

	@Override
	public int getlikeCount(Map<String, Object> map) {
		return activityDao.getlikeCount(map);
	}

	@Override
	public List getlikeList(Map<String, Object> map) {
		return activityDao.getlikeList(map);
	}

	@Override
	public List getActivityAppraisal(Map<String, Object> map) {
		return activityDao.getActivityAppraisal(map);
	}

	@Override
	public Map getPublicCount(Map<String, Object> params) {
		return activityDao.getPublicCount(params);
	}

	@Override
	public int gethotacCount(Map<String, Object> map) {
		return activityDao.gethotacCount(map);
	}

	@Override
	public int getappraisalCount(Map<String, Object> map) {
		return activityDao.getappraisalCount(map);
	}

	@Override
	public List getActivityAppraisalDetail(Map<String, Object> params) {
		return activityDao.getActivityAppraisalDetail(params);
	}

	@Override
	public List getpassedactivityList(Map<String, Object> params) {
		return activityDao.getpassedactivityList(params);
	}

	@Override
	public int getpassedactivityCount(Map<String, Object> map) {
		return activityDao.getpassedactivityCount(map);
	}

	@Override
	public List getfinishactivityList(Map<String, Object> params) {
		return activityDao.getfinishactivityList(params);
	}

	@Override
	public int getfinishactivityCount(Map<String, Object> map) {
		return activityDao.getfinishactivityCount(map);
	}
}
