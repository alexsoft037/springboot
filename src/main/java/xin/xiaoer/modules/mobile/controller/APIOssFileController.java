package xin.xiaoer.modules.mobile.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;
import xin.xiaoer.modules.comment.service.CommentService;
import xin.xiaoer.modules.review.service.ReviewService;
import xin.xiaoer.service.FileService;

@RestController
@RequestMapping("mobile/ossFile")
@ApiIgnore
public class APIOssFileController {
    @Autowired
    private FileService fileService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private ReviewService reviewService;

//    @RequestMapping("/upload")
//    public ResponseBean upload(@RequestParam Map<String, Object> params) throws IOException {
//
//        List<File> fileList = fileService.getList(null);
//        for (File file: fileList) {
//            if (StringUtils.isBlank(file.getOssYn()) || !file.getOssYn().equals("Y")){
//                String localFilePath = PathUtil.getClassResources() + Const.FILEPATH_STATIC + file.getUrl();
//                java.io.File localFile = new java.io.File(localFilePath);
//                if (localFile.exists()) {
//                    String suffix = localFile.getName().substring(localFile.getName().lastIndexOf("."));
//                    CloudStorageService cloudStorageService = OSSFactory.build();
//                    String url = cloudStorageService.uploadSuffix(new FileInputStream(localFile), suffix);
//                    file.setOssYn("Y");
//                    file.setUrl(url);
//                    fileService.update(file);
//                } else {
//                    fileService.delete(file.getId());
//                }
//            }
//        }
//
//        return new ResponseBean(false,"success", null, fileList);
//    }
//
//    @RequestMapping("/deleteAll")
//    public ResponseBean deleteAll(@RequestParam Map<String, Object> params) throws IOException {
//
//        List<File> fileList = fileService.getList(null);
//        for (File file: fileList) {
//            fileService.delete(file.getId());
//        }
//
//        return new ResponseBean(false,"success", null, fileList);
//    }
}
