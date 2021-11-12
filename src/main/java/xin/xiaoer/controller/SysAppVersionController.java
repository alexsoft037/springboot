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

package xin.xiaoer.controller;

import com.google.gson.Gson;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import xin.xiaoer.common.exception.MyException;
import xin.xiaoer.common.integralConfig.IntegralConfig;
import xin.xiaoer.common.log.SysLog;
import xin.xiaoer.common.oss.CloudStorageService;
import xin.xiaoer.common.oss.OSSFactory;
import xin.xiaoer.common.utils.*;
import xin.xiaoer.common.validator.ValidatorUtils;
import xin.xiaoer.entity.AndroidVersion;
import xin.xiaoer.entity.File;
import xin.xiaoer.entity.IphoneVersion;
import xin.xiaoer.service.SysConfigService;

import java.util.Date;
import java.util.UUID;

/**
 * 文件上传
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-25 12:13:26
 */
@Controller
@RequestMapping("sys/appVersion")
public class SysAppVersionController {

    @Autowired
    private SysConfigService sysConfigService;

    private final static String ANDROID_VERSION_KEY = ConfigConstant.ANDROID_VERSION_CONFIG_KEY;

    private final static String IPHONE_VERSION_KEY = ConfigConstant.IPHONE_VERSION_CONFIG_KEY;

    /**
     * 云存储配置信息
     */
    @RequestMapping("/config")
    @RequiresPermissions("sys:appVersion:all")
    public String config(Model model) {
        AndroidVersion androidVersion = sysConfigService.getConfigClassObject(ANDROID_VERSION_KEY, AndroidVersion.class);
        IphoneVersion iphoneVersion = sysConfigService.getConfigClassObject(IPHONE_VERSION_KEY, IphoneVersion.class);
        model.addAttribute("android", androidVersion);
        model.addAttribute("iphone", iphoneVersion);
        return "/appVersion/edit";
    }


    /**
     * 保存云存储配置信息
     */
    @ResponseBody
    @SysLog("APP Android版本管理保存")
    @RequestMapping("/saveAndroidConfig")
    @RequiresPermissions("sys:appVersion:all")
    public R saveAndroidConfig(@RequestBody AndroidVersion config) {
        //校验类型
        ValidatorUtils.validateEntity(config);
        sysConfigService.updateValueByKey(ANDROID_VERSION_KEY, new Gson().toJson(config));

        return R.ok();
    }

    /**
     * 保存云存储配置信息
     */
    @ResponseBody
    @SysLog("APP IOS版本管理保存")
    @RequestMapping("/saveIphoneConfig")
    @RequiresPermissions("sys:appVersion:all")
    public R saveIphoneConfig(@RequestBody IphoneVersion config) {
        //校验类型
        ValidatorUtils.validateEntity(config);
        sysConfigService.updateValueByKey(IPHONE_VERSION_KEY, new Gson().toJson(config));

        return R.ok();
    }

    /**
     * layui文件上传
     */
    //就上传到项目的static文件夹下
    @ResponseBody
    @RequestMapping("/upload")
    public R upload(@RequestParam("file") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw new MyException("上传文件不能为空");
        }
        String filePath = PathUtil.getClassResources() + Const.FILEPATH_STATIC + Const.FILEPATH_UPLOAD;
        String fileName = "shaoer";

        FileUpload.fileUp(file, filePath, fileName);

        String fullUrl = Const.FILEPATH_STATIC + Const.FILEPATH_UPLOAD + fileName + ".apk";

        return R.ok().put("url", fullUrl);
    }
}
