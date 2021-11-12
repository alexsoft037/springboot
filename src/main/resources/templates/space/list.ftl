<#--  xiaoer 2018-08-02 21:56:04-->
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
        html, body{
            height: 100%;
        }
        .map-container{
            height: 100%;
        }
    </style>
    <script type="text/javascript" src="/space/js/list.js"></script>
    <script type="text/javascript"
            src="http://api.map.baidu.com/api?v=2.0&ak=${(BaiduApiKey)!""}"> //The reference to v2.0: src="http://api.map.baidu.com/api?v=2.0&ak=your key"
    </script>
</head>
<body>
<div class="map-container">
    <div class="areaMap" id="areaMap" style="width:100%; height:100%;"></div>
</div>
<form class="layui-form " action="" style="margin-top: 15px">
    <div class="layui-form-item">
        <label class="layui-form-label">名称:</label>
        <div class="layui-input-inline">
            <input type="text" name="" placeholder="请输入名称" class="layui-input">
        </div>

        <div class="layui-input-normal">
            <button class="layui-btn layui-btn-green" lay-submit="" lay-filter="moreSearch">
                <i class="fa fa-chevron-down">&nbsp;</i>更多
            </button>
            <button class="layui-btn search-btn" table-id="spaceTable" lay-submit="" lay-filter="search">
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
<@shiro.hasPermission name="space:save">
    <button class="layui-btn" onclick="addPage('/space/add')">
        <i class="fa fa-plus">&nbsp;</i>增加
    </button>
</@shiro.hasPermission>
<@shiro.hasPermission name="space:update">
    <button class="layui-btn" onclick="editPage('spaceTable','/space/edit')">
        <i class="fa fa-pencil-square-o">&nbsp;</i>修改
    </button>
    <button class="layui-btn layui-btn-green" onclick="updateState('批量启用','spaceTable','/space/enable')">
        <i class="fa fa-check-square-o">&nbsp;</i>启用
    </button>
    <button class="layui-btn  layui-btn-danger" onclick="updateState('批量禁用','spaceTable','/space/limit')">
        <i class="fa fa-expeditedssl">&nbsp;</i>禁用
    </button>
</@shiro.hasPermission>
<@shiro.hasPermission name="space:delete">
    <button class="layui-btn layui-btn-delete" onclick="deleteBatch('批量删除','spaceTable','/space/delete');">
        <i class="fa fa-trash-o">&nbsp;</i>删除
    </button>
</@shiro.hasPermission>

</div>
<div class="layui-form">
    <table class="layui-table" id="spaceTable" cyType="pageGrid"
           cyProps="url:'/space/listData',checkbox:'true',pageColor:'#2991d9'">
        <thead>
        <tr>
            <!--复选框-->
            <th width="1%" param="{type:'checkbox'}">
                <input type="checkbox" lay-skin="primary" lay-filter="allChoose">
            </th>
            <!--isPrimary：是否是主键-->
            <th width="10%" param="{name:'spaceId',isPrimary:'true',hide:'true'}"></th>


            <th width="10%" param="{name:'spaceName'}">空间名</th>

            <th width="10%" param="{name:'level'}">水平</th>

            <th width="10%" param="{name:'upperId'}">亲空间</th>

            <th width="10%" param="{name:'address'}">地址</th>

            <th width="10%" param="{name:'area'}">面积</th>

            <th width="10%" param="{name:'state',enumName:'StateEnum',render:'Render.customState'}">状态</th>
            <th width="10%" param="{operate:'true',buttons:'Render.state,Render.edit,Render.delete'}">操作</th>
        </tr>
        </thead>
    </table>
</div>


<script type="text/javascript">
    var map = new BMap.Map("areaMap");
    var point = new BMap.Point(116.331398, 39.897445);
    map.addControl(new BMap.NavigationControl());
    map.addControl(new BMap.ScaleControl());
    map.addControl(new BMap.OverviewMapControl());
    map.addControl(new BMap.MapTypeControl());
    map.centerAndZoom(point, 11);
    map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放
    var geolocation = new BMap.Geolocation();
    geolocation.getCurrentPosition(function (r) {
        if (this.getStatus() == BMAP_STATUS_SUCCESS) {
            // var mk = new BMap.Marker(r.point);
            // map.addOverlay(mk);
            // map.panTo(r.point);
            console.log('您的位置：' + r.point.lng + ',' + r.point.lat);
            var point1 = new BMap.Point(r.point.lng, r.point.lat);

            map.centerAndZoom(point1, 15);
        }
        else {
            console.log('failed' + this.getStatus());
        }
    }, {enableHighAccuracy: true})

    $(document).ready(function () {
        $.ajax({
            method: "POST",
            url: "/space/allListData",
            data: {}
        }).done(function (data) {
            var spaces = data.page;
            for (var i = 0; i < spaces.length; i++){
                var p = new BMap.Point(spaces[i].longitude, spaces[i].latitude);
                marker = new BMap.Marker(p);
                map.addOverlay(marker);
            }
        });
    });
</script>
</body>
</html>