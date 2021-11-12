<#--  xiaoer 2018-09-23 20:48:27-->

<html>
<head>
    <title>详情页面</title>
    <#include "../resource.ftl"/>
</head>
<body>
<div class="layui-field-box">
    <form class="layui-form" action="">

        <div class="layui-form-item">
            <label class="layui-label-left">用户名<span class="label_span">:</span></label>
            <label class="layui-label-right">${(model.userName)!"-"}</label>
        </div>
        <div class="layui-form-item">
            <label class="layui-label-left">意见反馈<span class="label_span">:</span></label>
            <div class="layui-label-right">${(model.content)!"-"}</div>
        </div>
        <div class="layui-form-item">
            <label class="layui-label-left">联系方式<span class="label_span">:</span></label>
            <label class="layui-label-right">${(model.contactInfo)!"-"}</label>
        </div>
    </form>
</div>

</body>
</html>
