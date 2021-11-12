layui.use(['form'], function () {
    var form = layui.form();
    var titleList = [];
    //监听提交
    form.on('select(articleType)', function (data) {
        // console.log("select htmlId", data);
        if (data.value === "") {
            return true;
        }
        // $.ajax({
        //     type: "post",
        //     url: "/htmlfile/info/"+ data.value,
        //     contentType: "application/json",
        //     async: false,
        //     dataType:"json",
        //     success: function (result) {
        //         if (result.code == 0) {
        //             var htmlFile = result.htmlFile;
        //             $("#bnName").val(htmlFile.subject);
        //             $("#bnLink").val(htmlFile.filePath);
        //         } else {
        //             parent.layer.msg(result.info, {icon: 5});
        //         }
        //     },
        //     error: function () {
        //         parent.layer.msg("系统繁忙", {icon: 5});
        //     }
        // });
        $.ajax({
            type: "post",
            url: "/article/autocomplete",
            dataType: "json",
            data: {articleType: data.value},
            success: function (result) {
                if (result.code == 0) {
                    var articleList = result.data;
                    var html = "";
                    titleList = [];
                    for (var i = 0; i < articleList.length; i++) {
                        titleList[articleList[i].articleId] = articleList[i].articleTitle;
                        html += '<option value="' + articleList[i].articleId + '">' + articleList[i].articleTitle + '</option>'
                    }
                    $("#articleId").html(html);
                    $("#adName").val(titleList[articleList[0].articleId]);
                    $("#adUrl").val("");
                    layui.form('select').render();
                } else {
                    parent.layer.msg(result.info, {icon: 5});
                }
            },
            error: function () {
                parent.layer.msg("系统繁忙", {icon: 5});
            }
        });
        return false;
    });
    form.on('select(articleId)', function (data) {
        // console.log("select htmlId", data);
        if (data.value === "") {
            return true;
        }
        $("#adName").val(titleList[data.value]);
        $("#adUrl").val("");
        return false;
    });

    $('#adUrl').change(function () {
//        console.log("change bnLink");
        $('#articleId').html("");
        layui.form('select').render();
    });
});