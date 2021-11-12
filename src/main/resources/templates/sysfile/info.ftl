<#--  xiaoer 2018-08-18 02:44:19-->

<html>
<head>
    <title>地产附件表详情页面</title>
    <#include "../resource.ftl"/>
</head>
<body>
<div class="layui-field-box">
    <form class="layui-form" action="">
             <div class="layui-form-item">
            <label class="layui-label-left">id<span class="label_span">:</span></label>
            <label class="layui-label-right">${(model.id)!"-"}</label>
        </div>
              <div class="layui-form-item">
            <label class="layui-label-left">控件唯一标识(UUID)<span class="label_span">:</span></label>
            <label class="layui-label-right">${(model.uploadId)!"-"}</label>
        </div>
              <div class="layui-form-item">
            <label class="layui-label-left">文件路径<span class="label_span">:</span></label>
            <label class="layui-label-right">${(model.url)!"-"}</label>
        </div>
              <div class="layui-form-item">
            <label class="layui-label-left"><span class="label_span">:</span></label>
            <label class="layui-label-right">${(model.thumbnailUrl)!"-"}</label>
        </div>
              <div class="layui-form-item">
            <label class="layui-label-left">文件名<span class="label_span">:</span></label>
            <label class="layui-label-right">${(model.fileName)!"-"}</label>
        </div>
              <div class="layui-form-item">
            <label class="layui-label-left">附件类型<span class="label_span">:</span></label>
            <label class="layui-label-right">${(model.fileType)!"-"}</label>
        </div>
              <div class="layui-form-item">
            <label class="layui-label-left">附件大小<span class="label_span">:</span></label>
            <label class="layui-label-right">${(model.fileSize)!"-"}</label>
        </div>
              <div class="layui-form-item">
            <label class="layui-label-left"><span class="label_span">:</span></label>
            <label class="layui-label-right">${(model.ossYn)!"-"}</label>
        </div>
              <div class="layui-form-item">
            <label class="layui-label-left">上传时间<span class="label_span">:</span></label>
            <label class="layui-label-right">${(model.createTime)!"-"}</label>
        </div>
          </form>
</div>

</body>
</html>
