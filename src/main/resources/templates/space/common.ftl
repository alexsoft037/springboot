<style>
    .ck-rounded-corners .ck.ck-editor__main>.ck-editor__editable, .ck.ck-editor__main>.ck-editor__editable.ck-rounded-corners{
        min-height: 380px;
    }
</style>

<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=${(BaiduApiKey)!""}"></script>

<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label">空间名<span class="span_must">*</span></label>
    <div class="layui-input-normal">
        <input type="text" name="spaceName" maxlength="256" lay-verify="required"
               value="${(model.spaceName)!""}" placeholder="请输入" class="layui-input">
    </div>
</div>

<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label">描述<span class="span_must">*</span></label>
    <div class="layui-input-normal">
        <textarea name="description" rows="5" lay-verify="required" placeholder="请输入" class="layui-textarea">${(model.description)!""}</textarea>
    </div>
</div>

<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label">水平<span class="span_must">*</span></label>
    <div class="layui-input-normal">
        <input type="number" name="level" maxlength="32" lay-verify="required" value="${(model.level)!""}" placeholder="请输入" class="layui-input">
    </div>
</div>

<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label">亲空间<span class="span_must"></span></label>
    <div class="layui-input-normal">
        <input type="text" name="upperId" maxlength="11" value="${(model.upperId)!""}" placeholder="请输入" class="layui-input">
    </div>
</div>

<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label">地址<span class="span_must">*</span></label>
    <div class="layui-input-normal">
        <input type="text" name="address" id="address" maxlength="256" lay-verify="required" value="${(model.address)!""}" placeholder="请输入" class="layui-input">
    </div>
</div>


<input type="hidden" name="district" id="district" value="${(model.district)!""}">

<input type="hidden" name="city" id="city"  value="${(model.city)!""}">

<input type="hidden" name="province" id="province"  value="${(model.province)!""}">

<input type="hidden" name="street" id="street"  value="${(model.street)!""}">

<input type="hidden" name="streetNumber" id="streetNumber"  value="${(model.streetNumber)!""}">


<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label">纬度<span class="span_must">*</span></label>
    <div class="layui-input-normal">
        <input type="number" name="latitude" id="latitude" maxlength="32" lay-verify="required" value="${(model.latitude)!""}" placeholder="请输入" class="layui-input">
    </div>
</div>

<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label">经度<span class="span_must">*</span></label>
    <div class="layui-input-normal">
        <input type="number" name="longitude" id="longitude" maxlength="32" lay-verify="required" value="${(model.longitude)!""}" placeholder="请输入" class="layui-input">
    </div>
</div>

<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label">面积<span class="span_must"></span></label>
    <div class="layui-input-normal">
        <input type="number" name="area" maxlength="64" value="${(model.area)!""}" placeholder="请输入" class="layui-input">
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
                $("#streetNumber").val(result.addressComponents.streetNumber);
//                console.log('getLocation result == ', result);
            }
        });
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
                    // var mk = new BMap.Marker(r.point);
                    // map.addOverlay(mk);
                    // map.panTo(r.point);
//            console.log('您的位置：' + r.point.lng + ',' + r.point.lat);
                    var point1 = new BMap.Point(r.point.lng, r.point.lat);
                    if (marker){
                        map.removeOverlay(marker);
                    }
//                    console.log('getCurrentPosition', r);
                    var address = r.address.province+r.address.city+r.address.district+r.address.street+r.address.street_number;
                    $("#address").val(address);
                    $("#city").val(r.address.city);
                    $("#district").val(r.address.district);
                    $("#province").val(r.address.province);
                    $("#street").val(r.address.street);
                    $("#streetNumber").val(r.address.streetNumber);
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
                    $("#streetNumber").val(result.addressComponents.streetNumber);
                }
            });
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
            if (lat && lng){
                var myGeo = new BMap.Geocoder();
                myGeo.getLocation(new BMap.Point(lng, lat), function (result) {
                    if (result) {
                        $("#address").val(result.address);
                        $("#city").val(result.addressComponents.city);
                        $("#district").val(result.addressComponents.district);
                        $("#province").val(result.addressComponents.province);
                        $("#street").val(result.addressComponents.street);
                        $("#streetNumber").val(result.addressComponents.streetNumber);
//                        console.log('getLocation result == ', result);
                    }
                });
            }
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
//        dialog = $( "#dialog-form" ).dialog({
//            autoOpen: false,
//            height: 400,
//            width: 350,
//            modal: true,
//            buttons: {
//                Cancel: function() {
//                    dialog.dialog( "close" );
//                }
//            },
//            close: function() {
//            }
//        });
//       $("#address").click(function () {
//           dialog.dialog( 'open' );
//       });

        getCurrentLocaion();
    });
</script>