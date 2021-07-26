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
import subscription_management.tier.Tier;
import subscription_management.tier.TierDAO;
import utils.ContextAndSessionCheck;

/**
 *
 * @author chiuy
 */
@WebServlet(name = "EditTierServlet", urlPatterns = {"/EditTierServlet"})
public class EditTierServlet extends HttpServlet {

    private Tier tier = null;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (getServletContext().getAttribute("catList") == null || request.getSession().getAttribute("user") == null) {
            response.sendRedirect("WelcomePageServlet");
            return;
        }
        String strTierID = request.getParameter("tierid");
        //check if request string is null
        if (strTierID == null) {
            request.setAttribute("tiererror", "Tier not found");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }
        TierDAO dao = new TierDAO();
        int tierID = Integer.parseInt(strTierID);
        tier = dao.getTierById(tierID);

        //check if tier ID exists in the database
        if (tier == null) {
            request.setAttribute("tiererror", "Tier not found");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }

        //check if the tier is active
        if (tier.isIsActive() == false) {
            request.setAttribute("tiererror", "This tier has been removed");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }
        request.setAttribute("tier", tier);
        request.setAttribute("title", tier.getTierTitle());
        request.setAttribute("price", tier.getPrice());
        request.setAttribute("desc", tier.getDescription());
        request.getRequestDispatcher("tier_form.jsp").forward(request, response);
        request.getRequestDispatcher("tier_form.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        TierDAO tierDAO = new TierDAO();
        String title = request.getParameter("title");
        boolean valid = true;
        if (title.trim().isEmpty()) {
            valid = false;
            request.setAttribute("titleError", "Title is required");
        }
        else if (!tier.getTierTitle().equalsIgnoreCase(title)) {
            int tierID = tierDAO.getTierIDByUserAndTitle(tier.getCreator(), title);
            if (tierID >= 0) {
                valid = false;
                request.setAttribute("titleError", "There is another tier that has the same title");
            }
        }
        String priceStr = request.getParameter("price");
        String desc = request.getParameter("desc");
        if (priceStr.trim().isEmpty()) {
            valid = false;
            request.setAttribute("priceError", "Price is required");
        }
        if (valid == false) {
            request.setAttribute("title", title);
            request.setAttribute("price", priceStr);
            request.setAttribute("desc", desc);
            request.setAttribute("tier", tier);
            request.getRequestDispatcher("tier_form.jsp").forward(request, response);
            return;
        }
        int price = Integer.parseInt(priceStr);
        tier.setDescription(desc);
        tier.setTierTitle(title);
        tier.setPrice(price);
        tierDAO.updateTier(tier);
        response.sendRedirect("CreatorInfoServlet");
    }
}
