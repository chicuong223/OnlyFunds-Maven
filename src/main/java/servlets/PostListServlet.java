/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.TreeMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        PostDAO postDAO = new PostDAO();
        int count = 0;
        int pageIndex = 0;
        if (strPage == null)
            pageIndex = 1;
        else
            pageIndex = Integer.parseInt(strPage);
        int start = pageIndex * 8 - (8 - 1);
        int end = pageIndex * 8;
        LinkedHashMap<Post, Integer> postMap = new LinkedHashMap<>();
        if (action.equals("like")) {
            count = postDAO.countPosts();
            postMap = postDAO.getMostLikes(start, end);
        }
        else if (action.equals("free")) {
            count = postDAO.countFreePosts();
            ArrayList<Post> postList = postDAO.getFreePosts(start, end);
            for (Post post : postList) {
                int countLike = new PostLikeDAO().countPostLikeByPost(post);
                postMap.put(post, countLike);
            }
        }
        int endPage = count / 8;
        if (count % 8 != 0)
            endPage++;
        request.setAttribute("postList", postMap);
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

}
