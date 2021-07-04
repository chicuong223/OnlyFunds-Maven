/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map;

import category.Category;
import post_management.post.Post;

/**
 *
 * @author chiuy
 */
public class PostCategoryMap {
    private Category category;
    private Post post;
    
    public PostCategoryMap(){}

    public PostCategoryMap(Category category, Post post) {
        this.category = category;
        this.post = post;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
    
    
}
