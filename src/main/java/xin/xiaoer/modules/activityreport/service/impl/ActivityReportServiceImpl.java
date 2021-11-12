package xin.xiaoer.modules.activityreport.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

import xin.xiaoer.modules.activityreport.dao.ActivityReportDao;
import xin.xiaoer.modules.activityreport.entity.ActivityReport;
import xin.xiaoer.modules.activityreport.service.ActivityReportService;
import xin.xiaoer.modules.mobile.entity.ActivityReportListItem;


@Service("activityReportService")
@Transactional
public class ActivityReportServiceImpl implements ActivityReportService {
	@Autowired
	private ActivityReportDao activityReportDao;
	
	@Override
	public ActivityReport get(Integer reportId){
		return activityReportDao.get(reportId);
	}

	@Override
	public List<ActivityReport> getList(Map<String, Object> map){
		return activityReportDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return activityReportDao.getCount(map);
	}

	@Override
	public void save(ActivityReport activityReport){
		activityReportDao.save(activityReport);
	}

	@Override
	public void update(ActivityReport activityReport){
		activityReportDao.update(activityReport);
	}

	@Override
	public void delete(Integer reportId){
		activityReportDao.delete(reportId);
	}

	@Override
	public void deleteBatch(Integer[] reportIds){
		activityReportDao.deleteBatch(reportIds);
	}

    @Override
    public void updateState(String[] ids,String stateValue) {
        for (String id:ids){
			ActivityReport activityReport=get(Integer.parseInt(id));
			activityReport.setState(stateValue);
            update(activityReport);
        }
    }

	@Override
	public List<ActivityReportListItem> queryListData(Map<String, Object> search){
		return activityReportDao.queryListData(search);
	}

	@Override
	public Integer countListData(Map<String, Object> search){
		return activityReportDao.countListData(search);
	}

	@Override
	public ActivityReportListItem getListItemObject(Map search){
		return activityReportDao.getListItemObject(search);
	}

	@Override
	public ActivityReport getDetail(Integer reportId) {
		return activityReportDao.getDetail(reportId);
	}
}
