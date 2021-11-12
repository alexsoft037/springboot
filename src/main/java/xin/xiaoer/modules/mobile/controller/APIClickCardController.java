package xin.xiaoer.modules.mobile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;
import xin.xiaoer.common.integralConfig.IntegralConfig;
import xin.xiaoer.common.integralConfig.IntegralConfigFactory;
import xin.xiaoer.common.utils.DateUtil;
import xin.xiaoer.common.utils.DateUtility;
import xin.xiaoer.common.utils.YunXinUtil;
import xin.xiaoer.entity.Article;
import xin.xiaoer.modules.mobile.bean.ResponseBean;
import xin.xiaoer.modules.setting.entity.ClickCard;
import xin.xiaoer.modules.setting.entity.Integral;
import xin.xiaoer.modules.setting.service.ClickCardService;
import xin.xiaoer.modules.setting.service.IntegralService;
import xin.xiaoer.service.SysUserService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("mobile/clickCard")
@ApiIgnore
public class APIClickCardController {

    @Autowired
    SysUserService sysUserService;

    @Autowired
    IntegralService integralService;

    @Autowired
    ClickCardService clickCardService;

    @RequestMapping(value = "/click", method = RequestMethod.POST)
    public ResponseBean click(HttpServletRequest request) throws IOException {

        String userId = request.getParameter("userId");
        String clickDateStr = request.getParameter("clickDate");

        Date clickDate = DateUtil.fomatDate(clickDateStr);
        Date today = DateUtil.getToday();
        if (DateUtility.getDistance(today, clickDate) != 0){
            return new ResponseBean(false, ResponseBean.FAILED, "你必须今天点击!", null);
        }

        ClickCard clickCard = clickCardService.get(Long.parseLong(userId));
        YunXinUtil yunXinUtil = new YunXinUtil();
        IntegralConfig integralConfig = IntegralConfigFactory.build();
        Integer saveClickCount = 1;
        String todayIntegral = Double.toString(integralConfig.getPunchIntegral1());
        if (clickCard == null){
            clickCard = new ClickCard();
            clickCard.setUserId(Long.parseLong(userId));
            clickCard.setClickCount(1);
            clickCard.setLastClickDt(today);
            clickCardService.save(clickCard);

            Integral integral = new Integral();
            integral.setUserId(Long.parseLong(userId));
            integral.setIntegral(Double.toString(integralConfig.getPunchIntegral1()));
            integral.setArticleTypeCode(Article.INTEGRAL);
            integral.setTitle("打卡");
            integralService.save(integral);
            try {
                yunXinUtil.sendSinglePush(integral.getUserId(), "获得积分"+integral.getTitle());
            } catch (Exception e){
            }
        } else {
            Date lastClickDate = DateUtil.changeYMDDate(clickCard.getLastClickDt());
            int lastClickDiff = DateUtility.getDistance(lastClickDate, today);
            if (lastClickDiff == 0) {
                return new ResponseBean(false, ResponseBean.FAILED, "已经打卡今天！", null);
            } else {
                if (lastClickDiff == 1){
                    Integer clickCount = clickCard.getClickCount();
                    switch (clickCount) {
                        case 1: {
                            saveClickCount = 2;
                            todayIntegral = Double.toString(integralConfig.getPunchIntegral2());
                            break;
                        }
                        case 2: {
                            saveClickCount = 3;
                            todayIntegral = Double.toString(integralConfig.getPunchIntegral3());
                            break;
                        }
                        case 3: {
                            saveClickCount = 4;
                            todayIntegral = Double.toString(integralConfig.getPunchIntegral3());
                            break;
                        }
                        case 4: {
                            saveClickCount = 5;
                            todayIntegral = Double.toString(integralConfig.getPunchIntegral3());
                            break;
                        }
                        case 5: {
                            saveClickCount = 6;
                            todayIntegral = Double.toString(integralConfig.getPunchIntegral3());
                            break;
                        }
                        case 6: {
                            saveClickCount = 7;
                            todayIntegral = Double.toString(integralConfig.getPunchIntegral3());
                            break;
                        }
                        case 7: {
                            saveClickCount = 1;
                            todayIntegral = Double.toString(integralConfig.getPunchIntegral1());
                            break;
                        }
                    }
                } else {
                    saveClickCount = 1;
                    todayIntegral = Double.toString(integralConfig.getPunchIntegral1());
                }

                clickCard.setClickCount(saveClickCount);
                clickCard.setLastClickDt(today);
                clickCardService.update(clickCard);

                Integral integral = new Integral();
                integral.setUserId(Long.parseLong(userId));
                integral.setIntegral(todayIntegral);
                integral.setArticleTypeCode(Article.INTEGRAL);
                integral.setTitle("打卡");
                integralService.save(integral);
                try {
                    yunXinUtil.sendSinglePush(integral.getUserId(), "获得积分"+integral.getTitle());
                } catch (Exception e){
                }
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("integral", todayIntegral);
        return new ResponseBean(false, ResponseBean.SUCCESS, null, result);
    }

    @RequestMapping(value = "/todayCheck", method = RequestMethod.POST)
    public ResponseBean todayCheck(HttpServletRequest request) throws IOException {

        String userId = request.getParameter("userId");
        String clickDateStr = request.getParameter("clickDate");

        Date clickDate = DateUtil.fomatDate(clickDateStr);
        Date today = DateUtil.getToday();
        if (DateUtility.getDistance(today, clickDate) != 0){
            return new ResponseBean(false, ResponseBean.FAILED, "你必须今天点击!", null);
        }

        IntegralConfig integralConfig = IntegralConfigFactory.build();
        ClickCard clickCard = clickCardService.get(Long.parseLong(userId));
        String canClickYn = "N";
        String todayIntegral = Double.toString(integralConfig.getPunchIntegral1());
        if (clickCard == null) {
            canClickYn = "Y";
            todayIntegral = Double.toString(integralConfig.getPunchIntegral1());
        } else {
            Date lastClickDate = DateUtil.changeYMDDate(clickCard.getLastClickDt());
            int lastClickDiff = DateUtility.getDistance(lastClickDate, today);
            if (lastClickDiff == 0) {
                canClickYn = "N";
                todayIntegral = "";
                Integral integral = integralService.getClickCardIntegral(Long.parseLong(userId), today);
                Map<String, Object> result = new HashMap<>();
                result.put("integral", integral.getIntegral());
                result.put("canClickYn", canClickYn);
                return new ResponseBean(false, ResponseBean.FAILED, "已经打卡今天!", result);
            } else {
                canClickYn = "Y";
                if (lastClickDiff == 1){
                    Integer clickCount = clickCard.getClickCount();
                    switch (clickCount) {
                        case 1: {
                            todayIntegral = Double.toString(integralConfig.getPunchIntegral2());
                            break;
                        }
                        case 2: {
                            todayIntegral = Double.toString(integralConfig.getPunchIntegral3());
                            break;
                        }
                        case 3: {
                            todayIntegral = Double.toString(integralConfig.getPunchIntegral3());
                            break;
                        }
                        case 4: {
                            todayIntegral = Double.toString(integralConfig.getPunchIntegral3());
                            break;
                        }
                        case 5: {
                            todayIntegral = Double.toString(integralConfig.getPunchIntegral3());
                            break;
                        }
                        case 6: {
                            todayIntegral = Double.toString(integralConfig.getPunchIntegral3());
                            break;
                        }
                        case 7: {
                            todayIntegral = Double.toString(integralConfig.getPunchIntegral1());
                            break;
                        }
                    }
                } else {
                    todayIntegral = Double.toString(integralConfig.getPunchIntegral1());
                }
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("integral", todayIntegral);
        result.put("canClickYn", canClickYn);

        return new ResponseBean(false, ResponseBean.SUCCESS, null, result);
    }
}
