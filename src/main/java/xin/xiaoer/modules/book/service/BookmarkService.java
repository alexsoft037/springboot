package xin.xiaoer.modules.book.service;

import org.apache.ibatis.annotations.Param;
import xin.xiaoer.modules.book.entity.Bookmark;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-08-23 16:24:08
 */
public interface BookmarkService {
	
	Bookmark get(Integer id);
	
	List<Bookmark> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(Bookmark bookmark);
	
	void update(Bookmark bookmark);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);

    void updateState(String[] ids, String stateValue);

	Bookmark getByUserAndBook(Long userId, Long bookId);

	int deleteByUserAndBook(Long userId, Long bookId);
}
