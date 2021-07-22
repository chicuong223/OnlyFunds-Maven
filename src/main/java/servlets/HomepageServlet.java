/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
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
 * @author dungn
 */
@WebServlet(name = "HomepageServlet", urlPatterns = {"/homepage"})
public class HomepageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect("WelcomePageServlet");
            return;
        }
        PostDAO postDAO = new PostDAO();
        String strPage = request.getParameter("page");
        int pageIndex = 0;
        if (strPage == null)
            pageIndex = 1;
        else
            pageIndex = Integer.parseInt(strPage);
        int start = pageIndex * 8 - (8 - 1);
        int end = pageIndex * 8;
//        List<Post> postList = postDAO.getPosts(start, end);
        TreeMap<Post, int[]> postList = getPosts(user, start, end);
        int count = postDAO.countPosts();
        int endPage = count / 8;
        if (count % 8 != 0)
            endPage++;
        request.setAttribute("end", endPage);
        request.setAttribute("postList", postList);
        request.getRequestDispatcher("main_page.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    private TreeMap<Post, int[]> getPosts(User currUser, int start, int end) {
        PostDAO dao = new PostDAO();
        List<Post> postList = dao.getPosts(start, end);
        TreeMap<Post, int[]> postMap = new TreeMap<>();
        PostLikeDAO likeDAO = new PostLikeDAO();
        CommentDAO cmtDAO = new CommentDAO();
        postList.forEach(post -> {
            int allowed = checkTier(post, currUser);
            int likeCount = likeDAO.countPostLikeByPost(post);
            int cmtCount = cmtDAO.countCommentsByPost(post.getPostId());
            int[] value = {likeCount, cmtCount, allowed};
            postMap.put(post, value);
        });
//        for (Post post : postList) {
//            ArrayList<Tier> postTiers = tierDAO.getTiersByPost(post);
//            if (post.getUploader().getUsername().equalsIgnoreCase(currUser.getUsername()))
//                postMap.put(post, Boolean.TRUE);
//            else if (postTiers.size() <= 0)
//                postMap.put(post, Boolean.TRUE);
//            else if (postTiers.size() > 0) {
//                ArrayList<Tier> userTiers = tierDAO.getTiersBySubscription(currUser);
//                for (Tier postTier : postTiers) {
//                    boolean view = false;
//                    for (Tier userTier : userTiers)
//                        if (userTier.getTierId() == postTier.getTierId()) {
//                            view = true;
//                            break;
//                        }
//                    postMap.put(post, view);
//                }
//            }
//        }
        return postMap;
    }

    private int checkTier(Post post, User user) {
        TierDAO tierDAO = new TierDAO();
        List<Tier> userTiers = tierDAO.getTiersBySubscription(user);
        List<Tier> postTiers = tierDAO.getTiersByPost(post);
        if (postTiers.size() <= 0)
            return 1;
        else
            if (user == null)
                return 0;
            else if (user.getUsername().equalsIgnoreCase(post.getUploader().getUsername()))
                return 1;
        for (Tier userTier : userTiers)
            for (Tier postTier : postTiers)
                if (userTier.getTierId() == postTier.getTierId())
                    return 1;
        return 0;
    }
}
