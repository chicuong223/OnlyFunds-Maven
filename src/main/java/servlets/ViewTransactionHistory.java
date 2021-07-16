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
            request.setAttribute("usererror", "User not found");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }
        BillDAO billDAO = new BillDAO();
        ArrayList<Bill> billList = new ArrayList<>();
        String filter = request.getParameter("filter");
        if (filter == null) {
            billList = billDAO.getTransactionsByUser(user);
        }
        else if (filter.equalsIgnoreCase("receive")) {
            billList = billDAO.getReceiveTransactions(user);
        }
        else if (filter.equalsIgnoreCase("send")) {
            billList = billDAO.getSendTransactions(user);
        }
        else {
            request.setAttribute("actionerror", "Invalid parameter");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }
        int count = billList.size();
        int pageSize = 5;
        int end = count / pageSize;
        if (count % pageSize != 0) {
            end++;
        }
        String strPageIndex = request.getParameter("page");
        int pageIndex = 0;
        if (strPageIndex == null) {
            pageIndex = 1;
        }
        else {
            pageIndex = Integer.parseInt(strPageIndex);
        }
        ArrayList<Bill> result = new ArrayList<>();
        for (int i = pageIndex * pageSize - (pageSize - 1) - 1; i <= pageIndex * pageSize - 1; i++) {
            if (i >= billList.size()) {
                break;
            }
            result.add(billList.get(i));
        }
        if (filter != null) {
            request.setAttribute("filter", filter);
        }
        for (Bill bill : result) {
            System.out.println(bill.getContent());
        }
        request.setAttribute("username", username);
        request.setAttribute("end", end);
        request.setAttribute("transactions", result);
        request.getRequestDispatcher("transactions.jsp").forward(request, response);
    }

//View own transaction
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
}
