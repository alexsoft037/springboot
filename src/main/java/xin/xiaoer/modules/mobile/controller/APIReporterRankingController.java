package xin.xiaoer.modules.mobile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;
import xin.xiaoer.modules.mobile.bean.ResponseBean;
import xin.xiaoer.modules.reporterranking.entity.ReporterRanking;
import xin.xiaoer.modules.reporterranking.service.ReporterRankingService;
import xin.xiaoer.modules.setting.service.IntegralService;
import xin.xiaoer.service.SysUserService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("mobile/reporterRanking")
@ApiIgnore
public class APIReporterRankingController {
    @Autowired
    private IntegralService reporterRankingService;

    @Autowired
    private SysUserService sysUserService;

    @RequestMapping(value = "/{period}", method = RequestMethod.GET)
    public ResponseBean thisWeek(@PathVariable("period") String period) {

        List<ReporterRanking> rankingList = new ArrayList<>();

        if (period.equals("week")){
            rankingList = reporterRankingService.getThisWeekRanking(null);
        } else {
            rankingList = reporterRankingService.getThisMonthRanking(null);
        }

        for (ReporterRanking reporterRanking: rankingList){
            reporterRanking.setAvatar(sysUserService.getAvatar(reporterRanking.getAvatar()));
        }

        return new ResponseBean(false, "success", null, rankingList);
    }

    @RequestMapping(value = "/{period}/{userId}", method = RequestMethod.GET)
    public ResponseBean thisMonth(@PathVariable("period") String period, @PathVariable("userId") Long userId) {

        List<ReporterRanking> rankingList = new ArrayList<>();

        if (period.equals("week")){
            rankingList = reporterRankingService.getThisWeekRanking(userId);
        } else {
            rankingList = reporterRankingService.getThisMonthRanking(userId);
        }

        ReporterRanking reporterRanking = new ReporterRanking();
        if (rankingList != null && rankingList.size() > 0){
            reporterRanking = rankingList.get(0);
        } else {
            reporterRanking.setUserId(userId);
            reporterRanking.setIntegral(0);
        }
        reporterRanking.setAvatar(sysUserService.getAvatar(reporterRanking.getAvatar()));

        return new ResponseBean(false, "success", null, reporterRanking);
    }
}
