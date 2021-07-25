/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package authority_management.admin;

import authority_management.staff.Staff;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import utils.DBConnect;

/**
 *
 * @author DELL
 */
public class AdminDAO {
    public Admin CheckLogin(String username, String password) {
        try {
            Connection con = DBConnect.makeConnection();
            if(con != null){
                PreparedStatement ps = con.prepareStatement("SELECT * FROM [Admin] where username=? And password=?");
                ps.setString(1, username);
                ps.setString(2, password);
                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                    String lastName = rs.getString("lastname");
                    String firstName = rs.getString("firstname");
                    String email = rs.getString("email");
                    Admin admin = new Admin(username, password, lastName, firstName, email);
                    return admin;
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
}
