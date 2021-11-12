<#--  xiaoer 2018-08-24 23:03:45-->
<!DOCTYPE html>
<html>
<head>
    <title>列表</title>
    <#include "../resource.ftl"/>
    <style>
        tr td span{
            display: flex;
        }
        .layui-form-label{
            width: 60px;
        }
        #friendShowTable tbody tr td[name="content"]{
            text-overflow: ellipsis;
            overflow: hidden;
            white-space: nowrap;
            max-width: 100px;
        }
    </style>
    <script type="text/javascript" src="/friendShow/js/list.js"></script>
</head>
<body>
<form class="layui-form " action="">
    <div class="layui-form-item">
        <label class="layui-form-label">名称:</label>
        <div class="layui-input-inline">
            <input type="text" name=""  placeholder="请输入名称" class="layui-input">
        </div>

        <div class="layui-input-normal">
            <button class="layui-btn layui-btn-green" lay-submit="" lay-filter="moreSearch">
                <i class="fa fa-chevron-down">&nbsp;</i>更多
            </button>
            <button class="layui-btn search-btn" table-id="friendShowTable" lay-submit="" lay-filter="search">
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
        <@shiro.hasPermission name="friendShow:save">
        <button class="layui-btn" onclick="addPage('/friendShow/add')">
            <i class="fa fa-plus">&nbsp;</i>增加
        </button>
        </@shiro.hasPermission>
        <@shiro.hasPermission name="friendShow:update">
        <button class="layui-btn" onclick="editPage('friendShowTable','/friendShow/edit')">
            <i class="fa fa-pencil-square-o">&nbsp;</i>修改
        </button>
         <button class="layui-btn layui-btn-green" onclick="updateState('批量启用','friendShowTable','/friendShow/enable')">
            <i class="fa fa-check-square-o">&nbsp;</i>启用
        </button>
        <button class="layui-btn  layui-btn-danger" onclick="updateState('批量禁用','friendShowTable','/friendShow/limit')">
            <i class="fa fa-expeditedssl">&nbsp;</i>禁用
        </button>
        </@shiro.hasPermission>
        <@shiro.hasPermission name="friendShow:delete">
         <button class="layui-btn layui-btn-delete" onclick="deleteBatch('批量删除','friendShowTable','/friendShow/delete');">
            <i class="fa fa-trash-o">&nbsp;</i>删除
        </button>
        </@shiro.hasPermission>

</div>
<div class="layui-form ">
    <table class="layui-table" id="friendShowTable" cyType="pageGrid"
           cyProps="url:'/friendShow/listData',checkbox:'true',pageColor:'#2991d9'">
        <thead>
        <tr>
            <!--复选框-->
            <th width="1%" param="{type:'checkbox'}">
                <input type="checkbox" lay-skin="primary" lay-filter="allChoose">
            </th>
            <!--isPrimary：是否是主键-->
            <th width="10%" param="{name:'showId',isPrimary:'true',hide:'true'}"></th>

            <th width="10%" param="{name:'image',render:'Render.renderImage'}">图片</th>

            <th width="10%" param="{name:'spaceName',sort:'false'}">少儿空间</th>

            <th width="10%" param="{name:'content'}">内容</th>

            <th width="10%" param="{name:'createUser'}">用户名</th>

            <th width="10%" param="{name:'featuredYn',render:'Render.featuredYn'}">热门设置</th>

            <th width="10%" param="{name:'readCount'}">读数</th>

            <th width="10%" param="{name:'state',enumName:'StateEnum',render:'Render.customState'}">状态</th>
            <th width="10%" param="{operate:'true',buttons:'Render.state,Render.edit,Render.delete'}">操作</th>
        </tr>
        </thead>
    </table>
</div>
</body>
</html>