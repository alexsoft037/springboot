package xin.xiaoer.modules.donatespace.service;

import xin.xiaoer.common.utils.Query;
import xin.xiaoer.modules.donatespace.entity.DonateSpace;
import xin.xiaoer.modules.mobile.entity.DSpaceListItem;
import xin.xiaoer.modules.mobile.entity.DonateUserResume;
import xin.xiaoer.modules.mobile.entity.PersonalDonate;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author chenyi
 * @email 228112142@qq.com
 * @date 2018-07-20 00:07:30
 */
public interface DonateSpaceService {
	
	DonateSpace get(Long itemId);
	
	List<DonateSpace> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(DonateSpace donateSpace);
	
	void update(DonateSpace donateSpace);
	
	void delete(Long itemId);
	
	void deleteBatch(Long[] itemIds);

    void updateState(String[] ids, String stateValue);

	int getCountDSpace(Map<String, Object> params);

	List<DSpaceListItem> getDSpaceListData(Map<String, Object> params);

	List<DSpaceListItem> getFeaturedItemList(String typeCode, String spaceId);

	List<PersonalDonate> getPersonalList(Map<String, Object> params);

	int getPersonalCount(Map<String, Object> params);

	DSpaceListItem getListItemObject(Map<String, Object> params);

	DonateSpace getDetail(Long itemId);

	List<DonateUserResume> gettotal();

	List<DonateUserResume> gettotalbyuser(Integer userId);

	List<DonateSpace> getNewDonate(Query query);

	List getreviewList(Map<String, Object> params);

	int getreviewcount(Map<String, Object> map);

	List getdonatedList(Map<String, Object> params);

	int getdonatedcount(Map<String, Object> map);

	List getdonateintro(Map<String, Object> params);

	int getdonateintrocount(Map<String, Object> map);

	List getdonateprocess(Map<String, Object> params);

	List getdonateuser(Map<String, Object> params);

	List getdonatereviewlist(Map<String, Object> params);

	List gethonorbyregion(Map<String, Object> params);

	List gethonorbygroup(Map<String, Object> params);

	List gethonorbyvolunteer(Map<String, Object> params);

	List gethonorbysearch(Map<String, Object> params);

	int getsearchCount(Map<String, Object> map);


	List<Map> getTitles(Query query);
}
