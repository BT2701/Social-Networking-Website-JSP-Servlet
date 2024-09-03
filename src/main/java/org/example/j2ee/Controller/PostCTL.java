/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.example.j2ee.Controller;

import jakarta.persistence.EntityManager;

import java.util.List;

import org.example.j2ee.Model.Post;
import org.example.j2ee.Model.User;
import org.example.j2ee.Service.PostSV;
import org.example.j2ee.Util.JPAUtil;

/**
 * @author phamv
 */
public class PostCTL {
    PostSV postSV = new PostSV();
    EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();

    public Post createPost(int userId, String statusText, String images) {
        Post newPost = new Post();
        User user = em.find(User.class, userId);
        newPost.setContent(statusText);
        newPost.setUser(user);
        newPost.setImage(images);
        return postSV.createPost(newPost);
    }


    public List<Post> getTheNewestPosts() {
        return postSV.getTheNewestPosts();
    }
}
