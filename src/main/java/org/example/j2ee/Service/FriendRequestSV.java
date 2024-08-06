package org.example.j2ee.Service;

import org.example.j2ee.Model.FriendRequest;
import org.example.j2ee.Util.JPAUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import org.example.j2ee.Model.Friend;

public class FriendRequestSV {

    public List<Object[]> getAllFriendRequestsByReceiverId(int receiverId, String limit) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            // Xây dựng câu truy vấn cơ bản
            String queryStr = "SELECT fr.id, fr.sender, fr.receiver, fr.timeline, "
                    + "u.name, u.avt, "
                    + "(SELECT COUNT(*) FROM Friend f WHERE f.user1 = u.id OR f.user2 = u.id) AS friendCount "
                    + "FROM FriendRequest fr "
                    + "JOIN User u ON fr.sender = u.id "
                    + "WHERE fr.receiver = :receiverId "
                    + "ORDER BY fr.timeline DESC";

            Query query = em.createQuery(queryStr);
            query.setParameter("receiverId", receiverId);

            // Nếu limit không phải là "all", thêm giới hạn số lượng kết quả
            if (!"all".equalsIgnoreCase(limit)) {
                try {
                    int limitValue = Integer.parseInt(limit);
                    if (limitValue > 0) {
                        query.setMaxResults(limitValue);
                    }
                } catch (NumberFormatException e) {
                    // Xử lý trường hợp limit không phải là số hợp lệ
                    // Bạn có thể ghi log hoặc thông báo lỗi ở đây nếu cần
                }
            }

            return query.getResultList(); // Trả về List<Object[]>
        } finally {
            em.close();
        }
    }

    public List<Object[]> getAllFriendRequestsBySenderId(int senderId, String limit) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            // Xây dựng câu truy vấn cơ bản
            String queryStr = "SELECT fr.id, fr.sender, fr.receiver, fr.timeline, "
                    + "u.name, u.avt, "
                    + "(SELECT COUNT(*) FROM Friend f WHERE f.user1 = u.id OR f.user2 = u.id) AS friendCount "
                    + "FROM FriendRequest fr "
                    + "JOIN User u ON fr.receiver = u.id "
                    + "WHERE fr.sender = :senderId "
                    + "ORDER BY fr.timeline DESC";

            Query query = em.createQuery(queryStr);
            query.setParameter("senderId", senderId);

            // Nếu limit không phải là "all", thêm giới hạn số lượng kết quả
            if (!"all".equalsIgnoreCase(limit)) {
                try {
                    int limitValue = Integer.parseInt(limit);
                    if (limitValue > 0) {
                        query.setMaxResults(limitValue);
                    }
                } catch (NumberFormatException e) {
                    // Xử lý trường hợp limit không phải là số hợp lệ
                    // Bạn có thể ghi log hoặc thông báo lỗi ở đây nếu cần
                }
            }

            return query.getResultList(); // Trả về List<Object[]>
        } finally {
            em.close();
        }
    }

    public boolean confirmFriendRequest(int requestId) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = em.getTransaction();
            transaction.begin();

            // Tìm yêu cầu kết bạn dựa trên ID
            FriendRequest friendRequest = em.find(FriendRequest.class, requestId);
            if (friendRequest == null) {
                // Không tìm thấy yêu cầu kết bạn
                return false;
            }

            // Tạo đối tượng Friend với sender là user1 và receiver là user2
            Friend friend = new Friend();
            friend.setUser1(friendRequest.getSender());
            friend.setUser2(friendRequest.getReceiver());
            friend.setTimeline(Timestamp.from(Instant.now()));
            friend.setIsfriend(1); // Hoặc giá trị khác tùy thuộc vào logic của bạn

            // Lưu đối tượng Friend vào cơ sở dữ liệu
            em.persist(friend);

            // Xóa yêu cầu kết bạn đã xác nhận
            em.remove(friendRequest);

            transaction.commit();
            return true; // Xác nhận thành công
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false; // Xác nhận không thành công
        } finally {
            em.close();
        }
    }

    public boolean deleteRequest(int requestId) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = em.getTransaction();
            transaction.begin();

            // Tìm yêu cầu kết bạn dựa trên ID
            FriendRequest friendRequest = em.find(FriendRequest.class, requestId);
            if (friendRequest == null) {
                // Không tìm thấy yêu cầu kết bạn
                return false;
            }

            // Xóa yêu cầu kết bạn
            em.remove(friendRequest);

            transaction.commit();
            return true; // Xóa thành công
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false; // Xóa không thành công
        } finally {
            em.close();
        }
    }

    public int addRequest(int senderId, int receiverId) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = em.getTransaction();
            transaction.begin();

            // Tạo yêu cầu kết bạn mới
            FriendRequest friendRequest = new FriendRequest();
            friendRequest.setSender(senderId);
            friendRequest.setReceiver(receiverId);
            friendRequest.setTimeline(Timestamp.from(Instant.now()));

            // Lưu yêu cầu kết bạn vào cơ sở dữ liệu
            em.persist(friendRequest);

            transaction.commit();

            // Trả về ID của yêu cầu kết bạn mới
            return friendRequest.getId();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return -1; // Trả về -1 nếu thêm yêu cầu kết bạn không thành công
        } finally {
            em.close();
        }
    }

    public List<Object[]> getListSuggestedFriends(int userId, String limit) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        List<Object[]> result = new ArrayList<>();
        try {
            // Truy vấn để lấy danh sách bạn chung và không có bạn chung
            String queryStr = "SELECT DISTINCT u2.id AS user_id, u2.name, u2.avt, "
                    + "COALESCE(common_friends.common_friends_count, 0) AS common_friends_count "
                    + "FROM User u2 "
                    + "LEFT JOIN ("
                    + "    SELECT u2.id AS user_id, COUNT(*) AS common_friends_count "
                    + "    FROM User u1 "
                    + "    JOIN Friend f1 ON (u1.id = f1.user1 OR u1.id = f1.user2) "
                    + "    JOIN Friend f2 ON (f1.user2 = f2.user1 OR f1.user2 = f2.user2) "
                    + "    JOIN User u2 ON (f2.user1 = u2.id OR f2.user2 = u2.id) "
                    + "    WHERE u1.id = :userId "
                    + "    AND f1.isfriend = 1 "
                    + "    AND f2.isfriend = 1 "
                    + "    AND u2.id <> u1.id "
                    + "    AND u2.id NOT IN ("
                    + "        SELECT user1 FROM Friend WHERE user2 = :userId AND isfriend = 1 "
                    + "        UNION "
                    + "        SELECT user2 FROM Friend WHERE user1 = :userId AND isfriend = 1"
                    + "    ) "
                    + "    GROUP BY u2.id "
                    + ") AS common_friends ON u2.id = common_friends.user_id "
                    + "WHERE u2.id <> :userId "
                    + "AND NOT EXISTS ("
                    + "    SELECT 1 FROM Friend f WHERE (f.user1 = u2.id AND f.user2 = :userId AND f.isfriend = 1) "
                    + "    OR (f.user2 = u2.id AND f.user1 = :userId AND f.isfriend = 1)"
                    + ") "
                    + "AND u2.id NOT IN ("
                    + "    SELECT sender FROM FriendRequest WHERE receiver = :userId "
                    + "    UNION "
                    + "    SELECT receiver FROM FriendRequest WHERE sender = :userId"
                    + ") "
                    + "ORDER BY common_friends_count DESC";

            Query query = em.createNativeQuery(queryStr);
            query.setParameter("userId", userId);

            // Áp dụng giới hạn nếu limit không phải là "all"
            if (!"all".equalsIgnoreCase(limit)) {
                try {
                    int limitValue = Integer.parseInt(limit);
                    if (limitValue > 0) {
                        query.setMaxResults(limitValue);
                    }
                } catch (NumberFormatException e) {
                    // Xử lý lỗi nếu limit không phải là số hợp lệ
                    e.printStackTrace();
                }
            }

            result = query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
            result = new ArrayList<>(); // Trả về danh sách rỗng nếu có lỗi
        } finally {
            em.close();
        }
        return result;
    }

}
