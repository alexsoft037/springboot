package xin.xiaoer.modules.setting.dao;

import org.apache.ibatis.annotations.Param;
import xin.xiaoer.dao.BaseDao;
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
public interface IntegralDao extends BaseDao<Integral> {

    Integral getClickCardIntegral(@Param("userId") Long userId, @Param("createAt") Date createAt);

    Double getUserIntegralTotal(Long userId);

    Double getThisWeekTotal(Map<String, Object> map);

    Double getTodayTotal(Map<String, Object> map);

    Double getTotalByUserAndArticle(Map<String, Object> map);

    Integer getUserRanking(@Param("userId") Long userId);

    Integral getByUserAndArticle(@Param("userId") Long userId, @Param("articleTypeCode") String articleTypeCode, @Param("articleId") Long articleId);

    IntegralDetail getDetail(Long id);

    List<Integral> getIntegralListByUser(Map<String, Object> map);

    int getIntegralCountByUser(Map<String, Object> map);

    List<ReporterRanking> getThisWeekRanking(Long userId);

    List<ReporterRanking> getThisMonthRanking(Long userId);

    String getSumIntegral(@Param("userId") Long userId);
}
