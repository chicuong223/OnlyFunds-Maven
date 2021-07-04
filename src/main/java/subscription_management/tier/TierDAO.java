/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package subscription_management.tier;

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
public class TierDAO {

    public ArrayList<Tier> getTiersByUser(User user) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Tier> lst = null;
        try {
            con = DBConnect.makeConnection();
            if (con != null) {
                lst = new ArrayList<>();
                ps = con.prepareStatement("SELECT * FROM Tier WHERE username = ? AND is_active = 1");
                ps.setString(1, user.getUsername());
                rs = ps.executeQuery();
                while (rs.next()) {
                    int tierID = rs.getInt("id");
                    String tierTitle = rs.getString("title");
                    String desc = rs.getString("description");
                    int price = rs.getInt("price");
                    Tier tier = new Tier(tierID, tierTitle, desc, user, price, true);
                    lst.add(tier);
                }
            }
        }
        catch (SQLException e) {
        }
        finally {
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
            }
            catch (SQLException e) {
            }
        }
        return lst;
    }

    public Tier getTierById(int tierID) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Tier tier = null;
        try {
            con = DBConnect.makeConnection();
            if (con != null) {
                ps = con.prepareStatement("SELECT * FROM Tier WHERE id = ?");
                ps.setInt(1, tierID);
                rs = ps.executeQuery();
                if (rs.next()) {
                    String tierTitle = rs.getString("title");
                    String desc = rs.getString("description");
                    User creator = new UserDAO().getUserByUsername(rs.getString("username"));
                    boolean isActive = rs.getBoolean("is_active");
                    int price = rs.getInt("price");
                    tier = new Tier(tierID, tierTitle, desc, creator, price, isActive);
                }
            }
        }
        catch (SQLException e) {
        }
        finally {
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
            }
            catch (SQLException e) {
            }
        }
        return tier;
    }

    public boolean deactivateTier(Tier tier) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBConnect.makeConnection();
            if (con != null) {
                ps = con.prepareStatement("UPDATE Tier SET is_active = 0 WHERE id = ?");
                ps.setInt(1, tier.getTierId());
                ps.executeUpdate();
                return true;
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            }
            catch (SQLException e) {
            }
        }
        return false;
    }

    public boolean addTier(Tier tier) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBConnect.makeConnection();
            if (con != null) {
                ps = con.prepareStatement("INSERT INTO Tier(username, title, description, price, is_active)\n"
                        + "VALUES(?, ?, ?, ?, ?)");
                ps.setString(1, tier.getCreator().getUsername());
                ps.setString(2, tier.getTierTitle());
                ps.setString(3, tier.getDescription());
                ps.setInt(4, tier.getPrice());
                ps.setBoolean(5, true);
                ps.executeUpdate();
                return true;
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            }
            catch (SQLException e) {
            }
        }
        return false;
    }

    public boolean updateTier(Tier updatedTier) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBConnect.makeConnection();
            if (con != null) {
                ps = con.prepareStatement("UPDATE Tier SET title=?, description=?, price=? WHERE id = ?\n");
                ps.setString(1, updatedTier.getTierTitle());
                ps.setString(2, updatedTier.getDescription());
                ps.setInt(3, updatedTier.getPrice());
                ps.setInt(4, updatedTier.getTierId());
                ps.executeUpdate();
                return true;
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            }
            catch (SQLException e) {
            }
        }
        return false;
    }

    public int getTierIDByUserAndTitle(User user, String title) {
        int id = -1;
        try {
            Connection con = DBConnect.makeConnection();
            if (con != null) {
                try (PreparedStatement ps = con.prepareStatement("SELECT id FROM Tier WHERE username = ? AND title = ?")) {
                    ps.setString(1, user.getUsername());
                    ps.setString((2), title);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            id = rs.getInt("id");
                        }
                    }
                }
                con.close();
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return id;
    }

    public ArrayList<Tier> getTiersByPost(Post post) {
        ArrayList<Tier> lst = new ArrayList<>();
        try {
            Connection con = DBConnect.makeConnection();
            if (con != null) {
                try (PreparedStatement ps = con.prepareStatement("SELECT * FROM Tier WHERE id in (\n"
                        + "SELECT tier_id FROM Tier_Map WHERE post_id = ?)")) {
                    ps.setInt(1, post.getPostId());
                    try (ResultSet rs = ps.executeQuery()) {
                        while (rs.next()) {
                            int tierID = rs.getInt("id");
                            String tierTitle = rs.getString("title");
                            String desc = rs.getString("description");
                            User creator = new UserDAO().getUserByUsername(rs.getString("username"));
                            boolean isActive = rs.getBoolean("is_active");
                            int price = rs.getInt("price");
                            Tier tier = new Tier(tierID, tierTitle, desc, creator, price, isActive);
                            lst.add(tier);
                        }
                    }
                }
                con.close();
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return lst;
    }

    public ArrayList<Tier> getTiersBySubscription(User subscriber) {
        ArrayList<Tier> lst = new ArrayList<>();
        try {
            Connection con = DBConnect.makeConnection();
            if (con != null) {
                PreparedStatement ps = con.prepareStatement("SELECT * FROM Tier WHERE id in\n"
                        + "(\n"
                        + "	SELECT tier_id \n"
                        + "	FROM subscription \n"
                        + "	WHERE subscriber_username = ?\n"
                        + ")");
                ps.setString(1, subscriber.getUsername());
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        int tierID = rs.getInt("id");
                        String tierTitle = rs.getString("title");
                        String desc = rs.getString("description");
                        User creator = new UserDAO().getUserByUsername(rs.getString("username"));
                        boolean isActive = rs.getBoolean("is_active");
                        int price = rs.getInt("price");
                        Tier tier = new Tier(tierID, tierTitle, desc, creator, price, isActive);
                        lst.add(tier);
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
}
