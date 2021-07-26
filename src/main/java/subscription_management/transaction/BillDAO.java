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

    public boolean addBill(User sender, Tier tier) {
        boolean result = false;
        try {
            Connection con = DBConnect.makeConnection();
            if (con != null) {
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

    public ArrayList<Bill> getSendTransactions(User user, int start, int end) {
        ArrayList<Bill> lst = new ArrayList<>();
        try {
            Connection con = DBConnect.makeConnection();
            if (con != null) {
                UserDAO userDAO = new UserDAO();
                PreparedStatement ps = con.prepareStatement("SELECT * FROM\n"
                        + "(SELECT ROW_NUMBER() OVER (ORDER BY id DESC) AS r, id, sender_username, recipient_username, content, price, transaction_date FROM [Transaction]\n"
                        + "WHERE sender_username = ?) as x\n"
                        + "WHERE x.r BETWEEN ? and ?");
                ps.setString(1, user.getUsername());
                ps.setInt(2, start);
                ps.setInt(3, end);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
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

    public ArrayList<Bill> getReceiveTransactions(User user, int start, int end) {
        ArrayList<Bill> lst = new ArrayList<>();
        try {
            Connection con = DBConnect.makeConnection();
            if (con != null) {
                UserDAO userDAO = new UserDAO();
                PreparedStatement ps = con.prepareStatement("SELECT * FROM\n"
                        + "(SELECT ROW_NUMBER() OVER (ORDER BY id DESC) AS r, id, sender_username, recipient_username, content, price, transaction_date FROM [Transaction]\n"
                        + "WHERE recipient_username = ?) as x\n"
                        + "WHERE x.r BETWEEN ? and ?");
                ps.setString(1, user.getUsername());
                ps.setInt(2, start);
                ps.setInt(3, end);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
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

    public ArrayList<Bill> getTransactionsByUser(User user, int start, int end) {
        ArrayList<Bill> lst = new ArrayList<>();
        try {
            Connection con = DBConnect.makeConnection();
            if (con != null) {
                UserDAO userDAO = new UserDAO();
                PreparedStatement ps = con.prepareStatement("SELECT * FROM \n"
                        + "(SELECT ROW_NUMBER() OVER (ORDER BY id DESC) AS r, id, sender_username, recipient_username, content, price, transaction_date FROM [Transaction]\n"
                        + "WHERE sender_username = ? OR recipient_username = ?) as x\n"
                        + "WHERE x.r BETWEEN ? AND ?");
                ps.setString(1, user.getUsername());
                ps.setString(2, user.getUsername());
                ps.setInt(3, start);
                ps.setInt(4, end);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
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
           e.printStackTrace();
        }
        return lst;
    }

    public ArrayList<Bill> searchBillByCreatorAndSubscriber(String creatorName, User subscriber, int start, int end) {
        ArrayList<Bill> lst = new ArrayList<>();
        String sql = "SELECT * FROM\n"
                + "(SELECT ROW_NUMBER() OVER (ORDER BY id DESC) as r, * FROM [Transaction] WHERE recipient_username LIKE ? AND sender_username = ?) as x\n"
                + "WHERE x.r BETWEEN ? AND ?";
        try (Connection con = DBConnect.makeConnection()) {
            if (con != null)
                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setString(1, "%" + creatorName + "%");
                    ps.setString(2, subscriber.getUsername());
                    ps.setInt(3, start);
                    ps.setInt(4, end);
                    UserDAO userDAO = new UserDAO();
                    try (ResultSet rs = ps.executeQuery()) {
                        while (rs.next()) {
                            int id = rs.getInt("id");
                            String content = rs.getString("content");
                            int price = rs.getInt("price");
                            Date transactionDate = rs.getDate("transaction_date");
                            User creator = userDAO.getUserByUsername(rs.getString("recipient_username"));
                            Bill bill = new Bill(id, subscriber, creator, content, price, transactionDate);
                            lst.add(bill);
                        }
                    }
                }
        }
        catch (Exception e) {

        }
        return lst;
    }
    
    public int countTransactionsByUser(User user){
        int count = -1;
        String sql = "SELECT COUNT(*) AS bill_count FROM [Transaction] WHERE recipient_username = ? OR sender_username = ?";
        try(Connection con = DBConnect.makeConnection(); PreparedStatement ps = con.prepareStatement(sql)){
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getUsername());
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next())
                    count = rs.getInt("bill_count");
            }
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        return count;
    }
    
    public int countSentTransactionsByUser(User user){
        int count = -1;
        String sql = "SELECT COUNT(*) AS bill_count FROM [Transaction] WHERE sender_username = ?";
        try(Connection con = DBConnect.makeConnection(); PreparedStatement ps = con.prepareStatement(sql)){
            ps.setString(1, user.getUsername());
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next())
                    count = rs.getInt("bill_count");
            }
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        return count;
    }
    
    public int countReceivedTransactionsByUser(User user){
        int count = -1;
        String sql = "SELECT COUNT(*) AS bill_count WHERE recipient_username = ?";
        try(Connection con = DBConnect.makeConnection(); PreparedStatement ps = con.prepareStatement(sql)){
            ps.setString(1, user.getUsername());
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next())
                    count = rs.getInt("bill_count");
            }
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        return count;
    }
    
    public ArrayList<Bill> getAllTransactions(int start, int end) {
        ArrayList<Bill> lst = new ArrayList<>();
        String sql = "SELECT * FROM\n"
                + "(SELECT ROW_NUMBER() OVER (ORDER BY transaction_date DESC) as r,"
                + " * FROM [Transaction]) as x\n"
                + "WHERE x.r BETWEEN ? AND ?";
        try (Connection con = DBConnect.makeConnection()) {
            if (con != null)
                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setInt(1, start);
                    ps.setInt(2, end);
                    UserDAO userDAO = new UserDAO();
                    try (ResultSet rs = ps.executeQuery()) {
                        while (rs.next()) {
                            int id = rs.getInt("id");
                            String content = rs.getString("content");
                            int price = rs.getInt("price");
                            Date transactionDate = rs.getDate("transaction_date");
                            User receiver = userDAO.getUserByUsername(rs.getString("recipient_username"));
                            User sender = userDAO.getUserByUsername(rs.getString("sender_username"));
                            Bill bill = new Bill(id, sender, receiver, content, price, transactionDate);
                            lst.add(bill);
                        }
                    }
                }
        }
        catch (Exception e) {

        }
        return lst;
    }
}
