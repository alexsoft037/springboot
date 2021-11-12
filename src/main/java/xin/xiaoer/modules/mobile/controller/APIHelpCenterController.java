package xin.xiaoer.modules.mobile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import xin.xiaoer.modules.help.entity.HelpCenter;
import xin.xiaoer.modules.help.service.HelpCenterService;
import xin.xiaoer.modules.mobile.bean.ResponseBean;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("mobile/helpcenter")
@ApiIgnore
public class APIHelpCenterController {

    @Autowired
    private HelpCenterService helpCenterService;

    @RequestMapping(value = "/listData", method = RequestMethod.POST)
    public ResponseBean listData(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        //查询列表数据

//        String pageCount = params.get("pageCount").toString();
//        String curPageNum = params.get("curPageNum").toString();
//        params.put("limit", pageCount);
//        params.put("page", curPageNum);
        params.put("state", "1");
        params.put("sidx", "help_id");
        params.put("order", "asc");
        params.put("category", "HELP");
//        Query query = new Query(params);

//        CloudStorageService cloudStorageService = OSSFactory.build();

        List<HelpCenter> helpCenters = helpCenterService.getList(params);
        for (HelpCenter helpCenter : helpCenters) {
            helpCenter.setContent("");
        }

        return new ResponseBean(false, "success", null, helpCenters);
    }

    @RequestMapping(value = "/detail/{helpId}", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseBean chapterDetail(@PathVariable("helpId") Integer helpId) throws Exception {
        //查询列表数据
        HelpCenter helpCenter = helpCenterService.get(helpId);

        return new ResponseBean(false, "success", null, helpCenter);
    }
}
