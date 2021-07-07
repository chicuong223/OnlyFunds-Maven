/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package subscription_management.subscription;

import user_management.user.User;
import subscription_management.tier.Tier;
import java.sql.Date;

/**
 *
 * @author chiuy
 */
public class Subscription {
    private int subscriptionId;
    private User subscriber;
    private Tier tier;
    private Date startDate;
    private Date endDate;
    private boolean isActive;
    
    public Subscription(){}

    public Subscription(int subscriptionId, User subscriber, Tier tier, Date startDate, Date endDate, boolean isActive) {
        this.subscriptionId = subscriptionId;
        this.subscriber = subscriber;
        this.tier = tier;
        this.startDate = startDate;
        this.endDate = endDate;
        this.isActive = isActive;
    }

    public int getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(int subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

   

    public User getSubscriber() {
        return subscriber;
    }

    public void setSubscriber(User subscriber) {
        this.subscriber = subscriber;
    }

    public Tier getTier() {
        return tier;
    }

    public void setTier(Tier tier) {
        this.tier = tier;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
    
    
}
