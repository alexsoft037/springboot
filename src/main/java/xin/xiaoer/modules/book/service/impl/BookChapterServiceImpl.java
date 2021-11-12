package xin.xiaoer.modules.book.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Map;

import xin.xiaoer.modules.book.dao.BookChapterDao;
import xin.xiaoer.modules.book.entity.BookChapter;
import xin.xiaoer.modules.book.service.BookChapterService;




@Service("bookChapterService")
@Transactional
public class BookChapterServiceImpl implements BookChapterService {
	@Autowired
	private BookChapterDao bookChapterDao;
	
	@Override
	public BookChapter get(Long chapterId){
		return bookChapterDao.get(chapterId);
	}

	@Override
	public List<BookChapter> getList(Map<String, Object> map){
		return bookChapterDao.getList(map);
	}

	@Override
	public int getCount(Map<String, Object> map){
		return bookChapterDao.getCount(map);
	}

	@Override
	public void save(BookChapter bookChapter){
		bookChapterDao.save(bookChapter);
	}

	@Override
	public void update(BookChapter bookChapter){
		bookChapterDao.update(bookChapter);
	}

	@Override
	public void delete(Long chapterId){
		bookChapterDao.delete(chapterId);
	}

	@Override
	public void deleteBatch(Long[] chapterIds){
		bookChapterDao.deleteBatch(chapterIds);
	}

    @Override
    public void updateState(String[] ids,String stateValue) {
        for (String id:ids){
			BookChapter bookChapter=get(Long.parseLong(id));
//			bookChapter.setState(stateValue);
            update(bookChapter);
        }
    }

    @Override
	public  List<BookChapter> getListByBookFile(Object bookFile){
		return  bookChapterDao.getListByBookFile(bookFile);
	}

	@Override
	public int deleteByBookFile(Object bookFile){
		return bookChapterDao.deleteByBookFile(bookFile);
	}

	@Override
	public int getCountByBookFile(Object bookFile){
		return bookChapterDao.getCountByBookFile(bookFile);
	}

	@Override
	public List<BookChapter> getListData(Map<String, Object> map){
		return bookChapterDao.getListData(map);
	}

	@Override
	public int getCountData(Map<String, Object> map){
		return bookChapterDao.getCountData(map);
	}
}
