/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package post_management.post;

import category.Category;
import user_management.user.User;
import user_management.user.UserDAO;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.DBConnect;

/**
 *
 * @author chiuy
 */
public class PostDAO {

    public int getLatestPostIdByUser(User user) {
        int postId = -1;
        try (Connection con = DBConnect.makeConnection()) {
            if (con != null)
                try (PreparedStatement ps = con.prepareStatement("SELECT MAX(id) as id FROM Post WHERE uploader_username = ?")) {
                ps.setString(1, user.getUsername());
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next())
                        postId = rs.getInt("id");
                }
            }
        }
        catch (SQLException e) {
        }
        return postId;
    }

    public int countPostsByUser(User user) {
        int count = -1;
        try (Connection con = DBConnect.makeConnection()) {
            if (con != null)
                try (PreparedStatement ps = con.prepareStatement("SELECT COUNT(id) as countNo FROM Post WHERE uploader_username = ?")) {
                ps.setString(1, user.getUsername());
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next())
                        count = rs.getInt("countNo");
                }
            }
        }
        catch (SQLException e) {
        }
        return count;
    }

    public int countActivePostsByUser(User user) {
        int count = -1;
        try (Connection con = DBConnect.makeConnection()) {
            if (con != null)
                try (PreparedStatement ps = con.prepareStatement("SELECT COUNT(id) as countNo FROM Post WHERE uploader_username = ? AND is_active = 1")) {
                ps.setString(1, user.getUsername());
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next())
                        count = rs.getInt("countNo");
                }
            }
        }
        catch (SQLException e) {
        }
        return count;
    }
    
    public int countInactivePostsByUser(User user){
        int count = -1;
        try (Connection con = DBConnect.makeConnection()) {
            if (con != null)
                try (PreparedStatement ps = con.prepareStatement("SELECT COUNT(id) as countNo FROM Post WHERE uploader_username = ? AND is_active = 0")) {
                ps.setString(1, user.getUsername());
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next())
                        count = rs.getInt("countNo");
                }
            }
        }
        catch (SQLException e) {
        }
        return count;
    }

    public ArrayList<Post> getPostsByUser(User user, int pageIndex) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Post> lst = new ArrayList<>();
        try {
            con = DBConnect.makeConnection();
            if (con != null) {
                ps = con.prepareStatement("SELECT * FROM\n"
                        + "(SELECT ROW_NUMBER() OVER (ORDER BY id DESC) as r,\n"
                        + "* FROM Post WHERE uploader_username = ?) as x\n"
                        + "WHERE x.r between ?*4-(4-1) and ?*4");
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
        }
        catch (SQLException e) {
        }
        finally {
            try {
                if (rs != null)
                    rs.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            }
            catch (SQLException e) {
            }
        }
        return lst;
    }

    public ArrayList<Post> getActivePostsByUser(User user, int pageIndex) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Post> lst = new ArrayList<>();
        try {
            con = DBConnect.makeConnection();
            if (con != null) {
                ps = con.prepareStatement("SELECT * FROM\n"
                        + "(SELECT ROW_NUMBER() OVER (ORDER BY id DESC) as r,\n"
                        + "* FROM Post WHERE uploader_username = ? AND is_active = 1) as x\n"
                        + "WHERE x.r between ?*4-(4-1) and ?*4");
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
        }
        catch (SQLException e) {
        }
        finally {
            try {
                if (rs != null)
                    rs.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            }
            catch (SQLException e) {
            }
        }
        return lst;
    }

    public ArrayList<Post> getInactivePostsByUser(User user, int pageIndex) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Post> lst = new ArrayList<>();
        try {
            con = DBConnect.makeConnection();
            if (con != null) {
                ps = con.prepareStatement("SELECT * FROM\n"
                        + "(SELECT ROW_NUMBER() OVER (ORDER BY id DESC) as r,\n"
                        + "* FROM Post WHERE uploader_username = ? AND is_active = 0) as x\n"
                        + "WHERE x.r between ?*4-(4-1) and ?*4");
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
        }
        catch (SQLException e) {
        }
        finally {
            try {
                if (rs != null)
                    rs.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            }
            catch (SQLException e) {
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
        }
        catch (SQLException e) {
        }
        finally {
            try {
                if (rs != null)
                    rs.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            }
            catch (SQLException e) {
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
        }
        catch (SQLException e) {
        }
        finally {
            try {
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            }
            catch (SQLException e) {
            }
        }
        return success;
    }

    public boolean deactivatePost(int id) {
        Connection con = null;
        PreparedStatement ps = null;
        boolean success = false;
        try {
            con = DBConnect.makeConnection();
            if (con != null) {
                ps = con.prepareStatement("UPDATE Post SET is_active = 0 WHERE id = ?");
                ps.setInt(1, id);
                success = ps.executeUpdate() >= 1;
            }
        }
        catch (SQLException e) {
        }
        finally {
            try {
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            }
            catch (SQLException e) {
            }
        }
        return success;
    }

    public boolean activatePost(int id) {
        Connection con = null;
        PreparedStatement ps = null;
        boolean success = false;
        try {
            con = DBConnect.makeConnection();
            if (con != null) {
                ps = con.prepareStatement("UPDATE Post SET is_active = 1 WHERE id = ?");
                ps.setInt(1, id);
                success = ps.executeUpdate() >= 1;
            }
        }
        catch (SQLException e) {
        }
        finally {
            try {
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            }
            catch (SQLException e) {
            }
        }
        return success;
    }

    public boolean activatePost(Post post) {
        Connection con = null;
        PreparedStatement ps = null;
        boolean success = false;
        try {
            con = DBConnect.makeConnection();
            if (con != null) {
                ps = con.prepareStatement("UPDATE Post SET is_active = 1 WHERE id = ?");
                ps.setInt(1, post.getPostId());
                success = ps.executeUpdate() >= 1;
            }
        }
        catch (SQLException e) {
        }
        finally {
            try {
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            }
            catch (SQLException e) {
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
                String sql = "INSERT INTO Post(title, description, attachment_URL, uploader_username, upload_date, view_count, is_active)\n"
                        + "VALUES(?, ?, ?, ?, ?, ?, ?)";
                ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, post.getTitle());
                ps.setString(2, post.getDescription());
                ps.setString(3, post.getAttachmentURL());
                ps.setInt(6, post.getViewCount());
                ps.setDate(5, post.getUploadDate());
                ps.setBoolean(7, post.isIsActive());
                ps.setString(4, post.getUploader().getUsername());
                ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();
                if(rs.next())
                    post.setPostId(rs.getInt(1));
                return true;
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            try {
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            }
            catch (SQLException e) {
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
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            try {
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            }
            catch (SQLException e) {
            }
        }
        return false;
    }

    public ArrayList<Post> getLikedPostsByUser(User user) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Post> lst = new ArrayList<>();
        try {
            con = DBConnect.makeConnection();
            if (con != null) {
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
        }
        catch (SQLException e) {
        }
        finally {
            try {
                if (rs != null)
                    rs.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            }
            catch (SQLException e) {
            }
        }
        return lst;
    }

    //Not tested
    public ArrayList<Post> getTopPostsInCategory(int categoryID) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Post> lst = new ArrayList<>();
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
        }
        catch (SQLException e) {
        }
        finally {
            try {
                if (rs != null)
                    rs.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            }
            catch (SQLException e) {
            }
        }
        return lst;
    }

    //Search for posts using navbar
    public List<Post> getSearchPost(String search) {
        List lst = new ArrayList();
        UserDAO userDAO = new UserDAO();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT *\n"
                + "FROM Post p\n"
                + "WHERE (p.uploader_username LIKE ?\n"
                + "OR p.title LIKE ?)\n"
                + "AND p.is_active=1";
        try {
            con = DBConnect.makeConnection();
            if (con != null) {
                ps = con.prepareStatement(sql);
                search = "%" + search + "%";
                ps.setString(1, search);
                ps.setString(2, search);
                rs = ps.executeQuery();
                while (rs.next()) {
                    Post post = new Post();
                    post.setPostId(rs.getInt("id"));
                    post.setTitle(rs.getString("title"));
                    post.setDescription(rs.getString("description"));
                    post.setViewCount(rs.getInt("view_count"));
                    post.setUploadDate(rs.getDate("upload_date"));
                    User uploader = userDAO.getUserByUsername(rs.getString("uploader_username"));
                    post.setUploader(uploader);
                    lst.add(post);
                }
            }
        }
        catch (SQLException e) {
        }
        finally {
            try {
                if (rs != null)
                    rs.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            }
            catch (SQLException e) {
            }
        }
        return lst;
    }

    //Search for posts by selecting category
    public List<Post> getSearchCatPost(Category cat) {
        List lst = new ArrayList();
        UserDAO userDAO = new UserDAO();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = "SELECT p.*\n"
                + "FROM Post p INNER JOIN Post_Category_Map pc ON (p.id=pc.post_id) \n"
                + "WHERE p.is_active = '1'\n"
                + "AND pc.category_id = ?";
        try {
            con = DBConnect.makeConnection();
            if (con != null) {
                ps = con.prepareStatement(sql);
                ps.setInt(1, cat.getCategoryId());
                rs = ps.executeQuery();
                while (rs.next()) {
                    Post post = new Post();
                    post.setPostId(rs.getInt("id"));
                    post.setTitle(rs.getString("title"));
                    post.setDescription(rs.getString("description"));
                    post.setViewCount(rs.getInt("view_count"));
                    post.setUploadDate(rs.getDate("upload_date"));
                    User uploader = userDAO.getUserByUsername(rs.getString("uploader_username"));
                    post.setUploader(uploader);
                    lst.add(post);
                }
            }
        }
        catch (SQLException e) {
        }
        finally {
            try {
                if (rs != null)
                    rs.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            }
            catch (SQLException e) {
            }
        }
        return lst;
    }

    public int countFreePosts() {
        int count = -1;
        String sql = "SELECT Count(id) as count FROM Post WHERE id NOT IN\n"
                + "(SELECT post_id FROM Tier_Map)\n"
                + "AND is_active = 1";
        try (Connection con = DBConnect.makeConnection()) {
            if (con != null)
                try (PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
                if (rs.next())
                    count = rs.getInt("count");
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(PostDAO.class
                    .getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        }
        return count;
    }

    public ArrayList<Post> getFreePosts(int start, int end) {
        ArrayList<Post> lst = new ArrayList<>();
        UserDAO userDAO = new UserDAO();
        try (Connection con = DBConnect.makeConnection()) {
            if (con != null) {
                String sql = "SELECT * FROM (\n"
                        + "SELECT ROW_NUMBER() OVER (Order BY id DESC) as r, * FROM Post \n"
                        + "WHERE id NOT IN(SELECT post_id FROM Tier_Map)\n"
                        + "AND is_active = 1) as x\n"
                        + "WHERE x.r BETWEEN ? AND ? ";
                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setInt(1, start);
                    ps.setInt(2, end);
                    try (ResultSet rs = ps.executeQuery()) {
                        while (rs.next()) {
                            Post post = new Post();
                            post.setPostId(rs.getInt("id"));
                            post.setTitle(rs.getString("title"));
                            post.setDescription(rs.getString("description"));
                            post.setUploadDate(rs.getDate("upload_date"));
                            post.setViewCount(rs.getInt("view_count"));
                            User uploader = userDAO.getUserByUsername(rs.getString("uploader_username"));
                            post.setUploader(uploader);
                            lst.add(post);
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
        return lst;
    }

//    public ArrayList<Post> getPostsThatUserCanView(User user, int start, int end) {
//        ArrayList<Post> lst = new ArrayList<>();
//        try (Connection con = DBConnect.makeConnection()) {
//            if (con != null) {
//                String sql = "SELECT * FROM (\n"
//                        + "SELECT ROW_number() OVER (ORDER BY id DESC) as r, id, title, uploader_username, description FROM Post \n"
//                        + "WHERE id not in(\n"
//                        + "	SELECT post_id from Tier_Map\n"
//                        + ")AND is_active = 1 OR id in (\n"
//                        + "	SELECT post_id FROM Tier_Map WHERE tier_id in (\n"
//                        + "		SELECT tier_id FROM Subscription WHERE subscriber_username = ? \n"
//                        + "	)\n"
//                        + ") AND is_active = 1) as x\n"
//                        + "WHERE x.r between ? and ?";
//                try (PreparedStatement ps = con.prepareStatement(sql)) {
//                    ps.setString(1, user.getUsername());
//                    ps.setInt(2, start);
//                    ps.setInt(3, end);
//                    try (ResultSet rs = ps.executeQuery()) {
//                        while (rs.next()) {
//                            Post post = new Post();
//                            post.setPostId(rs.getInt("id"));
//                            post.setTitle(rs.getString("title"));
//                            post.setDescription(rs.getString("description"));
//                            lst.add(post);
//                        }
//                    }
//                }
//            }
//        }
//        catch (Exception e) {
//            System.out.println(e);
//        }
//        return lst;
//    }
    public List<Post> getPosts(int start, int end) {
        List<Post> lst = new ArrayList<>();
        UserDAO userDAO = new UserDAO();
        try (Connection con = DBConnect.makeConnection()) {
            if (con != null) {
                String sql = "SELECT * FROM\n"
                        + "(SELECT ROW_NUMBER() OVER (ORDER BY id DESC) AS r, * FROM Post WHERE is_active = 1) as x\n"
                        + "WHERE x.r BETWEEN ? AND ?";
                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setInt(1, start);
                    ps.setInt(2, end);
                    try (ResultSet rs = ps.executeQuery()) {
                        while (rs.next()) {
                            Post post = new Post();
                            post.setPostId(rs.getInt("id"));
                            post.setTitle(rs.getString("title"));
                            post.setDescription(rs.getString("description"));
                            post.setUploadDate(rs.getDate("upload_date"));
                            post.setViewCount(rs.getInt("view_count"));
                            User uploader = userDAO.getUserByUsername(rs.getString("uploader_username"));
                            post.setUploader(uploader);
                            lst.add(post);
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
        return lst;
    }

    public int CountReportedPostsByUser(User user) {
        return CountReportedPostsByUsername(user.getUsername());
    }

    public int CountReportedPostsByUsername(String username) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBConnect.makeConnection();
            if (con != null) {
                String sql
                        = "select count (p.id) as num\n"
                        + "from Post p\n"
                        + "where p.id in \n"
                        + "	(select r.reported_id\n"
                        + "	from Report r\n"
                        + "	where r.type='post'\n"
                        + "		and r.status='approved')\n"
                        + "	and p.uploader_username=?";
                ps = con.prepareStatement(sql);
                ps.setString(1, username);
                rs = ps.executeQuery();
                if (rs.next())
                    return rs.getInt("num");
            }
        }
        catch (SQLException e) {
        }
        finally {
            try {
                if (rs != null)
                    rs.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            }
            catch (SQLException e) {
            }
        }
        return 0;
    }

    public int countPostsThatHasLikes() {
        int count = -1;
        String sql = "SELECT Count(*) as count FROM Post WHERE is_active = 1 AND id IN \n("
                + "SELECT post_id FROM Post_Like)";
        try (Connection con = DBConnect.makeConnection()) {
            if (con != null)
                try (PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
                if (rs.next())
                    count = rs.getInt("count");
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(PostDAO.class
                    .getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        }
        return count;
    }

    public int countPosts() {
        int count = -1;
        String sql = "SELECT Count(*) as count FROM Post WHERE is_active = 1";
        try (Connection con = DBConnect.makeConnection()) {
            if (con != null)
                try (PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
                if (rs.next())
                    count = rs.getInt("count");
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(PostDAO.class
                    .getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex);
        }
        return count;
    }

    public List<Post> getMostLikes(int start, int end) {
        List<Post> lst = new ArrayList<>();
        UserDAO userDAO = new UserDAO();
        try (Connection con = DBConnect.makeConnection()) {
            if (con != null) {
                String sql = "SELECT * FROM \n"
                        + "(SELECT post_id, title, description, upload_date, view_count, uploader_username, row_number() OVER (ORDER BY COUNT(*) DESC) AS r\n"
                        + "FROM Post_Like INNER JOIN Post ON (Post.id = Post_Like.post_id)\n"
                        + "WHERE Post.is_active=1\n"
                        + "GROUP BY post_id, title, description, upload_date, view_count, uploader_username)\n"
                        + "AS list\n"
                        + "WHERE list.r BETWEEN ? AND ?";
                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setInt(1, start);
                    ps.setInt(2, end);
                    try (ResultSet rs = ps.executeQuery()) {
                        while (rs.next()) {
                            Post post = new Post();
                            post.setPostId(rs.getInt("post_id"));
                            post.setTitle(rs.getString("title"));
                            post.setDescription(rs.getString("description"));
                            post.setViewCount(rs.getInt("view_count"));
                            post.setUploadDate(rs.getDate("upload_date"));
                            User uploader = userDAO.getUserByUsername(rs.getString("uploader_username"));
                            post.setUploader(uploader);
                            lst.add(post);
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return lst;
    }

    public List<Post> getLikedPost(User user, int start, int end) {
        List<Post> lst = new ArrayList<>();
        UserDAO userDAO = new UserDAO();
        try (Connection con = DBConnect.makeConnection()) {
            if (con != null) {
                String sql = "SELECT * FROM\n"
                        + "(SELECT *, row_number() OVER (ORDER BY post_id DESC) AS r\n"
                        + "FROM Post_Like INNER JOIN Post ON (Post.id = Post_Like.post_id)\n"
                        + "WHERE Post.is_active=1 and username=?)\n"
                        + "AS list\n"
                        + "WHERE list.r BETWEEN ? AND ?";
                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setString(1, user.getUsername());
                    ps.setInt(2, start);
                    ps.setInt(3, end);
                    try (ResultSet rs = ps.executeQuery()) {
                        while (rs.next()) {
                            Post post = new Post();
                            post.setPostId(rs.getInt("id"));
                            post.setTitle(rs.getString("title"));
                            post.setDescription(rs.getString("description"));
                            post.setViewCount(rs.getInt("view_count"));
                            post.setUploadDate(rs.getDate("upload_date"));
                            User uploader = userDAO.getUserByUsername(rs.getString("uploader_username"));
                            post.setUploader(uploader);
                            lst.add(post);
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
        return lst;
    }

    public int countLikedPosts(User user) {
        int count = -1;
        String sql = "SELECT Count(post_id) as post_count FROM Post_Like WHERE username = ?";
        try (Connection con = DBConnect.makeConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next())
                    count = rs.getInt("post_count");
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return count;
    }

    public List<Post> getBookmarkedPost(User user, int start, int end) {
        List<Post> lst = new ArrayList<>();
        UserDAO userDAO = new UserDAO();
        try (Connection con = DBConnect.makeConnection()) {
            if (con != null) {
                String sql = "SELECT * FROM\n"
                        + "(SELECT *, row_number() OVER (ORDER BY post_id DESC) AS r\n"
                        + "FROM Bookmark INNER JOIN Post ON (Post.id = Bookmark.post_id)\n"
                        + "WHERE Post.is_active=1 and username=?)\n"
                        + "AS list\n"
                        + "WHERE list.r BETWEEN ? AND ?";
                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setString(1, user.getUsername());
                    ps.setInt(2, start);
                    ps.setInt(3, end);
                    try (ResultSet rs = ps.executeQuery()) {
                        while (rs.next()) {
                            Post post = new Post();
                            post.setPostId(rs.getInt("id"));
                            post.setTitle(rs.getString("title"));
                            post.setDescription(rs.getString("description"));
                            post.setViewCount(rs.getInt("view_count"));
                            post.setUploadDate(rs.getDate("upload_date"));
                            User uploader = userDAO.getUserByUsername(rs.getString("uploader_username"));
                            post.setUploader(uploader);
                            lst.add(post);
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
        return lst;
    }

    public int countBookmarkedPosts(User user) {
        int count = -1;
        String sql = "SELECT Count(post_id) as post_count FROM Bookmark WHERE username = ?";
        try (Connection con = DBConnect.makeConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next())
                    count = rs.getInt("post_count");
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return count;
    }

    public List<Post> getMostViews(int start, int end) {
        List<Post> lst = new ArrayList();
        UserDAO userDAO = new UserDAO();
        try (Connection con = DBConnect.makeConnection()) {
            if (con != null) {
                String sql = "SELECT * FROM\n"
                        + "(SELECT row_number() OVER (ORDER BY view_count DESC) AS r, *\n"
                        + "FROM Post\n"
                        + "WHERE Post.is_active=1)\n"
                        + "as list\n"
                        + "WHERE list.r BETWEEN ? AND ?";
                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setInt(1, start);
                    ps.setInt(2, end);
                    try (ResultSet rs = ps.executeQuery()) {
                        while (rs.next()) {
                            Post post = new Post();
                            post.setPostId(rs.getInt("id"));
                            post.setTitle(rs.getString("title"));
                            post.setDescription(rs.getString("description"));
                            post.setViewCount(rs.getInt("view_count"));
                            post.setUploadDate(rs.getDate("upload_date"));
                            User uploader = userDAO.getUserByUsername(rs.getString("uploader_username"));
                            post.setUploader(uploader);
                            lst.add(post);
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
        return lst;
    }

    //increase view count
    public void increaseView(Post post) {
        try (Connection con = DBConnect.makeConnection()) {
            if (con != null) {
                String sql = "UPDATE Post\n"
                        + "SET view_count=?\n"
                        + "WHERE Post.id=?";
                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setInt(1, post.getViewCount()); //view count has been +1 in servlet
                    ps.setInt(2, post.getPostId());
                    ps.executeUpdate();
                }
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

}
