package org.example.j2ee.DAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.example.j2ee.Model.Post;
import org.example.j2ee.Model.User;
import org.example.j2ee.Util.JPAUtil;

import java.util.List;

public class SearchDAO {
    private EntityManager entityManager;
    public SearchDAO() {
       entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
    }
    public List<Post> getPosts(String subContent) {
        String queryStr = "SELECT p FROM Post p WHERE p.content LIKE :subContent";
        TypedQuery<Post> query = entityManager.createQuery(queryStr, Post.class);
        query.setParameter("subContent", "%" + subContent + "%");
        return query.getResultList();
    }
    public List<User> getUsers(String subName) {
        String queryStr = "SELECT u FROM User u WHERE u.name LIKE :subName";
        TypedQuery<User> query = entityManager.createQuery(queryStr, User.class);
        query.setParameter("subName", "%" + subName + "%");
        return query.getResultList();
    }

}
