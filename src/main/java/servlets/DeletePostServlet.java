/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import post_management.post.Post;
import post_management.post.PostDAO;
import utils.ContextAndSessionCheck;

/**
 *
 * @author chiuy
 */
@WebServlet(name = "DeletePostServlet", urlPatterns = {"/DeletePostServlet"})
public class DeletePostServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int postID = Integer.parseInt(request.getParameter("postID"));
        PostDAO dao = new PostDAO();
        Post post = dao.getPostByID(postID);
        //if post not found -> redirect to error.jsp
        if(post == null){
            request.setAttribute("posterror", "Post Not Found");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }
        dao.deactivatePost(post);
        //After deactivating post, go to your posts page
        response.sendRedirect("YourPostsServlet");
    }

}
