<!DOCTYPE html>
<html>
<head>
    <title>管理员列表</title>
    <#include "../resource.ftl"/>
    <style>
        tr td span{
            display: flex;
        }
        .layui-form-label{
            width: 60px;
        }
        .avatar {
            width: 75px;
            border-radius: 50%;
            height: 75px;
            background-size: cover;
        }
    </style>
    <script type="text/javascript" src="/newsman/js/list.js"></script>
</head>
<body>
<form class="layui-form " action="">
    <div class="layui-form-item">
        <label class="layui-form-label">用户名:</label>
        <div class="layui-input-inline">
            <input type="text" name="username" placeholder="请输入用户名" class="layui-input">
        </div>
        <label class="layui-form-label">手机号:</label>
        <div class="layui-input-inline">
            <input type="text" name="phoneNo" placeholder="请输入手机号" class="layui-input">
        </div>
        <div class="layui-input-inline">
            <button class="layui-btn search-btn" table-id="userTable" lay-submit="" lay-filter="search"><i class="fa fa-search">&nbsp;</i>查询
            </button>
            <button type="reset" class="layui-btn layui-btn-primary"><i class="fa fa-refresh">&nbsp;</i>重置</button>
        </div>
    </div>
</form>
<div class="layui-btn-group">
<@shiro.hasPermission name="newsman:update">
    <button class="layui-btn layui-btn-green" onclick="updateState('批量启用','userTable','/newsman/enable')">
        <i class="fa fa-check-square-o">&nbsp;</i>启用
    </button>
    <button class="layui-btn  layui-btn-danger" onclick="updateState('批量禁用','userTable','/newsman/limit')">
        <i class="fa fa-expeditedssl">&nbsp;</i>禁用
    </button>
</@shiro.hasPermission>
<@shiro.hasPermission name="newsman:delete">
    <button class="layui-btn" onclick="deleteBatch('userTable','/newsman/delete');" style="margin-left: 5px!important;">
        <i class="fa fa-trash-o">&nbsp;</i>删除
    </button>
</@shiro.hasPermission>
    <#--<button class="layui-btn" onclick="initPassword('userTable','/newsman/initPassword');" style="margin-left: 5px!important;">-->
        <#--<i class="fa fa-refresh">&nbsp;</i>初始化密码 123456-->
    <#--</button>-->

</div>
<div class="layui-form ">
    <table class="layui-table" id="userTable" cyType="pageGrid"
           cyProps="url:'/newsman/listData',checkbox:'true',pageColor:'#2991d9'">
        <thead>
        <tr>
            <!--复选框-->
            <th width="1%" param="{type:'checkbox'}">
                <input type="checkbox" lay-skin="primary" lay-filter="allChoose">
            </th>
            <!--isPrimary：是否是主键-->
            <th width="10%" param="{name:'userId',isPrimary:'true',hide:'true'}">用户ID</th>
            <th width="10%" param="{name:'avatar',render:'Render.renderImage'}">用户名</th>
            <th width="10%" param="{name:'username',sort:'true'}">用户名</th>
            <th width="10%" param="{name:'phoneNo',sort:'true'}">手机号</th>
            <th width="10%" param="{name:'volunteerId',sort:'true'}">志愿者号</th>
            <th width="10%" param="{name:'gender'}">性别</th>
            <th width="10%" param="{name:'addressTxt'}">地区</th>
            <th width="10%" param="{name:'personality'}">个性签名</th>
            <th width="10%" param="{name:'createTime',sort:'true', render:'Render.customDate'}">创建时间</th>
            <th width="10%" param="{name:'status',render:'Render.customState'}">状态</th>
            <th width="10%" param="{operate:'true',buttons:'Render.state,Render.delete'}">操作</th>
        </tr>
        </thead>
    </table>
</div>

</body>
</html>