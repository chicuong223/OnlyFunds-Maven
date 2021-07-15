<%-- 
    Document   : account_info
    Created on : 14-Mar-2021, 14:09:18
    Author     : chiuy
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Set Up Creator Page</title>
    </head>
    <style>
        .head{
            font-weight: bold;
        }
        fieldset{
            width: 30%;
            background-color: white;
            border-radius: 20px;
            margin-left: 36%;
            margin-right: auto;
        }

    </style>
    <body>
        <c:set var="user" value="${sessionScope.user}"/>
        <!--Navigation bar-->
        <c:import url="navbar.jsp"></c:import>
        
        <form method="POST" action="SetUpCreatorPageServlet">
            <h1>Let other users know more about you and your interests! (These can be changed later)</h1>
            <!--Change bio description-->
            <div>
                <p class="head">Bio:</p> 
                <textarea name="bio" rows="10">${user.bio}</textarea>
            </div>

            <!--Change interest-->
            <div>
                Please select topics you are interested in (these can be changed later):
                <c:forEach items="${applicationScope.catList}" var="cat">
                    <div class="form-check">
                        <input name="category" class="form-check-input" type="checkbox" id="${cat.categoryId}" value="${cat.categoryId}" />
                        <label class="form-check-label" for="${cat.categoryId}">${cat.categoryName}</label>
                    </div>
                </c:forEach>
            </div>
            <button type="submit">Submit</button>
        </form>
        <script src="scripts/manage_creator_page_script.js"></script>
    </body>
</html>
