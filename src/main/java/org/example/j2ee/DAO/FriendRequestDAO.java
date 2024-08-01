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
        Query query = entityManager.createQuery("select rq from FriendRequest rq where rq.sender = :currentUser");
        query.setParameter("currentUser", currentUser);
        List<FriendRequest> friendRequests = query.getResultList();
        return friendRequests;
//        TypedQuery<User> query = entityManager.createQuery(queryStr, User.class);
    }
    public List<FriendRequest> responseStack(int currentUser) {
        Query query = entityManager.createQuery("select rq from FriendRequest rq where rq.receiver = :currentUser");
        query.setParameter("currentUser", currentUser);
        List<FriendRequest> friendRequests = query.getResultList();
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
    public boolean refuseFriendRequest(int currentUser, int sender) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction=em.getTransaction();
        try {
            transaction.begin();
            Query query= em.createQuery("delete from FriendRequest rq where rq.sender = :sender and rq.receiver = :currentUser");
            query.setParameter("sender", sender);
            query.setParameter("currentUser", currentUser);
            query.executeUpdate();
            transaction.commit();
            return true;
        }
        catch(Exception e){
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
        finally {
            em.close();
        }
    }
    public boolean cancel(int currentUser, int receiver) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction=em.getTransaction();
        try {
            transaction.begin();
            Query query= em.createQuery("delete from FriendRequest rq where rq.sender = :currentUser and rq.receiver = :receiver");
            query.setParameter("receiver", receiver);
            query.setParameter("currentUser", currentUser);
            query.executeUpdate();
            transaction.commit();
            return true;
        }
        catch(Exception e){
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
        finally {
            em.close();
        }
    }
    public boolean acceptFriendRequest(int currentUser, int sender) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction=em.getTransaction();
        try {
            transaction.begin();
            Query query= em.createQuery("delete from FriendRequest rq where rq.sender = :sender and rq.receiver = :currentUser");
            query.setParameter("sender", sender);
            query.setParameter("currentUser", currentUser);
            query.executeUpdate();
            Friend friend = new Friend();
            friend.setUser1(sender);
            friend.setUser2(currentUser);
            friend.setTimeline(new Timestamp(System.currentTimeMillis()));
            friend.setIsfriend(1);
            em.persist(friend);
            transaction.commit();
            return true;
        }
        catch(Exception e){
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        }
        finally {
            em.close();
        }
    }
}
