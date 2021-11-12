package xin.xiaoer.modules.reporterranking.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

import xin.xiaoer.modules.reporterranking.dao.ReporterRankingDao;
import xin.xiaoer.modules.reporterranking.entity.ReporterRanking;
import xin.xiaoer.modules.reporterranking.service.ReporterRankingService;




@Service("reporterRankingService")
@Transactional
public class ReporterRankingServiceImpl implements ReporterRankingService {
	@Autowired
	private ReporterRankingDao reporterRankingDao;
	
	@Override
	public ReporterRanking get(Long userId){
		return reporterRankingDao.get(userId);
	}

	@Override
	public List<ReporterRanking> getList(Map<String, Object> map){
		return reporterRankingDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return reporterRankingDao.getCount(map);
	}

	@Override
	public void save(ReporterRanking reporterRanking){
		reporterRankingDao.save(reporterRanking);
	}

	@Override
	public void update(ReporterRanking reporterRanking){
		reporterRankingDao.update(reporterRanking);
	}

	@Override
	public void delete(Long userId){
		reporterRankingDao.delete(userId);
	}

	@Override
	public void deleteBatch(Long[] userIds){
		reporterRankingDao.deleteBatch(userIds);
	}

    @Override
    public void updateState(String[] ids,String stateValue) {
        for (String id:ids){
			ReporterRanking reporterRanking=get(Long.parseLong(id));
//			reporterRanking.setState(stateValue);
            update(reporterRanking);
        }
    }

	@Override
	public List<ReporterRanking> getThisWeekRanking(Long userId){
		return reporterRankingDao.getThisWeekRanking(userId);
	}

	@Override
	public List<ReporterRanking> getThisMonthRanking(Long userId){
		return reporterRankingDao.getThisMonthRanking(userId);
	}
}
