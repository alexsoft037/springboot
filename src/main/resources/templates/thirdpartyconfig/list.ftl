<#--  chenyi 2018-07-08 18:35:30-->
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
    <script type="text/javascript" src="/thirdpartyconfig/js/list.js"></script>
</head>
<body>
<form class="layui-form " action="">
    <div class="layui-form-item">
        <label class="layui-form-label">名称:</label>
        <div class="layui-input-inline">
            <div cyType="selectTool"
                 cyProps="url:'/getData/getCodeValues?codeName=thirdPartyType',search:'true',filter:'demo'"
                 name="tpTypeCode" value="" class="layui-input-inline"></div>
        </div>

        <div class="layui-input-normal">
            <button class="layui-btn layui-btn-green" lay-submit="" lay-filter="moreSearch">
                <i class="fa fa-chevron-down">&nbsp;</i>更多
            </button>
            <button class="layui-btn search-btn" table-id="thirdPartyConfigTable" lay-submit="" lay-filter="search">
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
<@shiro.hasPermission name="thirdpartyconfig:save">
    <button class="layui-btn" onclick="addPage('/thirdpartyconfig/add')">
        <i class="fa fa-plus">&nbsp;</i>增加
    </button>
</@shiro.hasPermission>
<@shiro.hasPermission name="thirdpartyconfig:update">
    <button class="layui-btn" onclick="editPage('thirdPartyConfigTable','/thirdpartyconfig/edit')">
        <i class="fa fa-pencil-square-o">&nbsp;</i>修改
    </button>
    <button class="layui-btn layui-btn-green"
            onclick="updateState('批量启用','thirdPartyConfigTable','/thirdpartyconfig/enable')">
        <i class="fa fa-check-square-o">&nbsp;</i>启用
    </button>
    <button class="layui-btn  layui-btn-danger"
            onclick="updateState('批量禁用','thirdPartyConfigTable','/thirdpartyconfig/limit')">
        <i class="fa fa-expeditedssl">&nbsp;</i>禁用
    </button>
</@shiro.hasPermission>
<@shiro.hasPermission name="thirdpartyconfig:delete">
    <button class="layui-btn layui-btn-delete"
            onclick="deleteBatch('批量删除','thirdPartyConfigTable','/thirdpartyconfig/delete');">
        <i class="fa fa-trash-o">&nbsp;</i>删除
    </button>
</@shiro.hasPermission>

</div>
<div class="layui-form ">
    <table class="layui-table" id="thirdPartyConfigTable" cyType="pageGrid"
           cyProps="url:'/thirdpartyconfig/listData',checkbox:'true',pageColor:'#2991d9'">
        <thead>
        <tr>
            <!--复选框-->
            <th width="1%" param="{type:'checkbox'}">
                <input type="checkbox" lay-skin="primary" lay-filter="allChoose">
            </th>
            <!--isPrimary：是否是主键-->
            <th width="10%" param="{name:'id',isPrimary:'true',hide:'true'}"></th>

            <th width="10%" param="{name:'tpTypeName',sort:'true'}">名称</th>

            <th width="10%" param="{name:'tpUrl'}">链接</th>

            <th width="10%" param="{name:'tpUser'}">帐号</th>

            <th width="10%" param="{name:'tpPassword'}">密码</th>

            <th width="10%" param="{name:'tpRemark'}">备注</th>
            <!--isPrimary：渲染列-->
            <th width="10%" param="{name:'state',enumName:'StateEnum',render:'Render.customState'}">状态</th>
            <th width="10%" param="{operate:'true',buttons:'Render.state,Render.edit,Render.delete'}">操作</th>
        </tr>
        </thead>
    </table>
</div>
</body>
</html>