package xin.xiaoer.modules.excitingactivity.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xin.xiaoer.modules.excitingactivity.dao.ExcitingActivityDao;
import xin.xiaoer.modules.excitingactivity.entity.ExcitingActivity;
import xin.xiaoer.modules.excitingactivity.service.ExcitingActivityService;

import java.util.List;
import java.util.Map;

@Service("excitingActivityService")
@Transactional
public class ExcitingActivityServiceImpl implements ExcitingActivityService {

    @Autowired
    private ExcitingActivityDao excitingActivityDao;
    @Override
    public ExcitingActivity get(Integer id) {
        return excitingActivityDao.get(id);
    }

    @Override
    public List<ExcitingActivity> getList(Map<String, Object> map) {
        return excitingActivityDao.getList(map);
    }

    @Override
    public int getCount(Map<String, Object> map) {
        return excitingActivityDao.getCount(map);
    }

    @Override
    public void save(ExcitingActivity excitingActivity) {
        excitingActivityDao.save(excitingActivity);
    }

    @Override
    public void update(ExcitingActivity excitingActivity) {
        excitingActivityDao.update(excitingActivity);
    }

    @Override
    public void delete(Integer id) {
        excitingActivityDao.delete(id);
    }

    @Override
    public void deleteBatch(Integer[] ids) {

    }

    @Override
    public void updateState(String[] ids, String stateValue) {
    }

    @Override
    public List<ExcitingActivity> getExcitingActivitylList(Map<String, Object> map) {
        return excitingActivityDao.getExcitingActivityList(map);
    }

    @Override
    public ExcitingActivity getDetail(Integer excitingActivityId) {
        return null;
    }

    @Override
    public ExcitingActivity checkExpired(ExcitingActivity excitingActivity) {
        return null;
    }

    @Override
    public ExcitingActivity checkExpiredByExcitingActicityId(Integer excitingActivityId) {
        return null;
    }

    @Override
    public List<ExcitingActivity> getByLatLng(Map<String, Object> params) {
        return null;
    }
}
