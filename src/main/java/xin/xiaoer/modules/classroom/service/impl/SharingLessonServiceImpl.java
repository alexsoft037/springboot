package xin.xiaoer.modules.classroom.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

import xin.xiaoer.modules.classroom.dao.SharingLessonDao;
import xin.xiaoer.modules.classroom.entity.SharingLesson;
import xin.xiaoer.modules.classroom.service.SharingLessonService;
import xin.xiaoer.modules.mobile.entity.VideoLessonListItem;


@Service("sharingLessonService")
@Transactional
public class SharingLessonServiceImpl implements SharingLessonService {
	@Autowired
	private SharingLessonDao sharingLessonDao;
	
	@Override
	public SharingLesson get(Integer lessonId){
		return sharingLessonDao.get(lessonId);
	}

	@Override
	public List<SharingLesson> getList(Map<String, Object> map){
		return sharingLessonDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return sharingLessonDao.getCount(map);
	}

	@Override
	public void save(SharingLesson sharingLesson){
		sharingLessonDao.save(sharingLesson);
	}

	@Override
	public void update(SharingLesson sharingLesson){
		sharingLessonDao.update(sharingLesson);
	}

	@Override
	public void delete(Integer lessonId){
		sharingLessonDao.delete(lessonId);
	}

	@Override
	public void deleteBatch(Integer[] lessonIds){
		sharingLessonDao.deleteBatch(lessonIds);
	}

    @Override
    public void updateState(String[] ids,String stateValue) {
        for (String id:ids){
			SharingLesson sharingLesson=get(Integer.parseInt(id));
			sharingLesson.setState(stateValue);
            update(sharingLesson);
        }
    }

    @Override
	public int removeCategoryCode(String categoryCode){
		return sharingLessonDao.removeCategoryCode(categoryCode);
	}

	@Override
	public List<VideoLessonListItem> getApiListData(Map<String, Object> map){
		return sharingLessonDao.getApiListData(map);
	}

	@Override
	public int getApiCount(Map<String, Object> map){
		return sharingLessonDao.getApiCount(map);
	}

	@Override
	public List<VideoLessonListItem> getFeaturedList(Map<String, Object> map){
		return sharingLessonDao.getFeaturedList(map);
	}

	@Override
	public List<VideoLessonListItem> getApiListDataByUser(Map<String, Object> map){
		return sharingLessonDao.getApiListDataByUser(map);
	}

	@Override
	public int getApiCountByUser(Map<String, Object> map){
		return sharingLessonDao.getApiCountByUser(map);
	}

	@Override
	public VideoLessonListItem getListItemObject(Map search){
		return sharingLessonDao.getListItemObject(search);
	}

	@Override
	public SharingLesson getDetail(Integer lessonId) {
		return sharingLessonDao.getDetail(lessonId);
	}

	@Override
	public List getshareList(Map<String, Object> map) {
		return sharingLessonDao.getshareList(map);
	}

	@Override
	public int getshareCount(Map<String, Object> map) {
		return sharingLessonDao.getshareCount(map);
	}
}
