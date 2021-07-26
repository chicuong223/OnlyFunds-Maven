/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import subscription_management.transaction.Bill;
import subscription_management.transaction.BillDAO;

/**
 *
 * @author DELL
 */
@WebServlet(name = "SearchBillServlet", urlPatterns = {"/SearchBillServlet"})
public class SearchBillServlet extends HttpServlet {

    private final int pageSize = 9;
    private final String noSearchPage = "StaffBillListServlet";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("isActive", "mBill");
        String searchedString = request.getParameter("search");
        if (searchedString == null || searchedString.trim().isEmpty()) {
            request.getRequestDispatcher(noSearchPage).forward(request, response);
            return;
        }
        request.setAttribute("search", searchedString);
        int page = 1;
        String strPage = request.getParameter("page");
        if (strPage != null) {
            page = Integer.parseInt(strPage);
        }
        BillDAO billDAO = new BillDAO();
        int count = 0;
        int start = page * pageSize - (pageSize - 1);
        int end = page * pageSize;
        ArrayList<Bill> billList = new ArrayList<>();
        billList = billDAO.searchTransactions(start, end, searchedString);
        //get end page
        int endPage = count / pageSize;
        if (count % pageSize != 0) {
            endPage++;
        }
        request.setAttribute("bills", billList);
        request.setAttribute("end", endPage);
        request.getRequestDispatcher("staff_transaction_management.jsp").forward(request, response);
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
        processRequest(request, response);
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
        processRequest(request, response);
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
