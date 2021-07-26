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
import utils.HashPassword;

/**
 *
 * @author chiuy
 */
@WebServlet(name = "AddStaffServlet", urlPatterns = {"/AddStaffServlet"})
public class AddStaffServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        StaffDAO dao = new StaffDAO();
        String firstName = request.getParameter("firstname");
        String lastName = request.getParameter("firstname");
        String userName = request.getParameter("firstname");
        String emailName = request.getParameter("firstname");
        String password = request.getParameter("password");
        String hashedPassword = HashPassword.HashPassword(password);
        Staff newStaff = new Staff(userName, hashedPassword, lastName, firstName, emailName, true);
        dao.addStaff(newStaff);
        response.sendRedirect("StaffListServlet");
    }

}
