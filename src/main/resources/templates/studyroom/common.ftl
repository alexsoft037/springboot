<style>
    .ck-rounded-corners .ck.ck-editor__main>.ck-editor__editable, .ck.ck-editor__main>.ck-editor__editable.ck-rounded-corners{
        min-height: 380px;
    }
</style>
<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=${(BaiduApiKey)!""}"></script>

<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label"><span class="span_must">*</span>分类</label>
    <div class="layui-input-normal">
        <div cyType="selectTool" cyProps="url:'/getData/getCodeValues?codeName=studyRoomType',search:'true',filter:'demo'" name="srTypeCode" value="${(model.srTypeCode)!""}" class="layui-input-inline"></div>
    </div>
</div>

<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label"><span class="span_must">*</span>标题</label>
    <div class="layui-input-normal">
        <input type="text" name="title" maxlength="256" lay-verify="required" value="${(model.title)!""}" placeholder="请输入" class="layui-input">
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
    <label class="layui-form-label"><span class="span_must">*</span>特色图片</label>
    <div class="layui-input-normal">
        <div cyType="HuploadTool" cyProps="name:'featuredImage',url:'/getData/uploaders/',uploadId:'${(model.featuredImage)!""}',btnName:'上传文件',uploadBtn:'true',deleteBtn:'true'"></div>
    </div>
</div>

<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label"><span class="span_must">*</span>内容</label>
    <div class="layui-input-normal" style="width: 70%">
        <textarea name="content" id="content" rows="5" lay-verify="required" placeholder="请输入" class="layui-textarea">${(model.content)!""}</textarea>
    </div>
</div>

<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label"><span class="span_must">*</span>上课时间</label>
    <div class="layui-input-normal">
        <input type="text" name="startTime1" id="startTime" maxlength="25" placeholder="yyyy-MM-dd HH:mm:ss" lay-verify="required" value="${(model.startTime?string["yyyy-MM-dd HH:mm:ss"])!""}" lay-verify="datetime" class="layui-input">
    </div>
</div>

<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label"><span class="span_must">*</span>进度</label>
    <div class="layui-input-normal">
        <div cyType="selectTool" cyProps="url:'/getData/getCodeValues?codeName=studyRoomStatus',search:'true',filter:'demo'" name="srStatusCode" value="${(model.srStatusCode)!""}" class="layui-input-inline"></div>
    </div>
</div>

<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label"><span class="span_must">*</span>课程积分</label>
    <div class="layui-input-normal">
        <input type="number" name="integral" maxlength="11" lay-verify="number" value="${(model.integral)!"0"}" placeholder="请输入" class="layui-input">
    </div>
</div>

<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label"><span class="span_must">*</span>人数</label>
    <div class="layui-input-normal">
        <input type="number" name="numberOfPeople" maxlength="11" lay-verify="number" value="${(model.numberOfPeople)!"0"}" placeholder="请输入" class="layui-input">
    </div>
</div>

<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label"><span class="span_must">*</span>地址</label>
    <div class="layui-input-normal">
        <input type="text" name="location" id="location" maxlength="128" lay-verify="required" value="${(model.location)!""}" placeholder="请输入" class="layui-input">
    </div>
</div>

<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label"><span class="span_must">*</span>纬度</label>
    <div class="layui-input-normal">
        <input type="number" name="latitude" id="latitude" maxlength="64" lay-verify="required" value="${(model.latitude)!"0"}" placeholder="请输入" class="layui-input">
    </div>
</div>

<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label"><span class="span_must">*</span>经度</label>
    <div class="layui-input-normal">
        <input type="number" name="longitude" id="longitude" maxlength="64" lay-verify="required" value="${(model.longitude)!"0"}" placeholder="请输入" class="layui-input">
    </div>
</div>

<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label"><span class="span_must">*</span>加权因子</label>
    <div class="layui-input-normal">
        <input type="number" name="weight" maxlength="11" lay-verify="required" value="${(model.weight)!"0"}" placeholder="请输入" class="layui-input">
    </div>
</div>

<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label"><span class="span_must">*</span>热门教学设置</label>
    <div class="layui-input-normal">
        <input type="checkbox" name="featuredYn" title="热门教学设置" value="Y" lay-skin="primary" <#if model?? && model.featuredYn?? && model.featuredYn == 'Y'>checked</#if>>
    </div>
</div>

<div class="layui-form-item">
    <label class="layui-form-label">状态</label>
    <div cyType="radioTool" cyProps="enumName:'StateEnum'" name="state"
         value="${(model.state)!"1"}" class="layui-input-inline"></div>
</div>

<div class="layui-container">
    <div class="layui-row">
        <div class="areaMap" id="areaMap" style="width:100%; height:100%;"></div>
    </div>
</div>

<script src="/statics/plugins/ckeditor5/ckeditor.js"></script>
<script type="text/javascript">
    ClassicEditor.create(document.querySelector('#content'), {
        autosave: {
            save( editor ) {
                return saveDataIntro( editor.getData() );
            }
        },
        ckfinder: {
            uploadUrl: '/getData/ckfinder/'
        }
    }).then( editor => {
        saveDataIntro( editor.getData() );
    } ).catch(error => {
        console.error(error);
    });

    function saveDataIntro( data ) {
        $('#content').html(encodeURIComponent(data));
    }
</script>
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
                $("#location").val(result.address);
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
        if (!lat || !lng || lat == 0 || lng == 0){
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
                    $("#location").val(address);
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
                    $("#location").val(result.address);
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
                        $("#location").val(result.address);
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