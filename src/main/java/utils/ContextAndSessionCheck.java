/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import user_management.user.User;

/**
 *
 * @author ASUS GAMING
 */
public class ContextAndSessionCheck extends HttpServlet{
    public boolean checkContextAndSession(HttpServletRequest request) {
        return (request.getSession().getAttribute("user")==null || getServletContext().getAttribute("catList")==null);
    }  
}
