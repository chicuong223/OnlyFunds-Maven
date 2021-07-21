<%-- 
    Document   : unauthorized_vertical_navbar
    Created on : Jul 21, 2021, 12:21:58 AM
    Author     : dungn
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="styles/new_vertical_nav.css">
    <script type="module" src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
    <script nomodule src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>
</head>
<div class="vertical-navbar" id="vertical-navbar" onmouseover="expand()" onmouseout="contract()">
    <ul class="first-list" style="border-bottom: 2px solid black;">
        <li class="list">
            <a href="WelcomePageServlet">
                <span class="icon">
                    <ion-icon name="home-outline"></ion-icon>
                </span>
                <span class="title"> Home</span>
            </a>
        </li>
        <li class="list">
            <a href="#Explore">
                <span class="icon"><i class="far fa-compass"></i></span>
                <span class="title">Explore</span>
            </a>
        </li>
        <li class="list">
            <a href="PostListServlet?action=free">
                <span class="icon"><i class="far fa-compass"></i></span>
                <span class="title">Free post</span>
            </a>
        </li>
    </ul>
    <div class="seccond-list" style="border-bottom: 2px solid black;">
        Please login or register to have more access<br>
        <button class="btn btn-primary" onclick="location.href = 'login'">Login</button>
        <button class="btn btn-primary" onclick="location.href = 'RegisterServlet'">Sign up</button>
    </div>
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
            <a href="5">
                <span class="icon"><ion-icon name="musical-notes-outline"></ion-icon></span>
                <span class="title"> Music</span>
            </a>
        </li>
        <li class="list">
            <a href="6">
                <span class="icon"><ion-icon name="ellipsis-horizontal-outline"></ion-icon></span>
                <span class="title"> Others</span>
            </a>
        </li>
    </ul>
</div>


