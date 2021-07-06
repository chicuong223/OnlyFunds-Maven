/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import subscription_management.transaction.Bill;
import subscription_management.transaction.BillDAO;
import user_management.user.User;
import user_management.user.UserDAO;

/**
 *
 * @author chiuy
 */
@WebServlet(name = "ViewTransactionHistory", urlPatterns = {"/ViewTransactionHistory"})
public class ViewTransactionHistory extends HttpServlet {

    //View transactions of others -> Staff does this
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        User user = new UserDAO().getUserByUsername(username);
        if (user == null) {
            request.setAttribute("usererror", "User not logged in");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }
        BillDAO billDAO = new BillDAO();
        ArrayList<Bill> billList = billDAO.getTransactionsByUser(user);
        
        //filter receiving transactions
        String filter = request.getParameter("filter");
        if(filter != null){
            if(filter.equalsIgnoreCase("receive")){
                Predicate<Bill> byRecieve = bill -> bill.getRecipient().getUsername().equals(username);
                billList.stream().filter(byRecieve).collect(Collectors.toList());
            }
        }
        
        for (Bill bill : billList) {
            System.out.println(bill.getContent());
        }
        request.setAttribute("transactions", billList);
        request.getRequestDispatcher("transactions.jsp").forward(request, response);
    }

//View own transaction
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
}
