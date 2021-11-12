<style>
    .ck-rounded-corners .ck.ck-editor__main>.ck-editor__editable, .ck.ck-editor__main>.ck-editor__editable.ck-rounded-corners{
        min-height: 380px;
    }
</style>
<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label"><span class="span_must">*</span>分类</label>
    <div class="layui-input-normal">
        <div cyType="selectTool" cyProps="url:'/getData/getCodeValues?codeName=reportType',search:'true',filter:'demo'" name="reportTypeCode" value="${(model.reportTypeCode)!""}" class="layui-input-inline"></div>
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
    <label class="layui-form-label"><span class="span_must">*</span>标题</label>
    <div class="layui-input-normal">
        <input type="text" name="title" maxlength="256" lay-verify="required" value="${(model.title)!""}" placeholder="请输入" class="layui-input">
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

<div class="layui-form-item" id="author-box" style="display: none;">${(model.bucket)!""}
    <label class="layui-form-label"><span class="span_must"></span>小记者</label>
    <div class="layui-input-normal">
        <input type="text" name="authorName" maxlength="256" value="${(model.authorName)!""}" placeholder="请输入" class="layui-input">
    </div>
</div>

<input type="hidden" id="author-name" value="${(model.createUser)!""}">

<div class="layui-form-item">
    <label class="layui-form-label">状态</label>
    <div cyType="radioTool" cyProps="enumName:'StateEnum'" name="state" value="${(model.state)!"1"}" class="layui-input-inline"></div>
</div>


<script src="/statics/plugins/ckeditor5/ckeditor.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        console.log('authorName')
        var authorName = $('#author-name').val();
        if (authorName == "" || authorName == null || authorName.length < 1){
            console.log('authorName: ',authorName);
            $('#author-box').css("display","block");
        }
    });
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