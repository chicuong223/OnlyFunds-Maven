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
        if (username == null || password == null) {
            request.setAttribute("LOGINERROR", "Enter username and password");
            request.getRequestDispatcher(adminLoginPage).forward(request, response);
        }
        else {
            String hashedPassword = HashPassword.HashPassword(password);
            Admin currentAdmin = sDAO.checkLogin(username, hashedPassword);
            if (currentAdmin == null) {
                request.setAttribute("LOGINERROR", "Username or password is incorrect");
                request.getRequestDispatcher(adminLoginPage).forward(request, response);
            }
            else {
                HttpSession session = request.getSession();
                session.setAttribute("admin", currentAdmin);
                System.err.println("current staff is null?");
                System.err.println(session.getAttribute("admin") == null);
                request.getRequestDispatcher(staffListPage).forward(request, response);
            }
        }
        request.getSession().setAttribute("admin", admin);
        response.sendRedirect("dashboard");
    }

}
