<#--  xiaoer 2018-07-27 21:24:14-->

<html>
<head>
    <title>详情页面</title>
    <#include "../resource.ftl"/>
</head>
<body>
<div class="layui-field-box">
    <form class="layui-form" action="">
        <div class="layui-form-item">
            <label class="layui-label-left"><span class="label_span">文章标题:</span></label>
            <label class="layui-label-right">${(model.articleTitle)!"-"}</label>
        </div>
        <div class="layui-form-item">
            <label class="layui-label-left"><span class="label_span">文章类型:</span></label>
            <label class="layui-label-right">${(model.articleTypeTxt)!"-"}</label>
        </div>
        <div class="layui-form-item">
            <label class="layui-label-left"><span class="label_span">内容:</span></label>
            <label class="layui-label-right">${(model.content)!"-"}</label>
        </div>
        <div class="layui-form-item">
            <label class="layui-label-left"><span class="label_span">用户:</span></label>
            <label class="layui-label-right">${(model.userName)!"-"}</label>
        </div>
        <div class="layui-form-item">
            <label class="layui-label-left"><span class="label_span">创造时间:</span></label>
            <label class="layui-label-right" id="createAt">${(model.createAt?string["yyyy-MM-dd HH:mm:ss"])!"-"}</label>
        </div>
    </form>
</div>
</body>
</html>
