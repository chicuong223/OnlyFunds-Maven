/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Details;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payee;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.PayerInfo;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import subscription_management.tier.Tier;
import user_management.user.User;

/**
 *
 * @author chiuy
 */
public class PaymentUtils {

    private static final String CLIENT_ID = "Aa07Q8vSpgreGRK6-izbEStccsSyXODoOUbQTlZEVlstbeLCek9CHonbtqCSK5cVQqA2m2lygpE5hBAJ";
    private static final String CLIENT_SECRET = "EFHpA_Bkl3l7XHIb0ezXGvheKup-w_C8vUVsSHtfmaU4PFpIhP965tbI6dzV9Wcoyr7cfe0BKMFZ8pso";
    private static final String MODE = "sandbox";

    public Payment definePayment(Tier tier, User subscriber) {
        PayerInfo payerInfo = new PayerInfo();
//        payerInfo.setFirstName(subscriber.getFirstName());
//        payerInfo.setLastName(subscriber.getLastName());
        payerInfo.setEmail(subscriber.getEmail());

        Payer payer = new Payer();
        payer.setPayerInfo(payerInfo);
        payer.setPaymentMethod("paypal");

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setReturnUrl("http://localhost:8080/OnlyFunds/review_payment");
        redirectUrls.setCancelUrl("http://localhost:8080/OnlyFunds/cancel_payment");

        Details details = new Details();
        details.setSubtotal(String.valueOf(tier.getPrice()));

        Amount amount = new Amount();
        amount.setCurrency("USD");
        amount.setTotal(String.valueOf(tier.getPrice()));
        amount.setDetails(details);

//        Payee payee = new Payee();
//        payee.setEmail(tier.getCreator().getEmail());

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setDescription(tier.getTierTitle());
//        transaction.setPayee(payee);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payment payment = new Payment();
        payment.setIntent("authorize");
        payment.setPayer(payer);
        payment.setRedirectUrls(redirectUrls);
        payment.setTransactions(transactions);

        return payment;
    }

    public String createPayment(Payment payment) {
        APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);
        try {
            Payment approvedPayment = payment.create(apiContext);
            List<Links> links = approvedPayment.getLinks();
            for (Links link : links) {
                if (link.getRel().equalsIgnoreCase("approval_url")) {
                    return link.getHref();
                }
            }
        }
        catch (PayPalRESTException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Payment executePayment(HttpServletRequest request) {
        Payment payment = new Payment();
        payment.setId(request.getParameter("paymentId"));
        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(request.getParameter("PayerID"));
        APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);
        try {
            Payment createdPayment = payment.execute(apiContext, paymentExecution);
            return createdPayment;
        }
        catch (PayPalRESTException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Payment getPaymentDetails(String paymentId) throws PayPalRESTException {
        APIContext apiContext = new APIContext(CLIENT_ID, CLIENT_SECRET, MODE);
        return Payment.get(apiContext, paymentId);
    }
}
