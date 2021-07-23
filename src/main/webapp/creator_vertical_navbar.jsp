<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<head>
    <link type="text/css" rel="stylesheet" href="styles/creator_nav.css">
    <script type="module" src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
    <script nomodule src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>
</head>
<!-- Creator Vertical navbar -->
<div class="vertical-navbar" id="vertical-navbar">
    <ul class="first-list">
        <li class="list">
            <a href="#Explore">
                <span class="icon"><ion-icon name="person-outline"></ion-icon></i></span>
                <span class="title">Manage Account</span>
            </a>
        </li>
        <li class="list">
            <a href="#Explore">
                <span class="icon"><i class="far fa-newspaper"></i></span>
                <span class="title">Creator's Page</span>
            </a>
        </li>
        <li class="list">
            <a href="#Explore">
                <span class="icon"><ion-icon name="wallet-outline"></ion-icon></i></span>
                <span class="title">Billing History</span>
            </a>
        </li>
        <li class="list">
            <a href="#Explore">
                <span class="icon"><ion-icon name="pricetags-outline"></ion-icon></span>
                <span class="title">My Subscription</span>
            </a>
        </li>
        <li class="list">
            <a href="#Explore">
                <span class="icon"><ion-icon name="file-tray-stacked-outline"></ion-icon></span>
                <span class="title">Tier Management</span>
            </a>
        </li>
        <li class='list'>
            <a href='#MyReport'>
                <span class="icon"><i class="far fa-clipboard"></i></span>
                <span class='title'>My Reports</span>
            </a>
        </li>
        <li class='list mb-2' id="logout">
            <a href='#Logout'>
                <span class="icon ps-1"><i class="fas fa-sign-out-alt"></i></span>
                <span class='title'>Logout</span>
            </a>
        </li>
    </ul>
</div>