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
import java.util.LinkedHashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import map.PostCategoryMapDAO;
import map.TierMapDAO;
import post_management.post.Post;
import post_management.post.PostDAO;
import subscription_management.tier.Tier;
import subscription_management.tier.TierDAO;
import user_management.user.User;
import utils.ContextAndSessionCheck;
import utils.UploadFile;

/**
 *
 * @author chiuy
 */
@WebServlet(name = "EditPostServlet", urlPatterns
        = {
            "/EditPostServlet"
        })
@MultipartConfig(
        fileSizeThreshold = 10 * 1024 * 1024,
        maxFileSize = 1024 * 1024 * 100,
        maxRequestSize = 1024 * 1024 * 100
)
public class EditPostServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (getServletContext().getAttribute("catList") == null || request.getSession().getAttribute("user") == null) {
            response.sendRedirect("WelcomePageServlet");
            return;
        }
        User user = (User) request.getSession().getAttribute("user");
        int postID = Integer.parseInt(request.getParameter("id"));
        System.out.println(postID);
        PostDAO dao = new PostDAO();
        Post post = dao.getPostByID(postID);
        CategoryDAO catDAO = new CategoryDAO();
        PostCategoryMapDAO postCatMap = new PostCategoryMapDAO();
        TierDAO tierDAO = new TierDAO();
        TierMapDAO tierMapDAO = new TierMapDAO();
        List<Category> postCatList = catDAO.getCategoriesByPost(post);
        ArrayList<Category> catList = catDAO.getAllCategories();

        //A map to check which categories the post is currently in
        LinkedHashMap<Category, Boolean> catMap = new LinkedHashMap<>();
        catList.forEach(cat -> catMap.put(cat, false));
        postCatList.forEach(postCat -> {
            catList.forEach(cat -> {
                if (cat.getCategoryId() == postCat.getCategoryId())
                    catMap.put(cat, Boolean.TRUE);
            });
        });

        List<Tier> postTiers = tierDAO.getTiersByPost(post);
        List<Tier> userTiers = tierDAO.getTiersByUser(user);
        LinkedHashMap<Tier, Boolean> tierMap = new LinkedHashMap<>();
        userTiers.forEach(tier -> tierMap.put(tier, Boolean.FALSE));
        postTiers.forEach(postTier -> {
            userTiers.stream().filter(userTier -> (postTier.getTierId() == userTier.getTierId())).forEachOrdered(userTier -> {
                tierMap.put(userTier, Boolean.TRUE);
            });
        });
        request.setAttribute("tierList", tierMap);
        request.setAttribute("post", post);
        request.setAttribute("catList", catMap);
        request.getRequestDispatcher("edit_post_form.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String cancel = request.getParameter("cancel");
        if (cancel != null) {
            response.sendRedirect("PostDetailServlet?id=" + cancel);
            return;
        }
        String strPostId = request.getParameter("postid");
        if (strPostId == null) {
            request.setAttribute("posterror", "Post not found at Edit Post Servlet");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }
        PostDAO postDAO = new PostDAO();
        Post post = postDAO.getPostByID(Integer.parseInt(strPostId));
        PostCategoryMapDAO postCatMapDAO = new PostCategoryMapDAO();
        String title = request.getParameter("title");
        String desc = request.getParameter("desc");
        UploadFile upload = new UploadFile();
        String filename = upload.getFileName(request.getPart("attachment"));
        post.setTitle(title);
        post.setDescription(desc);
        if (!filename.isEmpty()) {
//            System.out.println(post.getAttachmentURL());
            upload.deleteFile(request, post.getAttachmentURL(), "post_file");
            filename = upload.postAttachmentUpload(request, post.getPostId());
            post.setAttachmentURL(filename);
        }

        //update category map
        //Delete all current category map of post
        //Then add the new ones
        postCatMapDAO.deleteCategoryMapsByPost(post);
        String[] categories = request.getParameterValues("cat");
        CategoryDAO catDAO = new CategoryDAO();
        if (categories == null) {
            Category cat = catDAO.getCategoryByID(6);
            postCatMapDAO.addPostCatMapEdit(post, cat);
        }
        else
            for (String id : categories) {
                Category cat = catDAO.getCategoryByID(Integer.parseInt(id));
                postCatMapDAO.addPostCatMapEdit(post, cat);
            }
        postDAO.updatePost(post);
        response.sendRedirect("PostDetailServlet?id=" + post.getPostId());
    }

}
