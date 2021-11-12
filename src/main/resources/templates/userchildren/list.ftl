<#--  xiaoer 2018-09-03 12:51:23-->
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
    </style>
    <script type="text/javascript" src="/userchildren/js/list.js"></script>
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
            <button class="layui-btn search-btn" table-id="userChildrenTable" lay-submit="" lay-filter="search">
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
        <@shiro.hasPermission name="userchildren:save">
        <button class="layui-btn" onclick="addPage('/userchildren/add')">
            <i class="fa fa-plus">&nbsp;</i>增加
        </button>
        </@shiro.hasPermission>
        <@shiro.hasPermission name="userchildren:update">
        <button class="layui-btn" onclick="editPage('userChildrenTable','/userchildren/edit')">
            <i class="fa fa-pencil-square-o">&nbsp;</i>修改
        </button>
         <button class="layui-btn layui-btn-green" onclick="updateState('批量启用','userChildrenTable','/userchildren/enable')">
            <i class="fa fa-check-square-o">&nbsp;</i>启用
        </button>
        <button class="layui-btn  layui-btn-danger" onclick="updateState('批量禁用','userChildrenTable','/userchildren/limit')">
            <i class="fa fa-expeditedssl">&nbsp;</i>禁用
        </button>
        </@shiro.hasPermission>
        <@shiro.hasPermission name="userchildren:delete">
         <button class="layui-btn layui-btn-delete" onclick="deleteBatch('批量删除','userChildrenTable','/userchildren/delete');">
            <i class="fa fa-trash-o">&nbsp;</i>删除
        </button>
        </@shiro.hasPermission>

</div>
<div class="layui-form ">
    <table class="layui-table" id="userChildrenTable" cyType="pageGrid"
           cyProps="url:'/userchildren/listData',checkbox:'true',pageColor:'#2991d9'">
        <thead>
        <tr>
            <!--复选框-->
            <th width="1%" param="{type:'checkbox'}">
                <input type="checkbox" lay-skin="primary" lay-filter="allChoose">
            </th>
            			            <!--isPrimary：是否是主键-->
            <th width="10%" param="{name:'childrenId',isPrimary:'true',hide:'true'}"></th>
            
		                			
		          <th width="10%" param="{name:'userId'}"></th>
			            			
		          <th width="10%" param="{name:'name'}"></th>
			            			
		          <th width="10%" param="{name:'photo'}"></th>
			            			
		          <th width="10%" param="{name:'sex'}"></th>
			            			
		          <th width="10%" param="{name:'height'}"></th>
			            			
		          <th width="10%" param="{name:'birthday'}"></th>
			            			
		          <th width="10%" param="{name:'state'}"></th>
			            			
		          <th width="10%" param="{name:'createAt'}"></th>
			            			
		          <th width="10%" param="{name:'createBy'}"></th>
			            			
		          <th width="10%" param="{name:'updateAt'}"></th>
			            			
		          <th width="10%" param="{name:'updateBy'}"></th>
			                        <!--isPrimary：渲染列-->
            <th width="10%" param="{name:'state',enumName:'StateEnum',render:'Render.customState'}">状态</th>
            <th width="10%" param="{operate:'true',buttons:'Render.state,Render.edit,Render.delete'}">操作</th>
        </tr>
        </thead>
    </table>
</div>
</body>
</html>