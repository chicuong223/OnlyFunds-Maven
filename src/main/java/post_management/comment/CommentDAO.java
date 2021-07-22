/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package post_management.comment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import post_management.post.Post;
import post_management.post.PostDAO;
import user_management.user.User;
import user_management.user.UserDAO;
import utils.DBConnect;

/**
 *
 * @author chiuy
 */
public class CommentDAO {

    public ArrayList<Comment> getCommentsByPost(int postID) {
        ArrayList<Comment> lst = new ArrayList<>();
        try {
            Connection con = DBConnect.makeConnection();
            if (con != null) {
                PreparedStatement ps;
                ps = con.prepareStatement("SELECT * FROM Comment WHERE post_id = ? AND is_active = 1 ORDER BY id DESC");
                ps.setInt(1, postID);
                ResultSet rs;
                rs = ps.executeQuery();
                UserDAO uDAO = new UserDAO();
                PostDAO pDAO = new PostDAO();
                while (rs.next()) {
                    Comment comment = new Comment(
                            rs.getInt("id"),
                            uDAO.getUserByUsername(rs.getString("username")),
                            pDAO.getPostByID(postID),
                            rs.getString("content"),
                            rs.getDate("comment_date"),
                            rs.getBoolean("is_active")
                    );
                    lst.add(comment);
                }
                rs.close();
                ps.close();
                con.close();
            }
        } catch (SQLException e) {
        }
        return lst;
    }

    public Comment getCommentByID(int commentID) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBConnect.makeConnection();
            if (con != null) {
                ps = con.prepareStatement("SELECT * FROM Comment WHERE id = ?");
                ps.setInt(1, commentID);
                rs = ps.executeQuery();
                UserDAO uDAO = new UserDAO();
                PostDAO pDAO = new PostDAO();
                if (rs.next()) {
                    Comment comment = new Comment(
                            commentID,
                            uDAO.getUserByUsername(rs.getString("username")),
                            pDAO.getPostByID(rs.getInt("post_id")),
                            rs.getString("content"),
                            rs.getDate("comment_date"),
                            rs.getBoolean("is_active")
                    );
                    return comment;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return null;
    }

    public ArrayList<Comment> getReportedComments() {
        ArrayList<Comment> lst = new ArrayList<>();
        try {
            Connection con = DBConnect.makeConnection();
            if (con != null) {
                PreparedStatement ps;
                ps = con.prepareStatement(""
                        + "SELECT * FROM Comment WHERE id IN\n"
                        + "(SELECT reported_object_id FROM Report WHERE type LIKE 'Comment')");
                ResultSet rs;
                rs = ps.executeQuery();
                UserDAO uDAO = new UserDAO();
                PostDAO pDAO = new PostDAO();
                while (rs.next()) {
                    Comment comment = new Comment(
                            rs.getInt("id"),
                            uDAO.getUserByUsername(rs.getString("username")),
                            pDAO.getPostByID(rs.getInt("id")),
                            rs.getString("content"),
                            rs.getDate("comment_date"),
                            rs.getBoolean("is_active")
                    );
                    lst.add(comment);
                }
                rs.close();
                ps.close();
                con.close();
            }
        } catch (SQLException e) {
        }
        return lst;
    }

    public boolean addComment(Comment comment) {
        boolean result = false;
        try {
            Connection con = DBConnect.makeConnection();
            if (con != null) {
                PreparedStatement ps;
                ps = con.prepareStatement("INSERT INTO Comment(username, post_id, content, comment_date, is_active) \n"
                        + "VALUES (?, ?, ?, ?, ?)");
                ps.setString(1, comment.getUser().getUsername());
                ps.setInt(2, comment.getPost().getPostId());
                ps.setString(3, comment.getContent());
                ps.setDate(4, comment.getCommentDate());
                ps.setBoolean(5, true);
                result = ps.executeUpdate() > 0;
                ps.close();
                con.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public boolean deactivateComment(int commentID) {
        boolean result = false;
        try {
            Connection con = DBConnect.makeConnection();
            if (con != null) {
                PreparedStatement ps;
                ps = con.prepareStatement("UPDATE Comment SET is_active = 0 WHERE id = ?");
                ps.setInt(1, commentID);
                result = ps.executeUpdate() > 0;
                ps.close();
                con.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public boolean activateComment(int commentID) {
        boolean result = false;
        try {
            Connection con = DBConnect.makeConnection();
            if (con != null) {
                PreparedStatement ps;
                ps = con.prepareStatement("UPDATE Comment SET is_active = 1 WHERE id = ?");
                ps.setInt(1, commentID);
                result = ps.executeUpdate() > 0;
                ps.close();
                con.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public int countCommentsByPost(int postID) {
        int count = 0;
        try {
            Connection con = DBConnect.makeConnection();
            if (con != null) {
                PreparedStatement ps;
                ps = con.prepareStatement("SELECT Count(id) as countNo FROM Comment WHERE post_id = ? AND is_active = 1");
                ps.setInt(1, postID);
                ResultSet rs;
                rs = ps.executeQuery();
                UserDAO uDAO = new UserDAO();
                PostDAO pDAO = new PostDAO();
                if (rs.next()) {
                    count = rs.getInt("countNo");
                }
                rs.close();
                ps.close();
                con.close();
            }
        } catch (SQLException e) {
        }
        return count;
    }

    public boolean editComment(int commentID, String content) {
        boolean result = false;
        String sql = "UPDATE Comment SET content = ? WHERE id = ?";
        try (Connection con = DBConnect.makeConnection()) {
            if (con != null) {
                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setString(1, content);
                    ps.setInt(2, commentID);
                    result = ps.executeUpdate() > 0;
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }

    public int CountReportedCommentsByUser(User user) {
        return CountReportedCommentsByUsername(user.getUsername());
    }

    public int CountReportedCommentsByUsername(String username) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBConnect.makeConnection();
            if (con != null) {
                String sql
                        = "select count (c.id) as num\n"
                        + "from Comment c\n"
                        + "where c.id in \n"
                        + "	(select r.reported_id\n"
                        + "	from Report r\n"
                        + "	where r.type='comment'\n"
                        + "		and r.status='approved')\n"
                        + "	and c.username=?";
                ps = con.prepareStatement(sql);
                ps.setString(1, username);
                rs = ps.executeQuery();
                if (rs.next()) {
                    return rs.getInt("num");
                }
            }
        } catch (SQLException e) {
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        CommentDAO dao = new CommentDAO();
//        dao.getCommentsByPost(3).forEach(com -> {
//            System.out.println(com.getCommentID());
//        });
//        System.out.println(dao.getCommentByID(2));
//        dao.getReportedComments().forEach(com -> {
//            System.out.println(com.getCommentID());
//        });

        dao.deactivateComment(1);
        System.out.println(dao.getCommentByID(1).isIsActive());
    }
}
