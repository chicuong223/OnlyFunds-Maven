/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author chiuy
 */
public class DBConnect {

    public static Connection makeConnection() {
        Connection con = null;
        try {
            Context context = new InitialContext();
            Context end = (Context) context.lookup("java:comp/env");
            DataSource ds = (DataSource) end.lookup("DBConnection");
            con = ds.getConnection();
        }
        catch (SQLException | NamingException e) {
            System.out.println(e.getMessage());
        }
        return con;
    }
}
