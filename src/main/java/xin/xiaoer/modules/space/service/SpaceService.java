package xin.xiaoer.modules.space.service;

import xin.xiaoer.common.utils.Query;
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
public interface SpaceService {
	
	Space get(Integer spaceId);
	
	List<Space> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(Space space);
	
	void update(Space space);
	
	void delete(Integer spaceId);
	
	void deleteBatch(Integer[] spaceIds);

    void updateState(String[] ids, String stateValue);

	List<Space> getSpaceList(Map<String, Object> map);

	Space getByAddress(Map<String, Object> map);

	List<CodeValue> getCodeValues(Map<String, Object> map);

	String getByCity(String city);

    List<SpaceRankItem> getSpaceRank(Query query);

    List<Map<String,String>> getSpaceRankAtHonorList(Map<String, String> params);
}
