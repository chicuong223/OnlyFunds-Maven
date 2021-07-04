/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package post_management.like;

import user_management.user.User;
import post_management.comment.Comment;

/**
 *
 * @author chiuy
 */
public class CommentLike {
    private Comment comment;
    private User user;

    public CommentLike(){}
    
    public CommentLike(Comment comment, User user) {
        this.comment = comment;
        this.user = user;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    
}
