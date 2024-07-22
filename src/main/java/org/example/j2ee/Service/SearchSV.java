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
    public HashMap<String, Object> search(String keyword, String type) {
        HashMap<String, Object> result = new HashMap<>();
        List<Post> posts=searchDAO.getPosts(keyword);
        List<User> users=searchDAO.getUsers(keyword);
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
