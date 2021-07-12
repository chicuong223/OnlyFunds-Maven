/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.sql.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import post_management.comment.Comment;
import post_management.comment.CommentDAO;
import post_management.post.Post;
import post_management.post.PostDAO;
import user_management.user.User;

/**
 *
 * @author chiuy
 */
@WebServlet(name = "WriteCommentServlet", urlPatterns = {"/WriteCommentServlet"})
public class WriteCommentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String strPostId = request.getParameter("postId");
        if(strPostId == null){
            request.setAttribute("posterror", "Post not found");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }
        int postID = Integer.parseInt(strPostId);
        Post post = new PostDAO().getPostByID(postID);
        String content = request.getParameter("content");
        Date commentDate = new Date(System.currentTimeMillis());
        User currentUser = (User) request.getSession().getAttribute("user");
        Comment cmt = new Comment(0, currentUser, post, content, commentDate, true);
        CommentDAO cmtDAO = new CommentDAO();
        System.out.println(cmtDAO.addComment(cmt));
        response.sendRedirect("PostDetailServlet?id=" + post.getPostId());
    }

}
