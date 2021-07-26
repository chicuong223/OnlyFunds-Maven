package servlets;
import authority_management.staff.Staff;
import authority_management.staff.StaffDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import report.Report;
import report.ReportDAO;
/**
 *
 * @author DELL
 */
@WebServlet(name = "ReportListByStaffServlet", urlPatterns = {"/ReportListByStaffServlet"})
public class ReportListByStaffServlet extends HttpServlet {

    final int numReportInPage = 5;
    final String reportListPage = "reportByStaffList.jsp";
    final String noStaff="StaffListServlet";
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int pageNum = 1;
        if(request.getParameter("staffUsername")==null){
            request.getRequestDispatcher(noStaff).forward(request, response);
            return;
        }
        StaffDAO sDAO=new  StaffDAO();
        Staff staff=sDAO.getStaffByUsername(request.getParameter("staffUsername"));
        request.setAttribute("ofStaff", staff);
        if (request.getParameter("page") != null) {
            pageNum = Integer.parseInt(request.getParameter("page"));
        }
        ReportDAO rDAO=new ReportDAO();
        ArrayList<Report> reportList=rDAO.getReportsByStaff(staff);
         int numPage = reportList.size() / numReportInPage 
                + (reportList.size() % numReportInPage == 0 ? 0 : 1);
        request.setAttribute("numPage", numPage);
        
        int startIndex = (pageNum - 1) * numReportInPage;
        int endIndex = pageNum * numReportInPage ;
        endIndex=(endIndex>reportList.size()?reportList.size():endIndex);
        ArrayList<Report> subArray = new ArrayList<Report>(reportList.subList(startIndex, endIndex));
        request.setAttribute("reportList", subArray);
       
        request.getRequestDispatcher(reportListPage).forward(request, response);
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
     *   * @throws ServletException if a servlet-specific error occurs
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