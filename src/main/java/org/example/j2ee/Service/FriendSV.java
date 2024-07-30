package org.example.j2ee.Service;

import org.example.j2ee.DAO.FriendDAO;
import org.example.j2ee.DAO.FriendRequestDAO;
import org.example.j2ee.Model.FriendRequest;

import java.sql.Timestamp;

public class FriendSV {
    private FriendDAO friendDAO;
    private FriendRequestDAO friendRequestDAO;
    public FriendSV() {
        friendDAO = new FriendDAO();
        friendRequestDAO = new FriendRequestDAO();
    }
    public boolean checkAddFriend(int currentUser, String friendId) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        int friend=Integer.parseInt(friendId);
        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setSender(currentUser);
        friendRequest.setReceiver(friend);
        friendRequest.setTimeline(timestamp);
        return friendRequestDAO.sendFriendRequest(friendRequest); // hoặc false nếu có lỗi
    }
    public boolean checkRemoveFriend(int currentUser,String friendId) {
        int friend=Integer.parseInt(friendId);
        return friendDAO.removeFriend(currentUser, friend); // hoặc false nếu có lỗi
    }
}
