package org.example.j2ee.Service;

import org.example.j2ee.Model.User;
import org.example.j2ee.Util.JPAUtil;
import java.sql.Timestamp;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;

public class UserSV {

    // Không cần tạo EntityManagerFactory nữa
    // Thay vào đó, sử dụng JPAUtil để lấy EntityManagerFactory
    public User login(String username, String password) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            Query query = em.createQuery("SELECT u FROM User u WHERE (u.email = :username OR u.phone = :username) AND u.password = :password");
            query.setParameter("username", username);
            query.setParameter("password", password);
            return (User) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    // Phương thức đăng ký
    public boolean register(User user) {
        EntityManager em = null;
        EntityTransaction tx = null;
        try {
            em = JPAUtil.getEntityManagerFactory().createEntityManager();
            tx = em.getTransaction();
            tx.begin();

            // Đặt giá trị mặc định cho các trường bắt buộc nếu cần
            if (user.getLastActive() == null) {
                user.setLastActive(new Timestamp(System.currentTimeMillis())); // Đặt thời gian hiện tại cho lastActive
            }
            if (user.getIsOnline() == 0) {
                user.setIsOnline(1); // Đặt trạng thái online mặc định là 1
            }
            if (user.getTimeJoin() == null) {
                user.setTimeJoin(new Timestamp(System.currentTimeMillis())); // Đặt thời gian hiện tại cho timeJoin
            }

            // Lưu đối tượng User vào cơ sở dữ liệu
            em.persist(user);

            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback(); // Rollback transaction nếu có lỗi
            }
            e.printStackTrace(); // In lỗi để kiểm tra
            return false;
        } finally {
            if (em != null) {
                em.close(); // Đảm bảo đóng EntityManager
            }
        }
    }

    public boolean isUsernameExists(String username) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            Query query = em.createQuery("SELECT COUNT(u) FROM User u WHERE u.email = :username OR u.phone = :username");
            query.setParameter("username", username);
            long count = (Long) query.getSingleResult();
            return count > 0;
        } finally {
            em.close();
        }
    }

    // Thêm phương thức tìm tài khoản bằng username hoặc số điện thoại
    public User findAccountByUsername(String username) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            Query query = em.createQuery("SELECT u FROM User u WHERE u.email = :username OR u.phone = :username");
            query.setParameter("username", username);
            return (User) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public User findUserById(int userId) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            Query query = em.createQuery("SELECT u FROM User u WHERE u.id = :userId");
            query.setParameter("userId", userId);
            return (User) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }

    public boolean updatePassword(String userId, String newPassword) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();

            // Tìm user theo userId
            User user = em.find(User.class, userId);
            if (user != null) {
                // Cập nhật mật khẩu mới
                user.setPassword(newPassword);
                em.merge(user);
                tx.commit();
                return true; // Cập nhật thành công
            } else {
                tx.rollback();
                return false; // Không tìm thấy user
            }
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
            return false; // Có lỗi xảy ra
        } finally {
            em.close();
        }
    }

}
