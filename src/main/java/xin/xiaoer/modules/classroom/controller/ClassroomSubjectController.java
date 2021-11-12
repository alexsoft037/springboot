package xin.xiaoer.modules.classroom.controller;

import com.sun.corba.se.impl.encoding.OSFCodeSetRegistry;
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
import xin.xiaoer.common.utils.*;
import xin.xiaoer.entity.Category;
import xin.xiaoer.entity.CodeValue;
import xin.xiaoer.entity.File;
import xin.xiaoer.modules.book.service.BookService;
import xin.xiaoer.modules.classroom.entity.SharingLesson;
import xin.xiaoer.modules.classroom.service.SharingLessonService;
import xin.xiaoer.service.CategoryService;
import xin.xiaoer.service.FileService;

import java.util.ArrayList;
import java.util.HashMap;
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
@RequestMapping("classroomSubject")
public class ClassroomSubjectController {
	@Autowired
	private CategoryService categoryService;

	@Autowired
    private SharingLessonService sharingLessonService;

	@Autowired
    private FileService fileService;
    /**
     * 跳转到列表页
     */
    @RequestMapping("/list")
    @RequiresPermissions("classroomSubject:list")
    public String list() {
        return "classroomSubject/list";
    }
    
	/**
	 * 列表数据
	 */
    @ResponseBody
	@RequestMapping("/listData")
	@RequiresPermissions("classroomSubject:list")
	public R listData(@RequestParam Map<String, Object> params){
		//查询列表数据
        params.put("categoryCodeVague", "CRT");
        Query query = new Query(params);

        CloudStorageService cloudStorageService = OSSFactory.build();
		List<Category> categoryList = categoryService.getList(query);
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
        params.put("categoryCodeVague", "CRT");
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
        List<Category> categories = categoryService.getList(paraMap);
        if (categories != null && categories.size() > 0) {
            for (int i = 0; i < categories.size(); i++) {
                EnumBean bean = new EnumBean();
                bean.setCode(categories.get(i).getCategoryCode());
                bean.setValue(categories.get(i).getCategoryName());
                list.add(bean);
            }
        }
        return R.ok().put("data", list);
    }
    /**
     * 跳转到新增页面
     **/
    @RequestMapping("/add")
    @RequiresPermissions("classroomSubject:save")
    public String add(){
        return "classroomSubject/add";
    }

    /**
     *   跳转到修改页面
     **/
    @RequestMapping("/edit/{id}")
    @RequiresPermissions("classroomSubject:update")
    public String edit(Model model, @PathVariable("id") String id){
		Category category = categoryService.get(Integer.parseInt(id));
        model.addAttribute("model", category);
        return "classroomSubject/edit";
    }

	/**
	 * 信息
	 */
    @ResponseBody
    @RequestMapping("/info/{categoryId}")
    @RequiresPermissions("classroomSubject:info")
    public R info(@PathVariable("categoryId") Integer categoryId){
        Category category = categoryService.get(categoryId);
        return R.ok().put("category", category);
    }

    /**
	 * 保存
	 */
    @ResponseBody
    @SysLog("保存课程类型")
	@RequestMapping("/save")
	@RequiresPermissions("classroomSubject:save")
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
    @SysLog("修改课程类型")
	@RequestMapping("/update")
	@RequiresPermissions("classroomSubject:update")
	public R update(@RequestBody Category category){
        List<Category> categories = categoryService.findByVerify(category);
        if (categories != null && categories.size() > 0) {
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
    @SysLog("启用课程类型")
    @RequestMapping("/enable")
    @RequiresPermissions("classroomSubject:update")
    public R enable(@RequestBody String[] ids){
        String stateValue=StateEnum.ENABLE.getCode();
		categoryService.updateState(ids,stateValue);
        return R.ok();
    }
    /**
     * 禁用
     */
    @ResponseBody
    @SysLog("禁用课程类型")
    @RequestMapping("/limit")
    @RequiresPermissions("classroomSubject:update")
    public R limit(@RequestBody String[] ids){
        String stateValue=StateEnum.LIMIT.getCode();
		categoryService.updateState(ids,stateValue);
        return R.ok();
    }
	
	/**
	 * 删除
	 */
    @ResponseBody
    @SysLog("删除课程类型")
	@RequestMapping("/delete")
	@RequiresPermissions("classroomSubject:delete")
	public R delete(@RequestBody Integer[] categoryIds) {

        for (Integer categoryId: categoryIds){
            Category category = categoryService.get(categoryId);
            if (category != null){
                sharingLessonService.removeCategoryCode(category.getCategoryCode());
                categoryService.removeUpperCode(category.getCategoryCode());
                fileService.deleteByRelationId(category.getCategoryIcon());
            }
        }

		categoryService.deleteBatch(categoryIds);
		
		return R.ok();
	}
	
}
