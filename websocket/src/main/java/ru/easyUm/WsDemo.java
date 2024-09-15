package ru.easyUm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@SpringBootApplication
public class WsDemo {
    public static void main(String[] args) {
        SpringApplication.run(WsDemo.class, args);
    }
}
