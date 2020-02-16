var stompClient = null;
//加载完浏览器后调用connect()，打开双通道
$(function () {
    //打开双通道
    connect();
})
//强制关闭浏览器  调用websocket.close(),进行正常关闭
window.onunload = function () {
    disconnect();
}

function connect() {
    //连接SockJS的endpoint名称为"endpointYoung"
    var socket = new SockJS('http://127.0.0.1:8080/endpointYoung');
    stompClient = Stomp.over(socket);//使用STMOP子协议的WebSocket客户端
    stompClient.connect({}, function (frame) {//连接WebSocket服务端
        console.log('Connected:' + frame);
        //通过stompClient.subscribe订阅/topic/broadcast 目标(destination)发送的消息
        stompClient.subscribe('/topic/broadcast', function (response) {
            showResponse(JSON.parse(response.body));
        });
    });
}

//关闭双通道
function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
}

//服务端推送的广播消息显示
function showResponse(message) {
    $("#response").append("<p>" + message.userName + "," + message.age + "</p>");
}