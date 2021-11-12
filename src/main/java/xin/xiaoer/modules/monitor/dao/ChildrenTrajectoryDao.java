package xin.xiaoer.modules.monitor.dao;

import xin.xiaoer.dao.BaseDao;
import xin.xiaoer.modules.monitor.entity.ChildrenTrajectory;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-09-03 12:51:23
 */
public interface ChildrenTrajectoryDao extends BaseDao<ChildrenTrajectory> {

    List<ChildrenTrajectory> getPointList(Map<String, Object> map);

    List<String> getTimeList(String day);

    List<ChildrenTrajectory> getTimeDataList(String time);
}
