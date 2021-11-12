<style>
    #articleType .layui-anim dd[lay-value="AT0007"],#articleType .layui-anim dd[lay-value="AT0010"], #articleType .layui-anim dd[lay-value="AT0011"], #articleType .layui-anim dd[lay-value="AT0012"], #articleType .layui-anim dd[lay-value="AT0013"]{
        display: none;
    }
</style>

<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label"><span class="span_must">*</span></label>
    <div class="layui-input-normal">
        <div class="layui-input-normal">
            <div cyType="HuploadTool" cyProps="name:'adImagePath',url:'/getData/uploaders/',uploadId:'${(model.adImagePath)!""}',btnName:'上传文件',uploadBtn:'true',deleteBtn:'true'"></div>
        </div>
    </div>
</div>

<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label"><span class="span_must">*</span>选择菜单</label>
    <div class="layui-input-normal">
        <div cyType="selectTool" id="articleType" cyProps="url:'/getData/getCodeValues?codeName=articleType',search:'true',filter:'articleType'" name="articleType" value="${(model.articleType)!""}" class="layui-input-inline"></div>
    </div>
</div>

<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label"><span class="span_must">*</span>选择文章</label>
    <div class="layui-input-normal">
        <select name="articleId" id="articleId" lay-filter="articleId">
        <#if articles??>
            <#list articles as item>
                <option value="${item.articleId}" <#if articleId ?? && articleId == item.articleId>selected</#if>>${item.articleTitle}</option>
            </#list>
        </#if>

        </select>
    </div>
</div>


<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label"><span class="span_must">*</span>标题</label>
    <div class="layui-input-normal">
        <input type="text" name="adName" id="adName" maxlength="256" value="${(model.adName)!""}" placeholder="请输入" class="layui-input">
    </div>
</div>


<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label"><span class="span_must"></span>链接</label>
    <div class="layui-input-normal">
        <input type="text" name="adUrl" id="adUrl" maxlength="1024" value="${(model.adUrl)!""}" placeholder="请输入" class="layui-input">
    </div>
</div>

<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label"><span class="span_must"></span>内容</label>
    <div class="layui-input-normal">
        <input type="text" name="adContent" maxlength="512" value="${(model.adContent)!""}" placeholder="请输入" class="layui-input">
    </div>
</div>


<div class="layui-form-item">
    <label class="layui-form-label">状态</label>
    <div cyType="radioTool" cyProps="enumName:'StateEnum'" name="state"
         value="${(model.state)!"1"}" class="layui-input-inline"></div>
</div>