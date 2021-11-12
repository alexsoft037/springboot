package xin.xiaoer.modules.website.dao;

import org.apache.ibatis.annotations.Param;
import xin.xiaoer.common.utils.Query;
import xin.xiaoer.dao.BaseDao;
import xin.xiaoer.modules.website.entity.DonateWeb;

import java.util.List;

public interface DonateWebDao extends BaseDao<DonateWeb> {
    List<DonateWeb> getTitles(Query query);

    List<DonateWeb> getHotDonate(Query query);

    List<DonateWeb> getNewDonate(Query query);

    DonateWeb getById(@Param("id") Integer donateId);

    List<DonateWeb> getListAll(Query query);
}
