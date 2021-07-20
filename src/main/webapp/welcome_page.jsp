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
        <link rel="stylesheet" href="styles/welcome_page.css">
        <link rel="stylesheet" href="styles/Navbar.css">
        <link rel="stylesheet" href="styles/main_page.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    </head>
    <body>
        <script type="text/javascript">
            $(document).ready(function () {
                var start = 1;
                var end = 4;
                getPostData();
                $(window).scroll(function () {
                    getPostData();
                });
                function getPostData() {
                    $.get('WelcomePageServlet', {start: start, end: end, action: 'load'}, function (response) {
                        start += 4;
                        end += 4;
                        $('#posts').append(response);
                    }, 'text');
                }
            });
        </script>
        <!-- Main content -->
        <main class="mt-3">
            <div class="container main-content" style="margin-top: 1rem;">
                <!-- Top -->
                <div class="row">
                    <div class="col-lg-12 p-3 pb-1 my-auto text-center">
                        <span style="font-size: 50px; color:#B82481; font-family: Righteous;">ONLY FUNDS</span>
                        <p style="font-size: 40px; color:#69336D;">Create your own work and earn money!</p>
                        <a href="RegisterServlet" class="register-link">
                            <span>Get started!</span>
                            <svg width="18px" height="15px" viewBox="0 0 13 10">
                            <path d="M1,5 L11,5"></path>
                            <polyline points="8 1 12 5 8 9"></polyline>
                            </svg>
                        </a>
                    </div>
                </div>
                <div id="posts" class="row gx-4 p-3 mb-4">
                    <div class="header mb-4">
                        <span class="p-0 mb-5 mt-3"
                              style="font-size: 40px; font-weight: bold; border-bottom: 2px solid #B82481;">Newly uploaded posts</span>
                    </div>
                    <!-- Mỗi post tạo 1 column tương ứng -->
                </div>
            </div>
        </main>
    </div>
</body>
</html>
