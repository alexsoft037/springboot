package xin.xiaoer.modules.newcommonweal.dao;

import org.apache.ibatis.annotations.Param;
import xin.xiaoer.dao.BaseDao;
import xin.xiaoer.modules.newcommonweal.entity.NewCommonweal;

import java.util.List;
import java.util.Map;

public interface NewCommonwealDao extends BaseDao<NewCommonweal>{
    List<NewCommonweal> getNewConmonwealList(Map<String, Object> map);
    Integer countListData(Map<String, Object> search);

    int getPersonalCount(Map<String, Object> params);

    NewCommonweal getByAddress(@Param("address") String address, @Param("useId") Long useId);

    NewCommonweal getDetail(Integer activityId);
}
