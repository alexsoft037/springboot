package xin.xiaoer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xin.xiaoer.dao.PushMessageDao;
import xin.xiaoer.entity.PushMessage;
import xin.xiaoer.service.PushMessageService;

import java.util.List;

/**
 * @ClassName PushMessageServiceImpl
 * @Description TODO
 * @Author XiaoDong
 **/
@Service("pushMessageService")
@Transactional
public class PushMessageServiceImpl implements PushMessageService {

    @Autowired
    private PushMessageDao pushMessageDao;
    @Override
    public void saveMsg(PushMessage pushMessage, List<Long> ids) {
        pushMessageDao.saveAndGetId(pushMessage);
        pushMessageDao.saveTo(pushMessage.getMsgId(),ids);
    }
}
