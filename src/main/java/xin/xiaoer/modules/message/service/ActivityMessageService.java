package xin.xiaoer.modules.message.service;


import xin.xiaoer.modules.message.entity.ActivityMessage;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author DaiMingJian
 * @email 3088393266@qq.com
 * @date 2018/12/21
 */
public interface ActivityMessageService {

    void save(ActivityMessage activityMessage);

    void deleteBatch(Integer[] messageId);

    List<ActivityMessage> getList(Map<String, Object> map);

    int getCount(Map<String, Object> map);
}
