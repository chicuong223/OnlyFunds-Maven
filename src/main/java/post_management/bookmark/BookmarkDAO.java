/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package post_management.bookmark;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import user_management.user.User;
import utils.DBConnect;

/**
 *
 * @author DELL
 */
public class BookmarkDAO {
    public boolean CheckBookmark(String username, int postId) {
        boolean result = false;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql;
        try {
            con = DBConnect.makeConnection();
            sql = "Select *\n"
                    + "From Bookmark\n"
                    + "Where username=? and post_id=?";
            if (con != null) {
                ps = con.prepareStatement(sql);
                ps.setString(1, username);
                ps.setInt(2, postId);
                rs = ps.executeQuery();
                if (rs.next()) {
                    result = true;
                }
                ps.close();
                con.close();
                return result;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
    public boolean AddBookmark(User user, int postId) {
        return AddBookmark(user.getUsername(), postId);
    }
    public boolean AddBookmark(String username, int postId) {
        Connection con = null;
        PreparedStatement ps = null;
        String sql;
        try {
            con = DBConnect.makeConnection();
            sql="Insert into Bookmark(username, post_id)\n"
                    + "Values(?, ?)";
            if (con != null) {
                ps=con.prepareStatement(sql);
                ps.setString(1, username);
                ps.setInt(2, postId);
                boolean result = ps.executeUpdate() > 0;
                System.err.println("ok add");
                ps.close();
                con.close();
                return result;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
    public boolean DeleteBookmark(User user, int postId){
        return DeleteBookmark(user.getUsername(), postId);
    }
    public boolean DeleteBookmark(String username, int postId){
        Connection con=null;
        PreparedStatement ps = null;
        String sql;
        try {
            con = DBConnect.makeConnection();
            sql="DELETE FROM Bookmark \n"
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
