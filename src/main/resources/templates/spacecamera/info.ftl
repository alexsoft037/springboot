<#--  xiaoer 2018-09-03 18:37:09-->

<html>
<head>
    <title>详情页面</title>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" >
    <meta name="renderer" content="webkit">
    <style>
        body{margin:0;}
        #myPlayer{max-width: 1200px;width: 100%;}
    </style>
</head>
<body>
<div class="layui-field-box">
    <script src="/js/ezuikit.js"></script>
    <!--<script src="./ezuikit.js"></script>-->

    <video id="myPlayer" poster="" controls playsInline webkit-playsinline autoplay>
        <source src="${(model.url)!""}" type="application/x-mpegURL" />
    </video>
</div>
<script>
    var player = new EZUIPlayer('myPlayer');
    //    player.on('error', function(){
    //        console.log('error');
    //    });
    //    player.on('play', function(){
    //        console.log('play');
    //    });
    //    player.on('pause', function(){
    //        console.log('pause');
    //    });
    //    player.on('waiting', function(){
    //        console.log('waiting');
    //    });


    // 日志
    // player.on('log', log);
    //
    // function log(str){
    //     var div = document.createElement('DIV');
    //     div.innerHTML = (new Date()).Format('yyyy-MM-dd hh:mm:ss.S') + JSON.stringify(str);
    //     document.body.appendChild(div);
    // }


</script>
</body>
</html>
