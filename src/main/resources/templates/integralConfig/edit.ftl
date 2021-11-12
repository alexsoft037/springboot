<!DOCTYPE html>
<html>
<head>
    <title>系统参数</title>
<#include "../resource.ftl"/>
    <style>
        .layui-input-block{
            margin-left: 15px;
        }
        .layui-form-label{
            width: auto;
        }
    </style>
</head>
<body>
<div class="layui-field-box">
    <form class="layui-form" action="">
        <div class="layui-form-item">
            <label class="layui-form-label">公益捐赠积分比例<span class="span_must">&nbsp;*</span></label>
            <div class="layui-input-block">
                <input type="number" min="0" name="donateIntegralRate" value="${(model.donateIntegralRate)!""}" lay-verify="required" placeholder="请输入" class="layui-input">
            </div>
        </div>

        <#--<div class="layui-form-item">-->
            <#--<label class="layui-form-label">一般活动参与积分<span class="span_must">&nbsp;*</span></label>-->
            <#--<div class="layui-input-block">-->
                <#--<input type="text" name="activityIntegralDefault" value="${(model.activityIntegralDefault)!""}" lay-verify="required" placeholder="请输入" class="layui-input">-->
            <#--</div>-->
        <#--</div>-->

        <div class="layui-form-item">
            <label class="layui-form-label">活动参与每周最多获得积分<span class="span_must">&nbsp;*</span></label>
            <div class="layui-input-block">
                <input type="number" min="0" name="weekMaxActivityIntegral" value="${(model.weekMaxActivityIntegral)!""}" lay-verify="required" placeholder="请输入" class="layui-input">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">小记者发布报道基本积分<span class="span_must">&nbsp;*</span></label>
            <div class="layui-input-block">
                <input type="number" min="0" name="reporterPostIntegral" value="${(model.reporterPostIntegral)!""}" lay-verify="required" placeholder="请输入" class="layui-input">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">小记者每周最多获得积分<span class="span_must">&nbsp;*</span></label>
            <div class="layui-input-block">
                <input type="number" min="0" name="weekMaxReporterIntegral" value="${(model.weekMaxReporterIntegral)!""}" lay-verify="required" placeholder="请输入" class="layui-input">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">小记者每周最多获得积分<span class="span_must">&nbsp;*</span></label>
            <div class="layui-input-block">
                <input type="number" min="0" name="weekMaxReporterIntegral" value="${(model.weekMaxReporterIntegral)!""}" lay-verify="required" placeholder="请输入" class="layui-input">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">空间书架每周最多获得积分<span class="span_must">&nbsp;*</span></label>
            <div class="layui-input-block">
                <input type="number" min="0" name="weekMaxBookIntegral" value="${(model.weekMaxBookIntegral)!""}" lay-verify="required" placeholder="请输入" class="layui-input">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">空间课堂直播老师每周最多获得积分<span class="span_must">&nbsp;*</span></label>
            <div class="layui-input-block">
                <input type="number" min="0" name="weekMaxLiveIntegral" value="${(model.weekMaxLiveIntegral)!""}" lay-verify="required" placeholder="请输入" class="layui-input">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">空间课堂上传一个视频获得积分<span class="span_must">&nbsp;*</span></label>
            <div class="layui-input-block">
                <input type="number" min="0" name="uploadVideoIntegral" value="${(model.uploadVideoIntegral)!""}" lay-verify="required" placeholder="请输入" class="layui-input">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">发布活动秀每天最多获得积分<span class="span_must">&nbsp;*</span></label>
            <div class="layui-input-block">
                <input type="number" min="0" name="weekMaxSpaceShowIntegral" value="${(model.weekMaxSpaceShowIntegral)!""}" lay-verify="required" placeholder="请输入" class="layui-input">
            </div>
        </div>

        <div class="layui-form-item">
            <h1 style="padding: 9px 15px;">打卡连续一周获得积分<span class="span_must">&nbsp;*</span></h1>
            <div class="layui-inline">
                <label class="layui-form-label">一天</label>
                <div class="layui-input-inline" style="width: 100px;">
                    <input type="number" min="0" name="punchIntegral1" value="${(model.punchIntegral1)!""}" lay-verify="required" placeholder="请输入" class="layui-input">
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">二天</label>
                <div class="layui-input-inline" style="width: 100px;">
                    <input type="number" min="0" name="punchIntegral2" value="${(model.punchIntegral2)!""}" lay-verify="required" placeholder="请输入" class="layui-input">
                </div>
            </div>
            <div class="layui-inline">
                <label class="layui-form-label">三天, 四天, 五天, 六天, 七天</label>
                <div class="layui-input-inline" style="width: 100px;">
                    <input type="number" min="0" name="punchIntegral3" value="${(model.punchIntegral3)!""}" lay-verify="required" placeholder="请输入" class="layui-input">
                </div>
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">首次使用app 获得积分<span class="span_must">&nbsp;*</span></label>
            <div class="layui-input-block">
                <input type="number" min="0" name="useAppIntegral" value="${(model.useAppIntegral)!""}" lay-verify="required" placeholder="请输入" class="layui-input">
            </div>
        </div>

        <div class="page-footer">
            <div class="btn-list">
                <div class="btnlist">
                    <button class="layui-btn" lay-submit="" lay-filter="submit" data-url="/sys/integral/saveConfig"><i class="fa fa-floppy-o">&nbsp;</i>保存</button>
                </div>
            </div>
        </div>
    </form>
</div>
</body>
</html>
