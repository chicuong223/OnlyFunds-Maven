/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user_management.follow;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import user_management.user.User;
import utils.DBConnect;

/**
 *
 * @author chiuy
 */
public class FollowDAO {

    public ArrayList<User> GetFollowersByUser(User user) {
        ArrayList<User> lst = new ArrayList<>();
        try {
            Connection con = DBConnect.makeConnection();
            if (con != null) {
                try (PreparedStatement ps = con.prepareStatement("SELECT * FROM [User] WHERE username IN \n"
                        + "(SELECT follower_username FROM Follow WHERE followed_username = ?) AND is_banned = 0")) {
                    ps.setString(1, user.getUsername());
                    try (ResultSet rs = ps.executeQuery()) {
                        while (rs.next()) {
                            String username = rs.getString("username");
                            String password = rs.getString("password");
                            String firstName = rs.getString("firstname");
                            String lastName = rs.getString("lastname");
                            String email = rs.getString("email");
                            String bio = rs.getString("bio");
                            String avatarURL = rs.getString("avatarURL");
                            boolean isBanned = rs.getBoolean("is_banned");
                            user = new User(username, password, lastName, firstName, email, bio, avatarURL, isBanned);
                            lst.add(user);
                        }
                    }
                }
                con.close();
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return lst;
    }
    
    public boolean addFollow(User user, User creator) {
        boolean result = false;
        String sql = "INSERT INTO Follow(follower_username, followed_username)\n"
                + "VALUES(?, ?)";
        try (Connection con = DBConnect.makeConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, creator.getUsername());
            result = ps.executeUpdate() > 0;
        }
        catch (Exception e) {
        }
        return result;
    }

    public boolean deleteFollow(User user, User creator) {
        boolean result = false;
        String sql = "DELETE FROM Follow WHERE follower_username = ? AND followed_username = ?";
        try (Connection con = DBConnect.makeConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, creator.getUsername());
            result = ps.executeUpdate() > 0;
        }
        catch (Exception e) {
        }
        return result;
    }
    
    public Follow getFollow(User user, User creator){
        Follow follow = null;
        String sql = "SELECT * FROM Follow WHERE follower_username = ? AND followed_username = ?";
        try (Connection con = DBConnect.makeConnection(); PreparedStatement ps = con.prepareStatement(sql)){
            ps.setString(1, user.getUsername());
            ps.setString(2, creator.getUsername());
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    follow = new Follow(user, creator);
                }
            }
        }
        catch (Exception e) {
        }
        return follow;
    }
}
