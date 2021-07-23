/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notification;

import java.io.Serializable;
import user_management.user.User;
import java.sql.Date;
import post_management.post.Post;

/**
 *
 * @author chiuy
 */
public class Notification implements Serializable {

    private int notificationId;
    private User recipient;
    private String content;
    private Date notificationDate;
    private boolean isRead;
    private Post post;

    public Notification(int notificationId, User recipient, String content, Date notificationDate, boolean isRead, Post post) {
        this.notificationId = notificationId;
        this.recipient = recipient;
        this.content = content;
        this.notificationDate = notificationDate;
        this.isRead = isRead;
        this.post = post;
    }

    public Notification() {
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(Date notificationDate) {
        this.notificationDate = notificationDate;
    }

    public boolean isIsRead() {
        return isRead;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }

}
