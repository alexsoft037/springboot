package xin.xiaoer.modules.message.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xin.xiaoer.common.utils.DxtonSMSSend;
import xin.xiaoer.modules.message.dao.ActivityMessageDao;
import xin.xiaoer.modules.message.entity.ActivityMessage;
import xin.xiaoer.modules.message.service.ActivityMessageService;
import xin.xiaoer.modules.mobile.bean.ResponseBean;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author DaiMingJian
 * @email 3088393266@qq.com
 * @date 2018/12/21
 */

@Service("activityMessageService")
@Transactional
public class ActivityMessageServiceImpl implements ActivityMessageService{
    @Autowired
    private ActivityMessageDao activityMessageDao;

    @Override
    public void save(ActivityMessage activityMessage){
        activityMessageDao.save(activityMessage);
    }

    @Override
    public void deleteBatch(Integer[] messageId){
        activityMessageDao.deleteBatch(messageId);
    }

    @Override
    public List<ActivityMessage> getList(Map<String, Object> map){
        return activityMessageDao.getList(map);
    }

    @Override
    public int getCount(Map<String, Object> map){
        return activityMessageDao.getCount(map);
    }
}
