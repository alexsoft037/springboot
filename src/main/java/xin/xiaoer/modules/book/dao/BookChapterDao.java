package xin.xiaoer.modules.book.dao;

import xin.xiaoer.dao.BaseDao;
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
public interface BookChapterDao extends BaseDao<BookChapter> {

    List<BookChapter> getListByBookFile(Object bookFile);

    List<BookChapter> getListData(Map<String, Object> map);

    int getCountData(Map<String, Object> map);

    int deleteByBookFile(Object bookFile);

    int getCountByBookFile(Object bookFile);
}
