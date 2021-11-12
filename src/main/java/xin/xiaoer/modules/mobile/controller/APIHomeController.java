package xin.xiaoer.modules.mobile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;
import org.springframework.web.bind.annotation.*;
import xin.xiaoer.common.enumresource.StateEnum;
import xin.xiaoer.common.oss.CloudStorageService;
import xin.xiaoer.common.oss.OSSFactory;
import xin.xiaoer.common.utils.StringUtility;
import xin.xiaoer.entity.File;
import xin.xiaoer.entity.SpaceNews;
import xin.xiaoer.modules.activity.entity.Activity;
import xin.xiaoer.modules.activity.service.ActivityService;
import xin.xiaoer.modules.activityreport.entity.ActivityReport;
import xin.xiaoer.modules.activityreport.service.ActivityReportService;
import xin.xiaoer.modules.advert.entity.XeAdvert;
import xin.xiaoer.modules.advert.service.XeAdvertService;
import xin.xiaoer.modules.book.entity.Book;
import xin.xiaoer.modules.book.service.BookService;
import xin.xiaoer.modules.classroom.entity.SharingLesson;
import xin.xiaoer.modules.classroom.entity.StudyRoom;
import xin.xiaoer.modules.classroom.service.SharingLessonService;
import xin.xiaoer.modules.classroom.service.StudyRoomService;
import xin.xiaoer.modules.donatespace.entity.DonateSpace;
import xin.xiaoer.modules.donatespace.service.DonateSpaceService;
import xin.xiaoer.modules.mobile.bean.ResponseBean;
import xin.xiaoer.modules.servicealliance.entity.ServiceAlliance;
import xin.xiaoer.modules.servicealliance.service.ServiceAllianceService;
import xin.xiaoer.modules.spacehaedline.entity.SpaceHeadline;
import xin.xiaoer.modules.spacehaedline.service.SpaceHeadlineService;
import xin.xiaoer.modules.story.entity.SpaceStory;
import xin.xiaoer.modules.story.service.SpaceStoryService;
import xin.xiaoer.modules.xebanner.entity.XeBanner;
import xin.xiaoer.modules.xebanner.service.XeBannerService;
import xin.xiaoer.service.FileService;
import xin.xiaoer.service.SpaceNewsService;
import xin.xiaoer.service.SysUserService;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("mobile/home")
@ApiIgnore
public class APIHomeController {

    @Autowired
    private XeBannerService xeBannerService;

    @Autowired
    private XeAdvertService advertService;

    @Autowired
    private FileService fileService;

    @Autowired
    private ServiceAllianceService serviceAllianceService;

    @Autowired
    private ActivityService activityService;

    @Autowired
    private ActivityReportService activityReportService;

    @Autowired
    private DonateSpaceService donateSpaceService;

    @Autowired
    private SpaceHeadlineService spaceHeadlineService;

    @Autowired
    private SpaceStoryService spaceStoryService;

    @Autowired
    private BookService bookService;

    @Autowired
    private SharingLessonService sharingLessonService;

    @Autowired
    private StudyRoomService studyRoomService;

    @Autowired
    private SpaceNewsService spaceNewsService;

    @Autowired
    SysUserService sysUserService;

    @RequestMapping(value = "/banner", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseBean banner(@RequestParam Map<String, Object> params)throws IOException {

        params.put("bnTypeCode", "BNT001");
        params.put("state", "1");
        List<XeBanner> xeBanners = xeBannerService.getList(params);

        CloudStorageService cloudStorageService = OSSFactory.build();
        for (XeBanner xeBanner: xeBanners){
            String articleType = xeBanner.getArticleType();

            if (xeBanner.getArticleId() != null) {
                if (articleType == null){

                } else if (articleType.equals("AT0001")) {
                    DonateSpace donateSpace = donateSpaceService.get(xeBanner.getArticleId());
                    if (donateSpace != null){
                        if (!donateSpace.getState().equals(StateEnum.ENABLE.getCode())){
                            xeBanners.remove(xeBanner);
                            continue;
                        }
                        xeBanner.setArticleName(donateSpace.getTitle());
                    }
                } else if (articleType.equals("AT0002")) {
                    SpaceHeadline spaceHeadline = spaceHeadlineService.get(Integer.parseInt(xeBanner.getArticleId().toString()));
                    if (spaceHeadline != null) {
                        if (!spaceHeadline.getState().equals(StateEnum.ENABLE.getCode())){
                            xeBanners.remove(xeBanner);
                            continue;
                        }
                        xeBanner.setArticleName(spaceHeadline.getTitle());
                    }
                } else if (articleType.equals("AT0003")) {
                    ActivityReport activityReport = activityReportService.get(Integer.parseInt(xeBanner.getArticleId().toString()));
                    if (activityReport != null){
                        if (!activityReport.getState().equals(StateEnum.ENABLE.getCode())){
                            xeBanners.remove(xeBanner);
                            continue;
                        }
                        xeBanner.setArticleName(activityReport.getTitle());
                    }
                } else if (articleType.equals("AT0004")) {
                    Activity activity = activityService.get(Integer.parseInt(xeBanner.getArticleId().toString()));
                    activity = activityService.checkExpired(activity);
                    if (activity != null){
                        if (!activity.getState().equals(StateEnum.ENABLE.getCode())){
                            xeBanners.remove(xeBanner);
                            continue;
                        }
                        xeBanner.setArticleName(activity.getTitle());
                        xeBanner.setArticleStatus(activity.getActivityStatusCode());
                    }
                } else if (articleType.equals("AT0005")) {
                    SpaceStory spaceStory = spaceStoryService.get(xeBanner.getArticleId());
                    if (spaceStory != null){
                        if (!spaceStory.getState().equals(StateEnum.ENABLE.getCode())){
                            xeBanners.remove(xeBanner);
                            continue;
                        }
                        xeBanner.setArticleName(spaceStory.getTitle());
                    }
                } else if (articleType.equals("AT0006")) {
                    Book book = bookService.get(xeBanner.getArticleId());
                    if (book != null){
                        if (!book.getState().equals(StateEnum.ENABLE.getCode())){
                            xeBanners.remove(xeBanner);
                            continue;
                        }
                        xeBanner.setArticleName(book.getSubject());
                    }
                } else if (articleType.equals("AT0008")) {
                    SharingLesson sharingLesson = sharingLessonService.get(Integer.parseInt(xeBanner.getArticleId().toString()));
                    if (sharingLesson != null){
                        if (!sharingLesson.getState().equals(StateEnum.ENABLE.getCode())){
                            xeBanners.remove(xeBanner);
                            continue;
                        }
                        xeBanner.setArticleName(sharingLesson.getTitle());
                    }
                } else if (articleType.equals("AT0009")) {
                    StudyRoom studyRoom = studyRoomService.get(Integer.parseInt(xeBanner.getArticleId().toString()));
                    if (studyRoom != null){
                        if (!studyRoom.getState().equals(StateEnum.ENABLE.getCode())){
                            xeBanners.remove(xeBanner);
                            continue;
                        }
                        xeBanner.setArticleName(studyRoom.getTitle());
                        xeBanner.setArticleStatus(studyRoom.getSrStatusCode());
                    }
                }
            }

            if (!StringUtility.isEmpty(xeBanner.getBnUrl())){
                List<File> files = fileService.getByRelationId(xeBanner.getBnUrl());
                if (files.size() > 0){
                    xeBanner.setBnUrl(cloudStorageService.generatePresignedUrl(files.get(0).getUrl()));
                } else {
                    xeBanner.setBnUrl("");
                }
            }
            if (!xeBanner.getBnLink().contains("http")){
                if (StringUtility.isNotEmpty(xeBanner.getBnLink())){
                    xeBanner.setBnLink(cloudStorageService.generatePresignedUrl(xeBanner.getBnLink()));
                }
            }
        }

        return new ResponseBean(false,"success", null, xeBanners);
    }

    @RequestMapping(value = "/advert", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseBean advert(@RequestParam Map<String, Object> params)throws IOException {

        params.put("state", "1");

        List<XeAdvert> xeAdverts = advertService.getList(params);
        CloudStorageService cloudStorageService = OSSFactory.build();
        for (XeAdvert xeAdvert: xeAdverts){
            String articleType = xeAdvert.getArticleType();

            if (xeAdvert.getArticleId() != null) {
                if (articleType == null){

                } else if (articleType.equals("AT0001")) {
                    DonateSpace donateSpace = donateSpaceService.get(xeAdvert.getArticleId());
                    if (donateSpace != null){
                        if (!donateSpace.getState().equals(StateEnum.ENABLE.getCode())){
                            xeAdverts.remove(xeAdvert);
                            continue;
                        }
                        xeAdvert.setArticleName(donateSpace.getTitle());
                    }
                } else if (articleType.equals("AT0002")) {
                    SpaceHeadline spaceHeadline = spaceHeadlineService.get(Integer.parseInt(xeAdvert.getArticleId().toString()));
                    if (spaceHeadline != null) {
                        if (!spaceHeadline.getState().equals(StateEnum.ENABLE.getCode())){
                            xeAdverts.remove(xeAdvert);
                            continue;
                        }
                        xeAdvert.setArticleName(spaceHeadline.getTitle());
                    }
                } else if (articleType.equals("AT0003")) {
                    ActivityReport activityReport = activityReportService.get(Integer.parseInt(xeAdvert.getArticleId().toString()));
                    if (activityReport != null){
                        if (!activityReport.getState().equals(StateEnum.ENABLE.getCode())){
                            xeAdverts.remove(xeAdvert);
                            continue;
                        }
                        xeAdvert.setArticleName(activityReport.getTitle());
                    }
                } else if (articleType.equals("AT0004")) {
                    Activity activity = activityService.get(Integer.parseInt(xeAdvert.getArticleId().toString()));
                    activity = activityService.checkExpired(activity);
                    if (activity != null){
                        if (!activity.getState().equals(StateEnum.ENABLE.getCode())){
                            xeAdverts.remove(xeAdvert);
                            continue;
                        }
                        xeAdvert.setArticleName(activity.getTitle());
                        xeAdvert.setArticleStatus(activity.getActivityStatusCode());
                    }
                } else if (articleType.equals("AT0005")) {
                    SpaceStory spaceStory = spaceStoryService.get(xeAdvert.getArticleId());
                    if (spaceStory != null){
                        if (!spaceStory.getState().equals(StateEnum.ENABLE.getCode())){
                            xeAdverts.remove(xeAdvert);
                            continue;
                        }
                        xeAdvert.setArticleName(spaceStory.getTitle());
                    }
                } else if (articleType.equals("AT0006")) {
                    Book book = bookService.get(xeAdvert.getArticleId());
                    if (book != null){
                        if (!book.getState().equals(StateEnum.ENABLE.getCode())){
                            xeAdverts.remove(xeAdvert);
                            continue;
                        }
                        xeAdvert.setArticleName(book.getSubject());
                    }
                } else if (articleType.equals("AT0008")) {
                    SharingLesson sharingLesson = sharingLessonService.get(Integer.parseInt(xeAdvert.getArticleId().toString()));
                    if (sharingLesson != null){
                        if (!sharingLesson.getState().equals(StateEnum.ENABLE.getCode())){
                            xeAdverts.remove(xeAdvert);
                            continue;
                        }
                        xeAdvert.setArticleName(sharingLesson.getTitle());
                    }
                } else if (articleType.equals("AT0009")) {
                    StudyRoom studyRoom = studyRoomService.get(Integer.parseInt(xeAdvert.getArticleId().toString()));
                    if (studyRoom != null){
                        if (!studyRoom.getState().equals(StateEnum.ENABLE.getCode())){
                            xeAdverts.remove(xeAdvert);
                            continue;
                        }
                        xeAdvert.setArticleName(studyRoom.getTitle());
                        xeAdvert.setArticleStatus(studyRoom.getSrStatusCode());
                    }
                }
            }
            if (!StringUtility.isEmpty(xeAdvert.getAdImagePath())){
                List<File> files = fileService.getByRelationId(xeAdvert.getAdImagePath());
                if (files.size() > 0){
                    xeAdvert.setAdImagePath(cloudStorageService.generatePresignedUrl(files.get(0).getUrl()));
                } else {
                    xeAdvert.setAdImagePath("");
                }
            }
        }

        return new ResponseBean(false,"success", null, xeAdverts);
    }

    @RequestMapping(value = "/livecount/{spaceId}", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseBean visitorCount(@PathVariable("spaceId") String spaceId)throws IOException {
        Map<String, Object> map = new HashMap<>();
        int onlineTotal = sysUserService.queryOnlineTotal();
        map.put("visitCount", onlineTotal);
        Map<String, Object> search = new HashMap<>();

        if (StringUtility.isEmpty(spaceId)) {
            spaceId = "0";
        }
        search.put("spaceId", spaceId);
        List<SpaceNews> news = spaceNewsService.getNewsList(search);
        map.put("news", news);
        return new ResponseBean(false,"success", null, map);
    }

    @RequestMapping(value = "/fuwulianmeng", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseBean fuwulianmeng(@RequestParam Map<String, Object> params)throws IOException {

        params.put("state", "1");
        List<ServiceAlliance> serviceAlliances = serviceAllianceService.getList(params);

        CloudStorageService cloudStorageService = OSSFactory.build();
        for (ServiceAlliance serviceAlliance: serviceAlliances){
            if (!StringUtility.isEmpty(serviceAlliance.getImgPath())){
                List<File> files = fileService.getByRelationId(serviceAlliance.getImgPath());
                if (files.size() > 0){
                    serviceAlliance.setImgPath(cloudStorageService.generatePresignedUrl(files.get(0).getUrl()));
                } else {
                    serviceAlliance.setImgPath("");
                }
            }
            if (!StringUtility.isEmpty(serviceAlliance.getContent())){
                List<File> files = fileService.getByRelationId(serviceAlliance.getContent());
                if (files.size() > 0){
                    serviceAlliance.setContent(cloudStorageService.generatePresignedUrl(files.get(0).getUrl()));
                } else {
                    serviceAlliance.setContent("");
                }
            }
        }

        return new ResponseBean(false,"success", null, serviceAlliances);
    }

    @RequestMapping(value = "/fuwulianmeng/{id}", method = RequestMethod.GET)
    public ResponseBean fuwulianmengDetail(@PathVariable("id") String id) throws IOException {

        ServiceAlliance serviceAlliance = serviceAllianceService.get(Integer.parseInt(id));
        if (!serviceAlliance.getState().equals("1")) {
            return new ResponseBean(false, ResponseBean.FAILED, "空", null);
        }

        CloudStorageService cloudStorageService = OSSFactory.build();
        if (!StringUtility.isEmpty(serviceAlliance.getImgPath())) {
            List<File> files = fileService.getByRelationId(serviceAlliance.getImgPath());
            if (files.size() > 0){
                serviceAlliance.setImgPath(cloudStorageService.generatePresignedUrl(files.get(0).getUrl()));
            } else {
                serviceAlliance.setImgPath("");
            }
        }
        if (!StringUtility.isEmpty(serviceAlliance.getContent())){
            List<File> files = fileService.getByRelationId(serviceAlliance.getContent());
            if (files.size() > 0){
                serviceAlliance.setContent(cloudStorageService.generatePresignedUrl(files.get(0).getUrl()));
            } else {
                serviceAlliance.setContent("");
            }
        }

        return new ResponseBean(false, "success", null, serviceAlliance);
    }
}
