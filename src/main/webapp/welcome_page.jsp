<%-- 
    Document   : welcome_page
    Created on : Jun 12, 2021, 1:47:58 PM
    Author     : ASUS GAMING
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- Navigation bar -->
<c:import url="navbar.jsp"></c:import>  
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Only Funds</title>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    </head>
    <body>
        <script type="text/javascript">
            $(document).ready(function () {
                var start = 1;
                var end = 3;
                getPostData();
                $(window).scroll(function () {
                    if ($(window).scrollTop() === $(document).height() - $(window).height()) {
                        getPostData();
                    }
                });

                function getPostData() {
                    $.get('WelcomePageServlet', {start: start, end: end, action: 'load'}, function(response){
                        start += 3;
                        end += 3;
                        $('#posts').append(response);
                    }, 'text');
                }
            });
        </script>
        <!-- Main content -->
        <main class="mt-3">
            <div class="container" style="margin-top: 10vh;">
                <!-- Top -->
                <div class="row">
                    <div class="col-lg-6 p-3 my-auto text-center">
                        <span style="font-size: 50px; color:#B82481; font-family: Righteous;">ONLY FUNDS</span>
                        <p style="font-size: 40px; color:#69336D;">Create your own work and earn money!</p>
                    </div>
                    <div class="col-lg-6 p-0">
                        <img src="images/Wallpaper-2.jfif" alt="Insert image here" class="img-fluid">
                    </div>
                </div>
                <!-- Bottom -->
                <div class="row mx-auto" id="posts">

                </div>
            </div>
        </main>
    </div>
</body>
</html>
