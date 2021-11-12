package xin.xiaoer.modules.review.controller;

import java.util.List;
import java.util.Map;
import xin.xiaoer.common.enumresource.StateEnum;
import xin.xiaoer.common.log.SysLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import javax.servlet.http.HttpServletRequest;

import xin.xiaoer.modules.review.entity.Review;
import xin.xiaoer.modules.review.service.ReviewService;
import xin.xiaoer.common.utils.PageUtils;
import xin.xiaoer.common.utils.Query;
import xin.xiaoer.common.utils.R;


/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-07-27 21:24:14
 */
@Controller
@RequestMapping("review")
public class ReviewController {
	@Autowired
	private ReviewService reviewService;
	
    /**
     * 跳转到列表页
     */
    @RequestMapping("/list")
    @RequiresPermissions("review:list")
    public String list() {
        return "review/list";
    }
    
	/**
	 * 列表数据
	 */
    @ResponseBody
	@RequestMapping("/listData")
	@RequiresPermissions("review:list")
	public R listData(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<Review> reviewList = reviewService.getAdminList(query);
		for (Review review: reviewList){
		    review = reviewService.filterReviewData(review);
        }
		int total = reviewService.getAdminCount(query);
		
		PageUtils pageUtil = new PageUtils(reviewList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}

    /**
     * 跳转到新增页面
     **/
    @RequestMapping("/add")
    @RequiresPermissions("review:save")
    public String add(){
        return "review/add";
    }

    /**
     *   跳转到修改页面
     **/
    @RequestMapping("/edit/{id}")
    @RequiresPermissions("review:update")
    public String edit(Model model, @PathVariable("id") String id){
		Review review = reviewService.get(Long.parseLong(id));
        model.addAttribute("model",review);
        return "review/edit";
    }

	/**
	 * 信息
	 */
    @RequestMapping("/info/{reviewId}")
    @RequiresPermissions("review:info")
    public String info(Model model, @PathVariable("reviewId") Long reviewId){
        Review review = reviewService.getDetail(reviewId);
        review = reviewService.filterReviewData(review);
        model.addAttribute("model",review);
        return "review/info";
    }

    /**
	 * 保存
	 */
    @ResponseBody
    @SysLog("保存")
	@RequestMapping("/save")
	@RequiresPermissions("review:save")
	public R save(@RequestBody Review review){
		reviewService.save(review);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
    @ResponseBody
    @SysLog("修改")
	@RequestMapping("/update")
	@RequiresPermissions("review:update")
	public R update(@RequestBody Review review){
		reviewService.update(review);
		
		return R.ok();
	}

    /**
     * 启用
     */
    @ResponseBody
    @SysLog("启用")
    @RequestMapping("/enable")
    @RequiresPermissions("review:update")
    public R enable(@RequestBody String[] ids){
        String stateValue=StateEnum.ENABLE.getCode();
		reviewService.updateState(ids,stateValue);
        return R.ok();
    }
    /**
     * 禁用
     */
    @ResponseBody
    @SysLog("禁用")
    @RequestMapping("/limit")
    @RequiresPermissions("review:update")
    public R limit(@RequestBody String[] ids){
        String stateValue=StateEnum.LIMIT.getCode();
		reviewService.updateState(ids,stateValue);
        return R.ok();
    }
	
	/**
	 * 删除
	 */
    @ResponseBody
    @SysLog("删除")
	@RequestMapping("/delete")
	@RequiresPermissions("review:delete")
	public R delete(@RequestBody Long[] reviewIds){
		reviewService.deleteBatch(reviewIds);
		
		return R.ok();
	}
	
}
