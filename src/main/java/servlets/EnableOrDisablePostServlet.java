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
@WebServlet(name = "EnableOrDisablePostServlet", urlPatterns = {"/enable_or_disable"})
public class EnableOrDisablePostServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = "WelcomePageServlet";
        boolean check = new ContextAndSessionCheck().checkContextAndSession(request);
        if (check) {
            response.sendRedirect(url);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        int postID = Integer.parseInt(request.getParameter("postID"));
        PostDAO dao = new PostDAO();
        Post post = dao.getPostByID(postID);
        if(action.equalsIgnoreCase("activate")){
            dao.activatePost(post);
        }
        else if(action.equalsIgnoreCase("deactivate")){
            dao.deactivatePost(post);
        }
        else{
            request.setAttribute("PARAMETER_ERROR", "Invalid parameter");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

}
