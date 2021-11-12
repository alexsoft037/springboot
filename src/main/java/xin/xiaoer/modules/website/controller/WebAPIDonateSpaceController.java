package xin.xiaoer.modules.website.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import xin.xiaoer.common.integralConfig.IntegralConfig;
import xin.xiaoer.common.integralConfig.IntegralConfigFactory;
import xin.xiaoer.common.oss.CloudStorageService;
import xin.xiaoer.common.oss.OSSFactory;
import xin.xiaoer.common.utils.PageUtils;
import xin.xiaoer.common.utils.Query;
import xin.xiaoer.common.utils.StringUtility;
import xin.xiaoer.common.utils.YunXinUtil;
import xin.xiaoer.entity.Article;
import xin.xiaoer.entity.Commpara;
import xin.xiaoer.entity.File;
import xin.xiaoer.modules.donatespace.entity.DonateSpace;
import xin.xiaoer.modules.donatespace.entity.DonateUser;
import xin.xiaoer.modules.donatespace.service.DonateSpaceIntroService;
import xin.xiaoer.modules.donatespace.service.DonateSpaceProcessService;
import xin.xiaoer.modules.donatespace.service.DonateSpaceService;
import xin.xiaoer.modules.donatespace.service.DonateUserService;
import xin.xiaoer.modules.mobile.bean.ResponseBean;
import xin.xiaoer.modules.mobile.entity.DSpaceListItem;
import xin.xiaoer.modules.mobile.entity.DonateUserResume;
import xin.xiaoer.modules.review.service.ReviewService;
import xin.xiaoer.modules.setting.entity.Integral;
import xin.xiaoer.modules.setting.service.IntegralService;
import xin.xiaoer.service.CommparaService;
import xin.xiaoer.service.FileService;
import xin.xiaoer.service.SysUserService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("website/donateSpace")
@ApiIgnore
public class WebAPIDonateSpaceController {
    @Autowired
    private DonateSpaceService donateSpaceService;

    @Autowired
    private DonateSpaceIntroService donateSpaceIntroService;

    @Autowired
    private DonateSpaceProcessService donateSpaceProcessService;

    @Autowired
    private DonateUserService donateUserService;

    @Autowired
    private FileService fileService;

    @Autowired
    private CommparaService commparaService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private IntegralService integralService;

    @RequestMapping(value = "/listData", method = RequestMethod.GET)
    public ResponseBean listData(@RequestParam Map<String, Object> params, HttpServletRequest request){
        //查询列表数据
        String city = (String)request.getSession().getAttribute("city");
//      String requestPageCount = request.getParameter("pageCount");
//        String pageCount = params.get("pageCount").toString();
        String typeCode = params.get("typeCode").toString();
//        String curPageNum = params.get("curPageNum").toString();
        String spaceId = params.get("spaceId").toString();
       if (StringUtility.isEmpty(spaceId)) {
           params.put("spaceId", 0);
       }
        params.put("spaceId", spaceId);
        params.put("limit", 10);
        params.put("page", 1);
        params.put("state", "1");
        params.put("city",city);
        params.put("dsTypeCode", typeCode);
        if (params.get("limit") == null){
            params.put("limit", 10);
        }
        if (params.get("page") == null){
            params.put("page", 1);
        }
        Query query = new Query(params);

        List<DSpaceListItem> donateSpaceList = donateSpaceService.getDSpaceListData(query);
        CloudStorageService cloudStorageService = OSSFactory.build();
        for (DSpaceListItem dSpaceListItem : donateSpaceList){
            if (dSpaceListItem.getFeturedImage() != null && !dSpaceListItem.getFeturedImage().equals("")){
                dSpaceListItem.setFeturedImage(cloudStorageService.generatePresignedUrl(dSpaceListItem.getFeturedImage()));
            }
        }
        int total = donateSpaceService.getCountDSpace(query);

        PageUtils pageUtil = new PageUtils(donateSpaceList, total, query.getLimit(), query.getPage());

        return new ResponseBean(false,"success", null, pageUtil);
    }

    @RequestMapping(value = "/gettotal",method = RequestMethod.GET)
    public ResponseBean total(){
         List<DonateUserResume> total = donateSpaceService.gettotal();
        return new ResponseBean(false,"success", null, total);
    }

    @RequestMapping(value = "/gettotalbyuser/{userId}",method = RequestMethod.GET)
    public ResponseBean totalbyuser(@PathVariable Integer userId){
        List<DonateUserResume> totalbyuser = donateSpaceService.gettotalbyuser(userId);
        return new ResponseBean(false,"success", null, totalbyuser);
    }


    /***
     * 公益详情页
     * @param itemId
     * @return
     */
    @RequestMapping(value = "/detail/{itemId}",method = RequestMethod.GET)
    public ResponseBean detail(@PathVariable("itemId") String itemId) {

        DonateSpace donateSpace = donateSpaceService.get(Long.parseLong(itemId));
        if (donateSpace==null) {
            return new ResponseBean(false, ResponseBean.FAILED, "没有此项目", null);
        }
        donateSpace.setReadCount(donateSpace.getReadCount() + 1);
        donateSpaceService.update(donateSpace);

        Map<String, Object> search = new HashMap<>();
        search.put("articleId", donateSpace.getItemId());
        search.put("articleTypeCode", "AT0001");

        Integer commentCount = reviewService.getCount(search);
        donateSpace.setCommentCount(commentCount);

        DonateUserResume donateUserResume = donateUserService.getDonateResumeByItemId(Long.parseLong(itemId));
        if (donateUserResume != null){
            donateSpace.setDonateAmount(donateUserResume.getDonateAmount());
            donateSpace.setDonateUserCount(donateUserResume.getDonateUserCount());
        }

        CloudStorageService cloudStorageService = OSSFactory.build();
        List<File> spaceImgFiles = fileService.getByRelationId(donateSpace.getSpaceImage());
        ArrayList<String> imageFiles = new ArrayList<>();
        for (File file: spaceImgFiles){
            if (!StringUtility.isEmpty(file.getUrl())){
                imageFiles.add(cloudStorageService.generatePresignedUrl(file.getUrl()) );
            }
        }
        donateSpace.setItemImages(imageFiles);

        if (spaceImgFiles.size() > 0){
            if (!StringUtility.isEmpty(spaceImgFiles.get(0).getUrl())){
                donateSpace.setSpaceImage(cloudStorageService.generatePresignedUrl(spaceImgFiles.get(0).getUrl()));
            }
        }

        List<File> introImgFiles = fileService.getByRelationId(donateSpace.getIntroImage());
        if (introImgFiles.size() > 0){
            if (!StringUtility.isEmpty(introImgFiles.get(0).getUrl())) {
                donateSpace.setIntroImage(cloudStorageService.generatePresignedUrl(introImgFiles.get(0).getUrl()) );
            }
        }

        List<File> processImgFiles = fileService.getByRelationId(donateSpace.getProcessImage());
        if (processImgFiles.size() > 0){
            if (!StringUtility.isEmpty(processImgFiles.get(0).getUrl())) {
                donateSpace.setProcessImage(cloudStorageService.generatePresignedUrl(processImgFiles.get(0).getUrl()));
            }
        }

        return new ResponseBean(false,"success", null, donateSpace);
    }

    /***
     * 项目介绍
     * @param itemId
     * @return
     */
    @RequestMapping(value = "/donateintro/{itemId}",method = RequestMethod.GET)
    public ResponseBean donateintro(@PathVariable("itemId") String itemId){
        HashMap map = new HashMap();
        map.put("itemId",itemId);
        List donateintroList= donateSpaceService.getdonateintro(map);
        return new ResponseBean(false,"success", null, donateintroList);
    }

    /***
     * 项目进展
     * @param itemId
     * @return
     */
    @RequestMapping(value = "/donateprocess/{itemId}",method = RequestMethod.GET)
    public ResponseBean donateprocess(@PathVariable("itemId") String itemId){
        HashMap map = new HashMap();
        map.put("itemId",itemId);
        List donateprocessList= donateSpaceService.getdonateprocess(map);
        return new ResponseBean(false,"success", null, donateprocessList);
    }

    /***
     * 评论列表
     * @param itemId
     * @return
     */
    @RequestMapping(value = "/donatereviewlist/{itemId}",method = RequestMethod.GET)
    public ResponseBean donatereviewlist(@PathVariable("itemId") String itemId){
        HashMap map = new HashMap();
        map.put("itemId",itemId);
        List<Map> donateprocessList= donateSpaceService.getdonatereviewlist(map);
        if(donateprocessList.size() == 0){
            return new ResponseBean(false,"failed", "没有此项目", null);
        }
        CloudStorageService cloudStorageService = OSSFactory.build();
        for (Map donatemap : donateprocessList) {
            donatemap.put("avatar",cloudStorageService.generatePresignedUrl((String) donatemap.get("avatar")));
        }
        return new ResponseBean(false,"success", null, donateprocessList);
    }

    /***
     * 捐款人列表
     * @param itemId
     * @return
     */
    @RequestMapping(value = "/donateUsers/{itemId}", method = RequestMethod.GET)
    public ResponseBean donateUsers(@PathVariable("itemId") String itemId){
        Map<String, Object> search = new HashMap<>();
        search.put("itemId", itemId);
        List<DonateUser> donateUsers = donateUserService.getListGroupByItemId(search);
        if(donateUsers.size() == 0){
            return new ResponseBean(false,"failed", "没有此项目", null);
        }
        for (DonateUser donateUser: donateUsers){
            donateUser.setAvatar(sysUserService.getAvatar(donateUser.getAvatar()));
        }
        return new ResponseBean(false,"success", null, donateUsers);
    }

    @RequestMapping(value = "/totalResume/{spaceId}", method = RequestMethod.GET)
    public ResponseBean totalResume(@PathVariable("spaceId") String spaceId, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();

        Double totalDonateAmount = donateUserService.getTotalDonateAmount(null, spaceId);
        result.put("totalAmount", totalDonateAmount);

        Map<String, Object> search = new HashMap<>();
        search.put("state", "1");
        search.put("spaceId", spaceId);
        Integer totalItemCount = donateSpaceService.getCount(search);
        result.put("totalItemCount", totalItemCount);

        result.put("area", 34578235);

        return new ResponseBean(false,"success", null, result);
    }

    @RequestMapping(value = "/featuredItems/{spaceId}", method = RequestMethod.GET)
    public ResponseBean featuredItems(@PathVariable("spaceId") String spaceId, HttpServletRequest request){

        List<Commpara> itemTypes = commparaService.getListByCode("donateSpaceType");

        if (StringUtility.isEmpty(spaceId)) {
            spaceId = "0";
        }

        CloudStorageService cloudStorageService = OSSFactory.build();
        List<Map<String, Object>> freaturedItems = new ArrayList<>();
        for (Commpara itemType: itemTypes) {
            Map<String, Object> data = new HashMap<>();
            data.put("itemType", itemType.getParaName());
            data.put("itemTypeCode", itemType.getParaKey());
            List<DSpaceListItem> items = donateSpaceService.getFeaturedItemList(itemType.getParaKey(), spaceId);
            for (DSpaceListItem dSpaceListItem: items) {
                if (!StringUtility.isEmpty(dSpaceListItem.getFeturedImage())) {
                    dSpaceListItem.setFeturedImage(cloudStorageService.generatePresignedUrl(dSpaceListItem.getFeturedImage()));
                }
            }
            data.put("items", items);
            freaturedItems.add(data);
        }

        return new ResponseBean(false,"success", null, freaturedItems);
    }

    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    public ResponseBean featuredItems(@RequestParam Map<String, Object> params){

        Long itemId = Long.parseLong(params.get("itemId").toString());
        String anonymous = params.get("anonymous").toString();
        Long userId = null;
        if (params.get("userId") != null) {
            if (!StringUtility.isEmpty(params.get("userId").toString())){
                userId = Long.parseLong(params.get("userId").toString());
            }
        }
        Double amount = Double.parseDouble(params.get("amount").toString());

        DonateUser donateUser = new DonateUser();
        donateUser.setItemId(itemId);
        donateUser.setUserId(userId);
        donateUser.setDonateAmount(amount);
        donateUser.setAnonymous(anonymous);
        donateUserService.save(donateUser);

        YunXinUtil yunXinUtil = new YunXinUtil();
        DonateSpace donateSpace = donateSpaceService.get(itemId);
        if(donateSpace==null){
            return new ResponseBean(false,"failed", "没有此项目", null);
        }
        IntegralConfig integralConfig = IntegralConfigFactory.build();
        Integral integral = new Integral();
        integral.setArticleTypeCode(Article.DONATE_SPACE);
        integral.setTitle("捐赠" + donateSpace.getTitle() + "爱心空间");
        integral.setArticleId(itemId);
        integral.setIntegral(Double.toString(amount * integralConfig.getDonateIntegralRate()));
        integral.setUserId(userId);
        integralService.save(integral);
        try {
            yunXinUtil.sendSinglePush(integral.getUserId(), "获得积分"+integral.getTitle());
        } catch (Exception e){
        }

        return new ResponseBean(false,"success", null, null);
    }

}
