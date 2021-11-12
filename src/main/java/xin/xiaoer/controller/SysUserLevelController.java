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
import org.springframework.web.bind.annotation.ResponseBody;
import xin.xiaoer.common.log.SysLog;
import xin.xiaoer.common.utils.ConfigConstant;
import xin.xiaoer.common.utils.R;
import xin.xiaoer.common.validator.ValidatorUtils;
import xin.xiaoer.entity.UserLevel;
import xin.xiaoer.service.SysConfigService;

/**
 * 文件上传
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-25 12:13:26
 */
@Controller
@RequestMapping("sys/userLevel")
public class SysUserLevelController {

    @Autowired
    private SysConfigService sysConfigService;

    private final static String USER_LEVEL_CONFIG_KEY = ConfigConstant.USER_LEVEL_CONFIG_KEY;

    /**
     * 云存储配置信息
     */
    @RequestMapping("/config")
    @RequiresPermissions("sys:userLevel:all")
    public String config(Model model) {
        UserLevel userLevel = sysConfigService.getConfigClassObject(USER_LEVEL_CONFIG_KEY, UserLevel.class);
        model.addAttribute("model", userLevel);
        return "/userLevel/edit";
    }


    /**
     * 保存云存储配置信息
     */
    @ResponseBody
    @SysLog("用户积分等级关系保存")
    @RequestMapping("/saveConfig")
    @RequiresPermissions("sys:userLevel:all")
    public R saveAndroidConfig(@RequestBody UserLevel config) {
        //校验类型
        ValidatorUtils.validateEntity(config);
        sysConfigService.updateValueByKey(USER_LEVEL_CONFIG_KEY, new Gson().toJson(config));

        return R.ok();
    }
}
