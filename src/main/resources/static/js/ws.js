var websocket = '';
var ajaxPageNum = 1;
var last_health;
var health_timeout = 10;
var tDates = [], tData = [];
var rightIndex;
var ws_port;
function init (userName,port){
    ws_port = '8887';
    if(port){
        ws_port = port;
    }
    if (userName != '' && $('body').attr('ws') == 'yes') {
        debugger;
        // var userName = $('body').attr('userName');
        if (window.WebSocket) {
            websocket = new WebSocket(
                encodeURI('ws://' + document.domain + ':'+ws_port));
            websocket.onopen = function() {
                console.log('已连接');
                websocket.send("online"+userName);
                heartbeat_timer = setInterval(function() {
                    keepalive(websocket)
                }, 10000);
            };
            websocket.onerror = function() {
                console.log('连接发生错误');
            };
            websocket.onclose = function() {
                console.log('已经断开连接');
                initWs();
            };
            // 消息接收
            websocket.onmessage = function(message) {
                if(message && message.data){
                    showNotice(message.data)
                }
            };
        } else {
            alert("该浏览器不支持下单提醒。<br/>建议使用高版本的浏览器，<br/>如 IE10、火狐 、谷歌  、搜狗等");
        }

    }
}

var initWs = function() {
    if (window.WebSocket) {
        websocket = new WebSocket(
            encodeURI('ws://' + document.domain + ':'+ws_port));
        websocket.onopen = function() {
            console.log('已连接');
            websocket.send("online"+userName);
            heartbeat_timer = setInterval(function() {
                keepalive(websocket)
            }, 10000);
        };
        websocket.onerror = function() {
            console.log('连接发生错误');
        };
        websocket.onclose = function() {
            console.log('已经断开连接');
            initWs();
        };
        // 消息接收
        websocket.onmessage = function(message) {
            if(message && message.data){
                showNotice(message.data)
            }
            console.log(message)

        };
    } else {
        alert("该浏览器不支持下单提醒。<br/>建议使用高版本的浏览器，<br/>如 IE10、火狐 、谷歌  、搜狗等");
    }
}
var vadioTimeOut;
function showNotice(msgdata) {
    var data = JSON.parse(msgdata);
    // if (!title && !content) {
    //     title = "您有新的待处理订单";
    //     content = "您有新的订单,请及时处理！";
    // }
    var iconUrl = "http://www.wonyen.com/favicon.ico";

    // '<p>客户姓名：马文有</p> <p>服务项目：洗衣机维修</p> <p>要求服务时间：2018-06-25 09:00:00</p>'
    layer.open({
        title: data.title
        ,content: '<p>客户姓名：'+data.userName+'</p> <p>服务项目：'+data.serviceItem+'</p> <p>要求服务时间：'+data.serviceTime+'</p>',
        offset: 'rb', // 定位右下角
        shade:0  , // 取消遮罩
        btn: ['处理', '忽略'],
        yes:function(index,layero){
            // <a class="cy-page" href="javascript:;" data-url="commpara/list" title="字典管理"><i class="fa fa-file-code-o"></i> 字典管理</a>
            // alert(1)
            layer.close(index);
            var name = '服务工单管理';
            var url = 'customerorder/list';
            //判断该页面是否已存在
            if ($("#navTab").find("li[data-url='" + url + "']").length === 0) {
                var index = Loading.open(1,false);
                //如果不存在
                $("#navTab").find("li").removeClass("selected");
                //新增tab页
                var _li = ['<li tabid="tools-utils" class="selected" data-url="' + url + '">',
                    '<a href="javascript:" title="' + name + '" class="tools-utils">',
                    '<span>' + name + '</span>',
                    '</a>',
                    '<a href="javascript:;" class="close">close</a>',
                    '</li>'].join("");
                $("#navTab").find("ul").append(_li);
                //新增右侧更多list
                $(".tabsMoreList").find("li").removeClass("selected");
                var moreli = '<li class="selected" data-url="'+url+'"><a href="javascript:"  title="' + name + '">' + name + '</a></li>';
                $(".tabsMoreList").append(moreli);

                $(".content").find("iframe").removeClass("cy-show");
                //打开iframe
                var iframe = $('<iframe class="cy-show" scrolling="yes" frameborder="0" style="width: 100%; height: 100%; overflow: visible; "></iframe>');
                $(iframe).attr("src", url);
                $(".content").append(iframe);
                $(iframe).load(function() {
                    Loading.close(index);
                });

                //tab过多时
                var _lis = $(".tabsPageHeaderContent").find("li");
                var n = 0;
                for (var i = 0; i < _lis.length; i++) {
                    n += $(_lis[i]).width();
                }

                //获取右侧区域宽度
                var _width = $("#navTab").width();
                if (n > parseInt(_width)-150 ) {
                    $(".tabsRight,.tabsLeft").show();
                }


            }else{
                $("#navTab").find("li").removeClass("selected");
                $("#navTab").find("li[data-url='" + url + "']").addClass("selected");
                $(".content").find("iframe").removeClass("cy-show");
                $(".content").find("iframe[src='"+url+"']").addClass("cy-show");
                //更多列表
                $(".tabsMoreList").find("li").removeClass("selected");
                $(".tabsMoreList").find("li[data-url='"+url+"']").addClass("selected");
            }
        }

    });



    // layer.msg('hello');
    // $("#myaudio")[0].play();// 消息播放语音
    // var playTime = 1;
    // var audio = document.createElement("myaudio");
    // clearTimeout(vadioTimeOut);
    // audio.addEventListener('ended', function() {
    //     vadioTimeOut = setTimeout(function() {
    //         playTime = playTime + 1;
    //         playTime < 3 ? audio.play() : clearTimeout(vadioTimeOut);
    //     }, 500);
    // })
    // Notification.permission = "granted";
    // if (Notification.permission == "granted") {
    //     var notification = new Notification(title, {
    //         body : content,
    //         icon : iconUrl
    //     });
    //
    //     notification.onclick = function() {
    //         notification.close();
    //     };
    // }

}

// 心跳包
function keepalive(ws) {
    var time = new Date();
    if (last_health != -1 && (time.getTime() - last_health > health_timeout)) {

        // ws.close();
    } else {
        if (ws.bufferedAmount == 0) {
            ws.send('~HC~');
        }
    }
}