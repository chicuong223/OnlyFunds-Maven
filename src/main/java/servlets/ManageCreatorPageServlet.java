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

/**
 *
 * @author ASUS GAMING
 */
@WebServlet(name = "ManageCreatorPageServlet", urlPatterns = {"/ManageCreatorPage"})
public class ManageCreatorPageServlet extends HttpServlet {

    private final String manageCreatorInfoPage = "manage_creator_page.jsp";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            String action = request.getParameter("action");
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            RequestDispatcher rd = request.getRequestDispatcher(manageCreatorInfoPage);
            
            UserDAO uDao = new UserDAO();
            UserCategoryMapDAO dao = new UserCategoryMapDAO();
            CategoryDAO cdao = new CategoryDAO();
            
            //change category
            if (action.equals("category")) {
                
                ArrayList<Category> userCatList = new ArrayList();
                String[] catList = request.getParameterValues("category");
                
                dao.removeAllCategoriesByUser(user);
                if (catList!=null) {
                    for (String catid : catList) {
                        int id = Integer.parseInt(catid);
                        Category cat = cdao.getCategoryByID(id);
                        userCatList.add(cat);
                        UserCategoryMap uCat = new UserCategoryMap(cat, user);
                        dao.addCategoryMap(uCat);
                    }
                }
                session.setAttribute("userCatList", userCatList);
            }
            
            //change bio
            if (action.equals("bio")) {
                String bio = request.getParameter("bio");
                uDao.changeBio(user.getUsername(), bio);
            }
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
        UserCategoryMapDAO ucDao = new UserCategoryMapDAO();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        ArrayList<Category> userCatList = ucDao.getCategoriesByUser(user);
        RequestDispatcher rd = request.getRequestDispatcher(manageCreatorInfoPage);
            
        request.setAttribute("userCategoryList", userCatList);
        request.setAttribute("newUser", false);
        rd.forward(request, response);
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
