package xin.xiaoer.modules.newcommonweal.service;

import xin.xiaoer.modules.newcommonweal.entity.NewCommonweal;

import java.util.List;
import java.util.Map;

public interface NewCommonwealService {
    NewCommonweal get(Integer id);

    List<NewCommonweal> getList(Map<String, Object> map);

    int getCount(Map<String, Object> map);

    void save(NewCommonweal newCommonweal);

    void update(NewCommonweal newCommonweal);

    void delete(Integer id);

    void deleteBatch(Integer[] ids);

    void updateState(String[] ids, String stateValue);

    List<NewCommonweal> getNewCommonwealList(Map<String, Object> map);

    NewCommonweal getDetail(Integer newCommonwealId);

    NewCommonweal checkExpired(NewCommonweal newCommonweal);

    NewCommonweal checkExpiredByHotStoryId(Integer newCommonwealId);

    List<NewCommonweal> getByLatLng(Map<String, Object> params);
}
