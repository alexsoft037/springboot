package xin.xiaoer.modules.excitingactivity.dao;

import org.apache.ibatis.annotations.Param;
import xin.xiaoer.dao.BaseDao;
import xin.xiaoer.modules.excitingactivity.entity.ExcitingActivity;

import java.util.List;
import java.util.Map;

public interface ExcitingActivityDao extends BaseDao<ExcitingActivity>{
    List<ExcitingActivity> getExcitingActivityList(Map<String, Object> map);

    Integer countListData(Map<String, Object> search);

    int getPersonalCount(Map<String, Object> params);

    ExcitingActivity getByAddress(@Param("address") String address, @Param("useId") Long useId);

    ExcitingActivity getDetail(Integer activityId);
}
