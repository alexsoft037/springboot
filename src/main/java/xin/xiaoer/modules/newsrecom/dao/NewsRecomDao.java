package xin.xiaoer.modules.newsrecom.dao;

import org.apache.ibatis.annotations.Param;
import xin.xiaoer.dao.BaseDao;
import xin.xiaoer.modules.newsrecom.entity.NewsRecom;

import java.util.List;
import java.util.Map;

public interface NewsRecomDao extends BaseDao<NewsRecom> {
    List<NewsRecom> getNewsRecomList(Map<String, Object> map);

    Integer countListData(Map<String, Object> search);

    int getPersonalCount(Map<String, Object> params);

    NewsRecom getByAddress(@Param("address") String address, @Param("useId") Long useId);

    NewsRecom getDetail(Integer activityId);
}
