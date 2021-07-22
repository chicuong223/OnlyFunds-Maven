/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
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

/**
 *
 * @author chiuy
 */
@WebServlet(name = "PostListServlet", urlPatterns = {"/PostListServlet"})
public class PostListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        String strPage = request.getParameter("page");
        String actionTitle = "";
        PostDAO postDAO = new PostDAO();
        CommentDAO cmtDAO = new CommentDAO();
        PostLikeDAO likeDAO = new PostLikeDAO();
        TierDAO tierDAO = new TierDAO();
        User user = (User) request.getSession().getAttribute("user");
        int count = 0;
        int pageIndex = 0;
        if (strPage == null)
            pageIndex = 1;
        else
            pageIndex = Integer.parseInt(strPage);
        int start = pageIndex * 8 - (8 - 1);
        int end = pageIndex * 8;
        List<Post> postList = new ArrayList<>();
//        LinkedHashMap<Post, Integer> postMap = new LinkedHashMap<>();
        if (action.equals("mostlikes")) {
            count = postDAO.countPostsThatHasLikes();
//            postMap = postDAO.getMostLikes(start, end);
//            postMap.forEach((p, c) -> System.out.println(p.getPostId()));
            postList = postDAO.getMostLikes(start, end);
            actionTitle = "Most Liked Posts";
        } 
        else if (action.equals("mostviews")) {
            count = postDAO.countPosts();
            postList = postDAO.getMostViews(start, end);
            actionTitle = "Most Viewed Posts";
        }
        else if (action.equals("free")) {
            count = postDAO.countFreePosts();
            postList = postDAO.getFreePosts(start, end);
            actionTitle = "Free Posts";
        }
        else if (action.equals("recent")) {
            actionTitle = "Recent Posts";
            count = postDAO.countPosts();
            postList = postDAO.getPosts(start, end);
        }
        int endPage = count / 8;
        if (count % 8 != 0)
            endPage++;

        //add like count, comment count into the map
        //check if the user is allowed to view the post
        int allowed = 0;
        LinkedHashMap<Post, int[]> postMap = new LinkedHashMap<>();
        for (Post post : postList) {
            List<Tier> postTiers = tierDAO.getTiersByPost(post);
            int likeCount = likeDAO.countPostLikeByPost(post);
            int cmtCount = cmtDAO.countCommentsByPost(post.getPostId());
            // if the post is free
            if (postTiers.size() <= 0)
                allowed = 1;
            else
                allowed = checkTier(postTiers, user);
            int[] value = {likeCount, cmtCount, allowed};
            postMap.put(post, value);
        }
        request.setAttribute("postList", postMap);
        request.setAttribute("actionTitle", actionTitle);
        request.setAttribute("end", endPage);
        request.setAttribute("action", action);
        request.getRequestDispatcher("posts.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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

    private int checkTier(List<Tier> postTiers, User user) {
        if (user == null)
            return 0;
        TierDAO tierDAO = new TierDAO();
        List<Tier> userTiers = tierDAO.getTiersBySubscription(user);
        for (Tier userTier : userTiers)
            for (Tier postTier : postTiers)
                if (userTier.getTierId() == postTier.getTierId())
                    return 1;
        return 0;
    }
}
