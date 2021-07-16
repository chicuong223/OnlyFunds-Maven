<%-- 
    Document   : register
    Created on : May 24, 2021, 10:00:33 PM
    Author     : chiuy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>Register</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Righteous">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous">
        <script src="https://www.google.com/recaptcha/api.js" async defer></script>
        <style>
            .title{
                font-family: Righteous, "Segoe ui", arial;
                color: #ff75c1;
            }
            .motto{
                font-family: Arial;
                color: #7e487c;
            }

            header{
                text-align: center;
            }
            
            #pic{
                float: right; padding:0; 
                height: 10rem; 
                line-height: 9rem; 
            }
            small.success {
                display: none;
            }
            small.error {
                display: block;
                color: red;
            }
        </style>
    </head>
    <body>
        <header class='p-4'>
            <h1 style="font-family: Righteous" class="title">ONLY FUNDS</h1>
            <h2 class="motto">Create your own works and earn money!</h2>
        </header>
        <div class="mx-auto p-3 shadow" style="width: 40%;">
            <form id="register-form" action="RegisterServlet?action=register" method="POST" enctype="multipart/form-data">
                <h2 class="text-center">Register</h2>
                <c:set var="newUser" value="${sessionScope.user}"/>
                <div class="row">
                    <div class="col-sm-6">
                        <label for="firstname">First name</label>
                        <input type='text' placeholder='First name' class='form-control' name='firstname' id='firstname' value="${newUser.firstName}"/>
                        <small class="success">Error message</small>  
                        <font color="red" id="usernameError">${requestScope.ERROR_LIST[0]}</font>
                    </div>
                    <div class="col-sm-6">
                        <label for="lastname">Last name</label>
                        <input type='text' placeholder="Last name" class='form-control' name='lastname' id='lastname' value="${newUser.lastName}"/>
                        <small class="success">Error message</small>  
                        <font color="red" id="usernameError">${requestScope.ERROR_LIST[1]}</font>
                    </div>
                </div>
                        
                <div>
                    <label for="username">Username</label>
                    <input type='text' placeholder="Username(4-16 characters)" class='form-control' name='username' id='username' value="${newUser.username}" onkeypress="return blockSpaceKey()"/>
                    <small class="success">Error message</small>
                    <font color="red" id="usernameError">${requestScope.ERROR_LIST[2]}</font>
                </div>
                
                <div>
                    <label for="email">Email</label>
                    <input type='text' placeholder="Email" class='form-control' name='email' id='email' value="${newUser.email}" onkeypress="return blockSpaceKey()"/>
                    <small class="success">Error message</small>
                    <font color="red" id="emailError">${requestScope.ERROR_LIST[3]}</font>
                </div>
                
                <div>
                    <label for="Password">Password</label>
                    <input type='password' placeholder="Password(more than 6 characters)" class='form-control' name='password' id='password' onkeypress="return blockSpaceKey()"/>
                    <font color="red" id="passwordError">${requestScope.ERROR_LIST[4]}</font>
                    <small class="success">Error message</small>
                </div>
                
                <div>
                    <label for="confPass">Confirm passowrd</label>
                    <input type='password' placeholder="Confirm your password" class='form-control' name='confPass' id='confPass' onkeypress="return blockSpaceKey()"/>
                    <font color="red" id="confPassError">${ERROR_LIST[5]}</font>
                    <small class="success">Error message</small>
                </div>
                
                <div class="container">
                    <div class="row">
                        <div class="col-5 m-0 p-0" style="padding-left:0">
                            <label class='label-form' for='avatarURL'>Profile Picture</label>
                            <input type="file" class="form-control" name="avatar" id="avatarURL" accept="image/*"/>
                        </div>
                        <div class="col-12 mt-2 mb-3 text-center" id="pic" style="border: dashed 5px;">
                            <span class="align-middle" id="zone">Your profile picture is displayed here</span>
                            <img id="output" style="max-height: 100%; max-width: 100%"/>
                        </div>
                    </div>
                </div>
                
                <!-- reCaptcha -->
                <div class="form-group">
                    <div 
                        class="g-recaptcha" 
                        data-callback="recaptchaCallback" 
                        data-sitekey="6Lfl4CkbAAAAAOA9Fjmv0xIrry41nfNz_EHYNEPJ" 
                        onsubmit="onSubmit">
                    </div>
                </div>
                <!-- end of reCaptcha -->
                
                <div class='mt-3 text-center'>
                    <button type='submit' class='btn btn-danger' id="register-btn" disabled>Register</button>
                </div>
            </form>
        </div>
        <script defer>
            //if reCaptcha is loaded -> register-btn enabled
            function recaptchaCallback() {
                var registerBtn = document.querySelector("#register-btn");
                
                registerBtn.removeAttribute("disabled");
                registerBtn.style.cusor = "pointer";
            }
            
            let loadFile = function (event) {
                const image = document.getElementById('output');
                image.src = URL.createObjectURL(event.target.files[0]);
            };
            const zone = document.getElementById('zone');

            document.getElementById("avatarURL").addEventListener('change', event => {
                if(zone !== null) zone.remove();
                document.getElementById('pic').style.border = null;
                loadFile(event);
            });
        </script>
        <script src="scripts/register_script.js" defer></script>
    </body>
    
</html>