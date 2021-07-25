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
import java.util.LinkedHashMap;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import map.UserCategoryMapDAO;
import post_management.comment.CommentDAO;
import post_management.like.PostLikeDAO;
import post_management.post.Post;
import post_management.post.PostDAO;
import subscription_management.tier.Tier;
import subscription_management.tier.TierDAO;
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
            PostLikeDAO likeDAO = new PostLikeDAO();
            CommentDAO cmtDAO = new CommentDAO();
            UserDAO userDAO = new UserDAO();
            PostDAO postDAO = new PostDAO();
            
            //pass data for creator list
            //category list
            ArrayList<Category> catList = cDao.getAllCategories();
            ArrayList<User> popularCreators = userDAO.getUsersMostSubscriber();
            HashMap<User, ArrayList<Category>> userCatMap = new HashMap<>();
            getServletContext().setAttribute("catList", catList);
            popularCreators.forEach(popularCreator -> {
                ArrayList<Category> lst = uDao.getCategoriesByUser(popularCreator);
                userCatMap.put(popularCreator, lst);
            });
            //get Posts
            int pageIndex = 0;
            String strPage = request.getParameter("page");
            if (strPage == null)
                pageIndex = 1;
            else
                pageIndex = Integer.parseInt(strPage);
            int start = pageIndex * 4 - (4 - 1);
            int end = pageIndex * 4;
            List<Post> postList = postDAO.getPosts(start, end);
            LinkedHashMap<Post, int[]> postMap = new LinkedHashMap<>();
            TierDAO tierDAO = new TierDAO();
            postList.forEach(post -> {
                int allowed = 0;
                List<Tier> tiers = tierDAO.getTiersByPost(post);
                int likeCount = likeDAO.countPostLikeByPost(post);
                int cmtCount = cmtDAO.countCommentsByPost(post.getPostId());
                if(tiers.size() <= 0)
                    allowed = 1;
                else
                    allowed = 0;
                int[] arr = {likeCount, cmtCount, allowed};
                postMap.put(post, arr);
            });
            request.setAttribute("postList", postMap);
            request.setAttribute("userList", userCatMap);
//                System.out.println(postMap.size());
            rd.forward(request, response);
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
