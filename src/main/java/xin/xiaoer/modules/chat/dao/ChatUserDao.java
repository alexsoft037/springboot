package xin.xiaoer.modules.chat.dao;

import org.apache.ibatis.annotations.Param;
import xin.xiaoer.dao.BaseDao;
import xin.xiaoer.modules.chat.entity.ChatUser;

import java.util.List;

/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-09-11 22:35:19
 */
public interface ChatUserDao extends BaseDao<ChatUser> {

    List<ChatUser> getGroupUserList(@Param("spaceId") Integer spaceId);

    List<Long> queryByAccids(@Param("accids") List<String> accids);
}
