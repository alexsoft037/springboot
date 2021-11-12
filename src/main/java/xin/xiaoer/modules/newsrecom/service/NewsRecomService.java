package xin.xiaoer.modules.newsrecom.service;

import xin.xiaoer.modules.newsrecom.entity.NewsRecom;
import java.util.List;
import java.util.Map;

public interface NewsRecomService {
    NewsRecom get(Integer id);

    List<NewsRecom> getList(Map<String, Object> map);

    int getCount(Map<String, Object> map);

    void save(NewsRecom newsRecom);

    void update(NewsRecom newsRecom);

    void delete(Integer id);

    void deleteBatch(Integer[] ids);

    void updateState(String[] ids, String stateValue);

    List<NewsRecom> getNewsRecomList(Map<String, Object> map);

    NewsRecom getDetail(Integer newsRecomId);

    NewsRecom checkExpired(NewsRecom newsrecom);

    NewsRecom checkExpiredByHotStoryId(Integer newsRecomId);

    List<NewsRecom> getByLatLng(Map<String, Object> params);
}
