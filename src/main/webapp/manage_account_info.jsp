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
                   
                   <!-- Popup form (not implemented yet) -->
                   <form action="ManageAccount" method="POST" id="change-password-form" hidden>
                        Current password: <input type="password" name="currentPassword"><br>
                        <font color="red" id="passwordError">${requestScope.ERROR_LIST[0]}</font><br>
                        New password: <input type="password" name="newPassword"><br>
                        <font color="red" id="newPasswordError">${requestScope.ERROR_LIST[1]}</font><br>
                        Confirm new password: <input type="password" name="confNewPassword"><br>
                        <font color="red" id="confPasswordError">${requestScope.ERROR_LIST[2]}</font><br>
                        <button type="submit" name="action" value="password">Submit</button>
                   </form>
                        
                </div>
            </fieldset>
        </section>
    </body>
    <script src="scripts/account_info_script.js"></script>
</html>
