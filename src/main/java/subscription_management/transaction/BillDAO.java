/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package subscription_management.transaction;

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
public class BillDAO {
    public boolean addBill(User sender, Tier tier){
        boolean result = false;
        try {
            Connection con = DBConnect.makeConnection();
            if(con != null){
                PreparedStatement ps = con.prepareStatement("INSERT INTO [Transaction] (sender_username, recipient_username, content, price, transaction_date)\n"
                        + "VALUES(?, ?, ?, ?, ?)");
                ps.setString(1, tier.getCreator().getUsername());
                ps.setString(2, sender.getUsername());
                ps.setString(3, tier.getTierTitle());
                ps.setInt(4, tier.getPrice());
                long millis = System.currentTimeMillis();
                Date date = new Date(millis);
                ps.setDate(5, date);
                result = ps.executeUpdate() > 0;
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
}
