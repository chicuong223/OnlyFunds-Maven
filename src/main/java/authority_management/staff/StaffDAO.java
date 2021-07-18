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
    
    public boolean addStaff(Staff staff){
        boolean result = false;
        try {
            Connection con = DBConnect.makeConnection();
            if(con != null){
                PreparedStatement ps = con.prepareStatement("INSERT INTO Staff\n"
                        + "VALUES(?, ?, ?, ?, ?, ?)");
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
        try {
            Connection con = DBConnect.makeConnection();
            if(con != null){
                PreparedStatement ps = con.prepareStatement("SELECT * FROM Staff where username=?");
                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                    String password = rs.getString("password");
                    String lastName = rs.getString("lastname");
                    String firstName = rs.getString("firstname");
                    String email = rs.getString("email");
                    boolean isActive = rs.getBoolean("is_active");
                    Staff staff = new Staff(username, password, lastName, firstName, email, isActive);
                    return staff;
                }
                rs.close();
                ps.close();
                con.close();
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    public Staff CheckLogin(String username, String password) {
        try {
            Connection con = DBConnect.makeConnection();
            if(con != null){
                PreparedStatement ps = con.prepareStatement("SELECT * FROM Staff where username=? And password=?");
                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                    String lastName = rs.getString("lastname");
                    String firstName = rs.getString("firstname");
                    String email = rs.getString("email");
                    boolean isActive = rs.getBoolean("is_active");
                    Staff staff = new Staff(username, password, lastName, firstName, email, isActive);
                    return staff;
                }
                rs.close();
                ps.close();
                con.close();
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    public static void main(String[] args) {
        StaffDAO dao = new StaffDAO();
        Staff staff = new Staff("staff2", HashPassword.HashPassword("staff2"), "Tang","Chi-Cuong", "chicuong@gmail.com", true);
        System.out.println(dao.addStaff(staff));
    }
}
