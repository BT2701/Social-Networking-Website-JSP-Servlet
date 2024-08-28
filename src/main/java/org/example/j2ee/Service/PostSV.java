package org.example.j2ee.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.example.j2ee.Model.Comment;
import org.example.j2ee.Model.Post;
import org.example.j2ee.Model.Reaction;
import org.example.j2ee.Model.User;
import org.example.j2ee.Util.JPAUtil;

import java.sql.Timestamp;
import java.util.List;

public class PostSV {
    public Post getPostById(int id) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            return em.find(Post.class, id);
        } finally {
            em.close();
        }
    }

    public boolean likePost(int userId, int postId) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();

            User user = em.find(User.class, userId);
            Post post = em.find(Post.class, postId);

            if (user == null || post == null) {
                return false;
            }

            Reaction reaction = new Reaction();
            reaction.setUser(user);
            reaction.setPost(post);
            reaction.setTimeline(new Timestamp(System.currentTimeMillis()));

            em.persist(reaction);

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

    public boolean unLikePost(int userId, int postId) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();

            List<Reaction> reaction = em.createQuery("SELECT r FROM Reaction r WHERE r.user.id = :userId AND r.post.id = :postId", Reaction.class)
                    .setParameter("userId", userId)
                    .setParameter("postId", postId)
                    .getResultList();

            if (!reaction.isEmpty()) {
                // Nếu tìm thấy, xóa đối tượng
                em.remove(reaction.get(0));
                transaction.commit();
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            em.close();
        }
    }

    public Comment commentPost(int userId, int postId, String commentContent) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();

            User user = em.find(User.class, userId);
            Post post = em.find(Post.class, postId);

            if (user == null || post == null) {
                return null;
            }

            Comment comment = new Comment();
            comment.setContent(commentContent);
            comment.setUser(user);
            comment.setPost(post);
            comment.setTimeline(new Timestamp(System.currentTimeMillis()));

            em.persist(comment);

            transaction.commit();
            return comment;
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
            e.printStackTrace();
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
