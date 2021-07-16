/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import category.Category;
import category.CategoryDAO;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import map.PostCategoryMapDAO;
import map.TierMapDAO;
import notification.NotificationDAO;
import post_management.post.Post;
import post_management.post.PostDAO;
import subscription_management.tier.Tier;
import subscription_management.tier.TierDAO;
import user_management.follow.FollowDAO;
import user_management.user.User;
import user_management.user.UserDAO;
import utils.UploadFile;

/**
 *
 * @author chiuy
 */
@MultipartConfig(
        fileSizeThreshold = 10 * 1024 * 1024,
        maxFileSize = 1024 * 1024 * 100,
        maxRequestSize = 1024 * 1024 * 100
)
@WebServlet(name = "WritePostServlet", urlPatterns = {"/WritePostServlet"})
public class WritePostServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CategoryDAO cDAO = new CategoryDAO();
        TierDAO tDAO = new TierDAO();
        ArrayList<Category> catList = cDAO.getAllCategories();
        User user = (User) request.getSession().getAttribute("user");
        ArrayList<Tier> tierList = tDAO.getTiersByUser(user);
        request.setAttribute("catList", catList);
        request.setAttribute("tierList", tierList);
        request.getRequestDispatcher("post_form.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        CategoryDAO categoryDAO = new CategoryDAO();
        PostDAO dao = new PostDAO();
        TierDAO tierDAO = new TierDAO();
        TierMapDAO tierMapDAO = new TierMapDAO();
        String title = request.getParameter("title");
        String desc = request.getParameter("desc");
        UploadFile fileUpload = new UploadFile();
        String[] cats = request.getParameterValues("cat");
        String[] tiers = request.getParameterValues("tier");
        User user = (User) request.getSession().getAttribute("user");
        String filename = fileUpload.getFileName(request.getPart("attachment"));
        long millis = System.currentTimeMillis();
        Date date = new java.sql.Date(millis);
        PostCategoryMapDAO categoryMapDAO = new map.PostCategoryMapDAO();
        Post post = new Post(0, user, title, desc, filename, date, 0, true);
        
        //add post
        dao.addPost(post);
        
        //Reason: Because we use identity for post ID in database,
        //we can only get the correct post id of the added post after it has been inserted into the database
        //Steps:
        //1. Get the latest added post id of the current user
        //2. Change the in-memory post id to the id gotten from step 1
        //3. change file name to the post id and upload it to the server
        //4. change the in-memory post's attachment URL
        //5. Update the post in the database
        if(!filename.trim().equals("")){
            int postId = dao.getLatestPostIdByUser(user);
//            Post post = dao.getLatestPostByUser(user)
            post.setPostId(postId);
            filename = fileUpload.postAttachmentUpload(request, postId);
            post.setAttachmentURL(filename);
            dao.updatePost(post);
        }
        //If user did not choose any category, add category Others to category map
        //else, add all checked categories to category map
        if (cats == null) {
            Category cat = categoryDAO.getCategoryByID(6);
            categoryMapDAO.addPostCatMap(user, cat);
        }
        else {
            for (String catID : cats) {
                Category cat = categoryDAO.getCategoryByID(Integer.parseInt(catID));
                categoryMapDAO.addPostCatMap(user, cat);
            }
        }
        //if user chose any tier, add a tier map
        if (tiers != null) {
            for (String tierID : tiers) {
                Tier tier = tierDAO.getTierById(Integer.parseInt(tierID));
                tierMapDAO.addTierMap(tier, user);
            }
        }
        sendNotifications(post, user);
        response.sendRedirect("YourPostsServlet");
    }

    private void sendNotifications(Post post, User user) {
        FollowDAO fwDAO = new FollowDAO();
        UserDAO userDAO = new UserDAO();
        ArrayList<User> followers = fwDAO.GetFollowersByUser(user);
        ArrayList<User> subscribers = userDAO.getSubscribers(user);
        ArrayList<User> recipients = new ArrayList<>();
        NotificationDAO ntDAO = new NotificationDAO();
        
        //add followers to recipients
        followers.forEach(follower -> {
            recipients.add(follower);
        });
        
        //add subscribers to recipients
        subscribers.forEach(subscriber -> {
            recipients.add(subscriber);
        });
        
        //remove duplicated users in recipients
        for (int i = 0; i < recipients.size(); i++) {
            for (int j = i + 1; j < recipients.size(); j++) {
                if (recipients.get(i).getUsername().equalsIgnoreCase(recipients.get(j).getUsername())) {
                    recipients.remove(j);
                    break;
                }
            }
        }
        recipients.forEach(recipient -> {
            ntDAO.generateNotification(post, recipient);
        });
    }

}
