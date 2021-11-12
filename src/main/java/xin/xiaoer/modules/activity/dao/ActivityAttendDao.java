package xin.xiaoer.modules.activity.dao;

import org.apache.ibatis.annotations.Param;
import xin.xiaoer.dao.BaseDao;
import xin.xiaoer.modules.activity.entity.ActivityAttend;

/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-07-24 12:11:49
 */
public interface ActivityAttendDao extends BaseDao<ActivityAttend> {

    ActivityAttend getByUserIdAndActivityId(@Param("activityId") Integer activityId,@Param("userId") Long userId);

    Integer getCountByActivityId(Integer activityId);

    int deleteByActivityId(Object id);

    int queryAttendTotal();
}
