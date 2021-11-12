<#--  公用页面-->
<#--  xiaoer 2018-07-24 12:11:49-->
            <div class="layui-form-item">${(model.bucket)!""}
            <label class="layui-form-label"><span class="span_must">*</span></label>
            <div class="layui-input-normal">
                <input type="text"  name="activityId" maxlength="20" lay-verify="required"
                 value="${(model.activityId)!""}"  placeholder="请输入"  class="layui-input">
            </div>
        </div>

              <div class="layui-form-item">${(model.bucket)!""}
            <label class="layui-form-label"><span class="span_must">*</span></label>
            <div class="layui-input-normal">
                <input type="text"  name="userId" maxlength="20" lay-verify="required"
                 value="${(model.userId)!""}"  placeholder="请输入"  class="layui-input">
            </div>
        </div>

              <div class="layui-form-item">${(model.bucket)!""}
            <label class="layui-form-label"><span class="span_must">*</span></label>
            <div class="layui-input-normal">
                <input type="text"  name="state" maxlength="1" lay-verify="required"
                 value="${(model.state)!""}"  placeholder="请输入"  class="layui-input">
            </div>
        </div>

              <div class="layui-form-item">${(model.bucket)!""}
            <label class="layui-form-label"><span class="span_must">*</span></label>
            <div class="layui-input-normal">
                <input type="text"  name="createTime" maxlength="20" lay-verify="required"
                 value="${(model.createTime)!""}"  placeholder="请输入"  class="layui-input">
            </div>
        </div>

              <div class="layui-form-item">
            <label class="layui-form-label">状态</label>
            <div cyType="radioTool" cyProps="enumName:'StateEnum'" name="state"
                 value="${(model.state)!"1"}" class="layui-input-inline"></div>
        </div>