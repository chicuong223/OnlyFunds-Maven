/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import subscription_management.subscription.Subscription;
import subscription_management.subscription.SubscriptionDAO;
import user_management.user.User;

/**
 *
 * @author chiuy
 */
@WebServlet(name = "ManageSubscriptionServlet", urlPatterns = {"/ManageSubscriptions"})
public class ManageSubscriptionServlet extends HttpServlet {

    private Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            request.setAttribute("usererror", "User not logged in");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }
        request.getRequestDispatcher("manage_subscriptions.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        User user = (User) request.getSession().getAttribute("user");
        SubscriptionDAO dao = new SubscriptionDAO();
        String action = request.getParameter("action");
        if (action == null) {
            int start = Integer.parseInt(request.getParameter("start"));
            int end = Integer.parseInt(request.getParameter("end"));
            List<Subscription> subLst = dao.getSubscriptionsByUser(user, start, end);
            for (Subscription subscription : subLst) {
                out.write("<tr id='sub-" + subscription.getSubscriptionId() + "'>");
                out.write("<td>");
                out.write("<img src=\"images/avatars/" + subscription.getTier().getCreator().getAvatarURL() + "\" width=150px height=150px style=\"border-radius: 50%\"/>");
                out.write("</td>");
                out.write("<td>");
                out.write("<h3 class=\"my-0 fw-bold\">" + subscription.getTier().getCreator().getUsername() + "</h3>\n"
                        + "<p class=\"my-0\"><span class=\"fw-bold\">Tier:</span>" + subscription.getTier().getTierTitle() + "</p>\n"
                        + "<p class=\"my-0\"><span class=\"fw-bold\">Price:</span> $" + subscription.getTier().getPrice() + "</p>\n"
                        + "<p class=\"my-0\"><span class=\"fw-bold\">Start date:</span>" + subscription.getStartDate() + "</p>\n"
                        + "<p class=\"my-0\"><span class=\"fw-bold\">End date:</span>" + subscription.getEndDate() + "</p>");
                out.write("</td>");
                out.write("<td>");
                out.write("<a href=\"#\" class=\"link-danger\" data-bs-toggle=\"modal\" data-bs-target=\"#modal-" + subscription.getSubscriptionId() + "\">Cancel</a>\n"
                        + "<div class=\"modal\" id=\"modal-" + subscription.getSubscriptionId() + "\">\n"
                        + "                    <div class=\"modal-dialog\">\n"
                        + "                        <div class=\"modal-content\">\n"
                        + "                            <div class=\"modal-header\">\n"
                        + "                                <h5 class=\"modal-title\">Cancel subscription</h5>\n"
                        + "                            </div>\n"
                        + "                            <div class=\"modal-body\">\n"
                        + "                                <form id=\"form-" + subscription.getSubscriptionId() +"\" action=\"CancelSubscriptionServlet\" method=\"POST\">\n"
                        + "                                    <input type=\"hidden\" name=\"id\" value=\"" + subscription.getSubscriptionId() + "\"/>\n"
                        + "                                    <p>You will not receive refund after cancelling this subscription. Are you sure?</p>\n"
                        + "                                </form>\n"
                        + "                            </div>\n"
                        + "                            <div class=\"modal-footer\">\n"
                        + "                                <button type=\"button\" class=\"btn btn-danger del-btn\" value=\"Cancel\" id=\"del-" + subscription.getSubscriptionId() + "\">Cancel</button>\n"
                        + "                                <button class=\"btn btn-secondary\" data-bs-dismiss=\"modal\">Close</button>\n"
                        + "                            </div>\n"
                        + "                        </div>\n"
                        + "                </div>");
                out.write("</td>");
                out.write("</td>");
            }
//            gson.toJson(subLst, response.getWriter());
        }
        else {
            String strSubID = request.getParameter("id");
            if (strSubID == null) {
                request.setAttribute("suberror", "Subscription not found");
                request.getRequestDispatcher("error.jsp").forward(request, response);
                return;
            }
            int id = Integer.parseInt(strSubID);

        }
//        response.getWriter().write(subLst.toString());
//        for (Subscription subscription : subLst) {
//            
//        }
//            response.getWriter().write("<tr>\n"
//                    + "<td><img src=\"images/avatars/" + subscription.getTier().getCreator().getAvatarURL() + "\" width=150px height=150px/></td>\n"
//                    + "<td>\n"
//                    + "<h4>" + subscription.getTier().getCreator().getUsername() + "</h4>\n"
//                    + "<p class='my-0' style='font-size: 50%'>Tier: " + subscription.getTier().getTierTitle() + "</p>\n"
//                    + "<p class='my-0' style='font-size: 50%'>Price: " + subscription.getTier().getPrice() + " USD </p>\n"
//                    + "<p class='my-0' style='font-size: 50%'>Start Date: " + subscription.getStartDate() + "</p>\n"
//                    + "<p class='my-0' style='font-size: 50%'>End Date: " + subscription.getEndDate() + "</p>\n"
//                    + "</td>\n"
//                    + "<td>\n"
//                    + "<a href=\"#\">Cancel</a>\n"
//                    + "</td>\n"
//                    + "</tr>");
////            response.getWriter().write(subscription.toString());
    }
}
