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
        <title>Profile Page</title>
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
        <!--Navigation bar-->
        <c:import url="navbar.jsp"></c:import>
        
        <section class="body-text">
            <c:set var="user" value="${sessionScope.user}"/>
            <fieldset>
                <legend><h1 style="font-size: 150%">Account Information</h1></legend>
                <div>
                    <p class="head">Profile picture: </p>
                    <p><img src="${pageContext.request.contextPath}/images/avatars/${user.avatarURL}" id="avatar"/></p>
                </div>
                <div>
                    <p class="head">Username: </p>
                    <p>${user.username}</p>
                </div>
                <div>
                    <p class="head">Name: </p>
                    <p>${user.firstName} ${user.lastName}</p>
                </div>
                <div>
                    <p class="head">Email: </p>
                    <p>${user.email}</p>
                </div>
                <div>
                   <p class="head">Password: ********</p>
                   <button id="change-password-btn">Change password</button>
                   <form action="ManageAccount" method="POST" id="change-password-form" hidden>
                        Current password: <input type="text" name="currentPassword"><br>
                        <font color="red" id="usernameError">${requestScope.ERROR_LIST[0]}</font><br>
                        New password: <input type="text" name="newPassword"><br>
                        <font color="red" id="usernameError">${requestScope.ERROR_LIST[1]}</font><br>
                        Confirm new password: <input type="text" name="confNewPassword"><br>
                        <font color="red" id="usernameError">${requestScope.ERROR_LIST[2]}</font><br>
                        <button type="submit" name="action" value="password">Submit</button>
                   </form>
                </div>
                <div>
                    <p class="head">Interests:</p> <button id="change-interest-btn">Change interest:</button>
                    <div id="currentInterest">
                        <c:choose>
                            <c:when test="${!empty userCatList}">
                                <c:forEach items="${applicationScope.userCatList}" var="ucat">
                                    <strong>${ucat.categoryName}</strong>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <strong>None</strong>
                            </c:otherwise>
                        </c:choose>
                    </div>
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
            </fieldset>
        </section>
    </body>
    <script src="scripts/account_info_script.js"></script>
</html>
