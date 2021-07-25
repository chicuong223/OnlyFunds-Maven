<%-- 
    Document   : vertical_navbar
    Created on : Jul 20, 2021, 3:32:59 PM
    Author     : chiuy
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<head>
    <link type="text/css" rel="stylesheet" href="styles/vertical_nav.css">
    <script type="text/javascript" src="scripts/vertical_navbar.js" defer></script>
    <script type="module" src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
    <script nomodule src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>
</head>
<!-- Vertical navbar -->
<div class="vertical-navbar" id="vertical-navbar">
    <input type="hidden" value="${isActive}" id="isActive">
    <div class="create-post" <c:if test="${sessionScope.user == null}">style="display:none"</c:if>>
            <a href="WritePostServlet">
                <span class="icon"><i class="fas fa-plus-circle"></i></span>
                <span class="title">Create post</span>
            </a>
        </div>
        <ul class="first-list" style="border-bottom: 2px solid black;">
            <li class="list">
            <c:if test="${sessionScope.user == null}">
                <a href="WelcomePageServlet">
                    <span class="icon"><ion-icon name="home-outline"></ion-icon></span>
                    <span class="title" id="home"> Home</span>
                </a>
            </c:if>
            <c:if test="${sessionScope.user != null}">
                <a href="homepage">
                    <span class="icon"><ion-icon name="home-outline"></ion-icon></span>
                    <span class="title" id="home"> Home</span>
                </a>
            </c:if>
        </li>
        <li class="list">
            <a href="explore">
                <span class="icon"><i class="far fa-compass"></i></span>
                <span class="title" id="explore">Explore</span>
            </a>
        </li>
        <li class='list'>
            <a href='PostListServlet?action=free'>
                <span class="icon"><ion-icon name="globe-outline"></ion-icon></span>
                <span class='title' id="free">Free Posts</span>
            </a>
        </li>
        <c:if test="${sessionScope.user == null}">
            <li class="list">
                <p>Please sign in to like posts, comment subscribe and more</p>
                <button class="btn btn-primary" onclick="location.href = 'login'">Sign in</button>
            </li>
        </c:if>
        <c:if test="${sessionScope.user != null}">
            <li class="list">
                <a href="FollowingListServlet">
                    <span class="icon"><ion-icon name="people-outline"></ion-icon></span>
                    <span class="title" id="follow"> Follow</span>
                </a>
            </li>
        </c:if>
    </ul>
    <c:if test="${sessionScope.user != null}">
        <ul class="second-list">
            <li class="list">
                <a href="#toggle" class="marker" onclick="toggle('.second-list')">
                    <span class="marker">
                        Posts
                    </span>
                    <span>
                        <ion-icon name="caret-forward-outline"></ion-icon>
                    </span>
                </a>
            </li>
            <li class="list">
                <a href="YourPostsServlet">
                    <span class="icon"><i class="far fa-address-book"></i></span>
                    <span class="title" id="mPost"> My Posts</span>
                </a>
            </li>
            <li class="list">
                <a href="PostListServlet?action=liked">
                    <span class="icon"><ion-icon name="heart-outline"></ion-icon></span>
                    <span class="title" id="liked">Liked Posts</span>
                </a>
            </li>
            <li class="list">
                <a href="PostListServlet?action=saved">
                    <span class="icon"><ion-icon name="save-outline"></ion-icon></span>
                    <span class="title" id="saved"> Saved Posts</span>
                </a>
            </li>
        </ul>
    </c:if>
    <ul class="third-list">
        <li class="list">
            <a href="#toggle" class="marker" onclick="toggle('.third-list')">
                <span class="marker">
                    Category
                </span>
                <span>
                    <ion-icon name="caret-forward-outline"></ion-icon>
                </span>
            </a>
        </li>
        <li class="list">
            <a href="SearchServlet?a=searchtag&id=1">
                <span class="icon"><ion-icon name="color-palette-outline"></ion-icon></span>
                <span class="title" id="1"> Art</span>
            </a>
        </li>
        <li class="list">
            <a href="SearchServlet?a=searchtag&id=2">
                <span class="icon"><ion-icon name="game-controller-outline"></ion-icon></span>
                <span class="title" id="2">Software & Game</span>
            </a>
        </li>
        <li class="list">
            <a href="SearchServlet?a=searchtag&id=3">
                <span class="icon"><ion-icon name="newspaper-outline"></ion-icon></span>
                <span class="title" id="3"> Journalism</span>
            </a>
        </li>
        <li class="list">
            <a href="SearchServlet?a=searchtag&id=4">
                <span class="icon"><ion-icon name="camera-outline"></ion-icon></span>
                <span class="title" id="4">Photography</span>
            </a>
        </li>
        <li class="list">
            <a href="SearchServlet?a=searchtag&id=5">
                <span class="icon"><ion-icon name="musical-notes-outline"></ion-icon></span>
                <span class="title" id="5"> Music</span>
            </a>
        </li>
        <li class="list">
            <a href="SearchServlet?a=searchtag&id=6">
                <span class="icon"><ion-icon name="ellipsis-horizontal-outline"></ion-icon></span>
                <span class="title" id="6"> Others</span>
            </a>
        </li>
    </ul>
</div>
