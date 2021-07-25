/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import subscription_management.transaction.Bill;
import subscription_management.transaction.BillDAO;
import user_management.user.User;

/**
 *
 * @author chiuy
 */
@WebServlet(name = "ViewTransactionHistory", urlPatterns = {"/ViewTransactionHistory"})
public class ViewTransactionHistory extends HttpServlet {

    private final int pageSize = 9;

    //View transactions of others -> Staff does this
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        String strPageIndex = request.getParameter("page");
//        String filter = request.getParameter("filter")
        doPost(request, response);
    }

//View own transaction
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         request.setAttribute("isActive", "mBill");
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            request.setAttribute("usererror", "User not found");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }
        String filter = request.getParameter("filter");
        if (filter == null) {
            request.getRequestDispatcher("transactions.jsp").forward(request, response);
            return;
        }
        BillDAO billDAO = new BillDAO();
        ArrayList<Bill> billList = new ArrayList<>();
        int start = Integer.parseInt(request.getParameter("start"));
        int end = Integer.parseInt(request.getParameter("end"));
        if (filter.equals("All"))
            billList = billDAO.getTransactionsByUser(user, start, end);
        else if (filter.equalsIgnoreCase("Received"))
            billList = billDAO.getReceiveTransactions(user, start, end);
        else if (filter.equalsIgnoreCase("Sent"))
            billList = billDAO.getSendTransactions(user, start, end);
        else if (filter.equalsIgnoreCase("search")){
            String creator = request.getParameter("creator");
            billList = billDAO.searchBillByCreatorAndSubscriber(creator, user, start, end);
        }
        else {
            request.setAttribute("actionerror", "Invalid parameter");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }
        String color = "";
        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
        for (Bill bill : billList){
            if(bill.getSender().getUsername().equals(user.getUsername()))
                color = "table-danger";
            else
                color = "table-success";
            String date = format.format(bill.getTransactionDate());
            response.getWriter().write("<tr class=\"" + color + "\">\n"
                    + "<td>" + bill.getContent() + "</td>\n"
                    + "<td>" + bill.getSender().getUsername() + "</td>\n"
                    + "<td>" + bill.getRecipient().getUsername() + "</td>\n"
                    + "<td>" + bill.getPrice() + "</td>\n"
                    + "<td>" + date + "</td>\n"
                    + "</tr>");
        }
    }
}
