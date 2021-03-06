package xin.xiaoer.modules.book.controller;

import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import org.apache.commons.lang.StringUtils;
import xin.xiaoer.common.enumresource.StateEnum;
import xin.xiaoer.common.exception.MyException;
import xin.xiaoer.common.log.SysLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import xin.xiaoer.common.oss.CloudStorageService;
import xin.xiaoer.common.oss.OSSFactory;
import xin.xiaoer.common.shiro.ShiroUtils;
import xin.xiaoer.common.utils.*;
import xin.xiaoer.entity.Category;
import xin.xiaoer.entity.File;
import xin.xiaoer.entity.SysUser;
import xin.xiaoer.modules.advert.service.XeAdvertService;
import xin.xiaoer.modules.book.entity.Book;
import xin.xiaoer.service.CategoryService;
import xin.xiaoer.modules.book.service.BookChapterService;
import xin.xiaoer.modules.book.service.BookService;
import xin.xiaoer.modules.comment.service.CommentService;
import xin.xiaoer.modules.favourite.service.FavouriteService;
import xin.xiaoer.modules.like.service.LikeService;
import xin.xiaoer.modules.review.service.ReviewService;
import xin.xiaoer.modules.xebanner.service.XeBannerService;
import xin.xiaoer.service.FileService;


/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-07-30 11:10:06
 */
@Controller
@RequestMapping("bookTuShu")
public class BookTuShuController {
	@Autowired
	private BookService bookService;

	@Autowired
    private CategoryService categoryService;

	@Autowired
    private BookChapterService bookChapterService;

    @Autowired
    private FileService fileService;

    @Autowired
    private XeBannerService xeBannerService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private FavouriteService favouriteService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private XeAdvertService xeAdvertService;
	
    /**
     * ??????????????????
     */
    @RequestMapping("/list")
    @RequiresPermissions("book:list")
    public String list() {
        return "book/tushu/list";
    }
    
	/**
	 * ????????????
	 */
    @ResponseBody
	@RequestMapping("/listData")
	@RequiresPermissions("book:list")
	public R listData(@RequestParam Map<String, Object> params){
		//??????????????????
        params.put("categoryCodeVague", "BCT002");
        Query query = new Query(params);

        SysUser admin = ShiroUtils.getUserEntity();
        if (admin.getUserId() != 1L) {
            query.put("spaceId", admin.getSpaceId());
        }

		List<Book> bookList = bookService.getList(query);
        CloudStorageService cloudStorageService = OSSFactory.build();
        for (Book book : bookList){
            book.setCover(cloudStorageService.generatePresignedUrl(book.getCover()));
            book.setBookFile(cloudStorageService.generatePresignedUrl(book.getBookFile()));
        }
		int total = bookService.getCount(query);
		
		PageUtils pageUtil = new PageUtils(bookList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}

    /**
     * ?????????????????????
     **/
    @RequestMapping("/add")
    @RequiresPermissions("book:save")
    public String add(Model model){
        model.addAttribute("admin",ShiroUtils.getUserEntity());
        return "book/tushu/add";
    }

    /**
     *   ?????????????????????
     **/
    @RequestMapping("/edit/{id}")
    @RequiresPermissions("book:update")
    public String edit(Model model, @PathVariable("id") String id){
		Book book = bookService.get(Long.parseLong(id));
        book.setCategoryCode(getUpperCategoryCodes(book.getCategoryCode()));
        model.addAttribute("admin",ShiroUtils.getUserEntity());
        model.addAttribute("model",book);
        return "book/tushu/edit";
    }

    private String getUpperCategoryCodes(String categoryCode){
        Category category = categoryService.getByCategoryCode(categoryCode);
        if (category == null){
            return categoryCode;
        }

        if (category.getUpperCode() == null){
            return categoryCode;
        }

        Category upperCategory = categoryService.getByCategoryCode(category.getUpperCode());
        if (upperCategory == null){
            return categoryCode;
        }

        if (upperCategory.getUpperCode().equals("BCT002")){
            categoryCode = upperCategory.getCategoryCode()+ "," + categoryCode;
            return categoryCode;
        }
        categoryCode = getUpperCategoryCodes(upperCategory.getCategoryCode())+ "," + categoryCode ;
        return categoryCode;
    }

	/**
	 * ??????
	 */
    @ResponseBody
    @RequestMapping("/info/{bookId}")
    @RequiresPermissions("book:info")
    public R info(@PathVariable("bookId") Long bookId){
        Book book = bookService.get(bookId);
        return R.ok().put("book", book);
    }

    /**
	 * ??????
	 */
    @ResponseBody
    @SysLog("??????????????????")
	@RequestMapping("/save")
	@RequiresPermissions("book:save")
	public R save(@RequestBody Map<String, Object> params){
        Gson gson = new Gson();
        JsonElement jsonElement = gson.toJsonTree(params);
        Book book = gson.fromJson(jsonElement, Book.class);

        if (ShiroUtils.getUserId() == 1L){
            String spaceId = params.get("spaceId1").toString();
            if (StringUtility.isEmpty(spaceId) || !StringUtils.isNumeric(spaceId)) {
                throw new MyException("???????????????????????????");
            }
            book.setSpaceId(Integer.parseInt(spaceId));
        } else {
            book.setSpaceId(ShiroUtils.getSpaceId());
        }

        if (book.getCategoryCodes().size() < 1){
            throw new MyException("????????????????????????");
        }

        if (StringUtility.isEmpty(book.getCategoryCodes().get(0).toString())){
            throw new MyException("????????????????????????");
        }

        if (book.getCategoryCodes().size() < 2){
            throw new MyException("????????????????????????");
        }
        String categoryCode = book.getCategoryCodes().get(1).toString();

        if (StringUtility.isEmpty(categoryCode)){
            throw new MyException("????????????????????????");
        }

        List<File> fileList = fileService.getByRelationId(book.getCover());
        if (fileList.size() < 1) {
            throw new MyException("?????????????????????");
        }
        if (fileList.size() > 1) {
            throw new MyException("??????????????????1???");
        }
        List<File> bookFileList = fileService.getByRelationId(book.getBookFile());
        if (bookFileList.size() < 1) {
            throw new MyException("????????????????????????");
        }
        if (bookFileList.size() > 1) {
            throw new MyException("?????????????????????1???");
        }
        book.setCategoryCode(categoryCode);
        book.setUpdateBy(ShiroUtils.getUserId());
        book.setCreateBy(ShiroUtils.getUserId());
		bookService.save(book);
		
		return R.ok();
	}
	
	/**
	 * ??????
	 */
    @ResponseBody
    @SysLog("??????????????????")
	@RequestMapping("/update")
	@RequiresPermissions("book:update")
	public R update(@RequestBody Map<String, Object> params){
        Gson gson = new Gson();
        JsonElement jsonElement = gson.toJsonTree(params);
        Book book = gson.fromJson(jsonElement, Book.class);

        if (ShiroUtils.getUserId() == 1L){
            String spaceId = params.get("spaceId1").toString();
            if (StringUtility.isEmpty(spaceId) || !StringUtils.isNumeric(spaceId)) {
                throw new MyException("???????????????????????????");
            }
            book.setSpaceId(Integer.parseInt(spaceId));
        } else {
            book.setSpaceId(ShiroUtils.getSpaceId());
        }

        if (book.getCategoryCodes().size() < 1){
            throw new MyException("????????????????????????");
        }

        if (StringUtility.isEmpty(book.getCategoryCodes().get(0).toString())){
            throw new MyException("????????????????????????");
        }

        if (book.getCategoryCodes().size() < 2){
            throw new MyException("????????????????????????");
        }
        String categoryCode = book.getCategoryCodes().get(1).toString();

        if (StringUtility.isEmpty(categoryCode)){
            throw new MyException("????????????????????????");
        }

        List<File> fileList = fileService.getByRelationId(book.getCover());
        if (fileList.size() < 1) {
            throw new MyException("?????????????????????");
        }
        if (fileList.size() > 1) {
            throw new MyException("??????????????????1???");
        }
        List<File> bookFileList = fileService.getByRelationId(book.getBookFile());
        if (bookFileList.size() < 1) {
            throw new MyException("????????????????????????");
        }
        if (bookFileList.size() > 1) {
            throw new MyException("?????????????????????1???");
        }

        book.setCategoryCode(categoryCode);
        book.setUpdateBy(ShiroUtils.getUserId());
		bookService.update(book);
		
		return R.ok();
	}

    /**
     * ??????
     */
    @ResponseBody
    @SysLog("??????????????????")
    @RequestMapping("/enable")
    @RequiresPermissions("book:update")
    public R enable(@RequestBody String[] ids){
        String stateValue=StateEnum.ENABLE.getCode();
		bookService.updateState(ids,stateValue);
        return R.ok();
    }
    /**
     * ??????
     */
    @ResponseBody
    @SysLog("??????????????????")
    @RequestMapping("/limit")
    @RequiresPermissions("book:update")
    public R limit(@RequestBody String[] ids){
        String stateValue=StateEnum.LIMIT.getCode();
		bookService.updateState(ids,stateValue);
        return R.ok();
    }
	
	/**
	 * ??????
	 */
    @ResponseBody
    @SysLog("??????????????????")
	@RequestMapping("/delete")
	@RequiresPermissions("book:delete")
	public R delete(@RequestBody Long[] bookIds){

        for (Long bookId: bookIds){
            Book book = bookService.get(bookId);
            if (book !=null){
                fileService.deleteByRelationId(book.getCover());
                fileService.deleteByRelationId(book.getBookFile());
                bookChapterService.deleteByBookFile(book.getBookFile());
            }
        }

		bookService.deleteBatch(bookIds);
		
		return R.ok();
	}
	
}
