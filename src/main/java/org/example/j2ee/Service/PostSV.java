package org.example.j2ee.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import org.example.j2ee.Model.Comment;
import org.example.j2ee.Model.Post;
import org.example.j2ee.Model.Reaction;
import org.example.j2ee.Model.User;
import org.example.j2ee.Util.JPAUtil;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

public class PostSV {

    private ProfileSV profileSV = new ProfileSV();

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

            Reaction reaction = em.createQuery("SELECT r FROM Reaction r WHERE r.user.id = :userId AND r.post.id = :postId", Reaction.class)
                    .setParameter("userId", userId)
                    .setParameter("postId", postId)
                    .getSingleResult();

            if (reaction != null) {
                em.remove(reaction);
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

//    public boolean createPost(Post post) {
//        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
//        EntityTransaction transaction = em.getTransaction();
//        try {
//            transaction.begin();
//            em.persist(post); // Use persist to create a new entity
//            transaction.commit();
//            return true; // Return true if commit is successful
//        } catch (Exception e) {
//            if (transaction.isActive()) {
//                transaction.rollback(); // Rollback in case of exception
//            }
//            e.printStackTrace();
//            return false; // Return false if there is an exception
//        } finally {
//            em.close(); // Đảm bảo đóng EntityManager
//        }
//    }

    public Post createPost(Post post) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(post); // Persist the new Post entity

            // Flush and refresh the entity to ensure it's updated with any database-generated values
            em.flush();       // Synchronize the persistence context with the database
            em.refresh(post); // Refresh the post object to reflect the database state

            transaction.commit(); // Commit the transaction
            return post; // Return the updated Post object
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback(); // Rollback in case of exception
            }
            e.printStackTrace();
            return null; // Return null if there is an exception
        } finally {
            em.close(); // Ensure EntityManager is closed
        }
    }


    public List<Post> getTheNewestPosts() {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        List<Post> posts = null;
        try {
            transaction.begin();
            posts = em.createQuery("SELECT p FROM Post p ORDER BY p.timeline DESC", Post.class)
                    .setMaxResults(5)
                    .getResultList();
            posts.forEach(post -> {
//                post.setComments(getCommentsByPostId(post.getId()));
//                post.setReactions(getReactionsByPostId(post.getId()));
                post.setLikedByUser(post.getReactions().stream().anyMatch(r -> r.getUser().getId() == post.getUser().getId()));
            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return posts;
    }
}
