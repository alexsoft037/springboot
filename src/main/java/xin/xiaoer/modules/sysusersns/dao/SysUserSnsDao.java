package xin.xiaoer.modules.sysusersns.dao;

import org.apache.ibatis.annotations.Param;
import xin.xiaoer.dao.BaseDao;
import xin.xiaoer.modules.sysusersns.entity.SysUserSns;

/**
 * 
 * 
 * @author chenyi
 * @email 228112142@qq.com
 * @date 2018-07-08 17:49:46
 */
public interface SysUserSnsDao extends BaseDao<SysUserSns> {

    SysUserSns getBySns(@Param("snsUserId") String snsUserId, @Param("snsType") String snsType);

    int updateUserId(SysUserSns sysUserSns);

    int deleteBySns(@Param("snsUserId") String snsUserId, @Param("snsType") String snsType);

    SysUserSns getBySnsUserId(@Param("openId") String openId, @Param("snsType") String snsType);
}
