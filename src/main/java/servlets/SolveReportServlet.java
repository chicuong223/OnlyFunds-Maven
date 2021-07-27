/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import authority_management.staff.Staff;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import post_management.comment.Comment;
import post_management.comment.CommentDAO;
import post_management.post.Post;
import post_management.post.PostDAO;
import report.ReportDAO;
import user_management.user.User;
import user_management.user.UserDAO;
import utils.EmailSender;

@WebServlet(name = "SolveReportServlet", urlPatterns = {"/SolveReportServlet"})
public class SolveReportServlet extends HttpServlet {

    final String reportListPage = "ReportListServlet";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getSession().getAttribute("staff") == null && request.getSession().getAttribute("admin") == null) {
            response.sendRedirect("WelcomePageServlet");
            return;
        }
        HttpSession session = request.getSession();
        Staff staff = (Staff) session.getAttribute("staff");
        System.err.println("current staff is null?");
        System.err.println(session.getAttribute("staff") == null);
        String objId = request.getParameter("objId");
        String type = request.getParameter("type");
        String status = request.getParameter("status");
        ReportDAO rDAO = new ReportDAO();

        if (status.equals("declined")) {
            rDAO.declineReports(objId, type, staff);
            if (type.equals("post")) {
                PostDAO pDAO = new PostDAO();
                Post reportedP = pDAO.getPostByID(Integer.parseInt(objId));
                pDAO.activatePost(reportedP);
            } else if (type.equals("comment")) {
                CommentDAO cDAO = new CommentDAO();
                Comment reportedC = cDAO.getCommentByID(Integer.parseInt(objId));
                cDAO.activateComment(reportedC.getCommentID());
            } else if (type.equals("user")) {

            }
        } else {
            rDAO.approveReports(objId, type, staff);
            User reportedUser=new User();
            PostDAO pDAO = new PostDAO();
            CommentDAO cDAO = new CommentDAO();
            UserDAO uDAO = new UserDAO();
            if (type.equals("post")) {
                Post reportedP = pDAO.getPostByID(Integer.parseInt(objId));
                pDAO.deactivatePost(reportedP);
                reportedUser = reportedP.getUploader();
            } else if (type.equals("comment")) {
                Comment reportedC = cDAO.getCommentByID(Integer.parseInt(objId));
                cDAO.deactivateComment(reportedC.getCommentID());
                reportedUser = reportedC.getUser();
            } else if (type.equals("user")) {
                reportedUser = uDAO.getUserByUsername(objId);
            }
            int approvedReportedPostsNum = pDAO.CountReportedPostsByUser(reportedUser);
            int approvedReportedCommentsNum = cDAO.CountReportedCommentsByUser(reportedUser);
            int approvedReportedUsersNum= uDAO.CountReportedUserByUser(reportedUser);
            int sumApproved=approvedReportedCommentsNum+approvedReportedPostsNum+approvedReportedUsersNum;
            if(sumApproved>=3){
                if(uDAO.banUser(reportedUser)){
                    ArrayList<String> toEmails=new ArrayList<String>();
                    toEmails.add(reportedUser.getEmail());
                    try { 
                        EmailSender.SendEmail(toEmails, "Ban account", 
                                "Your account has been banned due to violations to our Terms of Service\nFor further information, please contact swpacckhoi@gmail.com.");
                    } catch (MessagingException ex) {
                        Logger.getLogger(SolveReportServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        request.getRequestDispatcher(reportListPage).forward(request, response);
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
