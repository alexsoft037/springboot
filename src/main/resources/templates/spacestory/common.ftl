<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label"><span class="span_must">*</span>标题</label>
    <div class="layui-input-normal">
        <input type="text" name="title" maxlength="256" lay-verify="required" readonly value="${(model.title)!""}" placeholder="请输入" class="layui-input">
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
    <label class="layui-form-label"><span class="span_must">*</span>精选</label>
    <div class="layui-input-normal">
        <input type="checkbox" name="featuredYn" title="精选" value="Y" lay-skin="primary" <#if model?? && model.featuredYn?? && model.featuredYn == 'Y'>checked</#if>>
        <#--<input type="text" name="featuredYn" maxlength="1" lay-verify="required" value="${(model.featuredYn)!""}" placeholder="请输入" class="layui-input">-->
    </div>
</div>

<div class="layui-form-item">
    <label class="layui-form-label">状态</label>
    <div cyType="radioTool" cyProps="enumName:'StateEnum'" name="state"
         value="${(model.state)!"1"}" class="layui-input-inline"></div>
</div>

<div class="layui-form-item">
    <label class="layui-form-label">权重</label>
    <div cyType="radioTool" cyProps="enumName:'RankEnum'" name="rank" value="${(model.rank)!"3"}" class="layui-input-inline"></div>
</div>