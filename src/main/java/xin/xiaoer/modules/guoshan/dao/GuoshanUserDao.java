package xin.xiaoer.modules.guoshan.dao;

import xin.xiaoer.dao.BaseDao;
import xin.xiaoer.modules.guoshan.entity.GuoshanUser;

import java.util.Map;

/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-08-03 12:44:53
 */
public interface GuoshanUserDao extends BaseDao<GuoshanUser> {
    GuoshanUser getByVolunteerId(String volunteerId);

    int getVolCount();
}
