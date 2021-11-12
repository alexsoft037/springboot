package xin.xiaoer.modules.book.service;

import xin.xiaoer.modules.book.entity.BookChapter;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-08-09 10:26:24
 */
public interface BookChapterService {
	
	BookChapter get(Long chapterId);
	
	List<BookChapter> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(BookChapter bookChapter);
	
	void update(BookChapter bookChapter);
	
	void delete(Long chapterId);
	
	void deleteBatch(Long[] chapterIds);

    void updateState(String[] ids, String stateValue);

	List<BookChapter> getListByBookFile(Object bookFile);

	int deleteByBookFile(Object bookFile);

	int getCountByBookFile(Object bookFile);

	List<BookChapter> getListData(Map<String, Object> map);

	int getCountData(Map<String, Object> map);
}
