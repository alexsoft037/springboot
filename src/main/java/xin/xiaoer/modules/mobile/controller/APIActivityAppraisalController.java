package xin.xiaoer.modules.mobile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;
import xin.xiaoer.common.oss.CloudStorageService;
import xin.xiaoer.common.oss.OSSFactory;
import xin.xiaoer.common.utils.PageUtils;
import xin.xiaoer.common.utils.Query;
import xin.xiaoer.common.utils.StringUtility;
import xin.xiaoer.entity.File;
import xin.xiaoer.modules.activityappraisal.entity.ActivityAppraisal;
import xin.xiaoer.modules.activityappraisal.service.ActivityAppraisalService;
import xin.xiaoer.modules.mobile.bean.ResponseBean;
import xin.xiaoer.service.FileService;
import xin.xiaoer.service.SysUserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("mobile/activityAppraisal")
@ApiIgnore
public class APIActivityAppraisalController {
    @Autowired
    private ActivityAppraisalService activityAppraisalService;

    @Autowired
    private FileService fileService;

    @Autowired
    private SysUserService sysUserService;

    @RequestMapping(value = "/listData", method = RequestMethod.POST)
    public ResponseBean listData(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        //查询列表数据

//        String requestPageCount = request.getParameter("pageCount");
        String pageCount = params.get("pageCount").toString();
        String curPageNum = params.get("curPageNum").toString();
        String spaceId = params.get("spaceId").toString();
        if (StringUtility.isEmpty(spaceId)){
            params.put("spaceId", 0);
        }
        params.put("limit", pageCount);
        params.put("page", curPageNum);
        params.put("state", "1");
        Query query = new Query(params);

        CloudStorageService cloudStorageService = OSSFactory.build();
        List<ActivityAppraisal> appraisalList = activityAppraisalService.getList(query);
        for (ActivityAppraisal activityAppraisal : appraisalList) {
            activityAppraisal.setSpaceName("活动评选");
            List<File> files = fileService.getByRelationId(activityAppraisal.getFeaturedImage());
            if (files.size() > 0) {
                activityAppraisal.setFeaturedImage(cloudStorageService.generatePresignedUrl(files.get(0).getUrl()));
            } else {
                activityAppraisal.setFeaturedImage("");
            }
            if (!StringUtility.isEmpty(activityAppraisal.getContentFile()) && !activityAppraisal.getContentFile().contains("http")) {
                activityAppraisal.setContentFile(cloudStorageService.generatePresignedUrl(activityAppraisal.getContentFile()));
            }
        }

        int total = activityAppraisalService.getCount(query);

        PageUtils pageUtil = new PageUtils(appraisalList, total, query.getLimit(), query.getPage());

        return new ResponseBean(false, "success", null, pageUtil);
    }
}
