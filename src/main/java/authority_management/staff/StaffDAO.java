/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package authority_management.staff;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Logger;
import utils.DBConnect;
import utils.HashPassword;

/**
 *
 * @author chiuy
 */
public class StaffDAO {

    public ArrayList<Staff> getAllStaffs(){
        ArrayList<Staff> lst = new ArrayList<>();
        try {
            Connection con = DBConnect.makeConnection();
            if(con != null){
                PreparedStatement ps = con.prepareStatement("SELECT * FROM Staff");
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    String username = rs.getString("username");
                    String password = rs.getString("password");
                    String lastName = rs.getString("lastname");
                    String firstName = rs.getString("firstname");
                    String email = rs.getString("email");
                    boolean isActive = rs.getBoolean("is_active");
                    Staff staff = new Staff(username, password, lastName, firstName, email, isActive);
                    lst.add(staff);
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
    public ArrayList<Staff> getAllBannedStaffs(){
        ArrayList<Staff> lst = new ArrayList<>();
        try {
            Connection con = DBConnect.makeConnection();
            if(con != null){
                PreparedStatement ps = con.prepareStatement("SELECT * FROM Staff where is_active = 0");
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    String username = rs.getString("username");
                    String password = rs.getString("password");
                    String lastName = rs.getString("lastname");
                    String firstName = rs.getString("firstname");
                    String email = rs.getString("email");
                    boolean isActive = false;
                    Staff staff = new Staff(username, password, lastName, firstName, email, isActive);
                    lst.add(staff);
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
    public ArrayList<Staff> getAllActiveStaffs(){
        ArrayList<Staff> lst = new ArrayList<>();
        try {
            Connection con = DBConnect.makeConnection();
            if(con != null){
                PreparedStatement ps = con.prepareStatement("SELECT * FROM Staff where is_active = 1");
                ResultSet rs = ps.executeQuery();
                while(rs.next()){
                    String username = rs.getString("username");
                    String password = rs.getString("password");
                    String lastName = rs.getString("lastname");
                    String firstName = rs.getString("firstname");
                    String email = rs.getString("email");
                    boolean isActive = true;
                    Staff staff = new Staff(username, password, lastName, firstName, email, isActive);
                    lst.add(staff);
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
    public ArrayList<Staff> searchStaff(String search) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Staff> lst = new ArrayList<>();
        String sql = "SELECT *\n"
                + "FROM Staff u\n"
                + "WHERE u.username LIKE ?\n"
                + " Or u.email Like ?\n"
                + " Or u.firstname Like ?\n"
                + " Or u.lastname Like ?\n";
        try {
            con = DBConnect.makeConnection();
            if (con != null) {
                ps = con.prepareStatement(sql);
                search = "%" + search + "%";
                ps.setString(1, search);
                ps.setString(2, search);
                ps.setString(3, search);
                ps.setString(4, search);
                rs = ps.executeQuery();
                while (rs.next()) {
                    Staff staff = new Staff();
                    staff.setUsername(rs.getString("username"));
                    staff.setFirstName(rs.getString("firstname"));
                    staff.setLastName(rs.getString("lastname"));
                    staff.setEmail(rs.getString("email"));
                    staff.setIsActive(rs.getBoolean("is_active"));
                    lst.add(staff);
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

    public boolean addStaff(Staff staff) {
        boolean result = false;
        try (Connection con = DBConnect.makeConnection()) {
            if (con != null)
                try (PreparedStatement ps = con.prepareStatement("INSERT INTO Staff\n"
                    + "VALUES(?, ?, ?, ?, ?, ?)")) {
                ps.setString(1, staff.getUsername());
                ps.setString(2, staff.getPassword());
                ps.setString(3, staff.getFirstName());
                ps.setString(4, staff.getLastName());
                ps.setString(5, staff.getEmail());
                ps.setBoolean(6, true);
                result = ps.executeUpdate() > 0;
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    //no check
    public Staff getStaffByUsername(String username) {
        Staff staff = null;
        try (Connection con = DBConnect.makeConnection()) {
            if (con != null)
                try (PreparedStatement ps = con.prepareStatement("SELECT * FROM Staff where username=?")) {
                ps.setString(1, username);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        String password = rs.getString("password");
                        String lastName = rs.getString("lastname");
                        String firstName = rs.getString("firstname");
                        String email = rs.getString("email");
                        boolean isActive = rs.getBoolean("is_active");
                        staff = new Staff(username, password, lastName, firstName, email, isActive);
                    }
                }
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return staff;
    }
    public Staff checkLogin(String username, String password) {
        try {
            Connection con = DBConnect.makeConnection();
            if(con != null){
                PreparedStatement ps = con.prepareStatement("SELECT * FROM Staff where username=? And password=?");
                ps.setString(1, username);
                ps.setString(2, password);
                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                    String lastName = rs.getString("lastname");
                    String firstName = rs.getString("firstname");
                    String email = rs.getString("email");
                    boolean isActive = rs.getBoolean("is_active");
                    Staff staff = new Staff(username, password, lastName, firstName, email, isActive);
                    return staff;
                }
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    public boolean deactivateStaff(Staff staff){
        boolean result = false;
        String sql = "UPDATE STAFF SET is_active = 0 WHERE username = ?";
        try(Connection con  = DBConnect.makeConnection(); PreparedStatement ps = con.prepareStatement(sql)){
            ps.setString(1, staff.getUsername());
            result = ps.executeUpdate() > 0;
        }
        catch(Exception ex){
            
        }
        return result;
    }
}
