/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import user_management.user.UserDAO;
import user_management.user.User;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import utils.UploadFile;
import utils.HashPassword;

/**
 *
 * @author chiuy
 */
@WebServlet(name = "RegisterServlet", urlPatterns = {"/RegisterServlet"})
@MultipartConfig(
        fileSizeThreshold = 10 * 1024 * 1024,
        maxFileSize = 1024 * 1024 * 50,
        maxRequestSize = 1024 * 1024 * 100
)

public class RegisterServlet extends HttpServlet {

    private final String invalidPage = "register.jsp";

    private final String registerForm = "register.jsp";
    private final String verifyEmailPage = "SendEmailConfirmCode";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ClassNotFoundException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        
        String fname = request.getParameter("firstname");
        String lname = request.getParameter("lastname");
        String username = request.getParameter("username");
        String password = HashPassword.HashPassword(request.getParameter("password"));
        String confPass = HashPassword.HashPassword(request.getParameter("confPass"));
        String email = request.getParameter("email");
//        String avatarURL = new UploadFile().uploadFile(request);
//        response.getWriter().print(avatarURL);
        String avatarURL;
        avatarURL = new UploadFile().getFileName(request.getPart("avatar"));
        String url = invalidPage;
        boolean error = false;
        UserDAO dao = new UserDAO();
        boolean foundUser = dao.usernameCheck(username);
        boolean foundEmail = dao.emailCheck(email);

        //Check missing field
        String[] errorList = new String[6];
        if (fname == null || fname.trim().isEmpty()) {
            error = true;
            errorList[0] = "Missing first name";
        }
        if (lname == null || lname.trim().isEmpty()) {
            error = true;
            errorList[1] = "Missing last name";
        }
        if (username == null || username.trim().isEmpty()) {
            error = true;
            errorList[2] = "Missing username";
        } else {
            if (foundUser == true) {
//                request.setAttribute("ERROR_USERNAME_TAKEN", "Username has already been taken!");
                error = true;
                errorList[2] = "Username has already been taken!";
            }
        }
        if (email == null || email.trim().isEmpty()) {
            error = true;
            errorList[3] = "Missing email";
        } else {
            if (foundEmail == true) {
//                request.setAttribute("ERROR_EMAIL_TAKEN", "Email has already been registered!");
                error = true;
                errorList[3] = "Email has already been registered!";
            }
        }
        if (password == null || password.trim().isEmpty()) {
            error = true;
            errorList[4] = "Missing password";
        }
        if (confPass == null || confPass.trim().isEmpty()) {
            error = true;
            errorList[5] = "Re-enter password";
        } else if (!confPass.equals(password)) {
            error = true;
            errorList[5] = "Fail to confirm password";
        }
        
        User newUser = new User(username, password, fname, lname, email, avatarURL, false);
        if (error == false) {
            //if avatar is null, replace with a default one
            if (avatarURL.equals("")) {
                avatarURL = "defaultAvatar.png";
            }
            url = verifyEmailPage;
            HttpSession session = request.getSession();
            session.setAttribute("user", newUser);
            session.setAttribute("filepart", request.getPart("avatar"));
        } else {
            request.setAttribute("ERROR_LIST", errorList);
        }
        System.err.println("error: " + error);
        System.err.println("url: " + url);
        RequestDispatcher rd = request.getRequestDispatcher(url);
        HttpSession session = request.getSession();
        session.setAttribute("user", newUser);
        //request.setAttribute("user", newUser);
        rd.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
           response.sendRedirect(registerForm);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
