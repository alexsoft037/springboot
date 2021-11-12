package xin.xiaoer.dao;

import org.apache.ibatis.annotations.Param;
import xin.xiaoer.entity.PushMessage;

import java.util.List;

public interface PushMessageDao extends BaseDao<PushMessage> {
    int saveAndGetId(PushMessage pushMessage);

    void saveTo(@Param("msgId") int msgId, @Param("ids") List<Long> ids);
}
