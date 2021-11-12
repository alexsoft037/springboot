package xin.xiaoer.modules.setting.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xin.xiaoer.modules.reporterranking.entity.ReporterRanking;
import xin.xiaoer.modules.setting.dao.IntegralDao;
import xin.xiaoer.modules.setting.entity.Integral;
import xin.xiaoer.modules.setting.entity.IntegralDetail;
import xin.xiaoer.modules.setting.service.IntegralService;

import java.util.Date;
import java.util.List;
import java.util.Map;




@Service("integralService")
@Transactional
public class IntegralServiceImpl implements IntegralService {
	@Autowired
	private IntegralDao integralDao;


	@Override
	public Integral get(Long id){
		return integralDao.get(id);
	}

	@Override
	public List<Integral> getList(Map<String, Object> map){
		return integralDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return integralDao.getCount(map);
	}

	@Override
	public void save(Integral integral){
		integralDao.save(integral);
	}

	@Override
	public void update(Integral integral){
		integralDao.update(integral);
	}

	@Override
	public void delete(Long id){
		integralDao.delete(id);
	}

	@Override
	public void deleteBatch(Long[] ids){
		integralDao.deleteBatch(ids);
	}

	@Override
	public void updateState(String[] ids,String stateValue) {
		for (String id:ids){
			Integral integral=get(Long.parseLong(id));
//			integral.setState(stateValue);
			update(integral);
		}
	}

	@Override
	public Integral getClickCardIntegral(Long userId, Date createAt){
		return integralDao.getClickCardIntegral(userId, createAt);
	}

	@Override
	public Double getUserIntegralTotal(Long userId){
		return integralDao.getUserIntegralTotal(userId);
	}

	@Override
	public Double getThisWeekTotal(Map<String, Object> map){
		return integralDao.getThisWeekTotal(map);
	}

	@Override
	public Double getTodayTotal(Map<String, Object> map){
		return integralDao.getTodayTotal(map);
	}

	@Override
	public Double getTotalByUserAndArticle(Map<String, Object> map){
		return integralDao.getTotalByUserAndArticle(map);
	}

	@Override
	public Integer getUserRanking(Long userId){
		return integralDao.getUserRanking(userId);
	}

	@Override
	public IntegralDetail getDetail(Long id){
		return integralDao.getDetail(id);
	}

	@Override
	public Integral getByUserAndArticle(Long userId, String articleTypeCode, Long articleId){
		return integralDao.getByUserAndArticle(userId, articleTypeCode, articleId);
	}

	@Override
	public List<Integral> getIntegralListByUser(Map<String, Object> map){
		return integralDao.getIntegralListByUser(map);
	}

	@Override
	public int getIntegralCountByUser(Map<String, Object> map){
		return integralDao.getIntegralCountByUser(map);
	}

	@Override
	public List<ReporterRanking> getThisWeekRanking(Long userId){
		return integralDao.getThisWeekRanking(userId);
	}

	@Override
	public List<ReporterRanking> getThisMonthRanking(Long userId){
		return integralDao.getThisMonthRanking(userId);
	}

	@Override
	public String getSumIntegral(Long userId) {
		return integralDao.getSumIntegral(userId);
	}
}
