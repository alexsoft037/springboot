package xin.xiaoer.modules.reporterranking.service;

import xin.xiaoer.modules.reporterranking.entity.ReporterRanking;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-07-25 12:04:25
 */
public interface ReporterRankingService {
	
	ReporterRanking get(Long userId);
	
	List<ReporterRanking> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(ReporterRanking reporterRanking);
	
	void update(ReporterRanking reporterRanking);
	
	void delete(Long userId);
	
	void deleteBatch(Long[] userIds);

    void updateState(String[] ids, String stateValue);

	List<ReporterRanking> getThisWeekRanking(Long userId);

	List<ReporterRanking> getThisMonthRanking(Long userId);
}
