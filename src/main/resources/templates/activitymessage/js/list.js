/**
 * @author DaiMingJian
 * @email 3088393266@qq.com
 * @date 2018/12/21
 */

/**数据渲染对象*/
var Render = {

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
     * @description      删除按钮渲染
     */
    delete:function(rowdata,renderData){
        var btn=' <button  onclick="deleteOne(\''+"删除"+'\',\''+"/activitymessage/delete"+'\',\''+rowdata.messageId+'\')" class="layui-btn layui-btn-mini layui-btn-delete">删除</button>';
        return btn;
    }
};
