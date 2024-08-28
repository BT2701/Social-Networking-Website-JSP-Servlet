//package org.example.j2ee.Socket;
//import jakarta.websocket.*;
//import jakarta.websocket.server.PathParam;
//import jakarta.websocket.server.ServerEndpoint;
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
//@ServerEndpoint("/notifications")
//public class NotificationWebSocket {
//
//    private static Map<String, Session> sessions = new ConcurrentHashMap<>();
//
//    @OnOpen
//    public void onOpen(Session session, @PathParam("userId") String userId) {
//        sessions.put(userId, session);
//    }
//
//    @OnClose
//    public void onClose(Session session, @PathParam("userId") String userId) {
//        sessions.remove(userId);
//    }
//
//    @OnError
//    public void onError(Session session, Throwable throwable) {
//        throwable.printStackTrace();
//    }
//
//    public static void sendNotification(String userId, String message) {
//        Session session = sessions.get(userId);
//        if (session != null && session.isOpen()) {
//            session.getAsyncRemote().sendText(message);
//        }
//    }
//}
//
