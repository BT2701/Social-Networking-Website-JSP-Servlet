package org.example.j2ee.Controller;

import java.util.ArrayList;
import org.example.j2ee.Model.FriendRequest;
import org.example.j2ee.Service.FriendRequestSV;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class FriendRequestCTL {

    private FriendRequestSV friendRequestSV;

    public FriendRequestCTL() {
        this.friendRequestSV = new FriendRequestSV(); // Khởi tạo Service lớp
    }

    // Method to get all friend requests by receiverId
    public List<Map<String, Object>> getAllFriendRequestsByReceiverId(int receiverId, String limit) {
        List<Object[]> resultList = friendRequestSV.getAllFriendRequestsByReceiverId(receiverId, limit);

        // Xử lý kết quả từ Service để trả về danh sách các Map
        List<Map<String, Object>> friendRequests = new ArrayList<>();
        for (Object[] result : resultList) {
            Map<String, Object> friendRequestData = new HashMap<>();
            friendRequestData.put("id", result[0]);
            friendRequestData.put("sender", result[1]);
            friendRequestData.put("receiver", result[2]);
            friendRequestData.put("timeline", result[3]);
            friendRequestData.put("name", result[4]);
            friendRequestData.put("avt", result[5]);
            friendRequestData.put("friendCount", result[6]);
            friendRequests.add(friendRequestData);
        }
        return friendRequests;
    }

    // Method to get all friend requests by senderId
    public List<Map<String, Object>> getAllFriendRequestsBySenderId(int senderId, String limit) {
        List<Object[]> resultList = friendRequestSV.getAllFriendRequestsBySenderId(senderId, limit);

        // Xử lý kết quả từ Service để trả về danh sách các Map
        List<Map<String, Object>> friendRequests = new ArrayList<>();
        for (Object[] result : resultList) {
            Map<String, Object> friendRequestData = new HashMap<>();
            friendRequestData.put("id", result[0]);
            friendRequestData.put("sender", result[1]);
            friendRequestData.put("receiver", result[2]);
            friendRequestData.put("timeline", result[3]);
            friendRequestData.put("name", result[4]);
            friendRequestData.put("avt", result[5]);
            friendRequestData.put("friendCount", result[6]);
            friendRequests.add(friendRequestData);
        }
        return friendRequests;
    }

    // Method to confirm a friend request
    public boolean confirmFriendRequest(int requestId) {
        // Gọi phương thức xác nhận yêu cầu kết bạn từ Service
        return friendRequestSV.confirmFriendRequest(requestId);
    }

    // Method to confirm a delete request
    public boolean deleteRequest(int requestId) {
        // Gọi phương thức xác nhận yêu cầu kết bạn từ Service
        return friendRequestSV.deleteRequest(requestId);
    }
    // Method to addRequest a add request
    public int addRequest(int senderId, int receiverId) {
        return friendRequestSV.addRequest(senderId, receiverId);
    }
    // Method to get top 40 suggested friends
    public List<Map<String, Object>> getListSuggestedFriends(int userId, String limit) {
        List<Object[]> resultList = friendRequestSV.getListSuggestedFriends(userId, limit);

        // Xử lý kết quả từ Service để trả về danh sách các Map
        List<Map<String, Object>> suggestedFriends = new ArrayList<>();
        for (Object[] result : resultList) {
            Map<String, Object> userData = new HashMap<>();
            userData.put("user_id", result[0]);
            userData.put("name", result[1]);
            userData.put("avt", result[2]);
            userData.put("common_friends_count", result[3]);
            suggestedFriends.add(userData);
        }
        return suggestedFriends;
    }

}
