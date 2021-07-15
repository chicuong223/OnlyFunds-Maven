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
import java.util.HashMap;
import java.util.LinkedHashMap;
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
                
                ArrayList<Category> userCatArrayList = new ArrayList();
                LinkedHashMap<Category, Boolean> ucList = (LinkedHashMap) session.getAttribute("ucList");
                String[] userCatList = request.getParameterValues("category");
                
                // delete all cat in db
                dao.removeAllCategoriesByUser(user);
                // change all value in hashmap to false
                ucList.replaceAll((Category, Boolean) -> Boolean=false);
                
                if (userCatList!=null) {
                    for (String catid : userCatList) {
                        // add category into database
                        int id = Integer.parseInt(catid);
                        Category cat = cdao.getCategoryByID(id);
                        userCatArrayList.add(cat);
                        UserCategoryMap uCat = new UserCategoryMap(cat, user);
                        dao.addCategoryMap(uCat);
                        // change hash map value
                        ucList.put(cat, true);
                    }
                }
                session.setAttribute("ucList", ucList);
            }
            
            //change bio
            if (action.equals("bio")) {
                String bio = request.getParameter("bio");
                uDao.changeBio(user.getUsername(), bio);
                user.setBio(bio);
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
        ArrayList<Category> catList = (ArrayList) getServletContext().getAttribute("catList");
        RequestDispatcher rd = request.getRequestDispatcher(manageCreatorInfoPage);
        LinkedHashMap<Category, Boolean> ucList = new LinkedHashMap<>();
        
        System.out.println(catList);
//        catList.removeAll(userCatList);
//        System.out.println(catList);
       
        catList.forEach(cat -> {
            ucList.put(cat, false);
        });
        System.out.println(ucList);
        userCatList.forEach(ucat -> {
            catList.forEach(cat -> {
                if (ucat.getCategoryId() == cat.getCategoryId()) {
                    ucList.put(cat, true);
                }
            });
        });
        
        session.setAttribute("ucList", ucList);
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
