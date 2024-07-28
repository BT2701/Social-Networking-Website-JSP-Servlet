package org.example.j2ee.DAO;

import jakarta.persistence.EntityManager;
import org.example.j2ee.Util.JPAUtil;

public class PostDAO {
    private EntityManager entityManager;
    public PostDAO() {
        entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
    }

}
