<%-- 
    Document   : vertical_navbar
    Created on : Jul 20, 2021, 3:32:59 PM
    Author     : chiuy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<head>
    <link type="text/css" rel="stylesheet" href="styles/vertical_nav.css">
    <script type="module" src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
    <script nomodule src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>
</head>
<!-- Vertical navbar -->
<div class="vertical-navbar" id="vertical-navbar">
    <div class="create-post">
        <a href="#Create post">
            <span class="icon"><i class="fas fa-plus-circle"></i></span>
            <span class="title">Create post</span>
        </a>
    </div>
    <ul class="first-list" style="border-bottom: 2px solid black;">
        <li class="list active">
            <a href="#Home">
                <span class="icon" style="color: #ce68a8"><i class="fas fa-home"></i></span>
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
            <a href="#Home">
                <span class="icon"><i class="far fa-address-book"></i></span>
                <span class="title"> My Posts</span>
            </a>
        </li>
        <li class="list">
            <a href="#Home">
                <span class="icon"><ion-icon name="heart-outline"></ion-icon></span>
                <span class="title">Liked Posts</span>
            </a>
        </li>
        <li class="list">
            <a href="#Home">
                <span class="icon"><ion-icon name="save-outline"></ion-icon></span>
                <span class="title"> Saved Posts</span>
            </a>
        </li>
        <li class="list">
            <a href="#Home">
                <span class="icon"><ion-icon name="pricetags-outline"></ion-icon></span>
                <span class="title"> My Subscription</span>
            </a>
        </li>
        <li class="list">
            <a href="#Home">
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
    </ul>
    <ul class="seccond-list">
        <li class="list">
            <h3 class="ps-3 pt-3">Category</h3>
        </li>
        <li class="list">
            <a href="#Home">
                <span class="icon"><ion-icon name="color-palette-outline"></ion-icon></span>
                <span class="title"> Art</span>
            </a>
        </li>
        <li class="list">
            <a href="#Explore">
                <span class="icon"><ion-icon name="game-controller-outline"></ion-icon></span>
                <span class="title">Software & Game</span>
            </a>
        </li>
        <li class="list">
            <a href="#Home">
                <span class="icon"><ion-icon name="newspaper-outline"></ion-icon></span>
                <span class="title"> Journalism</span>
            </a>
        </li>
        <li class="list">
            <a href="#Home">
                <span class="icon"><ion-icon name="camera-outline"></ion-icon></span>
                <span class="title">Photography</span>
            </a>
        </li>
        <li class="list">
            <a href="#Home">
                <span class="icon"><ion-icon name="musical-notes-outline"></ion-icon></span>
                <span class="title"> Music</span>
            </a>
        </li>
        <li class="list">
            <a href="#Home">
                <span class="icon"><ion-icon name="ellipsis-horizontal-outline"></ion-icon></span>
                <span class="title"> Others</span>
            </a>
        </li>

        <li class="list">
            <a href="#Home">
                <span class="icon"><i class="fas fa-home"></i></span>
                <span class="title"> Art</span>
            </a>
        </li>
    </ul>
</div>
