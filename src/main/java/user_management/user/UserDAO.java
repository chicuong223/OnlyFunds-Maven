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
        } catch (Exception e) {
        }
        System.out.println(user);
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
                if (rs.next()) {
                    return true;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
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
            } catch (Exception ex) {
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
                if (rs.next()) {
                    return true;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
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
            } catch (Exception ex) {
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
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (Exception ex) {
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
        } catch (SQLException ex) {
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
            } catch (SQLException ex) {
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

    public ArrayList<User> getUsersMostSubscriber() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<User> lst = new ArrayList<>();
        String sql = "SELECT DISTINCT TOP 10 [User].*, tierNo.sumNo\n"
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
    public ArrayList<User> getSearchUser(String search) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<User> lst = new ArrayList<>();
        String sql
                = "select u.*\n"
                + "from [User] u\n"
                + "where u.is_banned='0'\n"
                + "     and(u.username Like ?\n"
                + "	or	u.firstname Like ?\n"
                + "	or u.lastname Like ?)";
        try {
            con = DBConnect.makeConnection();
            if (con != null) {
                ps = con.prepareStatement(sql);
                search = "%" + search + "%";
                ps.setString(1, search);
                ps.setString(2, search);
                ps.setString(3, search);
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
        return false;
    } 
    
    public static void main(String[] args) {
        UserDAO dao = new UserDAO();
//        User user = dao.getUserByUsername("");
        ArrayList<User> lst = dao.getUsersMostSubscriber();
        lst.forEach(u -> System.out.println(u));
    }
}
