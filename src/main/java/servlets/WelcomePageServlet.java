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
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import map.UserCategoryMapDAO;
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
            UserCategoryMapDAO uDao = new UserCategoryMapDAO();
            UserDAO userDAO = new UserDAO();
            //load page on startup
            if (a == null) {
                //pass data for creator list
                //category list
                ArrayList<Category> catList = cDao.getAllCategories();
//                ArrayList<User> popularCreators = userDAO.getUsersMostSubscriber();
                getServletContext().setAttribute("catList", catList);
//                request.setAttribute("userList", popularCreators);
                rd.forward(request, response);
            }
            else if (a.equals("load")) {
                int start = Integer.parseInt(request.getParameter("start"));
                int end = Integer.parseInt(request.getParameter("end"));
                PostDAO postDAO = new PostDAO();
                ArrayList<Post> postList = postDAO.getFreePosts(start, end);
//            request.setAttribute("postList", postList);
//            request.getRequestDispatcher(WELCOME_PAGE).forward(request, response);
                postList.forEach(post -> {
                    out.write("<div class=\"col-3 border mx-4 my-3\">\n"
                            + "<div class=\"card-header fw-bold\">\n"
                            + post.getTitle() + "\n"
                            + "</div>\n"
                            + "<div class=\"card-body overflow-auto\" style=\"height: 150px\">\n"
                            + post.getDescription() + "\n"
                            + "</div>\n"
                            + "<div class=\"card-footer\">\n"
                            + "<a href=\"PostDetailServlet?id=" + post.getPostId() + "\">See more</a>"
                            + "</div>\n"
                            + "</div>");
                });
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
