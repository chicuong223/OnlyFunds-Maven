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
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import utils.HashPassword;

@WebServlet(name = "StaffLoginServlet", urlPatterns = {"/staff"})
public class StaffLoginServlet extends HttpServlet {

    final String staffLoginPage = "staffLogin.jsp";
    final String reportListPage = "ReportListServlet";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher(staffLoginPage).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        StaffDAO sDAO = new StaffDAO();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (username == null || password == null) {
            request.setAttribute("LOGINERROR", "Enter username and password");
            request.getRequestDispatcher(staffLoginPage).forward(request, response);
        } else {
            String hashedPassword = HashPassword.HashPassword(password);
            System.out.println(hashedPassword);
            Staff currentStaff = sDAO.CheckLogin(username, hashedPassword);
            if (currentStaff == null) {
                request.setAttribute("LOGINERROR", "Username or password is incorrect");
                request.getRequestDispatcher(staffLoginPage).forward(request, response);
            } else {
                HttpSession session = request.getSession();
                session.setAttribute("staff", currentStaff);
                System.err.println("current staff is null?");
                System.err.println(session.getAttribute("staff")==null);
                request.getRequestDispatcher(reportListPage).forward(request, response);
            }
        }
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
