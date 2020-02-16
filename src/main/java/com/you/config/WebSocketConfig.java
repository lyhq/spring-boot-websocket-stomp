package com.you.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/***
 * WebSocketConfig 配置WebSocket
 *
 * @EnableWebSocketMessageBroker: 注解开启使用STOMP协议来传输基于代理(message broker)的消息,
 * 这时控制器将支持使用@MessageMapping来映射客户端的请求,就像使用@RequestMapping一样
 *
 * WebSocketMessageBrokerConfigurer：实现WebSocket消息代理的接口，配置相关信息。
 *
 * @author: YangYoung
 * @date: 2020/2/15
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * 注册Stomp协议的节点（endpoint）,并映射指定的url
     *
     * @param registry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        /**
         * 注册一个Stomp的endpoint端点,客户端打开双通道时需要的url,允许所有的域名跨域访问，指定使用SockJS协议。
         */
        registry.addEndpoint("/endpointYoung").setAllowedOrigins("*").withSockJS();
    }

    /**
     * 配置消息代理（Message Broker）
     *
     * @param registry
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //广播消息需要配置一个 “/topic” 消息代理，点对点配置一个“/user”消息代理
        //聊天室功能增加代理： 群发（mass），单独聊天（alone）
        registry.enableSimpleBroker("/topic", "/user", "/mass", "/alone");
        //点对点配置的订阅路径前缀（在客户端的订阅路径上体现），不设置时，默认也是“/user”
        //registry.setUserDestinationPrefix("/user");
    }
}
