<#--  xiaoer 2018-07-23 20:59:06-->
<!DOCTYPE html>
<html>
<head>
    <title>列表</title>
<#include "../resource.ftl"/>
    <style>
        tr td span {
            display: flex;
        }

        .layui-form-label {
            width: 60px;
        }
    </style>
    <script type="text/javascript" src="/activitymessage/js/list.js"></script>
</head>
<body>
<form class="layui-form " action="">
    <div class="layui-form-item">
        <label class="layui-form-label">标题:</label>
        <div class="layui-input-inline">
            <input type="text" name="name" placeholder="请输入联系人" class="layui-input">
        </div>

        <div class="layui-input-normal">
            <button class="layui-btn layui-btn-green" lay-submit="" lay-filter="moreSearch">
                <i class="fa fa-chevron-down">&nbsp;</i>更多
            </button>
            <button class="layui-btn search-btn" table-id="activitymessageTable" lay-submit="" lay-filter="search">
                <i class="fa fa-search">&nbsp;</i>查询
            </button>
            <button type="reset" class="layui-btn layui-btn-primary"><i class="fa fa-refresh">&nbsp;</i>重置</button>
        </div>
    </div>
    <div class="layui-form-item more-search">
    <#-- 更多条件-->
    </div>
</form>
<div class="layui-btn-group">
<@shiro.hasPermission name="activitymessage:delete">
    <button class="layui-btn layui-btn-delete"
            onclick="deleteBatch('批量删除','activitymessageTable','/activitymessage/delete');">
        <i class="fa fa-trash-o">&nbsp;</i>删除
    </button>
</@shiro.hasPermission>
</div>
<div class="layui-form ">
    <table class="layui-table" id="activitymessageTable" cyType="pageGrid"
           cyProps="url:'/activitymessage/listData',checkbox:'true',pageColor:'#2991d9'">
        <thead>
        <tr>
            <!--复选框-->
            <th width="1%" param="{type:'checkbox'}">
                <input type="checkbox" lay-skin="primary" lay-filter="allChoose">
            </th>
            <!--isPrimary：是否是主键-->
            <th width="10%" param="{name:'messageId',isPrimary:'true',hide:'true'}"></th>

            <th width="10%" param="{name:'name'}">联系人</th>

            <th width="10%" param="{name:'phoneNo'}">联系号码</th>

            <th width="10%" param="{name:'content'}">短信内容</th>

            <th width="10%" param="{name:'createAt',render:'Render.customDate'}">发送时间</th>

            <!--isPrimary：渲染列-->
            <th width="10%" param="{operate:'true',buttons:'Render.delete'}">操作</th>
        </tr>
        </thead>
    </table>
</div>
</body>
</html>