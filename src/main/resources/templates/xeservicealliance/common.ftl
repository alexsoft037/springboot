
<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label"><span class="span_must">*</span>图片</label>
    <div class="layui-input-normal">
        <div cyType="HuploadTool" cyProps="name:'imgPath',url:'/getData/uploaders/',uploadId:'${(model.imgPath)!""}',btnName:'上传文件',uploadBtn:'true',deleteBtn:'true'"></div>
    </div>
</div>

<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label"><span class="span_must">*</span>标题</label>
    <div class="layui-input-normal">
        <input type="text" name="title" maxlength="256" lay-verify="required" value="${(model.title)!""}" placeholder="请输入" class="layui-input">
    </div>
</div>

<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label"><span class="span_must">*</span>颜色</label>
    <div class="layui-input-normal">
        <input type="text" name="color" id="colorPicker" maxlength="32" lay-verify="required" value="${(model.color)!""}" placeholder="请输入" class="layui-input">
    </div>
</div>

<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label"><span class="span_must"></span>内容</label>
    <div class="layui-input-normal">
        <div cyType="HuploadTool" cyProps="name:'content',url:'/getData/uploaders/',uploadId:'${(model.content)!""}',btnName:'上传文件',uploadBtn:'true',deleteBtn:'true'"></div>
    </div>
</div>

<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label"><span class="span_must"></span>url地址</label>
    <div class="layui-input-normal">
        <input type="text" name="url" value="${(model.url)!""}" placeholder="请输入" class="layui-input">
    </div>
</div>

<div class="layui-form-item">
    <label class="layui-form-label">状态</label>
    <div cyType="radioTool" cyProps="enumName:'StateEnum'" name="state" value="${(model.state)!"1"}" class="layui-input-inline"></div>
</div>

<script>
    $('#colorPicker').ColorPicker({
        onSubmit: function (hsb, hex, rgb, el) {
            $(el).val('#'+hex);
            $(el).ColorPickerHide();
        },
        onBeforeShow: function () {
            $(this).ColorPickerSetColor(this.value);
        }
    }).bind('keyup', function () {
        $(this).ColorPickerSetColor(this.value);
    });
</script>