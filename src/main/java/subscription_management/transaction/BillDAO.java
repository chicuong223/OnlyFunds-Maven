/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package subscription_management.transaction;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import subscription_management.tier.Tier;
import user_management.user.User;
import user_management.user.UserDAO;
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
                ps.setString(1, sender.getUsername());
                ps.setString(2, tier.getCreator().getUsername());
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
    
    public ArrayList<Bill> getSendTransactions(User user){
        ArrayList<Bill> lst = new ArrayList<>();
        try {
            Connection con = DBConnect.makeConnection();
            if(con != null){
                UserDAO userDAO = new UserDAO();
                PreparedStatement ps = con.prepareStatement("SELECT id, sender_username, recipient_username, content, price, transaction_date FROM [Transaction]\n"
                        + "WHERE sender_username = ? ORDER BY id");
                ps.setString(1, user.getUsername());
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    int id = rs.getInt("id");
                    User sender = userDAO.getUserByUsername(rs.getString("sender_username"));
                    User recipient = userDAO.getUserByUsername(rs.getString("recipient_username"));
                    String content = rs.getString("content");
                    int price = rs.getInt("price");
                    Date transactionDate = rs.getDate("transaction_date");
                    Bill bill = new Bill(id, sender, recipient, content, price, transactionDate);
                    lst.add(bill);
                }
                rs.close();
                ps.close();
                con.close();
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return lst;
    }
    
    public ArrayList<Bill> getReceiveTransactions(User user){
        ArrayList<Bill> lst = new ArrayList<>();
        try {
            Connection con = DBConnect.makeConnection();
            if(con != null){
                UserDAO userDAO = new UserDAO();
                PreparedStatement ps = con.prepareStatement("SELECT id, sender_username, recipient_username, content, price, transaction_date FROM [Transaction]\n"
                        + "WHERE recipient_username = ? ORDER BY id");
                ps.setString(1, user.getUsername());
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    int id = rs.getInt("id");
                    User sender = userDAO.getUserByUsername(rs.getString("sender_username"));
                    User recipient = userDAO.getUserByUsername(rs.getString("recipient_username"));
                    String content = rs.getString("content");
                    int price = rs.getInt("price");
                    Date transactionDate = rs.getDate("transaction_date");
                    Bill bill = new Bill(id, sender, recipient, content, price, transactionDate);
                    lst.add(bill);
                }
                rs.close();
                ps.close();
                con.close();
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return lst;
    }
    
    public ArrayList<Bill> getTransactionsByUser(User user){
        ArrayList<Bill> lst = new ArrayList<>();
        try {
            Connection con = DBConnect.makeConnection();
            if(con != null){
                UserDAO userDAO = new UserDAO();
                PreparedStatement ps = con.prepareStatement("SELECT id, sender_username, recipient_username, content, price, transaction_date FROM [Transaction]\n"
                        + "WHERE sender_username = ? OR recipient_username = ? ORDER BY id");
                ps.setString(1, user.getUsername());
                ps.setString(2, user.getUsername());
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    int id = rs.getInt("id");
                    User sender = userDAO.getUserByUsername(rs.getString("sender_username"));
                    User recipient = userDAO.getUserByUsername(rs.getString("recipient_username"));
                    String content = rs.getString("content");
                    int price = rs.getInt("price");
                    Date transactionDate = rs.getDate("transaction_date");
                    Bill bill = new Bill(id, sender, recipient, content, price, transactionDate);
                    lst.add(bill);
                }
                rs.close();
                ps.close();
                con.close();
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return lst;
    }
}
