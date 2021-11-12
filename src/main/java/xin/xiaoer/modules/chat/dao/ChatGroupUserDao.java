package xin.xiaoer.modules.chat.dao;

import org.apache.ibatis.annotations.Param;
import xin.xiaoer.dao.BaseDao;
import xin.xiaoer.modules.chat.entity.ChatGroupUser;

/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-09-12 18:28:36
 */
public interface ChatGroupUserDao extends BaseDao<ChatGroupUser> {

    ChatGroupUser getBySpaceAndUser(@Param("userId") Long userId, @Param("spaceId") Integer spaceId);
}
