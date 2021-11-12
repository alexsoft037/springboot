
layui.use(['form'], function(){
    var form = layui.form();

    //监听提交
    form.on('select(htmlId)', function(data){
        if (data.value === ""){
            return true;
        }
        $.ajax({
            type: "post",
            url: "/htmlfile/info/"+ data.value,
            contentType: "application/json",
            async: false,
            dataType:"json",
            success: function (result) {
                if (result.code == 0) {
                    var htmlFile = result.htmlFile;
                    $("#title").val(htmlFile.subject);
                    $("#contentFile").val(htmlFile.filePath);
                } else {
                    parent.layer.msg(result.info, {icon: 5});
                }
            },
            error: function () {
                parent.layer.msg("系统繁忙", {icon: 5});
            }
        });
        // $.ajax({
        //     type: "post",
        //     url: "/hospitalmaindepartment/normallist/"+ data.value,
        //     contentType: "application/json",
        //     async: false,
        //     dataType:"json",
        //     success: function (result) {
        //         if (result.code == 0) {
        //             var mainDepartList = result.data;
        //             var html = "";
        //             for(var i=0; i < mainDepartList.length; i++){
        //                 html += '<option value="'+mainDepartList[i].id+'">'+mainDepartList[i].name+'</option>'
        //             }
        //             $("#hospitalMainDepartmentId").html(html);
        //             layui.form('select').render();
        //         } else {
        //             parent.layer.msg(result.info, {icon: 5});
        //         }
        //     },
        //     error: function () {
        //         parent.layer.msg("系统繁忙", {icon: 5});
        //     }
        // });
        return false;
    });
});