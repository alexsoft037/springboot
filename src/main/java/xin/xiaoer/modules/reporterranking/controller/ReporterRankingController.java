package xin.xiaoer.modules.reporterranking.controller;

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

import xin.xiaoer.modules.reporterranking.entity.ReporterRanking;
import xin.xiaoer.modules.reporterranking.service.ReporterRankingService;
import xin.xiaoer.common.utils.PageUtils;
import xin.xiaoer.common.utils.Query;
import xin.xiaoer.common.utils.R;


/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-07-25 12:04:25
 */
@Controller
@RequestMapping("reporterranking")
public class ReporterRankingController {
	@Autowired
	private ReporterRankingService reporterRankingService;
	
    /**
     * 跳转到列表页
     */
    @RequestMapping("/list")
    @RequiresPermissions("reporterranking:list")
    public String list() {
        return "reporterranking/list";
    }
    
	/**
	 * 列表数据
	 */
    @ResponseBody
	@RequestMapping("/listData")
	@RequiresPermissions("reporterranking:list")
	public R listData(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<ReporterRanking> reporterRankingList = reporterRankingService.getList(query);
		int total = reporterRankingService.getCount(query);
		
		PageUtils pageUtil = new PageUtils(reporterRankingList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}

    /**
     * 跳转到新增页面
     **/
    @RequestMapping("/add")
    @RequiresPermissions("reporterranking:save")
    public String add(){
        return "reporterranking/add";
    }

    /**
     *   跳转到修改页面
     **/
    @RequestMapping("/edit/{id}")
    @RequiresPermissions("reporterranking:update")
    public String edit(Model model, @PathVariable("id") String id){
		ReporterRanking reporterRanking = reporterRankingService.get(Long.parseLong(id));
        model.addAttribute("model",reporterRanking);
        return "reporterranking/edit";
    }

	/**
	 * 信息
	 */
    @ResponseBody
    @RequestMapping("/info/{userId}")
    @RequiresPermissions("reporterranking:info")
    public R info(@PathVariable("userId") Long userId){
        ReporterRanking reporterRanking = reporterRankingService.get(userId);
        return R.ok().put("reporterRanking", reporterRanking);
    }

    /**
	 * 保存
	 */
    @ResponseBody
    @SysLog("保存")
	@RequestMapping("/save")
	@RequiresPermissions("reporterranking:save")
	public R save(@RequestBody ReporterRanking reporterRanking){
		reporterRankingService.save(reporterRanking);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
    @ResponseBody
    @SysLog("修改")
	@RequestMapping("/update")
	@RequiresPermissions("reporterranking:update")
	public R update(@RequestBody ReporterRanking reporterRanking){
		reporterRankingService.update(reporterRanking);
		
		return R.ok();
	}

    /**
     * 启用
     */
    @ResponseBody
    @SysLog("启用")
    @RequestMapping("/enable")
    @RequiresPermissions("reporterranking:update")
    public R enable(@RequestBody String[] ids){
        String stateValue=StateEnum.ENABLE.getCode();
		reporterRankingService.updateState(ids,stateValue);
        return R.ok();
    }
    /**
     * 禁用
     */
    @ResponseBody
    @SysLog("禁用")
    @RequestMapping("/limit")
    @RequiresPermissions("reporterranking:update")
    public R limit(@RequestBody String[] ids){
        String stateValue=StateEnum.LIMIT.getCode();
		reporterRankingService.updateState(ids,stateValue);
        return R.ok();
    }
	
	/**
	 * 删除
	 */
    @ResponseBody
    @SysLog("删除")
	@RequestMapping("/delete")
	@RequiresPermissions("reporterranking:delete")
	public R delete(@RequestBody Long[] userIds){
		reporterRankingService.deleteBatch(userIds);
		
		return R.ok();
	}
	
}
