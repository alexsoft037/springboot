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
            <label class="layui-form-label">所属角色</label>
            <div cyType="selectTool" cyProps="url:'/sys/role/findAll',multiple:'false'" value="${(roleIdList)!""}" name="roleIdList1[]" class="layui-input-normal"></div>
        </div>
        <div class="page-footer">
            <div class="btn-list">
                <div class="btnlist">
                    <button class="layui-btn" lay-submit="" lay-filter="submit" data-url="/sys/adminRole/update"><i
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
