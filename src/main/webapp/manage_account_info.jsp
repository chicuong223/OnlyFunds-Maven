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
                    <form action="ManageAccount" method="post" enctype="multipart/form-data">
                        <input type="hidden" name="action" value="avatar"/>
                        <p class="head">Profile picture: </p>
                        <p><img src="images/avatars/${user.avatarURL}" id="avatar"/></p>
                        <div class="form-group">
                            <label for="avatar">Choose new avatar</label>
                            <input type="file" name="avatar" class="form-control" id="avatar"/>
                        </div>
                        <div class="form-group">
                            <button class="btn btn-success" type="submit">Submit</button>
                        </div>
                    </form>
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
                    <button id="change-password-btn">Change password</button>
                    <!-- Popup form (not implemented yet) -->
                    <form action="ManageAccount" method="POST" id="change-password-form" hidden>
                        <div class="form-group">
                            <label for="currPassword">Current password: </label>
                            <input type="password" name="currentPassword" id="currPassword"/>
                            <p class="text-danger" id="passwordError">${requestScope.ERROR_LIST[0]}</p>
                        </div>
                        <div class="form-group">
                            <label for="newPassword">New password: </label>
                            <input type="password" name="newPassword" id="newPassword"/>
                            <p class="text-danger" id="newPasswordError">${requestScope.ERROR_LIST[1]}</p>
                        </div>
                        <div class="form-group">
                            <label for="confPassword">Confirm new password: </label>
                            <input type="password" name="confNewPassword" id="confPassword"/>
                            <p class="text-danger" id="confPasswordError">${requestScope.ERROR_LIST[2]}</p>
                        </div>
                        <div class="form-group">
                            <button type="submit" name="action" value="password" class="btn btn-success">Submit</button>
                        </div>
                    </form>
                </div>
            </fieldset>
        </section>
    </body>
    <script src="scripts/account_info_script.js"></script>
</html>
