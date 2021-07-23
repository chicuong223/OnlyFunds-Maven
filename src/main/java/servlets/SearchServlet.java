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
import java.util.List;
import java.util.TreeMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
@WebServlet(name = "SearchServlet", urlPatterns = {"/SearchServlet"})
public class SearchServlet extends HttpServlet {

    final String SEARCHPAGE = "search_page.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("a");
        String title = "";
        String type = request.getParameter("type");
        if (type == null)
            type = "post";
        if (action == null || action.trim().isEmpty()) {
            System.out.println(action);
            //write code for action==null
            return;
        }
        if (action.equals("searchtag"))
            if (request.getParameter("id") == null) {
                //write code for categoryID==null
                request.setAttribute("categoryerror", "Category Not Found");
                request.getRequestDispatcher(SEARCHPAGE).forward(request, response);
                return;
            }
            else {
                int categoryID = Integer.parseInt(request.getParameter("id"));
                CategoryDAO catDAO = new CategoryDAO();
                Category searchedCat = catDAO.getCategoryByID(categoryID);
                if (searchedCat == null) {
                    request.setAttribute("categoryerror", "Category Not Found");
                    request.getRequestDispatcher(SEARCHPAGE).forward(request, response);
                    return;
                }
                if (type.equals("post")) {
                    PostDAO pDAO = new PostDAO();
                    PostLikeDAO likeDAO = new PostLikeDAO();
                    CommentDAO cmtDAO = new CommentDAO();
                    List<Post> postInCategory = pDAO.getSearchCatPost(searchedCat);
                    TreeMap<Post, int[]> postMap = new TreeMap<>();
                    title = "Posts with category: " + searchedCat.getCategoryName();
                    for (Post post : postInCategory) {
                        int likeCount = likeDAO.countPostLikeByPost(post);
                        int cmtCount = cmtDAO.countCommentsByPost(post.getPostId());
                        int allowed = checkTiers(post, request);
                        int[] value = {likeCount, cmtCount, allowed};
                        postMap.put(post, value);
                    }
                    request.setAttribute("postList", postMap);
                }
                else if (type.equals("creator")) {
                    UserDAO uDAO = new UserDAO();
                    List<User> creatorInCategoryList = uDAO.getSearchCatUser(searchedCat);
                    title = "Creators with category: " + searchedCat.getCategoryName();
                    request.setAttribute("userList", creatorInCategoryList);
                }
                else {
                    request.setAttribute("categoryerror", "Invalid Parameter");
                    request.getRequestDispatcher(SEARCHPAGE).forward(request, response);
                    return;
                }
                request.setAttribute("id", searchedCat.getCategoryId());
            }
        else if (action.equals("searchstring")) {
            String searchedString = request.getParameter("search");
            if (searchedString == null || searchedString.trim().isEmpty()) {
                response.sendRedirect(request.getHeader("Referer"));
                return;
            }
            else if (type.equals("creator")) {
                UserDAO uDAO = new UserDAO();
                List<User> searchedUsers = uDAO.getSearchUser(searchedString);
                title = "Creators with keyword: " + searchedString;
                request.setAttribute("userList", searchedUsers);
            }
            else if (type.equals("post")) {
                PostDAO pDAO = new PostDAO();
                PostLikeDAO likeDAO = new PostLikeDAO();
                CommentDAO cmtDAO = new CommentDAO();
                List<Post> searchedPosts = pDAO.getSearchPost(searchedString);
                title = "Posts with keyword: " + searchedString;
                TreeMap<Post, int[]> postMap = new TreeMap<>();
                for (Post post : searchedPosts) {
                    int likeCount = likeDAO.countPostLikeByPost(post);
                    int cmtCount = cmtDAO.countCommentsByPost(post.getPostId());
                    int allowed = checkTiers(post, request);
                    int[] value = {likeCount, cmtCount, allowed};
                    postMap.put(post, value);
                }
                request.setAttribute("postList", postMap);
            }
            request.setAttribute("search", searchedString);
        }
        request.setAttribute("type", type);
        request.setAttribute("title", title);
        request.setAttribute("action", action);
        request.getRequestDispatcher(SEARCHPAGE).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    private int checkTiers(Post post, HttpServletRequest request) {
        TierDAO tierDAO = new TierDAO();
        List<Tier> tiers = tierDAO.getTiersByPost(post);
        if (tiers.size() <= 0)
            return 1;
        User user = (User) request.getSession().getAttribute("user");
        if (user == null)
            return 0;
        List<Tier> userTiers = tierDAO.getTiersByUser(user);
        for (Tier userTier : userTiers)
            for (Tier postTier : tiers)
                if (userTier.getTierId() == postTier.getTierId())
                    return 1;
        return 0;
    }

}
