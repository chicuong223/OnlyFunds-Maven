/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import post_management.comment.CommentDAO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import post_management.like.PostLikeDAO;
import post_management.post.Post;
import post_management.post.PostDAO;
import user_management.user.User;
import utils.ContextAndSessionCheck;

/**
 *
 * @author chiuy
 */
@WebServlet(name = "YourPostsServlet", urlPatterns
        = {
            "/YourPostsServlet"
        })
public class YourPostsServlet extends HttpServlet {

    private final int pageSize = 4;
    private int endPage = 0;
    private int count = 0;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("isActive", "mPost");
        //check session & context
        if (getServletContext().getAttribute("catList") == null || request.getSession().getAttribute("user") == null) {
            response.sendRedirect("WelcomePageServlet");
            return;
        }

        PostDAO dao = new PostDAO();
        User user = (User) request.getSession().getAttribute("user");
        PostLikeDAO likeDAO = new PostLikeDAO();
        String pageStr = request.getParameter("page");
        CommentDAO cDAO = new CommentDAO();
        String action = request.getParameter("action");
        if (action == null)
            action = "all";
        int pageIndex = 0;
        if (pageStr == null)
            pageIndex = 1;
        else
            pageIndex = Integer.parseInt(pageStr);
        List<Post> postList = new ArrayList<>();
        if (action.equalsIgnoreCase("all")) {
            postList = dao.getPostsByUser(user, pageIndex);
            count = dao.countPostsByUser(user);
        }
        else if (action.equalsIgnoreCase("active")) {
            postList = dao.getActivePostsByUser(user, pageIndex);
            count = dao.countActivePostsByUser(user);
        }
        else if (action.equalsIgnoreCase("disabled")) {
            count = dao.countInactivePostsByUser(user);
            postList = dao.getInactivePostsByUser(user, pageIndex);
        }
        TreeMap<Post, int[]> map = new TreeMap<>();
        for (Post post : postList) {
            int likeCount = likeDAO.countPostLikeByPost(post);
            int cmtCount = cDAO.countCommentsByPost(post.getPostId());
            int[] arr = {
                likeCount, cmtCount
            };
            map.put(post, arr);
        }
        endPage = count / pageSize;
        if (count % pageSize != 0)
            endPage++;
        request.setAttribute("postList", map);
        request.setAttribute("end", endPage);
        request.setAttribute("count", count);
        request.setAttribute("action", action);
        request.getRequestDispatcher("your_posts.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

}
