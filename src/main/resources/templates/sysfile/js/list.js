/**
 * 地产附件表
 * @author xiaoer
 * @email 228112142@qq.com
 * @date 2018-08-18 02:44:19
 */

/**数据渲染对象*/
var Render = {
    /**
     * 渲染状态列
     * @param rowdata    行数据
     * @param renderData 渲染后的列
     * @param index
     * @param value      当前对象值
     */
    customState: function (rowdata,renderData, index, value) {
        if(value == "启用"){
            return '<span style="color:green">'+value+'</span>';
        }
        if(value == "禁用"){
            return '<span style="color:red">'+value+'</span>';
        }
        return value;
    },
    customImage: function (rowdata,renderData, index, value) {
        if(value == ""){
            return '';
        }
        var imageTypes = ["jpg", "png", "jpeg", "gif"]
        var videoTypes = ["mp4", "mpg", "avi"];
        var fileType = (/[.]/.exec(value)) ? /[^.]+$/.exec(value) : undefined;
        fileType = undefined ? '' : fileType[0].substring(0, fileType[0].indexOf('?'));
        console.log('fileType = ', fileType);
        var imageHtml = '';
        if (imageTypes.indexOf(fileType.toLowerCase()) > -1){
            imageHtml = '<a href="'+value+'" target="_blank"><img src="'+value+'" class="banner-image"></a>';
        } else if (videoTypes.indexOf(fileType.toLowerCase()) > -1) {
            imageHtml = '<a href="'+value+'" target="_blank"><img src="/statics/img/videosIcon.png" class="banner-image"></a>';
        } else {
            imageHtml = '<a href="'+value+'" target="_blank"><img src="/statics/img/fileIcon.png" class="banner-image"></a>';
        }

        return imageHtml;
    },
    /**
     * @param rowdata    行数据
     * @param renderData 渲染后的列
     * @description      详情按钮渲染
     */
    info:function(rowdata,renderData){
        var btn=' <button  onclick="detailOne(\''+"/sysfile/info"+'\',\''+rowdata.id+'\')" class="layui-btn layui-btn-mini">详情</button>';
        return btn;
    },
    /**
     * @param rowdata    行数据
     * @param renderData 渲染后的列
     * @description      修改按钮渲染
     */
    edit:function(rowdata,renderData){
        var btn=' <button  onclick="editOne(\''+"/sysfile/edit"+'\',\''+rowdata.id+'\')" class="layui-btn layui-btn-mini">修改</button>';
        return btn;
    },
    /**
     * @param rowdata    行数据
     * @param renderData 渲染后的列
     * @description      删除按钮渲染
     */
    delete:function(rowdata,renderData){
        var btn=' <button  onclick="deleteOne(\''+"删除"+'\',\''+"/sysfile/delete"+'\',\''+rowdata.id+'\')" class="layui-btn layui-btn-mini layui-btn-delete">删除</button>';
        return btn;
    },
    /**
     * @param rowdata    行数据
     * @param renderData 渲染后的列
     * @description      启用禁用按钮渲染
     */
    state:function(rowdata,renderData){
        if(rowdata.state=='0'){
            return' <button onclick="updateStateOne(\''+"启用"+'\',\''+"/sysfile/enable"+'\',\''+rowdata.id+'\')"' +
                '  class="layui-btn layui-btn-mini layui-btn-green">启用</button>';
        }
        if(rowdata.state=='1'){
            return' <button  onclick="updateStateOne(\''+"禁用"+'\',\''+"/sysfile/limit"+'\',\''+rowdata.id+'\')" ' +
                'class="layui-btn layui-btn-mini layui-btn-danger">禁用</button>';
        }
        return "";
    }
};
