package xin.xiaoer.modules.book.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

import xin.xiaoer.modules.book.dao.BookDao;
import xin.xiaoer.modules.book.entity.Book;
import xin.xiaoer.modules.book.service.BookService;
import xin.xiaoer.modules.mobile.entity.BookListItem;
import xin.xiaoer.modules.mobile.entity.PersonalBook;


@Service("bookService")
@Transactional
public class BookServiceImpl implements BookService {
	@Autowired
	private BookDao bookDao;
	
	@Override
	public Book get(Long bookId){
		return bookDao.get(bookId);
	}

	@Override
	public List<Book> getList(Map<String, Object> map){
		return bookDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return bookDao.getCount(map);
	}

	@Override
	public void save(Book book){
		bookDao.save(book);
	}

	@Override
	public void update(Book book){
		bookDao.update(book);
	}

	@Override
	public void delete(Long bookId){
		bookDao.delete(bookId);
	}

	@Override
	public void deleteBatch(Long[] bookIds){
		bookDao.deleteBatch(bookIds);
	}

    @Override
    public void updateState(String[] ids,String stateValue) {
        for (String id:ids){
			Book book=get(Long.parseLong(id));
			book.setState(stateValue);
            update(book);
        }
    }

	@Override
	public List<BookListItem> getRecentList(Map<String, Object> map){
		return bookDao.getRecentList(map);
	}

	@Override
	public List<BookListItem> getFeaturedList(Map<String, Object> map){
		return bookDao.getFeaturedList(map);
	}

	@Override
	public List<BookListItem> getMaxList(Map<String, Object> map){
		return bookDao.getMaxList(map);
	}

	//新加
	@Override
	public List<BookListItem> getListItemForAPI(Map<String, Object> map){
		return bookDao.getListItemForAPI(map);
	}

	@Override
	public int getCountForAPI(Map<String, Object> map){
		return bookDao.getCountForAPI(map);
	}

	@Override
	public int removeCategoryCode(String categoryCode){
		return bookDao.removeCategoryCode(categoryCode);
	}

	@Override
	public List<PersonalBook> getPersonalList(Map<String, Object> map){
		return bookDao.getPersonalList(map);
	}

	@Override
	public int getPersonalCount(Map<String, Object> map){
		return bookDao.getPersonalCount(map);
	}

	@Override
	public BookListItem getListItemObject(Map search){
		return bookDao.getListItemObject(search);
	}

	@Override
	public Book getDetail(Long bookId){
		return bookDao.getDetail(bookId);
	}
}
