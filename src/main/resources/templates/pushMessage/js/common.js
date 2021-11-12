function push() {
    $.ajax({
        type:"post",
        dataType:'json',
        url:"/sysMessage/pushMessage",
        data:$('#formid').serialize(),
        success:function (result) {
            if (result.code == 0) {
                layer.alert(result.msg,function () {
                    window.location.reload();
                });
            }
            if (result.code == 500) {
                // alert(result.msg);
                layer.alert(result.msg);
                // window.location.reload();
            }
        },
        error:function () {
            alert("推送异常！请联系管理员！");
        }
    });
}

function pushAll(url){
    parent.layer.open({
        type: 2,
        title: '平台用户',
        shadeClose: false,
        shade: [0.3, '#000'],
        maxmin: true, //开启最大化最小化按钮
        area: ['900px', '600px'],
        content: url
    });
}

function pushByPhone(url){
    parent.layer.open({
        type: 2,
        title: '个别用户',
        shadeClose: false,
        shade: [0.3, '#000'],
        maxmin: true, //开启最大化最小化按钮
        area: ['900px', '600px'],
        content: url
    });
}

function pushByRank(url){
    parent.layer.open({
        type: 2,
        title: '等级分类',
        shadeClose: false,
        shade: [0.3, '#000'],
        maxmin: true, //开启最大化最小化按钮
        area: ['900px', '600px'],
        content: url
    });
}