/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import user_management.user.UserDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import static utils.HashPassword.HashPassword;

/**
 *
 * @author chiuy
 */
@WebServlet(name = "ResetPasswordReset", urlPatterns = {"/passwordReset"})
public class ResetPasswordServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String otp = request.getParameter("otp");
        HttpSession session = request.getSession();
        if (otp != null && session.getAttribute("OTP") != null) {
            if (otp.equals(session.getAttribute("OTP").toString())) {
                request.setAttribute("flag", "validOTP");
                session.setMaxInactiveInterval(60 * 15);
                request.getRequestDispatcher("password_reset.jsp").forward(request, response);
            } else {
                request.setAttribute("INVALIDOTP", "Invalid OTP");
                long now = new Date().getTime();
                long creationTime = session.getCreationTime();
                long endTime = creationTime + session.getMaxInactiveInterval() * 1000;
                long remainingTime = (endTime - now) / 1000;
                System.out.println(remainingTime);
                request.setAttribute("resendTime", remainingTime);
                request.getRequestDispatcher("password_reset_otp.jsp").forward(request, response);
            }
            return;
        }
        String password = request.getParameter("newPass");
//        response.getWriter().println(password);
        String hashedPassword = HashPassword(password);
        String email = (String) request.getSession().getAttribute("EMAIL");
        UserDAO dao = new UserDAO();
        dao.changePassword(email, hashedPassword);
        session.invalidate();
        response.sendRedirect("password_reset_success.html");
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
