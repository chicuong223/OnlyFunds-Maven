/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import category.Category;
import category.CategoryDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import map.UserCategoryMap;
import map.UserCategoryMapDAO;
import user_management.user.User;
import user_management.user.UserDAO;
import utils.ContextAndSessionCheck;

/**
 *
 * @author ASUS GAMING
 */
@WebServlet(name = "SetUpCreatorPageServlet", urlPatterns = {"/SetUpCreatorPageServlet"})
public class SetUpCreatorPageServlet extends HttpServlet {

    private String url = "main_page.jsp";
    private String errorUrl = "error.jsp";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            //check session & context
            String url = "WelcomePageServlet";
            boolean check = new ContextAndSessionCheck().checkContextAndSession(request);
            if (check) {
                response.sendRedirect(url);
                return;
            }
            
            String bio = request.getParameter("bio");
            String[] catList = request.getParameterValues("category");
            HttpSession session = request.getSession();
            User newUser = (User) session.getAttribute("user");
            RequestDispatcher rd = request.getRequestDispatcher(url);
            ArrayList<Category> userCatList = new ArrayList();
            
            UserDAO udao = new UserDAO();
            UserCategoryMapDAO dao = new UserCategoryMapDAO();
            CategoryDAO cdao = new CategoryDAO();
            
            //update bio
            udao.changeBio(newUser.getUsername(), bio);
            newUser.setBio(bio);
            //add category list
            if (catList!=null) {
                for (String catid : catList) {
                    int id = Integer.parseInt(catid);
                    Category cat = cdao.getCategoryByID(id);
                    userCatList.add(cat);
                    UserCategoryMap uCat = new UserCategoryMap(cat, newUser);
                    dao.addCategoryMap(uCat);
                }
            }
            session.setAttribute("user", newUser);
            session.setAttribute("userCatList", userCatList);
            rd.forward(request, response);
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
