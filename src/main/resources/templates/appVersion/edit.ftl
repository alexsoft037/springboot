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
        .layui-upload-list{
            display: none !important;
        }
    </style>
</head>
<body>
<div class="layui-field-box">
    <div class="layui-tab">
        <ul class="layui-tab-title">
            <li class="layui-this">Android</li>
            <li>Iphone</li>
        </ul>
        <div class="layui-tab-content">
            <div class="layui-tab-item layui-show">
                <form class="layui-form" action="">
                    <div class="layui-form-item">
                        <label class="layui-form-label">应用版本<span class="span_must">&nbsp;*</span></label>
                        <div class="layui-input-block">
                            <input type="text" name="versionName" value="${(android.versionName)!""}" lay-verify="required" placeholder="请输入" class="layui-input">
                        </div>
                    </div>

                    <div class="layui-form-item">
                        <label class="layui-form-label">构建版本<span class="span_must">&nbsp;*</span></label>
                        <div class="layui-input-block">
                            <input type="text" name="versionCode" value="${(android.versionCode)!""}" lay-verify="required" placeholder="请输入" class="layui-input">
                        </div>
                    </div>

                    <div class="layui-form-item">
                        <label class="layui-form-label">捆绑标识符<span class="span_must">&nbsp;*</span></label>
                        <div class="layui-input-block">
                            <input type="text" name="applicationId" value="${(android.applicationId)!""}" lay-verify="required" placeholder="请输入" class="layui-input">
                        </div>
                    </div>

                    <div class="layui-form-item">
                        <label class="layui-form-label">上传Android应用<span class="span_must">&nbsp;*</span></label>
                        <div class="layui-input-block">
                            <div cyType="uploadTool" cyProps="url:'/sys/appVersion/upload/',value:'',name:'file',btnName:'上传Android应用',multiple:'false',type:'file',exts:'apk',size:'100000'"></div>
                        </div>
                    </div>

                    <div class="layui-form-item">
                        <label class="layui-form-label">下载Android应用<span class="span_must">&nbsp;*</span></label>
                        <div class="layui-input-block">
                            <a class="layui-btn" href="/upload/shaoer.apk" download=""><i class="fa fa-fw fa-android"></i>下载少儿空间</a>
                        </div>
                    </div>

                    <div class="page-footer">
                        <div class="btn-list">
                            <div class="btnlist">
                                <button class="layui-btn" lay-submit="" lay-filter="submit" data-url="/sys/appVersion/saveAndroidConfig"><i class="fa fa-floppy-o">&nbsp;</i>保存</button>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="layui-tab-item">
                <form class="layui-form" action="">
                    <div class="layui-form-item">
                        <label class="layui-form-label">应用版本<span class="span_must">&nbsp;*</span></label>
                        <div class="layui-input-block">
                            <input type="text" name="bundleShortVersionString" value="${(iphone.bundleShortVersionString)!""}" lay-verify="required" placeholder="请输入" class="layui-input">
                        </div>
                    </div>

                    <div class="layui-form-item">
                        <label class="layui-form-label">构建版本<span class="span_must">&nbsp;*</span></label>
                        <div class="layui-input-block">
                            <input type="text" name="bundleVersion" value="${(iphone.bundleVersion)!""}" lay-verify="required" placeholder="请输入" class="layui-input">
                        </div>
                    </div>

                    <div class="layui-form-item">
                        <label class="layui-form-label">捆绑标识符<span class="span_must">&nbsp;*</span></label>
                        <div class="layui-input-block">
                            <input type="text" name="bundleIdentifier" value="${(iphone.bundleIdentifier)!""}" lay-verify="required" placeholder="请输入" class="layui-input">
                        </div>
                    </div>

                    <div class="page-footer">
                        <div class="btn-list">
                            <div class="btnlist">
                                <button class="layui-btn" lay-submit="" lay-filter="submit" data-url="/sys/appVersion/saveIphoneConfig"><i class="fa fa-floppy-o">&nbsp;</i>保存</button>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<script>
    layui.use('element', function () {
    });
</script>
</body>
</html>
