/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package authority_management.admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import utils.DBConnect;

/**
 *
 * @author chiuy
 */
public class AdminDAO {

    public Admin checkLogin(String username, String password) {
        Admin admin = null;
        String sql = "SELECT * FROM Admin WHERE username = ? AND password = ?";
        try (Connection con = DBConnect.makeConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if(rs.next()) {
                    String firstName = rs.getString("firstname");
                    String lastName = rs.getString("lastname");
                    String email = rs.getString("email");
                    admin = new Admin(username, password, lastName, firstName, email);
                }
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return admin;
    }
}
