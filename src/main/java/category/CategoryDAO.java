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
import java.util.List;
import post_management.post.Post;
import utils.DBConnect;

/**
 *
 * @author chiuy
 */
public class CategoryDAO {
    //get all categories from database
    public ArrayList<Category> getAllCategories(){
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Category> lst = new ArrayList<>();
        try {
            con = DBConnect.makeConnection();
            if(con != null){
                ps = con.prepareStatement("SELECT * FROM Category");
                rs = ps.executeQuery();
                while(rs.next()){
                    int categoryId = rs.getInt("id");
                    String categoryName = rs.getString("name");
                    Category category = new Category(categoryId, categoryName);
                    lst.add(category);
                }
            }
        } catch (SQLException e) {
        }
        finally{
            try {
                if(rs != null) rs.close();
                if(ps != null) ps.close();
                if(con != null) con.close();
            } catch (SQLException e) {
            }
        }
        return lst;
    }
    
    //get category by id
    public Category getCategoryByID(int id){
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Category category = null;
        try {
            con = DBConnect.makeConnection();
            if(con != null){
                ps = con.prepareStatement("SELECT * FROM Category WHERE id = ?");
                ps.setInt(1, id);
                rs = ps.executeQuery();
                if(rs.next()){
                    int categoryId = rs.getInt("id");
                    String categoryName = rs.getString("name");
                    category = new Category(categoryId, categoryName);
                }
            }
        } catch (SQLException e) {
        }
        finally{
            try {
                if(rs != null) rs.close();
                if(ps != null) ps.close();
                if(con != null) con.close();
            } catch (SQLException e) {
            }
        }
        return category;
    }
    
    public List<Category> getCategoriesByPost(Post post){
        List<Category> lst = new ArrayList<>();
        String sql = "SELECT * FROM Category WHERE id IN (\n"
                + "SELECT category_id FROM Post_Category_Map WHERE post_id = ?)";
        try (Connection con = DBConnect.makeConnection()){
            if(con != null){
                try(PreparedStatement ps = con.prepareStatement(sql)){
                    ps.setInt(1, post.getPostId());
                    try(ResultSet rs = ps.executeQuery()){
                        while(rs.next()){
                            int id = rs.getInt("id");
                            String name = rs.getString("name");
                            Category cat = new Category(id, name);
                            lst.add(cat);
                        }
                    }
                }
            }
        }
        catch (Exception e) {
            System.out.println(e.getStackTrace());
        }
        return lst;
    }

}
