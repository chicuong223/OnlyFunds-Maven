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
    <script type="text/javascript" src="scripts/vertical_navbar.js" defer></script>
    <script type="module" src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
    <script nomodule src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>
</head>
<div class="vertical-navbar" id="vertical-navbar">
    <input type="hidden" value="${isActive}" id="isActive">
    <ul class="first-list" style="border-bottom: 2px solid black;">
        <li class="list">
            <a href="WelcomePageServlet">
                <span class="icon">
                    <ion-icon name="home-outline"></ion-icon>
                </span>
                <span class="title" id="home"> Home</span>
            </a>
        </li>
        <li class="list">
            <a href="explore">
                <span class="icon"><i class="far fa-compass"></i></span>
                <span class="title" id="explore">Explore</span>
            </a>
        </li>
        <li class="list">
            <a href="PostListServlet?action=free">
                <span class="icon"><ion-icon name="globe-outline"></ion-icon></span>
                <span class="title" id="free">Free post</span>
            </a>
        </li>
    </ul>
    <div class="second-list" style="border-bottom: 2px solid black;">
        <span>Please login or sign up to have access to more features</span><br>
        <button class="btn btn-sm btn-primary" data-bs-toggle="modal" data-bs-target="#modal-login">Login</button>
        <button class="btn btn-sm btn-primary" onclick="location.href = 'RegisterServlet'">Sign up</button>
    </div>
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
<script type="text/javascript">
        function toggle(parameter) {
            let list = document.querySelector(parameter);
            if(list.classList.contains('active')) {
                list.classList.remove('active');
            }
            else {
                list.classList.add('active');
            }
        }
    </script>


