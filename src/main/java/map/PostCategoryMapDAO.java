/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map;

import category.Category;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import post_management.post.Post;
import user_management.user.User;
import utils.DBConnect;

/**
 *
 * @author chiuy
 */
public class PostCategoryMapDAO {
    public boolean addPostCatMap(User user, Category cat){
        boolean result = false;
        try{
            Connection con = DBConnect.makeConnection();
            if(con != null){
                try (PreparedStatement ps = con.prepareStatement("INSERT INTO Post_Category_Map(post_id, category_id)\n"
                        + "VALUES((SELECT MAX(id) FROM Post WHERE uploader_username = ?), ?)")) {
                    ps.setString(1, user.getUsername());
                    ps.setInt(2, cat.getCategoryId());
                    result = ps.executeUpdate() > 0;
                }
                con.close();
            }
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return result;
    }
    
    public boolean deleteCategoryMapsByPost(Post post){
        try{
            Connection con = DBConnect.makeConnection();
            if(con != null){
                PreparedStatement ps = con.prepareStatement("DELETE FROM Post_Category_Map WHERE post_id = ?");
                ps.setInt(1, post.getPostId());
                return ps.executeUpdate() > 0;
            }
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }
}
