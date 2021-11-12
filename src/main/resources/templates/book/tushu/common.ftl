<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label"><span class="span_must">*</span>类别</label>
    <div class="layui-input-normal">
        <div cyType="linkSelectTool" cyProps="url:'/bookcategory/normalList/',topId:'BCT002',name:'categoryCodes[]',idtemp:'categoryCode'" value="${(model.categoryCode)!"BCT002001"}" class="layui-input-inline"></div>
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
        <input type="text" name="subject" maxlength="256" lay-verify="required" value="${(model.subject)!""}" placeholder="请输入" class="layui-input">
    </div>
</div>

<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label"><span class="span_must">*</span>简介</label>
    <div class="layui-input-normal">
        <textarea name="introduction" rows="5" lay-verify="required" placeholder="请输入" class="layui-textarea">${(model.introduction)!""}</textarea>
    </div>
</div>

<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label"><span class="span_must">*</span>书皮</label>
    <div class="layui-input-normal">
        <div cyType="HuploadTool" cyProps="name:'cover',url:'/getData/uploaders/',uploadId:'${(model.cover)!""}',btnName:'上传文件',uploadBtn:'true',deleteBtn:'true'"></div>
    </div>
</div>

<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label"><span class="span_must">*</span>书文件</label>
    <div class="layui-input-normal">
        <div cyType="HuploadTool" cyProps="name:'bookFile',url:'/getData/uploadBook/',uploadId:'${(model.bookFile)!""}',btnName:'上传文件',uploadBtn:'true',deleteBtn:'true'"></div>
    </div>
</div>

<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label"><span class="span_must">*</span>作者</label>
    <div class="layui-input-normal">
        <input type="text" name="author" maxlength="32" lay-verify="required" value="${(model.author)!""}" placeholder="请输入" class="layui-input">
    </div>
</div>

<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label"><span class="span_must">*</span>出版所</label>
    <div class="layui-input-normal">
        <input type="text" name="publisher" maxlength="128" lay-verify="required" value="${(model.publisher)!""}" placeholder="请输入" class="layui-input">
    </div>
</div>

<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label"><span class="span_must">*</span>出版年份</label>
    <div class="layui-input-normal">
        <input type="text" name="publishingYear" maxlength="10" lay-verify="required" value="${(model.publishingYear)!""}" placeholder="请输入" class="layui-input">
    </div>
</div>

<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label"><span class="span_must">*</span>发行数</label>
    <div class="layui-input-normal">
        <input type="number" name="numberOfIssues" maxlength="32" lay-verify="required" value="${(model.numberOfIssues)!""}" placeholder="请输入" class="layui-input">
    </div>
</div>

<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label"><span class="span_must">*</span>页数</label>
    <div class="layui-input-normal">
        <input type="number" name="numberOfPages" maxlength="32" lay-verify="required" value="${(model.numberOfPages)!""}" placeholder="请输入" class="layui-input">
    </div>
</div>

<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label"><span class="span_must">*</span>ISBN</label>
    <div class="layui-input-normal">
        <input type="text" name="isbn" maxlength="128" lay-verify="required" value="${(model.isbn)!""}" placeholder="请输入" class="layui-input">
    </div>
</div>

<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label"><span class="span_must">*</span>经验值</label>
    <div class="layui-input-normal">
        <input type="number" min="0" name="integral" lay-verify="required" value="${(model.integral)!""}" placeholder="请输入" class="layui-input">
    </div>
</div>

<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label"><span class="span_must">*</span>权重</label>
    <div class="layui-input-normal">
        <input type="number" name="weight" maxlength="11" lay-verify="required" value="${(model.weight)!""}" placeholder="请输入" class="layui-input">
    </div>
</div>

<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label"><span class="span_must">*</span>精选</label>
    <div class="layui-input-normal">
        <input type="checkbox" name="featuredYn" title="精选" value="Y" lay-skin="primary" <#if model?? && model.featuredYn?? && model.featuredYn == 'Y'>checked</#if>>
    </div>
</div>

<div class="layui-form-item">
    <label class="layui-form-label">状态</label>
    <div cyType="radioTool" cyProps="enumName:'StateEnum'" name="state" value="${(model.state)!"1"}" class="layui-input-inline"></div>
</div>