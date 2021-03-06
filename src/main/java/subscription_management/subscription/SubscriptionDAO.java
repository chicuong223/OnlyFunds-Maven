/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package subscription_management.subscription;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import subscription_management.tier.Tier;
import subscription_management.tier.TierDAO;
import user_management.user.User;
import user_management.user.UserDAO;
import utils.DBConnect;

/**
 *
 * @author chiuy
 */
public class SubscriptionDAO {

    public boolean addSubscription(User user, Tier tier) {
        boolean result = false;
        try {
            Connection con = DBConnect.makeConnection();
            if (con != null) {
                PreparedStatement ps = con.prepareStatement("INSERT INTO Subscription(subscriber_username, tier_id, start_date, end_date, is_active)\n"
                        + "VALUES(?, ?, ?, DATEADD(day, 30, ?), ?)");
                ps.setString(1, user.getUsername());
                ps.setInt(2, tier.getTierId());
                long millis = System.currentTimeMillis();
                Date date = new Date(millis);
                ps.setDate(3, date);
                ps.setDate(4, date);
                ps.setBoolean(5, true);
                result = ps.executeUpdate() > 0;
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    public List<Subscription> getSubscriptionsByUser(User user, int start, int end) {
        List<Subscription> lst = new ArrayList<>();
        String sql = "SELECT * FROM (\n"
                + "SELECT ROW_NUMBER() OVER (ORDER BY is_active DESC) as r, * FROM Subscription\n"
                + "WHERE subscriber_username = ?) as s\n"
                + "WHERE s.r BETWEEN ? AND ?";
        try (Connection con = DBConnect.makeConnection()) {
            if (con != null) {
                TierDAO tierDAO = new TierDAO();
                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setString(1, user.getUsername());
                    ps.setInt(2, start);
                    ps.setInt(3, end);
                    try (ResultSet rs = ps.executeQuery()) {
                        while (rs.next()) {
                            int id = rs.getInt("id");
                            Tier tier = tierDAO.getTierById(rs.getInt("tier_id"));
                            Date startDate = rs.getDate("start_date");
                            Date endDate = rs.getDate("end_date");
                            boolean isActive = rs.getBoolean("is_active");
                            Subscription sub = new Subscription(id, user, tier, startDate, endDate, isActive);
                            lst.add(sub);
                        }
                    }
                }
            }
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return lst;
    }
    
    public int countSubscriptions(User user){
        int count = -1;
        String sql = "SELECT COUNT(*) AS sub_count FROM Subscription WHERE subscriber_username = ?";
        try(Connection con = DBConnect.makeConnection(); PreparedStatement ps = con.prepareStatement(sql)){
            ps.setString(1, user.getUsername());
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next())
                    count = rs.getInt("sub_count");
            }
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        return count;
    }
    
    public Subscription getSubscriptionById(int id){
        Subscription sub = null;
        String sql = "SELECT id FROM Subscription WHERE id = ?";
        try(Connection con = DBConnect.makeConnection(); PreparedStatement ps = con.prepareStatement(sql)){
            ps.setInt(1, id);
            try(ResultSet rs= ps.executeQuery()){
                if(rs.next()){
                    sub = new Subscription();
                    sub.setSubscriptionId(rs.getInt("id"));
                }
            }
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        return sub;
    }
    
    public boolean cancelSub(Subscription sub){
        boolean result = false;
        String sql = "UPDATE Subscription SET is_active = 0, end_date = ? WHERE id = ?";
        try (Connection con = DBConnect.makeConnection(); PreparedStatement ps = con.prepareStatement(sql)){
            ps.setInt(2, sub.getSubscriptionId());
            Long millis = System.currentTimeMillis();
            Date endDate = new Date(millis);
            ps.setDate(1, endDate);
            result = ps.executeUpdate() > 0;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
}
