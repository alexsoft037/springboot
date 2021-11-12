package xin.xiaoer.modules.publicbenefit.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xin.xiaoer.common.utils.Query;
import xin.xiaoer.modules.publicbenefit.dao.PublicBenefitActivitiesDao;
import xin.xiaoer.modules.publicbenefit.entity.PublicBenefitActivities;
import xin.xiaoer.modules.publicbenefit.service.PublicBenefitActivitiesService;

import java.util.List;
import java.util.Map;

@Service
public class PublicBenefitActivitiesServiceImpl implements PublicBenefitActivitiesService {

    @Autowired
    private PublicBenefitActivitiesDao publicBenefitActivitiesDao;
    @Override
    public List<PublicBenefitActivities> getList(Map map) {
        return publicBenefitActivitiesDao.getList(map);
    }

    @Override
    public int getCount(Query query) {
        return publicBenefitActivitiesDao.getCount(query);
    }
}
