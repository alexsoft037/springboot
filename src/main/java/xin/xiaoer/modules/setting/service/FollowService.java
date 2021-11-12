package xin.xiaoer.modules.setting.service;

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
public interface FollowService {
	
	Follow get(Long id);
	
	List<Follow> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(Follow follow);
	
	void update(Follow follow);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);

    void updateState(String[] ids, String stateValue);

	Follow getByUserIdAndFollowId(Long userId, Long followId);

	int deleteByUserIdAndFollowId(Long userId, Long followId);

	List<Follow> getListByFollowId(Map<String, Object> map);

	List<Follow> getListByUserId(Map<String, Object> map);

	List<String> getAccountListByFollowId(Map<String, Object> map);
}
