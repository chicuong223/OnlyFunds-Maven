/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import post_management.comment.CommentDAO;
import post_management.post.PostDAO;
import user_management.user.User;
import user_management.user.UserDAO;

/**
 *
 * @author DELL
 */
@WebServlet(name = "StaffSearchUserServlet", urlPatterns = {"/StaffSearchUserServlet"})
public class StaffSearchUserServlet extends HttpServlet {
    
    final int numReportInPage = 5;
    final String noSearchPage = "StaffUserListServlet";
    final String searchPage = "staffUserList.jsp";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String searchedString = request.getParameter("search");
        if (searchedString == null || searchedString.trim().isEmpty()) {
            request.getRequestDispatcher(noSearchPage).forward(request, response);
            return;
        } else {
            UserDAO uDAO = new UserDAO();
            int pageNum = 1;
            if (request.getParameter("page") != null) {
                pageNum = Integer.parseInt(request.getParameter("page"));
            }
            request.setAttribute("page", pageNum);
            request.setAttribute("isBanned", "all");
            ArrayList<User> userList = uDAO.getStaffSearchUser(searchedString);
            System.err.println("full list size: "+userList.size());
            int numPage = userList.size() / numReportInPage
                    + (userList.size() % numReportInPage == 0 ? 0 : 1);
            request.setAttribute("numPage", numPage);
            
            int startIndex = (pageNum - 1) * numReportInPage;
            int endIndex = pageNum * numReportInPage;
            endIndex = (endIndex > userList.size() ? userList.size() : endIndex);
            ArrayList<User> subArray = new ArrayList<User>(userList.subList(startIndex, endIndex));
            PostDAO pDAO = new PostDAO();
            CommentDAO cDAO = new CommentDAO();
            ArrayList<Integer> violationNumList = new ArrayList<>();
            for (User user : subArray) {
                System.err.println("user: "+user);
                int PostViolationNum = pDAO.CountReportedPostsByUser(user);
                int CommentViolationNum = cDAO.CountReportedCommentsByUser(user);
                int UserViolationNum = uDAO.CountReportedUserByUser(user);
                int violtionNum = CommentViolationNum + PostViolationNum + UserViolationNum;
                violationNumList.add(violtionNum);
            }
            request.setAttribute("violationNumList", violationNumList);
            request.setAttribute("userList", subArray);
            request.setAttribute("search", searchedString);
            request.getRequestDispatcher(searchPage).forward(request, response);
            return;
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
