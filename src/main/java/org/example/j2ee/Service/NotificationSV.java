package org.example.j2ee.Service;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.example.j2ee.Model.Comment;
import org.example.j2ee.Model.Notification;
import org.example.j2ee.Util.JPAUtil;

import java.util.List;

public class NotificationSV {
//    public void notify(String userId, String message) {
//        NotificationWebSocket.sendNotification(userId, message);
//    }

    public List<Notification> getAllNotificationsByUserId (String userId) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();

            List<Notification> notifications = em.createQuery("SELECT n FROM Notification n WHERE n.user.id = :userId ORDER BY n.timeline DESC")
                    .setParameter("userId", userId)
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

    public boolean markAllNotificationsAsRead(String userId) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();

            int updatedCount = em.createQuery(
                            "UPDATE Notification n SET n.is_read = 1 WHERE n.user.id = :userId AND n.is_read = 0")
                    .setParameter("userId", userId)
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
