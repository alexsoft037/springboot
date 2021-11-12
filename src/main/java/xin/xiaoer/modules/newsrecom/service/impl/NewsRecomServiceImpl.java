package xin.xiaoer.modules.newsrecom.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xin.xiaoer.modules.newsrecom.dao.NewsRecomDao;
import xin.xiaoer.modules.newsrecom.entity.NewsRecom;
import xin.xiaoer.modules.newsrecom.service.NewsRecomService;

import java.util.List;
import java.util.Map;

@Service("newsRecomService")
@Transactional
public class NewsRecomServiceImpl implements NewsRecomService{

    @Autowired
    private NewsRecomDao newsRecomDao;

    @Override
    public NewsRecom get(Integer id) {
        return  newsRecomDao.get(id);
    }

    @Override
    public List<NewsRecom> getList(Map<String, Object> map) {
        return newsRecomDao.getList(map);
    }

    @Override
    public int getCount(Map<String, Object> map) {
        return newsRecomDao.getCount(map);
    }

    @Override
    public void save(NewsRecom newsRecom) {
        newsRecomDao.save(newsRecom);
    }

    @Override
    public void update(NewsRecom newsRecom) {
        newsRecomDao.update(newsRecom);
    }

    @Override
    public void delete(Integer id) {
        newsRecomDao.delete(id);
    }

    @Override
    public void deleteBatch(Integer[] ids) {

    }

    @Override
    public void updateState(String[] ids, String stateValue) {

    }

    @Override
    public List<NewsRecom> getNewsRecomList(Map<String, Object> map) {
        return newsRecomDao.getNewsRecomList(map);
    }

    @Override
    public NewsRecom getDetail(Integer newsRecomId) {
        return null;
    }

    @Override
    public NewsRecom checkExpired(NewsRecom newsrecom) {
        return null;
    }

    @Override
    public NewsRecom checkExpiredByHotStoryId(Integer newsRecomId) {
        return null;
    }

    @Override
    public List<NewsRecom> getByLatLng(Map<String, Object> params) {
        return null;
    }
}
