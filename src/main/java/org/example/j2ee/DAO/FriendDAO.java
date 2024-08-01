package org.example.j2ee.DAO;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import org.example.j2ee.Model.Friend;
import org.example.j2ee.Model.User;
import org.example.j2ee.Util.JPAUtil;

import java.sql.Timestamp;
import java.util.List;

public class FriendDAO {
    @PersistenceContext
    private EntityManager entityManager;
    public FriendDAO() {
        entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
    }
    public Long getFriendsCount(int user){
        String queryStr= "SELECT count(u.id) as total FROM User u " +
                "JOIN Friend f ON (u.id = f.user1 OR u.id = f.user2) " +
                "WHERE (f.user1 = :userId OR f.user2 = :userId) AND u.id != :userId AND f.isfriend = 1 " +
                "GROUP BY u.id, u.name, u.email, u.avt";
        TypedQuery<Long> query = entityManager.createQuery(queryStr, Long.class);
        query.setParameter("userId", user);
        List<Long> results = query.getResultList();
        if (results.isEmpty()) {
            return 0L; // Trả về 0 nếu không có kết quả
        } else {
            return results.get(0); // Trả về kết quả đầu tiên
        }
    }
    public List<User> getFriends(int currentUser){
        String queryStr = "SELECT u FROM User u " +
                "JOIN Friend f ON (u.id = f.user1 OR u.id = f.user2) " +
                "WHERE (f.user1 = :userId OR f.user2 = :userId) AND u.id != :userId AND f.isfriend = 1 " +
                "GROUP BY u.id, u.name, u.email, u.avt";
        TypedQuery<User> query = entityManager.createQuery(queryStr, User.class);
        query.setParameter("userId", currentUser);
        return query.getResultList();
    }

    @Transactional
    public boolean removeFriend(int user1, int user2){
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction=em.getTransaction();
        try {
            transaction.begin();
            String queryStr = "DELETE FROM Friend WHERE (user1 = :user1 AND user2 = :user2) or (user1 = :user2 AND user2 = :user1)";
            Query query = em.createQuery(queryStr);
            query.setParameter("user1", user1);
            query.setParameter("user2", user2);
            query.executeUpdate();
            transaction.commit();
            return true;
        }
        catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
        finally {
            em.close();
        }

    }
}
