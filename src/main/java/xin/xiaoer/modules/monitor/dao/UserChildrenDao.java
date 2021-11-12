package xin.xiaoer.modules.monitor.dao;

import xin.xiaoer.dao.BaseDao;
import xin.xiaoer.entity.CodeValue;
import xin.xiaoer.modules.monitor.entity.UserChildren;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-09-03 12:51:23
 */
public interface UserChildrenDao extends BaseDao<UserChildren> {

    List<CodeValue> getCodeValues(Map<String, Object> map);
}
