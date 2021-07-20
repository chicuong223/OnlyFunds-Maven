/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import category.Category;
import category.CategoryDAO;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import map.UserCategoryMapDAO;
import post_management.post.Post;
import post_management.post.PostDAO;
import user_management.user.User;
import user_management.user.UserDAO;

/**
 *
 * @author ASUS GAMING
 */
@WebServlet(name = "WelcomePageServlet", urlPatterns = {"/WelcomePageServlet"})
public class WelcomePageServlet extends HttpServlet {

    private static final String WELCOME_PAGE = "welcome_page.jsp";
    private final Gson gson = new Gson();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String a = request.getParameter("action");
            RequestDispatcher rd = request.getRequestDispatcher(WELCOME_PAGE);
            CategoryDAO cDao = new CategoryDAO();
            UserCategoryMapDAO uDao = new UserCategoryMapDAO();
            UserDAO userDAO = new UserDAO();
            //load page on startup
            if (a == null) {
                //pass data for creator list
                //category list
                ArrayList<Category> catList = cDao.getAllCategories();
                ArrayList<User> popularCreators = userDAO.getUsersMostSubscriber();
                HashMap<User, ArrayList<Category>> userCatMap = new HashMap<>();
                getServletContext().setAttribute("catList", catList);
                for (User popularCreator : popularCreators) {
                    ArrayList<Category> lst = uDao.getCategoriesByUser(popularCreator);
                    userCatMap.put(popularCreator, lst);
                }
                request.setAttribute("userList", userCatMap);
                rd.forward(request, response);
            }
            else if (a.equals("load")) {
                int start = Integer.parseInt(request.getParameter("start"));
                int end = Integer.parseInt(request.getParameter("end"));
                PostDAO postDAO = new PostDAO();
                ArrayList<Post> postList = postDAO.getFreePosts(start, end);
                String json = gson.toJson(postList);
                System.out.println(json);
                response.getWriter().write(json);
//            request.setAttribute("postList", postList);
//            request.getRequestDispatcher(WELCOME_PAGE).forward(request, response);
//                postList.forEach(post -> {
//                    out.write("<div class=\"col-lg-3 mb-2\">\n"
//                            + "                        <div class=\"card\" id=\"post\">\n"
//                            + "                            <a href=\"#post-detail\" class=\"stretched-link\"></a>\n"
//                            + "                            <div class=\"card-header p-2 pt-1\">\n"
//                            + "                                <h4 class=\"card-title fw-bold\">Post's title</h4>\n"
//                            + "                                <h6 class=\"card-subtitle text-muted\" style=\"font-size: 16px;\">Author's name</h6>\n"
//                            + "                            </div>\n"
//                            + "                            <div class=\"card-body p-2 pt-1\">\n"
//                            + "                                <a href=\"#post-detail\" class=\"stretched-link\"></a>\n"
//                            + "                                <p class=\"card-text\">Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do\n"
//                            + "                                    eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,\n"
//                            + "                                    quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.\n"
//                            + "                                    Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu\n"
//                            + "                                    fugiat nulla pariatur\n"
//                            + "                                </p>\n"
//                            + "                            </div>\n"
//                            + "                            <div class=\"card-footer p-2 pt-1 pb-1\">\n"
//                            + "                                <small><i class=\"fas fa-thumbs-up\"></i> 1234</small>\n"
//                            + "                                <small><i class=\"fas fa-comment\"></i> 1234</small>\n"
//                            + "                                <small><i class=\"far fa-eye\"></i> 1234</small>\n"
//                            + "                            </div>\n"
//                            + "                        </div>\n"
//                            + "                    </div>");
//                });
            }
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
