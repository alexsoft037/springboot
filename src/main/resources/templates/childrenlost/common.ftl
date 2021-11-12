<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label">少儿空间<span class="span_must">*</span></label>
    <div class="layui-input-normal">
        <#if admin.userId == 1>
            <div cyType="selectTool" value="${(model.spaceId)!""}" cyProps="url:'/space/getCodeValues',search:'true',filter:'demo'" name="spaceId1" class="layui-input-inline"></div>
        <#else>
            <input type="text" disabled value="${(admin.spaceName)!""}" class="layui-input">
        </#if>
    </div>
</div>

<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label"><span class="span_must">*</span>姓名</label>
    <div class="layui-input-normal">
        <div cyType="selectTool" cyProps="url:'/userchildren/autocomplete',search:'true',filter:'demo'" name="childrenId1" value="${(model.childrenId)!""}" class="layui-input-inline"></div>
    </div>
</div>

<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label"><span class="span_must"></span>照片</label>
    <div class="layui-input-normal">
        <div cyType="HuploadTool" cyProps="name:'photo',url:'/getData/uploaders/',uploadId:'${(model.photo)!""}',btnName:'上传文件',uploadBtn:'true',deleteBtn:'true'"></div>
    </div>
</div>

<div class="layui-form-item">${(model.bucket)!""}
  <label class="layui-form-label"><span class="span_must">*</span>标题</label>
  <div class="layui-input-normal">
      <input type="text" name="subject" maxlength="256" lay-verify="required" value="${(model.subject)!""}" placeholder="请输入" class="layui-input">
  </div>
</div>

<div class="layui-form-item">${(model.bucket)!""}
  <label class="layui-form-label"><span class="span_must">*</span>说明</label>
  <div class="layui-input-normal">
      <textarea name="description" id="description" rows="5" lay-verify="required" placeholder="请输入" class="layui-textarea">${(model.description)!""}</textarea>
  </div>
</div>

<div class="layui-form-item">${(model.bucket)!""}
  <label class="layui-form-label"><span class="span_must">*</span>失去的时间</label>
  <div class="layui-input-normal">
      <input type="text" name="lostTime1" id="lostTime" maxlength="25" placeholder="yyyy-MM-dd HH:mm:ss" lay-verify="required" value="${(model.lostTime?string["yyyy-MM-dd HH:mm:ss"])!""}" lay-verify="datetime" class="layui-input">
  </div>
</div>

<div class="layui-form-item">${(model.bucket)!""}
  <label class="layui-form-label"><span class="span_must">*</span>纬度</label>
  <div class="layui-input-normal">
      <input type="number" name="latitude" id="latitude" lay-verify="required" value="${(model.latitude)!""}" placeholder="请输入" class="layui-input">
  </div>
</div>

<div class="layui-form-item">${(model.bucket)!""}
  <label class="layui-form-label"><span class="span_must">*</span>经度</label>
  <div class="layui-input-normal">
      <input type="number" name="longitude" id="longitude" lay-verify="required" value="${(model.longitude)!""}" placeholder="请输入" class="layui-input">
  </div>
</div>

<div class="layui-form-item">
  <label class="layui-form-label">状态</label>
  <div cyType="radioTool" cyProps="enumName:'StateEnum'" name="state" value="${(model.state)!"1"}" class="layui-input-inline"></div>
</div>


<div class="layui-container">
    <div class="layui-row">
        <div class="areaMap" id="areaMap" style="width:100%; height:100%;"></div>
    </div>
</div>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=${(BaiduApiKey)!""}"></script>
<script type="text/javascript">
    layui.use('laydate', function () {

        // 时间选择器
        var laydate = layui.laydate;

        //时间选择器
        laydate.render({
            elem: '#lostTime',
            type: 'datetime',
            format: 'yyyy-MM-dd HH:mm:ss'
        });
    });

    var dialog, marker;
    var map = new BMap.Map("areaMap");
    map.addControl(new BMap.NavigationControl());
    map.addControl(new BMap.ScaleControl());
    map.addControl(new BMap.OverviewMapControl());
    map.addControl(new BMap.MapTypeControl());

    var point = new BMap.Point(116.331398, 39.897445);
    map.centerAndZoom(point, 15);
    map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放
    map.addEventListener("click", function (e) {
        if (marker){
            map.removeOverlay(marker);
        }
        $("#latitude").val(e.point.lat);
        $("#longitude").val(e.point.lng);
        var p = new BMap.Point(e.point.lng, e.point.lat);
        marker = new BMap.Marker(p, {enableDragging: true});
        map.addOverlay(marker);
        bindMarker(marker);
        map.centerAndZoom(p, 15);
//        marker.setLabel(label);
//        dialog.dialog( 'close' );

    });

    function getCurrentLocaion() {
        var lat = $('#latitude').val();
        var lng = $('#longitude').val();
        if (!lat || !lng){
            var geolocation = new BMap.Geolocation();
            geolocation.getCurrentPosition(function (r) {
                if (this.getStatus() == BMAP_STATUS_SUCCESS) {
                    var point1 = new BMap.Point(r.point.lng, r.point.lat);
                    if (marker){
                        map.removeOverlay(marker);
                    }
                    $("#latitude").val(r.point.lat);
                    $("#longitude").val(r.point.lng);
                    marker = new BMap.Marker(point1, {enableDragging: true});
                    map.addOverlay(marker);
                    bindMarker(marker);
                    map.centerAndZoom(point1, 15);
                }
                else {
                    console.log('failed' + this.getStatus());
                }
            }, {enableHighAccuracy: true})
        }
    }


    function bindMarker(marker) {
        marker.addEventListener("dragend", function (event) {
            $("#latitude").val(event.point.lat);
            $("#longitude").val(event.point.lng);
        });
    }
    $(document).ready(function () {
        bindLatLngInput();
        $('#latitude').on('input', function() {
            getGeoLocation();
            bindLatLngInput();
        });
        $('#longitude').on('input', function() {
            getGeoLocation();
            bindLatLngInput();
        });

        function getGeoLocation() {
            var lat = $('#latitude').val();
            var lng = $('#longitude').val();
        }

        function bindLatLngInput() {
            var lat = $('#latitude').val();
            var lng = $('#longitude').val();
            if (lat && lng){
                if (marker) {
                    map.removeOverlay(marker);
                }
                var p = new BMap.Point(lng, lat);
                marker = new BMap.Marker(p, {enableDragging: true});
                map.addOverlay(marker);
                bindMarker(marker);
                map.centerAndZoom(p, 15);
            }
        }

        getCurrentLocaion();
    });
</script>