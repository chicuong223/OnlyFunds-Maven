
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
        <link rel="stylesheet"
              href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x"
              crossorigin="anonymous">
        <!-- CSS by Quang Dung -->
        <link rel="stylesheet" href="styles/Welcome Page.css">
        <link rel="stylesheet" href="styles/Navbar.css">
        <link rel="icon" href="images/logo_head.png" type="image/icon type">
        <script src="https://kit.fontawesome.com/30877617bb.js" crossorigin="anonymous"></script>
    </head>

    <body>
        <div class="app">
            <!-- Navigation bar -->
            <nav class="navbar navbar-expand-md navbar-light bg-light shadow p-0">
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
                        <nav class="nav navbar-nav" id="left-nav">
                            <span class="nav-item me-3">
                                <a href="#Explore" class="nav-link text-dark"><u>Explore</u></a>
                            </span>
                            <span class="nav-item dropdown me-3">
                                <a href="#Category" class="nav-link dropdown-toggle text-dark"
                                   id="navbarDropdown" role="button" data-bs-toggle="dropdown"><u>Category</u>
                                </a>
                                <div class="container dropdown-menu">
                                    <c:set var="begin" value="0" />
                                    <c:set var="end" value="5" />
                                    <div class="row">
                                        <c:forEach begin="0" end="4">

                                            <c:forEach items="${applicationScope.catList}" var="cat"
                                                       begin="${begin}" end="${end}">
                                                <div class="col-sm-4 nav-link m-0 p-0 text-center text-dark">
                                                    <a class="tag"
                                                       href="SearchServlet?a=searchtag&id=${cat.categoryId}">
                                                        <c:out value="${cat.getCategoryName()}" />
                                                    </a>
                                                </div>
                                            </c:forEach>

                                            <c:set var="begin" value="${begin+7}" />
                                            <c:set var="end" value="${end+7}" />
                                        </c:forEach>
                                    </div>
                                </div>
                            </span>
                            <span class="nav-item me-5">
                                <a href="#Explore" class="nav-link text-dark"><u>About us</u></a>
                            </span>
                            <span class="nav-item mt-1 ms-5">
                                <form class="form-inline" action="SearchServlet?a=searchstring" method="GET">
                                    <input type="hidden" name="a" value="searchstring"/>
                                    <div class="input-group input-group-sm" id="search-input">
                                        <input type="text" class="form-control" name="search" placeholder="Search...">
                                        <button class="input-group-text" id="basic-addon2" type="submit">
                                            <i class="fas fa-search"></i>
                                        </button>
                                    </div>
                                </form>
                            </span>
                        </nav>
                        <!-- Right nav -->
                        <nav class="navbar-nav ms-auto" id="right-nav">
                            <c:choose>
                                <c:when test='${sessionScope.user == null || cookie.user.value == ""}'>
                                    <!-- For visitors -->
                                    <form action="login" method="GET">
                                        <button class="btn btn-sm rounded-pill" id="login-btn" type="submit"><i
                                                class="fas fa-sign-in-alt me-1 align-middle"></i><span
                                                class="align-middle">Login</span></button>
                                    </form>
                                    <form action="RegisterServlet" method="GET">
                                        <button class="btn btn-sm rounded-pill ms-3" id="signup-btn"><i
                                                class="fas fa-user align-middle"></i>
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
                                                <a href="PostDetailServlet?id=${noti.post.postId}">${noti.content}</a>
                                            </c:forEach>
                                        </div>
                                    </div>
                                    <!-- User management -->
                                    <div class="nav-item" id="user-dropdown">
                                        <a href="#User-info" class="nav-link" id="navbarDropdown" role="button"
                                           data-bs-toggle="dropdown"><img class="rounded-pill"
                                                                       src="${pageContext.request.contextPath}/images/avatars/${sessionScope.user.avatarURL}" 
                                                                       width=40px height=40px alt="Avatar">
                                        </a>
                                        <div class="dropdown-menu m-0 p-0" style="width: 200px; position: absolute; left: 85vw;">
                                            <a href="ManageAccount" class="col-12 nav-link m-0 p-0 text-center border-bottom">Manage account</a>
                                            <a href="ManageSubscription" class="col-12 nav-link m-0 p-0 text-center border-bottom">Manage subscription</a>
                                            <a href="ViewTransactionHistory?username=${sessionScope.user.username}" class="col-12 nav-link m-0 p-0 text-center border-bottom">Transaction History</a>
                                            <a href="logout" class="col-12 nav-link m-0 p-0 text-center">Logout</a>
                                        </div>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </nav>
                    </div>
                </div>
            </nav>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4"
        crossorigin="anonymous"></script>
    </body>

</html>