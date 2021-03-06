/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import user_management.user.User;
import java.io.IOException;
import java.util.ArrayList;
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
import utils.EmailSender;

/**
 *
 * @author DELL
 */
@WebServlet(name = "SendEmailConfirmCode", urlPatterns = {"/SendEmailConfirmCode"})
public class SendEmailConfirmationCode extends HttpServlet {

    User newUser = null;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
//    int sessionTimeOut = 180;//180s
    String emailConfirmPage = "register_otp.jsp";//page addr to enter confirm code
    String emailErrorpage = "register.jsp";//page in case cannot send email 

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("action");
        HttpSession session=request.getSession();
//        session.setMaxInactiveInterval(sessionTimeOut);
        if ((User) session.getAttribute("user") != null) {
            newUser = (User) session.getAttribute("user");
            try {
                String otp = String.format("%06d", new Random().nextInt(999999));
                String emailAddr = newUser.getEmail();
                ArrayList<String> emailList = new ArrayList<String>();
                emailList.add(emailAddr);
                EmailSender.SendEmail(emailList, "OnlyFund email confirmation", "Your email confiration code is " + otp);
                session.setAttribute("otp", otp);
                //request.setAttribute("user", newUser);
                request.getRequestDispatcher(emailConfirmPage).forward(request, response);
            } catch (MessagingException ex) {
                Logger.getLogger(SendEmailConfirmationCode.class.getName()).log(Level.SEVERE, null, ex);
                request.getRequestDispatcher(emailErrorpage).forward(request, response);
            }
        }
        else {
            request.getRequestDispatcher(emailErrorpage).forward(request, response);
        }

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
