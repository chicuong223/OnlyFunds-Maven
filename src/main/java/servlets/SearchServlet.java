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
import post_management.post.Post;
import post_management.post.PostDAO;
import user_management.user.User;
import user_management.user.UserDAO;

/**
 *
 * @author ASUS GAMING
 */
@WebServlet(name = "SearchServlet", urlPatterns = {"/SearchServlet"})
public class SearchServlet extends HttpServlet {

    final String SEARCHPAGE = "search_page.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String action = (String) request.getParameter("a");
        System.err.println("action"+action);
        if (action == null) {
            //write code for action==null
        } else {
            switch (action) {
                case "searchtag":
                    if (request.getParameter("id") == null) {
                        //write code for categoryID==null

                    } else {
                        int categoryID = Integer.parseInt(request.getParameter("id"));
                        System.err.println("id "+categoryID);
                        UserDAO uDAO = new UserDAO();
                        ArrayList<User> creatorInCategoryList = uDAO.getBestCreatorsInCategory(categoryID);
                        System.err.println("Num of users: " + creatorInCategoryList.size());
                        for (User user : creatorInCategoryList) {
                            System.err.println(user.getUsername());
                        }
                        request.setAttribute("userList", creatorInCategoryList);
                        PostDAO pDAO = new PostDAO();
                        ArrayList<Post> postInCategory = pDAO.getTopPostsInCategory(categoryID);
                        System.err.println("Num of posts: " + postInCategory.size());
                        for (Post post : postInCategory) {
                            System.err.println(post.getPostId() + " " + post.getTitle());
                        }
                        request.setAttribute("postList", postInCategory);
                        request.getRequestDispatcher(SEARCHPAGE).forward(request, response);
                    }
                    break;
                case "searchstring":
                    String searchedString = (String) request.getParameter("search");
                    if (searchedString == null) {
                        throw new ServletException("search string not found");
                    } else {
                        System.err.println("search: "+searchedString);
                        UserDAO uDAO = new UserDAO();
                        ArrayList<User> searchedUsers = uDAO.getSearchUser(searchedString);
                        System.err.println("Num of users: " + searchedUsers.size());
                        for (User searchedUser : searchedUsers) {
                            System.err.println(searchedUser.getUsername());
                        }
                        request.setAttribute("userList", searchedUsers);
                        PostDAO pDAO = new PostDAO();
                        ArrayList<Post> searchedPosts = pDAO.getSearchedPost(searchedString);
                        System.err.println("Num of posts: " + searchedPosts.size());
                        for (Post searchedPost : searchedPosts) {
                            System.err.println(searchedPost.getPostId() + " " + searchedPost.getTitle());
                        }
                        request.setAttribute("postList", searchedPosts);
                        request.getRequestDispatcher(SEARCHPAGE).forward(request, response);
                    }
                    break;
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
