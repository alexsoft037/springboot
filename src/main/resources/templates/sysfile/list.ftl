<#--  xiaoer 2018-08-18 02:44:19-->
<!DOCTYPE html>
<html>
<head>
    <title>地产附件表列表</title>
<#include "../resource.ftl"/>
    <style>
        tr td span {
            display: flex;
        }

        .layui-form-label {
            width: 60px;
        }
    </style>
    <script type="text/javascript" src="/sysfile/js/list.js"></script>
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
            <button class="layui-btn search-btn" table-id="sysFileTable" lay-submit="" lay-filter="search">
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
<@shiro.hasPermission name="sysfile:save">
    <div cyType="HuploadTool" cyProps="name:'featuredImage',url:'/getData/uploaders/',uploadId:'',btnName:'上传文件',uploadBtn:'true',deleteBtn:'true'"></div>
</@shiro.hasPermission>
<@shiro.hasPermission name="sysfile:delete">
    <button class="layui-btn layui-btn-delete" onclick="deleteBatch('批量删除','sysFileTable','/sysfile/delete');">
        <i class="fa fa-trash-o">&nbsp;</i>删除
    </button>
</@shiro.hasPermission>

</div>
<div class="layui-form ">
    <table class="layui-table" id="sysFileTable" cyType="pageGrid"
           cyProps="url:'/sysfile/listData',checkbox:'true',pageColor:'#2991d9'">
        <thead>
        <tr>
            <!--复选框-->
            <th width="1%" param="{type:'checkbox'}">
                <input type="checkbox" lay-skin="primary" lay-filter="allChoose">
            </th>
            <!--isPrimary：是否是主键-->
            <th width="10%" param="{name:'id',isPrimary:'true',hide:'true'}">id</th>

            <th width="10%" param="{name:'url',render:'Render.customImage'}">文件路径</th>

            <th width="10%" param="{name:'fileName'}">文件名</th>

            <th width="10%" param="{name:'fileType'}">附件类型</th>

            <th width="10%" param="{name:'fileSize'}">附件大小</th>

            <th width="10%" param="{name:'createTime'}">上传时间</th>

            <th width="10%" param="{operate:'true',buttons:'Render.delete'}">操作</th>
        </tr>
        </thead>
    </table>
</div>
</body>
</html>