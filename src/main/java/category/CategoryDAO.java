/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import user_management.user.User;
import utils.DBConnect;

/**
 *
 * @author chiuy
 */
public class CategoryDAO {

    //get all categories from database
    public ArrayList<Category> getAllCategories() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Category> lst = new ArrayList<>();
        try {
            con = DBConnect.makeConnection();
            if (con != null) {
                ps = con.prepareStatement("SELECT * FROM Category");
                rs = ps.executeQuery();
                while (rs.next()) {
                    int categoryId = rs.getInt("id");
                    String categoryName = rs.getString("name");
                    Category category = new Category(categoryId, categoryName);
                    lst.add(category);
                }
            }
        } catch (SQLException e) {
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
            }
        }
        return lst;
    }

    public ArrayList<Category> getCategoriesByCreator(User user) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Category> lst = new ArrayList<>();
        try {
            con = DBConnect.makeConnection();
            if (con != null) {
                String sql = 
                          "select ucm.*, c.Name\n"
                        + "from User_Category_Map ucm, Category c\n"
                        + "where ucm.username=? \n"
                        + "	and c.id=ucm.category_id";
                ps = con.prepareStatement("sql");
                ps.setString(1, user.getUsername());
                rs = ps.executeQuery();
                while (rs.next()) {
                    int categoryId = rs.getInt("id");
                    String categoryName = rs.getString("name");
                    Category category = new Category(categoryId, categoryName);
                    lst.add(category);
                }
            }
        } catch (SQLException e) {
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
            }
        }
        return lst;
    }

    //get category by id
    public Category getCategoryByID(int id) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Category category = null;
        try {
            con = DBConnect.makeConnection();
            if (con != null) {
                ps = con.prepareStatement("SELECT * FROM Category WHERE id = ?");
                ps.setInt(1, id);
                rs = ps.executeQuery();
                if (rs.next()) {
                    int categoryId = rs.getInt("id");
                    String categoryName = rs.getString("name");
                    category = new Category(categoryId, categoryName);
                }
            }
        } catch (SQLException e) {
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
            }
        }
        return category;
    }

}
