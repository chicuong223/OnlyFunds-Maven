/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import post_management.comment.Comment;
import post_management.comment.CommentDAO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import post_management.bookmark.BookmarkDAO;
import post_management.like.CommentLikeDAO;
import notification.Notification;
import notification.NotificationDAO;
import post_management.like.PostLikeDAO;
import post_management.post.Post;
import post_management.post.PostDAO;
import report.Report;
import report.ReportDAO;
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
        int postID = Integer.parseInt(request.getParameter("id"));
        
        //If user accesses the post via a notification
        //remove the notification from the notiList in session
        //set the notification is_read to true in the database
        if (strNotiID != null) {
            NotificationDAO notiDAO = new NotificationDAO();
            ArrayList<Notification> notiList = (ArrayList<Notification>) request.getSession().getAttribute("notiList");
            Notification notification = notiList.stream().filter(noti -> noti.getNotificationId() == Integer.parseInt(strNotiID)).findFirst().orElse(null);
            notiDAO.setIsRead(notification);
        }
        //get post info
        //if not found -> redirect to error page
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
                for (Tier userTier : userTiers)
                    for (Tier postTier : postTiers)
                        if (userTier.getTierId() == postTier.getTierId()) {
                            cmp = true;
                            break;
                        }
                        else if (currentUser.getUsername().equals(post.getUploader().getUsername()))
                            cmp = true;
            }
            if (cmp == false)
                request.setAttribute("tiererror", "You are not allowed to view this post");
        }
        if (currentUser != null) {
            boolean isPostLiked = postLikeDAO.CheckPostLike(currentUser.getUsername(), postID);
            request.setAttribute("isPostLiked", isPostLiked);
//            System.err.println(isPostLiked);
        }
        ArrayList<Comment> cmtList = cDAO.getCommentsByPost(postID);

        //true: has reported
        //false: not reported
        LinkedHashMap<Comment, Boolean> cmtMap = new LinkedHashMap<>();
        cmtList.forEach(comment -> {
            cmtMap.put(comment, Boolean.FALSE);
        });
        if (currentUser != null) {
            //check if user already liked post
            boolean isPostLiked = postLikeDAO.CheckPostLike(currentUser.getUsername(), postID);
            request.setAttribute("isPostLiked", isPostLiked);
            BookmarkDAO bmDAO = new BookmarkDAO();
            ReportDAO reportDAO = new ReportDAO();
            //check if user has bookmarked the post
            boolean isBookmarked = bmDAO.CheckBookmark(currentUser.getUsername(), postID);

            //check if user has reported the post
            List<Report> reportPosts = reportDAO.getReportsByObjIdAndType(String.valueOf(postID), "Post");
            List<Report> userReports = reportDAO.getReportsByUser(currentUser);
            boolean reported = false;
            OUTERLOOP:
            for (Report userReport : userReports)
                for (Report postReport : reportPosts)
                    if (postReport.getId() == userReport.getId()) {
                        reported = true;
                        break OUTERLOOP;
                    }

            //check for each comment, user has reported
            cmtList.forEach(cmt -> {
                List<Report> cmtReports = reportDAO.getReportsByObjIdAndType(String.valueOf(cmt.getCommentID()), "comment");
                OUTER:
                for (Report cmtReport : cmtReports)
                    for (Report userReport : userReports)
                        if (userReport.getId() == cmtReport.getId()) {
                            cmtMap.put(cmt, Boolean.TRUE);
                            break OUTER;
                        }
            });
            request.setAttribute("reported", reported);
            request.setAttribute("isBookmarked", isBookmarked);
        }
        //count likes of the post
        int postLikeCount = postLikeDAO.countPostLikeByPost(post);
        request.setAttribute("postLikeCount", postLikeCount);
//        request.setAttribute("cmtList", cmtList);
        request.setAttribute("post", post);

        //check if user already liked comment
        CommentLikeDAO clDAO = new CommentLikeDAO();
        ArrayList<Boolean> isCommnetLikedList = new ArrayList<>();
        ArrayList<Integer> countCommentLikeList = new ArrayList<>();
        cmtList.stream().map(comment -> {
            if (currentUser != null) {
                boolean isCommnetLiked = clDAO.CheckCommentLike(currentUser.getUsername(), comment.getCommentID());
                isCommnetLikedList.add(isCommnetLiked);
            }
            else
                isCommnetLikedList.add(Boolean.FALSE);
            return comment;
        }).map(comment -> clDAO.countCommentLikeByCommentId(comment.getCommentID())).forEachOrdered(countCommentLike -> {
            countCommentLikeList.add(countCommentLike);
        });
        
        // check if postID in postViewed AL
        // if false -> viewCount++ / add postID to AL
        ArrayList<Integer> postViewed = (ArrayList<Integer>) getServletContext().getAttribute("postViewed");
        postViewed.forEach((poID) -> {
            System.out.println(poID);
        });
        if (!postViewed.contains(postID)) {
            System.out.println(170);
            post.setViewCount(post.getViewCount()+1);
            postViewed.add(postID);
            dao.increaseView(post);
            getServletContext().setAttribute("postViewed", postViewed);
        }
        
        request.setAttribute("cmtList", cmtMap);
        request.setAttribute("isCommnetLikedList", isCommnetLikedList);
        request.setAttribute("countCommentLikeList", countCommentLikeList);
        request.getRequestDispatcher("post.jsp").forward(request, response);
    }
}
