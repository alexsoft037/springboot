package xin.xiaoer.modules.excitingactivity.service;

import xin.xiaoer.modules.excitingactivity.entity.ExcitingActivity;
import java.util.List;
import java.util.Map;

public interface ExcitingActivityService {
    ExcitingActivity get(Integer id);

    List<ExcitingActivity> getList(Map<String, Object> map);

    int getCount(Map<String, Object> map);

    void save(ExcitingActivity newCommonweal);

    void update(ExcitingActivity newCommonweal);

    void delete(Integer id);

    void deleteBatch(Integer[] ids);

    void updateState(String[] ids, String stateValue);

    List<ExcitingActivity> getExcitingActivitylList(Map<String, Object> map);

    ExcitingActivity getDetail(Integer excitingActivityId);

    ExcitingActivity checkExpired(ExcitingActivity excitingActivity);

    ExcitingActivity checkExpiredByExcitingActicityId(Integer excitingActivityId);

    List<ExcitingActivity> getByLatLng(Map<String, Object> params);
}
