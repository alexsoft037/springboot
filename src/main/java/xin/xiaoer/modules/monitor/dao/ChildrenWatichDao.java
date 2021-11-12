package xin.xiaoer.modules.monitor.dao;

import xin.xiaoer.dao.BaseDao;
import xin.xiaoer.modules.monitor.entity.ChildrenWatich;

/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-09-04 11:22:31
 */
public interface ChildrenWatichDao extends BaseDao<ChildrenWatich> {

    ChildrenWatich getByDeviceId(String deviceID);
}
