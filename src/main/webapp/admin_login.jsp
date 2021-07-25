<%-- 
    Document   : admin_login
    Created on : Jul 24, 2021, 5:05:36 PM
    Author     : chiuy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
        <link rel="stylesheet" href="styles/login.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x"
              crossorigin="anonymous">
    </head>
    <body>
        <div class="mx-auto w-25 p-3 shadow">
            <form action="admin" method="POST" id="loginForm">
                <h2 class="text-center">Login</h2>
                <p class="text-center text-danger" id="error">${LOGINERROR}</p>
                <div class="form-floating">
                    <i class="fas fa-user"></i>
                    <input type="text" class="form-control" name="username" id="username"
                           value="" placeholder="Username" style="padding-left: 2.6rem;"
                           onkeypress="return RestrictSpace()">
                    <p class="text-danger" id="usernameError"></p>
                    <label for="username" style="padding-left: 2.9rem;">Username</label>
                </div>
                <div class="form-floating">
                    <i class="fas fa-lock"></i>
                    <input type="password" class="form-control" name="password" id="password"
                           placeholder="Password" style="padding-left: 2.6rem;" 
                           onkeypress="return RestrictSpace()">
                    <p class="text-danger" id="passwordError"></p>
                    <label for="password" style="padding-left: 2.9rem;">Password</label>
                </div>
                <div class="w-100">
                    <button type="submit" name="login" class="btn btn-success w-100" id="loginBtn">Login</button>
                </div>
            </form>
        </div>
        <script defer src='scripts/login_script.js'></script>
    </body>
</html>
