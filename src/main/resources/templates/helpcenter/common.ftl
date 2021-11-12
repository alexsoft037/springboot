<style>
    .ck-rounded-corners .ck.ck-editor__main>.ck-editor__editable, .ck.ck-editor__main>.ck-editor__editable.ck-rounded-corners{
        min-height: 380px;
    }
</style>
<div class="layui-form-item">${(model.bucket)!""}
    <label class="layui-form-label">标题<span class="span_must">*</span></label>
    <div class="layui-input-normal">
        <input type="text" name="title" maxlength="256" lay-verify="required"
               value="${(model.title)!""}" placeholder="请输入" class="layui-input">
    </div>
</div>

<div class="layui-form-item">${(model.bucket)!""}
  <label class="layui-form-label">内容<span class="span_must">*</span></label>
  <div class="layui-input-normal" style="width: 70%">
      <textarea name="content" id="content" rows="5" lay-verify="required" placeholder="请输入" class="layui-textarea">${(model.content)!""}</textarea>
  </div>
</div>

<div class="layui-form-item">
  <label class="layui-form-label">状态</label>
  <div cyType="radioTool" cyProps="enumName:'StateEnum'" name="state"
       value="${(model.state)!"1"}" class="layui-input-inline"></div>
</div>


<script src="/statics/plugins/ckeditor5/ckeditor.js"></script>
<script type="text/javascript">
    ClassicEditor.create(document.querySelector('#content'), {
        autosave: {
            save( editor ) {
                return saveDataIntro( editor.getData() );
            }
        },
        ckfinder: {
            uploadUrl: '/getData/ckfinder/'
        }
    }).then( editor => {
        saveDataIntro( editor.getData() );
    } ).catch(error => {
        console.error(error);
    });

    function saveDataIntro( data ) {
        $('#content').html(encodeURIComponent(data));
    }

</script>