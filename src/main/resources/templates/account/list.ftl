<#--  xiaoer 2018-09-18 19:00:52-->
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
    <script type="text/javascript" src="/account/js/list.js"></script>
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
            <button class="layui-btn search-btn" table-id="accountTable" lay-submit="" lay-filter="search">
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
        <@shiro.hasPermission name="account:save">
        <button class="layui-btn" onclick="addPage('/account/add')">
            <i class="fa fa-plus">&nbsp;</i>增加
        </button>
        </@shiro.hasPermission>
        <@shiro.hasPermission name="account:update">
        <button class="layui-btn" onclick="editPage('accountTable','/account/edit')">
            <i class="fa fa-pencil-square-o">&nbsp;</i>修改
        </button>
         <button class="layui-btn layui-btn-green" onclick="updateState('批量启用','accountTable','/account/enable')">
            <i class="fa fa-check-square-o">&nbsp;</i>启用
        </button>
        <button class="layui-btn  layui-btn-danger" onclick="updateState('批量禁用','accountTable','/account/limit')">
            <i class="fa fa-expeditedssl">&nbsp;</i>禁用
        </button>
        </@shiro.hasPermission>
        <@shiro.hasPermission name="account:delete">
         <button class="layui-btn layui-btn-delete" onclick="deleteBatch('批量删除','accountTable','/account/delete');">
            <i class="fa fa-trash-o">&nbsp;</i>删除
        </button>
        </@shiro.hasPermission>

</div>
<div class="layui-form ">
    <table class="layui-table" id="accountTable" cyType="pageGrid"
           cyProps="url:'/account/listData',checkbox:'true',pageColor:'#2991d9'">
        <thead>
        <tr>
            <!--复选框-->
            <th width="1%" param="{type:'checkbox'}">
                <input type="checkbox" lay-skin="primary" lay-filter="allChoose">
            </th>
            			            <!--isPrimary：是否是主键-->
            <th width="10%" param="{name:'uid',isPrimary:'true',hide:'true'}">用户名</th>
            
		                			
		          <th width="10%" param="{name:'pwd'}">用户密码</th>
			            			
		          <th width="10%" param="{name:'token'}">用户token</th>
			            			
		          <th width="10%" param="{name:'state'}">登录状态</th>
			            			
		          <th width="10%" param="{name:'userSig'}">sig</th>
			            			
		          <th width="10%" param="{name:'registerTime'}">注册时间戳</th>
			            			
		          <th width="10%" param="{name:'loginTime'}">登录时间戳</th>
			            			
		          <th width="10%" param="{name:'logoutTime'}">退出时间戳</th>
			            			
		          <th width="10%" param="{name:'lastRequestTime'}">最新请求时间戳</th>
			            			
		          <th width="10%" param="{name:'currentAppid'}">当前appid</th>
			                        <!--isPrimary：渲染列-->
            <th width="10%" param="{name:'state',enumName:'StateEnum',render:'Render.customState'}">状态</th>
            <th width="10%" param="{operate:'true',buttons:'Render.state,Render.edit,Render.delete'}">操作</th>
        </tr>
        </thead>
    </table>
</div>
</body>
</html>