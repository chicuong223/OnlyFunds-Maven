/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package subscription_management.subscription;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import subscription_management.tier.Tier;
import user_management.user.User;
import utils.DBConnect;

/**
 *
 * @author chiuy
 */
public class SubscriptionDAO {
    public boolean addSubscription(User user, Tier tier){
        boolean result =false;
        try {
            Connection con = DBConnect.makeConnection();
            if(con != null){
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
}
