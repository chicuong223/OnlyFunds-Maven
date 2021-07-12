<%-- 
    Document   : manage_categories
    Created on : Jul 3, 2021, 12:01:00 PM
    Author     : ASUS GAMING
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Manage Categories</title>
    </head>
    <body>
        <!--Change bio description-->
        <div>
            
        </div>
        
        <!--Change interest-->
        <div>
            <p class="head">Current Interests:</p> 
            <div id="currentInterest">
                <c:choose>
                    <c:when test="${!empty userCatList}">
                        <c:forEach items="${sessionScope.userCatList}" var="ucat">
                            <strong>${ucat.categoryName}</strong>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <strong>None</strong>
                    </c:otherwise>
                </c:choose>
            </div>
            <!--Interest form starts here-->
            <button id="change-interest-btn">Change interest:</button>
            <form id="change-interest-form" method="POST" hidden>
                <c:forEach items="${applicationScope.catList}" var="cat">
                    <div class="form-check">
                        <input name="category" class="form-check-input" type="checkbox" id="${cat.categoryId}" value="${cat.categoryId}" />
                        <label class="form-check-label" for="${cat.categoryId}">${cat.categoryName}</label>
                    </div>
                </c:forEach>
                <button type="submit" name="action" value="category">Submit</button>
            </form>
        </div>
        
        <script src="scripts/manage_creator_page_script.js"></script>
    </body>
</html>
