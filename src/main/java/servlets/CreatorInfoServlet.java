/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
 * @author chiuy
 */
@WebServlet(name = "CreatorInfoServlet", urlPatterns = {"/CreatorInfoServlet"})
public class CreatorInfoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        TierDAO tierDAO = new TierDAO();
        String username = request.getParameter("username");
        UserDAO userDAO = new UserDAO();
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("user");
        User creator = userDAO.getUserByUsername(username);
        if (creator == null) {
            creator = currentUser;
        }
        if (currentUser == null || !currentUser.getUsername().equalsIgnoreCase(creator.getUsername())) {
            getPosts(request, currentUser, creator);
            getTiersByCreator(creator, currentUser, request);
        }
        else {
            getOwnPost(request, creator);
        }
        request.setAttribute("creator", creator);
        request.getRequestDispatcher("creator_info.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getAttribute("tiererror") != null) {
            doGet(request, response);
        }
    }

    private void getTiersByCreator(User creator, User current, HttpServletRequest request) {
        TierDAO tierDAO = new TierDAO();
        ArrayList<Tier> creatorList = tierDAO.getTiersByUser(creator);
        //if user not logged in, shows all tiers by the creator
        if (current == null) {
            request.setAttribute("tiers", creatorList);
            return;
        }
        //if user is logged in
        //check if user has subscribed to any tier of the creator
        //if true => hide all tiers
        //else => show all tiers
        ArrayList<Tier> currentList = tierDAO.getTiersBySubscription(current);
        boolean result = true;
        outerLoop:
        for (Tier creatorTier : creatorList) {
            for (Tier tier : currentList) {
                if (creatorTier.getTierId() == tier.getTierId()) {
                    result = false;
                    break outerLoop;
                }
            }
        }
        if (result == false) {
            request.setAttribute("subscribed", "You have already subscribed to this user");
        }
        else {
            request.setAttribute("tiers", creatorList);
        }
    }

    private void getPosts(HttpServletRequest request, User currentUser, User creator) {
        PostDAO dao = new PostDAO();
        TierDAO tierDAO = new TierDAO();
        String pageStr = request.getParameter("page");
        int pageIndex = 0;
        if (pageStr == null) {
            pageIndex = 1;
        }
        else {
            pageIndex = Integer.parseInt(pageStr);
        }
        ArrayList<Post> postList = dao.getPostsByUserPage(creator, pageIndex);
        TreeMap<Post, Boolean> postMap = new TreeMap<>();
        if (currentUser == null) {
            for (Post post : postList) {
                ArrayList<Tier> postTiers = tierDAO.getTiersByPost(post);
                if (postTiers.size() > 0) {
                    postMap.put(post, false);
                }
                else {
                    postMap.put(post, true);
                }
            }
        }
        else if (currentUser.getUsername().equals(creator.getUsername())) {
            for (Post post : postList) {
                postMap.put(post, true);
            }
        }
        else {
            ArrayList<Tier> userTiers = tierDAO.getTiersBySubscription(currentUser);
            for (Post post : postList) {
                boolean cmp = false;
                ArrayList<Tier> postTiers = tierDAO.getTiersByPost(post);
                if (postTiers.size() > 0) {
                    for (Tier userTier : userTiers) {
                        for (Tier postTier : postTiers) {
                            if (userTier.getTierId() == postTier.getTierId()) {
                                cmp = true;
                                break;
                            }
                        }
                    }
                }
                else {
                    cmp = true;
                }
                postMap.put(post, cmp);
            }
        }
        int count = dao.countPostsByUser(creator);
        int pageSize = 3;
        int endPage = count / pageSize;
        if (count % pageSize != 0) {
            endPage++;
        }
        request.setAttribute("end", endPage);
        request.setAttribute("count", count);
        request.setAttribute("postList", postMap);
    }

    private void getOwnPost(HttpServletRequest request, User creator) {
        PostDAO dao = new PostDAO();
        PostLikeDAO likeDAO = new PostLikeDAO();
        String pageStr = request.getParameter("page");
        CommentDAO cDAO = new CommentDAO();
        int pageIndex = 0;
        if (pageStr == null) {
            pageIndex = 1;
        }
        else {
            pageIndex = Integer.parseInt(pageStr);
        }
        ArrayList<Post> postList = dao.getPostsByUserPage(creator, pageIndex);
        TreeMap<Post, int[]> map = new TreeMap<>();
        for (Post post : postList) {
            int likeCount = likeDAO.countPostLikeByPost(post);
            int cmtCount = cDAO.countCommentsByPost(post.getPostId());
            int[] arr = {
                likeCount, cmtCount
            };
            map.put(post, arr);
        }
        int pageSize = 3;
        int count = dao.countPostsByUser(creator);
        int endPage = count / pageSize;
        if (count % pageSize != 0) {
            endPage++;
        }
        request.setAttribute("CreatorInfoServletFlag", "flag");
        request.setAttribute("postList", map);
        request.setAttribute("end", endPage);
        request.setAttribute("count", count);
    }
}
