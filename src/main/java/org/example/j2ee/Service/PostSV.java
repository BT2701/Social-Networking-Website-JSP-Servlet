package org.example.j2ee.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.example.j2ee.Model.Post;
import org.example.j2ee.Model.User;
import org.example.j2ee.Util.JPAUtil;

public class PostSV {
    public Post getPostById(int id) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.find(Post.class, id);
        } finally {
            em.close();
        }
    }

    public Post updatePost(Post post) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            post = em.merge(post);
            transaction.commit();
            return post;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace(); // Log lỗi chi tiết
            throw new RuntimeException(e);
        } finally {
            em.close();
        }
    }

    public boolean deletePost(int id) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            Post post = em.find(Post.class, id);
            if (post == null) {
                return false;
            }
            transaction.begin();
            em.remove(post);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            em.close();
        }
    }
}
