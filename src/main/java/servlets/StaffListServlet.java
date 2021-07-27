/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;
import authority_management.staff.Staff;
import authority_management.staff.StaffDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import report.ReportDAO;
/**
 *
 * @author DELL
 */
@WebServlet(name = "StaffListServlet", urlPatterns = {"/StaffListServlet"})
public class StaffListServlet extends HttpServlet {
    
    final int numStaffInPage = 8;
    final String StaffListPage = "staffList.jsp";
    String isBanned = "all";
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getSession().getAttribute("staff") == null && request.getSession().getAttribute("admin") == null) {
            response.sendRedirect("WelcomePageServlet");
            return;
        }
        int pageNum = 1;
        if (request.getParameter("page") != null) {
            pageNum = Integer.parseInt(request.getParameter("page"));
        }
        request.setAttribute("page", pageNum);
        if (request.getParameter("isBanned") != null) {
            isBanned = request.getParameter("isBanned");
        }
         request.setAttribute("isBanned", isBanned);
        ArrayList<Staff> staffList = new ArrayList<Staff>();
        StaffDAO sDAO = new StaffDAO();
        if (isBanned.equals("all")) {
            staffList = sDAO.getAllStaffs();
        } else if (isBanned.equals("banned")) {
            staffList = sDAO.getAllBannedStaffs();
        } else {
            staffList = sDAO.getAllActiveStaffs();
        }
        int numPage = staffList.size() / numStaffInPage
                + (staffList.size() % numStaffInPage == 0 ? 0 : 1);
        request.setAttribute("numPage", numPage);
        int startIndex = (pageNum - 1) * numStaffInPage;
        int endIndex = pageNum * numStaffInPage;
        endIndex = (endIndex > staffList.size() ? staffList.size() : endIndex);
        System.err.println("full list size: "+ staffList.size());
        ArrayList<Staff> subArray = new ArrayList<>(staffList.subList(startIndex, endIndex));
        ArrayList<Integer>numSolvedReportList=new ArrayList<>();
        ReportDAO rDAO=new ReportDAO();
        for (Staff staff : subArray) {
            int numSolvedReport=rDAO.countReportsByStaff(staff);
            numSolvedReportList.add(numSolvedReport);
        }
        request.setAttribute("numSolvedReportList", numSolvedReportList);
        request.setAttribute("staffList", subArray);
        request.getRequestDispatcher(StaffListPage).forward(request, response);
    }
     // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}