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
import utils.ContextAndSessionCheck;

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
        //check session & context
        if (getServletContext().getAttribute("catList") == null || request.getSession().getAttribute("user") == null) {
            response.sendRedirect("WelcomePageServlet");
            return;
        }
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
        if (filter == null)
            filter = "all";
        int page = 0;
        String strPage = request.getParameter("page");
        if (strPage == null)
            page = 1;
        else
            page = Integer.parseInt(strPage);
        BillDAO billDAO = new BillDAO();
        int count = 0;
        int start = page * pageSize - (pageSize - 1);
        int end = page * pageSize;
        ArrayList<Bill> billList = new ArrayList<>();
        if (filter.equalsIgnoreCase("All")) {
            request.setAttribute("active_tab", "all");
            billList = billDAO.getTransactionsByUser(user, start, end);
            count = billDAO.countTransactionsByUser(user);
        }
        else if (filter.equalsIgnoreCase("Received")) {
            request.setAttribute("active_tab", "received");
            billList = billDAO.getReceiveTransactions(user, start, end);
            count = billDAO.countReceivedTransactionsByUser(user);
        }
        else if (filter.equalsIgnoreCase("Sent")) {
            request.setAttribute("active_tab", "sent");
            billList = billDAO.getSendTransactions(user, start, end);
            count = billDAO.countSentTransactionsByUser(user);
        }
        else {
            request.setAttribute("actionerror", "Invalid parameter");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }
        //get end page
        int endPage = count / pageSize;
        if (count % pageSize != 0)
            endPage++;
        request.setAttribute("bills", billList);
        request.setAttribute("end", endPage);
        request.setAttribute("filter", filter);
        request.getRequestDispatcher("transactions.jsp").forward(request, response);
    }
}
