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
        <title>Manage Creator Page</title>
    </head>
    <body>
        <c:set var="user" value="${sessionScope.user}"/>
        <c:set var="ucList" value="${sessionScope.ucList}"/>
        
        <!--Navigation bar-->
        <c:import url="navbar.jsp"></c:import>
        
        <<h1>Manage Creator Page</h1>
        <!--Change bio description-->
        <form action="ManageCreatorPage" method="POST">
                <p class="head">Bio:</p> 
                <textarea name="bio" rows="10">${user.bio}</textarea>
                <button type="submit" name="action" value="bio">Submit</button>
        </form>
            
        <!--Change interest-->
        <div>
            <p class="head">Current Interests:</p> 
            <div id="currentInterest">
                <c:set var="check" value="${false}"/>
                <c:forEach items="${ucList}" var="ucat">
                    <c:if test="${ucat.value}">
                        <c:set var="check" value="${true}"/>
                        <strong>${ucat.key.categoryName}</strong>
                    </c:if>
                </c:forEach>
                <c:if test="${!check}">
                    <strong>None</strong>
                </c:if>
            </div>
            <!--Interest form starts here-->
            <button id="change-interest-btn">Change interest:</button>
            <form id="change-interest-form" action="ManageCreatorPage" method="POST" hidden>
                <c:forEach items="${ucList}" var="cat">
                    <c:choose>
                        <c:when test="${cat.value}">
                            <input checked name="category" class="form-check-input" type="checkbox" id="${cat.key.categoryId}" value="${cat.key.categoryId}" />
                            <label class="form-check-label" for="${cat.key.categoryId}">${cat.key.categoryName}</label>
                        </c:when>
                        <c:otherwise>
                            <input name="category" class="form-check-input" type="checkbox" id="${cat.key.categoryId}" value="${cat.key.categoryId}" />
                            <label class="form-check-label" for="${cat.key.categoryId}">${cat.key.categoryName}</label>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
                <button type="submit" name="action" value="category">Submit</button>
            </form>
        </div>
        <script src="scripts/manage_creator_page_script.js"></script>
    </body>
</html>
