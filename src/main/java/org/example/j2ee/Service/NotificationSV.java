package org.example.j2ee.Service;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.servlet.http.HttpSession;
import org.example.j2ee.Model.Comment;
import org.example.j2ee.Model.Notification;
import org.example.j2ee.Model.Post;
import org.example.j2ee.Model.User;
import org.example.j2ee.Socket.NotificationWebSocket;
import org.example.j2ee.Util.JPAUtil;

import java.util.List;

public class NotificationSV {
    public boolean createOrUpdateNotification(String content, int userId, int receiverId, Post post) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();

            // Tìm kiếm Notification có cùng userId, receiverId và postId
            Query query = em.createQuery("SELECT n FROM Notification n WHERE n.user.id = :userId AND n.receiver.id = :receiverId AND n.post.id = :postId");
            query.setParameter("userId", userId);
            query.setParameter("receiverId", receiverId);
            query.setParameter("postId", post.getId());

            Notification notification;
            try {
                notification = (Notification) query.getSingleResult();
            } catch (NoResultException e) {
                notification = null; // Không tìm thấy kết quả nào
            }

            if (notification != null) {
                // Cập nhật timeline nếu Notification đã tồn tại
                notification.setTimeline(new java.sql.Timestamp(System.currentTimeMillis()));
                notification.setContent(content);
                em.merge(notification);
            } else {
                // Tạo mới Notification nếu không tồn tại
                notification = new Notification();
                notification.setUser(em.find(User.class, userId));
                notification.setPost(post);
                notification.setReceiver(em.find(User.class, receiverId));
                notification.setContent(content);
                notification.setIs_read(0);
                notification.setTimeline(new java.sql.Timestamp(System.currentTimeMillis()));
                em.persist(notification);
            }

            transaction.commit();

            // Gửi thông báo qua WebSocket nếu người nhận là người dùng đang đăng nhập hiện tại
            NotificationWebSocket.sendNotification(receiverId, content);

            return true;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

//    public boolean deleteNotification(int userId, int receiverId, int postId) {
//        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
//        EntityTransaction transaction = em.getTransaction();
//
//        try {
//            transaction.begin();
//
//            String query = "DELETE FROM Notification n WHERE n.user.id = :userId AND n.receiver.id = :receiverId AND n.post.id = :postId";
//            em.createQuery(query)
//                    .setParameter("userId", userId)
//                    .setParameter("receiverId", receiverId)
//                    .setParameter("postId", postId)
//                    .executeUpdate();
//
//            transaction.commit();
//            return true;
//        } catch (Exception e) {
//            if (transaction.isActive()) {
//                transaction.rollback();
//            }
//            e.printStackTrace();
//            return false;
//        } finally {
//            em.close();
//        }
//    }

    public List<Notification> getAllNotificationsByReceiverId (String receiverId) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();

            List<Notification> notifications = em.createQuery("SELECT n FROM Notification n WHERE n.receiver.id = :receiverId ORDER BY n.timeline DESC")
                    .setParameter("receiverId", receiverId)
                    .getResultList();

            transaction.commit();
            return notifications;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw new RuntimeException(e);
        }  finally {
            em.close();
        }
    }

    public boolean markAllNotificationsAsRead(String receiverId) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();

            int updatedCount = em.createQuery(
                            "UPDATE Notification n SET n.is_read = 1 WHERE n.receiver.id = :receiverId AND n.is_read = 0")
                    .setParameter("receiverId", receiverId)
                    .executeUpdate();

            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            em.close();
        }
    }
}
