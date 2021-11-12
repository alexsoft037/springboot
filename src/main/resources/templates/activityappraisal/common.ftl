<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label"><span class="span_must">*</span>标题</label>
    <div class="layui-input-normal">
        <input type="text" name="title" id="title" maxlength="256" lay-verify="required" value="${(model.title)!""}" placeholder="请输入" class="layui-input">
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

<#--<div class="layui-form-item">${(model.bucket)!""}-->
    <#--<label class="layui-form-label"><span class="span_must">*</span>内容</label>-->
    <#--<div class="layui-input-normal">-->
        <#--<div cyType="HuploadTool" cyProps="name:'contentFile',url:'/getData/uploaders/',uploadId:'${(model.contentFile)!""}',btnName:'上传URL地址',uploadBtn:'true',deleteBtn:'true'"></div>-->
    <#--</div>-->
<#--</div>-->

<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label"><span class="span_must"></span>选择URL地址</label>
    <div class="layui-input-normal">
        <div cyType="selectTool" cyProps="url:'/htmlfile/getCodeValues',search:'true',filter:'htmlId'" name="htmlId1" value="${(model.htmlId)!""}" class="layui-input-inline"></div>
    </div>
</div>

<#--<div class="layui-form-item">${(model.bucket)!""}-->
<#--<label class="layui-form-label"><span class="span_must">*</span>内容</label>-->
<#--<div class="layui-input-normal">-->
<#--<input type="text" name="bnText" lay-verify="required" value="${(model.bnText)!""}" placeholder="请输入" class="layui-input">-->
<#--</div>-->
<#--</div>-->

<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label"><span class="span_must">*</span>链接</label>
    <div class="layui-input-normal">
        <input type="text" name="contentFile" id="contentFile" lay-verify="required" value="${(model.contentFile)!""}" placeholder="请输入" class="layui-input">
    </div>
</div>

<div class="layui-form-item">
    <label class="layui-form-label">状态</label>
    <div cyType="radioTool" cyProps="enumName:'StateEnum'" name="state" value="${(model.state)!"1"}" class="layui-input-inline"></div>
</div>