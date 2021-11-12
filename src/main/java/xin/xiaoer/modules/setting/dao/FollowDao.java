package xin.xiaoer.modules.setting.dao;

import org.apache.ibatis.annotations.Param;
import xin.xiaoer.dao.BaseDao;
import xin.xiaoer.modules.setting.entity.Follow;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-10-02 12:05:02
 */
public interface FollowDao extends BaseDao<Follow> {

    Follow getByUserIdAndFollowId(@Param("userId") Long userId, @Param("followId") Long followId);

    int deleteByUserIdAndFollowId(@Param("userId") Long userId, @Param("followId") Long followId);

    List<Follow> getListByFollowId(Map<String, Object> map);

    List<Follow> getListByUserId(Map<String, Object> map);

    List<String> getAccountListByFollowId(Map<String, Object> map);
}
