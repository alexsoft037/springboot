<#--  xiaoer 2018-07-31 18:28:15-->
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
    <script type="text/javascript" src="/bookcategory/js/list.js"></script>
</head>
<body>
<form class="layui-form " action="">
    <div class="layui-form-item">
        <label class="layui-form-label">名称:</label>
        <div class="layui-input-inline">
            <input type="text" name="categoryName" placeholder="请输入名称" class="layui-input">
        </div>
        <label class="layui-form-label">上级分类:</label>
        <div class="layui-input-inline">
            <input id="upperCode" cyType="treeTool" cyProps="url:'/bookcategory/select',name:'upperCode'" placeholder="请选择上级分类" class="layui-input"/>
        </div>


        <div class="layui-input-normal">
            <button class="layui-btn layui-btn-green" lay-submit="" lay-filter="moreSearch">
                <i class="fa fa-chevron-down">&nbsp;</i>更多
            </button>
            <button class="layui-btn search-btn" table-id="bookCategoryTable" lay-submit="" lay-filter="search">
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
<@shiro.hasPermission name="bookcategory:save">
    <button class="layui-btn" onclick="addPage('/bookcategory/add')">
        <i class="fa fa-plus">&nbsp;</i>增加
    </button>
</@shiro.hasPermission>
<@shiro.hasPermission name="bookcategory:update">
    <button class="layui-btn" onclick="editPage('bookCategoryTable','/bookcategory/edit')">
        <i class="fa fa-pencil-square-o">&nbsp;</i>修改
    </button>
    <button class="layui-btn layui-btn-green" onclick="updateState('批量启用','bookCategoryTable','/bookcategory/enable')">
        <i class="fa fa-check-square-o">&nbsp;</i>启用
    </button>
    <button class="layui-btn  layui-btn-danger" onclick="updateState('批量禁用','bookCategoryTable','/bookcategory/limit')">
        <i class="fa fa-expeditedssl">&nbsp;</i>禁用
    </button>
</@shiro.hasPermission>
<@shiro.hasPermission name="bookcategory:delete">
    <button class="layui-btn layui-btn-delete"
            onclick="deleteBatch('批量删除','bookCategoryTable','/bookcategory/delete');">
        <i class="fa fa-trash-o">&nbsp;</i>删除
    </button>
</@shiro.hasPermission>

</div>
<div class="layui-form ">
    <table class="layui-table" id="bookCategoryTable" cyType="pageGrid" cyProps="url:'/bookcategory/listData',checkbox:'true',pageColor:'#2991d9'">
        <thead>
        <tr>
            <!--复选框-->
            <th width="1%" param="{type:'checkbox'}">
                <input type="checkbox" lay-skin="primary" lay-filter="allChoose">
            </th>
            <!--isPrimary：是否是主键-->
            <th width="10%" param="{name:'categoryId',isPrimary:'true',hide:'true'}"></th>

            <th width="10%" param="{name:'categoryIcon',render:'Render.renderImage'}">分类图标</th>

            <th width="10%" param="{name:'categoryName',sort:'true'}">分类名称</th>

            <th width="10%" param="{name:'categoryCode'}">分类值</th>

            <th width="10%" param="{name:'upperName'}">上级分类</th>

            <th width="10%" param="{name:'state',enumName:'StateEnum',render:'Render.customState'}">状态</th>
            <th width="10%" param="{operate:'true',buttons:'Render.state,Render.edit,Render.delete'}">操作</th>
        </tr>
        </thead>
    </table>
</div>
</body>
</html>