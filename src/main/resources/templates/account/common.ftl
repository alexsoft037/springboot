<#--  公用页面-->
<#--  xiaoer 2018-09-18 19:00:52-->
            <div class="layui-form-item">${(model.bucket)!""}
            <label class="layui-form-label">用户名<span class="span_must">*</span></label>
            <div class="layui-input-normal">
                <input type="text"  name="uid" maxlength="50" lay-verify="required"
                 value="${(model.uid)!""}"  placeholder="请输入用户名"  class="layui-input">
            </div>
        </div>

              <div class="layui-form-item">${(model.bucket)!""}
            <label class="layui-form-label">用户密码<span class="span_must">*</span></label>
            <div class="layui-input-normal">
                <input type="text"  name="pwd" maxlength="256" lay-verify="required"
                 value="${(model.pwd)!""}"  placeholder="请输入用户密码"  class="layui-input">
            </div>
        </div>

              <div class="layui-form-item">${(model.bucket)!""}
            <label class="layui-form-label">用户token<span class="span_must">*</span></label>
            <div class="layui-input-normal">
                <input type="text"  name="token" maxlength="50" lay-verify="required"
                 value="${(model.token)!""}"  placeholder="请输入用户token"  class="layui-input">
            </div>
        </div>

              <div class="layui-form-item">${(model.bucket)!""}
            <label class="layui-form-label">登录状态<span class="span_must">*</span></label>
            <div class="layui-input-normal">
                <input type="text"  name="state" maxlength="1" lay-verify="required"
                 value="${(model.state)!""}"  placeholder="请输入登录状态"  class="layui-input">
            </div>
        </div>

              <div class="layui-form-item">${(model.bucket)!""}
            <label class="layui-form-label">sig<span class="span_must">*</span></label>
            <div class="layui-input-normal">
                <input type="text"  name="userSig" maxlength="512" lay-verify="required"
                 value="${(model.userSig)!""}"  placeholder="请输入sig"  class="layui-input">
            </div>
        </div>

              <div class="layui-form-item">${(model.bucket)!""}
            <label class="layui-form-label">注册时间戳<span class="span_must">*</span></label>
            <div class="layui-input-normal">
                <input type="text"  name="registerTime" maxlength="11" lay-verify="required"
                 value="${(model.registerTime)!""}"  placeholder="请输入注册时间戳"  class="layui-input">
            </div>
        </div>

              <div class="layui-form-item">${(model.bucket)!""}
            <label class="layui-form-label">登录时间戳<span class="span_must">*</span></label>
            <div class="layui-input-normal">
                <input type="text"  name="loginTime" maxlength="11" lay-verify="required"
                 value="${(model.loginTime)!""}"  placeholder="请输入登录时间戳"  class="layui-input">
            </div>
        </div>

              <div class="layui-form-item">${(model.bucket)!""}
            <label class="layui-form-label">退出时间戳<span class="span_must">*</span></label>
            <div class="layui-input-normal">
                <input type="text"  name="logoutTime" maxlength="11" lay-verify="required"
                 value="${(model.logoutTime)!""}"  placeholder="请输入退出时间戳"  class="layui-input">
            </div>
        </div>

              <div class="layui-form-item">${(model.bucket)!""}
            <label class="layui-form-label">最新请求时间戳<span class="span_must">*</span></label>
            <div class="layui-input-normal">
                <input type="text"  name="lastRequestTime" maxlength="11" lay-verify="required"
                 value="${(model.lastRequestTime)!""}"  placeholder="请输入最新请求时间戳"  class="layui-input">
            </div>
        </div>

              <div class="layui-form-item">${(model.bucket)!""}
            <label class="layui-form-label">当前appid<span class="span_must">*</span></label>
            <div class="layui-input-normal">
                <input type="text"  name="currentAppid" maxlength="11" lay-verify="required"
                 value="${(model.currentAppid)!""}"  placeholder="请输入当前appid"  class="layui-input">
            </div>
        </div>

              <div class="layui-form-item">
            <label class="layui-form-label">状态</label>
            <div cyType="radioTool" cyProps="enumName:'StateEnum'" name="state"
                 value="${(model.state)!"1"}" class="layui-input-inline"></div>
        </div>