package xin.xiaoer.modules.space.dao;

import xin.xiaoer.common.utils.Query;
import xin.xiaoer.dao.BaseDao;
import xin.xiaoer.entity.CodeValue;
import xin.xiaoer.modules.space.entity.Space;
import xin.xiaoer.modules.website.entity.SpaceRankItem;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-08-02 21:56:04
 */
public interface SpaceDao extends BaseDao<Space> {

    List<Space> getSpaceList(Map<String, Object> map);

    List<Space> getByAddress(Map<String, Object> map);

    List<CodeValue> getCodeValues(Map<String, Object> map);

    String getByCity(String city);

    List<SpaceRankItem> getSpaceRank(Query query);

    List<Map<String,String>> getSpaceRankAtHonorList(Map<String, String> params);
}
