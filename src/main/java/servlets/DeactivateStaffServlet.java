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

/**
 *
 * @author chiuy
 */
@WebServlet(name = "DeactivateStaffServlet", urlPatterns = {"/DeactivateStaffServlet"})
public class DeactivateStaffServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String staffUsername = request.getParameter("username");
        StaffDAO dao = new StaffDAO();
        Staff staff = dao.getStaffByUsername(staffUsername);
        if(staff == null){
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }
        dao.deactivateStaff(staff);
        response.sendRedirect("StaffListServlet");
    }

}
