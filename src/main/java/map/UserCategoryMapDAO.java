/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map;

import category.CategoryDAO;
import category.Category;
import user_management.user.User;
import user_management.user.UserDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import utils.DBConnect;

/**
 *
 * @author chiuy
 */
public class UserCategoryMapDAO {

    //get category map by category id
    public ArrayList<UserCategoryMap> getCategoryMapByCategoryID(int categoryId) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<UserCategoryMap> lst = new ArrayList<>();
        try {
            con = DBConnect.makeConnection();
            if (con != null) {
                ps = con.prepareStatement("SELECT * FROM User_Category_Map WHERE category_id = ?");
                ps.setInt(1, categoryId);
                rs = ps.executeQuery();
                CategoryDAO catDAO = new CategoryDAO();
                UserDAO userDAO = new UserDAO();
                while (rs.next()) {
                    Category category = catDAO.getCategoryByID(rs.getInt("category_id"));
                    User user = userDAO.getUserByUsername(rs.getString("username"));
                    UserCategoryMap catMap = new UserCategoryMap(category, user);
                    lst.add(catMap);
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

    //Add category map into database
    public boolean addCategoryMap(UserCategoryMap categoryMap) {
        Connection con = null;
        PreparedStatement ps = null;
        boolean success = false;
        String sql = "INSERT INTO User_Category_Map(category_id, username)"
                + "VALUES(?, ?)";
        try {
            con = DBConnect.makeConnection();
            if (con != null) {
                ps = con.prepareStatement(sql);
                ps.setInt(1, categoryMap.getCategory().getCategoryId());
                ps.setString(2, categoryMap.getUser().getUsername());
                ps.executeUpdate();
                success = true;
            }
        } catch (SQLException e) {
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
            }
        }
        return success;
    }

    public ArrayList<Category> getCategoriesByUser(User user) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Category> lst = null;
        try {
            con = DBConnect.makeConnection();
            if (con != null) {
                lst = new ArrayList<>();
                ps = con.prepareStatement("SELECT * FROM Category WHERE id in(\n"
                        + "SELECT category_id FROM User_Category_Map WHERE username LIKE ?)");
                ps.setString(1, user.getUsername());
                rs = ps.executeQuery();
                while (rs.next()) {
                    int categoryID = rs.getInt("id");
                    String categoryName = rs.getString("Name");
                    Category category = new Category(categoryID, categoryName);
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

    public static void main(String[] args) {
        UserDAO uDAO = new UserDAO();
        User user = uDAO.getUserByUsername("chicuong");
        UserCategoryMapDAO ucDAO = new UserCategoryMapDAO();
        ArrayList<Category> lst = ucDAO.getCategoriesByUser(user);
        for (Category category : lst) {
            System.out.println(category.getCategoryId());
        }
    }
}
