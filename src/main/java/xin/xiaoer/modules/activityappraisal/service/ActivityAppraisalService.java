package xin.xiaoer.modules.activityappraisal.service;

import xin.xiaoer.modules.activityappraisal.entity.ActivityAppraisal;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-07-25 01:47:05
 */
public interface ActivityAppraisalService {
	
	ActivityAppraisal get(Integer appraisalId);
	
	List<ActivityAppraisal> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(ActivityAppraisal activityAppraisal);
	
	void update(ActivityAppraisal activityAppraisal);
	
	void delete(Integer appraisalId);
	
	void deleteBatch(Integer[] appraisalIds);

    void updateState(String[] ids, String stateValue);
}
