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
import subscription_management.tier.Tier;
import subscription_management.tier.TierDAO;
import user_management.user.User;

/**
 *
 * @author chiuy
 */
@WebServlet(name = "AddTierServlet", urlPatterns = {"/AddTierServlet"})
public class AddTierServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        TierDAO tierDAO = new TierDAO();
        User currentUser = (User) request.getSession().getAttribute("user");
        ArrayList<Tier> tierList = tierDAO.getTiersByUser(currentUser);
        if(tierList.size() >= 5){
            request.setAttribute("tiererror", "You can create at most 5 tiers only");
            request.getRequestDispatcher("TierManagementServlet").forward(request, response);
            return;
        }
        request.getRequestDispatcher("tier_form.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String title = request.getParameter("title");
        String priceStr = request.getParameter("price");
        User creator = (User) request.getSession().getAttribute("user");
        String desc = request.getParameter("desc");
        boolean error = false;
        if (title.replaceAll("\\s", "").length() < 1) {
            error = true;
            request.setAttribute("titleError", "Title is required");
        }
        if (priceStr.replaceAll("\\s", "").length() < 1) {
            error = true;
            request.setAttribute("priceError", "Price is required");
        }
        TierDAO dao = new TierDAO();
        if (dao.getTierIDByUserAndTitle(creator, title) >= 0) {
            error = true;
            request.setAttribute("titleError", "This title already exists!");
            request.setAttribute("title", title);
            request.setAttribute("price", priceStr);
            request.setAttribute("desc", desc);
        }
        if (error == true) {
            request.getRequestDispatcher("tier_form.jsp").forward(request, response);
            return;
        }
        int price = Integer.parseInt(priceStr);
        Tier tier = new Tier(0, title, desc, creator, price, true);
        dao.addTier(tier);
        response.sendRedirect("TierManagementServlet");
    }

}
