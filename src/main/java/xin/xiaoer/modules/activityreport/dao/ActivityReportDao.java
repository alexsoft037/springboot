package xin.xiaoer.modules.activityreport.dao;

import xin.xiaoer.dao.BaseDao;
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
public interface ActivityReportDao extends BaseDao<ActivityReport> {
    List<ActivityReportListItem> queryListData(Map<String, Object> search);

    Integer countListData(Map<String, Object> search);

    ActivityReportListItem getListItemObject(Map search);

    ActivityReport getDetail(Integer reportId);
}
