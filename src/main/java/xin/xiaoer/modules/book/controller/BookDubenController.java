package xin.xiaoer.modules.book.controller;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import xin.xiaoer.common.enumresource.StateEnum;
import xin.xiaoer.common.exception.MyException;
import xin.xiaoer.common.log.SysLog;
import xin.xiaoer.common.oss.CloudStorageService;
import xin.xiaoer.common.oss.OSSFactory;
import xin.xiaoer.common.shiro.ShiroUtils;
import xin.xiaoer.common.utils.PageUtils;
import xin.xiaoer.common.utils.Query;
import xin.xiaoer.common.utils.R;
import xin.xiaoer.common.utils.StringUtility;
import xin.xiaoer.entity.File;
import xin.xiaoer.entity.SysUser;
import xin.xiaoer.modules.advert.service.XeAdvertService;
import xin.xiaoer.modules.book.entity.Book;
import xin.xiaoer.modules.book.entity.BookChapter;
import xin.xiaoer.modules.book.service.BookChapterService;
import xin.xiaoer.modules.book.service.BookService;
import xin.xiaoer.modules.comment.service.CommentService;
import xin.xiaoer.modules.favourite.service.FavouriteService;
import xin.xiaoer.modules.like.service.LikeService;
import xin.xiaoer.modules.review.service.ReviewService;
import xin.xiaoer.modules.xebanner.service.XeBannerService;
import xin.xiaoer.service.FileService;

import java.util.List;
import java.util.Map;


/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-07-30 11:10:06
 */
@Controller
@RequestMapping("bookDuBen")
public class BookDubenController {
	@Autowired
	private BookService bookService;

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

    @Autowired
    private BookChapterService bookChapterService;
	
    /**
     * 跳转到列表页
     */
    @RequestMapping("/list")
    @RequiresPermissions("book:list")
    public String list() {
        return "book/duben/list";
    }
    
	/**
	 * 列表数据
	 */
    @ResponseBody
	@RequestMapping("/listData")
	@RequiresPermissions("book:list")
	public R listData(@RequestParam Map<String, Object> params){
		//查询列表数据
        params.put("categoryCodeVague", "BCT001");
        Query query = new Query(params);

        SysUser admin = ShiroUtils.getUserEntity();
        if (admin.getUserId() != 1L){
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
     * 跳转到新增页面
     **/
    @RequestMapping("/add")
    @RequiresPermissions("book:save")
    public String add(Model model){
        model.addAttribute("admin",ShiroUtils.getUserEntity());
        return "book/duben/add";
    }

    /**
     *   跳转到修改页面
     **/
    @RequestMapping("/edit/{id}")
    @RequiresPermissions("book:update")
    public String edit(Model model, @PathVariable("id") String id){
		Book book = bookService.get(Long.parseLong(id));
        model.addAttribute("admin",ShiroUtils.getUserEntity());
        model.addAttribute("model",book);
        return "book/duben/edit";
    }

	/**
	 * 信息
	 */
    @ResponseBody
    @RequestMapping("/info/{bookId}")
    @RequiresPermissions("book:info")
    public R info(@PathVariable("bookId") Long bookId){
        Book book = bookService.get(bookId);
        return R.ok().put("book", book);
    }

    /**
	 * 保存
	 */
    @ResponseBody
    @SysLog("保存少儿读本")
	@RequestMapping("/save")
	@RequiresPermissions("book:save")
	public R save(@RequestBody Map<String, Object> params){
        Gson gson = new Gson();
        JsonElement jsonElement = gson.toJsonTree(params);
        Book book = gson.fromJson(jsonElement, Book.class);

        if (ShiroUtils.getUserId() == 1L){
            String spaceId = params.get("spaceId1").toString();
            if (StringUtility.isEmpty(spaceId) || !StringUtils.isNumeric(spaceId)) {
                throw new MyException("少儿空间不能为空！");
            }
            book.setSpaceId(Integer.parseInt(spaceId));
        } else {
            book.setSpaceId(ShiroUtils.getSpaceId());
        }

        if (book.getCategoryCode().isEmpty()){
            throw new MyException("类别不能为空！");
        }
        List<File> fileList = fileService.getByRelationId(book.getCover());
        if (fileList.size() < 1) {
            throw new MyException("书皮不能为空！");
        }
        if (fileList.size() > 1) {
            throw new MyException("书皮不能超过1！");
        }
        List<File> bookFileList = fileService.getByRelationId(book.getBookFile());
        if (bookFileList.size() < 1) {
            throw new MyException("书文件不能为空！");
        }
        if (bookFileList.size() > 1) {
            throw new MyException("书文件不能超过1！");
        }
        book.setUpdateBy(ShiroUtils.getUserId());
        book.setCreateBy(ShiroUtils.getUserId());
		bookService.save(book);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
    @ResponseBody
    @SysLog("修改少儿读本")
	@RequestMapping("/update")
	@RequiresPermissions("book:update")
	public R update(@RequestBody Map<String, Object> params){
        Gson gson = new Gson();
        JsonElement jsonElement = gson.toJsonTree(params);
        Book book = gson.fromJson(jsonElement, Book.class);

        if (ShiroUtils.getUserId() == 1L){
            String spaceId = params.get("spaceId1").toString();
            if (StringUtility.isEmpty(spaceId) || !StringUtils.isNumeric(spaceId)) {
                throw new MyException("少儿空间不能为空！");
            }
            book.setSpaceId(Integer.parseInt(spaceId));
        } else {
            book.setSpaceId(ShiroUtils.getSpaceId());
        }

        if (book.getCategoryCode().isEmpty()){
            throw new MyException("类别不能为空！");
        }
        List<File> fileList = fileService.getByRelationId(book.getCover());
        if (fileList.size() < 1) {
            throw new MyException("书皮不能为空！");
        }
        if (fileList.size() > 1) {
            throw new MyException("书皮不能超过1！");
        }
        List<File> bookFileList = fileService.getByRelationId(book.getBookFile());
        if (bookFileList.size() < 1) {
            throw new MyException("书文件不能为空！");
        }
        if (bookFileList.size() > 1) {
            throw new MyException("书文件不能超过1！");
        }
        book.setUpdateBy(ShiroUtils.getUserId());
		bookService.update(book);
		
		return R.ok();
	}

    /**
     * 启用
     */
    @ResponseBody
    @SysLog("启用少儿读本")
    @RequestMapping("/enable")
    @RequiresPermissions("book:update")
    public R enable(@RequestBody String[] ids){
        String stateValue=StateEnum.ENABLE.getCode();
		bookService.updateState(ids,stateValue);
        return R.ok();
    }
    /**
     * 禁用
     */
    @ResponseBody
    @SysLog("禁用少儿读本")
    @RequestMapping("/limit")
    @RequiresPermissions("book:update")
    public R limit(@RequestBody String[] ids){
        String stateValue=StateEnum.LIMIT.getCode();
		bookService.updateState(ids,stateValue);
        return R.ok();
    }
	
	/**
	 * 删除
	 */
    @ResponseBody
    @SysLog("删除少儿读本")
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
