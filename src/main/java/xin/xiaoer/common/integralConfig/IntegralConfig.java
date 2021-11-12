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

package xin.xiaoer.common.integralConfig;

import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 云存储配置信息
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-25 16:12
 */
public class IntegralConfig implements Serializable {
    private static final long serialVersionUID = 1L;

    //公益捐赠积分比例
    @NotBlank(message="公益捐赠积分比例", groups = IntegralConfig.class)
    private Double donateIntegralRate = 1.0;

//    //一般活动参与积分
//    @NotBlank(message="一般活动参与积分", groups = IntegralConfig.class)
//    private Double activityIntegralDefault = 40.0;

    //活动参与每周最多获得积分
    @NotBlank(message="活动参与每周最多获得积分", groups = IntegralConfig.class)
    private Double weekMaxActivityIntegral = 200.0;

    //小记者每周最多获得积分
    @NotBlank(message="小记者每周最多获得积分", groups = IntegralConfig.class)
    private Double weekMaxReporterIntegral = 200.0;

    //小记者每周最多获得积分
    @NotBlank(message="小记者每周最多获得积分", groups = IntegralConfig.class)
    private Double reporterPostIntegral = 40.0;

    //空间书架每周最多获得积分
    @NotBlank(message="空间书架每周最多获得积分", groups = IntegralConfig.class)
    private Double weekMaxBookIntegral = 200.0;

    //空间课堂直播老师每周最多获得积分
    @NotBlank(message="空间课堂直播老师每周最多获得积分", groups = IntegralConfig.class)
    private Double weekMaxLiveIntegral = 200.0;

    //空间课堂直播老师每周最多获得积分
    @NotBlank(message="空间课堂上传一个视频获得积分", groups = IntegralConfig.class)
    private Double uploadVideoIntegral = 40.0;

    //空间书架每周最多获得积分
    @NotBlank(message="空间书架每周最多获得积分", groups = IntegralConfig.class)
    private Double weekMaxSpaceShowIntegral = 200.0;

    //打卡连续一周获得积分
    @NotBlank(message="一天", groups = IntegralConfig.class)
    private Double punchIntegral1 = 1.0;

    //打卡连续一周获得积分
    @NotBlank(message="二天", groups = IntegralConfig.class)
    private Double punchIntegral2 = 2.0;

    //打卡连续一周获得积分
    @NotBlank(message="三天, 四天, 五天, 六天, 七天", groups = IntegralConfig.class)
    private Double punchIntegral3 = 3.0;

    //打卡连续一周获得积分
    @NotBlank(message="首次使用app 获得积分", groups = IntegralConfig.class)
    private Double useAppIntegral = 40.0;

//    public Double getActivityIntegralDefault() {
//        return activityIntegralDefault;
//    }
//
//    public void setActivityIntegralDefault(Double activityIntegralDefault) {
//        this.activityIntegralDefault = activityIntegralDefault;
//    }

    public Double getDonateIntegralRate() {
        return donateIntegralRate;
    }

    public void setDonateIntegralRate(Double donateIntegralRate) {
        this.donateIntegralRate = donateIntegralRate;
    }

    public Double getWeekMaxActivityIntegral() {
        return weekMaxActivityIntegral;
    }

    public void setWeekMaxActivityIntegral(Double weekMaxActivityIntegral) {
        this.weekMaxActivityIntegral = weekMaxActivityIntegral;
    }

    public Double getWeekMaxReporterIntegral() {
        return weekMaxReporterIntegral;
    }

    public void setWeekMaxReporterIntegral(Double weekMaxReporterIntegral) {
        this.weekMaxReporterIntegral = weekMaxReporterIntegral;
    }

    public Double getReporterPostIntegral() {
        return reporterPostIntegral;
    }

    public void setReporterPostIntegral(Double reporterPostIntegral) {
        this.reporterPostIntegral = reporterPostIntegral;
    }

    public Double getWeekMaxBookIntegral() {
        return weekMaxBookIntegral;
    }

    public void setWeekMaxBookIntegral(Double weekMaxBookIntegral) {
        this.weekMaxBookIntegral = weekMaxBookIntegral;
    }

    public Double getWeekMaxSpaceShowIntegral() {
        return weekMaxSpaceShowIntegral;
    }

    public void setWeekMaxSpaceShowIntegral(Double weekMaxSpaceShowIntegral) {
        this.weekMaxSpaceShowIntegral = weekMaxSpaceShowIntegral;
    }

    public Double getWeekMaxLiveIntegral() {
        return weekMaxLiveIntegral;
    }

    public void setWeekMaxLiveIntegral(Double weekMaxLiveIntegral) {
        this.weekMaxLiveIntegral = weekMaxLiveIntegral;
    }

    public Double getUploadVideoIntegral() {
        return uploadVideoIntegral;
    }

    public void setUploadVideoIntegral(Double uploadVideoIntegral) {
        this.uploadVideoIntegral = uploadVideoIntegral;
    }

    public Double getPunchIntegral1() {
        return punchIntegral1;
    }

    public Double getPunchIntegral2() {
        return punchIntegral2;
    }

    public Double getPunchIntegral3() {
        return punchIntegral3;
    }

    public void setPunchIntegral1(Double punchIntegral1) {
        this.punchIntegral1 = punchIntegral1;
    }

    public void setPunchIntegral2(Double punchIntegral2) {
        this.punchIntegral2 = punchIntegral2;
    }

    public void setPunchIntegral3(Double punchIntegral3) {
        this.punchIntegral3 = punchIntegral3;
    }

    public Double getUseAppIntegral() {
        return useAppIntegral;
    }

    public void setUseAppIntegral(Double useAppIntegral) {
        this.useAppIntegral = useAppIntegral;
    }
}
