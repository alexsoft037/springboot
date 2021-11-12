package xin.xiaoer.modules.book.service;

import xin.xiaoer.modules.book.entity.Book;
import xin.xiaoer.modules.mobile.entity.BookListItem;
import xin.xiaoer.modules.mobile.entity.PersonalBook;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-07-30 11:10:06
 */
public interface BookService {
	
	Book get(Long bookId);
	
	List<Book> getList(Map<String, Object> map);
	
	int getCount(Map<String, Object> map);
	
	void save(Book book);
	
	void update(Book book);
	
	void delete(Long bookId);
	
	void deleteBatch(Long[] bookIds);

    void updateState(String[] ids, String stateValue);

	List<BookListItem> getRecentList(Map<String, Object> map);

	List<BookListItem> getFeaturedList(Map<String, Object> map);

	List<BookListItem> getListItemForAPI(Map<String, Object> map);

	//新加
	List<BookListItem> getMaxList(Map<String, Object> map);

	int getCountForAPI(Map<String, Object> map);

	int removeCategoryCode(String categoryCode);

	List<PersonalBook> getPersonalList(Map<String, Object> map);

	int getPersonalCount(Map<String, Object> map);

	BookListItem getListItemObject(Map search);

	Book getDetail(Long bookId);
}
