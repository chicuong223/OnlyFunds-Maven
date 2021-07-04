<%-- 
    Document   : password_recovery
    Created on : May 26, 2021, 2:36:45 PM
    Author     : chiuy
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Password Reset</title>
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Righteous">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous">
        <style>
            .title{
                font-family: Righteous, "Segoe ui", arial;
                color: #ff75c1;
            }
            .motto{
                font-family: Arial;
                color: #7e487c;
            }
        </style>
    </head>
    <body>
        <header class='p-4 text-center'>
            <h1 style="font-family: Righteous" class="title">ONLY FUNDS</h1>
            <h2 class="motto">Create your own works and earn money!</h2>
        </header>
        <main>
            <div class='mx-auto w-25 p-3 shadow'>
                <h2 class='text-center'>Password Reset</h2>
                <p id="error" class='text-center text-danger'>${EMAILNOTFOUNDERROR}</p>
                <c:choose>
                    <c:when test="${flag == null}">
                        <form id="form" action="passwordEmail" method="POST">
                            <input type="hidden" name="action" value="passReset"/>
                            <div>
                                <label for="email" class='label-form'>Please enter the email address you used to register to our site</label>
                                <input type="text" name="email" id="email" class='form-control mt-2'/>
                            </div>  
                            <button type="submit" class='btn btn-success mt-3'>Submit</button>
                        </form>
                    </c:when>
                    <c:otherwise>
                        <form id="form" action="passwordReset" method="POST">
                            <div>
                                <label for="newPassword" class='label-form'>New Password</label>
                                <input type="password" name="newPass" id="newPassword" class='form-control'/>
                            </div>
                            <div>
                                <label for="confPassword" class='label-form'>Confirm Password</label>
                                <input type="password" name="confPass" id="confPassword" class='form-control'/>
                            </div>
                            <div>
                                <button type="submit" class='btn btn-success'>Reset</button>
                            </div>
                        </form>
                    </c:otherwise>
                </c:choose> 
            </div>
        </main>
        <script defer src="scripts/pass_recovery_script.js">
        </script>
    </body>
</html>
