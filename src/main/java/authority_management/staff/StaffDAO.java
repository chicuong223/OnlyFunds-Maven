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

    public ArrayList<Staff> getAllStaffs(int start, int end) {
        ArrayList<Staff> lst = new ArrayList<>();
        try (Connection con = DBConnect.makeConnection()) {
            if (con != null)
                try (PreparedStatement ps = con.prepareStatement("SELECT * FROM"
                    + "(SELECT ROW_NUMBER() OVER(ORDER BY username) AS r, * FROM Staff) AS x\n"
                    + "WHERE x.r BETWEEN ? AND ?")) {
                ps.setInt(1, start);
                ps.setInt(2, end);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        String username = rs.getString("username");
                        String password = rs.getString("password");
                        String lastName = rs.getString("lastname");
                        String firstName = rs.getString("firstname");
                        String email = rs.getString("email");
                        boolean isActive = rs.getBoolean("is_active");
                        Staff staff = new Staff(username, password, lastName, firstName, email, isActive);
                        lst.add(staff);
                    }
                }
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
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
        Staff staff = null;
        try (Connection con = DBConnect.makeConnection()) {
            if (con != null)
                try (PreparedStatement ps = con.prepareStatement("SELECT * FROM Staff where username=? And password=?")) {
                    ps.setString(1, username);
                    ps.setString(2, password);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
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
