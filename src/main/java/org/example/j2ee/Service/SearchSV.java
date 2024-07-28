package org.example.j2ee.Service;

import org.example.j2ee.DAO.FriendDAO;
import org.example.j2ee.DAO.SearchDAO;
import org.example.j2ee.Model.Post;
import org.example.j2ee.Model.User;

import java.util.HashMap;
import java.util.List;

public class SearchSV {
    private SearchDAO searchDAO;
    private FriendDAO friendDAO;
    public SearchSV() {
        searchDAO = new SearchDAO();
        friendDAO = new FriendDAO();
    }
    public HashMap<String, Object> search(String keyword, String type, int currentUser) {
        HashMap<String, Object> result = new HashMap<>();
        List<Post> posts=searchDAO.getPosts(keyword);
        List<User> users=searchDAO.getUsers(keyword);
        List<User> friends= friendDAO.getFriends(currentUser);


        int index=0;
        for(int i=0;i <users.size();i++){
            Long friendsCount= 0L;
            if(friendDAO.getFriendsCount(users.get(i).getId())!=null){
                friendsCount=friendDAO.getFriendsCount(users.get(i).getId());
            }
            users.get(i).setFriendsCount(friendsCount);

            if(users.get(i).getId()==currentUser){
                index=i;
            }
        }
        users.remove(index);

        for(Post post:posts){
            if(post.getUser().getId()==currentUser){
                post.setOwner(true);
            }
            else post.setOwner(false);
        }

        result.put("friends", friends);
        if(type.equalsIgnoreCase("post")){
            result.put("posts",posts);
            return result;
        }
        else if(type.equalsIgnoreCase("user")){
            result.put("users",users);
            return result;
        }
        else {
            result.put("posts",posts);
            result.put("users",users);
            return result;
        }
    }

}
