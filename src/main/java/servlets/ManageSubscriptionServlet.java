/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import subscription_management.subscription.Subscription;
import subscription_management.subscription.SubscriptionDAO;
import user_management.user.User;
import utils.ContextAndSessionCheck;

/**
 *
 * @author chiuy
 */
@WebServlet(name = "ManageSubscriptionServlet", urlPatterns = {"/ManageSubscriptions"})
public class ManageSubscriptionServlet extends HttpServlet {

    private final int pageSize = 8;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         request.setAttribute("isActive", "mSub");
        //check session & context
        String url = "WelcomePageServlet";
        boolean check = new ContextAndSessionCheck().checkContextAndSession(request);
        if (check) {
            response.sendRedirect(url);
            return;
        }
        
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return;
        }
        String strPage = request.getParameter("page");
        int page = 0;
        if(strPage == null)
            page = 1;
        else
            page = Integer.parseInt(strPage);
        SubscriptionDAO subDAO = new SubscriptionDAO();
        int count = subDAO.countSubscriptions(user);
        int start = page * pageSize - (pageSize - 1);
        int end = page * pageSize;
        int endPage = count / pageSize;
        if(count % pageSize != 0)
            endPage ++;
        List<Subscription> subList = subDAO.getSubscriptionsByUser(user, start, end);
        request.setAttribute("subList", subList);
        request.setAttribute("end", endPage);
        request.getRequestDispatcher("manage_subscriptions.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String strID = request.getParameter("id");
        if(strID == null){
            request.setAttribute("suberror", "Subscription not found");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }
        int subID = Integer.parseInt(strID);
        SubscriptionDAO subDAO = new SubscriptionDAO();
        Subscription sub = subDAO.getSubscriptionById(subID);
        if(sub == null){
            request.setAttribute("suberror", "Subscription not found");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }
        
        System.out.println(subDAO.cancelSub(sub));
        doGet(request, response);
    }
}
