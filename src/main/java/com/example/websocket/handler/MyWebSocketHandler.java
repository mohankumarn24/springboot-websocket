package com.example.websocket.handler;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MyWebSocketHandler extends TextWebSocketHandler {

    // Stores all currently connected clients.
    //
    // Example:
    //      Client A connects -> Session A added
    //      Client B connects -> Session B added
    //      Client C connects -> Session C added
    //
    // sessions:
    //      [SessionA, SessionB, SessionC]
    //
    // We maintain this list ourselves because raw WebSocket does not automatically provide broadcasting support
    private static final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();


    // Called automatically by Spring whenever a new client successfully establishes a WebSocket connection.
    //
    // Example:
    //      Postman -> ws://localhost:8080/chat
    //
    // Spring creates a WebSocketSession and invokes this method.
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {

        // Add newly connected client to our list
        sessions.add(session);

        System.out.println(String.format("Connected: %s", session.getId()));
        System.out.println(String.format("Total Clients: %d", sessions.size()));
    }


    // Called automatically by Spring whenever a connected client sends a text message.
    //
    // Example:
    //      Client A sends: "Hello"
    //
    // Spring receives the message and invokes this method.
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {

        // Extract actual text from WebSocket frame
        String payload = message.getPayload();

        System.out.println(String.format("Received from %s : %s", session.getId(), payload));

        // ---------------------------------------------------
        // RESPONSE TO SENDER ONLY
        // ---------------------------------------------------
        //
        // session = client who sent the message
        //
        // Example:
        //      Client A sends "Hello"
        //
        // Only Client A receives:
        //      "Server received -> Hello"
        //
        session.sendMessage(new TextMessage(String.format("Server received -> %s", payload)));


        // ---------------------------------------------------
        // BROADCAST TO ALL CONNECTED CLIENTS
        // ---------------------------------------------------
        //
        // Iterate through every connected session.
        //
        // Example:
        //
        // sessions:
        //      [SessionA, SessionB, SessionC]
        //
        // Send message to everyone.
        //
        // Result:
        //
        // Client A receives:
        //      Broadcast from A : Hello
        //
        // Client B receives:
        //      Broadcast from A : Hello
        //
        // Client C receives:
        //      Broadcast from A : Hello
        //
        for (WebSocketSession s : sessions) {
            // Send only if connection is still active
            if (s.isOpen()) {
                s.sendMessage(new TextMessage(String.format("Broadcast from %s : %s", session.getId(), payload)));
            }
        }
    }


    // Called automatically by Spring when a client disconnects.
    //
    // Example:
    // User closes Postman tab
    // User clicks Disconnect
    // Network connection drops
    //
    // Spring detects disconnect and invokes this method.
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {

        // Remove disconnected client from active session list
        sessions.remove(session);

        System.out.println(String.format("Disconnected: %s", session.getId()));
        System.out.println(String.format("Total Clients: %d", sessions.size()));
    }
}

/*
MyWebSocketHandler:
Client connects      -> afterConnectionEstablished()
Client sends message -> handleTextMessage()
Client disconnects   -> afterConnectionClosed()
 */