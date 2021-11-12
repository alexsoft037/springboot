package xin.xiaoer.modules.mobile.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xin.xiaoer.common.integralConfig.IntegralConfig;
import xin.xiaoer.common.integralConfig.IntegralConfigFactory;
import xin.xiaoer.common.oss.CloudStorageService;
import xin.xiaoer.common.oss.OSSFactory;
import xin.xiaoer.common.utils.*;
import xin.xiaoer.entity.Article;
import xin.xiaoer.entity.Category;
import xin.xiaoer.entity.File;
import xin.xiaoer.modules.book.entity.Book;
import xin.xiaoer.modules.book.entity.BookChapter;
import xin.xiaoer.modules.book.entity.Bookmark;
import xin.xiaoer.modules.book.service.BookChapterService;
import xin.xiaoer.modules.book.service.BookService;
import xin.xiaoer.modules.book.service.BookmarkService;
import xin.xiaoer.modules.like.entity.Like;
import xin.xiaoer.modules.like.service.LikeService;
import xin.xiaoer.modules.mobile.bean.ResponseBean;
import xin.xiaoer.modules.mobile.entity.BookListItem;
import xin.xiaoer.modules.review.service.ReviewService;
import xin.xiaoer.modules.setting.entity.Integral;
import xin.xiaoer.modules.setting.service.IntegralService;
import xin.xiaoer.service.CategoryService;
import xin.xiaoer.service.FileService;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("mobile/book")
public class APIBookController {
    @Autowired
    private BookService bookService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private FileService fileService;

    @Autowired
    private BookChapterService bookChapterService;

    @Autowired
    private BookmarkService bookmarkService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private IntegralService integralService;


    //获取所有书籍列表分页
    @RequestMapping("/listData")
    public ResponseBean listData(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        //查询列表数据

//        String requestPageCount = request.getParameter("pageCount");
        String pageCount = params.get("pageCount").toString();
        String curPageNum = params.get("curPageNum").toString();
        //解决书架重复
        if (params.get("categoryCode")==null|| StringUtils.isBlank(params.get("categoryCode").toString())){
            return new ResponseBean(false,ResponseBean.SUCCESS,null,new PageUtils(null, 0, 1, 1));
        }
//        String categoryCode = params.get("categoryCode").toString();
        params.put("limit", pageCount);
        params.put("page", curPageNum);
        params.put("state", "1");
//        params.put("categoryCode", categoryCode);
        Query query = new Query(params);

        CloudStorageService cloudStorageService = OSSFactory.build();

        List<BookListItem> bookListItems = bookService.getListItemForAPI(query);
        for (BookListItem bookListItem : bookListItems) {
            if (!StringUtility.isEmpty(bookListItem.getCover())) {
                bookListItem.setCover(cloudStorageService.generatePresignedUrl(bookListItem.getCover()));
            }
        }
        int total = bookService.getCountForAPI(query);

        PageUtils pageUtil = new PageUtils(bookListItems, total, query.getLimit(), query.getPage());

        return new ResponseBean(false, "success", null, pageUtil);
    }

    //获取书对应目录分页
    @RequestMapping("/chapterListData")
    public ResponseBean chapterListData(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        //查询列表数据

//        String requestPageCount = request.getParameter("pageCount");


        String bookId = params.get("bookId").toString();
        if (params.get("pageCount") != null){
            String pageCount = params.get("pageCount").toString();
            params.put("limit", pageCount);
        }

        if (params.get("curPageNum") != null){
            String curPageNum = params.get("curPageNum").toString();
            params.put("page", curPageNum);
        }

        params.put("state", "1");
        params.put("sidx", "index");
        params.put("order", "asc");
        Book book = bookService.get(Long.parseLong(bookId));
        params.put("bookFile", book.getBookFile());

        List<BookChapter> bookChapters;
        PageUtils pageUtil;
        int total;
        if (params.get("pageCount") != null && params.get("curPageNum") != null){
            Query query = new Query(params);
            bookChapters = bookChapterService.getListData(query);
            total = bookChapterService.getCountData(query);
            pageUtil = new PageUtils(bookChapters, total, query.getLimit(), query.getPage());
        } else {
            bookChapters = bookChapterService.getListData(params);
            total = bookChapterService.getCountData(params);
            pageUtil = new PageUtils(bookChapters, total, 0, 0);
        }

        return new ResponseBean(false, "success", null, pageUtil);
    }

    //记录积分表因读书加的分数
    @RequestMapping("/chapterDetail/{chapterId}/{userId}")
    public ResponseBean chapterDetail(@PathVariable("chapterId") Long chapterId, @PathVariable("userId") Long userId) throws Exception {
        //查询列表数据
        BookChapter bookChapter = bookChapterService.get(chapterId);
        int chapterCount = bookChapterService.getCountByBookFile(bookChapter.getBookFile());
        Book book = bookService.get(bookChapter.getBookId());
        if (book != null && book.getIntegral() != null && StringUtility.isNumeric(book.getIntegral())){
            Double integralValue = Double.parseDouble(book.getIntegral());
            Double chapterIntegral = integralValue/chapterCount;
            IntegralConfig integralConfig = IntegralConfigFactory.build();
            Map<String, Object> typeParams = new HashMap<>();
            typeParams.put("articleTypeCode", Article.BOOK);
            typeParams.put("userId", userId);

            Double weekIntegral = integralService.getThisWeekTotal(typeParams);

            typeParams.put("articleId", chapterId);
            Double chptIntegral = integralService.getTotalByUserAndArticle(typeParams);
            if (chptIntegral == null) {
                Integral integral = new Integral();
                integral.setArticleTypeCode(Article.BOOK);
                integral.setArticleId(chapterId);
                integral.setUserId(userId);
                integral.setTitle("读" + book.getSubject() + bookChapter.getTitle());
                YunXinUtil yunXinUtil = new YunXinUtil();
                if (weekIntegral == null){
                    integral.setIntegral(chapterIntegral.toString());
                    integralService.save(integral);
                    try {
                        yunXinUtil.sendSinglePush(integral.getUserId(), "获得积分"+integral.getTitle());
                    } catch (Exception e){
                    }
                } else if (weekIntegral < integralConfig.getWeekMaxBookIntegral()){
                    if(weekIntegral + chapterIntegral >= integralConfig.getWeekMaxBookIntegral()){
                        chapterIntegral = integralConfig.getWeekMaxBookIntegral() - weekIntegral;
                    }
                    integral.setIntegral(chapterIntegral.toString());
                    integralService.save(integral);
                    try {
                        yunXinUtil.sendSinglePush(integral.getUserId(), "获得积分"+integral.getTitle());
                    } catch (Exception e){
                    }
                }
            }
        }
        return new ResponseBean(false, "success", null, bookChapter);
    }

    //获取当前显示的书籍类别
    @ResponseBody
    @RequestMapping("/getCategories/{upperCode}")
    public R getCodeValues(@PathVariable String upperCode) {
        HashMap<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("sidx", "category_code");
        paraMap.put("order", "asc");
        paraMap.put("upperCode", upperCode);
        CloudStorageService cloudStorageService = OSSFactory.build();
        List<Category> bookCategories = categoryService.getListByUpperCode(paraMap);
        for (Category category : bookCategories){
            if (!StringUtility.isEmpty(category.getCategoryIcon())){
                category.setCategoryIcon(cloudStorageService.generatePresignedUrl(category.getCategoryIcon()));
            }
        }

        return R.ok().put("data", bookCategories);
    }

    @RequestMapping(value = "/readCount/{bookId}", method = RequestMethod.GET)
    public ResponseBean readCount(@PathVariable("bookId") Long bookId) throws Exception {
        Book book = bookService.get(bookId);
        book.setReadCount(book.getReadCount() + 1);
        bookService.update(book);

        return new ResponseBean(false, "success", null, book.getReadCount());
    }

    @RequestMapping(value = "/bookmark/{bookId}/{userId}", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseBean bookmark(@PathVariable("bookId") Long bookId, @PathVariable("userId") Long userId) throws Exception {

        Bookmark bookmark = bookmarkService.getByUserAndBook(userId, bookId);
        boolean isSaved;
        if (bookmark != null){
            bookmarkService.delete(bookmark.getId());
            isSaved = false;
        } else {
            bookmark = new Bookmark();
            bookmark.setBookId(bookId);
            bookmark.setUserId(userId);
            bookmarkService.save(bookmark);
            isSaved = true;
        }

        Map<String, Object> map = new HashMap<>();
        map.put("isSaved", isSaved);
        return new ResponseBean(false, "success", null, map);
    }

    @RequestMapping(value = "/detail/{bookId}/{userId}", method = RequestMethod.GET)
    public ResponseBean detail(@PathVariable("bookId") Long bookId, @PathVariable("userId") Long userId) throws Exception {
        //查询列表数据
        Book book = bookService.get(bookId);
        if (!book.getState().equals("1")) {
            return new ResponseBean(false, ResponseBean.FAILED, "空", null);
        }

        Like like = likeService.getByArticleAndUser(Article.BOOK, bookId, userId);
        if (like == null){
            book.setLikeYn(false);
        } else {
            book.setLikeYn(true);
        }

        Integer reviewCount = reviewService.getCountByCodeAndId(Article.BOOK, bookId);
        book.setReviewCount(reviewCount);
        List<File> coverFiles = fileService.getByRelationId(book.getCover());
        List<File> bookFiles = fileService.getByRelationId(book.getBookFile());

        Bookmark bookmark = bookmarkService.getByUserAndBook(userId, bookId);
        if (bookmark != null){
            book.setBookmarkYn(true);
        } else {
            book.setBookmarkYn(false);
        }
        CloudStorageService cloudStorageService = OSSFactory.build();
        Category category = categoryService.getByCategoryCode(book.getCategoryCode());
        if (category != null){
            book.setCategoryName(category.getCategoryName());
        }

        if (coverFiles.size() > 0) {
            if (!StringUtility.isEmpty(coverFiles.get(0).getUrl())) {
                book.setCover(cloudStorageService.generatePresignedUrl(coverFiles.get(0).getUrl()));
            } else {
                book.setCover("");
            }
        } else {
            book.setCover("");
        }
        int chapters = bookChapterService.getCountByBookFile(book.getBookFile());

        book.setCountChapter(chapters);

        if (chapters > 0){
            book.setHasChapters(true);
        } else {
            book.setHasChapters(false);
        }

        if (bookFiles.size() > 0) {
            if (!StringUtility.isEmpty(bookFiles.get(0).getUrl())) {
                book.setBookFile(cloudStorageService.generatePresignedUrl(bookFiles.get(0).getUrl()));
            } else {
                book.setBookFile("");
            }
        } else {
            book.setBookFile("");
        }

        return new ResponseBean(false, "success", null, book);
    }


    //以weight代表推荐度来选取最高的书籍
    @RequestMapping("/featuredItems")
    public ResponseBean featuredItems(@RequestParam Map<String, Object> params, HttpServletRequest request) {

        if (params.get("limit") == null){
            params.put("limit", 10);
        }
        //parameters limit

        CloudStorageService cloudStorageService = OSSFactory.build();
        List<BookListItem> featuredItems = bookService.getFeaturedList(params);
        for (BookListItem bookListItem : featuredItems) {

            if (!StringUtility.isEmpty(bookListItem.getCover())) {
                bookListItem.setCover(cloudStorageService.generatePresignedUrl(bookListItem.getCover()));
            }
        }

        return new ResponseBean(false, "success", null, featuredItems);
    }

    //以创建书籍时间获取
    @RequestMapping("/recentItems")
    public ResponseBean recentItems(@RequestParam Map<String, Object> params, HttpServletRequest request) {

        if (params.get("limit") == null){
            params.put("limit", 10);
        }
        //parameters limit

        CloudStorageService cloudStorageService = OSSFactory.build();
        List<BookListItem> recentList = bookService.getRecentList(params);
        for (BookListItem bookListItem : recentList) {

            if (!StringUtility.isEmpty(bookListItem.getCover())) {
                bookListItem.setCover(cloudStorageService.generatePresignedUrl(bookListItem.getCover()));
            }
        }

        return new ResponseBean(false, "success", null, recentList);
    }
}
