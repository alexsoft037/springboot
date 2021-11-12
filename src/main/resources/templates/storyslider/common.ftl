<#--  公用页面-->
<#--  chenyi 2018-07-12 18:47:51-->
<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label"><span class="span_must">*</span>文件路径</label>
    <div class="layui-input-normal">
        <div cyType="HuploadTool" cyProps="name:'bnUrl',url:'/getData/uploaders/',uploadId:'${(model.bnUrl)!""}',btnName:'上传文件',uploadBtn:'true',deleteBtn:'true'"></div>
    </div>
</div>

<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label"><span class="span_must">*</span>标题</label>
    <div class="layui-input-normal">
        <input type="text" name="bnName" lay-verify="required" value="${(model.bnName)!""}" placeholder="请输入" class="layui-input">
    </div>
</div>

<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label"><span class="span_must"></span>空间故事</label>
    <div class="layui-input-normal">
        <input type="text" id="select-story" placeholder="请输入" value="${(model.articleName)!""}" class="layui-input">
        <input type="hidden" name="articleId" id="articleId" value="${(model.articleId)!""}">
        <input type="hidden" name="articleType" id="articleType" value="AT0005">
    </div>
</div>

<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label"><span class="span_must"></span>链接</label>
    <div class="layui-input-normal">
        <input type="text" name="bnLink" id="bnLink" value="${(model.bnLink)!""}" placeholder="请输入" class="layui-input">
    </div>
</div>

<div class="layui-form-item">
    <label class="layui-form-label">状态</label>
    <div cyType="radioTool" cyProps="enumName:'StateEnum'" name="state" value="${(model.state)!"1"}" class="layui-input-inline"></div>
</div>

<script>
    $('#bnLink').change(function () {
//        console.log("change bnLink");
        $('#select-story').val("");
        $('#articleId').val("");
    });
</script>