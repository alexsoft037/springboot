
<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label"><span class="span_must">*</span>名称</label>
    <div class="layui-input-normal">
        <div cyType="selectTool" cyProps="url:'/getData/getCodeValues?codeName=thirdPartyType',search:'true',filter:'demo'" name="tpTypeCode" value="${(model.tpTypeCode)!""}" class="layui-input-inline"></div>
    </div>
</div>

<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label"><span class="span_must">*</span>链接</label>
    <div class="layui-input-normal">
        <input type="text" name="tpUrl" maxlength="1024" lay-verify="required" value="${(model.tpUrl)!""}" placeholder="请输入" class="layui-input">
    </div>
</div>

<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label"><span class="span_must">*</span>帐号</label>
    <div class="layui-input-normal">
        <input type="text" name="tpUser" maxlength="256" lay-verify="required" value="${(model.tpUser)!""}" placeholder="请输入" class="layui-input">
    </div>
</div>

<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label"><span class="span_must">*</span>密码</label>
    <div class="layui-input-normal">
        <input type="text" name="tpPassword" maxlength="256" lay-verify="required" value="${(model.tpPassword)!""}" placeholder="请输入" class="layui-input">
    </div>
</div>

<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label">备注</label>
    <div class="layui-input-normal">
        <textarea type="text" name="tpRemark" maxlength="1024" placeholder="请输入" class="layui-input">${(model.tpRemark)!""}</textarea>
    </div>
</div>

<div class="layui-form-item">
    <label class="layui-form-label">状态</label>
    <div cyType="radioTool" cyProps="enumName:'StateEnum'" name="state"
         value="${(model.state)!"1"}" class="layui-input-inline"></div>
</div>