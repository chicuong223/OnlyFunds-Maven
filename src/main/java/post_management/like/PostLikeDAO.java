/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package post_management.like;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import post_management.post.Post;
import user_management.user.User;
import user_management.user.UserDAO;
import utils.DBConnect;

/**
 *
 * @author chiuy
 */
public class PostLikeDAO {
    public int  countPostLikeByPost(Post post){
        int count = 0;
        try {
            Connection con = DBConnect.makeConnection();
            if(con != null){
                try (PreparedStatement ps = con.prepareStatement("SELECT COUNT(username) as countNo FROM Post_Like WHERE post_id = ?")) {
                    ps.setInt(1, post.getPostId());
                    try (ResultSet rs = ps.executeQuery()) {
                        UserDAO uDAO = new UserDAO();
                        if(rs.next()){
                            count = rs.getInt("countNo");
                        }
                    }
                }
                con.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return count;
    }
public boolean CheckPostLike(String username, int postId) {
    boolean result=false;     
    Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs=null;
        String sql;
        try {
            con = DBConnect.makeConnection();
            sql="Select *\n"
                    + "From Post_Like\n"
                    + "Where username=? and post_id=?";
            if (con != null) {
                ps=con.prepareStatement(sql);
                ps.setString(1, username);
                ps.setInt(2, postId);
                rs=ps.executeQuery();
                if(rs.next())
                    result=true;
                ps.close();
                con.close();
                return result;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
    public boolean AddPostLike(User user, int postId) {
        return AddPostLike(user.getUsername(), postId);
    }
    public boolean AddPostLike(String username, int postId) {
        Connection con = null;
        PreparedStatement ps = null;
        String sql;
        try {
            con = DBConnect.makeConnection();
            sql="Insert into Post_Like(username, post_id)\n"
                    + "Values(?, ?)";
            if (con != null) {
                ps=con.prepareStatement(sql);
                ps.setString(1, username);
                ps.setInt(2, postId);
                boolean result = ps.executeUpdate() > 0;
                ps.close();
                con.close();
                return result;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
    public boolean DeletePostLike(User user, int postId){
        return DeletePostLike(user.getUsername(), postId);
    }
    public boolean DeletePostLike(String username, int postId){
        Connection con=null;
        PreparedStatement ps = null;
        String sql;
        try {
            con = DBConnect.makeConnection();
            sql="DELETE FROM Post_Like \n"
                    + "WHERE username=? and post_id=?\n";
            if (con != null) {
                ps=con.prepareStatement(sql);
                ps.setString(1, username);
                ps.setInt(2, postId);
                boolean result = ps.executeUpdate() > 0;
                ps.close();
                con.close();
                return result;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}
