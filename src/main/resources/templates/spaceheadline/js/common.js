layui.use('laydate', function () {

    // 时间选择器
    var laydate = layui.laydate;

    //时间选择器
    laydate.render({
        elem: '#createAt',
        type: 'datetime',
        format: 'yyyy-MM-dd HH:mm:ss'
    });
});