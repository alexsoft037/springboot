<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label">团队名字<span class="span_must">*</span></label>
    <div class="layui-input-normal">
        <input type="text" name="groupName" maxlength="64" lay-verify="required" value="${(model.groupName)!""}" placeholder="请输入" class="layui-input">
    </div>
</div>

<div class="layui-form-item">
  <label class="layui-form-label">状态</label>
  <div cyType="radioTool" cyProps="enumName:'StateEnum'" name="state"
       value="${(model.state)!"1"}" class="layui-input-inline"></div>
</div>