/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package subscription_management.transaction;

import java.io.Serializable;
import user_management.user.User;
import java.sql.Date;

/**
 *
 * @author chiuy
 */
public class Bill implements Serializable {
    private int billId;
    private User sender;
    private User recipient;
    private String content;
    private int price;
    private Date transactionDate;
    
    public Bill(){}

    public Bill(int billId, User sender, User recipient, String content, int price, Date transactionDate) {
        this.billId = billId;
        this.sender = sender;
        this.recipient = recipient;
        this.content = content;
        this.price = price;
        this.transactionDate = transactionDate;
    }

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }
    
    
}
