/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import category.Category;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import map.UserCategoryMapDAO;
import user_management.user.User;
import user_management.user.UserDAO;
import utils.ContextAndSessionCheck;
import utils.HashPassword;
import utils.UploadFile;

/**
 *
 * @author ASUS GAMING
 */
@WebServlet(name = "ManageAccountServlet", urlPatterns = {"/ManageAccount"})
@MultipartConfig(
        fileSizeThreshold = 10 * 1024 * 1024,
        maxFileSize = 1024 * 1024 * 100,
        maxRequestSize = 1024 * 1024 * 100
)
public class ManageAccountServlet extends HttpServlet {

    private final String accountInfoPage = "manage_account_info.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String action = request.getParameter("action");
            String[] errorList = new String[6];
            UserDAO uDao = new UserDAO();
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            RequestDispatcher rd = request.getRequestDispatcher(accountInfoPage);

            //change password
            if (action.equals("password")) {
                String curPassword = HashPassword.HashPassword(request.getParameter("currentPassword"));
                String newPassword = HashPassword.HashPassword(request.getParameter("newPassword"));
                String confNewPassword = HashPassword.HashPassword(request.getParameter("confNewPassword"));
                if (!curPassword.equals(user.getPassword())) //check match current password
                    errorList[0] = "Password does not match";
                if (!newPassword.equals(confNewPassword)) //check if new and conf equals
                    errorList[1] = "New password does not match";
                if (errorList.length == 0) {
                    uDao.changePassword(user.getEmail(), newPassword);
                    user.setPassword(newPassword);
                    session.setAttribute("user", user);
                }
                else
                    request.setAttribute("ERROR_LIST", errorList);
            }
            else if (action.equals("avatar")) {
                UploadFile upload = new UploadFile();
                String newAvatar = upload.getFileName(request.getPart("avatar"));
                if (newAvatar == null || newAvatar.isEmpty()) {
                    rd.forward(request, response);
                    return;
                }
                upload.deleteFile(request, user.getAvatarURL(), "images" + File.separator + "avatars");
                user.setAvatarURL(upload.uploadFile(request, request.getPart("avatar"), user));
                uDao.updateUser(user);
            }
            rd.forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (getServletContext().getAttribute("catList") == null || request.getSession().getAttribute("user") == null) {
            response.sendRedirect("WelcomePageServlet");
            return;
        }
        request.setAttribute("isActive", "mAcc");
        request.getRequestDispatcher(accountInfoPage).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("isActive", "mAcc");
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
