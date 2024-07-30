package org.example.j2ee.DAO;

import jakarta.persistence.*;
import org.example.j2ee.Model.Friend;
import org.example.j2ee.Model.FriendRequest;
import org.example.j2ee.Model.User;
import org.example.j2ee.Util.JPAUtil;

import java.sql.Timestamp;
import java.util.List;


public class FriendRequestDAO {
    @PersistenceContext
    private EntityManager entityManager;
    public FriendRequestDAO() {
        entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
    }
    public List<FriendRequest> requestList(int currentUser) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Query query = em.createQuery("select rq from FriendRequest rq where rq.sender = :currentUser");
        query.setParameter("currentUser", currentUser);
        List<FriendRequest> friendRequests = query.getResultList();
        transaction.commit();
        return friendRequests;
//        TypedQuery<User> query = entityManager.createQuery(queryStr, User.class);
    }
    public List<FriendRequest> responseStack(int currentUser) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Query query = em.createQuery("select rq from FriendRequest rq where rq.receiver = :currentUser");
        query.setParameter("currentUser", currentUser);
        List<FriendRequest> friendRequests = query.getResultList();
        transaction.commit();
        return friendRequests;
    }
    public boolean sendFriendRequest(FriendRequest friendRequest) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction=em.getTransaction();
        try{
            transaction.begin();
            em.persist(friendRequest);
            transaction.commit();
            return true;
        }
        catch(Exception e){
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }
    public boolean refuseFriendRequest(FriendRequest friendRequest) {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(friendRequest);
            entityManager.getTransaction().commit();
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
    public boolean acceptFriendRequest(FriendRequest friendRequest) {
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(friendRequest);
            Friend friend = new Friend();
            friend.setUser1(friendRequest.getSender());
            friend.setUser2(friendRequest.getReceiver());
            friend.setTimeline(new Timestamp(System.currentTimeMillis()));
            friend.setIsfriend(1);
            entityManager.persist(friend);
            entityManager.getTransaction().commit();
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
