<!DOCTYPE html>
<html>
<head>
    <title>管理员列表</title>
    <#include "../resource.ftl"/>
    <style>
        .address-picker .layui-input-inline{
            display: block;
        }
    </style>
</head>
<body>
<div class="layui-field-box">
    <form class="layui-form" action="">
        <input type="hidden" name="userId" value="${(model.userId)!""}">
        <div class="layui-form-item">
            <label class="layui-form-label">用户名<span class="span_must">*</span></label>
            <label class="layui-label-right">${(model.username)!""}</label>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">昵称</label>
            <div class="layui-input-normal">
                <input type="text" name="nickname" value="${(model.nickname)!""}" lay-verify="required" placeholder="请输入昵称" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="layui-form-item">${(model.bucket)!""}
            <label class="layui-form-label"><span class="span_must">*</span>头像</label>
            <div class="layui-input-normal">
                <div cyType="HuploadTool" cyProps="name:'avatar',url:'/getData/uploaders/',uploadId:'${(model.avatar)!""}',btnName:'上传文件',uploadBtn:'true',deleteBtn:'true'"></div>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">手机号<span class="span_must">*</span></label>
            <label class="layui-label-right">${(model.phoneNo)!""}</label>
            <#--<div class="layui-input-normal">-->
                <#--<input type="text" name="phoneNo" value="${(model.phoneNo)!""}" lay-verify="phone" placeholder="请输入手机号"-->
                       <#--autocomplete="off" class="layui-input">-->
            <#--</div>-->
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">性别</label>
            <div class="layui-input-normal">
                <div cyType="selectTool" cyProps="enumName:'SexEnum',search:'true',filter:'demo'" name="gender" value="${(model.gender)!"1"}" class="layui-input-inline"></div>
            </div>
        </div>
        <div class="layui-form-item">
            <label class="layui-form-label">个性签名</label>
            <div class="layui-input-normal">
                <textarea name="personality" id="personality" rows="5" lay-verify="required" placeholder="请输入" class="layui-textarea">${(model.personality)!""}</textarea>
            </div>
        </div>
        <div class="layui-form-item address-picker">
            <label class="layui-form-label">地区<span class="span_must">*</span></label>
            <div cyType="linkSelectTool" cyProps="url:'/area/normalList/',topId:'0000000000',name:'areaCode[]',idtemp:'areaCode'" value="${(model.address)!""}" class="layui-input-inline"></div>
        </div>

        <#--<div class="layui-form-item">-->
            <#--<label class="layui-form-label">所属角色</label>-->
            <#--<div cyType="selectTool" cyProps="url:'/sys/role/findAll',multiple:'true'" value="${(roleIdList)!""}" name="roleIdList1[]" class="layui-input-normal"></div>-->
        <#--</div>-->

        <div class="layui-form-item">
            <label class="layui-form-label">最后登录设备ID</label>
            <div class="layui-input-normal">
                <input type="text" name="deviceId" value="${(model.deviceId)!""}" placeholder=""
                       autocomplete="off" class="layui-input">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">状态</label>
            <div cyType="radioTool" cyProps="enumName:'StateEnum'" name="status"
                 value="${(model.status)!""}" class="layui-input-inline"></div>
        </div>


        <div class="page-footer">
            <div class="btn-list">
                <div class="btnlist">
                    <button class="layui-btn" lay-submit="" lay-filter="submit" data-url="/sys/user/update"><i
                            class="fa fa-floppy-o">&nbsp;</i>保存
                    </button>
                    <button class="layui-btn" onclick="$t.closeWindow();"><i class="fa fa-undo">&nbsp;</i>返回</button>
                </div>
            </div>
        </div>
    </form>
</div>
</body>
</html>
