<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label">课程名称<span class="span_must">*</span></label>
    <div class="layui-input-normal">
        <input type="text" readonly value="${(model.title)!""}" class="layui-input">
    </div>
</div>

<#--<div class="layui-form-item">${(model.bucket)!""}-->
    <#--<label class="layui-form-label">少儿空间<span class="span_must">*</span></label>-->
    <#--<div class="layui-input-normal">-->
        <#--<#if admin.userId == 1>-->
            <#--<div cyType="selectTool" value="${(model.spaceId)!""}" cyProps="url:'/space/getCodeValues',search:'true',filter:'demo'" name="spaceId1" class="layui-input-inline"></div>-->
        <#--<#else>-->
            <#--<input type="text" disabled value="${(admin.spaceName)!""}" class="layui-input">-->
        <#--</#if>-->
    <#--</div>-->
<#--</div>-->

<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label"><span class="span_must">*</span>内容</label>
    <div class="layui-input-normal" style="width: 70%">
        <textarea id="introduction" readonly rows="5" lay-verify="required" placeholder="请输入" class="layui-textarea">${(model.introduction)!""}</textarea>
    </div>
</div>

<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label">课程类型<span class="span_must">*</span></label>
    <div class="layui-input-normal">
        <input value="${(model.categoryCode)!""}" id="categoryCode" cyType="treeTool" cyProps="url:'/classroomSubject/select',name:'categoryCode'" placeholder="请选择分类" class="layui-input"/>
    </div>
</div>

<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label">特色图片<span class="span_must">*</span></label>
    <div class="layui-input-normal">
        <input type="hidden" value="${(model.featuredImage)!""}" id="featuredImage">
        <div id="${(model.featuredImage)!""}_show"></div>
        <#--<div cyType="HuploadTool" cyProps="name:'featuredImage',url:'/getData/uploaders/',uploadId:'${(model.featuredImage)!""}',btnName:'上传文件',uploadBtn:'false',deleteBtn:'true'"></div>-->
    </div>
</div>

<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label">视频<span class="span_must">*</span></label>
    <div class="layui-input-normal">
        <input type="hidden" value="${(model.videoFile)!""}" id="videoFile">
        <div id="${(model.videoFile)!""}_show"></div>
        <#--<div cyType="HuploadTool" cyProps="name:'videoFile',url:'/getData/uploaders/',uploadId:'${(model.videoFile)!""}',btnName:'上传文件',uploadBtn:'false',deleteBtn:'true'"></div>-->
    </div>
</div>

<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label">加权因子<span class="span_must">*</span></label>
    <div class="layui-input-normal">
        <input type="number" name="weight" maxlength="11" lay-verify="number" value="${(model.weight)!"0"}" placeholder="请输入加权因子" class="layui-input">
    </div>
</div>

<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label">热门教学设置<span class="span_must">*</span></label>
    <div class="layui-input-normal">
        <input type="checkbox" name="featuredYn" title="热门教学设置" value="Y" lay-skin="primary" <#if model?? && model.featuredYn?? && model.featuredYn == 'Y'>checked</#if>>
    </div>
</div>

<div class="layui-form-item">
    <label class="layui-form-label">状态</label>
    <div cyType="radioTool" cyProps="enumName:'StateEnum'" name="state" value="${(model.state)!"1"}" class="layui-input-inline"></div>
</div>


<script>
    $(document).ready(function () {
        var featuredImage = $('#featuredImage').val();
        $.ajax({
            type: "POST",  //提交方式
            url: "/getData/getFile/" + featuredImage,//路径
            dataType: "json",
            success: function (result) {//返回数据根据结果进行相应的处理
                if (result.code == '0') {
                    var list = result.fileList;
                    for (var i = 0; i < list.length; i++) {
                        list[i].img= list[i].url;
                        //是否显示删除按钮
                        var $deleteBtn="<i></i>";
                        $deleteBtn='<i class="fa fa-trash-o" fileId="' + list[i].id + '" onclick="removeHImg(this)">&nbsp;</i>';
                        $("#" + featuredImage + "_show").append('<div class="float-left"> ' +
                                '<img src="' + list[i].img + '" class="layui-upload-img"> ' +
                                '<div style="margin-bottom: 10px;width:120px;white-space: nowrap;text-overflow:ellipsis;overflow:hidden;">' + $deleteBtn+
                                '<span onclick="downloadFile(\'' + list[i].url + '\')" class="click-span" title="' + list[i].fileName + '" >' + list[i].fileName + '</span>' +
                                '</div> </div>');
                    }

                }
            }
        });
        var videoFile = $('#videoFile').val();
        $.ajax({
            type: "POST",  //提交方式
            url: "/getData/getFile/" + videoFile,//路径
            dataType: "json",
            success: function (result) {//返回数据根据结果进行相应的处理
                if (result.code == '0') {
                    var list = result.fileList;
                    for (var i = 0; i < list.length; i++) {
                        list[i].img= list[i].url;
                        //是否显示删除按钮
                        var $deleteBtn="<i></i>";
                        $deleteBtn='<i class="fa fa-trash-o" fileId="' + list[i].id + '" onclick="removeHImg(this)">&nbsp;</i>';
                        $("#" + videoFile + "_show").append('<div class="float-left"> ' +
                                '<a href="'+list[i].url+'" target="_blank"><img src="/statics/img/videosIcon.png" class="layui-upload-img"></a>' +
                                '<div style="margin-bottom: 10px;width:120px;white-space: nowrap;text-overflow:ellipsis;overflow:hidden;">' + $deleteBtn+
                                '<span onclick="downloadFile(\'' + list[i].url + '\')" class="click-span" title="' + list[i].fileName + '" >' + list[i].fileName + '</span>' +
                                '</div> </div>');
                    }

                }
            }
        });
    });
    function removeHImg(obj) {
        confirm("确认删除图片?",function(){
            var fileId = $(obj).attr("fileId");
            $.ajax({
                type: "POST",  //提交方式
                url: "/getData/deleteFile/" + fileId,//路径
                dataType: "json",
                success: function (result) {//返回数据根据结果进行相应的处理
                    if (result.code == '0') {
                        $(obj).parents(".float-left").remove();
                        parent.layer.msg('删除成功 !', {icon: 1});
                    }
                },
                error: function () {
                    parent.layer.msg("系统繁忙", {icon: 5});
                }
            });
        });
    }
</script>