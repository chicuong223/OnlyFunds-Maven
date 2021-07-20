/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import post_management.comment.Comment;
import post_management.comment.CommentDAO;
import post_management.post.Post;
import post_management.post.PostDAO;
import report.Report;
import report.ReportDAO;

/**
 *
 * @author DELL
 */
@WebServlet(name = "ReportDetailServlet", urlPatterns = {"/ReportDetailServlet"})
public class ReportDetailServlet extends HttpServlet {

    final String reportDetailPage = "report_detail.jsp";
    final String reportListPage = "reportList.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.err.println("Call ReportDetailServlet");
        String id = request.getParameter("id");
        if (id == null) {
            request.getRequestDispatcher(reportListPage).forward(request, response);
        } else {
            System.err.println("id " + id);
            ReportDAO rDAO = new ReportDAO();
            Report report = rDAO.getReportById(id);
            if (report == null) {
                request.getRequestDispatcher(reportListPage).forward(request, response);
            } else {
                System.err.println("report title" + report.getTitle());
                request.setAttribute("report", report);
                ArrayList<Report> similiar = rDAO.getReportsByObjIdAndType(report.getReportedObjectId().toString(), report.getType());
                similiar.removeIf(x -> x.getId() == Integer.parseInt(id));
                request.setAttribute("otherReports", similiar);
                if (report.getType().equals("user")) {

                } else if (report.getType().equals("post")) {
                    PostDAO pDAO = new PostDAO();
                    Post reportedP = pDAO.getPostByID(Integer.parseInt(report.getReportedObjectId()));
                    request.setAttribute("post", reportedP);
                } else if (report.getType().equals("comment")) {
                    CommentDAO cDAO = new CommentDAO();
                    Comment reportedC = cDAO.getCommentByID(Integer.parseInt(report.getReportedObjectId()));
                    request.setAttribute("comment", reportedC);
                    PostDAO pDAO = new PostDAO();
                    Post reportedP = pDAO.getPostByID(Integer.parseInt(report.getReportedObjectId()));
                    request.setAttribute("post", reportedP);
                }
                request.getRequestDispatcher(reportDetailPage).forward(request, response);
            }
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
