<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:import url="navbar.jsp"></c:import>
    <!DOCTYPE html>
    <html>
        <head>
            <meta charset="utf-8">
            <meta http-equiv="Content-Type" content="text/html;">
            <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1">
            <!-- Bootstrap -->
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet"
                  integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous">
            <!-- Logo -->
            <link rel="icon" href="images/logo_head.png" type="image/icon type">
            <!-- Navbar and shared css -->
            <link type="text/css" rel="stylesheet" href="styles/navbar.css">
            <link type="text/css" rel="stylesheet" href="styles/vertical_nav.css">
            <link rel="stylesheet" href="styles/shared.css">
            <!-- Main css -->
            <link type="text/css" rel="stylesheet" href="styles/main_page.css">
            <!-- Icon -->
            <script src="https://kit.fontawesome.com/30877617bb.js" crossorigin="anonymous"></script>
            <!--Ajax-->
            <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
            
        </head>

        <body>  
            <main class="main-container">
                <!-- Vertical navbar -->
                <div>
                    <c:import url="creator_vertical_navbar.jsp"></c:import>
                </div>
                <!-- Main content of the page -->
                <div class="main-content" id="main-content">
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
            </div>
        </main>
        <script type="module" src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
        <script nomodule src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>
        <script src="scripts/account_info_script.js"></script>
    </body>
</html>

