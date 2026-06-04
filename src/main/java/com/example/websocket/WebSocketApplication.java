package com.example.websocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebSocketApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebSocketApplication.class, args);
    }
}

/**
 * 1. Start Spring boot application
 *      - Spring starts an embedded Tomcat server on port 8080
 *      - WebSocket endpoint '/chat' is registered
 *      - Clients can now connect using:
 *          ws://localhost:8080/chat
 *
 * 2. Open 3 tabs in Postman (New > WebSocket). Enter 'ws://localhost:8080/chat' and click 'Connect'
 *      - Client A / Postman 1:  ws://localhost:8080/chat
 *      - Client B / Postman 2:  ws://localhost:8080/chat
 *      - Client C / Postman 3:  ws://localhost:8080/chat
 *
 * 3. Send 'Hello' from 'Client A'
 *      - Client A receives:
 *              Server received -> Hello
 *              Broadcast from <sessionId> : Hello
 *
 *      - Client B receives:
 *              Broadcast from <sessionId> : Hello
 *
 *      - Client C receives:
 *              Broadcast from <sessionId> : Hello
 *
 * 2. Spring Boot Console:
 *
 * 		Connected: 08144f3b-158a-0926-f7ff-eb5d4e8003e8
 * 		Total Clients: 1
 * 		Connected: 3c4ae1e7-4318-894b-ae1b-13b4c80e45c3
 * 		Total Clients: 2
 * 		Connected: 33d77d5c-0085-f687-1da6-5d7a4e558a72
 * 		Total Clients: 3
 *
 * 		Received from 08144f3b-158a-0926-f7ff-eb5d4e8003e8 : Hello
 *
 *      Disconnected: 384ca146-fc48-620a-2355-b297ce23ea66
 *      Total Clients: 2
 *      Disconnected: d4742154-a487-d355-bf0d-062be7c19e4a
 *      Total Clients: 1
 *      Disconnected: 4dd5bf82-a6f8-2e5f-0577-7282d4cb9425
 *      Total Clients: 0
 */