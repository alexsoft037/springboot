package xin.xiaoer.modules.monitor.dao;

import xin.xiaoer.dao.BaseDao;
import xin.xiaoer.modules.monitor.entity.CameraCapture;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-09-06 13:29:33
 */
public interface CameraCaptureDao extends BaseDao<CameraCapture> {

    List<String> getDateList(Map<String, Object> map);

    List<CameraCapture> getCaptureList(Map<String, Object> map);
}
