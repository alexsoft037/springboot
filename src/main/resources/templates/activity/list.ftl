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
    <script type="text/javascript" src="/activity/js/list.js"></script>
</head>
<body>
<form class="layui-form " action="">
    <div class="layui-form-item">
        <label class="layui-form-label">标题:</label>
        <div class="layui-input-inline">
            <input type="text" name="title" placeholder="请输入名称" class="layui-input">
        </div>

        <div class="layui-input-normal">
            <button class="layui-btn layui-btn-green" lay-submit="" lay-filter="moreSearch">
                <i class="fa fa-chevron-down">&nbsp;</i>更多
            </button>
            <button class="layui-btn search-btn" table-id="activityTable" lay-submit="" lay-filter="search">
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
<@shiro.hasPermission name="activity:save">
    <button class="layui-btn" onclick="addPage('/activity/add')">
        <i class="fa fa-plus">&nbsp;</i>增加
    </button>
</@shiro.hasPermission>
<@shiro.hasPermission name="activity:update">
    <button class="layui-btn" onclick="editPage('activityTable','/activity/edit')">
        <i class="fa fa-pencil-square-o">&nbsp;</i>修改
    </button>
    <button class="layui-btn layui-btn-green"
            onclick="updateState('批量启用','activityTable','/activity/enable')">
        <i class="fa fa-check-square-o">&nbsp;</i>启用
    </button>
    <button class="layui-btn  layui-btn-danger"
            onclick="updateState('批量禁用','activityTable','/activity/limit')">
        <i class="fa fa-expeditedssl">&nbsp;</i>禁用
    </button>
</@shiro.hasPermission>
<@shiro.hasPermission name="activity:delete">
    <button class="layui-btn layui-btn-delete"
            onclick="deleteBatch('批量删除','activityTable','/activity/delete');">
        <i class="fa fa-trash-o">&nbsp;</i>删除
    </button>
</@shiro.hasPermission>

</div>
<div class="layui-form ">
    <table class="layui-table" id="activityTable" cyType="pageGrid"
           cyProps="url:'/activity/listData',checkbox:'true',pageColor:'#2991d9'">
        <thead>
        <tr>
            <!--复选框-->
            <th width="1%" param="{type:'checkbox'}">
                <input type="checkbox" lay-skin="primary" lay-filter="allChoose">
            </th>
            <!--isPrimary：是否是主键-->
            <th width="10%" param="{name:'activityId',isPrimary:'true',hide:'true'}"></th>

            <th width="10%" param="{name:'featuredImage',render:'Render.renderImage'}">特色图片</th>

            <th width="10%" param="{name:'activityType'}">分类</th>

            <th width="10%" param="{name:'title',sort:'true'}">标题</th>

            <th width="10%" param="{name:'spaceName',sort:'false'}">少儿空间</th>

            <th width="10%" param="{name:'signNumber',sort:'false'}">活动编码</th>

            <th width="10%" param="{name:'integral',sort:'false'}">经验值</th>

            <th width="10%" param="{name:'periodFrom',sort:'true',render:'Render.customDate'}">期间开始</th>

            <th width="10%" param="{name:'periodTo',sort:'true',render:'Render.customDate'}">期间结束</th>

            <th width="10%" param="{name:'activityStatus'}">进度</th>

            <th width="10%" param="{name:'numberOfPeople',sort:'true'}">人数</th>

            <th width="10%" param="{name:'totalUsers',render:'Render.viewAttendUsers'}">活动报名人数</th>

            <th width="10%" param="{name:'featuredYn',render:'Render.featuredYn'}">热门活动设置</th>

            <#--<th width="10%" param="{name:'weight',sort:'true'}">加权因子</th>-->

            <!--isPrimary：渲染列-->
            <th width="10%" param="{name:'state',enumName:'StateEnum',render:'Render.customState'}">状态</th>
            <th width="10%" param="{operate:'true',buttons:'Render.state,Render.edit,Render.delete'}">操作</th>
        </tr>
        </thead>
    </table>
</div>
</body>
</html>