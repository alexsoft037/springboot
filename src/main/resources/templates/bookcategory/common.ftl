<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label">分类名称<span class="span_must">*</span></label>
    <div class="layui-input-normal">
        <input type="text" name="categoryName" maxlength="256" lay-verify="required" value="${(model.categoryName)!""}" placeholder="请输入" class="layui-input">
    </div>
</div>

<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label">分类值<span class="span_must">*</span></label>
    <div class="layui-input-normal">
        <input type="text" name="categoryCode" maxlength="32" lay-verify="required" value="${(model.categoryCode)!""}" placeholder="请输入" class="layui-input">
    </div>
</div>

<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label">分类图标<span class="span_must">*</span></label>
    <div class="layui-input-normal">
        <div cyType="HuploadTool" cyProps="name:'categoryIcon',url:'/getData/uploaders/',uploadId:'${(model.categoryIcon)!""}',btnName:'上传文件',uploadBtn:'true',deleteBtn:'true'"></div>
        <#--<input type="text" name="categoryIcon" maxlength="64" lay-verify="required" value="${(model.categoryIcon)!""}" placeholder="请输入" class="layui-input">-->
    </div>
</div>

<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label">上级分类<span class="span_must">*</span></label>
    <div class="layui-input-normal">
        <input value="${(model.upperCode)!""}" id="upperCode" cyType="treeTool" cyProps="url:'/bookcategory/select',name:'upperCode'" placeholder="请选择上级分类" class="layui-input"/>
        <#--<input type="text" name="upperCode" maxlength="32" lay-verify="required" value="${(model.upperCode)!""}" placeholder="请输入" class="layui-input">-->
    </div>
</div>

<div class="layui-form-item">
    <label class="layui-form-label">状态</label>
    <div cyType="radioTool" cyProps="enumName:'StateEnum'" name="state"
         value="${(model.state)!"1"}" class="layui-input-inline"></div>
</div>