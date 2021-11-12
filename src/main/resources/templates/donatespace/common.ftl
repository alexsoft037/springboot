<style>
    .ck-rounded-corners .ck.ck-editor__main>.ck-editor__editable, .ck.ck-editor__main>.ck-editor__editable.ck-rounded-corners{
        min-height: 380px;
    }
</style>
<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label"><span class="span_must">*</span>分类</label>
    <div class="layui-input-normal">
        <div cyType="selectTool" cyProps="url:'/getData/getCodeValues?codeName=donateSpaceType',search:'true',filter:'demo'" name="dsTypeCode" value="${(model.dsTypeCode)!""}" class="layui-input-inline"></div>
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
    <label class="layui-form-label"><span class="span_must">*</span>内容</label>
    <div class="layui-input-normal">
        <textarea name="description" rows="5" lay-verify="required" placeholder="请输入" class="layui-textarea">${(model.description)!""}</textarea>
    </div>
</div>

<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label"><span class="span_must">*</span>众筹目标</label>
    <div class="layui-input-normal">
        <input type="number" name="targetAmount" lay-verify="required" value="${(model.targetAmount?double?c)!"0.0"}" placeholder="请输入" class="layui-input">
    </div>
</div>

<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label"><span class="span_must">*</span>特色图片</label>
    <div class="layui-input-normal">
        <div cyType="HuploadTool" cyProps="name:'spaceImage',url:'/getData/uploaders/',uploadId:'${(model.spaceImage)!""}',btnName:'上传文件',uploadBtn:'true',deleteBtn:'true'"></div>
    </div>
</div>

<#--<div><h1>项目介绍</h1></div>-->

<#--<div class="layui-form-item">${(model.bucket)!""}-->
    <#--<label class="layui-form-label"><span class="span_must">*</span>项目介绍图片</label>-->
    <#--<div class="layui-input-normal">-->
        <#--<div cyType="HuploadTool" cyProps="name:'introImage',url:'/getData/uploaders/',uploadId:'${(model.introImage)!""}',btnName:'上传文件',uploadBtn:'true',deleteBtn:'true'"></div>-->
    <#--</div>-->
<#--</div>-->

<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label"><span class="span_must">*</span>项目介绍</label>
    <div class="layui-input-normal" style="width: 70%">
        <textarea name="introContent" id="introContent" rows="55" lay-verify="required" placeholder="请输入" class="layui-textarea">${(model.introContent)!""}</textarea>
    </div>
</div>

<#--<div><h1>项目进展</h1></div>-->
<#--<div class="layui-form-item">${(model.bucket)!""}-->
    <#--<label class="layui-form-label"><span class="span_must">*</span>项目进展图片</label>-->
    <#--<div class="layui-input-normal">-->
        <#--<div cyType="HuploadTool" cyProps="name:'processImage',url:'/getData/uploaders/',uploadId:'${(model.processImage)!""}',btnName:'上传文件',uploadBtn:'true',deleteBtn:'true'"></div>-->
    <#--</div>-->
<#--</div>-->

<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label"><span class="span_must">*</span>项目进展</label>
    <div class="layui-input-normal" style="width: 70%">
        <textarea name="processContent" rows="55" lay-verify="required" id="processContent" placeholder="请输入" class="layui-textarea">${(model.processContent)!""}</textarea>
    </div>
</div>

<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label"><span class="span_must">*</span>捐赠状态</label>
    <div class="layui-input-normal">
        <div cyType="selectTool" cyProps="url:'/getData/getCodeValues?codeName=donateSpaceStatus',search:'true',filter:'demo'" name="dsStatusCode" value="${(model.dsStatusCode)!""}" class="layui-input-inline"></div>
    </div>
</div>

<div class="layui-form-item">
    <label class="layui-form-label">状态</label>
    <div cyType="radioTool" cyProps="enumName:'StateEnum'" name="state" value="${(model.state)!"1"}" class="layui-input-inline"></div>
</div>

<script src="/statics/plugins/ckeditor5/ckeditor.js"></script>
<script type="text/javascript">
    ClassicEditor.create(document.querySelector('#introContent'), {
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
    ClassicEditor.create(document.querySelector('#processContent'), {
        autosave: {
            save( editor ) {
                return saveDataProgress( editor.getData() );
            }
        },
        ckfinder: {
            uploadUrl: '/getData/ckfinder/'
        }
    }).then( editor => {
        saveDataProgress( editor.getData() );
    } ).catch(error => {
        console.error(error);
    });

    function saveDataIntro( data ) {
        $('#introContent').html(encodeURIComponent(data));
    }
    function saveDataProgress( data ) {
        $('#processContent').html(encodeURIComponent(data));
    }
</script>