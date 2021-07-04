/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.paypal.api.payments.Payment;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import subscription_management.tier.Tier;
import subscription_management.tier.TierDAO;
import user_management.user.User;
import utils.PaymentUtils;

/**
 *
 * @author chiuy
 */
@WebServlet(name = "SubscribeServlet", urlPatterns = {"/SubscribeServlet"})
public class SubscribeServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        TierDAO tierDAO = new TierDAO();
        int tierID = Integer.parseInt(request.getParameter("tierid"));
        Tier tier = tierDAO.getTierById(tierID);
        PaymentUtils paymentUtil = new PaymentUtils();
        User subscriber = (User) request.getSession().getAttribute("user");
        try {
            Payment definedPayment = paymentUtil.definePayment(tier, subscriber);
            String approvalLink = paymentUtil.createPayment(definedPayment);
            response.sendRedirect(approvalLink);
        }
        catch (Exception e) {
        }
    }

}
