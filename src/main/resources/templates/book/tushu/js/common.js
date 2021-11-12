
layui.use(['form'], function(){
    var form = layui.form();

    //监听提交
    form.on('select(hospital)', function(data){
        return false;
    });
});