<#--  xiaoer 2018-07-24 09:07:02-->

<html>
<head>
    <title>修改页面</title>
    <#include "../resource.ftl"/>
    <script type="text/javascript" src="/spaceheadline/js/common.js"></script>
</head>
<body>
<div class="layui-field-box">
    <form class="layui-form" action="">
       <#include "common.ftl"/>
        <input type="hidden" name="headlineId" value="${model.headlineId}">
        <div class="page-footer">
            <div class="btn-list">
                <div class="btnlist">
                    <button class="layui-btn" lay-submit="" lay-filter="submit" data-url="/spaceheadline/update"><i class="fa fa-floppy-o">&nbsp;</i>保存</button>
                    <button class="layui-btn" onclick="$t.closeWindow();"><i class="fa fa-undo">&nbsp;</i>返回</button>
                </div>
            </div>
        </div>
    </form>
</div>

</body>
</html>

