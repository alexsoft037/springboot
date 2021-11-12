package xin.xiaoer.controller;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xin.xiaoer.common.enumresource.StateEnum;
import xin.xiaoer.common.exception.MyException;
import xin.xiaoer.common.oss.CloudStorageService;
import xin.xiaoer.common.oss.OSSFactory;
import xin.xiaoer.common.shiro.ShiroUtils;
import xin.xiaoer.common.utils.*;
import xin.xiaoer.entity.Commpara;
import xin.xiaoer.entity.File;
import xin.xiaoer.entity.SysOss;
import xin.xiaoer.entity.TextChapter;
import xin.xiaoer.modules.book.dao.BookChapterDao;
import xin.xiaoer.modules.book.entity.Book;
import xin.xiaoer.modules.book.entity.BookChapter;
import xin.xiaoer.modules.book.service.BookChapterService;
import xin.xiaoer.service.CommparaService;
import xin.xiaoer.service.FileService;
import xin.xiaoer.service.SysOssService;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.*;
import java.util.UUID;

/**
 * Created by 陈熠
 * 2017/7/19.
 */
@RestController
@RequestMapping("/getData")
public class GetDataController {
    @Autowired
    private CommparaService commparaService;

    @Autowired
    private FileService fileService;

    @Autowired
    private SysOssService ossService;

    @Autowired
    private FastDFSClientWrapper dfsClient;

    @Autowired
    private BookChapterService bookChapterService;

    /**
     * @param
     * @author chenyi
     * @Description 通过枚举获取数据列表
     * @date date 2017-7-20
     */
    @ResponseBody
    @RequestMapping("/getEnum")
    public R getEnum(@RequestParam Map<String, Object> params) throws Exception {
        List<EnumBean> values = new ArrayList<>();
        String enumName = (String) params.get("enumName");
        if (enumName != null && !"".equals(enumName)) {
            Class clzz = null;
            try {
                clzz = Class.forName(Constant.PACKAGE_NAME + "." + enumName);
                Method method = clzz.getMethod("values");
                EnumMessage inter[] = (EnumMessage[]) method.invoke(new Object[]{}, new Object[]{});
                for (EnumMessage enumMessage : inter) {
                    EnumBean e = new EnumBean();
                    e.setCode(enumMessage.getCode());
                    e.setValue(enumMessage.getValue());
                    values.add(e);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return R.ok().put("data", values);
    }

    /**
     * @param
     * @author chenyi
     * @Description 通过表码获取数据列表
     * @date date 2017-7-20
     */
    @ResponseBody
    @RequestMapping("/getCodeValues")
    public R getCodeValues(@RequestParam Map<String, Object> params) {
        List<Commpara> sysCodeList = null;
        if (params.get("codeName") != null && !"".equals(params.get("codeName"))) {
            sysCodeList = commparaService.getCodeValues(params);
        }
        return R.ok().put("data", sysCodeList);
    }

    /**
     * @param
     * @author chenyi
     * @Description oss文件上传
     * @date date 2017-7-20
     */
    public String uploadImage(String fileName, InputStream inputStream) {
        //获取oss
        Map<String, Object> params = new HashMap<>();
        params.put("state", StateEnum.ENABLE.getCode());
        List<SysOss> ossList = ossService.getList(params);
        if (ossList != null && ossList.size() > 0) {
            SysOss oss = ossList.get(0);
            String fileNameBak = fileName;
            String resultImgUrl = oss.getUrl();
            String endpoint = oss.getEndpoint();
            String accessKeyId = oss.getAccessKeyId();
            String accessKeySecret = oss.getAccessKeySecret();
            String bucket = oss.getBucket();
            OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
            if (!ossClient.doesBucketExist(bucket)) {
                ossClient.createBucket(bucket);
            }

            ObjectMetadata objectMeta = new ObjectMetadata();//oss属性设置
            //objectMeta.setContentLength(inputStream.getSize());//标记长度
            objectMeta.setContentType("image/jpeg");// 可以在metadata中标记文件类型
            try {
                //获取上传的图片文件名
                Long nanoTime = System.nanoTime();// 防止文件被覆盖，以纳秒生成图片名
                String _extName = fileName.substring(fileName.indexOf("."));//获取扩展名
                fileName = nanoTime + _extName;
//                fileName = DateUtil.getYmd() + "/" + fileName + "/" + fileNameBak;
                fileName = DateUtil.getYmd() + "/" + fileName;
                ossClient.putObject(bucket, fileName, inputStream, objectMeta);
                resultImgUrl += fileName;
            } catch (Exception e) {
                e.printStackTrace();
                throw new MyException("上传失败");
            } finally {
                ossClient.shutdown();
            }
            return resultImgUrl;
        }
        throw new MyException("未启用oss配置");
    }

    /**
     * layui文件上传
     */
    @ResponseBody
    @RequestMapping("/upload")
    public R upload(@RequestParam("file") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw new MyException("上传文件不能为空");
        }
        String fileUploadName = file.getOriginalFilename();
        String _extName = fileUploadName.substring(fileUploadName.indexOf("."), fileUploadName.length());//获取扩展名
        Long size = file.getSize();
//        String fileName = file.getOriginalFilename();
//        String _extName = fileName.substring(fileName.indexOf("."), fileName.length());//获取扩展名
//
//        if (file.getSize() > 1 * 1024 * 1024) {
//            throw new MyException("图片不能大于1M");
//        }
//        //上传文件
//        String url = uploadImage(file.getOriginalFilename(), file.getInputStream());
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        CloudStorageService cloudStorageService = OSSFactory.build();
        String url = cloudStorageService.uploadSuffix(file.getBytes(), suffix);
        String fullUrl = cloudStorageService.generatePresignedUrl(url, 200);

        String relationId = UUID.randomUUID().toString();
        File uploadFile = new File();
        uploadFile.setUploadId(relationId);
        uploadFile.setFileName(fileUploadName);
        uploadFile.setFileSize(size.toString());
        uploadFile.setCreateTime(new Date());
        uploadFile.setUrl( url);
        uploadFile.setOssYn("Y");
        //获取文件类型
        boolean isPicture = FileUtil.isPicture(fileUploadName);
        if (isPicture) {
            uploadFile.setFileType("image");
        } else {
            uploadFile.setFileType(_extName);
        }
        if (ShiroUtils.getUserId() != 1L){
            uploadFile.setSpaceId(ShiroUtils.getSpaceId());
        }
        fileService.save(uploadFile);
        //String url = "/statics/img/timg.jpg";
//        String url = dfsClient.uploadFile(file);
        return R.ok().put("url", fullUrl);
    }

    /**
     * layui文件上传
     */
    @ResponseBody
    @RequestMapping("/uploader")
    public R uploader(@RequestParam("file") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw new MyException("上传文件不能为空");
        }
        String url = "";
        String fileOrgName = "";
        String error = "";
        String fullUrl = "";
        String fileUploadName = file.getOriginalFilename();
        String _extName = fileUploadName.substring(fileUploadName.indexOf("."), fileUploadName.length());//获取扩展名
        Long size = file.getSize();

        try {
            if (null != file && !file.isEmpty()) {
//                String ffile = DateUtil.getYmd(), fileName = "";
//
//                String filePath = PathUtil.getClassResources() + Const.FILEPATH_STATIC + Const.FILEPATH_UPLOAD + ffile;
//                String thumbnailPath = PathUtil.getClassResources() + Const.FILEPATH_STATIC + Const.FILEPATH_UPLOAD_THUMBNAIL + ffile;   //鏂囦欢涓婁紶璺緞
//                fileName = FileUpload.fileUp(file, filePath, UuidUtil.get32UUID() , thumbnailPath, 320, 320);                //鎵ц涓婁紶
//
//                url = "/" + Const.FILEPATH_UPLOAD + ffile + "/" + fileName;
//                fileOrgName = file.getOriginalFilename();
                String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
                CloudStorageService cloudStorageService = OSSFactory.build();
                url = cloudStorageService.uploadSuffix(file.getBytes(), suffix);
                fullUrl = cloudStorageService.generatePresignedUrl(url, 200);
                fileOrgName = file.getOriginalFilename();
            } else {
                error = "涓婁紶澶辫触";
            }
        } catch (Exception e) {
            error = "涓婁紶澶辫触";
            e.printStackTrace();
        }
        String relationId = UUID.randomUUID().toString();
        File uploadFile = new File();
        uploadFile.setUploadId(relationId);
        uploadFile.setFileName(fileUploadName);
        uploadFile.setFileSize(size.toString());
        uploadFile.setCreateTime(new Date());
        uploadFile.setUrl( url);
        uploadFile.setOssYn("Y");
        //获取文件类型
        boolean isPicture = FileUtil.isPicture(fileUploadName);
        if (isPicture) {
            uploadFile.setFileType("image");
        } else {
            uploadFile.setFileType(_extName);
        }
        if (ShiroUtils.getUserId() != 1L){
            uploadFile.setSpaceId(ShiroUtils.getSpaceId());
        }
        fileService.save(uploadFile);

        return R.ok().put("url", fullUrl).put("error", error).put("fileOrgName", fileOrgName);
    }

    @ResponseBody
    @RequestMapping("/ckfinder")
    public Map<String, Object> ckfinder(@RequestParam("upload") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw new MyException("上传文件不能为空");
        }
        Map<String, Object> resultMap = new HashMap<>();
        String url = "";
        String error = "";
        String fullUrl = "";
        String fileUploadName = file.getOriginalFilename();
        String _extName = fileUploadName.substring(fileUploadName.indexOf("."), fileUploadName.length());//获取扩展名
        Long size = file.getSize();
        try {
            if (null != file && !file.isEmpty()) {
//                String ffile = DateUtil.getYmd(), fileName = "";
//
//                String filePath = PathUtil.getClassResources() + Const.FILEPATH_STATIC + Const.FILEPATH_UPLOAD + ffile;
//                String thumbnailPath = PathUtil.getClassResources() + Const.FILEPATH_STATIC + Const.FILEPATH_UPLOAD_THUMBNAIL + ffile;   //鏂囦欢涓婁紶璺緞
//                fileName = FileUpload.fileUp(file, filePath, UuidUtil.get32UUID() , thumbnailPath, 320, 320);                //鎵ц涓婁紶
//
//                url = "/" + Const.FILEPATH_UPLOAD + ffile + "/" + fileName;
//                fileOrgName = file.getOriginalFilename();
                String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
                CloudStorageService cloudStorageService = OSSFactory.build();
                url = cloudStorageService.uploadSuffix(file.getBytes(), suffix);
                fullUrl = cloudStorageService.generatePresignedUrl(url, 200);
            } else {
                error = "涓婁紶澶辫触";
            }
        } catch (Exception e) {
            error = "涓婁紶澶辫触";
            e.printStackTrace();
        }
        String relationId = UUID.randomUUID().toString();
        File uploadFile = new File();
        uploadFile.setUploadId(relationId);
        uploadFile.setFileName(fileUploadName);
        uploadFile.setFileSize(size.toString());
        uploadFile.setCreateTime(new Date());
        uploadFile.setUrl( url);
        uploadFile.setOssYn("Y");
        //获取文件类型
        boolean isPicture = FileUtil.isPicture(fileUploadName);
        if (isPicture) {
            uploadFile.setFileType("image");
        } else {
            uploadFile.setFileType(_extName);
        }
        if (ShiroUtils.getUserId() != 1L){
            uploadFile.setSpaceId(ShiroUtils.getSpaceId());
        }
        fileService.save(uploadFile);
        resultMap.put("uploaded", true);
        resultMap.put("url", fullUrl);
        return resultMap;
    }
    /**
     * layui文件上传
     */
    @ResponseBody
    @RequestMapping("/uploaders")
    public R uploaders(@RequestParam("uploadFile") MultipartFile[] file, HttpServletRequest request) throws Exception {
        if (file[0].isEmpty()) {
            throw new MyException("上传文件不能为空");
        }
        String url = "";
        String fullUrl = "";
        String fileOrgName = "";
        String error = "";

        String fileUploadName = file[0].getOriginalFilename();
        String _extName = fileUploadName.substring(fileUploadName.indexOf("."), fileUploadName.length());//获取扩展名
        Long size = file[0].getSize();

        try {
            if (null != file[0] && !file[0].isEmpty()) {
//                String ffile = DateUtil.getYmd(), fileName = "";
//
//                String filePath = PathUtil.getClassResources() + Const.FILEPATH_STATIC + Const.FILEPATH_UPLOAD + ffile;
//                String thumbnailPath = PathUtil.getClassResources() + Const.FILEPATH_STATIC + Const.FILEPATH_UPLOAD_THUMBNAIL + ffile;   //鏂囦欢涓婁紶璺緞
//                fileName = FileUpload.fileUp(file[0], filePath, UuidUtil.get32UUID() , thumbnailPath, 320, 320);                //鎵ц涓婁紶
//
//                url = ffile + "/" + fileName;
//                fileOrgName = file[0].getOriginalFilename();
                String suffix = file[0].getOriginalFilename().substring(file[0].getOriginalFilename().lastIndexOf("."));
                CloudStorageService cloudStorageService = OSSFactory.build();
                url = cloudStorageService.uploadSuffix(file[0].getBytes(), suffix);
                fullUrl = cloudStorageService.generatePresignedUrl(url);
            } else {
                error = "涓婁紶澶辫触";
            }
        } catch (Exception e) {
            error = "涓婁紶澶辫触";
            e.printStackTrace();
        }

        String relationId = request.getParameter("relationId");

        File uploadFile = new File();
        uploadFile.setUploadId(relationId);
        uploadFile.setFileName(fileUploadName);
        uploadFile.setFileSize(size.toString());
        uploadFile.setCreateTime(new Date());
        uploadFile.setUrl( url);
        uploadFile.setOssYn("Y");
        //获取文件类型
        boolean isPicture = FileUtil.isPicture(fileUploadName);
        if (isPicture) {
            uploadFile.setFileType("image");
        } else {
            uploadFile.setFileType(_extName);
        }
        if (ShiroUtils.getUserId() != 1L){
            uploadFile.setSpaceId(ShiroUtils.getSpaceId());
        }
        fileService.save(uploadFile);

        return R.ok().put("url", fullUrl).put("fileId", uploadFile.getId());
    }

    @ResponseBody
    @RequestMapping("/uploadBook")
    public R uploadBook(@RequestParam("uploadFile") MultipartFile[] file, HttpServletRequest request) throws Exception {
        if (file[0].isEmpty()) {
            throw new MyException("上传文件不能为空");
        }
        String url = "";
        String fullUrl = "";
        String ossUrl = "";
        String fileOrgName = "";
        String error = "";

        String fileUploadName = file[0].getOriginalFilename();
        String _extName = fileUploadName.substring(fileUploadName.indexOf("."), fileUploadName.length());//获取扩展名
        Long size = file[0].getSize();

        try {
            if (null != file[0] && !file[0].isEmpty()) {
                String ffile = DateUtil.getYmd(), fileName = "";

                String filePath = PathUtil.getClassResources() + Const.FILEPATH_STATIC + Const.FILEPATH_UPLOAD + ffile;
//                String thumbnailPath = PathUtil.getClassResources() + Const.FILEPATH_STATIC + Const.FILEPATH_UPLOAD_THUMBNAIL + ffile;   //鏂囦欢涓婁紶璺緞
                fileName = FileUpload.fileUp(file[0], filePath, UuidUtil.get32UUID());                //鎵ц涓婁紶
                url = ffile + "/" + fileName;

                String suffix = file[0].getOriginalFilename().substring(file[0].getOriginalFilename().lastIndexOf("."));
                CloudStorageService cloudStorageService = OSSFactory.build();
                ossUrl = cloudStorageService.uploadSuffix(file[0].getBytes(), suffix);
                fullUrl = cloudStorageService.generatePresignedUrl(ossUrl);
            } else {
                error = "涓婁紶澶辫触";
            }
        } catch (Exception e) {
            error = "涓婁紶澶辫触";
            e.printStackTrace();
        }

        String relationId = request.getParameter("relationId");
        File uploadFile = new File();
        uploadFile.setUploadId(relationId);
        uploadFile.setFileName(fileUploadName);
        uploadFile.setFileSize(size.toString());
        uploadFile.setCreateTime(new Date());
        uploadFile.setUrl( ossUrl);
        uploadFile.setOssYn("Y");
        //获取文件类型
        boolean isPicture = FileUtil.isPicture(fileUploadName);
        if (isPicture) {
            uploadFile.setFileType("image");
        } else {
            uploadFile.setFileType(_extName);
        }
        if (ShiroUtils.getUserId() != 1L){
            uploadFile.setSpaceId(ShiroUtils.getSpaceId());
        }
        fileService.save(uploadFile);

        if (_extName.toLowerCase().equals(".txt")){
            TxtChapterReader chapterReader=new TxtChapterReader();
            ArrayList<TextChapter> matches = chapterReader.readerChapters(PathUtil.getClassResources() + Const.FILEPATH_STATIC + Const.FILEPATH_UPLOAD + url);
            List<BookChapter> bookChapters = bookChapterService.getListByBookFile(relationId);
            if (bookChapters.size() > 0){
                bookChapterService.deleteByBookFile(relationId);
            }
            for (TextChapter textChapter: matches) {
                BookChapter bookChapter = new BookChapter();
                bookChapter.setBookFile(relationId);
                bookChapter.setChapterIndex(textChapter.getChapter());
                bookChapter.setContent(textChapter.getContent());
                bookChapter.setTitle(textChapter.getChapterTitle());
                bookChapter.setIndex(textChapter.getChapterIndex());
                bookChapter.setTextCount(textChapter.getTextCount());
                bookChapterService.save(bookChapter);
            }
        } else if (_extName.toLowerCase().equals(".pdf")) {
        }

        return R.ok().put("url", fullUrl).put("fileId", uploadFile.getId());
    }
    /**
     * Hupload上传文件
     */
    @ResponseBody
    @RequestMapping("/uploads")
    public R uploads(@RequestParam("uploadFile") MultipartFile[] file, HttpServletRequest request) throws Exception {

        if (file[0].isEmpty()) {
            throw new MyException("上传文件不能为空");
        }
        String fileName = file[0].getOriginalFilename();
        String _extName = fileName.substring(fileName.indexOf("."), fileName.length());//获取扩展名
        Long size = file[0].getSize();
////        if (size > 1 * 1024 * 1024) {
////            throw new MyException("图片不能大于1M");
////        }
//
//        //上传文件
//        String url = uploadImage(file[0].getOriginalFilename(), file[0].getInputStream());
//        String url = dfsClient.uploadFile(file[0]);;
        String suffix = file[0].getOriginalFilename().substring(file[0].getOriginalFilename().lastIndexOf("."));
        CloudStorageService cloudStorageService = OSSFactory.build();
        String url = cloudStorageService.uploadSuffix(file[0].getBytes(), suffix);
        String fullUrl = cloudStorageService.generatePresignedUrl(url);
        //存到本地文件
        //String url = "/statics/img/timg.jpg";
        String relationId = request.getParameter("relationId");
        File uploadFile = new File();
        uploadFile.setUploadId(relationId);
        uploadFile.setFileName(fileName);
        uploadFile.setFileSize(size.toString());
        uploadFile.setCreateTime(new Date());
        uploadFile.setUrl(url);
        uploadFile.setOssYn("Y");
        //获取文件类型
        boolean isPicture = FileUtil.isPicture(fileName);
        if (isPicture) {
            uploadFile.setFileType("image");
        } else {
            uploadFile.setFileType(_extName);
        }
        if (ShiroUtils.getUserId() != 1L){
            uploadFile.setSpaceId(ShiroUtils.getSpaceId());
        }
        fileService.save(uploadFile);

        return R.ok().put("url", fullUrl).put("fileId", uploadFile.getId());
    }


    /**
     * Hupload文件回填
     **/
    @ResponseBody
    @RequestMapping("/getFile/{relationId}")
    public R getFile(@PathVariable("relationId") String relationId) {
        List<File> list = fileService.getByRelationId(relationId);
        CloudStorageService cloudStorageService = OSSFactory.build();
        for (File file: list){
            file.setUrl(cloudStorageService.generatePresignedUrl(file.getUrl()));
        }
        return R.ok().put("fileList", list);
    }

    /**
     * Hupload删除上传文件
     */
    @ResponseBody
    @RequestMapping("/deleteFile/{fileId}")
    public R uploadFile(@PathVariable("fileId") String fileId) throws Exception {
        fileService.delete(fileId);
        return R.ok();
    }

    /**
     * Hupload删除文件
     */
    @ResponseBody
    @RequestMapping("/deleteByRelationId/{relationId}")
    public R deleteByRelationId(@PathVariable("relationId") String relationId) throws Exception {
        List<File> list = fileService.getByRelationId(relationId);
        if (list.size() > 0) {
            bookChapterService.deleteByBookFile(relationId);
        }
        fileService.deleteByRelationId(relationId);
        return R.ok();
    }


}
