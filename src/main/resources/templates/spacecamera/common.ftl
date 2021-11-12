<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label"><span class="span_must">*</span>标题</label>
    <div class="layui-input-normal">
        <input type="text" name="title" maxlength="256" lay-verify="required"
               value="${(model.title)!""}" placeholder="请输入" class="layui-input">
    </div>
</div>

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
    <label class="layui-form-label"><span class="span_must">*</span>设备序列号</label>
    <div class="layui-input-normal">
        <input type="text" name="serialNo" maxlength="256" lay-verify="required"
               value="${(model.serialNo)!""}" placeholder="请输入" class="layui-input">
    </div>
</div>

<div class="layui-form-item">${(model.bucket)!""}
  <label class="layui-form-label"><span class="span_must">*</span>url地址</label>
  <div class="layui-input-normal">
      <input type="text" name="url" maxlength="1024" lay-verify="required" value="${(model.url)!""}"
             placeholder="请输入" class="layui-input">
  </div>
</div>

<div class="layui-form-item">${(model.bucket)!""}
  <label class="layui-form-label"><span class="span_must">*</span>照片</label>
  <div class="layui-input-normal">
      <div cyType="HuploadTool"
           cyProps="name:'img',url:'/getData/uploaders/',uploadId:'${(model.img)!""}',btnName:'上传文件',uploadBtn:'true',deleteBtn:'true'"></div>
  </div>
</div>

<div class="layui-form-item">${(model.bucket)!""}
  <label class="layui-form-label"><span class="span_must">*</span>地址</label>
  <div class="layui-input-normal">
      <input type="text" name="address" id="address" lay-verify="required"
             value="${(model.address)!""}" placeholder="请输入" class="layui-input">
  </div>
</div>

<input type="hidden" name="district" id="district" value="${(model.district)!""}">

<input type="hidden" name="city" id="city" value="${(model.city)!""}">

<input type="hidden" name="province" id="province" value="${(model.province)!""}">

<input type="hidden" name="street" id="street" value="${(model.street)!""}">

  <div class="layui-form-item">${(model.bucket)!""}
      <label class="layui-form-label"><span class="span_must">*</span>纬度</label>
      <div class="layui-input-normal">
          <input type="number" name="latitude" id="latitude" lay-verify="required" value="${(model.latitude)!""}"
                 placeholder="请输入" class="layui-input">
      </div>
  </div>

  <div class="layui-form-item">${(model.bucket)!""}
      <label class="layui-form-label"><span class="span_must">*</span>经度</label>
      <div class="layui-input-normal">
          <input type="number" name="longitude" id="longitude" lay-verify="required" value="${(model.longitude)!""}"
                 placeholder="请输入" class="layui-input">
      </div>
  </div>

  <div class="layui-form-item">${(model.bucket)!""}
      <label class="layui-form-label"><span class="span_must"></span>备注</label>
      <div class="layui-input-normal">
          <textarea name="remark" id="remark" rows="5" placeholder="请输入"
                    class="layui-textarea">${(model.remark)!""}</textarea>
      </div>
  </div>


  <div class="layui-form-item">
      <label class="layui-form-label">状态</label>
      <div cyType="radioTool" cyProps="enumName:'StateEnum'" name="state" value="${(model.state)!"1"}"
           class="layui-input-inline"></div>
  </div>


<div class="layui-container">
    <div class="layui-row">
        <div class="areaMap" id="areaMap" style="width:100%; height:100%;"></div>
    </div>
</div>

<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=${(BaiduApiKey)!""}"></script>
<script type="text/javascript">
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

        var myGeo = new BMap.Geocoder();
        myGeo.getLocation(new BMap.Point(e.point.lng, e.point.lat), function (result) {
            if (result) {
                $("#address").val(result.address);
                $("#city").val(result.addressComponents.city);
                $("#district").val(result.addressComponents.district);
                $("#province").val(result.addressComponents.province);
                $("#street").val(result.addressComponents.street);
//                console.log('getLocation result == ', result);
            }
        });
        if (marker) {
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
        if (!lat || !lng) {
            var geolocation = new BMap.Geolocation();
            geolocation.getCurrentPosition(function (r) {
                if (this.getStatus() == BMAP_STATUS_SUCCESS) {

                    var point1 = new BMap.Point(r.point.lng, r.point.lat);
                    if (marker) {
                        map.removeOverlay(marker);
                    }

                    var address = r.address.province + r.address.city + r.address.district + r.address.street + r.address.street_number;
                    $("#address").val(address);
                    $("#city").val(r.address.city);
                    $("#district").val(r.address.district);
                    $("#province").val(r.address.province);
                    $("#street").val(r.address.street);
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
            var dragendGeo = new BMap.Geocoder();
            dragendGeo.getLocation(new BMap.Point(event.point.lng, event.point.lat), function (result) {
                if (result) {
                    $("#address").val(result.address);
                    $("#city").val(result.addressComponents.city);
                    $("#district").val(result.addressComponents.district);
                    $("#province").val(result.addressComponents.province);
                    $("#street").val(result.addressComponents.street);
                }
            });
            $("#latitude").val(event.point.lat);
            $("#longitude").val(event.point.lng);
        });
    }

    $(document).ready(function () {
        bindLatLngInput();
        $('#latitude').on('input', function () {
            getGeoLocation();
            bindLatLngInput();
        });
        $('#longitude').on('input', function () {
            getGeoLocation();
            bindLatLngInput();
        });

        function getGeoLocation() {
            var lat = $('#latitude').val();
            var lng = $('#longitude').val();
            if (lat && lng) {
                var myGeo = new BMap.Geocoder();
                myGeo.getLocation(new BMap.Point(lng, lat), function (result) {
                    if (result) {
                        $("#address").val(result.address);
                        $("#city").val(result.addressComponents.city);
                        $("#district").val(result.addressComponents.district);
                        $("#province").val(result.addressComponents.province);
                        $("#street").val(result.addressComponents.street);
//                        console.log('getLocation result == ', result);
                    }
                });
            }
        }

        function bindLatLngInput() {
            var lat = $('#latitude').val();
            var lng = $('#longitude').val();
            if (lat && lng) {
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