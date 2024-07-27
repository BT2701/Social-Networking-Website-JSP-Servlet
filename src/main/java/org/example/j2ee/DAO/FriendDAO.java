package org.example.j2ee.DAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.example.j2ee.Model.User;
import org.example.j2ee.Util.JPAUtil;

import java.util.List;

public class FriendDAO {
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
}
