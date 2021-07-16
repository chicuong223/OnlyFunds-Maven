/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import user_management.user.User;
import user_management.user.UserDAO;

/**
 *
 * @author chiuy
 */
@WebServlet(name = "CreatorListServlet", urlPatterns = {"/CreatorListServlet"})
public class CreatorListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
            request.setAttribute("usererror", "User not logged in");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }
        if(action.equals("sub")){
            getSubscribedCreators(user, request, response);
            return;
        }
        if(action.equals("follow")){
            getFollowedCreators(user, request, response);
//            return;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    private static void getSubscribedCreators(User user, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        UserDAO userDAO = new UserDAO();
        ArrayList<User> lst = userDAO.getCreatorThatUserSubscribedTo(user);
        request.setAttribute("subList", lst);
        request.getRequestDispatcher("creator_list.jsp").forward(request, response);
    }
    
    private static void getFollowedCreators(User user, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        UserDAO userDAO = new UserDAO();
        ArrayList<User> lst = userDAO.getCreatorThatUserFollows(user);
        request.setAttribute("followList", lst);
        request.getRequestDispatcher("creator_list.jsp").forward(request, response);
    }
}
