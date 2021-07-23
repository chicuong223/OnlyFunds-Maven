/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package post_management.bookmark;

import java.io.Serializable;
import post_management.post.Post;
import user_management.user.User;

/**
 *
 * @author chiuy
 */
public class Bookmark implements Serializable{
    private User user;
    private Post post;
    
    public Bookmark(){}

    public Bookmark(User user, Post post) {
        this.user = user;
        this.post = post;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
    
    
    
}
