/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * @author ASUS GAMING
 */
@WebServlet(name = "StaffBillListServlet", urlPatterns = {"/StaffBillListServlet"})
public class StaffBillListServlet extends HttpServlet {

    private final int pageSize = 9;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            request.setAttribute("isActive", "mBill");
            int page = 0;
            String strPage = request.getParameter("page");
            if (strPage == null)
                page = 1;
            else
                page = Integer.parseInt(strPage);
            BillDAO billDAO = new BillDAO();
            int start = page * pageSize - (pageSize - 1);
            int end = page * pageSize;
            List<Bill> billList = new ArrayList<>();
            int count = 0;
            String action = request.getParameter("action");
            if (action != null) {
                Date startDate = Date.valueOf(request.getParameter("start"));
                Date endDate = Date.valueOf(request.getParameter("end"));
                billList = billDAO.getTransactionsFromDateToDate(startDate, endDate, start, end);
                count = billDAO.countTransactionFromDateToDate(startDate, endDate);
                request.setAttribute("start", startDate);
                request.setAttribute("end", endDate);
                request.setAttribute("action", action);
            }
            else {
                billList = billDAO.getAllTransactions(start, end);
                count = billDAO.countAllTransactions();
            }
            //get end page
            int endPage = count / pageSize;
            if (count % pageSize != 0)
                endPage++;
            request.setAttribute("bills", billList);
            request.setAttribute("end", endPage);
            request.getRequestDispatcher("staff_transaction_management.jsp").forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        }
        catch (ParseException ex) {
            Logger.getLogger(StaffBillListServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        }
        catch (ParseException ex) {
            Logger.getLogger(StaffBillListServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
