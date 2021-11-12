package xin.xiaoer.modules.spaceofthehall.service;

import xin.xiaoer.modules.spaceofthehall.entity.SpaceOfTheHall;

import java.util.List;
import java.util.Map;

public interface SpaceOfTheHallService {
    List getList(Map<String, Object> params);

    int getCount(Map<String, Object> params);

    void save(SpaceOfTheHall spaceOfTheHall);

    SpaceOfTheHall getById(Integer integer);

    void deleteById(Integer id);

    void update(SpaceOfTheHall spaceOfTheHall);


    void updateStatus(Integer[] ids, int i);
}
