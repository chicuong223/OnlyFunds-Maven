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
import post_management.comment.Comment;
import user_management.user.User;
import utils.DBConnect;

/**
 *
 * @author DELL
 */
public class CommentLikeDAO {
    public int countCommentLikeByComment(Comment cmt) {
        return countCommentLikeByCommentId(cmt.getCommentID());
    }
    public int countCommentLikeByCommentId(int cmtId) {
        int count = 0;
        try {
            Connection con = DBConnect.makeConnection();
            if (con != null) {
                try (PreparedStatement ps = con.prepareStatement("SELECT COUNT(username) as countNo FROM Comment_Like WHERE comment_id = ?")) {
                    ps.setInt(1, cmtId);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
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
    public boolean CheckCommentLike(String username, int commentId) {
        boolean result = false;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql;
        try {
            con = DBConnect.makeConnection();
            sql = "Select *\n"
                    + "From Comment_Like\n"
                    + "Where username=? and comment_id=?";
            if (con != null) {
                ps = con.prepareStatement(sql);
                ps.setString(1, username);
                ps.setInt(2, commentId);
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
    public boolean AddCommentLike(User user, int commentId) {
        return AddCommentLike(user.getUsername(), commentId);
    }
    public boolean AddCommentLike(String username, int commentId) {
        Connection con = null;
        PreparedStatement ps = null;
        String sql;
        try {
            con = DBConnect.makeConnection();
            sql="Insert into Comment_Like(username, comment_id)\n"
                    + "Values(?, ?)";
            if (con != null) {
                ps=con.prepareStatement(sql);
                ps.setString(1, username);
                ps.setInt(2, commentId);
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
    public boolean DeleteCommentLike(User user, int commentId){
        return DeleteCommentLike(user.getUsername(), commentId);
    }
    public boolean DeleteCommentLike(String username, int commentId){
        Connection con=null;
        PreparedStatement ps = null;
        String sql;
        try {
            con = DBConnect.makeConnection();
            sql="DELETE FROM Comment_Like \n"
                    + "WHERE username=? and comment_id=?\n";
            if (con != null) {
                ps=con.prepareStatement(sql);
                ps.setString(1, username);
                ps.setInt(2, commentId);
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
