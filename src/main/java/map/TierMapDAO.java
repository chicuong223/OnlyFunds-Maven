/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import post_management.post.Post;
import subscription_management.tier.Tier;
import user_management.user.User;
import utils.DBConnect;

/**
 *
 * @author chiuy
 */
public class TierMapDAO {

    public boolean addTierMap(Tier tier, Post post) {
        boolean result = false;
        try {
            Connection con = DBConnect.makeConnection();
            if (con != null) {
                try (PreparedStatement ps = con.prepareStatement("INSERT INTO Tier_Map(post_id, tier_id)\n"
                        + "VALUES(?, ?)")) {
                    ps.setInt(1, post.getPostId());
                    ps.setInt(2, tier.getTierId());
                    result = ps.executeUpdate() > 0;
                }
                con.close();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
    
    public boolean deleteTierMapsByPost(Post post){
        try{
            Connection con = DBConnect.makeConnection();
            if(con != null){
                PreparedStatement ps = con.prepareStatement("DELETE FROM Tier_Map WHERE post_id = ?");
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
