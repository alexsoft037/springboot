package xin.xiaoer.modules.chat.dao;

import org.apache.ibatis.annotations.Param;
import xin.xiaoer.dao.BaseDao;
import xin.xiaoer.modules.chat.entity.ChatGroup;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-09-12 18:21:56
 */
public interface ChatGroupDao extends BaseDao<ChatGroup> {
	List<ChatGroup> getListByUser(@Param("userId") Long userId);

	List<ChatGroup> getAdminList(Map<String, Object> map);

	int getAdminCount(Map<String, Object> map);
}
