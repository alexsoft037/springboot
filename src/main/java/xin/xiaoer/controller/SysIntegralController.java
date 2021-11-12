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
import xin.xiaoer.common.integralConfig.IntegralConfig;
import xin.xiaoer.common.log.SysLog;
import xin.xiaoer.common.oss.CloudStorageConfig;
import xin.xiaoer.common.utils.ConfigConstant;
import xin.xiaoer.common.utils.R;
import xin.xiaoer.common.validator.ValidatorUtils;
import xin.xiaoer.service.SysConfigService;

/**
 * 文件上传
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-25 12:13:26
 */
@Controller
@RequestMapping("sys/integral")
public class SysIntegralController {

    @Autowired
    private SysConfigService sysConfigService;

    private final static String KEY = ConfigConstant.INTEGRAL_CONFIG_KEY;

    /**
     * 云存储配置信息
     */
    @RequestMapping("/config")
    @RequiresPermissions("sys:integral:all")
    public String config(Model model) {
        IntegralConfig config = sysConfigService.getConfigClassObject(KEY, IntegralConfig.class);
		model.addAttribute("model",config);
		return "/integralConfig/edit";
    }


	/**
	 * 保存云存储配置信息
	 */
	@ResponseBody
	@RequestMapping("/saveConfig")
	@SysLog("积分管理修改")
	@RequiresPermissions("sys:integral:all")
	public R saveConfig(@RequestBody IntegralConfig config){
		//校验类型
		ValidatorUtils.validateEntity(config);
		sysConfigService.updateValueByKey(KEY, new Gson().toJson(config));

		return R.ok();
	}
}
