<!DOCTYPE html>
<html xmlns:c="http://www.w3.org/1999/html">
<head>
    <title>全部用户</title>
    <#include "../resource.ftl"/>
    <style>body {
            text-align: center
        }</style>
    <script type="text/javascript" src="/pushMessage/js/common.js"></script>
</head>
<body>
<div class="layui-field-box">
    <form class="layui-form " action="" id="formid">

        <!--通过url渲染数据-->
        <span class="span_must">*</span>推送平台<br>
        <div id="powered" cyType="radioTool" cyProps="url:'/frontframe/json/powered.json',filter:'sex'"
             name="powered" value="1" class="layui-input-inline"></div>
        <br><br>

        <div style="display: none">
            <input name="method" value="1">
        </div>


        <span class="span_must">*</span>消息内容
        <div>
            <textarea rows="9" cols="65" id="msg" name="msg"></textarea>
        </div>
        <br>
        <br>
        <br>

    </form>
    <button id="button1" class="layui-btn" onclick="push()">发送</button>
</div>
</body>

</html>
