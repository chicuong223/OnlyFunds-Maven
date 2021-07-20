
<%-- Document : navbar Created on : May 23, 2021, 10:28:46 AM Author : chiuy --%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1">
        <!-- Bootstrap -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous">
        <!-- Logo -->
        <link rel="icon" href="images/logohead.png" type="image/icontype">
        <!-- Navbar and shared css -->
        <link type="text/css" rel="stylesheet" href="styles/Navbar.css">
        <link type="text/css" rel="stylesheet" href="styles/vertical_nav.css">
        <link rel="stylesheet" href="styles/shared.css">
        <!-- Main css -->
        <link type="text/css" rel="stylesheet" href="styles/main_page.css">
        <!-- Icon -->
        <script src="https://kit.fontawesome.com/30877617bb.js" crossorigin="anonymous"></script>
    </head>

    <body>
        <nav class="navbar navbar-expand-xl navbar-light bg-light shadow p-0" id="horizontal-nav"
             style="position: sticky; top: 0; background-color: white; z-index: 100;">
            <div class="container-fluid">
                <div class="navbar-header">
                    <c:if test="${sessionScope.user == null}">
                        <a href="WelcomePageServlet" class="nav-link d-flex align-items-center"><img src="images/Logo.png"
                                                                                                     style="width:90px;" height="30px"></a>
                        </c:if>
                        <c:if test="${sessionScope.user != null}">
                        <a href="homepage" class="nav-link d-flex align-items-center"><img src="../images/Logo.png"
                                                                                           style="width:90px;" height="30px"></a>
                        </c:if>
                    <!--button when navbar is collapse-->
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
                <div class="navbar-collapse collapse" id="navbarSupportedContent">
                    <!--Left nav-->
                    <nav class="nav navbar-nav" id="left-nav">
                        <span class="nav-item dropdown me-3">
                            <a href="#Category" class="nav-link dropdown-toggle text-dark" id="navbarDropdown" role="button"
                               data-bs-toggle="dropdown">Category
                            </a>
                            <div class="container dropdown-menu">
                                <div class="row">
                                    <div class="col-sm-4 nav-link m-0 p-0 text-center text-dark">
                                        <a href="" class="tag">Tag 1</a>
                                    </div>
                                    <div class="col-sm-4 nav-link m-0 p-0 text-center text-dark">
                                        <a href="" class="tag">Tag 1</a>
                                    </div>
                                    <div class="col-sm-4 nav-link m-0 p-0 text-center text-dark">
                                        <a href="" class="tag">Tag 1</a>
                                    </div>
                                    <div class="col-sm-4 nav-link m-0 p-0 text-center text-dark">
                                        <a href="" class="tag">Tag 1</a>
                                    </div>
                                    <div class="col-sm-4 nav-link m-0 p-0 text-center text-dark">
                                        <a href="" class="tag">Tag 1</a>
                                    </div>
                                    <div class="col-sm-4 nav-link m-0 p-0 text-center text-dark">
                                        <a href="" class="tag">Tag 1</a>
                                    </div>
                                </div>
                            </div>
                        </span>
                    </nav>
                    <nav class="nav navbar-nav w-50 d-flex justify-content-end" id="middle-nav">
                        <span class="nav-item">
                            <form method="post" action="#search-function">
                                <div class="input-group input-group-sm" id="search-input">
                                    <input type="text" class="form-control" placeholder="Search...">
                                    <button class="input-group-text" id="basic-addon2" type="submit"><i
                                            class="fas fa-search"></i></button>
                                </div>
                            </form>
                        </span>
                    </nav>
                    <!--Right nav-->
                    <nav class="nav navbar-nav ms-auto" id="right-nav">
                        <!-- For visitors -->
                        <div class="nav-item me-auto" id="btn-group">
                            <a class="btn btn-sm rounded-pill" id="login-btn" href="login"><i
                                    class="fas fa-sign-in-alt me-1 align-middle"></i><span
                                    class="align-middle">Login</span></a>
                            <a class="btn btn-sm rounded-pill ms-3" id="signup-btn" href="RegisterServlet"><i
                                    class="fas fa-user me-1 align-middle"></i>
                                <span class="align-middle">Register</span>
                            </a>
                        </div>
                    </nav>
                </div>
            </div>
        </nav>
        <!-- Vertical navbar -->
        <div class="vertical-navbar" id="vertical-navbar" onmouseover="expand()" onmouseout="contract()">
            <div class="create-post">
                <a href="WritePostServlet">
                    <span class="icon"><i class="fas fa-plus-circle"></i></span>
                    <span class="title">Create post</span>
                </a>
            </div>
            <ul class="first-list" style="border-bottom: 2px solid black;">
                <li class="list">
                    <c:if test="${sessionScope.user != null}">
                        <a href="homepage">
                            <span class="icon" style="color: #ce68a8"><i class="fas fa-home"></i></span>
                            <span class="title"> Home</span>
                        </a>
                    </c:if>
                    <c:if test="${sessionScope.user == null}">
                        <a href="WelcomePageServlet">
                            <span class="icon" style="color: #ce68a8"><i class="fas fa-home"></i></span>
                            <span class="title"> Home</span>
                        </a>
                    </c:if>
                </li>
                <!--                <li class="list">
                                    <a href="#">
                                        <span class="icon">
                                    </a>
                                </li>-->
                <c:if test="${sessionScope.user != null}">
                    <li class="list">
                        <a href="YourPostServlet">
                            <span class="icon"><i class="far fa-address-book"></i></span>
                            <span class="title"> My Posts</span>
                        </a>
                    </li>
                    <li class="list">
                        <a href="LikedPostsServlet">
                            <span class="icon"><ion-icon name="heart-outline"></ion-icon></span>
                            <span class="title">Liked Posts</span>
                        </a>
                    </li>
                    <li class="list">
                        <a href="BookmarkedPostServlet">
                            <span class="icon"><ion-icon name="save-outline"></ion-icon></span>
                            <span class="title"> Saved Posts</span>
                        </a>
                    </li>
                    <li class="list">
                        <a href="ManageSubscriptions">
                            <span class="icon"><ion-icon name="pricetags-outline"></ion-icon></span>
                            <span class="title"> My Subscriptions</span>
                        </a>
                    </li>
                    <li class="list">
                        <a href="ManageFollowServlet">
                            <span class="icon"><ion-icon name="people-outline"></ion-icon></span>
                            <span class="title"> Follow</span>
                        </a>
                    </li>
                    <li>
                        <a href="#Home">
                            <span class="icon"><ion-icon name="clipboard-outline"></ion-icon></span>
                            <span class="title"> My reports</span>
                        </a>
                    </li>
                </c:if>
                <c:if test="${sessionScope.user == null}">
                    <ul>
                        <li class="list">
                            <p>Sign in to like posts, comment, subscribe and more</p>
                            <button class="btn btn-primary" onclick="location.href = 'login'">Sign in</button>
                        </li>
                    </ul>
                </c:if>
            </ul>
            <ul class="seccond-list">
                <li class="list">
                    <h3 class="ps-3 pt-3">Category</h3>
                </li>
                <li class="list">
                    <a href="SearchServlet?a=searchtag&id=1">
                        <span class="icon"><ion-icon name="color-palette-outline"></ion-icon></span>
                        <span class="title"> Art</span>
                    </a>
                </li>
                <li class="list">
                    <a href="SearchServlet?a=searchtag&id=2">
                        <span class="icon"><ion-icon name="game-controller-outline"></ion-icon></span>
                        <span class="title">Software & Game</span>
                    </a>
                </li>
                <li class="list">
                    <a href="SearchServlet?a=searchtag&id=3">
                        <span class="icon"><ion-icon name="newspaper-outline"></ion-icon></span>
                        <span class="title"> Journalism</span>
                    </a>
                </li>
                <li class="list">
                    <a href="SearchServlet?a=searchtag&id=4">
                        <span class="icon"><ion-icon name="camera-outline"></ion-icon></span>
                        <span class="title">Photography</span>
                    </a>
                </li>
                <li class="list">
                    <a href="SearchServlet?a=searchtag&id=5">
                        <span class="icon"><ion-icon name="musical-notes-outline"></ion-icon></span>
                        <span class="title"> Music</span>
                    </a>
                </li>
                <li class="list">
                    <a href="SearchServlet?a=searchtag&id=6">
                        <span class="icon"><ion-icon name="ellipsis-horizontal-outline"></ion-icon></span>
                        <span class="title"> Others</span>
                    </a>
                </li>
            </ul>
        </div>
        <script type="module" src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
        <script nomodule src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>
        <!-- JavaScript Bundle with Popper -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4"
        crossorigin="anonymous"></script>
        <div class="main-content">
            <div class="category-bar">
                <a href="#All" class="chip-link active">Recent posts</a>
                <a href="#All" class="chip-link">Most viewed</a>
                <a href="#All" class="chip-link">Most liked</a>
                <a href="#All" class="chip-link">Most active</a>
            </div>
        </div>
    </body>

</html>