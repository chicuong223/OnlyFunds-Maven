/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notification;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import post_management.post.Post;
import post_management.post.PostDAO;
import user_management.user.User;
import utils.DBConnect;

/**
 *
 * @author chiuy
 */
public class NotificationDAO {

    public boolean generateNotification(Post post, User user) {
        boolean result = false;
        try {
            Connection con = DBConnect.makeConnection();
            if (con != null) {
                try (PreparedStatement ps = con.prepareStatement("INSERT INTO Notification(content, recipient_username, notification_date, is_read, post_id)\n"
                        + "VALUES (?, ?, ?, ?, (SELECT MAX(id) FROM Post WHERE uploader_username = ?))")) {
                    ps.setString(1, post.getUploader().getUsername() + " has uploaded a new post");
                    ps.setString(2, user.getUsername());
                    ps.setDate(3, post.getUploadDate());
                    ps.setBoolean(4, false);
                    ps.setString(5, post.getUploader().getUsername());
                    result = ps.executeUpdate() > 0;
                }
                con.close();
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
    
    public ArrayList<Notification> getUnreadNotificationsByRecipient(User user){
        ArrayList<Notification> lst = new ArrayList<>();
        try {
            Connection con = DBConnect.makeConnection();
            if(con != null){
                try (PreparedStatement ps = con.prepareStatement("SELECT  * FROM Notification WHERE recipient_username = ? AND is_read = 0")) {
                    ps.setString(1, user.getUsername());
                    try (ResultSet rs = ps.executeQuery()) {
                        PostDAO pDAO = new PostDAO();
                        while(rs.next()){
                            int id = rs.getInt("id");
                            String content = rs.getString("content");
                            Date date = rs.getDate("notification_date");
                            boolean isRead = rs.getBoolean("is_read");
                            int postId = rs.getInt("post_id");
                            Post post = pDAO.getPostByID(postId);
                            Notification noti = new Notification(id, user, content, date, isRead, post);
                            lst.add(noti);
                        }
                    }
                }
                con.close();
            }
        }
        catch (SQLException e) {
        }
        return lst;
    }
    
    public ArrayList<Notification> getNotificationsByRecipient(User recipient){
        ArrayList<Notification> lst = new ArrayList<>();
        try {
            Connection con = DBConnect.makeConnection();
            if(con != null){
                try (PreparedStatement ps = con.prepareStatement("SELECT  id, content, post_id FROM Notification WHERE recipient_username = ? ORDER BY id DESC")) {
                    ps.setString(1, recipient.getUsername());
                    try (ResultSet rs = ps.executeQuery()) {
                        PostDAO pDAO = new PostDAO();
                        while(rs.next()){
                            int id = rs.getInt("id");
                            String content = rs.getString("content");
//                            Date date = rs.getDate("notification_date");
                            boolean isRead = rs.getBoolean("is_read");
                            int postId = rs.getInt("post_id");
                            Post post = pDAO.getPostByID(postId);
                            Notification noti = new Notification();
                            noti.setNotificationId(id);
                            noti.setContent(content);
                            noti.setPost(post);
                            noti.setIsRead(isRead);
//                            Notification noti = new Notification(id, recipient, content, date, isRead, post);
                            lst.add(noti);
                        }
                    }
                }
                con.close();
            }
        }
        catch (SQLException e) {
        }
        return lst;
    }
    
    public boolean setIsRead(Notification noti){
        boolean result = false;
        try {
            Connection con = DBConnect.makeConnection();
            if(con != null){
                PreparedStatement ps = con.prepareStatement("UPDATE Notification SET is_read = 1 WHERE id = ?");
                ps.setInt(1, noti.getNotificationId());
                result = ps.executeUpdate() > 0;
                ps.close();
                con.close();
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
}
