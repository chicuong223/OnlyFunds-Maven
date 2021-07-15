/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import post_management.comment.Comment;
import post_management.comment.CommentDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.function.Consumer;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import post_management.like.PostLike;
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
@WebServlet(name = "PostDetailServlet", urlPatterns = {"/PostDetailServlet"})
public class PostDetailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int postID = Integer.parseInt(request.getParameter("id"));
        PostDAO dao = new PostDAO();
        Post post = dao.getPostByID(postID);
        if (post == null) {
            request.setAttribute("posterror", "POST NOT FOUND");
//            response.sendError(404, "Post not found");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }
        CommentDAO cDAO = new CommentDAO();
        PostLikeDAO postLikeDAO = new PostLikeDAO();
        TierDAO tierDAO = new TierDAO();
        ArrayList<Tier> postTiers = tierDAO.getTiersByPost(post);
        // check if post includes any tier
        if (postTiers.size() > 0) {
            HttpSession session = request.getSession();
            User currentUser = (User) session.getAttribute("user");
            boolean cmp = false;
            if (currentUser == null) { //check user/visitor
                cmp = false;
            }
            //check if user is the uploader
            else if (currentUser.getUsername().equals(post.getUploader().getUsername())) { 
                cmp = true;
            }
            else { //check if user has alr subscribed
                ArrayList<Tier> userTiers = tierDAO.getTiersBySubscription(currentUser);
                for (Tier userTier : userTiers) {
                    for (Tier postTier : postTiers) {
                        if (userTier.getTierId() == postTier.getTierId()) {
                            cmp = true;
                            break;
                        }
                    }
                }

            }
            if (cmp == false) {
                request.setAttribute("tiererror", "You are not allowed to view this post");
            }
        }
        ArrayList<Comment> cmtList = cDAO.getCommentsByPost(postID);
        int postLikeCount = postLikeDAO.countPostLikeByPost(post);
        request.setAttribute("postLikeCount", postLikeCount);
        request.setAttribute("cmtList", cmtList);
        request.setAttribute("post", post);
        request.getRequestDispatcher("post.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

}
