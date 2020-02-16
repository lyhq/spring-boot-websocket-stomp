package com.you.controller;

import com.you.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

/***
 * WebSocketController 控制器
 *
 * @author: YangRun
 * @date: 2020/2/15
 */
@Controller
public class WebSocketController {

    /**
     * SpringBoot提供的操作 WebSocket API 的对象
     */
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    /**
     * 广播推送消息
     */
    //@Scheduled(fixedRate = 5000)
    public void broadcastMessageByTopic() {
        System.out.println("服务端消息广播中...");
        User user = new User("1000120013010", "Tom", 15);
        /**
         * 向客户端推送消息
         * 参数一： 客户端监听的服务端的 url
         * 参数二： 发送的消息（可以是对象、字符串等）
         */
        this.simpMessagingTemplate.convertAndSend("/topic/broadcast", user);
    }

    /**
     * 推送一对一消息
     */
    //@Scheduled(fixedRate = 10000)
    public void sendQueueMessage() {
        System.out.println("服务端推送一对一消息...");
        User user = new User("13214324141", "Jack", 30);
        /**
         * 向客户端推送消息
         * 参数一： 接收消息的客户端标识
         * 参数二： 客户端监听的服务端的 url
         * 参数三： 发送的消息（可以是对象、字符串等）
         */
        this.simpMessagingTemplate.convertAndSendToUser(user.getId(), "/queue/response", user);
    }
}
