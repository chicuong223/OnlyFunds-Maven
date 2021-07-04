/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package post_management.comment;

import post_management.post.Post;
import user_management.user.User;
import java.sql.Date;

/**
 *
 * @author chiuy
 */
public class Comment {
    private int commentID;
    private User user;
    private Post post;
    private String content;
    private Date commentDate;
    private boolean isActive;
    
    public Comment(){}

    public Comment(int commentID, User user, Post post, String content, Date commentDate, boolean isActive) {
        this.commentID = commentID;
        this.user = user;
        this.post = post;
        this.content = content;
        this.commentDate = commentDate;
        this.isActive = isActive;
    }

    public int getCommentID() {
        return commentID;
    }

    public void setCommentID(int commentID) {
        this.commentID = commentID;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    
    
}
