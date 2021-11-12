package xin.xiaoer.modules.book.dao;

import org.apache.ibatis.annotations.Param;
import xin.xiaoer.dao.BaseDao;
import xin.xiaoer.modules.book.entity.Bookmark;

/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-08-23 16:24:08
 */
public interface BookmarkDao extends BaseDao<Bookmark> {

    Bookmark getByUserAndBook(@Param("userId") Long userId, @Param("bookId") Long bookId);

    int deleteByUserAndBook(@Param("userId") Long userId, @Param("bookId") Long bookId);
}
