package xin.xiaoer.modules.classroom.dao;

import org.apache.ibatis.annotations.Param;
import xin.xiaoer.dao.BaseDao;
import xin.xiaoer.modules.classroom.entity.SharingLesson;
import xin.xiaoer.modules.mobile.entity.VideoLessonListItem;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-08-15 16:13:22
 */
public interface SharingLessonDao extends BaseDao<SharingLesson> {

    int removeCategoryCode(@Param("categoryCode") String categoryCode);

    List<VideoLessonListItem> getApiListData(Map<String, Object> map);

    List<VideoLessonListItem> getFeaturedList(Map<String, Object> map);

    int getApiCount(Map<String, Object> map);

    List<VideoLessonListItem> getApiListDataByUser(Map<String, Object> map);

    int getApiCountByUser(Map<String, Object> map);

    VideoLessonListItem getListItemObject(Map search);

    SharingLesson getDetail(Integer lessonId);

    List getshareList(Map<String, Object> map);

    int getshareCount(Map<String, Object> map);
}
