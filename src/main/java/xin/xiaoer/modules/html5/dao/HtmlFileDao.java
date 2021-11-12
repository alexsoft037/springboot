package xin.xiaoer.modules.html5.dao;

import xin.xiaoer.dao.BaseDao;
import xin.xiaoer.modules.html5.entity.HtmlFile;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-08-07 09:50:18
 */
public interface HtmlFileDao extends BaseDao<HtmlFile> {

    List<HtmlFile> getCodeValues(Map<String, Object> map);
}
