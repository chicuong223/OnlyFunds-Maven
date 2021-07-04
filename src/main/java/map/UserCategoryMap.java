/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map;

import category.Category;
import user_management.user.User;

/**
 *
 * @author chiuy
 */
public class UserCategoryMap {
    private Category category;
    private User user;
    
    public UserCategoryMap(){}

    public UserCategoryMap(Category category, User user) {
        this.category = category;
        this.user = user;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    
}
