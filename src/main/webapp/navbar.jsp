
<%-- Document : navbar Created on : May 23, 2021, 10:28:46 AM Author : chiuy --%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <!-- Default -->
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="apple-touch-icon" sizes="180x180" href="/images/apple-touch-icon.png">
        <link rel="icon" type="image/png" sizes="32x32" href="/images/favicon-32x32.png">
        <link rel="icon" type="image/png" sizes="16x16" href="/images/favicon-16x16.png">
        <link rel="manifest" href="/site.webmanifest">
        <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Righteous">
        <link rel="stylesheet"
              href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x"
              crossorigin="anonymous">
        <!-- CSS by Quang Dung -->
        <!--<link rel="stylesheet" href="styles/Welcome Page.css">-->
        <link rel="stylesheet" href="styles/login.css">
        <link type="text/css" rel="stylesheet" href="styles/navbar.css">
        <!--<link rel="stylesheet" href="/styles/shared.css">-->
        <link rel="icon" href="images/logo_head.png" type="image/icon type">
        <script src="https://kit.fontawesome.com/30877617bb.js" crossorigin="anonymous"></script>
    </head>
    <body>
        <!-- Navigation bar -->
        <nav class="navbar navbar-expand-md navbar-light bg-white shadow p-0 border-bottom" id="navbar-container">
            <div class="container-fluid">
                <div class="navbar-header">
                    <c:choose>
                        <c:when test='${sessionScope.user==null||cookie.user.value==""}'>
                            <a href="WelcomePageServlet" class="nav-link d-flex align-items-center">
                                <img src="images/Logo.png" style="width:90px;" height="30px"/>
                            </a>
                        </c:when>
                        <c:otherwise>
                            <a href="homepage" class="nav-link d-flex align-items-center">
                                <img src="images/Logo.png" style="width:90px;" height="30px"/>
                            </a>
                        </c:otherwise>
                    </c:choose>

                    <!-- Button when navbar is collapsed -->
                    <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                            data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                            aria-expanded="false" aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                </div>
                <!--collapse when screen is smaller than 1200px-->
                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <!--Left nav-->
                    <nav class="nav navbar-nav w-50 d-flex justify-content-end" id="middle-nav">
                        <span class="nav-item">
                            <form method="get" action="SearchServlet">
                                <input type="hidden" value="searchstring" name="a"/>
                                <div class="input-group input-group-sm" id="search-input">
                                    <input type="text" class="form-control" name="search" placeholder="Search...">
                                    <button class="input-group-text" id="basic-addon2" type="submit"><i
                                            class="fas fa-search"></i></button>
                                </div>
                            </form>
                        </span>
                    </nav>
                    <!-- Right nav -->
                    <nav class="navbar-nav ms-auto" id="right-nav">
                        <c:choose>
                            <c:when test='${sessionScope.user == null || cookie.user.value == ""}'>
                                <!-- For visitors -->
                                <!--<form action="login" method="GET">-->
                                <button class="btn btn-sm rounded-pill" id="login-btn" type="submit" data-bs-toggle="modal" data-bs-target="#modal-login"><i
                                        class="fas fa-sign-in-alt me-1 align-middle"></i><span
                                        class="align-middle">Login</span></button>
                                <!-- login form modal -->
                                <!--</form>-->
                                <form action="RegisterServlet" method="GET">
                                    <button class="btn btn-sm rounded-pill ms-3" id="signup-btn"><i
                                            class="fa fa-user-circle align-middle"></i>
                                        <span class="align-middle">Register</span>
                                    </button>
                                </form>
                            </c:when>
                            <c:otherwise>
                                <!-- For user -->
                                <div class="nav-item" id="notification">
                                    <a href="#Category" class="nav-link" id="navbarDropdown" role="button"
                                       data-bs-toggle="dropdown"><i class="fas fa-bell"></i>
                                    </a>
                                    <div class="dropdown-menu p-2" id="dropdown-noti">
                                        <c:forEach items="${sessionScope.notiList}" var="noti">
                                            <a href="PostDetailServlet?id=${noti.post.postId}&noti=${noti.notificationId}">${noti.content}</a>
                                        </c:forEach>
                                    </div>
                                </div>
                                <!-- User management -->
                                <div class="nav-item" id="user-dropdown">
                                    <a href="#User-info" class="nav-link" id="navbarDropdown" role="button"
                                       data-bs-toggle="dropdown"><img class="rounded-pill"
                                                                   src="images/avatars/${sessionScope.user.avatarURL}" 
                                                                   width=40px height=40px alt="Avatar">
                                    </a>
                                    <div class="dropdown-menu m-0 p-0" style="width: 200px; position: absolute; left: 85vw;">
                                        <a href="ManageAccount" class="col-12 nav-link m-0 p-0 text-center border-bottom">Manage account</a>
                                        <a href="ManageCreatorPage" class="col-12 nav-link m-0 p-0 text-center border-bottom">Manage creator page</a>
                                        <a href="ManageSubscriptions" class="col-12 nav-link m-0 p-0 text-center border-bottom">Manage subscription</a>
                                        <a href="ViewTransactionHistory" class="col-12 nav-link m-0 p-0 text-center border-bottom">Billing History</a>
                                        <a href="logout" class="col-12 nav-link m-0 p-0 text-center">Logout</a>
                                    </div>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </nav>
                </div>
            </div>
        </nav>

        <!-- modal login -->
        <c:if test="${sessionScope.user == null}" >
            <div class="modal fade" id="modal-login" tabindex="-1">
                <div class="modal-dialog modal-dialog-centered">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title">Login</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <form action="login" method="POST" id="loginForm">
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
                                        <button type="submit" name="login" class="btn btn-success w-100" id="loginBtn" <c:if
                                                test="${x != null}">disabled</c:if>
                                                >Login</button>
                                    </div>
                                </form>
                            </div>
                            <div class="modal-footer">
                                <div>
                                    <p>Don't have an account? <a href="RegisterServlet" style="text-decoration: none; color: red; font-weight: bold;">Sign up</a></p>                      
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
        </c:if>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4"
        crossorigin="anonymous"></script>
        <script src="scripts/login_script.js" defer></script>
    </body>

</html>