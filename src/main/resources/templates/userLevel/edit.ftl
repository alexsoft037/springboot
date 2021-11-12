<!DOCTYPE html>
<html>
<head>
    <title>系统参数</title>
<#include "../resource.ftl"/>
    <style>
        .layui-input-block{
            margin-left: 15px;
        }
        .layui-form-label{
            width: auto;
        }
    </style>
</head>
<body>
<div class="layui-field-box">
    <form class="layui-form" action="">
        <div class="layui-form-item">
            <label class="layui-form-label">等级1 最低限度<span class="span_must">&nbsp;*</span></label>
            <div class="layui-input-block">
                <input type="number" min="1" name="level1Min" value="${(model.level1Min)!"1"}" lay-verify="required" placeholder="请输入" class="layui-input">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">等级1 最大值<span class="span_must">&nbsp;*</span></label>
            <div class="layui-input-block">
                <input type="number" min="0" name="level1Max" value="${(model.level1Max)!"1000"}" lay-verify="required" placeholder="请输入" class="layui-input">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">等级2 最低限度<span class="span_must">&nbsp;*</span></label>
            <div class="layui-input-block">
                <input type="number" min="0" name="level2Min" value="${(model.level2Min)!"1001"}" lay-verify="required" placeholder="请输入" class="layui-input">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">等级2 最大值<span class="span_must">&nbsp;*</span></label>
            <div class="layui-input-block">
                <input type="number" min="0" name="level2Max" value="${(model.level2Max)!"2000"}" lay-verify="required" placeholder="请输入" class="layui-input">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">等级3 最低限度<span class="span_must">&nbsp;*</span></label>
            <div class="layui-input-block">
                <input type="number" min="0" name="level3Min" value="${(model.level3Min)!"2001"}" lay-verify="required" placeholder="请输入" class="layui-input">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">等级3 最大值<span class="span_must">&nbsp;*</span></label>
            <div class="layui-input-block">
                <input type="number" min="0" name="level3Max" value="${(model.level3Max)!"3000"}" lay-verify="required" placeholder="请输入" class="layui-input">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">等级4 最低限度<span class="span_must">&nbsp;*</span></label>
            <div class="layui-input-block">
                <input type="number" min="0" name="level4Min" value="${(model.level4Min)!"3001"}" lay-verify="required" placeholder="请输入" class="layui-input">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">等级4 最大值<span class="span_must">&nbsp;*</span></label>
            <div class="layui-input-block">
                <input type="number" min="0" name="level4Max" value="${(model.level4Max)!"4000"}" lay-verify="required" placeholder="请输入" class="layui-input">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">等级5 最低限度<span class="span_must">&nbsp;*</span></label>
            <div class="layui-input-block">
                <input type="number" min="0" name="level5Min" value="${(model.level5Min)!"4001"}" lay-verify="required" placeholder="请输入" class="layui-input">
            </div>
        </div>

        <div class="layui-form-item">
            <label class="layui-form-label">等级5 最大值<span class="span_must">&nbsp;*</span></label>
            <div class="layui-input-block">
                <input type="number" min="0" name="level5Max" value="${(model.level5Max)!"5000"}" lay-verify="required" placeholder="请输入" class="layui-input">
            </div>
        </div>
        <div class="page-footer">
            <div class="btn-list">
                <div class="btnlist">
                    <button class="layui-btn" lay-submit="" lay-filter="submit" data-url="/sys/userLevel/saveConfig"><i class="fa fa-floppy-o">&nbsp;</i>保存</button>
                </div>
            </div>
        </div>
    </form>
</div>
</body>
</html>
