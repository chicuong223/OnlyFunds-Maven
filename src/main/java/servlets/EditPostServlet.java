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
import utils.UploadFile;

/**
 *
 * @author chiuy
 */
@WebServlet(name = "EditPostServlet", urlPatterns =
{
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
        int postID = Integer.parseInt(request.getParameter("id"));
        PostDAO dao = new PostDAO();
        Post post = dao.getPostByID(postID);
        CategoryDAO catDAO = new CategoryDAO();
        TierDAO tDAO = new TierDAO();
        ArrayList<Category> catList = catDAO.getAllCategories();
//        User user = (User) request.getSession().getAttribute("user");
        HttpSession session = request.getSession();
        session.setAttribute("post", post);
        ArrayList<Tier> tierList = tDAO.getTiersByUser(post.getUploader());
        ArrayList<Tier> postTiers = tDAO.getTiersByPost(post);
        request.setAttribute("postTiers", postTiers);
        request.setAttribute("tierList", tierList);
        request.setAttribute("catList", catList);
        request.getRequestDispatcher("edit_post_form.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String cancel = request.getParameter("cancel");
        if (cancel != null)
        {
            response.sendRedirect("PostDetailServlet?id=" + cancel);
            return;
        }
        String title = request.getParameter("title");
        String desc = request.getParameter("desc");
        UploadFile upload = new UploadFile();
        String filename = upload.getFileName(request.getPart("attachment"));
        PostDAO postDAO = new PostDAO();
        Post post = (Post) request.getSession().getAttribute("post");
        post.setTitle(title);
        post.setDescription(desc);
        if (!filename.isEmpty())
        {
            upload.deleteFile(request, post.getAttachmentURL());
            filename = upload.postAttachmentUpload(request, post.getPostId());
            post.setAttachmentURL(filename);
        }
        postDAO.updatePost(post);
        response.sendRedirect("PostDetailServlet?id=" + post.getPostId());
    }

}
