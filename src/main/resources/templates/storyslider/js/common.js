var Render = {
    onChangeStory: function (val, render) {
        $.ajax({
            type: "post",
            url: "/spacestory/autocomplete/",
            data: {keywords: val},
            dataType: "json",
            success: function (result) {
                if (result.code == 0) {
                    if (result.data.length > 0) {
                        var data = $.map(result.data, function(dataItem) {
                            return { title: dataItem.title, value: dataItem.storyId };
                        });
                        render(data);
                    }
                } else {
                    parent.layer.msg(result.info, {icon: 5});
                }
            },
            error: function () {
                parent.layer.msg("系统繁忙", {icon: 5});
            }
        });
    }
};


layui.use(['form', 'autocomplete', 'jquery'], function () {

    var form = layui.form();
    var autocomplete = layui.autocomplete;

    autocomplete.init({
        elem: "input[id=select-story]",
        textLength: 1,//触发长度
        delay: 200,//触发延迟
        callback: {
            data: function (val, render) {
                Render.onChangeStory(val, render);
            },
            selected: function (data) {
                $("#articleId").val(data.value);
                $("#bnLink").val("")
            },
            close: function () {
            }
        }
    });
});