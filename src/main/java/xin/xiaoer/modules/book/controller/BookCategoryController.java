package xin.xiaoer.modules.book.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import xin.xiaoer.entity.CodeValue;
import xin.xiaoer.entity.File;
import xin.xiaoer.service.CategoryService;
import xin.xiaoer.modules.book.service.BookService;
import xin.xiaoer.service.FileService;


/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-07-30 11:10:06
 */
@Controller
@RequestMapping("bookcategory")
public class BookCategoryController {
	@Autowired
	private CategoryService categoryService;

	@Autowired
    private BookService bookService;

	@Autowired
    private FileService fileService;
    /**
     * 跳转到列表页
     */
    @RequestMapping("/list")
    @RequiresPermissions("bookcategory:list")
    public String list() {
        return "bookcategory/list";
    }
    
	/**
	 * 列表数据
	 */
    @ResponseBody
	@RequestMapping("/listData")
	@RequiresPermissions("bookcategory:list")
	public R listData(@RequestParam Map<String, Object> params){
		//查询列表数据
        params.put("categoryCodeVague", "BCT");
        Query query = new Query(params);

		List<Category> categoryList = categoryService.getList(query);
        CloudStorageService cloudStorageService = OSSFactory.build();
		for (Category category: categoryList){
            category.setCategoryIcon(cloudStorageService.generatePresignedUrl(category.getCategoryIcon()));
        }
		int total = categoryService.getCount(query);
		
		PageUtils pageUtil = new PageUtils(categoryList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}

    @ResponseBody
    @RequestMapping("/select")
    public R select() {
        //查询列表数据
        Map<String, Object> params = new HashMap<>();
        params.put("categoryCodeVague", "BCT");
        List<Category> bookCategories = categoryService.getList(params);

        List<ZtreeBean> ztreeBeans = new ArrayList<>();
        for (Category category : bookCategories) {
            ZtreeBean tree = new ZtreeBean();
            tree.setId(category.getCategoryCode());
            tree.setpId(category.getUpperCode());
            tree.setName(category.getCategoryName());
            tree.setOpen("null");
            tree.setChkDisabled("false");
            ztreeBeans.add(tree);
        }

        return R.ok().put("data", ztreeBeans);
    }

    @ResponseBody
    @RequestMapping("/getCodeValues")
    public R getCodeValues(@RequestParam Map<String, Object> params) {
        List<CodeValue> categoryList = null;
        if (params.get("upperCode") != null && !"".equals(params.get("upperCode"))) {
            String upperCode = params.get("upperCode").toString();
            categoryList = categoryService.getCodeValues(upperCode);
        }
        return R.ok().put("data", categoryList);
    }

    /**
     * 获取下级地区
     */
    @ResponseBody
    @RequestMapping("normalList/{upperCode}")
    public R normalList(@PathVariable String upperCode) {
        List<EnumBean> list = new ArrayList<>();
        HashMap<String, Object> paraMap = new HashMap<String, Object>();
        paraMap.put("sidx", "category_code");
        paraMap.put("order", "asc");
        paraMap.put("upperCode", upperCode);
        List<Category> bookCategories = categoryService.getList(paraMap);
        if (bookCategories != null && bookCategories.size() > 0) {
            for (int i = 0; i < bookCategories.size(); i++) {
                EnumBean bean = new EnumBean();
                bean.setCode(bookCategories.get(i).getCategoryCode());
                bean.setValue(bookCategories.get(i).getCategoryName());
                list.add(bean);
            }
        }
        return R.ok().put("data", list);
    }
    /**
     * 跳转到新增页面
     **/
    @RequestMapping("/add")
    @RequiresPermissions("bookcategory:save")
    public String add(){
        return "bookcategory/add";
    }

    /**
     *   跳转到修改页面
     **/
    @RequestMapping("/edit/{id}")
    @RequiresPermissions("bookcategory:update")
    public String edit(Model model, @PathVariable("id") String id){
		Category category = categoryService.get(Integer.parseInt(id));
        model.addAttribute("model", category);
        return "bookcategory/edit";
    }

	/**
	 * 信息
	 */
    @ResponseBody
    @RequestMapping("/info/{categoryId}")
    @RequiresPermissions("bookcategory:info")
    public R info(@PathVariable("categoryId") Integer categoryId){
        Category category = categoryService.get(categoryId);
        return R.ok().put("category", category);
    }

    /**
	 * 保存
	 */
    @ResponseBody
    @SysLog("保存空间书架分类")
	@RequestMapping("/save")
	@RequiresPermissions("bookcategory:save")
	public R save(@RequestBody Category category){
        List<Category> categories = categoryService.findByVerify(category);
        if (categories != null && categories.size() > 0){
            throw  new MyException(category.getCategoryCode()+"下已存在该分类值");
        }
        List<File> fileList = fileService.getByRelationId(category.getCategoryIcon());
        if (fileList.size() > 1) {
            throw new MyException("分类图标不能超过1！");
        }
        category.setCreateBy(ShiroUtils.getUserId());
        category.setUpdateBy(ShiroUtils.getUserId());
		categoryService.save(category);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
    @ResponseBody
    @SysLog("修改空间书架分类")
	@RequestMapping("/update")
	@RequiresPermissions("bookcategory:update")
	public R update(@RequestBody Category category){
        List<Category> categories = categoryService.findByVerify(category);
        if (categories != null && categories.size() > 0){
            throw  new MyException(category.getCategoryCode()+"下已存在该分类值");
        }
        List<File> fileList = fileService.getByRelationId(category.getCategoryIcon());
        if (fileList.size() > 1) {
            throw new MyException("分类图标不能超过1！");
        }
        category.setUpdateBy(ShiroUtils.getUserId());
		categoryService.update(category);
		
		return R.ok();
	}

    /**
     * 启用
     */
    @ResponseBody
    @SysLog("启用空间书架分类")
    @RequestMapping("/enable")
    @RequiresPermissions("bookcategory:update")
    public R enable(@RequestBody String[] ids){
        String stateValue=StateEnum.ENABLE.getCode();
		categoryService.updateState(ids,stateValue);
        return R.ok();
    }
    /**
     * 禁用
     */
    @ResponseBody
    @SysLog("禁用空间书架分类")
    @RequestMapping("/limit")
    @RequiresPermissions("bookcategory:update")
    public R limit(@RequestBody String[] ids){
        String stateValue=StateEnum.LIMIT.getCode();
		categoryService.updateState(ids,stateValue);
        return R.ok();
    }
	
	/**
	 * 删除
	 */
    @ResponseBody
    @SysLog("删除空间书架分类")
	@RequestMapping("/delete")
	@RequiresPermissions("bookcategory:delete")
	public R delete(@RequestBody Integer[] categoryIds) {

        for (Integer categoryId: categoryIds){
            Category category = categoryService.get(categoryId);
            if (category != null){
                fileService.deleteByRelationId(category.getCategoryIcon());
                bookService.removeCategoryCode(category.getCategoryCode());
                categoryService.removeUpperCode(category.getCategoryCode());
            }
        }

		categoryService.deleteBatch(categoryIds);
		
		return R.ok();
	}
	
}
