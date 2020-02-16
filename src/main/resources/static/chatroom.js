var stompClient = null;

//加载完浏览器后  调用connect()，打开双通道
$(function () {
    //打开双通道
    connect();

    $("#selectName").change(function () {
        alert("sendAloneUser...");
        sendAloneUser();
    });

    $("#sendMassMessage").click(function () {
        alert("sendMassMessage...");
        sendMassMessage();
    });

    $("#sendAloneMessage").click(function () {
        alert("sendAloneMessage...");
        sendAloneMessage();
    });
})

//强制关闭浏览器  调用websocket.close(),进行正常关闭
window.onunload = function () {
    disconnect();
}

//打开双通道
function connect() {
    //连接SockJS的endpoint名称为"endpointYoung"
    var socket = new SockJS('http://127.0.0.1:8080/endpointYoung');
    stompClient = Stomp.over(socket);//使用STMOP子协议的WebSocket客户端
    stompClient.connect({}, function (frame) {//连接WebSocket服务端
        console.log('Connected:' + frame);
        //接收广播信息
        stompTopic();
    });
}

//关闭双通道
function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
}

//广播（一对多）
function stompTopic() {
    //通过stompClient.subscribe订阅/topic/getResponse 目标(destination)发送的消息（广播接收信息）
    stompClient.subscribe('/mass/response', function (response) {
        var message = JSON.parse(response.body);
        //展示广播接收的内容
        var response = $("#chatRecord");
        response.append("<p><span>" + message.userName + ":</span><span>" + message.chatMsg + "</span></p>");
    });
}

//队列（一对一）
function stompQueue() {
    var userId = $("#selectName").val();
    alert("监听:" + userId);
    //通过stompClient.subscribe订阅/topic/getResponse 目标(destination)发送的消息（队列接收信息）
    stompClient.subscribe('/user/' + userId + '/alone/response', function (response) {
        var message = JSON.parse(response.body);
        //展示一对一的接收的内容接收
        var response = $("#chatRecord2");
        response.append("<p><span>" + message.userName + ":</span><span>" + message.chatMsg + "</span></p>");
    });
}

//选择发送给谁的时候触发连接服务器
function sendAloneUser() {
    stompQueue();
}

//群发
function sendMassMessage() {
    var postValue = {};
    var chatMsg = $("#sendChatMsg");
    var userId = $("#selectName").val();//发送人userId
    alert("userId="+userId);
    var userName = $("#selectName").find("option:selected").text();//发送人userName
    alert("userName="+userName);
    postValue.userName = userName;
    postValue.chatMsg = chatMsg.val();//发送内容
    if (userId == 1 || userId == null) {
        alert("请选择你是谁！");
        return;
    }
    if (chatMsg.val() == "" || userId == null) {
        alert("不能发送空消息！");
        return;
    }
    stompClient.send("/massRequest", {}, JSON.stringify(postValue));
    chatMsg.val("");
}

//单独发
function sendAloneMessage() {
    var postValue = {};
    var chatMsg = $("#sendChatMsg2");
    var userId = $("#selectName").val();//发送人userId
    var userName = $("#selectName").find("option:selected").text();//发送人userName
    var sendToId = $("#selectName2").val();//接收人userId
    var response = $("#chatRecord2");
    postValue.userName = userName;
    postValue.chatMsg = chatMsg.val();
    postValue.userId = sendToId;
    if (userId == 1 || userId == null) {
        alert("请选择你是谁！");
        return;
    }
    if (sendToId == 1 || sendToId == null) {
        alert("请选择你要发给谁！");
        return;
    }
    if (chatMsg.val() == "" || userId == null) {
        alert("不能发送空消息！");
        return;
    }
    stompClient.send("/aloneRequest", {}, JSON.stringify(postValue));
    response.append("<p><span>" + userName + ":</span><span>" + chatMsg.val() + "</span></p>");
    chatMsg.val("");
}