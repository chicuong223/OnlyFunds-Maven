<%-- 
    Document   : category_select
    Created on : Jun 20, 2021, 7:47:44 PM
    Author     : ASUS GAMING
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Category Select</title>
    </head>
    <body>
        
        <!--Select category-->
        <form action="CategorySelectServlet" method="POST">
            Please select topics you are interested in (these can be changed later):
            <c:forEach items="${applicationScope.catList}" var="cat">
                <div class="form-check">
                    <input name="category" class="form-check-input" type="checkbox" id="${cat.categoryId}" value="${cat.categoryId}" />
                    <label class="form-check-label" for="${cat.categoryId}">${cat.categoryName}</label>
                </div>
            </c:forEach>
            <button type="submit">Submit</button>
        </form>
        
    </body>
</html>
