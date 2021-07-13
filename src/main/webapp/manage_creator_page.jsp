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
        <%--<c:set var="new" scope="request" value="${new}"/>--%>
        <h1>Let other users know more about you and your interests! (These can be changed later)</h1>
        <!--Change bio description-->
        <div>
            <p class="head">Bio:</p> 
            <textarea name="bio" rows="10">${user.bio}</textarea>
        </div>
        
        <!--Change interest-->
        <div>
            <c:choose>
                
                <c:when test="${newUser}">
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
                </c:when>
                
                <c:otherwise>
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
                </c:otherwise>
                    
            </c:choose>
        </div>
        
        <script src="scripts/manage_creator_page_script.js"></script>
    </body>
</html>
