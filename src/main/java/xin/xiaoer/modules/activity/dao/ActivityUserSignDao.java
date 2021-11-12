package xin.xiaoer.modules.activity.dao;

import org.apache.ibatis.annotations.Param;
import xin.xiaoer.dao.BaseDao;
import xin.xiaoer.modules.activity.entity.ActivityUserSign;

/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-10-08 01:01:48
 */
public interface ActivityUserSignDao extends BaseDao<ActivityUserSign> {

    ActivityUserSign getByUserAndActivity(@Param("userId") Long userId, @Param("activityId") Integer activityId);
}
