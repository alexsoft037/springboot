package xin.xiaoer.service;

import xin.xiaoer.entity.PushMessage;

import java.util.List;

public interface PushMessageService {
    void saveMsg(PushMessage pushMessage, List<Long> ids);
}
