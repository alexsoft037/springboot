package xin.xiaoer.modules.newcommonweal.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xin.xiaoer.modules.newcommonweal.dao.NewCommonwealDao;
import xin.xiaoer.modules.newcommonweal.entity.NewCommonweal;
import xin.xiaoer.modules.newcommonweal.service.NewCommonwealService;
import xin.xiaoer.modules.newsrecom.entity.NewsRecom;
import xin.xiaoer.modules.newsrecom.service.NewsRecomService;

import java.util.List;
import java.util.Map;

@Service("newConmmonwealService")
@Transactional
public class NewCommonwealServiceImpl implements NewCommonwealService {
    @Autowired
    private NewCommonwealDao newCommonwealDao;

    @Override
    public NewCommonweal get(Integer id) {
        return newCommonwealDao.get(id);
    }

    @Override
    public List<NewCommonweal> getList(Map<String, Object> map) {
        return newCommonwealDao.getList(map);
    }

    @Override
    public int getCount(Map<String, Object> map) {
        return newCommonwealDao.getCount(map);
    }

    @Override
    public void save(NewCommonweal newCommonweal) {
        newCommonwealDao.save(newCommonweal);
    }

    @Override
    public void update(NewCommonweal newCommonweal) {
        newCommonwealDao.update(newCommonweal);
    }

    @Override
    public void delete(Integer id) {
        newCommonwealDao.delete(id);
    }

    @Override
    public void deleteBatch(Integer[] ids) {

    }

    @Override
    public void updateState(String[] ids, String stateValue) {

    }

    @Override
    public List<NewCommonweal> getNewCommonwealList(Map<String, Object> map) {
        return newCommonwealDao.getNewConmonwealList(map);
    }

    @Override
    public NewCommonweal getDetail(Integer newCommonwealId) {
        return null;
    }

    @Override
    public NewCommonweal checkExpired(NewCommonweal newCommonweal) {
        return null;
    }

    @Override
    public NewCommonweal checkExpiredByHotStoryId(Integer newCommonwealId) {
        return null;
    }

    @Override
    public List<NewCommonweal> getByLatLng(Map<String, Object> params) {
        return null;
    }
}
