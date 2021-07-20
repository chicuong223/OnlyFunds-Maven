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
@WebServlet(name = "SearchServlet", urlPatterns = {"/SearchServlet"})
public class SearchServlet extends HttpServlet {

    final String SEARCHPAGE = "search_page.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String action = (String) request.getParameter("a");
        System.err.println("action" + action);
        if (action == null) {
            //write code for action==null
        } else {
            switch (action) {
                case "searchtag":
                    if (request.getParameter("id") == null) {
                        //write code for categoryID==null

                    } else {
                        int categoryID = Integer.parseInt(request.getParameter("id"));
                        UserDAO uDAO = new UserDAO();
                        ArrayList<User> creatorInCategoryList = uDAO.getBestCreatorsInCategory(categoryID);
                        ArrayList<Integer> numSubscriberList = new ArrayList<Integer>();
                        ArrayList<ArrayList<Category>> cateListList=new ArrayList<ArrayList<Category>>();
                        UserCategoryMapDAO cateDAO=new UserCategoryMapDAO();
                        for (User user : creatorInCategoryList) {
                            int numsubscriber = uDAO.getSubscribers(user).size();
                            numSubscriberList.add(numsubscriber);
                            ArrayList<Category>cateList= cateDAO.getCategoriesByUser(user);
                            System.err.println("cateList size"+ cateList.size());
                            cateListList.add(cateList);
                        }
                        request.setAttribute("numSubscriberList", numSubscriberList);
                        request.setAttribute("cateListList", cateListList);
                        request.setAttribute("userList", creatorInCategoryList);
                        PostDAO pDAO = new PostDAO();
                        ArrayList<Post> postInCategory = pDAO.getTopPostsInCategory(categoryID);
                        ArrayList<Integer> numLikeList=new ArrayList<Integer>();
                        ArrayList<Integer> numCommentList=new ArrayList<Integer>();
                        PostLikeDAO lDAO=new PostLikeDAO();
                        CommentDAO cmtDAO=new CommentDAO();
                        for (Post post : postInCategory) {
                            int numLike=lDAO.countPostLikeByPost(post);
                            numLikeList.add(numLike);
                            int numCmt=cmtDAO.countCommentsByPost(post.getPostId());
                            numCommentList.add(numCmt);
                        }
                        request.setAttribute("numLikeList", numLikeList);
                        request.setAttribute("numCommentList", numCommentList);
                        request.setAttribute("postList", postInCategory);
                        request.getRequestDispatcher(SEARCHPAGE).forward(request, response);
                    }
                    break;
                case "searchstring":
                    String searchedString = (String) request.getParameter("search");
                    if (searchedString == null) {
                        throw new ServletException("search string not found");
                    } else {
                        UserDAO uDAO = new UserDAO();
                        ArrayList<User> searchedUsers = uDAO.getSearchUser(searchedString);
                        ArrayList<Integer> numSubscriberList = new ArrayList<Integer>();
                        ArrayList<ArrayList<Category>> cateListList=new ArrayList<ArrayList<Category>>();
                        UserCategoryMapDAO cDAO=new UserCategoryMapDAO();
                        for (User user : searchedUsers) {
                            int numsubscriber = uDAO.getSubscribers(user).size();
                            numSubscriberList.add(numsubscriber);
                            ArrayList<Category>cateList= cDAO.getCategoriesByUser(user);
                            cateListList.add(cateList);
                        }
                        request.setAttribute("numSubscriberList", numSubscriberList);
                        request.setAttribute("cateListList", cateListList);
                        request.setAttribute("userList", searchedUsers);
                        PostDAO pDAO = new PostDAO();
                        ArrayList<Post> searchedPosts = pDAO.getSearchedPost(searchedString);
                        ArrayList<Integer> numLikeList=new ArrayList<Integer>();
                        ArrayList<Integer> numCommentList=new ArrayList<Integer>();
                        PostLikeDAO lDAO=new PostLikeDAO();
                        CommentDAO cmtDAO=new CommentDAO();
                        for (Post post : searchedPosts) {
                            int numLike=lDAO.countPostLikeByPost(post);
                            numLikeList.add(numLike);
                            int numCmt=cmtDAO.countCommentsByPost(post.getPostId());
                            numCommentList.add(numCmt);
                        }
                        request.setAttribute("numLikeList", numLikeList);
                        request.setAttribute("numCommentList", numCommentList);
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
