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
//        User user = (User) request.getSession().getAttribute("user");
//        PostDAO postDAO = new PostDAO();
//        List<Post> postList = postDAO.getPostsThatUserCanView(user).stream().limit(10).collect(Collectors.toList());
//        request.setAttribute("postList", postList);
//        UserDAO userDAO = new UserDAO();
//        List<User> subList = userDAO.getCreatorThatUserSubscribedTo(user).stream().limit(3).collect(Collectors.toList());
//        List<User> followList = userDAO.getCreatorThatUserFollows(user).stream().limit(3).collect(Collectors.toList());
//        List<User> userCatList = userDAO.getCreatorsSameCategoryAsUser(user).stream().limit(3).collect(Collectors.toList());
//        System.out.println(followList);
//        System.out.println(followList);
//        System.out.println(userCatList);
//        request.setAttribute("cateCreators", userCatList);
//        request.setAttribute("subCreators", subList);
//        request.setAttribute("followCreators", followList);
        if (request.getSession().getAttribute("user") == null) {
            response.sendRedirect("login");
            return;
        }
        request.getRequestDispatcher("main_page.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        User user = (User) request.getSession().getAttribute("user");
//        PostDAO dao = new PostDAO();
        PostLikeDAO likeDAO = new PostLikeDAO();
        CommentDAO cmtDAO = new CommentDAO();
        int start = Integer.parseInt(request.getParameter("start"));
        int end = Integer.parseInt(request.getParameter("end"));
//        ArrayList<Post> lst = dao.getPosts(start, end);
        TreeMap<Post, Boolean> postMap = getPosts(user, start, end);
        postMap.forEach((p, view) -> {
            int likeCount = likeDAO.countPostLikeByPost(p);
            int cmtCount = cmtDAO.countCommentsByPost(p.getPostId());
            out.write(""
                    + "<div class=\"col-lg-3 mb-2\">\n"
                    + "<div class=\"card\" id=\"post\">\n"
                    + "<a href=\"PostDetailServlet?id=" + p.getPostId() + "\" class=\"stretched-link\"></a>\n"
                    + "<div class=\"card-header p-2 pt-1\">\n"
                    + "<h4 class=\"card-title fw-bold\">" + p.getTitle() + "</h4>\n"
                    + "<h6 class=\"card-subtitle text-muted\" style=\"font-size: 16px;\">" + p.getUploader().getUsername() + "</h6>\n"
                    + "</div>\n"
                    + "<div class=\"card-body p-2 pt-1\">\n"
                    + "<a href=\"PostDetailServlet?id=" + p.getPostId() + "\" class=\"stretched-link\"></a>\n"
                    + "<p class=\"card-text\">\n"
                    + p.getDescription() + "\n"
                    + "</p>\n"
                    + "</div>\n"
                    + "<div class=\"card-footer p-2 pt-1 pb-1\">\n"
                    + "<small><i class=\"fas fa-thumbs-up\"></i>" + likeCount + "</small>\n"
                    + "<small><i class=\"fas fa-comment\"></i>" + cmtCount + "</small>\n"
                    + "<small><i class=\"far fa-eye\"></i> 1234</small>\n"
                    + "</div>\n"
                    + "</div>"
                    + "</div>");
        });
    }
//        for (Post post : lst)
//            response.getWriter().write("<div class=\"col-3 mx-2 my-2 card\">\n"
//                    + "<div class=\"card-header\">\n"
//                    + "<p class=\"card-title\">" + post.getTitle() + "</p>\n"
//                    + "</div>\n"
//                    + "<div class=\"card-body overflow-hidden\" style=\"height: 150px\">\n"
//                    + post.getDescription() + "\n"
//                    + "</div>\n"
//                    + "<div class=\"card-footer\">\n"
//                    + "<a href=\"PostDetailServlet?id=" + post.getPostId() + "\">See more</a>\n"
//                    + "</div>\n"
//                    + "</div>");

    private TreeMap<Post, Boolean> getPosts(User currUser, int start, int end) {
        PostDAO dao = new PostDAO();
        TierDAO tierDAO = new TierDAO();
        List<Post> postList = dao.getPosts(start, end);
        TreeMap<Post, Boolean> postMap = new TreeMap<>();
        for (Post post : postList) {
            ArrayList<Tier> postTiers = tierDAO.getTiersByPost(post);
            if (post.getUploader().getUsername().equalsIgnoreCase(currUser.getUsername()))
                postMap.put(post, Boolean.TRUE);
            if (postTiers.size() <= 0)
                postMap.put(post, Boolean.TRUE);
            if (postTiers.size() > 0) {
                ArrayList<Tier> userTiers = tierDAO.getTiersBySubscription(currUser);
                for (Tier postTier : postTiers) {
                    boolean view = false;
                    for (Tier userTier : userTiers)
                        if (userTier.getTierId() == postTier.getTierId()) {
                            view = true;
                            break;
                        }
                    postMap.put(post, view);
                }
            }
        }
        return postMap;
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
