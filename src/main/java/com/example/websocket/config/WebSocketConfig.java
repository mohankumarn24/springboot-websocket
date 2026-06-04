package com.example.websocket.config;

import com.example.websocket.handler.MyWebSocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket    // Enable WebSocket support
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry
                .addHandler(new MyWebSocketHandler(), "/chat")
                // WebSocket endpoint:
                // ws://localhost:8080/chat
                //
                // In HTTP      : GET /employees
                // In WebSocket : CONNECT /chat
                //
                // Spring routes all WebSocket lifecycle events (connect, message, disconnect) to MyWebSocketHandler
                .setAllowedOrigins("*");
    }
}