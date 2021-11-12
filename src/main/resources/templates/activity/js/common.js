layui.use(['form','laydate'], function () {
    // 时间选择器
    var form = layui.form();
    var laydate = layui.laydate;
    var activityStatus = $('select[name="activityStatusCode"]').val();
    console.log(activityStatus, activityStatus);
    if (activityStatus == 'AVS001') {
        $('#registerEnd').removeAttr("disabled");
        $('#periodFrom').removeAttr("disabled");
    } else if (activityStatus == 'AVS002') {
        $('#registerEnd').attr("disabled", "disabled");
        $('#periodFrom').attr("disabled", "disabled");
        $('#periodTo').removeAttr("disabled");
    } else if (activityStatus == 'AVS003') {
        $('#registerEnd').attr("disabled", "disabled");
        $('#periodFrom').attr("disabled", "disabled");
        $('#periodTo').attr("disabled", "disabled");
    }
    form.on('select(activityStatus)', function(data){
        if (data.value == 'AVS001') {
            $('#registerEnd').removeAttr("disabled");
            $('#periodFrom').removeAttr("disabled");
        } else if (data.value == 'AVS002') {
            $('#registerEnd').attr("disabled", "disabled");
            $('#periodFrom').attr("disabled", "disabled");
            $('#periodTo').removeAttr("disabled");
        } else if (data.value == 'AVS003') {
            $('#registerEnd').attr("disabled", "disabled");
            $('#periodFrom').attr("disabled", "disabled");
            $('#periodTo').attr("disabled", "disabled");
        }
    });

    var periodToValue = $("#periodTo").val();
    //时间选择器
    var periodFrom = "";
    var periodToData = new Date(periodToValue);
    var today = new Date();
    if (periodToValue == "" || periodToData < today) {
        periodFrom = laydate.render({
            elem: '#periodFrom',
            type: 'datetime',
            calendar: true,
            min: (new Date()).Format('yyyy-MM-dd HH:mm:ss'),
            format: 'yyyy-MM-dd HH:mm:ss'
            , done: function (value, date) {
                if (!value) {
                    Alert.alert('必填项不能为空');
                    return false;
                }
                periodTo.config.min = {
                    year: date.year,
                    month: date.month - 1,
                    date: date.date,
                    hours: date.hours,
                    minutes: date.minutes,
                    seconds: date.seconds
                };
                registerEnd.config.max = {
                    year: date.year,
                    month: date.month - 1,
                    date: date.date,
                    hours: date.hours,
                    minutes: date.minutes,
                    seconds: date.seconds
                };
            }
        });
    } else {
        periodFrom = laydate.render({
            elem: '#periodFrom',
            type: 'datetime',
            calendar: true,
            min: (new Date()).Format('yyyy-MM-dd HH:mm:ss'),
            max: $("#periodTo").val(),
            format: 'yyyy-MM-dd HH:mm:ss'
            , done: function (value, date) {
                if (!value) {
                    Alert.alert('必填项不能为空');
                    return false;
                }
                periodTo.config.min = {
                    year: date.year,
                    month: date.month - 1,
                    date: date.date,
                    hours: date.hours,
                    minutes: date.minutes,
                    seconds: date.seconds
                };
                registerEnd.config.max = {
                    year: date.year,
                    month: date.month - 1,
                    date: date.date,
                    hours: date.hours,
                    minutes: date.minutes,
                    seconds: date.seconds
                };
            }
        });
    }


    var periodTo = laydate.render({
        elem: '#periodTo',
        type: 'datetime',
        min: $("#periodFrom").val(),
        calendar: true,
        format: 'yyyy-MM-dd HH:mm:ss'
        , done: function (value, date) {
            if (!value) {
                Alert.alert('必填项不能为空');
                return false;
            }
            // registerEnd.config.max = {
            //     year: date.year,
            //     month: date.month - 1,
            //     date: date.date,
            //     hours: date.hours,
            //     minutes: date.minutes,
            //     seconds: date.seconds
            // };
            periodFrom.config.max = {
                year: date.year,
                month: date.month - 1,
                date: date.date,
                hours: date.hours,
                minutes: date.minutes,
                seconds: date.seconds
            };
        }
    });
    var registerEnd = "";
    if (periodToValue == "" || periodToData < today) {
        registerEnd = laydate.render({
            elem: '#registerEnd',
            type: 'date',
            calendar: true,
            max: $("#periodFrom").val(),
            min: (new Date()).Format('yyyy-MM-dd HH:mm:ss'),
            format: 'yyyy-MM-dd'
            , done: function (value, date) {
                if (!value) {
                    Alert.alert('必填项不能为空');
                    return false;
                }
            }
        });
    } else {
        registerEnd = laydate.render({
            elem: '#registerEnd',
            type: 'date',
            calendar: true,
            max: $("#periodFrom").val(),
            min: (new Date()).Format('yyyy-MM-dd HH:mm:ss'),
            format: 'yyyy-MM-dd'
            , done: function (value, date) {
                if (!value) {
                    Alert.alert('必填项不能为空');
                    return false;
                }
            }
        });
    }

});