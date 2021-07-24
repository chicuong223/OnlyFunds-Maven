/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user_management.user;

import category.Category;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.DBConnect;
import static utils.HashPassword.HashPassword;

/**
 *
 * @author chiuy
 */
public class UserDAO {

    public User checkLogin(String username, String password) {
        User user = null;
        try {
            Connection con = DBConnect.makeConnection();
            if (con != null) {
                PreparedStatement ps = con.prepareStatement("SELECT * FROM [User] WHERE username=? AND password=?");
                ps.setString(1, username);
                ps.setString(2, password);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    String firstName = rs.getString("firstname");
                    String lastName = rs.getString("lastname");
                    String email = rs.getString("email");
                    String bio = rs.getString("bio");
                    String avatarURL = rs.getString("avatarURL");
                    boolean isBanned = rs.getBoolean("is_banned");
                    user = new User(username, password, lastName, firstName, email, bio, avatarURL, isBanned);
                }
                rs.close();
                ps.close();
                con.close();
            }
        }
        catch (Exception e) {
        }
//        System.out.println(user);
        return user;
    }

    // check if username is available
    public boolean usernameCheck(String username) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnect.makeConnection();
            if (con != null) {
                String sql = "SELECT * FROM [User] WHERE username=?";
                ps = con.prepareStatement(sql);
                ps.setString(1, username);
                rs = ps.executeQuery();
                //  if username exists
                if (rs.next())
                    return true;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
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
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        // if username does not exist -> false
        return false;
    }

    // check if username is available
    public boolean emailCheck(String email) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = DBConnect.makeConnection();
            if (con != null) {
                String sql = "SELECT * FROM [User] WHERE email=?";
                ps = con.prepareStatement(sql);
                ps.setString(1, email);
                rs = ps.executeQuery();
                //  if email exists
                if (rs.next())
                    return true;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
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
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        // if email does not exist -> false
        return false;
    }

    // add new user to database 
    public boolean addUser(User newUser) {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = DBConnect.makeConnection();
            if (con != null) {
                String sql = "INSERT INTO [User](username, password, firstname, lastname, "
                        + "email, avatarURL, is_banned)"
                        + "VALUES (?, ?, ?, ?, ?, ?, ?) ";
                ps = con.prepareStatement(sql);
                ps.setString(1, newUser.getUsername());
                ps.setString(2, newUser.getPassword());
                ps.setString(3, newUser.getFirstName());
                ps.setString(4, newUser.getLastName());
                ps.setString(5, newUser.getEmail());
                ps.setString(6, newUser.getAvatarURL());
                ps.setBoolean(7, false);

                ps.executeUpdate();
                return true;
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }
        finally {
            try {
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        // if email does not exist -> false
        return false;
    }

    //get user by username
    public User getUserByUsername(String username) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user = null;
        try {
            con = DBConnect.makeConnection();
            if (con != null) {
                String sql = "SELECT * FROM [User] WHERE username=?";
                ps = con.prepareStatement(sql);
                ps.setString(1, username);
                rs = ps.executeQuery();
                //  if user exists
                if (rs.next()) {
                    String password = rs.getString("password");
                    String firstName = rs.getString("firstname");
                    String lastName = rs.getString("lastname");
                    String email = rs.getString("email");
                    String bio = rs.getString("bio");
                    String avatarURL = rs.getString("avatarURL");
                    boolean isBanned = rs.getBoolean("is_banned");
                    user = new User(username, password, lastName, firstName, email, bio, avatarURL, isBanned);
                }
            }
        }
        catch (SQLException ex) {
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
            catch (SQLException ex) {
            }
        }
        return user;
    }

    public boolean changePassword(String email, String password) {
        Connection con = null;
        PreparedStatement ps = null;
        String sql = "UPDATE [User] SET password = ? WHERE email = ?";
        boolean success = false;
        try {
            con = DBConnect.makeConnection();
            if (con != null) {
                ps = con.prepareStatement(sql);
                ps.setString(1, password);
                ps.setString(2, email);
                ps.executeUpdate();
                success = true;
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

    public ArrayList<User> getUsersMostSubscriber() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<User> lst = new ArrayList<>();
        String sql = "SELECT DISTINCT TOP 4 [User].*, tierNo.sumNo\n"
                + "FROM\n"
                + "(\n"
                + "	SELECT Tier.username as username, SUM(countNo) as sumNo \n"
                + "	FROM (\n"
                + "		SELECT tier_id, Count(tier_id) as countNo From Subscription\n"
                + "		GROUP BY tier_id\n"
                + "	) as CountNo, Tier\n"
                + "	WHERE Tier.id = CountNo.tier_id\n"
                + "	GROUP BY Tier.username) as tierNo, [User]\n"
                + "WHERE [User].username = tierNo.username\n"
                + "ORDER BY tierNo.sumNo DESC";
        try {
            con = DBConnect.makeConnection();
            if (con != null) {
                ps = con.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next()) {
                    String username = rs.getString("username");
                    String password = rs.getString("password");
                    String firstName = rs.getString("firstname");
                    String lastName = rs.getString("lastname");
                    String email = rs.getString("email");
                    String bio = rs.getString("bio");
                    String avatarURL = rs.getString("avatarURL");
                    boolean isBanned = rs.getBoolean("is_banned");
                    User user = new User(username, password, lastName, firstName, email, bio, avatarURL, isBanned);
                    lst.add(user);
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

    public ArrayList<User> getSubscribers(User user) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<User> lst = new ArrayList<>();
        String sql = "SELECT DISTINCT * FROM [User] WHERE username in ("
                + "SELECT subscriber_username FROM Subscription WHERE tier_id in ("
                + "SELECT id FROM Tier WHERE username = ? ))";
        try {
            con = DBConnect.makeConnection();
            if (con != null) {
                ps = con.prepareStatement(sql);
                ps.setString(1, user.getUsername());
                rs = ps.executeQuery();
                while (rs.next()) {
                    String username = rs.getString("username");
                    String password = rs.getString("password");
                    String firstName = rs.getString("firstname");
                    String lastName = rs.getString("lastname");
                    String email = rs.getString("email");
                    String bio = rs.getString("bio");
                    String avatarURL = rs.getString("avatarURL");
                    boolean isBanned = rs.getBoolean("is_banned");
                    User subscriber = new User(username, password, lastName, firstName, email, bio, avatarURL, isBanned);
                    lst.add(subscriber);
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

    //not tested
    public ArrayList<User> getBestCreatorsInCategory(Category category) {
        return getBestCreatorsInCategory(category.getCategoryId());
    }

    //not tested
    public ArrayList<User> getBestCreatorsInCategory(int cateId) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<User> lst = new ArrayList<>();
        String sql
                = "select top 10 uwc.* , count(distinct s.subscriber_username) as NumOfSubcriber\n"
                + "from ((select u.*\n"
                + "	from User_Category_Map ucm, [User] u\n"
                + "	where ucm.category_id=?\n"
                + "		and ucm.username=u.username\n"
                + "		and u.is_banned='0') uwc \n"
                + "			left join Tier t on uwc.username=t.username) \n"
                + "				left join Subscription s on t.id=s.tier_id and t.is_active='1'\n"
                + "group by uwc.username , uwc.avatarURL, uwc.email, uwc.firstname,\n"
                + "	uwc.is_banned, uwc.lastname, uwc.password, uwc.username\n"
                + "order by NumOfSubcriber";
        try {
            con = DBConnect.makeConnection();
            if (con != null) {
                ps = con.prepareStatement(sql);
                ps.setInt(1, cateId);
                rs = ps.executeQuery();
                while (rs.next()) {
                    String username = rs.getString("username");
                    String password = rs.getString("password");
                    String firstName = rs.getString("firstname");
                    String lastName = rs.getString("lastname");
                    String email = rs.getString("email");
                    String bio = rs.getString("bio");
                    String avatarURL = rs.getString("avatarURL");
                    boolean isBanned = rs.getBoolean("is_banned");
                    User subscriber = new User(username, password, lastName, firstName, email, bio, avatarURL, isBanned);
                    lst.add(subscriber);
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

    //Search for user using navbar
    public List<User> getSearchUser(String search) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<User> lst = new ArrayList<>();
        String sql = "SELECT *\n"
                + "FROM [User] u\n"
                + "WHERE u.is_banned='0'\n"
                + "AND (u.username LIKE ?)";
        try {
            con = DBConnect.makeConnection();
            if (con != null) {
                ps = con.prepareStatement(sql);
                search = "%" + search + "%";
                ps.setString(1, search);
                rs = ps.executeQuery();
                while (rs.next()) {
                    User user = new User();
                    user.setUsername(rs.getString("username"));
                    user.setBio(rs.getString("bio"));
                    user.setAvatarURL(rs.getString("avatarURL"));
                    user.setIsBanned(false);
                    lst.add(user);
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

    //Search for user by selecting category 
    public List<User> getSearchCatUser(Category cat) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<User> lst = new ArrayList<>();
        String sql = "SELECT u.*\n"
                + "FROM [User] u INNER JOIN User_Category_Map uc ON (u.username=uc.username) \n"
                + "WHERE u.is_banned=0 \n"
                + "AND uc.category_id = ?";
        try {
            con = DBConnect.makeConnection();
            if (con != null) {
                ps = con.prepareStatement(sql);
                ps.setInt(1, cat.getCategoryId());
                rs = ps.executeQuery();
                while (rs.next()) {
                    User user = new User();
                    user.setUsername(rs.getString("username"));
                    user.setBio(rs.getString("bio"));
                    user.setAvatarURL(rs.getString("avatarURL"));
                    user.setIsBanned(false);
                    lst.add(user);
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

    //update bio
    public boolean changeBio(String username, String bio) {
        Connection con = null;
        PreparedStatement ps = null;
        String sql = "UPDATE [User] SET bio = ? WHERE username = ?";

        try {
            con = DBConnect.makeConnection();
            if (con != null) {
                ps = con.prepareStatement(sql);
                ps.setString(1, bio);
                ps.setString(2, username);
                ps.executeUpdate();
                return true;
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
        return false;
    }

    public ArrayList<User> getCreatorThatUserFollows(User user) {
        ArrayList<User> lst = new ArrayList<>();
        Connection con = null;
        try {
            con = DBConnect.makeConnection();
            if (con != null) {
                try (PreparedStatement ps = con.prepareStatement("SELECT * FROM [User] WHERE username in (\n"
                        + "SELECT followed_username FROM Follow WHERE follower_username = ?)")) {
                    ps.setString(1, user.getUsername());
                    try (ResultSet rs = ps.executeQuery()) {
                        while (rs.next()) {
                            User creator = new User();
                            creator.setUsername(rs.getString("username"));
                            creator.setBio(rs.getString("bio"));
                            creator.setAvatarURL(rs.getString("avatarURL"));
                            lst.add(creator);
                        }
                    }
                }
                con.close();
            }
        }
        catch (SQLException e) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, "Error getCreatorsThatUserFollows", e);
        }
        finally {
            if (con != null)
                try {
                con.close();
            }
            catch (SQLException ex) {
                Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Collections.shuffle(lst);
        return lst;
    }

    public ArrayList<User> getCreatorThatUserSubscribedTo(User user) {
        ArrayList<User> lst = new ArrayList<>();
        Connection con = null;
        try {
            con = DBConnect.makeConnection();
            if (con != null) {
                try (PreparedStatement ps = con.prepareStatement("select username, bio, avatarURL from [User] where username in (\n"
                        + "select username from Tier where id in (\n"
                        + "select tier_id from Subscription\n"
                        + "where subscriber_username = ?\n"
                        + "))")) {
                    ps.setString(1, user.getUsername());
                    try (ResultSet rs = ps.executeQuery()) {
                        while (rs.next()) {
                            User creator = new User();
                            creator.setUsername(rs.getString("username"));
                            creator.setBio(rs.getString("bio"));
                            creator.setAvatarURL(rs.getString("avatarURL"));
                            lst.add(creator);
                        }
                    }
                }
                con.close();
            }
        }
        catch (SQLException e) {
        }
        finally {
            if (con != null)
                try {
                con.close();
            }
            catch (SQLException ex) {
                Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Collections.shuffle(lst);
        return lst;
    }

    public boolean updateUser(User user) {
        boolean result = false;
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBConnect.makeConnection();
            if (con != null) {
                ps = con.prepareStatement("UPDATE [User] SET avatar_URL = ? WHERE username = ?");
                ps.setString(1, user.getAvatarURL());
                ps.setString(2, user.getUsername());
                result = ps.executeUpdate() > 0;
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
        return result;
    }

    public ArrayList<User> getCreatorsSameCategoryAsUser(User user) {
        ArrayList<User> lst = new ArrayList<>();
        String sql = "SELECT DISTINCT TOP 10  username, avatarURL, bio FROM [User]  WHERE username in (\n"
                + "SELECT username FROM User_Category_Map where NOT username = ? and category_id in (\n"
                + "SELECT category_id FROM User_Category_Map WHERE username = ? \n"
                + ")) AND is_banned = 0";
        try (Connection con = DBConnect.makeConnection()) {
            if (con != null)
                try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, user.getUsername());
                ps.setString(2, user.getUsername());
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        User creator = new User();
                        creator.setUsername(rs.getString("username"));
                        creator.setBio(rs.getString("bio"));
                        creator.setAvatarURL(rs.getString("avatarURL"));
                        lst.add(creator);
                    }
                }
            }
        }
        catch (Exception ex) {
            System.out.println(ex.getStackTrace());
        }
        Collections.shuffle(lst);
        return lst;
    }

    public int CountReportedUserByUser(User user) {
        return CountReportedUserByUsername(user.getUsername());
    }

    public int CountReportedUserByUsername(String username) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBConnect.makeConnection();
            if (con != null) {
                String sql
                        = "select count (u.username) as num\n"
                        + "from [User] u\n"
                        + "where u.username in \n"
                        + "	(select r.reported_id\n"
                        + "	from Report r\n"
                        + "	where r.type='user'\n"
                        + "		and r.status='approved')\n"
                        + "	and u.username=?";
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

    public boolean banUser(User user) {
        boolean result = false;
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBConnect.makeConnection();
            if (con != null) {
                ps = con.prepareStatement("UPDATE [User] SET is_banned=1  WHERE username = ?");
                ps.setString(1, user.getUsername());
                result = ps.executeUpdate() > 0;
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
        return result;
    }

    // count subs
    public int countSubscribers(User user) {
        int sub_count = 0;
        String sql = "SELECT COUNT(b.id) AS sub_count FROM\n"
                + "(SELECT s.id FROM Subscription s INNER JOIN \n"
                + "(SELECT id FROM Tier where username=? AND is_active=1) p\n"
                + " ON (s.tier_id=p.id)) b";
        try (Connection con = DBConnect.makeConnection()) {
            if (con != null)
                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setString(1, user.getUsername());
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next())
                            sub_count = rs.getInt("sub_count");
                    }
                }
        }
        catch (Exception ex) {
            System.out.println(ex.getStackTrace());
        }
        return sub_count;
    }

    // count followers
    public int countFollowers(User user) {
        int follow_count = 0;
        String sql = "SELECT COUNT(*) AS follow_count \n"
                + "FROM (SELECT f.follower_username \n"
                + "FROM Follow f \n"
                + "WHERE f.followed_username=? \n"
                + "AND f.followed_username IN \n"
                + "	(SELECT username \n"
                + "	FROM [User] u \n"
                + "	WHERE u.is_banned=0)) ftb";
        try (Connection con = DBConnect.makeConnection()) {
            if (con != null)
                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setString(1, user.getUsername());
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next())
                            follow_count = rs.getInt("follow_count");
                    }
                }
        }
        catch (Exception ex) {
            System.out.println(ex.getStackTrace());
        }
        return follow_count;
    }

    // count following
    public int countFollowing(User user) {
        int follow_count = 0;
        String sql = "SELECT COUNT(*) AS follow_count \n"
                + "FROM (SELECT f.followed_username \n"
                + "FROM Follow f \n"
                + "WHERE f.follower_username=? \n"
                + "AND f.follower_username IN \n"
                + "	(SELECT username \n"
                + "	FROM [User] u \n"
                + "	WHERE u.is_banned=0)) ftb";
        try (Connection con = DBConnect.makeConnection()) {
            if (con != null)
                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setString(1, user.getUsername());
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next())
                            follow_count = rs.getInt("follow_count");
                    }
                }
        }
        catch (Exception ex) {
            System.out.println(ex.getStackTrace());
        }
        return follow_count;
    }

    // list of following users
    public ArrayList<User> getFollowingUsers(User user, int start, int end) {
        ArrayList<User> lst = new ArrayList<>();
        String sql = "SELECT * FROM\n"
                + "(SELECT ROW_NUMBER() OVER (ORDER BY username) AS r, username, avatarURL FROM [User] u WHERE u.is_banned=0\n"
                + "AND u.username IN (SELECT f.followed_username \n"
                + "FROM Follow f \n"
                + "WHERE f.follower_username=?))\n"
                + "AS List\n"
                + "WHERE list.r BETWEEN ? and ?";
        try (Connection con = DBConnect.makeConnection()) {
            if (con != null)
                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setString(1, user.getUsername());
                    ps.setInt(2, start);
                    ps.setInt(3, end);
                    try (ResultSet rs = ps.executeQuery()) {
                        while (rs.next()) {
                            User followingUser = new User();
                            followingUser.setUsername(rs.getString("username"));
                            followingUser.setAvatarURL(rs.getString("avatarURL"));
                            lst.add(followingUser);
                        }
                    }
                }
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return lst;
    }

    public List<User> getCreators(int start, int end) {
        List<User> lst = new ArrayList<>();
        String sql = "SELECT * FROM (\n"
                + "SELECT ROW_NUMBER() OVER (ORDER BY username) AS r, username, avatarURL FROM [User] WHERE is_banned = 0) as x\n"
                + "WHERE x.r BETWEEN ? AND ?";
        try (Connection con = DBConnect.makeConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, start);
            ps.setInt(2, end);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    User creator = new User();
                    creator.setAvatarURL(rs.getString("avatarURL"));
                    creator.setUsername(rs.getString("username"));
                    lst.add(creator);
                }
            }
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return lst;
    }

    public int countCreators() {
        int count = -1;
        String sql = "SELECT COUNT(username) AS user_count FROM [User] WHERE is_banned = 0";
        try (Connection con = DBConnect.makeConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next())
                    count = rs.getInt("user_count");
            }
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return count;
    }
     public ArrayList<User> getAllUser() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<User> lst = new ArrayList<>();
        String sql = "SELECT DISTINCT * FROM [User]";
        try {
            con = DBConnect.makeConnection();
            if (con != null) {
                ps = con.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next()) {
                    String username = rs.getString("username");
                    String password = rs.getString("password");
                    String firstName = rs.getString("firstname");
                    String lastName = rs.getString("lastname");
                    String email = rs.getString("email");
                    String bio = rs.getString("bio");
                    String avatarURL = rs.getString("avatarURL");
                    boolean isBanned = rs.getBoolean("is_banned");
                    User subscriber = new User(username, password, lastName, firstName, email, bio, avatarURL, isBanned);
                    lst.add(subscriber);
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
     public ArrayList<User> getAllUnbannedUser() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<User> lst = new ArrayList<>();
        String sql = "SELECT DISTINCT * FROM [User] where is_banned=0";
        try {
            con = DBConnect.makeConnection();
            if (con != null) {
                ps = con.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next()) {
                    String username = rs.getString("username");
                    String password = rs.getString("password");
                    String firstName = rs.getString("firstname");
                    String lastName = rs.getString("lastname");
                    String email = rs.getString("email");
                    String bio = rs.getString("bio");
                    String avatarURL = rs.getString("avatarURL");
                    boolean isBanned = rs.getBoolean("is_banned");
                    User subscriber = new User(username, password, lastName, firstName, email, bio, avatarURL, isBanned);
                    lst.add(subscriber);
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
     public ArrayList<User> getAllBannedUser() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<User> lst = new ArrayList<>();
        String sql = "SELECT DISTINCT * FROM [User] where is_banned=1";
        try {
            con = DBConnect.makeConnection();
            if (con != null) {
                ps = con.prepareStatement(sql);
                rs = ps.executeQuery();
                while (rs.next()) {
                    String username = rs.getString("username");
                    String password = rs.getString("password");
                    String firstName = rs.getString("firstname");
                    String lastName = rs.getString("lastname");
                    String email = rs.getString("email");
                    String bio = rs.getString("bio");
                    String avatarURL = rs.getString("avatarURL");
                    boolean isBanned = rs.getBoolean("is_banned");
                    User subscriber = new User(username, password, lastName, firstName, email, bio, avatarURL, isBanned);
                    lst.add(subscriber);
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
}
