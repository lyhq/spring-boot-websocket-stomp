package com.you;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 开启定时任务支持
 */
@EnableScheduling
@SpringBootApplication
public class WebSocketStompApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebSocketStompApplication.class, args);
	}

}
