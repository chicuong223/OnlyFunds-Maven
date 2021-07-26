<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<head>
    <link type="text/css" rel="stylesheet" href="styles/creator_nav.css">
    <script type="text/javascript" src="scripts/vertical_navbar.js" defer></script>
    <script type="module" src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.esm.js"></script>
    <script nomodule src="https://unpkg.com/ionicons@5.5.2/dist/ionicons/ionicons.js"></script>
</head>
<!-- Creator Vertical navbar -->
<div class="vertical-navbar" id="vertical-navbar">
    <input type="hidden" value="${isActive}" id="isActive">
    <ul class="first-list">
        <li class="list">
            <a href="ReportListServlet">
                <span class="icon"><ion-icon name="person-outline"></ion-icon></i></span>
                <span class="title" id="mAcc">Report List</span>
            </a>
        </li>
        <li class="list">
            <a href="StaffUserListServlet">
                <span class="icon"><i class="far fa-newspaper"></i></span>
                <span class="title" id="mCreate">User List</span>
            </a>
        </li>
        <li class="list">
            <a href="StaffBillListServlet">
                <span class="icon"><ion-icon name="pricetags-outline"></ion-icon></span>
                <span class="title" id="mSub">Billing History</span>
            </a>
        </li>
        <li class='list mb-2' id="logout">
            <a href='logout'>
                <span class="icon ps-1"><i class="fas fa-sign-out-alt"></i></span>
                <span class='title'>Logout</span>
            </a>
        </li>
    </ul>
</div>