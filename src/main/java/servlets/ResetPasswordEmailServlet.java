/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import user_management.user.UserDAO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import static utils.EmailSender.SendEmail;
import static utils.HashPassword.HashPassword;

/**
 *
 * @author chiuy
 */
@WebServlet(name = "ForgotPasswordServlet", urlPatterns = {"/passwordEmail"})
public class ResetPasswordEmailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (request.getParameter("resend") != null) {
            session.invalidate();
        }
        request.getRequestDispatcher("password_reset.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        UserDAO dao = new UserDAO();
        boolean validEmail = dao.emailCheck(email);
        HttpSession session = request.getSession();
        if (validEmail == false) {
            request.setAttribute("EMAILNOTFOUNDERROR", "Could not found this email");
            request.getRequestDispatcher("password_reset.jsp").forward(request, response);
        } else {
            try {
                sendEmail(email, request);
            } catch (MessagingException ex) {
                Logger.getLogger(ResetPasswordEmailServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            long now = new Date().getTime();
            long creationTime = session.getCreationTime();
            long endTime = creationTime + session.getMaxInactiveInterval() * 1000;
            long remainingTime = (endTime - now - 60000) / 1000;
//            System.out.println(remainingTime);
            request.setAttribute("resendTime", remainingTime);
//            response.sendRedirect("password_reset_otp.jsp");
            request.getRequestDispatcher("password_reset_otp.jsp").forward(request, response);
        }
    }

    private void sendEmail(String email, HttpServletRequest request) throws MessagingException {
        int otpInt = new Random().nextInt(999999);
        String otp = String.format("%06d", otpInt);
        ArrayList emailList = new ArrayList();
        emailList.add(email);
        String body = ""
                + "Reset Password\n"
                + "Hello " + email + "\n"
                + "We have received a request to reset your password. Below is the OTP code for this action\n"
                + otp + "\n"
                + "If you did not request this, feel free to ignore this email";
        HttpSession session = request.getSession();
        session.setAttribute("OTP", otp);
        session.setAttribute("EMAIL", email);
        session.setMaxInactiveInterval(180);
        SendEmail(emailList, "Password Reset", body);
    }
}
