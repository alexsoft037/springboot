<style>
    .layui-upload-img{
        display: none;
    }
</style>
<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label">标题<span class="span_must">*</span></label>
    <div class="layui-input-normal">
        <input type="text" name="subject" maxlength="256" lay-verify="required" value="${(model.subject)!""}" placeholder="请输入" class="layui-input">
    </div>
</div>

<#--<div class="layui-form-item">${(model.bucket)!""}-->
    <#--<label class="layui-form-label"><span class="span_must">*</span></label>-->
    <#--<div class="layui-input-normal">-->
        <#--<input type="text" name="filePath" maxlength="1024" lay-verify="required"-->
               <#--value="${(model.filePath)!""}" placeholder="请输入" class="layui-input">-->
    <#--</div>-->
<#--</div>-->
<div class="layui-form-item">
    <label class="layui-form-label">上传URL地址<span class="span_must">*</span></label>
    <div class="layui-input-normal">
        <div cyType="uploadTool" cyProps="url:'/getData/uploader/',value:'${(model.filePath)!""}',name:'filePath',btnName:'上传URL地址',multiple:'false',type:'file'"></div>
    </div>
</div>

<div class="layui-form-item">
    <label class="layui-form-label">状态</label>
    <div cyType="radioTool" cyProps="enumName:'StateEnum'" name="state"
         value="${(model.state)!"1"}" class="layui-input-inline"></div>
</div>