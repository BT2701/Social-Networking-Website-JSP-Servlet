package org.example.j2ee.DAO;

import jakarta.persistence.EntityManager;
import org.example.j2ee.Util.JPAUtil;

public class ReactionDAO {
    private EntityManager entityManager;
    public ReactionDAO() {
        entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
    }
//    public int getReactionsByPost(){
//
//    }
}
