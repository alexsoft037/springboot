<!DOCTYPE html>
<html>
<head>
    <title>管理员列表</title>
    <#include "../resource.ftl"/>
    <style>
        tr td span{
            display: flex;
        }
        .layui-form-label{
            width: 60px;
        }
        .avatar{
            width: 75px;
            border-radius: 50%;
            height: 75px;
            background-size: contain;
        }
    </style>
    <#--<script type="text/javascript" src="/admin/js/list.js"></script>-->
</head>
<body>
<form class="layui-form " action="">
    <div class="layui-form-item">
        <label class="layui-form-label">用户名:</label>
        <div class="layui-input-inline">
            <input type="text" name="username" placeholder="请输入用户名" class="layui-input">
        </div>
        <label class="layui-form-label">手机号:</label>
        <div class="layui-input-inline">
            <input type="text" name="phoneNo" placeholder="请输入手机号" class="layui-input">
        </div>
        <div class="layui-input-inline">
            <button class="layui-btn search-btn" table-id="adminTable" lay-submit="" lay-filter="search"><i class="fa fa-search">&nbsp;</i>查询
            </button>
            <button type="reset" class="layui-btn layui-btn-primary"><i class="fa fa-refresh">&nbsp;</i>重置</button>
        </div>
    </div>
</form>
<div class="layui-btn-group">
<@shiro.hasPermission name="sys:admin:save">
    <button class="layui-btn" onclick="add('/sys/admin/add')"><i class="fa fa-plus">&nbsp;</i>增加</button>
</@shiro.hasPermission>
<@shiro.hasPermission name="sys:admin:update">
    <button class="layui-btn" onclick="edit('adminTable','/sys/admin/edit')" style="margin-left: 5px!important;">
        <i class="fa fa-pencil-square-o">&nbsp;</i>修改
    </button>
    <button class="layui-btn layui-btn-green" onclick="updateState('批量启用','adminTable','/sys/admin/enable')">
        <i class="fa fa-check-square-o">&nbsp;</i>启用
    </button>
    <button class="layui-btn  layui-btn-danger" onclick="updateState('批量禁用','adminTable','/sys/admin/limit')">
        <i class="fa fa-expeditedssl">&nbsp;</i>禁用
    </button>
</@shiro.hasPermission>
<@shiro.hasPermission name="sys:admin:delete">
    <button class="layui-btn" onclick="deleteBatch('adminTable','/sys/admin/delete');" style="margin-left: 5px!important;">
        <i class="fa fa-trash-o">&nbsp;</i>删除
    </button>
</@shiro.hasPermission>
    <button class="layui-btn" onclick="initPassword('adminTable','/sys/admin/initPassword');" style="margin-left: 5px!important;">
        <i class="fa fa-refresh">&nbsp;</i>初始化密码 123456
    </button>

</div>
<div class="layui-form ">
    <table class="layui-table" id="adminTable" cyType="pageGrid"
           cyProps="url:'/sys/admin/listData',checkbox:'true',pageColor:'#2991d9'">
        <thead>
        <tr>
            <!--复选框-->
            <th width="1%" param="{type:'checkbox'}">
                <input type="checkbox" lay-skin="primary" lay-filter="allChoose">
            </th>
            <!--isPrimary：是否是主键-->
            <th width="10%" param="{name:'userId',isPrimary:'true',hide:'true'}">用户ID</th>
            <th width="10%" param="{name:'avatar',render:'Render.renderImage'}">用户名</th>
            <th width="10%" param="{name:'username',sort:'true'}">用户名</th>
            <th width="10%" param="{name:'nickname',sort:'true'}">昵称</th>
            <th width="10%" param="{name:'spaceName',sort:'false'}">少儿空间</th>
            <th width="10%" param="{name:'volunteerId',sort:'false'}">志愿者号</th>
            <th width="10%" param="{name:'phoneNo',sort:'true'}">手机号</th>
            <th width="10%" param="{name:'gender'}">性别</th>
            <th width="10%" param="{name:'addressTxt'}">地区</th>
            <th width="10%" param="{name:'personality'}">个性签名</th>
            <th width="10%" param="{name:'lastLoginIpaddr'}">最后登陆ip</th>
            <th width="10%" param="{name:'lastLoginDt',render:'Render.customDate'}">最后登录时间</th>
            <th width="10%" param="{name:'loginCount'}">登陆次数</th>
            <th width="10%" param="{name:'deviceId'}">最后登录设备ID</th>
            <th width="10%" param="{name:'createTime',sort:'true',render:'Render.customDate'}">创建时间</th>
            <th width="10%" param="{name:'status',render:'Render.customState'}">状态</th>
            <th width="10%" param="{operate:'true',buttons:'Render.state,Render.edit,Render.delete'}">操作</th>
        </tr>
        </thead>
    </table>
</div>

</body>
<script type="text/javascript">
    /**
     * Created by 陈熠 on 2017/6/23.
     *  email   :  228112142@qq.com
     */
    /**跳转到添加页面*/
    function add(url) {
        //$("body").load(url);
        //type：0（信息框，默认）1（页面层）2（iframe层）3（加载层）4（tips层）
        parent.layer.open({
            type: 2,
            title: '添加',
            shadeClose: false,
            shade: [0.3, '#000'],
            maxmin: true, //开启最大化最小化按钮
            area: ['893px', '600px'],
            content: url
        });
    }
    /**跳转到修改页面*/
    function edit(table_id, url) {
        var id = getSelectedRow(table_id, url);
        if (id != null) {
            parent.layer.open({
                type: 2,
                title: '修改',
                shadeClose: false,
                shade: [0.3, '#000'],
                maxmin: true, //开启最大化最小化按钮
                area: ['893px', '600px'],
                content: url + "/" + id
            });
        }
    }
    /**删除*/
    function deleteBatch(table_id, url) {
        //获取选中的id
        var ids = getSelectedRows(table_id);
        if (ids != null) {
            confirm("确认删除？", function () {
                $.ajax({
                    type: "post",
                    url: url,
                    contentType: "application/json",
                    data: JSON.stringify(ids),
                    async: false,
                    dataType: "json",
                    success: function (R) {
                        if (R.code === 0) {
                            Msg.success('操作成功');
                            $(".search-btn").click();
                        } else {
                            Msg.error(R.msg);
                        }
                    },
                    error: function () {
                        Msg.error("系统繁忙");
                    }
                });
            });

        }

    }

    function initPassword(table_id, url) {
        //获取选中的id
        var ids = getSelectedRows(table_id);
        if (ids != null) {
            confirm("确认初始化？", function () {
                $.ajax({
                    type: "post",
                    url: url,
                    contentType: "application/json",
                    data: JSON.stringify(ids),
                    async: false,
                    dataType: "json",
                    success: function (R) {
                        if (R.code === 0) {
                            Msg.success('操作成功');
                            $(".search-btn").click();
                        } else {
                            Msg.error(R.msg);
                        }
                    },
                    error: function () {
                        Msg.error("系统繁忙");
                    }
                });
            });

        }

    }
    //数据渲染对象
    var Render = {
        /**
         *  Created by 陈熠 on 2017/6/22
         *  email  ：228112142@qq.com
         *  rowdata：当前行数据
         *  index  ：当前第几行
         *  value  ：当前渲染列的值
         */
        //渲染状态列
        customState: function (rowdata,renderData, index, value) {
            if(value == 1){
                return '<span style="color:green">正常</span>';
            }
            if(value == 0){
                return '<span style="color:red">禁用</span>';
            }
            return value;
        },
        //渲染操作方法列
        renderImage: function (rowdata, renderData, index, value) {
            if (value != null && value != ''){
                return '<div style="background-image: url('+value+');" class="avatar"></div>';
            } else {
                return '<div style="background-image: url(/statics/img/default-user.png);" class="avatar"></div>';
            }
            return value;
        },
        customIcon: function (rowdata,renderData, index, value) {
            if (value == "" || value == null) {
                return "";
            }
            var result = '<i class="' + value + ' fa-lg"></i>';
            return result;
        },
        customDate: function (rowdata, renderData, index, value) {
            if(value === null){
                return value;
            }
            var date = new Date(value);
            return date.Format("yyyy-MM-dd HH:mm:ss");
        },
        /**
         * @param rowdata    行数据
         * @param renderData 渲染后的列
         * @description      修改按钮渲染
         */
        edit:function(rowdata,renderData){
            var btn=' <button  onclick="editOne(\''+"/sys/admin/edit"+'\',\''+rowdata.userId+'\')" class="layui-btn layui-btn-mini">修改</button>';
            return btn;
        },
        /**
         * @param rowdata    行数据
         * @param renderData 渲染后的列
         * @description      删除按钮渲染
         */
        delete:function(rowdata,renderData){
            var btn=' <button  onclick="deleteOne(\''+"删除"+'\',\''+"/sys/admin/delete"+'\',\''+rowdata.userId+'\')" class="layui-btn layui-btn-mini layui-btn-delete">删除</button>';
            return btn;
        },
        /**
         * @param rowdata    行数据
         * @param renderData 渲染后的列
         * @description      启用禁用按钮渲染
         */
        state:function(rowdata,renderData){
            if(rowdata.status=='0'){
                return' <button onclick="updateStateOne(\''+"启用"+'\',\''+"/sys/admin/enable"+'\',\''+rowdata.userId+'\')"' +
                    '  class="layui-btn layui-btn-mini layui-btn-green">启用</button>';
            }
            if(rowdata.status=='1'){
                return' <button  onclick="updateStateOne(\''+"禁用"+'\',\''+"/sys/admin/limit"+'\',\''+rowdata.userId+'\')" ' +
                    'class="layui-btn layui-btn-mini layui-btn-danger">禁用</button>';
            }
            return "";
        }
    };

</script>
</html>