/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import post_management.comment.CommentDAO;

/**
 *
 * @author chiuy
 */
@WebServlet(name = "DeleteOrEditComment", urlPatterns = {"/DeleteOrEditComment"})
public class DeleteOrEditComment extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        System.out.println(action);
        CommentDAO cmtDAO = new CommentDAO();
        if (action.equals("delete")) {
            String cmtID = request.getParameter("commentId");
            cmtDAO.deactivateComment(Integer.parseInt(cmtID));
            return;
        }
        if (action.equals("edit")) {
            String content = request.getParameter("newContent");
            String strCmtID = request.getParameter("commentId");
            int cmtID = Integer.parseInt(strCmtID);
            cmtDAO.editComment(cmtID, content);
        }
        System.out.println("Delete Comment is called");
    }

}
