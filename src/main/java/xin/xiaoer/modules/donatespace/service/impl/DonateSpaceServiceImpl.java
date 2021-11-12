package xin.xiaoer.modules.donatespace.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xin.xiaoer.common.utils.Query;
import xin.xiaoer.modules.donatespace.dao.DonateSpaceDao;
import xin.xiaoer.modules.donatespace.dao.DonateSpaceIntroDao;
import xin.xiaoer.modules.donatespace.dao.DonateSpaceProcessDao;
import xin.xiaoer.modules.donatespace.entity.DonateSpace;
import xin.xiaoer.modules.donatespace.service.DonateSpaceService;
import xin.xiaoer.modules.mobile.entity.DSpaceListItem;
import xin.xiaoer.modules.mobile.entity.DonateUserResume;
import xin.xiaoer.modules.mobile.entity.PersonalDonate;

import java.util.List;
import java.util.Map;


@Service("donateSpaceService")
@Transactional
public class DonateSpaceServiceImpl implements DonateSpaceService {
	@Autowired
	private DonateSpaceDao donateSpaceDao;

	@Autowired
	private DonateSpaceIntroDao donateSpaceIntroDao;

	@Autowired
	private DonateSpaceProcessDao donateSpaceProcessDao;
	
	@Override
	public DonateSpace get(Long itemId){
		return donateSpaceDao.get(itemId);
	}

	@Override
	public List<DonateSpace> getList(Map<String, Object> map){
		return donateSpaceDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return donateSpaceDao.getCount(map);
	}

	@Override
	public void save(DonateSpace donateSpace){
		donateSpaceDao.save(donateSpace);
	}

	@Override
	public void update(DonateSpace donateSpace){
		donateSpaceDao.update(donateSpace);
	}

	@Override
	public void delete(Long itemId){
		donateSpaceIntroDao.delete(itemId);
		donateSpaceProcessDao.delete(itemId);
		donateSpaceDao.delete(itemId);
	}

	@Override
	public void deleteBatch(Long[] itemIds){
		donateSpaceIntroDao.deleteBatch(itemIds);
		donateSpaceProcessDao.deleteBatch(itemIds);
		donateSpaceDao.deleteBatch(itemIds);
	}

    @Override
    public void updateState(String[] ids,String stateValue) {
        for (String id:ids){
			DonateSpace donateSpace=get(Long.parseLong(id));
			donateSpace.setState(stateValue);
            update(donateSpace);
        }
    }

	@Override
	public int getCountDSpace(Map<String, Object> params){
		return donateSpaceDao.getCountDSpace(params);
	}

	@Override
	public List<DSpaceListItem> getDSpaceListData(Map<String, Object> params){
		return donateSpaceDao.getDSpaceListData(params);
	}

	@Override
	public List<DSpaceListItem> getFeaturedItemList(String typeCode, String spaceId){
		return donateSpaceDao.getFeaturedItemList(typeCode, spaceId);
	}

	@Override
	public List<PersonalDonate> getPersonalList(Map<String, Object> params){
		return donateSpaceDao.getPersonalList(params);
	}

	@Override
	public int getPersonalCount(Map<String, Object> params){
		return donateSpaceDao.getPersonalCount(params);
	}

	@Override
	public DSpaceListItem getListItemObject(Map<String, Object> params){
		return donateSpaceDao.getListItemObject(params);
	}

	@Override
	public DonateSpace getDetail(Long itemId){
		return donateSpaceDao.getDetail(itemId);
	}

	@Override
	public List<DonateUserResume> gettotal() {
		return donateSpaceDao.gettotal();
	}

	@Override
	public List<DonateUserResume> gettotalbyuser(Integer userId) {
		return donateSpaceDao.gettotalbyuser(userId);
	}

	@Override
	public List<DonateSpace> getNewDonate(Query query) {
		return donateSpaceDao.getNewDonate(query);
	}

	@Override
	public List getreviewList(Map<String, Object> params) {
		return donateSpaceDao.getreviewList(params);
	}

	@Override
	public List getdonatedList(Map<String, Object> params) {
		return donateSpaceDao.getdonatedList(params);
	}

	@Override
	public int getreviewcount(Map<String, Object> map) {
		return donateSpaceDao.getreviewcount(map);
	}

	@Override
	public int getdonatedcount(Map<String, Object> map) {
		return donateSpaceDao.getdonatedcount(map);
	}

	@Override
	public List getdonateintro(Map<String, Object> params) {
		return donateSpaceDao.getdonateintro(params);
	}

	@Override
	public int getdonateintrocount(Map<String, Object> map) {
		return donateSpaceDao.getdonatedcount(map);
	}

	@Override
	public List getdonateprocess(Map<String, Object> params) {
		return donateSpaceDao.getdonateprocess(params);
	}

	@Override
	public List getdonateuser(Map<String, Object> params) {
		return donateSpaceDao.getdonateuser(params);
	}

	@Override
	public List getdonatereviewlist(Map<String, Object> params) {
		return donateSpaceDao.getdonatereviewlist(params);
	}

	@Override
	public List gethonorbyregion(Map<String, Object> params) {
		return donateSpaceDao.gethonorbyregion(params);
	}

	@Override
	public List gethonorbygroup(Map<String, Object> params) {
		return donateSpaceDao.gethonorbygroup(params);
	}

	@Override
	public List gethonorbyvolunteer(Map<String, Object> params) {
		return donateSpaceDao.gethonorbyvolunteer(params);
	}

	@Override
	public List gethonorbysearch(Map<String, Object> params) {
		return donateSpaceDao.gethonorbysearch(params);
	}

	@Override
	public int getsearchCount(Map<String, Object> map) {
		return donateSpaceDao.getsearchCount(map);
	}

	@Override
	public List<Map> getTitles(Query query) {
		return donateSpaceDao.getTitles(query);
	}
}
