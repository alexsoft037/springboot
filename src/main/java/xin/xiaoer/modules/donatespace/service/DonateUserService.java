package xin.xiaoer.modules.donatespace.service;

import xin.xiaoer.common.utils.Query;
import xin.xiaoer.modules.donatespace.entity.DonateUser;
import xin.xiaoer.modules.donatespace.entity.DonateUserDetail;
import xin.xiaoer.modules.mobile.entity.DonateUserResume;
import xin.xiaoer.modules.website.entity.WebAccount;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author chenyi
 * @email 228112142@qq.com
 * @date 2018-07-20 00:22:00
 */
public interface DonateUserService {
	
	DonateUser get(Long id);
	
	List<DonateUser> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(DonateUser donateUser);
	
	void update(DonateUser donateUser);
	
	void delete(Long id);
	
	void deleteBatch(Long[] ids);

    void updateState(String[] ids, String stateValue);

	DonateUserResume getDonateResumeByItemId(Long itemId);

	List<DonateUser> getListGroupByItemId(Map<String, Object> params);

	Double getTotalDonateAmount(Long userId, String spaceId);

	Integer getTotalItemCount(Long userId, String spaceId);

	DonateUserDetail getDetail(Long id);

	Integer getDonateCount();

    int getAttendDonateCount();

    String getSumAmount(Long userId);

    List<WebAccount> getListAccountByUserId(Query query);
}
