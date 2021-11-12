/**
 * Copyright 2018 人人开源 http://www.renren.io
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package xin.xiaoer.common.oss;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.DeleteObjectsRequest;
import com.aliyun.oss.model.DeleteObjectsResult;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import xin.xiaoer.common.utils.DateUtil;
import xin.xiaoer.common.utils.RRException;
import xin.xiaoer.common.utils.StringUtil;
import xin.xiaoer.common.utils.StringUtility;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 阿里云存储
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-26 16:22
 */
public class AliyunCloudStorageService extends CloudStorageService {
    private OSSClient client;

    public AliyunCloudStorageService(CloudStorageConfig config){
        this.config = config;

        //初始化
        init();
    }

    private void init(){
        client = new OSSClient(config.getAliyunEndPoint(), config.getAliyunAccessKeyId(),
                config.getAliyunAccessKeySecret());
    }

    @Override
    public String upload(byte[] data, String path) {
        return upload(new ByteArrayInputStream(data), path);
    }

    @Override
    public String upload(InputStream inputStream, String path) {
        try {
            client.putObject(config.getAliyunBucketName(), path, inputStream);
        } catch (Exception e){
            throw new RRException("上传文件失败，请检查配置信息", e);
        }

//        config.getAliyunDomain() +
        return path;
    }

    @Override
    public String uploadSuffix(byte[] data, String suffix) {
        return upload(data, getPath(config.getAliyunPrefix(), suffix));
    }

    @Override
    public String uploadSuffix(InputStream inputStream, String suffix) {
        return upload(inputStream, getPath(config.getAliyunPrefix(), suffix));
    }

    @Override
    public String generatePresignedUrl(String key){
        if (StringUtil.isEmpty(key)){
            return "";
        }
//        if (!client.doesObjectExist(config.getAliyunBucketName(), key)){
//            return "";
//        }
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow  = calendar.getTime();

        URL path = client.generatePresignedUrl(config.getAliyunBucketName(), key, tomorrow);
        return path.toString();
    }

    public String generatePresignedUrl(String key, String with, String height){
        if (StringUtil.isEmpty(key)){
            return "";
        }
//        if (!client.doesObjectExist(config.getAliyunBucketName(), key)){
//            return "";
//        }
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow  = calendar.getTime();
        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(config.getAliyunBucketName(), key);
        String style = "image/crop,g_center,w_"+with+",h_"+height;
        request.setProcess(style);
        request.setExpiration(tomorrow);
        URL cropKey = client.generatePresignedUrl(request);
        return cropKey.toString();
    }

    @Override
    public String generatePresignedUrl(String key, Integer afterYears){
        if (StringUtil.isEmpty(key)){
            return "";
        }
//        if (!client.doesObjectExist(config.getAliyunBucketName(), key)){
//            return "";
//        }
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, afterYears);
        Date tomorrow  = calendar.getTime();
        URL path = client.generatePresignedUrl(config.getAliyunBucketName(), key, tomorrow);
        return path.toString();
    }

    @Override
    public String generatePresignedUrlWithResize(String key, String with, String height){
        if (StringUtil.isEmpty(key)){
            return "";
        }
//        if (!client.doesObjectExist(config.getAliyunBucketName(), key)){
//            return "";
//        }
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow  = calendar.getTime();
        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(config.getAliyunBucketName(), key);
        String style = "image/resize,m_fill,w_"+with+",h_"+height;
        request.setProcess(style);
        request.setExpiration(tomorrow);
        URL cropKey = client.generatePresignedUrl(request);
        return cropKey.toString();
    }

    public String generatePresignedUrlByWith(String key, String with) {
        if (StringUtil.isEmpty(key)){
            return "";
        }
//        if (!client.doesObjectExist(config.getAliyunBucketName(), key)){
//            return "";
//        }
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow  = calendar.getTime();
        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(config.getAliyunBucketName(), key);
        String style = "image/resize,w_"+with;
        request.setProcess(style);
        request.setExpiration(tomorrow);
        URL cropKey = client.generatePresignedUrl(request);
        return cropKey.toString();
    }

    public String generatePresignedUrlByHeight(String key, String height){
        if (StringUtil.isEmpty(key)){
            return "";
        }
//        if (!client.doesObjectExist(config.getAliyunBucketName(), key)){
//            return "";
//        }
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow  = calendar.getTime();
        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(config.getAliyunBucketName(), key);
        String style = "image/resize,h_"+height;
        request.setProcess(style);
        request.setExpiration(tomorrow);
        URL cropKey = client.generatePresignedUrl(request);
        return cropKey.toString();
    }

    @Override
    public String videoThumbnail(String key, String with, String height){
        if (StringUtil.isEmpty(key)){
            return "";
        }
//        if (!client.doesObjectExist(config.getAliyunBucketName(), key)){
//            return "";
//        }
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date tomorrow  = calendar.getTime();
        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(config.getAliyunBucketName(), key);
        String style = "video/snapshot,t_1000,f_jpg,w_" + with + ",h_" + height + ",m_fast";
        request.setProcess(style);
        request.setExpiration(tomorrow);
        URL cropKey = client.generatePresignedUrl(request);
        return cropKey.toString();
    }

    @Override
    public String getFullUrl(String key) {
        return config.getAliyunDomain() + "/" +key;
    }

    @Override
    public void deleteObject(String key){
        try {
            client.deleteObject(config.getAliyunBucketName(), key);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<String> deleteObjects(List<String> keys){
        try {
            DeleteObjectsResult deleteObjectsResult = client.deleteObjects(new DeleteObjectsRequest(config.getAliyunBucketName()).withKeys(keys));
            return deleteObjectsResult.getDeletedObjects();
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
