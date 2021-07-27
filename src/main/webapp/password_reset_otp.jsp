<%-- 
    Document   : otp
    Created on : May 28, 2021, 4:05:47 PM
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
            <div class="mx-auto w-25 p-3 shadow">
                <form method="POST" action="passwordReset">
                    <h2 class="text-center">Password Reset</h2>
                    <p class='text-center text-danger'>${INVALIDOTP}</p>
                    <label for="otp" class="label-form">Enter the OTP sent to your email</label>
                    <input type="text" name="otp" class="form-control" id="otp"/>
                    <button type="submit" name="action" value="otp" class="btn btn-success mt-2">Submit</button>
                </form>
            </div>
        </main>
        <script src='scripts/password_otp_script.js' async>
        </script>
    </body>
</html>
