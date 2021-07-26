/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import category.Category;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import map.UserCategoryMapDAO;
import user_management.user.User;
import user_management.user.UserDAO;

/**
 *
 * @author ASUS GAMING
 */
@WebServlet(name = "FollowingListServlet", urlPatterns = {"/FollowingListServlet"})
public class FollowingListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (getServletContext().getAttribute("catList") == null || request.getSession().getAttribute("user") == null) {
            response.sendRedirect("WelcomePageServlet");
            return;
        }
        request.setAttribute("isActive", "follow");
        HttpSession session = request.getSession();
        UserDAO userDAO = new UserDAO();
        UserCategoryMapDAO userCateMap = new UserCategoryMapDAO();
        User userSession = (User) session.getAttribute("user");
        int page = 0;
        String strPage = request.getParameter("page");
        if (strPage == null)
            page = 1;
        else
            page = Integer.parseInt(strPage);
        int count = userDAO.countFollowing(userSession);
        int endPage;
        endPage = count / 8;
        if (count % 8 != 0)
            endPage++;
        int start = page * 8 - (8 - 1);
        int end = page * 8;
        List<User> userList = userDAO.getFollowingUsers(userSession, start, end);
        LinkedHashMap<User, List<Category>> userMap = new LinkedHashMap<>();
        userList.forEach(user -> {
            List<Category> cateList = userCateMap.getCategoriesByUser(user);
            userMap.put(user, cateList);
        });
        request.setAttribute("title", "Creators You Are Following");
        request.setAttribute("type", "follow");
        request.setAttribute("end", endPage);
        request.setAttribute("creatorList", userMap);
        request.getRequestDispatcher("creator_list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
