/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user_management.follow;

import java.io.Serializable;
import user_management.user.User;

/**
 *
 * @author chiuy
 */
public class Follow implements Serializable {
    private User follower;
    private User followed;
    
    public Follow(){}

    public Follow(User follower, User followed) {
        this.follower = follower;
        this.followed = followed;
    }

    public User getFollower() {
        return follower;
    }

    public void setFollower(User follower) {
        this.follower = follower;
    }

    public User getFollowed() {
        return followed;
    }

    public void setFollowed(User followed) {
        this.followed = followed;
    }
    
    
}
