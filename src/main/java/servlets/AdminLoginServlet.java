/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import authority_management.admin.Admin;
import authority_management.admin.AdminDAO;
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
@WebServlet(name = "AdminLoginServlet", urlPatterns = {"/admin"})
public class AdminLoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("admin_login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        AdminDAO dao = new AdminDAO();
        Admin admin = dao.checkLogin(username, HashPassword.HashPassword(password));
        if(admin == null){
            request.setAttribute("LOGINERROR", "Incorrect username or Password");
            request.getRequestDispatcher("admin_login.jsp").forward(request, response);
            return;
        }
        request.getSession().setAttribute("admin", admin);
        response.sendRedirect("dashboard");
    }

}
