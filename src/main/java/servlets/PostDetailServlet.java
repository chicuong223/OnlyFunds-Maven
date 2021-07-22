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
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import static java.util.stream.Collectors.toSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import post_management.bookmark.Bookmark;
import post_management.bookmark.BookmarkDAO;
import post_management.like.CommentLikeDAO;
import notification.Notification;
import notification.NotificationDAO;
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
        String strNotiID = request.getParameter("noti");
        HttpSession session = request.getSession();
        //If user accesses the post via a notification
        //remove the notification from the notiList in session
        //set the notification is_read to true in the database
        if (strNotiID != null) {
            NotificationDAO notiDAO = new NotificationDAO();
            ArrayList<Notification> notiList = (ArrayList<Notification>) request.getSession().getAttribute("notiList");
            Notification notification = notiList.stream().filter(noti -> noti.getNotificationId() == Integer.parseInt(strNotiID)).findFirst().orElse(null);
//            notiList.remove(notification);
            //remove the old list from session, replace with the modified one
//            session.removeAttribute("notiList");
//            session.setAttribute("notiList", notiList);
            notiDAO.setIsRead(notification);
        }
        //get post info
        //if not found -> redirect to error page
        int postID = Integer.parseInt(request.getParameter("id"));
        PostDAO dao = new PostDAO();
        Post post = dao.getPostByID(postID);
        if (post == null) {
            request.setAttribute("posterror", "POST NOT FOUND");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }
        CommentDAO cDAO = new CommentDAO();
        PostLikeDAO postLikeDAO = new PostLikeDAO();
        TierDAO tierDAO = new TierDAO();
        //get tiers of the post
        ArrayList<Tier> postTiers = tierDAO.getTiersByPost(post);
        // check if post includes any tier
        User currentUser = (User) session.getAttribute("user");
        if (postTiers.size() > 0) {
            boolean cmp = false;
            if (currentUser == null) //check user/visitor
                cmp = false;
            //check if user is the uploader
            else if (currentUser.getUsername().equals(post.getUploader().getUsername()))
                cmp = true;
            else { //check if user has alr subscribed
                ArrayList<Tier> userTiers = tierDAO.getTiersBySubscription(currentUser);
                for (Tier userTier : userTiers) {
                    for (Tier postTier : postTiers)
                        if (userTier.getTierId() == postTier.getTierId()) {
                            cmp = true;
                            break;
                        }
                        else if (currentUser.getUsername().equals(post.getUploader().getUsername()))
                            cmp = true;
                }
            }
            if (cmp == false)
                request.setAttribute("tiererror", "You are not allowed to view this post");
        }
        if (currentUser != null) {
            boolean isPostLiked = postLikeDAO.CheckPostLike(currentUser.getUsername(), postID);
            request.setAttribute("isPostLiked", isPostLiked);
            System.err.println(isPostLiked);
        }
        ArrayList<Comment> cmtList = cDAO.getCommentsByPost(postID);
        //count likes of the post
        int postLikeCount = postLikeDAO.countPostLikeByPost(post);
        request.setAttribute("postLikeCount", postLikeCount);
        request.setAttribute("cmtList", cmtList);
        request.setAttribute("post", post);

        if (currentUser != null) {
            //check if user already liked post
            boolean isPostLiked = postLikeDAO.CheckPostLike(currentUser.getUsername(), postID);
            request.setAttribute("isPostLiked", isPostLiked);
            //check if user already
            BookmarkDAO bmDAO = new BookmarkDAO();
            boolean isBookmarked = bmDAO.CheckBookmark(currentUser.getUsername(), postID);
            request.setAttribute("isBookmarked", isBookmarked);
        }
        //check if user already liked comment
        CommentLikeDAO clDAO = new CommentLikeDAO();
        ArrayList<Boolean> isCommnetLikedList = new ArrayList<Boolean>();
        ArrayList<Integer> countCommentLikeList = new ArrayList<Integer>();
        for (Comment comment : cmtList) {
            if (currentUser != null) {
                boolean isCommnetLiked = clDAO.CheckCommentLike(currentUser.getUsername(), comment.getCommentID());
                isCommnetLikedList.add(isCommnetLiked);
            } else {
                isCommnetLikedList.add(Boolean.FALSE);
            }
            int countCommentLike = clDAO.countCommentLikeByCommentId(comment.getCommentID());
            countCommentLikeList.add(countCommentLike);
        }
        request.setAttribute("isCommnetLikedList", isCommnetLikedList);
        request.setAttribute("countCommentLikeList", countCommentLikeList);
        request.getRequestDispatcher("post.jsp").forward(request, response);
    }
}
