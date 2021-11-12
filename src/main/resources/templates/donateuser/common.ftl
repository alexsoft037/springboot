<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label"><span class="span_must">*</span></label>
    <div class="layui-input-normal">
        <input type="text" name="itemId" maxlength="20" lay-verify="required"
               value="${(model.itemId)!""}" placeholder="请输入" class="layui-input">
    </div>
</div>

<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label"><span class="span_must">*</span></label>
    <div class="layui-input-normal">
        <input type="text" name="userId" maxlength="20" lay-verify="required"
               value="${(model.userId)!""}" placeholder="请输入" class="layui-input">
    </div>
</div>

<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label"><span class="span_must">*</span></label>
    <div class="layui-input-normal">
        <input type="text" name="donateAmount" maxlength="10,2" lay-verify="required"
               value="${(model.donateAmount)!""}" placeholder="请输入" class="layui-input">
    </div>
</div>

<div class="layui-form-item">
    <label class="layui-form-label">状态</label>
    <div cyType="radioTool" cyProps="enumName:'StateEnum'" name="state"
         value="${(model.state)!"1"}" class="layui-input-inline"></div>
</div>