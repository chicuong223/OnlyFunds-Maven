/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package subscription_management.tier;

import java.io.Serializable;
import user_management.user.User;

/**
 *
 * @author chiuy
 */
public class Tier implements Serializable {
    private int tierId;
    private String tierTitle;
    private String description;
    private User creator;
    private int price;
    private boolean isActive;
    
    public Tier(){}

    public Tier(int tierId, String tierTitle, String description, User creator, int price, boolean isActive) {
        this.tierId = tierId;
        this.tierTitle = tierTitle;
        this.description = description;
        this.creator = creator;
        this.price = price;
        this.isActive = isActive;
    }

    public int getTierId() {
        return tierId;
    }

    public void setTierId(int tierId) {
        this.tierId = tierId;
    }

    public String getTierTitle() {
        return tierTitle;
    }

    public void setTierTitle(String tierTitle) {
        this.tierTitle = tierTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public String toString() {
        return "Tier{" + "tierId=" + tierId + ", tierTitle=" + tierTitle + ", description=" + description + ", creator=" + creator.getUsername() + ", price=" + price + ", isActive=" + isActive + '}';
    }
    
    
}
