/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package post_management.post;

import user_management.user.User;
import java.sql.Date;

/**
 *
 * @author chiuy
 */
public class Post implements Comparable<Post>{
    private int postId;
    private User uploader;
    private String title;
    private String description;
    private String attachmentURL;
    private Date uploadDate;
    private int viewCount;
    private boolean isActive;
    
    public Post(){}

    public Post(int postId, User uploader, String title, String description, String attachmentURL, Date uploadDate, int viewCount, boolean isActive) {
        this.postId = postId;
        this.uploader = uploader;
        this.title = title;
        this.description = description;
        this.attachmentURL = attachmentURL;
        this.uploadDate = uploadDate;
        this.viewCount = viewCount;
        this.isActive = isActive;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public User getUploader() {
        return uploader;
    }

    public void setUploader(User uploader) {
        this.uploader = uploader;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAttachmentURL() {
        return attachmentURL;
    }

    public void setAttachmentURL(String attachmentURL) {
        this.attachmentURL = attachmentURL;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public String toString() {
        return "Post{" + "postId=" + postId + ", uploader=" + uploader.getUsername() + ", title=" + title + ", description=" + description + ", attachmentURL=" + attachmentURL + ", uploadDate=" + uploadDate + ", viewCount=" + viewCount + ", isActive=" + isActive + '}';
    }

    @Override
    public int compareTo(Post t) {
        if(postId < t.getPostId()) return 1;
        else if(postId == t.getPostId()) return 0;
        return -1;
   }
    
    
}
