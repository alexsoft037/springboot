package xin.xiaoer.modules.monitor.dao;

import xin.xiaoer.dao.BaseDao;
import xin.xiaoer.modules.mobile.entity.LostNotice;
import xin.xiaoer.modules.monitor.entity.ChildrenLost;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-09-03 12:51:23
 */
public interface ChildrenLostDao extends BaseDao<ChildrenLost> {

    List<LostNotice> getNoticeList(Map<String, Object> map);
}
