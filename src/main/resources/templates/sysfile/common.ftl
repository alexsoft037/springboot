<#--  地产附件表公用页面-->
<#--  xiaoer 2018-08-18 02:44:19-->
            <div class="layui-form-item">${(model.bucket)!""}
            <label class="layui-form-label">id<span class="span_must">*</span></label>
            <div class="layui-input-normal">
                <input type="text"  name="id" maxlength="20" lay-verify="required"
                 value="${(model.id)!""}"  placeholder="请输入id"  class="layui-input">
            </div>
        </div>

              <div class="layui-form-item">${(model.bucket)!""}
            <label class="layui-form-label">控件唯一标识(UUID)<span class="span_must">*</span></label>
            <div class="layui-input-normal">
                <input type="text"  name="uploadId" maxlength="100" lay-verify="required"
                 value="${(model.uploadId)!""}"  placeholder="请输入控件唯一标识(UUID)"  class="layui-input">
            </div>
        </div>

              <div class="layui-form-item">${(model.bucket)!""}
            <label class="layui-form-label">文件路径<span class="span_must">*</span></label>
            <div class="layui-input-normal">
                <input type="text"  name="url" maxlength="1024" lay-verify="required"
                 value="${(model.url)!""}"  placeholder="请输入文件路径"  class="layui-input">
            </div>
        </div>

              <div class="layui-form-item">${(model.bucket)!""}
            <label class="layui-form-label"><span class="span_must">*</span></label>
            <div class="layui-input-normal">
                <input type="text"  name="thumbnailUrl" maxlength="1024" lay-verify="required"
                 value="${(model.thumbnailUrl)!""}"  placeholder="请输入"  class="layui-input">
            </div>
        </div>

              <div class="layui-form-item">${(model.bucket)!""}
            <label class="layui-form-label">文件名<span class="span_must">*</span></label>
            <div class="layui-input-normal">
                <input type="text"  name="fileName" maxlength="50" lay-verify="required"
                 value="${(model.fileName)!""}"  placeholder="请输入文件名"  class="layui-input">
            </div>
        </div>

              <div class="layui-form-item">${(model.bucket)!""}
            <label class="layui-form-label">附件类型<span class="span_must">*</span></label>
            <div class="layui-input-normal">
                <input type="text"  name="fileType" maxlength="50" lay-verify="required"
                 value="${(model.fileType)!""}"  placeholder="请输入附件类型"  class="layui-input">
            </div>
        </div>

              <div class="layui-form-item">${(model.bucket)!""}
            <label class="layui-form-label">附件大小<span class="span_must">*</span></label>
            <div class="layui-input-normal">
                <input type="text"  name="fileSize" maxlength="100" lay-verify="required"
                 value="${(model.fileSize)!""}"  placeholder="请输入附件大小"  class="layui-input">
            </div>
        </div>

              <div class="layui-form-item">${(model.bucket)!""}
            <label class="layui-form-label"><span class="span_must">*</span></label>
            <div class="layui-input-normal">
                <input type="text"  name="ossYn" maxlength="1" lay-verify="required"
                 value="${(model.ossYn)!""}"  placeholder="请输入"  class="layui-input">
            </div>
        </div>

              <div class="layui-form-item">${(model.bucket)!""}
            <label class="layui-form-label">上传时间<span class="span_must">*</span></label>
            <div class="layui-input-normal">
                <input type="text"  name="createTime" maxlength="20" lay-verify="required"
                 value="${(model.createTime)!""}"  placeholder="请输入上传时间"  class="layui-input">
            </div>
        </div>

              <div class="layui-form-item">
            <label class="layui-form-label">状态</label>
            <div cyType="radioTool" cyProps="enumName:'StateEnum'" name="state"
                 value="${(model.state)!"1"}" class="layui-input-inline"></div>
        </div>