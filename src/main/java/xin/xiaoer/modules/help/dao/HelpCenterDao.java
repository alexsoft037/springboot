package xin.xiaoer.modules.help.dao;

import xin.xiaoer.dao.BaseDao;
import xin.xiaoer.modules.help.entity.HelpCenter;

/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-09-23 17:02:07
 */
public interface HelpCenterDao extends BaseDao<HelpCenter> {

	HelpCenter getByCategory(String category);
}
