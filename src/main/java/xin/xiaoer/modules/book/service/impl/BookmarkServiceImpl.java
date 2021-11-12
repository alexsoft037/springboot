package xin.xiaoer.modules.book.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

import xin.xiaoer.modules.book.dao.BookmarkDao;
import xin.xiaoer.modules.book.entity.Bookmark;
import xin.xiaoer.modules.book.service.BookmarkService;




@Service("bookmarkService")
@Transactional
public class BookmarkServiceImpl implements BookmarkService {
	@Autowired
	private BookmarkDao bookmarkDao;
	
	@Override
	public Bookmark get(Integer id){
		return bookmarkDao.get(id);
	}

	@Override
	public List<Bookmark> getList(Map<String, Object> map){
		return bookmarkDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return bookmarkDao.getCount(map);
	}

	@Override
	public void save(Bookmark bookmark){
		bookmarkDao.save(bookmark);
	}

	@Override
	public void update(Bookmark bookmark){
		bookmarkDao.update(bookmark);
	}

	@Override
	public void delete(Integer id){
		bookmarkDao.delete(id);
	}

	@Override
	public void deleteBatch(Integer[] ids){
		bookmarkDao.deleteBatch(ids);
	}

    @Override
    public void updateState(String[] ids,String stateValue) {
        for (String id:ids){
			Bookmark bookmark=get(Integer.parseInt(id));
            update(bookmark);
        }
    }

    @Override
	public Bookmark getByUserAndBook(Long userId, Long bookId){
		return bookmarkDao.getByUserAndBook(userId, bookId);
	}

	public int deleteByUserAndBook(Long userId, Long bookId){
		return bookmarkDao.deleteByUserAndBook(userId, bookId);
	}
}
