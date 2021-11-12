package xin.xiaoer.modules.donatespace.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xin.xiaoer.common.utils.Query;
import xin.xiaoer.modules.donatespace.dao.DonateUserDao;
import xin.xiaoer.modules.donatespace.entity.DonateUser;
import xin.xiaoer.modules.donatespace.entity.DonateUserDetail;
import xin.xiaoer.modules.donatespace.service.DonateUserService;
import xin.xiaoer.modules.mobile.entity.DonateUserResume;
import xin.xiaoer.modules.website.entity.WebAccount;

import java.util.List;
import java.util.Map;


@Service("donateUserService")
@Transactional
public class DonateUserServiceImpl implements DonateUserService {
	@Autowired
	private DonateUserDao donateUserDao;
	
	@Override
	public DonateUser get(Long id){
		return donateUserDao.get(id);
	}

	@Override
	public List<DonateUser> getList(Map<String, Object> map){
		return donateUserDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return donateUserDao.getCount(map);
	}

	@Override
	public void save(DonateUser donateUser){
		donateUserDao.save(donateUser);
	}

	@Override
	public void update(DonateUser donateUser){
		donateUserDao.update(donateUser);
	}

	@Override
	public void delete(Long id){
		donateUserDao.delete(id);
	}

	@Override
	public void deleteBatch(Long[] ids){
		donateUserDao.deleteBatch(ids);
	}

    @Override
    public void updateState(String[] ids,String stateValue) {
        for (String id:ids){
			DonateUser donateUser=get(Long.parseLong(id));
//			donateUser.setState(stateValue);
            update(donateUser);
        }
    }

	@Override
	public DonateUserResume getDonateResumeByItemId(Long itemId){
		return donateUserDao.getDonateResumeByItemId(itemId);
	}

	@Override
	public List<DonateUser> getListGroupByItemId(Map<String, Object> params){
		return donateUserDao.getListGroupByItemId(params);
	}

	@Override
	public Double getTotalDonateAmount(Long userId, String spaceId){
		return donateUserDao.getTotalDonateAmount(userId, spaceId);
	}

	@Override
	public Integer getTotalItemCount(Long userId, String spaceId){
		return donateUserDao.getTotalItemCount(userId, spaceId);
	}

	@Override
	public DonateUserDetail getDetail(Long id){
		return donateUserDao.getDetail(id);
	}

	@Override
	public Integer getDonateCount() {
		return donateUserDao.getDonateCount();
	}

	@Override
	public int getAttendDonateCount() {
		return donateUserDao.getAttendDonateCount();//全国参与公益人数
	}

	@Override
	public List<WebAccount> getListAccountByUserId(Query query) {
		return donateUserDao.getListAccountByUserId(query);
	}

	@Override
	public String getSumAmount(Long userId) {
		return donateUserDao.getSumAmount(userId);//累计支出
	}
}
