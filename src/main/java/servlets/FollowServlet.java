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
import user_management.follow.FollowDAO;
import user_management.user.User;
import user_management.user.UserDAO;
import utils.ContextAndSessionCheck;

/**
 *
 * @author chiuy
 */
@WebServlet(name = "FollowServlet", urlPatterns = {"/follow_manage"})
public class FollowServlet extends HttpServlet {

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
        String creatorName = request.getParameter("creator");
        if(action == null)
            return;
        UserDAO userDAO = new UserDAO();
        User creator = userDAO.getUserByUsername(creatorName);
        if(creator == null)
            return;
        FollowDAO dao = new FollowDAO();
        User user = (User)request.getSession().getAttribute("user");
        if(user == null)
            return;
        if(action.equals("add"))
            dao.addFollow(user, creator);
        else if(action.equals("delete"))
            dao.deleteFollow(user, creator);
    }

}
