/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.paypal.api.payments.PayerInfo;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.Transaction;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import subscription_management.subscription.SubscriptionDAO;
import subscription_management.tier.Tier;
import subscription_management.transaction.BillDAO;
import user_management.user.User;
import utils.PaymentUtils;

/**
 *
 * @author chiuy
 */
@WebServlet(name = "ExecutePaymentServlet", urlPatterns = {"/ExecutePaymentServlet"})
public class ExecutePaymentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if(request.getParameter("cancel") != null){
            response.sendRedirect("cancel_payment");
            return;
        }
        try {
            PaymentUtils paymentUtils = new PaymentUtils();
            Payment payment = paymentUtils.executePayment(request);
            PayerInfo payerInfo = payment.getPayer().getPayerInfo();
            Transaction transaction = payment.getTransactions().get(0);
            SubscriptionDAO subscriptionDAO = new SubscriptionDAO();
            BillDAO billDAO = new BillDAO();
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            Tier tier = (Tier) session.getAttribute("tier");
            subscriptionDAO.addSubscription(user, tier);
            billDAO.addBill(user, tier);
            session.removeAttribute("tier");
            request.setAttribute("tier", tier);
            request.setAttribute("payer", payerInfo);
            request.setAttribute("transaction", transaction);
            request.getRequestDispatcher("receipt.jsp").forward(request, response);
        }
        catch (IOException | ServletException e) {
            request.setAttribute("errorMsg", "Could not execute payment");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

}
