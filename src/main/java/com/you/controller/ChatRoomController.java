package com.you.controller;

import com.you.bean.ChatRoomRequest;
import com.you.bean.ChatRoomResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

/***
 * ChatRoomController 聊天室控制器
 *
 * 客户端主动发送消息到服务端，服务端响应指定的客户端消息，类似http无状态请求；
 *  websocket可以从服务器指定发送消息给哪个客户端，而不像http只能响应请求端；
 *
 * @author: YangRun
 * @date: 2020/2/15
 */
@Controller
public class ChatRoomController {

    /**
     * SpringBoot提供的操作 WebSocket API 的对象
     */
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    /**
     * 群发消息
     *
     * @MessageMapping: 类似@RequestMapping，客户端请求服务器的URL，前提是双方端点已经打开;
     * @SendTo: 作用跟convertAndSend类似，广播消息给与该通道相连的客户端；
     */
    @MessageMapping("/massRequest")
    //SendTo 发送至 Broker 下的指定订阅路径
    @SendTo("/mass/response")
    public ChatRoomResponse mass(ChatRoomRequest chatRoomRequest) {
        System.out.println("用户名 = " + chatRoomRequest.getUserName());
        System.out.println("内容 = " + chatRoomRequest.getChatMsg());
        ChatRoomResponse response = new ChatRoomResponse(chatRoomRequest.getUserName(), chatRoomRequest.getChatMsg());
        return response;
    }

    /**
     * 一对一聊天
     *
     * @MessageMapping: 类似@RequestMapping，客户端请求服务器的URL，前提是双方端点已经打开;
     */
    @MessageMapping("/aloneRequest")
    public ChatRoomResponse alone(ChatRoomRequest chatRoomRequest) {
        System.out.println("用户编号 = " + chatRoomRequest.getUserId());
        System.out.println("用户名 = " + chatRoomRequest.getUserName());
        System.out.println("内容 = " + chatRoomRequest.getChatMsg());
        ChatRoomResponse response = new ChatRoomResponse(chatRoomRequest.getUserName(), chatRoomRequest.getChatMsg());
        this.simpMessagingTemplate.convertAndSendToUser(chatRoomRequest.getUserId(), "/alone/response", response);
        return response;
    }
}