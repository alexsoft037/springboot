package xin.xiaoer.modules.website.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xin.xiaoer.common.utils.Query;
import xin.xiaoer.modules.website.dao.DonateWebDao;
import xin.xiaoer.modules.website.entity.DonateWeb;
import xin.xiaoer.modules.website.service.DonateWebService;

import java.util.List;

@Service
@Transactional
public class DonateWebServiceImpl implements DonateWebService {
    @Autowired
    private DonateWebDao donateWebDao;
    @Override
    public List<DonateWeb> getTitles(Query query) {
        return donateWebDao.getTitles(query);
    }

    @Override
    public int getCount(Query query) {
        return donateWebDao.getCount(query);
    }

    @Override
    public List<DonateWeb> getHotDonate(Query query) {
        return donateWebDao.getHotDonate(query);
    }

    @Override
    public List<DonateWeb> getNewDonate(Query query) {
        return donateWebDao.getNewDonate(query);
    }

    @Override
    public DonateWeb getById(Integer donateId) {
        return donateWebDao.getById(donateId);
    }

    @Override
    public List<DonateWeb> getListAll(Query query) {
        return donateWebDao.getListAll(query);
    }

    @Override
    public void save(DonateWeb donateWeb) {
        donateWebDao.save(donateWeb);
    }

    @Override
    public void deleteBatch(Integer[] donateWebIds) {
        donateWebDao.deleteBatch(donateWebIds);
    }

    @Override
    public void updateState(String[] ids, String stateValue) {
        for (String id : ids) {
            DonateWeb donateWeb = getById(Integer.parseInt(id));
            donateWeb.setState(Integer.parseInt(stateValue));
            update(donateWeb);
        }
    }

    @Override
    public void update(DonateWeb donateWeb) {
        donateWebDao.update(donateWeb);
    }

    @Override
    public List<DonateWeb> getList(Query query) {
        return donateWebDao.getList(query);
    }
}
