package xin.xiaoer.modules.book.dao;

import org.apache.ibatis.annotations.Param;
import xin.xiaoer.dao.BaseDao;
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
public interface BookDao extends BaseDao<Book> {

    List<BookListItem> getRecentList(Map<String, Object> map);

    List<BookListItem> getFeaturedList(Map<String, Object> map);

    List<BookListItem> getListItemForAPI(Map<String, Object> map);

    //新加
    List<BookListItem> getMaxList(Map<String, Object> map);

    int getCountForAPI(Map<String, Object> map);

    int removeCategoryCode(@Param("categoryCode") String categoryCode);

    List<PersonalBook> getPersonalList(Map<String, Object> map);

    int getPersonalCount(Map<String, Object> map);

    BookListItem getListItemObject(Map search);

    Book getDetail(Long bookId);
}
