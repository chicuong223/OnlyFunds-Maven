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
        try {
            PaymentUtils paymentUtils = new PaymentUtils();
            Payment payment = paymentUtils.executePayment(request);
            PayerInfo payerInfo = payment.getPayer().getPayerInfo();
            Transaction transaction = payment.getTransactions().get(0);

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
