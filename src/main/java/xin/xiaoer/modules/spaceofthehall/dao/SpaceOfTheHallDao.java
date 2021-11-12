package xin.xiaoer.modules.spaceofthehall.dao;

import xin.xiaoer.dao.BaseDao;
import xin.xiaoer.modules.spaceofthehall.entity.SpaceOfTheHall;

public interface SpaceOfTheHallDao extends BaseDao<SpaceOfTheHall> {
    SpaceOfTheHall getById(Integer integer);

    void deleteById(Integer id);
}
