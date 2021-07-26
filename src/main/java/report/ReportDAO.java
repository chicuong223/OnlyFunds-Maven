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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
        ArrayList<Report> reportList = new ArrayList<>();
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
                    if (staffUsername == null) {
                        solveStaff = new Staff();
                        solveStaff.setUsername("");
                    }
                    else
                        solveStaff = new StaffDAO().getStaffByUsername(staffUsername);
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
        }
        catch (SQLException e) {
        }
        finally {
            try {
                if (rs != null)
                    rs.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            }
            catch (SQLException e) {
            }
        }
        return reportList;
    }

    //no check
    public ArrayList<Report> getReportsByType(String type) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Report> reportList = new ArrayList<>();
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
                    if (staffUsername == null) {
                        solveStaff = new Staff();
                        solveStaff.setUsername("");
                    }
                    else
                        solveStaff = new StaffDAO().getStaffByUsername(staffUsername);
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
        }
        catch (SQLException e) {
        }
        finally {
            try {
                if (rs != null)
                    rs.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            }
            catch (SQLException e) {
            }
        }
        return reportList;
    }

    //no check
    public ArrayList<Report> getReportsByStatus(String status) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Report> reportList = new ArrayList<>();
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
                    if (staffUsername == null) {
                        solveStaff = new Staff();
                        solveStaff.setUsername("");
                    }
                    else
                        solveStaff = new StaffDAO().getStaffByUsername(staffUsername);
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
        }
        catch (SQLException e) {
        }
        finally {
            try {
                if (rs != null)
                    rs.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            }
            catch (SQLException e) {
            }
        }
        return reportList;
    }

    //no check
    public ArrayList<Report> getReportsByStatusAndType(String status, String type) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Report> reportList = new ArrayList<>();
        try {
            con = DBConnect.makeConnection();
            if (con != null) {
                ps = con.prepareStatement("SELECT * FROM Report where status=? and type=?");
                ps.setString(1, status);
                ps.setString(2, type);
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
                    Date solveDate = rs.getDate("solve_date");
                    Report report = new Report(id, reportUser, reportedObjectId, type,
                            solveStaff, title, description, reportDate,
                            status, solveDate);
                    reportList.add(report);
                }
            }
        }
        catch (SQLException e) {
        }
        finally {
            try {
                if (rs != null)
                    rs.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            }
            catch (SQLException e) {
            }
        }
        return reportList;
    }

    //no check
    public ArrayList<Report> getReportsByObjIdAndType(String objId, String type) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Report> reportList = new ArrayList<>();
        try {
            con = DBConnect.makeConnection();
            if (con != null) {
                ps = con.prepareStatement("SELECT * FROM Report where reported_id=? and type=?");
                ps.setString(1, objId);
                ps.setString(2, type);
                rs = ps.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt("id");
                    UserDAO uDAO = new UserDAO();
                    User reportUser = uDAO.getUserByUsername(rs.getString("report_username"));
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
                    Report report = new Report(id, reportUser, objId, type,
                            solveStaff, title, description, reportDate,
                            status, solveDate);
                    reportList.add(report);
                }
            }
        }
        catch (SQLException e) {
            System.out.println(e.getStackTrace());
        }
        finally {
            try {
                if (rs != null)
                    rs.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            }
            catch (SQLException e) {
            }
        }
        return reportList;
    }

    public ArrayList<Report> getReportsByStaff(Staff solveStaff) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Report> reportList = new ArrayList<>();
        try {
            con = DBConnect.makeConnection();
            if (con != null) {
                ps = con.prepareStatement("SELECT * FROM Report where solved_by_staff=?");
                ps.setString(1, solveStaff.getUsername());
                rs = ps.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt("id");
                    UserDAO uDAO = new UserDAO();
                    User reportUser = uDAO.getUserByUsername(rs.getString("report_username"));
                    String reportedObjectId = rs.getString("reported_id");
                    String type = rs.getString("type");
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
        }
        catch (SQLException e) {
        }
        finally {
            try {
                if (rs != null)
                    rs.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            }
            catch (SQLException e) {
            }
        }
        return reportList;
    }
//no check

    public Report getReportById(String id) {
        return getReportById(Integer.parseInt(id));
    }

    public Report getReportById(int id) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBConnect.makeConnection();
            if (con != null) {
                ps = con.prepareStatement("SELECT * FROM Report where id=?");
                ps.setInt(1, id);
                rs = ps.executeQuery();
                if (rs.next()) {
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
                    return report;
                }
            }
        }
        catch (SQLException e) {
        }
        finally {
            try {
                if (rs != null)
                    rs.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            }
            catch (SQLException e) {
            }
        }
        return null;
    }

    //no check
    public boolean addReport(Report report) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBConnect.makeConnection();
            if (con != null) {
                ps = con.prepareStatement("INSERT INTO Report(report_username, reported_id, type, solved_by_staff, title, description, report_date, status, solve_date)\n"
                        + "VALUES(?, ?, ?, ?, ?, ?, ?, ? ,?)");
                ps.setString(1, report.getReportUser().getUsername());
                ps.setString(2, report.getReportedObjectId());
                ps.setString(3, report.getType());
                ps.setString(4, null);
                ps.setString(5, report.getTitle());
                ps.setString(6, report.getDescription());
                ps.setDate(7, report.getReportDate());
                ps.setString(8, report.getStatus());
                ps.setDate(9, report.getSolveDate());
                ps.executeUpdate();
                return true;
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            try {
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            }
            catch (SQLException e) {
            }
        }
        return false;
    }
//no check

    public boolean updateReport(Report report) {
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
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            try {
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            }
            catch (SQLException e) {
            }
        }
        return false;
    }

    //notcheck
    public boolean approveReports(String objectId, String type, Staff staff) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBConnect.makeConnection();
            if (con != null) {
                ps = con.prepareStatement("UPDATE Report SET solved_by_staff=?, status=\'approved\', solve_date=?\n"
                        + " WHERE reported_id=? and type=?\n");
                ps.setString(1, staff.getUsername());
                ps.setDate(2, Date.valueOf(LocalDate.now()));
                ps.setString(3, objectId);
                ps.setString(4, type);
                ps.executeUpdate();
                return true;
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            try {
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            }
            catch (SQLException e) {
            }
        }
        return false;
    }

    //notcheck
    public boolean declineReports(String objectId, String type, Staff staff) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = DBConnect.makeConnection();
            if (con != null) {
                ps = con.prepareStatement("UPDATE Report SET solved_by_staff=?, status=\'declined\', solve_date=?\n"
                        + " WHERE reported_id=? and type=?\n");
                ps.setString(1, staff.getUsername());
                ps.setDate(2, Date.valueOf(LocalDate.now()));
                ps.setString(3, objectId);
                ps.setString(4, type);
                ps.executeUpdate();
                System.err.println("declineReports() success");
                return true;
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            try {
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            }
            catch (SQLException e) {
            }
        }
        return false;
    }

    public List<Report> getReportsByUser(User user) {
        List<Report> lst = new ArrayList<>();
        String sql = "SELECT * FROM Report WHERE report_username = ?";
        try (Connection con = DBConnect.makeConnection()) {
            if (con != null)
                try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, user.getUsername());
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        int id = rs.getInt("id");
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
                        Report report = new Report(id, user, reportedObjectId, type,
                                solveStaff, title, description, reportDate,
                                status, solveDate);
                        lst.add(report);
                    }
                }
            }
        }
        catch (Exception e) {
//            System.out.println(e.getStackTrace());
            System.out.println(e.getMessage());
        }
        return lst;
    }

    public int countReportsByStaff(String username) {
        Staff solveStaff = null;
        if (username != null && username.isEmpty()) {
            StaffDAO sDAO = new StaffDAO();
            solveStaff = sDAO.getStaffByUsername(username);
            return countReportsByStaff(solveStaff);
        }
        else
            return 0;
    }

    public int countReportsByStaff(Staff solveStaff) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = DBConnect.makeConnection();
            if (con != null) {
                ps = con.prepareStatement("SELECT count(id) as num FROM Report Where solved_by_staff=?");
                ps.setString(1, solveStaff.getUsername());
                rs = ps.executeQuery();
                if (rs.next()) {
                    int num = rs.getInt("num");
                    return num;
                }
            }
        }
        catch (SQLException e) {
        }
        finally {
            try {
                if (rs != null)
                    rs.close();
                if (ps != null)
                    ps.close();
                if (con != null)
                    con.close();
            }
            catch (SQLException e) {
            }
        }
        return 0;
    }
//    //notcheck
//    public boolean pendReports(String objectId, String type, Staff staff) {
//        Connection con = null;
//        PreparedStatement ps = null;
//        try {
//            con = DBConnect.makeConnection();
//            if (con != null) {
//                ps = con.prepareStatement("UPDATE Report SET solved_by_staff=?, status=\"pending\", solve_date=?\n"
//                        + " WHERE reported_id=? and type=?\n");
//                ps.setString(1, staff.getUsername());
//                ps.setDate(2, Date.valueOf(LocalDate.now()));
//                ps.executeUpdate();
//                return true;
//            }
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//        } finally {
//            try {
//                if (ps != null) {
//                    ps.close();
//                }
//                if (con != null) {
//                    con.close();
//                }
//            } catch (SQLException e) {
//            }
//        }
//        return false;
//    }
}
