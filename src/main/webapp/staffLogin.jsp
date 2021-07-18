<%-- Document : login Created on : May 23, 2021, 1:17:46 PM Author : chiuy --%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="refresh" content="${pageContext.session.maxInactiveInterval}">
        <title>Login</title>
        <script src="https://kit.fontawesome.com/30877617bb.js" crossorigin="anonymous"></script>
        <link rel="icon" href="images/logo_head.png" type="image/icon type">
        <link rel="stylesheet" href="styles/login.css">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Righteous">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x"
              crossorigin="anonymous">
        <style>
            .title {
                font-family: Righteous, "Segoe ui", arial;
                color: #ff75c1;
            }

            .motto {
                font-family: Arial;
                color: #7e487c;
            }

            .forgotPass {
                text-decoration: none;
                color: #ff0000;
                font-weight: bold;
            }
        </style>
    </head>

    <body>
        <header class='p-4 text-center'>
            <h1 style="font-family: Righteous" class="title">ONLY FUNDS</h1>
            <h2 class="motto">Create your own works and earn money!</h2>
        </header>
        <c:set var="x" value="${sessionScope.Lock}" />
        <div class="mx-auto w-25 p-3 shadow">
            <form action="staff" method="POST" id="loginForm">
                <h2 class="text-center">Welcome</h2>
                <p class="text-center text-danger" id="error">${LOGINERROR}</p>
                <div class="form-floating">
                    <i class="fas fa-user"></i>
                    <input type="text" class="form-control" name="username" id="username"
                           value="" placeholder="Username" style="padding-left: 2.6rem;"
                           onkeypress="return RestrictSpace()" <c:if test="${x != null}">disabled</c:if>>
                           <p class="text-danger" id="usernameError"></p>
                           <label for="username" style="padding-left: 2.9rem;">Username</label>
                    </div>
                    <div class="form-floating">
                        <i class="fas fa-lock"></i>
                        <input type="password" class="form-control" name="password" id="password"
                               placeholder="Password" style="padding-left: 2.6rem;" 
                               onkeypress="return RestrictSpace()" <c:if test="${x != null}">disabled</c:if>>
                        <p class="text-danger" id="passwordError"></p>
                        <label for="password" style="padding-left: 2.9rem;">Password</label>
                    </div>
                    <div class="form-check form-switch mb-4">
                        <input class="form-check-input" type="checkbox" name="remember" value="rem"/>
                        <label class="form-check-label" for="checkbox">Remember me</label>
                        <span class="float-end">
                            <a href="passwordEmail" style="text-decoration: none; color: red; font-weight: bold;">Forgot password?</a>
                        </span>
                    </div>

                    <div class="w-100">
                        <button type="submit" name="login" class="btn btn-success w-100" id="loginBtn" <c:if test="${x != null}">disabled</c:if>>Login</button>
                </div>
            </form>
        </div>
        <script src="scripts/login_script.js" defer></script>
    </body>

</html>