package org.example.j2ee.Socket;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.example.j2ee.Model.User;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/notifications/{userId}")
public class NotificationWebSocket {

    private static ConcurrentHashMap<Integer, Session> userSessions = new ConcurrentHashMap<>();
    private static ObjectMapper objectMapper = new ObjectMapper(); // Dùng để chuyển đổi object thành JSON

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

    public static void sendNotification(int receiverId, User user, String content) {
        Session session = userSessions.get(receiverId);
        if (session != null && session.isOpen()) {
            try {
                var notificationData = new HashMap<String, Object>();
                notificationData.put("user", user);
                notificationData.put("content", content);

                String jsonMessage = objectMapper.writeValueAsString(notificationData);

                session.getBasicRemote().sendText(jsonMessage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
