package xin.xiaoer.modules.website.controller;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xin.xiaoer.common.oss.CloudStorageService;
import xin.xiaoer.common.oss.OSSFactory;
import xin.xiaoer.common.utils.PageUtils;
import xin.xiaoer.common.utils.Query;
import xin.xiaoer.common.utils.StringUtility;
import xin.xiaoer.entity.File;
import xin.xiaoer.entity.SpaceNews;
import xin.xiaoer.modules.mobile.bean.ResponseBean;
import xin.xiaoer.modules.spacehaedline.service.SpaceHeadlineService;
import xin.xiaoer.service.FileService;
import xin.xiaoer.service.SpaceNewsService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


/**
 * 
 * 
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-10-28 22:08:28
 */
@RestController
@RequestMapping("website/spaceNews")
public class WebAPISpaceNewsController {
	@Autowired
	private SpaceNewsService spaceNewsService;
	@Autowired
	private FileService fileService;
	@Autowired
    private SpaceHeadlineService spaceHeadlineService;
    @RequestMapping(value = "/getnewsbylocation")
    public ResponseBean listData(@RequestParam Map<String, Object> params,HttpServletRequest request){
        //String  spaceId = (String) request.getSession().getAttribute("spaceId");
        String page = params.get("page").toString();
        String limit = params.get("limit").toString();
        String spaceId = params.get("spaceId").toString();
        if (params.get("userId") != null && StringUtils.isBlank(params.get("userId").toString())){
            params.put("userId","-1");
        }
//        String userId = params.get("userId").toString();
//        if (StringUtility.isEmpty(userId)) {
//            params.put("userId", "-1");
//        }
        params.put("page",page);
        params.put("limit",limit);
        params.put("spaceId",spaceId);
        Query query = new Query(params);
        CloudStorageService cloudStorageService = OSSFactory.build();
        if(StringUtility.isEmpty(spaceId)){
            List<Map> allSpaceNewsList = spaceNewsService.getallspacenews(query);
            for (Map map : allSpaceNewsList) {
                List<File> files = fileService.getByRelationId(map.get("featured_image").toString());
                if (files.size() > 0 ){
                    map.put("featured_image",cloudStorageService.generatePresignedUrl(files.get(0).getUrl()));
                }
//                map.put("featured_image",cloudStorageService.generatePresignedUrl((String) map.get("featured_image")));
            }
            int total = spaceNewsService.getallnewscount(query);
            PageUtils pageUtil = new PageUtils(allSpaceNewsList, total, query.getLimit(), query.getPage());
            return new ResponseBean(false, "success", null, pageUtil);
        }
        List<Map> spaceNewsList = spaceNewsService.getnewsbylocation(query);
        for (Map map : spaceNewsList) {
            List<File> files = fileService.getByRelationId(map.get("featured_image").toString());
            if (files.size() > 0 ){
                map.put("featured_image",cloudStorageService.generatePresignedUrl(files.get(0).getUrl()));
            }
//            map.put("featured_image",cloudStorageService.generatePresignedUrl((String) map.get("featured_image")));
        }
        int total = spaceNewsService.getNewsCount(query);
        PageUtils pageUtil = new PageUtils(spaceNewsList, total, query.getLimit(), query.getPage());
        return new ResponseBean(false, "success", null, pageUtil);
    }

    @RequestMapping("/getreviewlist")
    public ResponseBean reviewlistData(@RequestParam Map<String, Object> params){
        params.put("page",1);
        params.put("limit",10);
        params.put("userId",30);
        params.put("atc","AT0005");
        Query query = new Query(params);
        List<SpaceNews> spaceNewsList = spaceNewsService.getreviewList(params);
        int total = spaceNewsService.getCount(query);
        PageUtils pageUtil = new PageUtils(spaceNewsList, total, query.getLimit(), query.getPage());
        return new ResponseBean(false, "success", null, pageUtil);
    }

    @RequestMapping("/getlikelist")
    public ResponseBean likelistData(@RequestParam Map<String, Object> params){
        params.put("page",1);
        params.put("limit",10);
        params.put("userId",30);
        params.put("atc","AT0005");
        Query query = new Query(params);
        List<SpaceNews> spaceNewsList = spaceNewsService.getlikeList(params);
        int total = spaceNewsService.getCount(query);
        PageUtils pageUtil = new PageUtils(spaceNewsList, total, query.getLimit(), query.getPage());
        return new ResponseBean(false, "success", null, pageUtil);
    }

    @RequestMapping("/getcollectionlist")
    public ResponseBean collectionlistData(@RequestParam Map<String, Object> params){
        params.put("page",1);
        params.put("limit",10);
        params.put("userId",35);
        params.put("atc","AT0010");
        Query query = new Query(params);
        List<SpaceNews> spaceNewsList = spaceNewsService.getcollectionList(params);
        int total = spaceNewsService.getCount(query);
        PageUtils pageUtil = new PageUtils(spaceNewsList, total, query.getLimit(), query.getPage());
        return new ResponseBean(false, "success", null, pageUtil);
    }

}
