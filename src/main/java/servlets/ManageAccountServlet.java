/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import category.Category;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import map.UserCategoryMapDAO;
import user_management.user.User;
import user_management.user.UserDAO;
import utils.HashPassword;

/**
 *
 * @author ASUS GAMING
 */
@WebServlet(name = "ManageAccountServlet", urlPatterns = {"/ManageAccount"})
public class ManageAccountServlet extends HttpServlet {

    private final String accountInfoPage = "manage_account_info.jsp";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            String action = request.getParameter("action");
            String[] errorList = {};
            UserDAO uDao = new UserDAO();
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            RequestDispatcher rd = request.getRequestDispatcher(accountInfoPage);
            
            //change password
            if (action.equals("password")) {
                String curPassword = HashPassword.HashPassword(request.getParameter("currentPassword"));
                String newPassword = HashPassword.HashPassword(request.getParameter("newPassword"));
                String confNewPassword = HashPassword.HashPassword(request.getParameter("confNewPassword"));
                
//                System.out.println(curPassword);
//                System.out.println(user.getPassword());
                
                if (!curPassword.equals(user.getPassword())) //check match current password
                    errorList[0] = "Password does not match";
                if (!newPassword.equals(confNewPassword)) //check if new and conf equals
                    errorList[1] = "New password does not match";
                if (errorList.length == 0) {
                    uDao.changePassword(user.getEmail(), newPassword);
                    user.setPassword(newPassword);
                    session.setAttribute("user", user);
                }
                request.setAttribute("ERROR_LIST", errorList);
                rd.forward(request, response);
            }
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(accountInfoPage);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
