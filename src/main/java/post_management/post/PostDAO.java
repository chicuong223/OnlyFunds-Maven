/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package post_management.post;

import user_management.user.User;
import user_management.user.UserDAO;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import utils.DBConnect;

/**
 *
 * @author chiuy
 */
public class PostDAO {

    
    public int getLatestPostIdByUser(User user){
        int postId = -1;
        try {
            Connection  con = DBConnect.makeConnection();
            if(con != null){
                PreparedStatement ps = con.prepareStatement("SELECT MAX(id) as id FROM Post WHERE uploader_username = ?");
                ps.setString(1, user.getUsername());
                ResultSet rs = ps.executeQuery();
                if(rs.next())
                    postId = rs.getInt("id");
                rs.close();
                ps.close();
                con.close();
            }
        }
        catch (SQLException e) {
        }
        return postId;
    }
    
    public int countPostsByUser(User user) {
        int count = 0;
        try {
            Connection con = DBConnect.makeConnection();
            if (con != null) {
                try (PreparedStatement ps = con.prepareStatement("SELECT COUNT(id) as countNo FROM Post WHERE uploader_username = ? AND is_active = 1")) {
                    ps.setString(1, user.getUsername());
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            count = rs.getInt("countNo");
                        }
                    }
                }
                con.close();
            }
        } catch (SQLException e) {
        }
        return count;
    }

    //get posts by user
    public ArrayList<Post> getPostsByUserPage(User user, int pageIndex) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Post> lst = null;
        try {
            con = DBConnect.makeConnection();
            if (con != null) {
                lst = new ArrayList<>();
                ps = con.prepareStatement("SELECT * FROM\n"
                        + "(SELECT ROW_NUMBER() OVER (ORDER BY id DESC) as r,\n"
                        + "* FROM Post WHERE uploader_username = ? AND is_active = 1) as x\n"
                        + "WHERE x.r between ?*3-(3-1) and ?*3");
                ps.setString(1, user.getUsername());
                ps.setInt(2, pageIndex);
                ps.setInt(3, pageIndex);
                rs = ps.executeQuery();
                while (rs.next()) {
                    int postID = rs.getInt("id");
                    String postTitle = rs.getString("title");
                    String postDescription = rs.getString("description");
                    String attachmentURL = rs.getString("attachment_URL");
                    int viewCount = rs.getInt("view_count");
                    Date uploadDate = rs.getDate("upload_date");
                    boolean isActive = rs.getBoolean("is_active");
                    Post post = new Post(postID, user, postTitle, postDescription, attachmentURL, uploadDate, viewCount, isActive);
                    lst.add(post);
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
        return lst;
    }

    public Post getPostByID(int ID) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Post post = null;
        try {
            con = DBConnect.makeConnection();
            if (con != null) {
                UserDAO uDAO = new UserDAO();
                ps = con.prepareStatement("SELECT * FROM Post WHERE id = ?");
                ps.setInt(1, ID);
                rs = ps.executeQuery();
                if (rs.next()) {
                    int postID = rs.getInt("id");
                    String postTitle = rs.getString("title");
                    String postDescription = rs.getString("description");
                    String attachmentURL = rs.getString("attachment_URL");
                    int viewCount = rs.getInt("view_count");
                    Date uploadDate = rs.getDate("upload_date");
                    String uploaderUsername = rs.getString("uploader_username");
                    User uploader = uDAO.getUserByUsername(uploaderUsername);
                    boolean isActive = rs.getBoolean("is_active");
                    post = new Post(postID, uploader, postTitle, postDescription, attachmentURL, uploadDate, viewCount, isActive);
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
        return post;
    }

    public boolean deactivatePost(Post post) {
        Connection con = null;
        PreparedStatement ps = null;
        boolean success = false;
        try {
            con = DBConnect.makeConnection();
            if (con != null) {
                ps = con.prepareStatement("UPDATE Post SET is_active = 0 WHERE id = ?");
                ps.setInt(1, post.getPostId());
                success = ps.executeUpdate() >= 1;
            }
        } catch (SQLException e) {
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
            }
        }
        return success;
    }

    public boolean addPost(Post post) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBConnect.makeConnection();
            if (con != null) {
                ps = con.prepareStatement("INSERT INTO Post(title, description, attachment_URL, uploader_username, upload_date, view_count, is_active)\n"
                        + "VALUES(?, ?, ?, ?, ?, ?, ?)");
                ps.setString(1, post.getTitle());
                ps.setString(2, post.getDescription());
                ps.setString(3, post.getAttachmentURL());
                ps.setInt(6, post.getViewCount());
                ps.setDate(5, post.getUploadDate());
                ps.setBoolean(7, post.isIsActive());
                ps.setString(4, post.getUploader().getUsername());
                ps.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
            }
        }
        return false;
    }

    public boolean updatePost(Post post) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBConnect.makeConnection();
            if (con != null) {
                ps = con.prepareStatement("UPDATE Post SET title = ?, description = ?, attachment_URL = ? WHERE id = ?\n");
                ps.setString(1, post.getTitle());
                ps.setString(2, post.getDescription());
                ps.setString(3, post.getAttachmentURL());
                ps.setInt(4, post.getPostId());
                ps.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
            }
        }
        return false;
    }

    public ArrayList<Post> getLikedPostsByUser(User user) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Post> lst = null;
        try {
            con = DBConnect.makeConnection();
            if (con != null) {
                lst = new ArrayList<>();
                ps = con.prepareStatement("SELECT * FROM Post WHERE id in \n"
                        + "(SELECT post_id FROM Post_Like WHERE username LIKE ?)");
                ps.setString(1, user.getUsername());
                rs = ps.executeQuery();
                while (rs.next()) {
                    int postID = rs.getInt("id");
                    String postTitle = rs.getString("title");
                    String postDescription = rs.getString("description");
                    String attachmentURL = rs.getString("attachment_URL");
                    int viewCount = rs.getInt("view_count");
                    Date uploadDate = rs.getDate("upload_date");
                    boolean isActive = rs.getBoolean("is_active");
                    Post post = new Post(postID, user, postTitle, postDescription, attachmentURL, uploadDate, viewCount, isActive);
                    lst.add(post);
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
        return lst;
    }

    //Not tested
    public ArrayList<Post> getTopPostsInCategory(int categoryID) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Post> lst = null;
        String sql
                = "select top 10 p.*, count(pl.username) as NumOfLike\n"
                + "from  Post_Category_Map pc, Post p Left join Post_Like pl on p.id=pl.post_id  \n"
                + "where p.id=pc.post_id\n"
                + "	and pc.category_id=?\n"
                + "group by p.attachment_URL, p.description, p.id, p.is_active, p.title,\n"
                + "	p.upload_date, p.uploader_username, p.view_count\n"
                + "order by NumOfLike";
        try {
            con = DBConnect.makeConnection();
            if (con != null) {
                lst = new ArrayList<>();
                ps = con.prepareStatement(sql);
                ps.setInt(1, categoryID);
                rs = ps.executeQuery();
                while (rs.next()) {
                    int postID = rs.getInt("id");
                    String postTitle = rs.getString("title");
                    String postDescription = rs.getString("description");
                    String attachmentURL = rs.getString("attachment_URL");
                    String uploaderUName = rs.getString("uploader_username");
                    UserDAO uDAO = new UserDAO();
                    User uploader = uDAO.getUserByUsername(uploaderUName);
                    int viewCount = rs.getInt("view_count");
                    Date uploadDate = rs.getDate("upload_date");
                    boolean isActive = rs.getBoolean("is_active");
                    Post post = new Post(postID, uploader, postTitle, postDescription, attachmentURL, uploadDate, viewCount, isActive);
                    lst.add(post);
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
        return lst;
    }

    //Not tested
    public ArrayList<Post> getSearchedPost(String search) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Post> lst = null;
        String sql
                = "select p.*\n"
                + "from Post p\n"
                + "where p.is_active='1'"
                + "     and(p.uploader_username Like ?\n"
                + "	or p.title Like ?)";
        try {
            con = DBConnect.makeConnection();
            if (con != null) {
                lst = new ArrayList<>();
                ps = con.prepareStatement(sql);
                search="%"+search+"%";
                ps.setString(1, search);
                ps.setString(2, search);
                rs = ps.executeQuery();
                while (rs.next()) {
                    int postID = rs.getInt("id");
                    String postTitle = rs.getString("title");
                    String postDescription = rs.getString("description");
                    String attachmentURL = rs.getString("attachment_URL");
                    String uploaderUName = rs.getString("uploader_username");
                    UserDAO uDAO = new UserDAO();
                    User uploader = uDAO.getUserByUsername(uploaderUName);
                    int viewCount = rs.getInt("view_count");
                    Date uploadDate = rs.getDate("upload_date");
                    boolean isActive = rs.getBoolean("is_active");
                    Post post = new Post(postID, uploader, postTitle, postDescription, attachmentURL, uploadDate, viewCount, isActive);
                    lst.add(post);
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
        return lst;
    }
    public static void main(String[] args) {
        User user = new User();
        user.setUsername("chicuong223");
        PostDAO dao = new PostDAO();
        System.out.println(dao.getLatestPostIdByUser(user));
    }
}
