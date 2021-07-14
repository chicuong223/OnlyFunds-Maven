/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package post_management.like;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import user_management.user.User;
import utils.DBConnect;

/**
 *
 * @author DELL
 */
public class CommentLikeDAO {
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
                ps.setString(0, username);
                ps.setInt(1, postId);
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
                    + "WHERE username=? and post_id\n";
            if (con != null) {
                ps=con.prepareStatement(sql);
                ps.setString(0, username);
                ps.setInt(1, postId);
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
