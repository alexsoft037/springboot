<#--  xiaoer 2018-07-24 12:11:49-->
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
    <script type="text/javascript" src="/activityattend/js/list.js"></script>
</head>
<body>
<form class="layui-form " action="">
    <div class="layui-form-item">
        <label class="layui-form-label">名称:</label>
        <div class="layui-input-inline">
            <input type="text" name="" placeholder="请输入名称" class="layui-input">
        </div>

        <div class="layui-input-normal">
            <button class="layui-btn layui-btn-green" lay-submit="" lay-filter="moreSearch">
                <i class="fa fa-chevron-down">&nbsp;</i>更多
            </button>
            <button class="layui-btn search-btn" table-id="activityAttendTable" lay-submit="" lay-filter="search">
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
<#--<@shiro.hasPermission name="activityattend:save">-->
    <#--<button class="layui-btn" onclick="addPage('/activityattend/add')">-->
        <#--<i class="fa fa-plus">&nbsp;</i>增加-->
    <#--</button>-->
<#--</@shiro.hasPermission>-->
<#--<@shiro.hasPermission name="activityattend:update">-->
    <#--<button class="layui-btn" onclick="editPage('activityAttendTable','/activityattend/edit')">-->
        <#--<i class="fa fa-pencil-square-o">&nbsp;</i>修改-->
    <#--</button>-->
    <#--<button class="layui-btn layui-btn-green"-->
            <#--onclick="updateState('批量启用','activityAttendTable','/activityattend/enable')">-->
        <#--<i class="fa fa-check-square-o">&nbsp;</i>启用-->
    <#--</button>-->
    <#--<button class="layui-btn  layui-btn-danger"-->
            <#--onclick="updateState('批量禁用','activityAttendTable','/activityattend/limit')">-->
        <#--<i class="fa fa-expeditedssl">&nbsp;</i>禁用-->
    <#--</button>-->
<#--</@shiro.hasPermission>-->
<@shiro.hasPermission name="activityattend:delete">
    <button class="layui-btn layui-btn-delete"
            onclick="deleteBatch('批量删除','activityAttendTable','/activityattend/delete');">
        <i class="fa fa-trash-o">&nbsp;</i>删除
    </button>
</@shiro.hasPermission>

</div>
<div class="layui-form ">
    <table class="layui-table" id="activityAttendTable" cyType="pageGrid"
           cyProps="url:'/activityattend/listData?activityId=${(activityId)!""}',checkbox:'true',pageColor:'#2991d9'">
        <thead>
        <tr>
            <!--复选框-->
            <th width="1%" param="{type:'checkbox'}">
                <input type="checkbox" lay-skin="primary" lay-filter="allChoose">
            </th>
            <!--isPrimary：是否是主键-->
            <th width="10%" param="{name:'id',isPrimary:'true',hide:'true'}"></th>

            <th width="10%" param="{name:'activityTitle'}">活动参与</th>

            <th width="10%" param="{name:'userName'}">用户名</th>

            <#--<th width="10%" param="{name:'state'}"></th>-->

            <th width="10%" param="{name:'createTime',render:'Render.customDate'}">创建时间</th>
            <!--isPrimary：渲染列-->
            <th width="10%" param="{name:'state',enumName:'StateEnum',render:'Render.customState'}">状态</th>
            <th width="10%" param="{operate:'true',buttons:'Render.delete'}">操作</th>
        </tr>
        </thead>
    </table>
</div>
</body>
</html>