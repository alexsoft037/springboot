package xin.xiaoer.modules.setting.service;

import xin.xiaoer.modules.reporterranking.entity.ReporterRanking;
import xin.xiaoer.modules.setting.entity.Integral;
import xin.xiaoer.modules.setting.entity.IntegralDetail;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-10-04 01:38:29
 */
public interface IntegralService {

	Integral get(Long id);

	List<Integral> getList(Map<String, Object> map);

	int getCount(Map<String, Object> map);

	void save(Integral integral);

	void update(Integral integral);

	void delete(Long id);

	void deleteBatch(Long[] ids);

	void updateState(String[] ids, String stateValue);

	Integral getClickCardIntegral(Long userId, Date createAt);

	Double getUserIntegralTotal(Long userId);

	Double getThisWeekTotal(Map<String, Object> map);

	Double getTodayTotal(Map<String, Object> map);

	Double getTotalByUserAndArticle(Map<String, Object> map);

	Integer getUserRanking(Long userId);

	IntegralDetail getDetail(Long id);

	Integral getByUserAndArticle(Long userId, String articleTypeCode, Long articleId);

	List<Integral> getIntegralListByUser(Map<String, Object> map);

	int getIntegralCountByUser(Map<String, Object> map);

	List<ReporterRanking> getThisWeekRanking(Long userId);

	List<ReporterRanking> getThisMonthRanking(Long userId);

    String getSumIntegral(Long userId);
}
