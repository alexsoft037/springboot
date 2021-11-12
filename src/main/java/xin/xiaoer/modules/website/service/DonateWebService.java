package xin.xiaoer.modules.website.service;

import xin.xiaoer.common.utils.Query;
import xin.xiaoer.modules.website.entity.DonateWeb;

import java.util.List;

public interface DonateWebService {
    List<DonateWeb> getTitles(Query query);

    int getCount(Query query);

    List<DonateWeb> getHotDonate(Query query);

    List<DonateWeb> getNewDonate(Query query);

    DonateWeb getById(Integer donateId);

    List<DonateWeb> getListAll(Query query);

    void save(DonateWeb donateWeb);

    void deleteBatch(Integer[] donateWebIds);

    void updateState(String[] ids, String stateValue);

    void update(DonateWeb donateWeb);

    List<DonateWeb> getList(Query query);
}
