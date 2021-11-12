package xin.xiaoer.modules.publicbenefit.service;

import xin.xiaoer.common.utils.Query;
import xin.xiaoer.modules.publicbenefit.entity.PublicBenefitActivities;

import java.util.List;
import java.util.Map;

public interface PublicBenefitActivitiesService {
    List<PublicBenefitActivities> getList(Map<String, Object> map);

    int getCount(Query query);
}
