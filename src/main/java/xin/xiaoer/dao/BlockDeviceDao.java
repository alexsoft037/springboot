package xin.xiaoer.dao;

import org.apache.ibatis.annotations.Param;
import xin.xiaoer.entity.BlockDevice;

/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-10-29 10:39:46
 */
public interface BlockDeviceDao extends BaseDao<BlockDevice> {

    BlockDevice getByUserAndDevice(@Param("userId") Long userId, @Param("deviceId") String deviceId);

    int deleteByUserAndDevice(@Param("userId") Long userId, @Param("deviceId") String deviceId);
}
