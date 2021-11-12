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
    <script type="text/javascript" src="/donateweb/js/list.js"></script>
</head>
<body>
<form class="layui-form " action="">
    <div class="layui-form-item">
        <label class="layui-form-label">标题:</label>
        <div class="layui-input-inline">
            <input type="text" name="titleVague" placeholder="请输入名称" class="layui-input">
        </div>

        <div class="layui-input-normal">
            <button class="layui-btn layui-btn-green" lay-submit="" lay-filter="moreSearch">
                <i class="fa fa-chevron-down">&nbsp;</i>更多
            </button>
            <button class="layui-btn search-btn" table-id="donatewebTable" lay-submit="" lay-filter="search">
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
<@shiro.hasPermission name="donateweb:save">
    <button class="layui-btn" onclick="addPage('/donateWeb/add')">
        <i class="fa fa-plus">&nbsp;</i>增加
    </button>
</@shiro.hasPermission>
<@shiro.hasPermission name="donateweb:update">
    <button class="layui-btn" onclick="editPage('donatewebTable','/donateWeb/edit')">
        <i class="fa fa-pencil-square-o">&nbsp;</i>修改
    </button>
    <button class="layui-btn layui-btn-green"
            onclick="updateState('批量启用','donatewebTable','/donateWeb/enable')">
        <i class="fa fa-check-square-o">&nbsp;</i>启用
    </button>
    <button class="layui-btn  layui-btn-danger"
            onclick="updateState('批量禁用','donatewebTable','/donateWeb/limit')">
        <i class="fa fa-expeditedssl">&nbsp;</i>禁用
    </button>
</@shiro.hasPermission>
<@shiro.hasPermission name="donateweb:delete">
    <button class="layui-btn layui-btn-delete"
            onclick="deleteBatch('批量删除','donatewebTable','/donateWeb/delete');">
        <i class="fa fa-trash-o">&nbsp;</i>删除
    </button>
</@shiro.hasPermission>

</div>
<div class="layui-form ">
    <table class="layui-table" id="donatewebTable" cyType="pageGrid"
           cyProps="url:'/donateWeb/listData',checkbox:'true',pageColor:'#2991d9'">
        <thead>
        <tr>
            <!--复选框-->
            <th width="1%" param="{type:'checkbox'}">
                <input type="checkbox" lay-skin="primary" lay-filter="allChoose">
            </th>
            <!--isPrimary：是否是主键-->
            <th width="10%" param="{name:'id',isPrimary:'true',hide:'true'}"></th>

            <th width="10%" param="{name:'featuredImage',render:'Render.renderImage'}">特色图片</th>

            <th width="10%" param="{name:'title',sort:'true'}">标题</th>

            <th width="10%" param="{name:'author',sort:'false'}">作者/来源</th>

            <th width="10%" param="{name:'createBy',sort:'false',hide:'true'}">创建者id</th>

            <th width="10%" param="{name:'createUser',sort:'false'}">创建者</th>

            <#--<th width="10%" param="{name:'content',style:'text-overflow: ellipsis;overflow: hidden;white-space: nowrap;max-width: 100px;'}" class="text-ellipsis">内容</th>-->

            <th width="10%" param="{name:'readCount',sort:'true'}">读数</th>

            <th width="10%" param="{name:'rank',sort:'true'}">权重</th>

            <th width="10%" param="{name:'createAt',sort:'true',render:'Render.customDate'}">创建时间</th>

            <!--isPrimary：渲染列-->
            <th width="10%" param="{name:'state',enumName:'StateEnum',render:'Render.customState'}">状态</th>
            <th width="10%" param="{operate:'true',buttons:'Render.state,Render.edit,Render.delete'}">操作</th>
        </tr>
        </thead>
    </table>
</div>
</body>
</html>