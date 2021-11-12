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
