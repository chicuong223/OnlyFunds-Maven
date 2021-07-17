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
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import post_management.post.Post;
import post_management.post.PostDAO;
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
        request.getRequestDispatcher("main_page.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        PostDAO dao = new PostDAO();
        int start = Integer.parseInt(request.getParameter("start"));
        int end = Integer.parseInt(request.getParameter("end"));
        ArrayList<Post> lst = dao.getPostsThatUserCanView(user, start, end);
        for (Post post : lst)
            response.getWriter().write("<div class=\"col-3 mx-2 my-2 card\">\n"
                    + "<div class=\"card-header\">\n"
                    + "<p class=\"card-title\">" + post.getTitle() + "</p>\n"
                    + "</div>\n"
                    + "<div class=\"card-body overflow-hidden\" style=\"height: 150px\">\n"
                    + post.getDescription() + "\n"
                    + "</div>\n"
                    + "<div class=\"card-footer\">\n"
                    + "<a href=\"PostDetailServlet?id=" + post.getPostId() + "\">See more</a>\n"
                    + "</div>\n"
                    + "</div>");
    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
