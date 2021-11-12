package xin.xiaoer.modules.reporterranking.dao;

import xin.xiaoer.dao.BaseDao;
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
public interface ReporterRankingDao extends BaseDao<ReporterRanking> {

    List<ReporterRanking> getThisWeekRanking(Long userId);

    List<ReporterRanking> getThisMonthRanking(Long userId);
}
