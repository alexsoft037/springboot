<#--  xiaoer 2018-09-18 19:00:52-->

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
            <label class="layui-label-right">${(model.uid)!"-"}</label>
        </div>
              <div class="layui-form-item">
            <label class="layui-label-left">用户密码<span class="label_span">:</span></label>
            <label class="layui-label-right">${(model.pwd)!"-"}</label>
        </div>
              <div class="layui-form-item">
            <label class="layui-label-left">用户token<span class="label_span">:</span></label>
            <label class="layui-label-right">${(model.token)!"-"}</label>
        </div>
              <div class="layui-form-item">
            <label class="layui-label-left">登录状态<span class="label_span">:</span></label>
            <label class="layui-label-right">${(model.state)!"-"}</label>
        </div>
              <div class="layui-form-item">
            <label class="layui-label-left">sig<span class="label_span">:</span></label>
            <label class="layui-label-right">${(model.userSig)!"-"}</label>
        </div>
              <div class="layui-form-item">
            <label class="layui-label-left">注册时间戳<span class="label_span">:</span></label>
            <label class="layui-label-right">${(model.registerTime)!"-"}</label>
        </div>
              <div class="layui-form-item">
            <label class="layui-label-left">登录时间戳<span class="label_span">:</span></label>
            <label class="layui-label-right">${(model.loginTime)!"-"}</label>
        </div>
              <div class="layui-form-item">
            <label class="layui-label-left">退出时间戳<span class="label_span">:</span></label>
            <label class="layui-label-right">${(model.logoutTime)!"-"}</label>
        </div>
              <div class="layui-form-item">
            <label class="layui-label-left">最新请求时间戳<span class="label_span">:</span></label>
            <label class="layui-label-right">${(model.lastRequestTime)!"-"}</label>
        </div>
              <div class="layui-form-item">
            <label class="layui-label-left">当前appid<span class="label_span">:</span></label>
            <label class="layui-label-right">${(model.currentAppid)!"-"}</label>
        </div>
          </form>
</div>

</body>
</html>
