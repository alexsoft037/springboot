package xin.xiaoer.modules.spaceofthehall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xin.xiaoer.modules.spaceofthehall.dao.SpaceOfTheHallDao;
import xin.xiaoer.modules.spaceofthehall.entity.SpaceOfTheHall;
import xin.xiaoer.modules.spaceofthehall.service.SpaceOfTheHallService;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SpaceOfTheHallServiceImpl
        implements SpaceOfTheHallService {

    @Autowired
    private SpaceOfTheHallDao spaceOfTheHallDao;

    @Override
    public List getList(Map<String, Object> params) {
        return spaceOfTheHallDao.getList(params);
    }

    @Override
    public int getCount(Map<String, Object> params) {
        return spaceOfTheHallDao.getCount(params);
    }

    @Override
    public void save(SpaceOfTheHall spaceOfTheHall) {
        spaceOfTheHallDao.save(spaceOfTheHall);
    }

    @Override
    public SpaceOfTheHall getById(Integer integer) {

        return spaceOfTheHallDao.getById(integer);
    }

    @Override
    public void deleteById(Integer id) {
        spaceOfTheHallDao.deleteById(id);
    }

    @Override
    public void update(SpaceOfTheHall spaceOfTheHall) {
        spaceOfTheHallDao.update(spaceOfTheHall);
    }



    @Override
    public void updateStatus(Integer[] ids, int i) {
        for (Integer id : ids) {
            SpaceOfTheHall byId = getById(id);
            byId.setStatus(i);
            update(byId);
        }
    }
}
