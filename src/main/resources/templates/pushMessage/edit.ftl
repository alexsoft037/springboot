<!DOCTYPE html>
<html xmlns:c="http://www.w3.org/1999/html">
<head>
    <title>推送消息</title>
    <#include "../resource.ftl"/>
    <style>
        .layui-input-block {
            margin-left: 15px;
        }

        .layui-form-label {
            width: auto;
        }
        body{ text-align:center}
    </style>
    <script type="text/javascript" src="/pushMessage/js/common.js"></script>
</head>
<body>
<div class="layui-main">
    <button class="layui-btn layui-btn-big" onclick="pushAll('/sysMessage/pushAll')">
        <i class="fa fa-plus">&nbsp;</i>全部用户
    </button>
    <br/>
    <br/>
    <br/>
    <button class="layui-btn layui-btn-big" onclick="pushByRank('/sysMessage/pushByRank')">
        <i class="fa fa-plus">&nbsp;</i>等级分类
    </button>
    <br/>
    <br/>
    <br/>
    <button class="layui-btn layui-btn-big" onclick="pushByPhone('/sysMessage/pushByPhone')">
        <i class="fa fa-plus">&nbsp;</i>个别用户
    </button>
    <div>
</body>

</html>
