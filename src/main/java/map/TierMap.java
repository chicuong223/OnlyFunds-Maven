/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map;

import post_management.post.Post;
import subscription_management.tier.Tier;

/**
 *
 * @author chiuy
 */
public class TierMap {
    private Tier tier;
    private Post post;

    public TierMap(Tier tier, Post post) {
        this.tier = tier;
        this.post = post;
    }

    public Tier getTier() {
        return tier;
    }

    public void setTier(Tier tier) {
        this.tier = tier;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
    
    
}
