/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package post_management.like;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import post_management.post.Post;
import user_management.user.User;
import user_management.user.UserDAO;
import utils.DBConnect;

/**
 *
 * @author chiuy
 */
public class PostLikeDAO {
    public int  countPostLikeByPost(Post post){
        int count = 0;
        try {
            Connection con = DBConnect.makeConnection();
            if(con != null){
                try (PreparedStatement ps = con.prepareStatement("SELECT COUNT(username) as countNo FROM Post_Like WHERE post_id = ?")) {
                    ps.setInt(1, post.getPostId());
                    try (ResultSet rs = ps.executeQuery()) {
                        UserDAO uDAO = new UserDAO();
                        if(rs.next()){
                            count = rs.getInt("countNo");
                        }
                    }
                }
                con.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return count;
    }
}
