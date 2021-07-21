/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import category.Category;
import user_management.user.UserDAO;
import user_management.user.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import map.UserCategoryMapDAO;
import notification.Notification;
import notification.NotificationDAO;
import utils.HashPassword;

/**
 *
 * @author chiuy
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    private int attempt = 0;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        System.out.println(request.getHeader("referer"));
//        response.sendRedirect("login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = HashPassword.HashPassword(request.getParameter("password"));
        UserDAO dao = new UserDAO();
        NotificationDAO ntDAO = new NotificationDAO();
        UserCategoryMapDAO ucDao = new UserCategoryMapDAO();
        User user = dao.checkLogin(username, password);
        HttpSession session = request.getSession();
        String src = request.getHeader("referer");
        if(src.substring(src.lastIndexOf('/') + 1).contains("WelcomePage"))
            src ="homepage";
        if (request.getSession().getAttribute("dest") == null)
            request.getSession().setAttribute("dest", src);
        if (user == null) {
            attempt++;
            if (attempt >= 5) {
                session.setAttribute("LOGINERROR", "Too many login attempts. Please try again after 30 minutes");
                session.setMaxInactiveInterval(1800);
                attempt = 0;
            }
            request.setAttribute("LOGINERROR", "Wrong username or password");
            request.setAttribute("username", username);
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }
        if (request.getParameterValues("remember") != null) {
            Cookie cookie = new Cookie("user", user.getUsername());
            cookie.setMaxAge(30 * 24 * 3600);
            response.addCookie(cookie);
        }
        ArrayList<Category> userCatList = ucDao.getCategoriesByUser(user);
        List<Notification> unreadNotiList = ntDAO.getNotificationsByRecipient(user).stream().limit(10).collect(Collectors.toList());
        session.setAttribute("user", user);
        session.setAttribute("userCatList", userCatList);
        session.setAttribute("notiList", unreadNotiList);
        response.sendRedirect((String) session.getAttribute("dest"));
        session.removeAttribute("dest");
    }

}
