/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package report;

import authority_management.staff.Staff;
import user_management.user.User;
import java.sql.Date;

/**
 *
 * @author chiuy
 */
public class Report {
    private int id;
    private User reportUser;
    private String reportedObjectId;
    private String type;
    private Staff solveStaff;
    private String title;
    private String description;
    private Date reportDate;
    private String status;
    private Date solveDate;

    public Report(int id, User reportUser, String reportedObjectId, String type,
            Staff solveStaff, String title, String description, Date reportDate,
            String status, Date solveDate) {
        this.id=id;
        this.reportUser = reportUser;
        this.reportedObjectId = reportedObjectId;
        this.type = type;
        this.solveStaff = solveStaff;
        this.title = title;
        this.description = description;
        this.reportDate = reportDate;
        this.status = status;
        this.solveDate = solveDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public User getReportUser() {
        return reportUser;
    }

    public void setReportUser(User reportUser) {
        this.reportUser = reportUser;
    }

    public String getReportedObjectId() {
        return reportedObjectId;
    }

    public void setReportedObjectId(String reportedObjectId) {
        this.reportedObjectId = reportedObjectId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Staff getSolveStaff() {
        return solveStaff;
    }

    public void setSolveStaff(Staff solveStaff) {
        this.solveStaff = solveStaff;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getSolveDate() {
        return solveDate;
    }

    public void setSolveDate(Date solveDate) {
        this.solveDate = solveDate;
    }
    
    
}
