/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.paypal.api.payments.Payee;
import com.paypal.api.payments.PayerInfo;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.PayPalRESTException;
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
@WebServlet(name = "ReviewPaymentServlet", urlPatterns = {"/review_payment"})
public class ReviewPaymentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String paymentID = request.getParameter("paymentId");
        String payerID = request.getParameter("PayerID");
        try {
            PaymentUtils paymentUtils = new PaymentUtils();
            Payment payment = paymentUtils.getPaymentDetails(paymentID);
            PayerInfo payerInfo = payment.getPayer().getPayerInfo();
            Transaction transaction = payment.getTransactions().get(0);
            request.setAttribute("payer", payerInfo);
            request.setAttribute("transaction", transaction);
            String url = "review_payment.jsp?paymentId=" + paymentID + "&PayerID=" + payerID;
            request.getRequestDispatcher(url).forward(request, response);
        }
        catch (PayPalRESTException | IOException | ServletException e) {
            System.out.println(e.getMessage());
            log("Error reviewing payment", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
