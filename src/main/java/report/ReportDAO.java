/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package report;

import authority_management.staff.Staff;
import authority_management.staff.StaffDAO;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import user_management.user.User;
import user_management.user.UserDAO;
import utils.DBConnect;

/**
 *
 * @author DELL
 */
public class ReportDAO {

    //no check
    public ArrayList<Report> getReports() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Report> reportList = new ArrayList<Report>();
        try {
            con = DBConnect.makeConnection();
            if (con != null) {
                ps = con.prepareStatement("SELECT * FROM Report");
                rs = ps.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt("id");
                    UserDAO uDAO = new UserDAO();
                    User reportUser = uDAO.getUserByUsername(rs.getString("report_username"));
                    String reportedObjectId = rs.getString("reported_id");
                    String type = rs.getString("type");
                    String staffUsername = rs.getString("solved_by_staff");
                    Staff solveStaff = null;
                    if (staffUsername != null && staffUsername.isEmpty()) {
                        StaffDAO sDAO = new StaffDAO();
                        solveStaff = sDAO.getStaffByUsername(staffUsername);
                    }
                    String title = rs.getString("title");
                    String description = rs.getString("description");
                    Date reportDate = rs.getDate("report_date");
                    String status = rs.getString("status");
                    Date solveDate = rs.getDate("solve_date");
                    Report report = new Report(id, reportUser, reportedObjectId, type,
                            solveStaff, title, description, reportDate,
                            status, solveDate);
                    reportList.add(report);
                }
            }
        } catch (SQLException e) {
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
            }
        }
        return reportList;
    }

    //no check
    public ArrayList<Report> getReportsByType(String type) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Report> reportList = new ArrayList<Report>();
        try {
            con = DBConnect.makeConnection();
            if (con != null) {
                ps = con.prepareStatement("SELECT * FROM Report where type=?");
                ps.setString(1, type);
                rs = ps.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt("id");
                    UserDAO uDAO = new UserDAO();
                    User reportUser = uDAO.getUserByUsername(rs.getString("report_username"));
                    String reportedObjectId = rs.getString("reported_id");
                    String staffUsername = rs.getString("solved_by_staff");
                    Staff solveStaff = null;
                    if (staffUsername != null && staffUsername.isEmpty()) {
                        StaffDAO sDAO = new StaffDAO();
                        solveStaff = sDAO.getStaffByUsername(staffUsername);
                    }
                    String title = rs.getString("title");
                    String description = rs.getString("description");
                    Date reportDate = rs.getDate("report_date");
                    String status = rs.getString("status");
                    Date solveDate = rs.getDate("solve_date");
                    Report report = new Report(id, reportUser, reportedObjectId, type,
                            solveStaff, title, description, reportDate,
                            status, solveDate);
                    reportList.add(report);
                }
            }
        } catch (SQLException e) {
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
            }
        }
        return reportList;
    }

    //no check
    public ArrayList<Report> getReportsByStatus(String status) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Report> reportList = new ArrayList<Report>();
        try {
            con = DBConnect.makeConnection();
            if (con != null) {
                ps = con.prepareStatement("SELECT * FROM Report where status=?");
                ps.setString(1, status);
                rs = ps.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt("id");
                    UserDAO uDAO = new UserDAO();
                    User reportUser = uDAO.getUserByUsername(rs.getString("report_username"));
                    String type = rs.getString("type");
                    String reportedObjectId = rs.getString("reported_id");
                    String staffUsername = rs.getString("solved_by_staff");
                    Staff solveStaff = null;
                    if (staffUsername != null && staffUsername.isEmpty()) {
                        StaffDAO sDAO = new StaffDAO();
                        solveStaff = sDAO.getStaffByUsername(staffUsername);
                    }
                    String title = rs.getString("title");
                    String description = rs.getString("description");
                    Date reportDate = rs.getDate("report_date");
                    Date solveDate = rs.getDate("solve_date");
                    Report report = new Report(id, reportUser, reportedObjectId, type,
                            solveStaff, title, description, reportDate,
                            status, solveDate);
                    reportList.add(report);
                }
            }
        } catch (SQLException e) {
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
            }
        }
        return reportList;
    }

    //no check
    public boolean addPost(Report report) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBConnect.makeConnection();
            if (con != null) {
                ps = con.prepareStatement("INSERT INTO Report(id, report_username, reported_id, type, solved_by_staff, title, description, report_date, status, solve_date)\n"
                        + "VALUES(?, ?, ?, ?, ?, ?, ?, ? ,?, ?)");
                ps.setInt(1, report.getId());
                ps.setString(2, report.getReportUser().getUsername());
                ps.setString(3, report.getReportedObjectId());
                ps.setString(4, report.getType());
                ps.setString(5, report.getSolveStaff().getUsername());
                ps.setString(6, report.getTitle());
                ps.setString(7, report.getDescription());
                ps.setDate(8, report.getReportDate());
                ps.setString(9, report.getStatus());
                ps.setDate(10, report.getSolveDate());
                ps.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
            }
        }
        return false;
    }
//no check
    public boolean updatePost(Report report) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBConnect.makeConnection();
            if (con != null) {
                ps = con.prepareStatement("UPDATE Report SET report_username=?, reported_id=?, type=?, solved_by_staff=?, title=?, description=?, report_date=?, status=?, solve_date=?\n"
                        + " WHERE id = ?\n");
                ps.setString(1, report.getReportUser().getUsername());
                ps.setString(2, report.getReportedObjectId());
                ps.setString(3, report.getType());
                ps.setString(4, report.getSolveStaff().getUsername());
                ps.setString(5, report.getTitle());
                ps.setString(6, report.getDescription());
                ps.setDate(7, report.getReportDate());
                ps.setString(8, report.getStatus());
                ps.setDate(9, report.getSolveDate());
                ps.setInt(10, report.getId());
                ps.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
            }
        }
        return false;
    }
}
