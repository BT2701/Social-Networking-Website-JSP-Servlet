package org.example.j2ee.Socket;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/notifications/{userId}")
public class NotificationWebSocket {

    private static ConcurrentHashMap<Integer, Session> userSessions = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("userId") int userId) {
        userSessions.put(userId, session);
    }

    @OnClose
    public void onClose(Session session, @PathParam("userId") int userId) {
        userSessions.remove(userId);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        throwable.printStackTrace();
    }

    public static void sendNotification(int userId, String message) {
        Session session = userSessions.get(userId);
        if (session != null && session.isOpen()) {
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
