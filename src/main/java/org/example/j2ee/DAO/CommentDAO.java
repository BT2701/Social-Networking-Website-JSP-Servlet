package org.example.j2ee.DAO;

import jakarta.persistence.EntityManager;
import org.example.j2ee.Util.JPAUtil;

public class CommentDAO {
    private EntityManager entityManager;
    public CommentDAO() {
        entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
    }
}
