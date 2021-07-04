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

/**
 *
 * @author chiuy
 */
@WebServlet(name = "DeleteTierServlet", urlPatterns = {"/DeleteTierServlet"})
public class DeleteTierServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int tierID = Integer.parseInt(request.getParameter("id"));
        TierDAO tierDAO = new TierDAO();
        Tier tier = tierDAO.getTierById(tierID);
        if(tier == null){
            request.setAttribute("tiererror", "Tier not found");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }
        tierDAO.deactivateTier(tier);
        response.sendRedirect("TierManagementServlet");
    }

}
