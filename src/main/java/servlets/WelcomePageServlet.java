/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import category.Category;
import category.CategoryDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import map.UserCategoryMapDAO;
import post_management.comment.CommentDAO;
import post_management.like.PostLikeDAO;
import post_management.post.Post;
import post_management.post.PostDAO;
import user_management.user.User;
import user_management.user.UserDAO;

/**
 *
 * @author ASUS GAMING
 */
@WebServlet(name = "WelcomePageServlet", urlPatterns = {"/WelcomePageServlet"})
public class WelcomePageServlet extends HttpServlet {

    private static final String WELCOME_PAGE = "welcome_page.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String a = request.getParameter("action");
            RequestDispatcher rd = request.getRequestDispatcher(WELCOME_PAGE);
            CategoryDAO cDao = new CategoryDAO();
            //load page on startup
            if (a == null) {
                //pass data for creator list
                //category list
                ArrayList<Category> catList = cDao.getAllCategories();
                getServletContext().setAttribute("catList", catList);
                rd.forward(request, response);
                return;
            }
            int start = Integer.parseInt(request.getParameter("start"));
            int end = Integer.parseInt(request.getParameter("end"));
            PostDAO postDAO = new PostDAO();
            PostLikeDAO likeDAO = new PostLikeDAO();
            ArrayList<Post> postList = postDAO.getFreePosts(start, end);
            CommentDAO cmtDAO = new CommentDAO();
//            request.setAttribute("postList", postList);
//            request.getRequestDispatcher(WELCOME_PAGE).forward(request, response);
            postList.forEach(post -> {
                int likeCount = likeDAO.countPostLikeByPost(post);
                int cmtCount = cmtDAO.countCommentsByPost(post.getPostId());
                out.write(""
                        + "<div class=\"col-lg-3 mb-2\">\n"
                        + " <div class=\"card\" id=\"post\">\n"
                        + "     <div class=\"card-header p-2 pt-1\">\n"
                        + "         <h4 class=\"card-title fw-bold\">" + post.getTitle() + "</h4>\n"
                        + "         <h6 class=\"card-subtitle text-muted\" style=\"font-size: 16px;\">" + post.getUploader().getUsername() + "</h6>\n"
                        + "     </div>\n"
                        + "     <div class=\"card-body p-2 pt-1\">\n"
                        + "         <a href=\"PostDetailServlet?id=" + post.getPostId() + "\" class=\"stretched-link\"></a>\n"
                        + "         <p class=\"card-text\">" + post.getDescription() + "\n"
                        + "         </p>\n"
                        + "     </div>\n"
                        + "     <div class=\"card-footer p-2 pt-1 pb-1\">\n"
                        + "         <small><i class=\"fas fa-thumbs-up\"></i>" + likeCount + "</small>\n"
                        + "         <small><i class=\"fas fa-comment\"></i>" + cmtCount + "</small>\n"
                        + "         <small><i class=\"far fa-eye\"></i> 1234</small>\n"
                        + "     </div>\n"
                        + " </div>\n"
                        + "</div>");
            });
            System.out.println("Is Called");
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
