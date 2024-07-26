package org.example.j2ee.Service;

import org.example.j2ee.DAO.SearchDAO;
import org.example.j2ee.Model.Post;
import org.example.j2ee.Model.User;

import java.util.HashMap;
import java.util.List;

public class SearchSV {
    private SearchDAO searchDAO;
    public SearchSV() {
        searchDAO = new SearchDAO();
    }
    public HashMap<String, Object> search(String keyword, String type, int currentUser) {
        HashMap<String, Object> result = new HashMap<>();
        List<Post> posts=searchDAO.getPosts(keyword);
        List<User> users=searchDAO.getUsers(keyword);
        List<User> friends= searchDAO.getFriends(currentUser);


        int index=0;
        for(int i=0;i <users.size();i++){
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
