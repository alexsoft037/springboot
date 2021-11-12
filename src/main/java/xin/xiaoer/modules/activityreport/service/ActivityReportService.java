package xin.xiaoer.modules.activityreport.service;

import xin.xiaoer.modules.activityreport.entity.ActivityReport;
import xin.xiaoer.modules.mobile.entity.ActivityReportListItem;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-07-24 17:57:36
 */
public interface ActivityReportService {
	
	ActivityReport get(Integer reportId);
	
	List<ActivityReport> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(ActivityReport activityReport);
	
	void update(ActivityReport activityReport);
	
	void delete(Integer reportId);
	
	void deleteBatch(Integer[] reportIds);

    void updateState(String[] ids, String stateValue);

	List<ActivityReportListItem> queryListData(Map<String, Object> search);

	Integer countListData(Map<String, Object> search);

	ActivityReportListItem getListItemObject(Map search);

	ActivityReport getDetail(Integer reportId);
}
